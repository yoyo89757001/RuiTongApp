package com.example.ruitongapp.fargments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.ruitongapp.MyApplication;
import com.example.ruitongapp.R;
import com.example.ruitongapp.beans.BaoCunBean;
import com.example.ruitongapp.beans.BaoCunBeanDao;
import com.example.ruitongapp.dialogs.QueRenDialog;
import com.example.ruitongapp.ui.LogingActivity;
import com.example.ruitongapp.ui.XiuGaiMiMaActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment5 extends Fragment {

    @BindView(R.id.r2)
    RelativeLayout r2;
    @BindView(R.id.r3)
    RelativeLayout r3;
    Unbinder unbinder;



    private BaoCunBeanDao baoCunBeanDao=null;
    private BaoCunBean baoCunBean=null;

    public Fragment5() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment5, container, false);
        TextView textView= (TextView) view.findViewById(R.id.zhanghao);
        baoCunBeanDao= MyApplication.myAppLaction.getDaoSession().getBaoCunBeanDao();
        if (baoCunBeanDao!=null){
            baoCunBean=baoCunBeanDao.load(123456L);
            if (baoCunBean!=null)
            textView.setText(baoCunBean.getZhanghao());
        }

        unbinder = ButterKnife.bind(this, view);


        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.r2, R.id.r3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.r2:
                startActivity(new Intent(getContext(), XiuGaiMiMaActivity.class));
                break;
            case R.id.r3:
                final QueRenDialog dialog2=new QueRenDialog(getActivity());
                dialog2.setVisibility_BT();
                dialog2.setCountText("你确定要退出?");
                dialog2.setOnPositiveListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        baoCunBeanDao.deleteByKey(123456L);
                        getActivity().sendBroadcast(new Intent("guanbiyemian"));
                        dialog2.dismiss();
                        startActivity(new Intent(getActivity(),LogingActivity.class));
                    }
                });
                dialog2.setOnQuXiaoListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog2.dismiss();
                    }
                });
                dialog2.show();
                break;
        }
    }
}
