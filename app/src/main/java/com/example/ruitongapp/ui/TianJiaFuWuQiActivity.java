package com.example.ruitongapp.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ruitongapp.MyApplication;
import com.example.ruitongapp.R;
import com.example.ruitongapp.beans.FuWuQiBean;
import com.example.ruitongapp.beans.FuWuQiBeanDao;
import com.example.ruitongapp.view.MyEditText;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TianJiaFuWuQiActivity extends Activity {

    @BindView(R.id.leftim)
    ImageView leftim;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.dizhi)
    MyEditText dizhi;
    @BindView(R.id.baocun)
    Button baocun;
    private FuWuQiBeanDao fuWuQiBeanDao = null;
    private List<FuWuQiBean> fuWuQiBeanList = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tian_jia_fu_wu_qi);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.titlecolor);
        }
        title.setText("添加服务器");
        fuWuQiBeanDao = MyApplication.myAppLaction.getDaoSession().getFuWuQiBeanDao();
        if (fuWuQiBeanDao!=null){
            fuWuQiBeanList=fuWuQiBeanDao.loadAll();
        }

    }


    @OnClick({R.id.leftim, R.id.baocun})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.leftim:
                finish();
                break;
            case R.id.baocun:
                if (fuWuQiBeanList!=null && !dizhi.getText().toString().trim().equals("")){
                    for (int i=0;i<fuWuQiBeanList.size();i++){
                        fuWuQiBeanList.get(i).setIsTrue(false);
                        fuWuQiBeanDao.update(fuWuQiBeanList.get(i));
                    }
                    FuWuQiBean bean=new FuWuQiBean();
                    bean.setIsTrue(true);
                    bean.setId(System.currentTimeMillis());
                    bean.setDizhi(dizhi.getText().toString().trim());
                    fuWuQiBeanDao.insert(bean);
                    sendBroadcast(new Intent("guanbi"));
                    finish();


                }else {
                    TastyToast.makeText(TianJiaFuWuQiActivity.this,"你没有填写地址",TastyToast.LENGTH_LONG,TastyToast.INFO);

                }





                break;
        }
    }
}
