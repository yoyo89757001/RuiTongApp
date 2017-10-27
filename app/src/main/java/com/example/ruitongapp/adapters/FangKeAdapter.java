package com.example.ruitongapp.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ruitongapp.R;
import com.example.ruitongapp.fangkebean.FangKeBean;
import com.example.ruitongapp.interfaces.ClickIntface;
import com.example.ruitongapp.utils.DateUtils;
import com.example.ruitongapp.utils.GlideCircleTransform;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Administrator on 2017/10/3.
 */


public class FangKeAdapter extends RecyclerView.Adapter<FangKeAdapter.ViewHolder> {
    private List<FangKeBean.ObjectsBean> datas;
    private ClickIntface clickIntface;
    //是否第一个Item的
    private boolean isFirstItemLetter = true;
    //记录是否显示字母标题，键为字母，值为下标
    private Map<String, Integer> map;
    private Context context;
    private String zhuji;


    public void setClickIntface(ClickIntface clickIntface){
        this.clickIntface=clickIntface;
    }

    public FangKeAdapter(List<FangKeBean.ObjectsBean> datas,Context context,String zhuji) {
        this.datas = datas;
        map = new HashMap<>();
        this.context=context;
        this.zhuji=zhuji;
    }

    //更新右边选择器
  public  void setLetters(){

        if (datas.size()>0){
            traverseList();
        }

    }
    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fangke_item,viewGroup,false);
        return new ViewHolder(view);
    }
    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        //判断是否显示字母标题
        if (map.get(DateUtils.timet(datas.get(position).getVisitDate()+"")) != null && map.get(DateUtils.timet(datas.get(position).getVisitDate()+""))==position) {
            viewHolder.txtFirstLetter.setVisibility(View.VISIBLE);
            viewHolder.txtFirstLetter.setText(DateUtils.timet(datas.get(position).getVisitDate()+""));

        } else {

            viewHolder.txtFirstLetter.setVisibility(View.GONE);
        }
        viewHolder.txtName.setText(datas.get(position).getName());
        if (datas.get(position).getAvatar()!=null && !datas.get(position).getAvatar().equals("")){
            Glide.with(context)
                    .load(zhuji+"/upload/avatar/"+datas.get(position).getAvatar())
                    .transform(new GlideCircleTransform(context, 0.6f, Color.parseColor("#ffffffff")))
                    .into(viewHolder.imageView);
        }else {
            Glide.with(context)
                    .load(R.drawable.morentouxiang)
                    .transform(new GlideCircleTransform(context, 0.6f, Color.parseColor("#ffffffff")))
                    .into(viewHolder.imageView);
        }
       switch (Integer.parseInt(datas.get(position).getAudit())){
           case 0:
               //未审核
               viewHolder.zhuantai.setText("待审核");
               viewHolder.zhuantai.setTextColor(Color.parseColor("#0166FF"));
               break;
           case 1:
               //通过
               viewHolder.zhuantai.setText("通过");
               viewHolder.zhuantai.setTextColor(Color.parseColor("#05e6a6"));
               break;
           case 2:
            //拒绝
               viewHolder.zhuantai.setText("拒绝");
               viewHolder.zhuantai.setTextColor(Color.parseColor("#FF4081"));
               break;
       }

    }
    //获取数据的数量
    @Override
    public int getItemCount() {
        return datas.size();
    }
    //自定义的ViewHolder，持有每个Item的的所有界面元素
      class ViewHolder extends RecyclerView.ViewHolder {
      private   ImageView imageView;
      private   TextView txtFirstLetter,txtName,zhuantai;


        private ViewHolder(View view){
            super(view);
            txtFirstLetter = (TextView) view.findViewById(R.id.txt_letter_category);
            txtName = (TextView) view.findViewById(R.id.txt_name);
            zhuantai = (TextView) view.findViewById(R.id.zhuangtai);
            imageView= (ImageView) view.findViewById(R.id.image);


        }
    }

    /**
     * 遍历列表
     * 由于传进来的mDatas是一个已排好序的列表，遍历整个列表，每遇到分类的第一个字母就把下标记录下来
     */
    private void traverseList() {
        //获取初始值
        String current = DateUtils.timet(datas.get(0).getVisitDate()+"");
        for (int i = 0; i < datas.size(); i++) {

            String tempFirstLetter = DateUtils.timet(datas.get(i).getVisitDate()+"");

            if (tempFirstLetter.equals(current)) {
                if (isFirstItemLetter) {
                    map.put(current, i);
                }
            } else {
                //更新初始值
                current = DateUtils.timet(datas.get(i).getVisitDate()+"");
                map.put(current, i);
            }
            isFirstItemLetter = false;
        }
    }

    /**
     * 获取当前字母的下标
     * @return
     */
    public int getCurrentLetterPosition(String currentLetter) {
        if (map.get(currentLetter) != null) {
            return map.get(currentLetter);
        } else
            return -1;
    }



}
