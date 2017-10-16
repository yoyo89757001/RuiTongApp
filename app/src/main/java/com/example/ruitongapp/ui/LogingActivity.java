package com.example.ruitongapp.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.ruitongapp.MyApplication;
import com.example.ruitongapp.R;
import com.example.ruitongapp.beans.BaoCunBean;
import com.example.ruitongapp.beans.BaoCunBeanDao;
import com.example.ruitongapp.beans.DengLuFanHuiBean;
import com.example.ruitongapp.beans.FuWuQiBean;
import com.example.ruitongapp.beans.FuWuQiBeanDao;
import com.example.ruitongapp.dialogs.TiJIaoDialog;
import com.example.ruitongapp.utils.GsonUtil;
import com.example.ruitongapp.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.sdsmdg.tastytoast.TastyToast;
import java.io.IOException;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

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
    private TiJIaoDialog jiaZaiDialog=null;
    private Call call=null;

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

    @Override
    protected void onResume() {
        super.onResume();

        fuWuQiBeanDao= MyApplication.myAppLaction.getDaoSession().getFuWuQiBeanDao();
        if (fuWuQiBeanDao!=null){
            fuWuQiBeanList=fuWuQiBeanDao.loadAll();
            for (int i=0;i<fuWuQiBeanList.size();i++){
                if (fuWuQiBeanList.get(i).getIsTrue()){
                    fuWuQiBean=fuWuQiBeanList.get(i);
                    Log.d("LogingActivity", fuWuQiBean.getDizhi());
                }
            }
        }
        baoCunBeanDao=MyApplication.myAppLaction.getDaoSession().getBaoCunBeanDao();
        if (fuWuQiBeanDao!=null){
            baoCunBean=baoCunBeanDao.load(123456L);
            if (baoCunBean!=null){
                startActivity(new Intent(LogingActivity.this,HomePageActivity.class));
                finish();
            }
        }
    }

    @OnClick({R.id.fr, R.id.denglu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fr:
            startActivity(new Intent(LogingActivity.this,GengGaiFuWuQiActivity.class));
                break;
            case R.id.denglu:
                if (!youxiang.getText().toString().trim().equals("") && !mima.getText().toString().trim().equals("")){
                    try {
                        link_denglu();
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }else {
                    Toast tastyToast= TastyToast.makeText(LogingActivity.this,"请填写账号和密码!",TastyToast.LENGTH_LONG,TastyToast.ERROR);
                    tastyToast.setGravity(Gravity.CENTER,0,0);
                    tastyToast.show();
                }
                break;
        }
    }

    private void link_denglu() {

        jiaZaiDialog=new TiJIaoDialog(this);
        if (!LogingActivity.this.isFinishing()){
            jiaZaiDialog.show();
        }

        RequestBody body = new FormBody.Builder()
                .add("account",youxiang.getText().toString().trim())
                .add("pwd",Utils.jiami(mima.getText().toString().trim()))
                .build();

        Request.Builder requestBuilder = new Request.Builder()
                // .header("Content-Type", "application/json")
                .post(body)
                .url(fuWuQiBean.getDizhi() + "/appLogin.do");


        // step 3：创建 Call 对象
         call = MyApplication.getOkHttpClient().newCall(requestBuilder.build());

        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (jiaZaiDialog!=null && jiaZaiDialog.isShowing()){
                            jiaZaiDialog.dismiss();
                        }
                        Toast tastyToast= TastyToast.makeText(LogingActivity.this,"网络错误!",TastyToast.LENGTH_LONG,TastyToast.ERROR);
                        tastyToast.setGravity(Gravity.CENTER,0,0);
                        tastyToast.show();
                    }
                });
                Log.d("AllConnects", "请求识别失败"+e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (jiaZaiDialog!=null && jiaZaiDialog.isShowing()){
                            jiaZaiDialog.dismiss();
                        }
                    }
                });
                Log.d("AllConnects", "请求识别成功"+call.request().toString());
                //获得返回体
                try {

                    ResponseBody body = response.body();
                    // Log.d("AllConnects", "识别结果返回"+response.body().string());
                    String ss=body.string();
                    Log.d("InFoActivity", ss);
                    JsonObject jsonObject= GsonUtil.parse(ss).getAsJsonObject();
                    Gson gson=new Gson();
                    final DengLuFanHuiBean zhaoPianBean=gson.fromJson(jsonObject,DengLuFanHuiBean.class);
                    if (zhaoPianBean.getDtoResult()==0){
                        BaoCunBean baoCunBean2=new BaoCunBean();
                        baoCunBean2.setId(123456L);
                        baoCunBean2.setDizhi(fuWuQiBean.getDizhi());
                        baoCunBean2.setZhanghao(youxiang.getText().toString().trim());
                        baoCunBean2.setMima(mima.getText().toString().trim());
                        baoCunBean2.setSid(zhaoPianBean.getSid()+"");
                        baoCunBean2.setToken(zhaoPianBean.getToken());
                        if (baoCunBean == null) {
                            baoCunBeanDao.insert(baoCunBean2);
                            Log.d("LogingActivity", "插入");
                        }else {
                            baoCunBeanDao.update(baoCunBean2);
                            Log.d("LogingActivity", "更新");
                        }
                        startActivity(new Intent(LogingActivity.this,HomePageActivity.class));
                        finish();
                        Log.d("LogingActivity", "关闭");
                    }else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                Toast tastyToast= TastyToast.makeText(LogingActivity.this,zhaoPianBean.getDtoDesc(),TastyToast.LENGTH_LONG,TastyToast.ERROR);
                                tastyToast.setGravity(Gravity.CENTER,0,0);
                                tastyToast.show();
                            }
                        });
                    }


                }catch (Exception e){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (jiaZaiDialog!=null && jiaZaiDialog.isShowing()){
                                jiaZaiDialog.dismiss();
                            }
                            Toast tastyToast= TastyToast.makeText(LogingActivity.this,"网络错误!",TastyToast.LENGTH_LONG,TastyToast.ERROR);
                            tastyToast.setGravity(Gravity.CENTER,0,0);
                            tastyToast.show();
                        }
                    });


                    Log.d("WebsocketPushMsg", e.getMessage());
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (call!=null)
            call.cancel();

        super.onDestroy();


    }
}
