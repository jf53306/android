# ToolBar Material design
toolbar 是5.0后，适配Material风格的控件，除了包含actionbar的功能外，还可以和各种高级控件组合，实现Material设计风格的效果。在此进行了toolbar的
简单封装。

## 主题
```java
<style name="Tb.AppTheme" parent="Theme.AppCompat.NoActionBar">
```
    AppCompat中的主题是像下兼容的，点进去你会发现，v21以上版本的会使用Material的主题
    
```java
    <item name="android:windowBackground">@color/windowBackground</item>
    <item name="android:navigationBarColor">@color/navigationBarColor</item>  //v21
    <item name="android:statusBarColor">@color/colorPrimaryDark</item>        //v21
```
    5.0以上的android是可以控制状态栏和导航栏的颜色的，开发之前，我们可能要确认状态栏，导航栏的色调，并且去除默认的标题栏
    
## 标题栏

    标题栏我打算用toolbar来实现，不可能每次用到toolbar的时候，都在布局中手写，所以我们可以针对一般情况，对toolbar进行简单的封装。
    
### toolbar 主题

```java
<style name="Tb.AppTheme.ToolBarTheme" parent="Tb.AppTheme">
        <!--toolbar title color-->
        <item name="android:textColorPrimary">@color/textColorPrimary</item>
        <item name="android:background">@color/colorPrimary</item>
        <item name="colorControlNormal">@color/colorControlNormal</item>
        <item name="actionOverflowMenuStyle">@style/Tb.AppTheme.ToolBarTheme.Overflow</item>
        <!--<item name="navigationIcon">@mipmap/ic_launcher</item>-->
        <item name="android:dropDownListViewStyle">@style/Tb.AppTheme.ToolBarTheme.DropDownDivider</item>
</style>
```
    textColorPrimary：标题颜色
    backgound:toolbar背景色
    colorControlNormal：系统图标颜色
    navigationIcon：返回按钮图标
    dropDownListViewStyle：溢出菜单分割线样式
    actionOverflowMenuStyle：溢出菜单的样式
    
### overflow样式

```java
<style name="Tb.AppTheme.ToolBarTheme.Overflow" parent="Widget.AppCompat.Light.PopupMenu.Overflow">
        <item name="overlapAnchor">false</item>
        <item name="android:dropDownWidth">wrap_content</item>
        <item name="android:dropDownVerticalOffset">4dp</item>
        <item name="android:dropDownHorizontalOffset">-4dp</item>
        <item name="android:popupBackground">?attr/colorPrimary</item>
        <item name="android:elevation">1dp</item>
</style>
```
    overlapAnchor:溢出菜单是否覆盖标题栏
    dropDownVerticalOffset、dropDownHorizontalOffset：位置偏移量
    popupBackground：溢出菜单背景色
    elevation：溢出菜单高度 v21
    
    
### 封装
    
```java
public interface IToolbarHelper {
    //创建toolbar
    View buildToolBar(AppCompatActivity activity,int layoutId);
    //是否显示回退
    void backEnable(boolean backEnable);
    //设置标题
    void setTitle(String title);
    //回收变量
    void release();
    //获取toolbar
    Toolbar getToolBar();
}
```
使用

```java
setContentView(toolbarHelper.buildToolBar(this,R.layout.activity_main));
toolbarHelper.setTitle("wtf");
```
封装的思想就是将activity对应的布局，导入到toolbar的布局中，然后替换activity的根布局具
    
