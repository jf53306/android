package com.example.jf.jlistview.utils;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;


import java.io.File;

/**
 * Created by lilian on 16-11-29.
 */

public class FrescoUtils {

//    public static void setImage(SimpleDraweeView imageView, String path, boolean isScrolling, int defalut){
//        Uri uri = (path != null) ? Uri.parse(path) : null;
//        final boolean isInMe= Fresco.getImagePipeline().isInBitmapMemoryCache(uri);
//        if (isInMe){
//            imageView.setImageURI(uri);
//        }else {
//            if (isScrolling){
//                imageView.setImageResource(defalut);
//            }else {
//                imageView.setImageURI(uri);
//            }
//        }
//    }

    public static String getFile(File file){
        return "file://"+file.getAbsolutePath();
    }
    public static String getFile(String path){
        return "file://"+path;
    }

    public static String getRes(Context context, int resId){
        return "res://"+context.getPackageName()+"/"+resId;
    }

//    public static String getLocalImg(Context context, String source){
//        if (TextUtils.isEmpty(source)){
//            return "";
//        }
//        if (!source.contains("/")){
//            return getRes(context, Integer.parseInt(source.toString()));
//        }else {
//            String thumburl=LocalImgLoader.getInstance(context).getDiskCacheUrl(source.toString());
//            return getFile(thumburl);
//        }
//    }

    public static String getSource(Context context, String source){
        if (TextUtils.isEmpty(source)){
            return "";
        }
        if (!source.contains("/")){
            return getRes(context, Integer.parseInt(source.toString()));
        }else {
            return getFile(source);
        }
    }

//    public static String getFromUrl(String from, String url){
//        if (TextUtils.isEmpty(from)){
//            return url;
//        }
//        switch (from){
//            case BeanImg.FROM_FILE:
//
//                return getFile(url);
//            case BeanImg.FROM_RES:
//
//                return getRes(App.getInstance().getApplicationContext(), Integer.parseInt(url));
//            case BeanImg.FROM_NET:
//
//                return url;
//        }
//
//        return url;
//    }
}
