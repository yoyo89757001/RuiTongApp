package com.example.ruitongapp.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ruitongapp.MyApplication;
import com.example.ruitongapp.R;
import com.example.ruitongapp.beans.BaoCunBean;
import com.example.ruitongapp.beans.BaoCunBeanDao;
import com.example.ruitongapp.beans.FuWuQiBean;
import com.example.ruitongapp.beans.FuWuQiBeanDao;
import com.example.ruitongapp.utils.Utils;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LogingActivity extends Activity {
    @BindView(R.id.fr)
    TextView fr;
    @BindView(R.id.youxiang)
    EditText youxiang;
    @BindView(R.id.mima)
    EditText mima;
    @BindView(R.id.denglu)
    Button denglu;
    private ImageView logo;
    private int dw, dh;
    private BaoCunBeanDao baoCunBeanDao=null;
    private BaoCunBean baoCunBean=null;
    private FuWuQiBean fuWuQiBean=null;
    private List<FuWuQiBean> fuWuQiBeanList=null;
    private FuWuQiBeanDao fuWuQiBeanDao=null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loging);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.titlecolor);
        }
        fuWuQiBeanDao= MyApplication.myAppLaction.getDaoSession().getFuWuQiBeanDao();
        if (fuWuQiBeanDao!=null){
            fuWuQiBeanList=fuWuQiBeanDao.loadAll();
            for (int i=0;i<fuWuQiBeanList.size();i++){
                if (fuWuQiBeanList.get(i).getIsTrue()){
                    fuWuQiBean=fuWuQiBeanList.get(i);
                }
            }
        }
        baoCunBeanDao=MyApplication.myAppLaction.getDaoSession().getBaoCunBeanDao();
        if (fuWuQiBeanDao!=null){
            baoCunBean=baoCunBeanDao.load(123456L);
        }

        ButterKnife.bind(this);
        dw = Utils.getDisplaySize(this).x;
        dh = Utils.getDisplaySize(this).y;
        logo = (ImageView) findViewById(R.id.imim);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) logo.getLayoutParams();
        params.width = dw / 3;
        params.height = dw / 6;
        logo.setLayoutParams(params);
        logo.invalidate();

    }

    @OnClick({R.id.fr, R.id.denglu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fr:
            startActivity(new Intent(LogingActivity.this,GengGaiFuWuQiActivity.class));
                break;
            case R.id.denglu:
                startActivity(new Intent(LogingActivity.this,HomePageActivity.class));
                break;
        }
    }
}
