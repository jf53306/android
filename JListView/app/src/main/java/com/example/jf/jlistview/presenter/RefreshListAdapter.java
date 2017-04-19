package com.example.jf.jlistview.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.jf.jlistview.R;
import com.example.jf.jlistview.utils.DensityUtil;
import com.example.jf.jlistview.utils.FrescoUtils;

import java.util.List;

/**
 * Created by jf on 17-4-19.
 */

public class RefreshListAdapter extends RecyclerView.Adapter{

    List<String> datas;
    int[] ress=new int[]{R.mipmap.ic_0,R.mipmap.ic_1,R.mipmap.ic_2,R.mipmap.ic_3,R.mipmap.ic_4,R.mipmap.ic_5,R.mipmap.ic_6};
    Context mContext;
    public RefreshListAdapter(Context context, List<String> datas){
        this.mContext=context;
        this.datas=datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(mContext).inflate(R.layout.item_refresh_list,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Holder hhh= (Holder) holder;

        int resPosition=position%7;
        displayImg(hhh.img,ress[resPosition],
                (int) DensityUtil.DIM_SCREEN_WIDTH,
                DensityUtil.dip2px(200));
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class Holder extends RecyclerView.ViewHolder{
        ImageView img;
        public Holder(View itemView) {
            super(itemView);

            img= (ImageView) itemView.findViewById(R.id.view_img);
        }
    }

    public void displayImg(@NonNull ImageView img, @NonNull int absPath, int width, int height) {
        try {
            Glide.with(img.getContext()).load(absPath).crossFade().centerCrop().override(width, height).into(img);
        } catch(IllegalArgumentException ignore) {
        }
    }

}
