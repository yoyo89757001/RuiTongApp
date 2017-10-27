package com.example.ruitongapp.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.ruitongapp.R;
import com.example.ruitongapp.beans.YuanGongBean;
import com.example.ruitongapp.interfaces.ClickIntface;
import com.example.ruitongapp.utils.GlideCircleTransform;

import java.util.List;

/**
 * Created by Administrator on 2017/10/3.
 */


public class BaoGaoAdapter extends RecyclerView.Adapter<BaoGaoAdapter.ViewHolder> {
    private List<YuanGongBean.ObjectsBean> datas;
    private ClickIntface clickIntface;
    private Context context;
    private String zhuji=null;

    public void setClickIntface(ClickIntface clickIntface){
        this.clickIntface=clickIntface;
    }

    public BaoGaoAdapter(List<YuanGongBean.ObjectsBean> datas, Context context, String zhuji) {
        this.datas = datas;
        this.context=context;
        this.zhuji=zhuji;
    }
    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.baogao2_item,viewGroup,false);
        return new ViewHolder(view);
    }
    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.t1.setText(datas.get(position).getName());
        viewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickIntface.BackId(position);
            }
        });

            if (datas.get(position).getAvatar()!=null && !datas.get(position).getAvatar().equals("")) {
                Glide.with(context)
                        .load(zhuji + "/upload/avatar/" + datas.get(position).getAvatar())
                        .transform(new GlideCircleTransform(context, 0.6f, Color.parseColor("#ffffffff")))
                        .into(viewHolder.touxiang);
            } else {
                Glide.with(context)
                        .load(R.drawable.morentouxiang)
                        .transform(new GlideCircleTransform(context, 0.6f, Color.parseColor("#ffffffff")))
                        .into(viewHolder.touxiang);
            }


    }
    //获取数据的数量
    @Override
    public int getItemCount() {
        return datas.size();
    }
    //自定义的ViewHolder，持有每个Item的的所有界面元素
      class ViewHolder extends RecyclerView.ViewHolder {
        private TextView t1 ;
        private ImageView touxiang;
        private LinearLayout relativeLayout;


        private ViewHolder(View view){
            super(view);
            t1 = (TextView) view.findViewById(R.id.t1);
            touxiang= (ImageView) view.findViewById(R.id.touxiang);
            relativeLayout= (LinearLayout) view.findViewById(R.id.fffr);


        }
    }
}
