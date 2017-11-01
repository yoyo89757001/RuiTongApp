package com.example.ruitongapp.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
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
import com.sdsmdg.tastytoast.TastyToast;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class QiDongActivity extends Activity {
    private BaoCunBeanDao baoCunBeanDao=null;
    private BaoCunBean baoCunBean=null;
    private List<FuWuQiBean> fuWuQiBeanList=null;
    private FuWuQiBeanDao fuWuQiBeanDao=null;
    private FuWuQiBean fuWuQiBean=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qi_dong);

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

        baoCunBeanDao= MyApplication.myAppLaction.getDaoSession().getBaoCunBeanDao();
        baoCunBean=baoCunBeanDao.load(123456L);
            if (baoCunBean!=null && baoCunBean.getDizhi()!=null && baoCunBean.getZhanghao()!=null && !baoCunBean.getZhanghao().equals("")){
                link_denglu();
            }

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    startActivity(new Intent(QiDongActivity.this,LogingActivity.class));
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }


    private void link_denglu() {

        RequestBody body = new FormBody.Builder()
                .add("account",baoCunBean.getZhanghao())
                .add("pwd", Utils.jiami(baoCunBean.getMima()))
                .build();

        Request.Builder requestBuilder = new Request.Builder()
                // .header("Content-Type", "application/json")
                .post(body)
                .url(fuWuQiBean.getDizhi() + "/appLogin.do");


        // step 3：创建 Call 对象
       Call call = MyApplication.getOkHttpClient().newCall(requestBuilder.build());

        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                Log.d("AllConnects", "请求识别失败"+e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

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
                        baoCunBean2.setZhanghao(baoCunBean.getZhanghao());
                        baoCunBean2.setMima(baoCunBean.getMima());
                        baoCunBean2.setSid(zhaoPianBean.getSid()+"");
                        baoCunBean2.setToken(zhaoPianBean.getToken());
                        if (baoCunBean == null) {
                            baoCunBeanDao.insert(baoCunBean2);
                            Log.d("LogingActivity", "插入");
                        }else {
                            baoCunBeanDao.update(baoCunBean2);
                            Log.d("LogingActivity", "更新");
                        }
                       // startActivity(new Intent(QiDongActivity.this,HomePageActivity.class));
                      //  finish();
                        Log.d("LogingActivity", "关闭");
                    }else {
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//
//                                Toast tastyToast= TastyToast.makeText(LogingActivity.this,zhaoPianBean.getDtoDesc(),TastyToast.LENGTH_LONG,TastyToast.ERROR);
//                                tastyToast.setGravity(Gravity.CENTER,0,0);
//                                tastyToast.show();
//                            }
//                        });
                    }


                }catch (Exception e){
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            if (jiaZaiDialog!=null && jiaZaiDialog.isShowing()){
//                                jiaZaiDialog.dismiss();
//                            }
//                            Toast tastyToast= TastyToast.makeText(LogingActivity.this,"网络错误!",TastyToast.LENGTH_LONG,TastyToast.ERROR);
//                            tastyToast.setGravity(Gravity.CENTER,0,0);
//                            tastyToast.show();
//                        }
//                    });


                    Log.d("WebsocketPushMsg", e.getMessage());
                }
            }
        });
    }

}
