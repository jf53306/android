### 列举一些android开发过程中自己一直遵守的规范

### 开发规范的必要性
    * 减少后期维护成本
    * 增强代码可读性
    * 便于交接

### 命名规范

### 基本原则
    * 前缀或后缀
    * 英文单词或缩写描述，望文知意
    
### 常量
    * 大写字母
    * 中间以'_'连接
```java
public static final int TYPE_IMG=0;
public static final int TYPE_VIDEO=1;
```

### 类
    * 驼峰命名规则
    * 首字母大写
    * 单词首字母大写
    * 对应变量使用一般在类名前+'m'
    * 变量的首字母是小写
例如android中最常见的上下文
```java
class HomeListAdapter{
  private Context mContext;
}
```
### 常用类
    * 从后台映射的数据模型: xxBean,xxEntity;
    * Activity:xxActivity
    * Fragment:xxFragmnet
    * 工具类：xxUtil
    * 配置文件 xxConfig
    * 管理类 xxManager
    
### xml
    * 布局文件名，前缀一般有几种：
        * activity_xxx(对应Activity)
        * fragment_xxx（对应Fragment)
        * item_xxx（对应列表子布局）
    * 控件id:习惯以view_xxx作为前缀
    * 自定义属性的命名：前缀+归属+功能描述
    
    举个栗子：activity_mian.xml、android：id="@+id/view_open_door"、app:j_cusview_aspect_ratio="0.5"

### 接口
    * 前缀习惯用'I'
例如mvp模式下的主页面
```java
public interface IHomeView{
  //主页面ui逻辑
}
//pre为Presenter缩写
public interface IHomePre{
  //主页面交互逻辑
}
```
### 包的命名
包是用来给项目归类的，android中有个applicationId的概念，这个对应的就是我们的包名，在应用商店上架的时候是用来区分不用的应用程序的。

    * 小写
    * 主包名：com.公司名英文或缩写.项目名英文或者缩写
    * 分包：按照功能块来命名
       * home 对应主页
       * user 对应用户信息
    *功能块包下面的分包，可以按照开发模式来命名，如modle、presenter、ui或者view之类
### 方法的命名
    * 大小写
    * 首字母小写
    * 方法的命名比较多变，要求能准确的表达方法的功能性
    
    
## 主题
创建一个应用程序最初要做的几件之一，就是确定一个主题。google给我们提供了足够多的主题，我们可以在它的基础上，定义自己的主题
如何定义主题，参见android主题定义.md
主题中可以定义自己的ui资源，这些资源通常都是共用的，，所以命名也应该尤其严格一点。

    * horizental：hrzt、vertiacl:vtc、background:bg、foreground:fg
    * 内边距 padding
      * pd_xx_window_hrzt
      * pd_xx_window_vtc
      * pd_xx_item_hrzt;
      * pd_xx_item_vtc;
    * 外边据 margin
      * mg_xx_item_hrzt
      * mg_xx_item_vtc
    * 尺寸 size
      * sz_xx_item_height
      * sz_xx_item_width
      * sz_xx_navi_height;
      * sz_xx_sub_navi_height
      * sz_xx_tab_width
    * 字体大小 textsize
      * tsz_xx_title
      * tsz_xx_sub_title
      * tsz_xx_uname
      * tsz_xx_detail
      * tsz_xx_tag
      * tsz_xx_time
    * 颜色 color
      * cl_xx_item_bg
      * cl_xx_window_bg
      * cl_xx_item_strock
      * cl_xx_img_fg
      * cl_xx_navi_bg
    * 字体颜色 textcolor
      * tcl_xx_title
      * 等等

    note:上命名的都是按照功能来的，如果ui可以按照视觉优先级来解析的话,也许会更好

    



        
