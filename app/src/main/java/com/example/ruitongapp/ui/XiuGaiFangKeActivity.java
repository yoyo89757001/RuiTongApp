package com.example.ruitongapp.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ruitongapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class XiuGaiFangKeActivity extends Activity {


    @BindView(R.id.ruzhi)
    RelativeLayout ruzhi;
    @BindView(R.id.vip)
    RelativeLayout vip;
    @BindView(R.id.r6)
    RelativeLayout r6;
    @BindView(R.id.leftim)
    ImageView leftim;
    @BindView(R.id.righttv)
    TextView righttv;
    @BindView(R.id.ee63)
    ImageView vip_im;
    @BindView(R.id.title)
    TextView title;

    private boolean isVip = false;
    private int type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xiu_gai_fang_ke);
        ButterKnife.bind(this);

        type=getIntent().getIntExtra("type",0);
        if (type==1){
            title.setText("添加访客");
        }else {
            title.setText("访客信息");
        }
        righttv.setVisibility(View.VISIBLE);
        righttv.setText("保存");


    }


    @OnClick({R.id.ruzhi, R.id.vip, R.id.r6, R.id.leftim, R.id.righttv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ruzhi:

                break;
            case R.id.vip:
                if (isVip) {
                    vip_im.setImageResource(R.drawable.kai);
                    isVip = false;
                } else {
                    isVip = true;
                    vip_im.setImageResource(R.drawable.guan);
                }

                break;
            case R.id.r6:

                break;
            case R.id.leftim:
                finish();
                break;
            case R.id.righttv:

                break;
        }
    }
}
