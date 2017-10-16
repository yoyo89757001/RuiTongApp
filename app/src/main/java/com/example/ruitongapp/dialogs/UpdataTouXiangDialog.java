package com.example.ruitongapp.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ruitongapp.R;


/**
 * @Function: 自定义对话框
 * @Date: 2013-10-28
 * @Time: 下午12:37:43
 * @author Tom.Cai
 */
public class UpdataTouXiangDialog extends Dialog {
    private LinearLayout l1,l2;
    private TextView paizhao,xiangce;
    public UpdataTouXiangDialog(Context context) {
        super(context, R.style.dialog_style);
        setCustomDialog();
    }

    private void setCustomDialog() {
        View mView = LayoutInflater.from(getContext()).inflate(R.layout.xiugaitouxiang_item, null);
     //   shanchu= (TextView) mView.findViewById(R.id.shanchu);
        paizhao= (TextView) mView.findViewById(R.id.paizhao);
        xiangce= (TextView) mView.findViewById(R.id.xiangce);
        l1= (LinearLayout)mView. findViewById(R.id.jiazai);
        l2= (LinearLayout) mView.findViewById(R.id.www);
        super.setContentView(mView);
    }


    public  void setL1Gone(){
        l1.setVisibility(View.VISIBLE);
        l2.setVisibility(View.GONE);
    }

    @Override
    public void setContentView(int layoutResID) {
    }

    @Override
    public void setContentView(View view, LayoutParams params) {
    }

    @Override
    public void setContentView(View view) {
    }

    /**
     * 确定键监听器
     * @param listener
     */
//    public void setOnDeleterListener(View.OnClickListener listener){
//        shanchu.setOnClickListener(listener);
//    }
    /**
     * 取消键监听器
     * @param listener
     */
    public void setOnUpdataPaiZhaoListener(View.OnClickListener listener){
        paizhao.setOnClickListener(listener);
    }
    public void setOnUpdataXiangceListener(View.OnClickListener listener){
        xiangce.setOnClickListener(listener);
    }

}
