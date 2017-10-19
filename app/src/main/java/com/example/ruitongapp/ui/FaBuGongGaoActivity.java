package com.example.ruitongapp.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ruitongapp.MyApplication;
import com.example.ruitongapp.R;
import com.example.ruitongapp.beans.BaoCunBean;
import com.example.ruitongapp.beans.BaoCunBeanDao;
import com.example.ruitongapp.beans.MoRenFanHuiBean;
import com.example.ruitongapp.beans.ShouFangBean;
import com.example.ruitongapp.dialogs.TiJIaoDialog;
import com.example.ruitongapp.utils.DateUtils;
import com.example.ruitongapp.utils.GsonUtil;
import com.example.ruitongapp.utils.Utils;
import com.example.ruitongapp.view.MyEditText;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.sdsmdg.tastytoast.TastyToast;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class FaBuGongGaoActivity extends Activity {

    @BindView(R.id.leftim)
    ImageView leftim;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.righttv)
    TextView righttv;
    @BindView(R.id.biaoti)
    MyEditText biaoti;
    @BindView(R.id.shijian)
    TextView shijian;
    @BindView(R.id.shijian_ll)
    LinearLayout shijianLl;
    @BindView(R.id.neirong)
    EditText neirong;
    private TiJIaoDialog tiJIaoDialog=null;
    private BaoCunBeanDao baoCunBeanDao=null;
    private BaoCunBean baoCunBean=null;;
    private String ss;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fa_bu_gong_gao);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.titlecolor);
        }

        baoCunBeanDao=MyApplication.myAppLaction.getDaoSession().getBaoCunBeanDao();
        if (baoCunBeanDao!=null){
            baoCunBean=baoCunBeanDao.load(123456L);
        }

        shijian.setText(DateUtils.time(System.currentTimeMillis()+""));
        title.setText("发布公告");
        righttv.setText("发 布");
        righttv.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.leftim, R.id.righttv, R.id.shijian_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.leftim:
                finish();
                break;
            case R.id.righttv:
               String s1=biaoti.getText().toString().trim();
                String s2=neirong.getText().toString().trim();
                String s3=shijian.getText().toString().trim();
                if (!s1.equals("") && !s2.equals("") && !s3.equals("")){
                    link_delect();
                }else {
                    Utils.showToast(FaBuGongGaoActivity.this,"请填写完整信息再发布",3);
                }


                break;
            case R.id.shijian_ll:

                Intent intent = new Intent(FaBuGongGaoActivity.this, DatePickActivity.class).putExtra("yyy",1);
                startActivityForResult(intent, 2);

                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == 2) {
            // 选择预约时间的页面被关闭
            String date = data.getStringExtra("date");
            int yyy=data.getIntExtra("yyy",0);
            if (yyy==1){
                shijian.setText(date);
            }
        }
    }

    private void link_delect() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (tiJIaoDialog==null && !FaBuGongGaoActivity.this.isFinishing()){
                    tiJIaoDialog=new TiJIaoDialog(FaBuGongGaoActivity.this);
                    tiJIaoDialog.show();
                }
            }
        });

        //final MediaType JSON=MediaType.parse("application/json; charset=utf-8");
        //http://192.168.2.4:8080/sign?cmd=getUnSignList&subjectId=jfgsdf
        OkHttpClient okHttpClient= MyApplication.getOkHttpClient();

        if (null!=baoCunBean.getSid()) {


            //    /* form的分割线,自己定义 */
            //        String boundary = "xx--------------------------------------------------------------xx";
            RequestBody body = new FormBody.Builder()
                   // .add("id",baoCunBean.getSid())
                    .add("title",biaoti.getText().toString().trim())
                    .add("issueType","0")
                    .add("issueTime2",shijian.getText().toString().trim())
                    .add("content",neirong.getText().toString().trim())
                    .add("token",baoCunBean.getToken())
                    .add("accountId",baoCunBean.getSid())
                    .build();

            Request.Builder requestBuilder = new Request.Builder()
                    // .header("Content-Type", "application/json")
                    .post(body)
                    .url(baoCunBean.getDizhi() + "/addOrModifyNotice.do");

            // step 3：创建 Call 对象
            Call call = okHttpClient.newCall(requestBuilder.build());

            //step 4: 开始异步请求
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.d("AllConnects", "请求识别失败" + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (tiJIaoDialog != null && !FaBuGongGaoActivity.this.isFinishing() && tiJIaoDialog.isShowing()) {
                                tiJIaoDialog.dismiss();
                                tiJIaoDialog = null;
                            }
                        }
                    });

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (tiJIaoDialog != null) {
                                tiJIaoDialog.dismiss();
                                tiJIaoDialog = null;
                            }
                        }
                    });
                    Log.d("AllConnects", "请求识别成功" + call.request().toString());
                    //获得返回体
                    try {

                        ResponseBody body = response.body();
                        ss = body.string().trim();
                        Log.d("DengJiActivity", ss);

                        JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                        Gson gson = new Gson();
                        ShouFangBean zhaoPianBean = gson.fromJson(jsonObject, ShouFangBean.class);

                        if (zhaoPianBean.getDtoResult() == 0) {

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    if (!FaBuGongGaoActivity.this.isFinishing()) {
                                        Toast tastyToast = TastyToast.makeText(FaBuGongGaoActivity.this, " 发布成功 ", TastyToast.LENGTH_LONG, TastyToast.INFO);
                                        tastyToast.setGravity(Gravity.CENTER, 0, 0);
                                        tastyToast.show();

                                    }
                                    biaoti.setText("");
                                    neirong.setText("");

                                }
                            });

                        } else if (zhaoPianBean.getDtoResult()==-33){
                            //登陆过期
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast tastyToast= TastyToast.makeText(FaBuGongGaoActivity.this,"登陆过期,或账号在其它机器登陆,请重新登陆",TastyToast.LENGTH_LONG,TastyToast.ERROR);
                                    tastyToast.setGravity(Gravity.CENTER,0,0);
                                    tastyToast.show();
                                }
                            });
                        }else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (!FaBuGongGaoActivity.this.isFinishing()) {
                                        Toast tastyToast = TastyToast.makeText(FaBuGongGaoActivity.this, " 发布失败 ", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                        tastyToast.setGravity(Gravity.CENTER, 0, 0);
                                        tastyToast.show();
                                    }
                                }
                            });
                        }

                    } catch (Exception e) {
                        dengLuGuoQi(ss);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (tiJIaoDialog != null && !FaBuGongGaoActivity.this.isFinishing()) {
                                    tiJIaoDialog.dismiss();
                                    tiJIaoDialog = null;
                                }
                            }
                        });
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                Toast tastyToast = TastyToast.makeText(FaBuGongGaoActivity.this, "提交失败,请检查网络", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                tastyToast.setGravity(Gravity.CENTER, 0, 0);
                                tastyToast.show();

                            }
                        });
                        Log.d("WebsocketPushMsg", e.getMessage());
                    }
                }
            });
        }else {
            Toast tastyToast = TastyToast.makeText(FaBuGongGaoActivity.this, "账户ID为空!,请设置帐户ID", TastyToast.LENGTH_LONG, TastyToast.ERROR);
            tastyToast.setGravity(Gravity.CENTER, 0, 0);
            tastyToast.show();
        }
    }

    private void dengLuGuoQi(String ss){
        try {
            JsonObject jsonObject= GsonUtil.parse(ss).getAsJsonObject();
            Gson gson=new Gson();
            final MoRenFanHuiBean zhaoPianBean=gson.fromJson(jsonObject,MoRenFanHuiBean.class);
            if (zhaoPianBean.getDtoResult()==-33){
                //登陆过期
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast tastyToast= TastyToast.makeText(FaBuGongGaoActivity.this,"登陆过期,或账号在其它机器登陆,请重新登陆",TastyToast.LENGTH_LONG,TastyToast.ERROR);
                        tastyToast.setGravity(Gravity.CENTER,0,0);
                        tastyToast.show();
                    }
                });
            }
        }catch (Exception e1){
            Log.d("Fragment1", "e1:" + e1);
        }
    }

}
