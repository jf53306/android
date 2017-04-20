# LocalImgLoader

## 初衷

    之前自己写了一个多媒体选择器，以满足本地文件上传的需求，可是有部分图片的缩略图在第三方框架下无法显示，
    于是自己写了一个本地图片加载的类
    
## 要点
    * 三级缓存
    * 创建缩略图
    * 复用机制导致的图片错乱
    * 建立了原图和缩略图的本地关联
    
## 工作流程
    
    * 初始化：维护一个后台轮询进程，维护一个任务队列，维护一个线程池用来执行任务，读取配置信息
    * 获取到图片地址，核对是否重复加载，创建任务并加入队列
    * 执行任务：先获读取内存，没有就读取缩略图文件，在没有就创建缩略图文件并建立关联
    * 图片展示：核对是否为目标图片，如果不是，显示占位图，如果是，显示图片
    * 结束：将关联信息存入配置文件，方便下次读取
    
    
## 代码

```java
public class LocalImgLoader {
    public static final int TYPE_VIDEO=110;
    public static final int TYPE_IMG=111;
    public static final int THREAD_COUNT=10;

    private String diskImgCacheDir;
    private LruCache<String,Bitmap> mLruCache;
    /**
     * 任务队列
     */
    private LinkedList<Runnable> mTaskQueue;
    /**
     * 后台轮询线程
     */
    private Thread mPoolThread;
    private Handler mPoolThreadHandler;
    /**
     * UI线程中的Handler
     */
    private Handler mUIHandler;

    /**
     * 线程池
     */
    private ExecutorService mThreadPool;
    private Semaphore mSemaphorePoolThreadHandler = new Semaphore(0);
    private Semaphore mSemaphoreThreadPool;

    private ConcurrentHashMap<String,String> diskCacheRecoder;

    private static LocalImgLoader instance;

    public static LocalImgLoader getInstance(Context context){
        if (instance==null){
            instance=new LocalImgLoader(context);
        }
        return instance;
    }

    private LocalImgLoader(Context context){
        init(context);
    }

    private void init(Context context){
        initBackThread();
        // 获取我们应用的最大可用内存
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheMemory = maxMemory / 8;
        mLruCache = new LruCache<String, Bitmap>(cacheMemory)
        {
            @Override
            protected int sizeOf(String key, Bitmap value)
            {
                return value.getRowBytes() * value.getHeight();
            }

        };

        // 创建线程池
        mThreadPool = Executors.newFixedThreadPool(THREAD_COUNT);
        mTaskQueue = new LinkedList<Runnable>();
        mSemaphoreThreadPool = new Semaphore(THREAD_COUNT);

        //创建硬盘缓存路径
        diskImgCacheDir=FileUtils.getAlbumDiskCachePath(context);
        FileUtils.createDir(diskImgCacheDir);
        //获取缓存对应关系
        diskCacheRecoder=getRecorder(context);
        //判断对应关系是否正确
        //应该在线程中执行
//        addTask(new Runnable() {
//            @Override
//            public void run() {
//                checkDiskCache(diskCacheRecoder);
//                mSemaphoreThreadPool.release();
//            }
//        });
        checkDiskCache(diskCacheRecoder);
    }

    public boolean hasLruOrDiskCache(String path){
        return hasLruCached(path)||hasDiskCached(path);
    }

    /**
     * 初始化后台轮询线程
     */
    private void initBackThread()
    {
        // 后台轮询线程
        mPoolThread = new Thread()
        {
            @Override
            public void run()
            {
                Looper.prepare();
                mPoolThreadHandler = new Handler()
                {
                    @Override
                    public void handleMessage(Message msg)
                    {
                        // 线程池去取出一个任务进行执行
                        mThreadPool.execute(getTask());
                        try
                        {
                            mSemaphoreThreadPool.acquire();
                        } catch (InterruptedException e)
                        {
                        }
                    }
                };
                // 释放一个信号量
                mSemaphorePoolThreadHandler.release();
                Looper.loop();
            };
        };

        mPoolThread.start();
    }




    /**
     * 从任务队列取出一个方法
     *
     * @return
     */
    private synchronized Runnable getTask() {
        if (mTaskQueue.size()>0){
            return mTaskQueue.removeFirst();
        }else return null;
    }



    /**
     * 根据path为imageview设置图片
     *
     * @param path
     * @param imageView
     */
    public void loadImage(final String path, final ImageView imageView)
    {
        loadImage(path,imageView,TYPE_IMG);
    }

    public void loadImage(final String path,final ImageView imageView,int type){
        //如果图片和原来的不一样
        if (!isConflict(imageView,path)){
            if (mUIHandler == null)
            {
                mUIHandler = new Handler()
                {
                    public void handleMessage(Message msg)
                    {
                        // 获取得到图片，为imageview回调设置图片
                        if (msg.what==111){
                            Holder holder = (Holder) msg.obj;
                            if (canShowImg(holder.url,holder.iv)){
                                holder.iv.setImageBitmap(holder.bm);
                            }
                        }
                    };
                };
            }
            addTask(buildTask(path, imageView,type));
        }
    }

    private synchronized void addTask(Runnable runnable){
        mTaskQueue.add(runnable);
        // if(mPoolThreadHandler==null)wait();
        try
        {
            if (mPoolThreadHandler == null)
                mSemaphorePoolThreadHandler.acquire();
        } catch (InterruptedException e)
        {
        }
        mPoolThreadHandler.sendEmptyMessage(0x110);
    }


    /**
     * 根据传入的参数，新建一个任务
     *
     * @param path
     * @param imageView
     * @return
     */
    private Runnable buildTask(final String path, final ImageView imageView, final int type){
        return new Runnable()
        {
            @Override
            public void run()
            {
                    Bitmap bm=getBm(path,type);
                    if (bm!=null){
                        Message message = mUIHandler.obtainMessage(111);
                        message.obj=new Holder(imageView,path,bm);
                        mUIHandler.sendMessage(message);
                    }
                mSemaphoreThreadPool.release();
            }


        };
    }

    public void canclAllTask(){
        mThreadPool.shutdownNow() ;
    }

    //创建缩略图并获取地址
    public String getThumbUrl(String path,int type){
        Bitmap bm=getOrig(path,type);
        return getDiskCacheUrl(path);
    }

    //获取缩略图
    private Bitmap getBm(final String path,int type){
        if (hasLruCached(path)){
            return getLruCache(path);
        }else if(hasDiskCached(path)){
            Bitmap disk=getDiskCache(path);
            if (disk==null){
                ////
                return getOrig(path,type);
            }else {
               return disk;
            }
        }else {
            //压缩图片并且存储到内存和硬盘
            return getOrig(path,type);
        }
    }
    //压缩原图
    private Bitmap getOrig(String path,int type){
        Bitmap bitmap=null;
        switch (type){
            case TYPE_IMG:
                bitmap = BitmapUtils.compress(path, 100);
                break;
            case TYPE_VIDEO:
                bitmap = FileUtils.getVideoThumbnail(path);
                break;
        }

        if (bitmap!=null){
            lruCache(path,bitmap);
            diskCache(bitmap,path);
        }
        return bitmap;
    }

    private boolean isConflict(ImageView iv,String url){
        Object tag = iv.getTag(R.id.item_url);
        if (tag!=null&&tag.equals(url)){
            return true;
        }else {
            iv.setTag(R.id.item_url,url);
            iv.setImageResource(R.mipmap.pic_wait_for_complete);
//            iv.setImageBitmap(null);
            return false;
        }
    }

    public boolean shouldRequest(ImageView iv,String url){
        Object tag=iv.getTag(R.id.item_url);

        boolean isSame=tag!=null&&tag.equals(url);
        return !isSame;
    }


    private boolean canShowImg(String path,ImageView iv){
        Object tag = iv.getTag(R.id.item_url);
        return tag!=null&&tag.equals(path);
    }



    private boolean hasDiskCached(String path){
        return diskCacheRecoder.containsKey(path);
    }

    private void diskCache(Bitmap bitmap,String path){
        String output=new File(diskImgCacheDir,FileUtils.getFileNameNoEx(path)+(int)(Math.random()*1000)).getAbsolutePath();
        if (BitmapUtils.save(output,bitmap)){
            diskCacheRecoder.put(path,output);
        }

    }

    private void removeDiskCache(String path){
        FileUtils.clear(new File(diskCacheRecoder.get(path)));
        diskCacheRecoder.remove(diskCacheRecoder.get(path));
    }

    private Bitmap getDiskCache(String path){
        return BitmapFactory.decodeFile(diskCacheRecoder.get(path));
    }

    private void lruCache(String path,Bitmap bm){
        mLruCache.put(path,bm);
    }

    private Bitmap getLruCache(String path){
        return mLruCache.get(path);
    }

    private boolean hasLruCached(String path){
       return mLruCache.get(path)!=null;
    }

    //保留缩略图对应关系
    public void saveRecorder(Context context){
        String recorder=new Gson().toJson(diskCacheRecoder);
        SPUtils.setParam(context,"thumb_recorder",recorder);
    }

    private ConcurrentHashMap<String,String> getRecorder(Context context){
        String recorder=String.valueOf(SPUtils.getParam(context,"thumb_recorder",""));
        if (recorder.equals("")){
            return  new ConcurrentHashMap<>();
        }else {
            return new Gson().fromJson(recorder,new TypeToken<ConcurrentHashMap<String,String>>(){}.getType());
        }

    }

    private void checkDiskCache(Map<String,String> cache){
        for (Map.Entry<String,String> entry:cache.entrySet()){
            final File keyFile=new File(entry.getKey());
            final File valueFile=new File(entry.getValue());
            if (!keyFile.exists()){
                cache.remove(entry.getKey());
                if (valueFile.exists()){
                    valueFile.delete();
                }
            }
        }
    }

    public String getDiskCacheUrl(String path){
        return diskCacheRecoder.get(path);
    }

    class Holder{
        ImageView iv;
        String url;
        Bitmap bm;
        public Holder(ImageView iv,String url,Bitmap bm){
            this.iv=iv;
            this.url=url;
            this.bm=bm;
        }
    }
}
```
