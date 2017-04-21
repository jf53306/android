# Intent 

Android 中Activity之间的交互，是通过Intent来实现的，某些情况下，我们需要上一个Activity的参数，
这种情况下是获取不到上一个Activity的引用的，而且也不可能需要传参就把数据源写成静态的。那么Intent就肩负起传参数的重任。
本人在工作和业余学习的过程中总结出几种传参方式

## Intent 传参

基本数据类型就不说了，如果要传递一个实体类，或者实体类的集合：官方提供了两种方法

### Parcelable接口

```java
public class BeanImg implements Parcelable{
   
    String url;
    String thumb;


    public BeanImg(){

    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.url);
        parcel.writeString(this.thumb);
    }

    public static final Parcelable.Creator<BeanImg> CREATOR = new Parcelable.Creator<BeanImg>()
    {
        public BeanImg createFromParcel(Parcel in)
        {
            return new BeanImg(in);
        }

        public BeanImg[] newArray(int size)
        {
            return new BeanImg[size];
        }
    };

    public BeanImg(Parcel parcel){
        this.url=parcel.readString();
        this.thumb=parcel.readString();
    }
}

```
```java
Bundle.putParcelable(Key, Object);  
```

### Serializable

```java
public class Person implements Serializable{
    
    private static final long serialVersionUID = 1L;
    // 成员变量
    private int id;
    private String name;
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
```

```java
 bundle.putSerializable(user, user);//序列化
```

## Gson 转化 
想想后台怎么给我们前端传递数据的
我们可以把数据转化为json串，然后通过Intent传递过去，然后在解析json

## Activity队列
通常情况下，我们会维护一个静态的Activity队列，我们会把打开的Activity添加进去，把销毁的Activity移除，
那么我们可以通过队列，来获取Activity的引用，进而获取它维护的数据。

```java
public class CusActivityManager{
    public static CusActivityManager INSTANCE;
    public static List<AppCompatActivity> activitys;
    
    public static CusActivityManager getInstance(){
        if(INSTANCE==null){
            INSTANCE=new CusActivityManager;
        }
        return INSTANCE;
    }
    
    public AppCompatActivity getActivity(int position){
        activitys.get(position);
    }
}


CusActivityManager.getInstance().getActivity(position).getDatas();
```
