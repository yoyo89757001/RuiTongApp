package com.example.ruitongapp.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.ruitongapp.R;
import com.example.ruitongapp.gonggaobean.GongGaoBean;
import com.example.ruitongapp.interfaces.ClickIntface;
import com.example.ruitongapp.utils.DateUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/10/3.
 */


public class GongGaoAdapter extends RecyclerView.Adapter<GongGaoAdapter.ViewHolder> {
    private List<GongGaoBean.ObjectsBean> datas;
    private ClickIntface clickIntface;
    private Context context;
    private String zhuji=null;

    public void setClickIntface(ClickIntface clickIntface){
        this.clickIntface=clickIntface;
    }

    public GongGaoAdapter(List<GongGaoBean.ObjectsBean> datas, Context context, String zhuji) {
        this.datas = datas;
        this.context=context;
        this.zhuji=zhuji;
    }
    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.gonggao_item,viewGroup,false);
        return new ViewHolder(view);
    }
    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        viewHolder.t1.setText(datas.get(position).getTitle());
        viewHolder.t2.setText(datas.get(position).getContent());
        viewHolder.time.setText(DateUtils.time(datas.get(position).getIssueTime()+""));
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clickIntface.BackId(position);
            }
        });
        viewHolder.cardView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        Log.d("GongGaoAdapter", "按下");
                        viewHolder.cardView.setBackgroundResource(R.drawable.danbai);
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.d("GongGaoAdapter", "抬起");
                        viewHolder.cardView.setBackgroundResource(R.drawable.bai);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.d("GongGaoAdapter", "移动");
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        viewHolder.cardView.setBackgroundResource(R.drawable.bai);
                        break;
                }

                return false;
            }
        });
        if (datas.get(position).isShanchu()){
            viewHolder.shanchu_im.setVisibility(View.VISIBLE);
        }else {
            viewHolder.shanchu_im.setVisibility(View.GONE);
        }
        viewHolder.shanchu_im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (datas.get(position).isShanChu2()){
                    viewHolder.shanchu_im.setImageResource(R.drawable.ic_select);
                    datas.get(position).setShanChu2(false);
                }else {
                    viewHolder.shanchu_im.setImageResource(R.drawable.ic_selected);
                    datas.get(position).setShanChu2(true);
                }

            }
        });
        if (datas.get(position).isShanChu2()){
            viewHolder.shanchu_im.setImageResource(R.drawable.ic_selected);
        }else {
            viewHolder.shanchu_im.setImageResource(R.drawable.ic_select);
        }


//            if (datas.get(position).getAvatar()!=null && !datas.get(position).getAvatar().equals("")) {
//                Glide.with(context)
//                        .load(zhuji + "/upload/avatar/" + datas.get(position).getAvatar())
//                        .transform(new GlideCircleTransform(context, 0.6f, Color.parseColor("#ffffffff")))
//                        .into(viewHolder.touxiang);
//            } else {
//                Glide.with(context)
//                        .load(R.drawable.tuijianyisheng)
//                        .transform(new GlideCircleTransform(context, 0.6f, Color.parseColor("#ffffffff")))
//                        .into(viewHolder.touxiang);
//            }


    }
    //获取数据的数量
    @Override
    public int getItemCount() {
        return datas.size();
    }
    //自定义的ViewHolder，持有每个Item的的所有界面元素
      class ViewHolder extends RecyclerView.ViewHolder {
        private TextView t1,t2,time;
        private CardView cardView;
        private ImageView shanchu_im;


        private ViewHolder(View view){
            super(view);
            t1 = (TextView) view.findViewById(R.id.title);
            t2 = (TextView) view.findViewById(R.id.couent);
            time = (TextView) view.findViewById(R.id.time);
            cardView= (CardView) view.findViewById(R.id.cfd);
            shanchu_im= (ImageView) view.findViewById(R.id.shanchu_im);


        }
    }
}
