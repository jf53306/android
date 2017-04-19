# 下拉刷新，上拉加载
基于RcyclerView对NestedScrollingChild的默认实现，内置RecyclerView,可高度定制刷新动画

## xml布局
```java
<com.example.jf.jlistview.jnestrefreshview.impl.RefreshingView
        android:id="@+id/view_list"
        android:layout_width="match_parent"
        android:layout_height="match_parent"></com.example.jf.jlistview.jnestrefreshview.impl.RefreshingView>
```

## java

```java

viewList.getListView().setAdapter(adapterList=new RefreshListAdapter(this,dataList));

        viewList.setOnRefreshListener(new JNestRefreshView.OnRefreshListener() {
            @Override
            public void onTopRefresh(final JNestRefreshView view) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dataList.clear();
                        loadDatas();
                        adapterList.notifyDataSetChanged();
                      view.finishRefresh();
                    }
                },1000);
            }

            @Override
            public void onBotRefresh(final JNestRefreshView view) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadDatas();
                        adapterList.notifyDataSetChanged();
                        view.finishRefresh();
                    }
                },1000);
            }
        });


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                viewList.autoTopRefresh();
            }
        },300);
```
