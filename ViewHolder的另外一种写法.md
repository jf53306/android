# 谁用谁知道

写过Android代码的都知道，当我们要创建一个列表适配器的时候，就要创建对应的ViewHolder,那么有没有一种方式，可以让所有的适配器器共用一个ViewHolder类
答案是可以的

```java

public class SuperHolder  extends RecyclerView.ViewHolder{
    SparseArray<View> views=new SparseArray<>();

    public SuperHolder(View itemView) {
        super(itemView);
    }


    public <T extends View> T getChild(int id){
        final View v=views.get(id);
        if (v==null){
            T child= (T) itemView.findViewById(id);
            views.put(id,child);
            return child;
        }
        return (T) v;
    }

    public void setOnclickListener(int position,int id, View.OnClickListener clickListener){
        View child = null;
        if (itemView.getId()==id){
            child=itemView;
        }else {
            child=getChild(id);
        }
        child.setTag(R.id.item_position,position);
        child.setOnClickListener(clickListener);
    }
}
```
    很简单，在ViewHolder中维护了一个list，这个list用来存放初始化过的子控件，通过getChild方法获取对应id的子控件，通过setOnclickListener方法
    设置点击监听
    
