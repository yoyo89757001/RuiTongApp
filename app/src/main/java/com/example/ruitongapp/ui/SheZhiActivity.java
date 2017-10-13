package com.example.ruitongapp.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ruitongapp.R;
import com.example.ruitongapp.dialogs.QueRenDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SheZhiActivity extends Activity {

    @BindView(R.id.leftim)
    ImageView leftim;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.r1)
    RelativeLayout r1;
    @BindView(R.id.r2)
    RelativeLayout r2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_she_zhi);
        ButterKnife.bind(this);


    }

    @OnClick({R.id.leftim, R.id.r1, R.id.r2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.leftim:
                finish();
                break;
            case R.id.r1:

                final QueRenDialog dialog=new QueRenDialog(SheZhiActivity.this);
                dialog.setVisibility_BT();
                dialog.setCountText("你确定要切换账号?");
                dialog.setOnPositiveListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        sendBroadcast(new Intent("guanbiyemian"));
                        finish();
                        dialog.dismiss();
                        startActivity(new Intent(SheZhiActivity.this,LogingActivity.class));
                    }
                });
                dialog.setOnQuXiaoListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();

                break;
            case R.id.r2:
                final QueRenDialog dialog2=new QueRenDialog(SheZhiActivity.this);
                dialog2.setVisibility_BT();
                dialog2.setCountText("你确定要退出?");
                dialog2.setOnPositiveListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sendBroadcast(new Intent("guanbiyemian"));
                        finish();
                        dialog2.dismiss();
                        startActivity(new Intent(SheZhiActivity.this,LogingActivity.class));
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
