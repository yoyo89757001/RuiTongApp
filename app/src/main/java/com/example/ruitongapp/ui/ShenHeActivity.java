package com.example.ruitongapp.ui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ruitongapp.MyApplication;
import com.example.ruitongapp.R;
import com.example.ruitongapp.beans.BaoCunBean;
import com.example.ruitongapp.beans.BaoCunBeanDao;
import com.example.ruitongapp.beans.MoRenFanHuiBean;
import com.example.ruitongapp.beans.ShouFangBean;
import com.example.ruitongapp.dialogs.QueRenDialog;
import com.example.ruitongapp.dialogs.TiJIaoDialog;
import com.example.ruitongapp.fangkebean.FangKeBean;
import com.example.ruitongapp.utils.DateUtils;
import com.example.ruitongapp.utils.GsonUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sdsmdg.tastytoast.TastyToast;

import org.parceler.Parcels;

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

public class ShenHeActivity extends Activity {
    @BindView(R.id.fangkeruku)
    ImageView fangkeruku;
    @BindView(R.id.laifangren)
    TextView laifangren;
    @BindView(R.id.beifangren)
    TextView beifangren;
    @BindView(R.id.shouji)
    TextView shouji;
    @BindView(R.id.zhiwei)
    TextView zhiwei;
    @BindView(R.id.ruzhishijian)
    TextView ruzhishijian;
    @BindView(R.id.beizhu)
    TextView beizhu;
    @BindView(R.id.tongguo)
    Button tongguo;
    @BindView(R.id.jujue)
    Button jujue;
    @BindView(R.id.leftim)
    ImageView leftim;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.righttv)
    TextView righttv;
    private BaoCunBeanDao baoCunBeanDao = null;
    private BaoCunBean baoCunBean = null;
    private FangKeBean.ObjectsBean benDiYuanGong = null;
    private TiJIaoDialog tiJIaoDialog = null;
    private String ss;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shen_he);
        ButterKnife.bind(this);

        benDiYuanGong = Parcels.unwrap(getIntent().getParcelableExtra("chuansong"));
        baoCunBeanDao = MyApplication.myAppLaction.getDaoSession().getBaoCunBeanDao();
        if (baoCunBeanDao != null) {
            baoCunBean = baoCunBeanDao.load(123456L);
        }
        title.setText("预约待审核");
        righttv.setText("删 除");
        righttv.setVisibility(View.VISIBLE);
        if (benDiYuanGong!=null){
            beizhu.setText(benDiYuanGong.getSource());
            laifangren.setText(benDiYuanGong.getName());
            beifangren.setText(benDiYuanGong.getVisitPerson());
            shouji.setText(benDiYuanGong.getPhone());
            zhiwei.setText(benDiYuanGong.getVisitIncident());
            ruzhishijian.setText(DateUtils.time(benDiYuanGong.getVisitDate()+""));
            Glide.with(ShenHeActivity.this)
                    .load(baoCunBean.getDizhi()+"/upload/compare/"+benDiYuanGong.getScanPhoto())
                    .into(fangkeruku);
        }

    }


    private void link_shenhe(String type) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (tiJIaoDialog == null && !ShenHeActivity.this.isFinishing()) {
                    tiJIaoDialog = new TiJIaoDialog(ShenHeActivity.this);
                    tiJIaoDialog.show();
                }
            }
        });


        //final MediaType JSON=MediaType.parse("application/json; charset=utf-8");
        //http://192.168.2.4:8080/sign?cmd=getUnSignList&subjectId=jfgsdf
        OkHttpClient okHttpClient = MyApplication.getOkHttpClient();

        if (null != baoCunBean.getSid()) {


            //    /* form的分割线,自己定义 */
            //        String boundary = "xx--------------------------------------------------------------xx";
            RequestBody body = new FormBody.Builder()
                    .add("id", benDiYuanGong.getId() + "")
                    .add("audit", type)
                    .add("token", baoCunBean.getToken())
                    .add("accountId", baoCunBean.getSid())
                    .build();

            Request.Builder requestBuilder = new Request.Builder()
                    // .header("Content-Type", "application/json")
                    .post(body)
                    .url(baoCunBean.getDizhi() + "/iauditVisitor.do");

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
                            if (tiJIaoDialog != null && !ShenHeActivity.this.isFinishing() && tiJIaoDialog.isShowing()) {
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
                        String ss = body.string().trim();
                        Log.d("DengJiActivity", ss);

                        JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                        Gson gson = new Gson();
                        ShouFangBean zhaoPianBean = gson.fromJson(jsonObject, ShouFangBean.class);

                        if (zhaoPianBean.getDtoResult() == 0) {

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    if (!ShenHeActivity.this.isFinishing()) {
                                        Toast tastyToast = TastyToast.makeText(ShenHeActivity.this, "提交成功", TastyToast.LENGTH_LONG, TastyToast.INFO);
                                        tastyToast.setGravity(Gravity.CENTER, 0, 0);
                                        tastyToast.show();
                                        finish();
                                        //  sendBroadcast(new Intent("shanchu3"));
                                    }


                                }
                            });

                        } else {
                            if (!ShenHeActivity.this.isFinishing()) {
                                Toast tastyToast = TastyToast.makeText(ShenHeActivity.this, "提交失败", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                tastyToast.setGravity(Gravity.CENTER, 0, 0);
                                tastyToast.show();
                            }
                        }

                    } catch (Exception e) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (tiJIaoDialog != null) {
                                    tiJIaoDialog.dismiss();
                                    tiJIaoDialog = null;
                                }
                            }
                        });
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                Toast tastyToast = TastyToast.makeText(ShenHeActivity.this, "提交失败,请检查网络", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                tastyToast.setGravity(Gravity.CENTER, 0, 0);
                                tastyToast.show();

                            }
                        });
                        Log.d("WebsocketPushMsg", e.getMessage());
                    }
                }
            });
        } else {
            Toast tastyToast = TastyToast.makeText(ShenHeActivity.this, "账户ID为空!,请设置帐户ID", TastyToast.LENGTH_LONG, TastyToast.ERROR);
            tastyToast.setGravity(Gravity.CENTER, 0, 0);
            tastyToast.show();
        }
    }

    @OnClick({R.id.tongguo, R.id.jujue, R.id.leftim, R.id.righttv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tongguo:
                link_shenhe("1");
                break;
            case R.id.jujue:
                link_shenhe("2");
                break;
            case R.id.leftim:
                finish();
                break;
            case R.id.righttv:
                final QueRenDialog dialog=new QueRenDialog(ShenHeActivity.this);
                dialog.setCountText("你确定要删除吗？");
                dialog.setOnPositiveListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        link_delect();
                        dialog.dismiss();
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
        }
    }

    private void link_delect() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (tiJIaoDialog==null && !ShenHeActivity.this.isFinishing()){
                    tiJIaoDialog=new TiJIaoDialog(ShenHeActivity.this);
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
                    .add("id", benDiYuanGong.getId()+"")
                    .add("token",baoCunBean.getToken())
                    .add("accountId",baoCunBean.getSid())
                    .build();

            Request.Builder requestBuilder = new Request.Builder()
                    // .header("Content-Type", "application/json")
                    .post(body)
                    .url(baoCunBean.getDizhi() + "/delCompare.do");

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
                            if (tiJIaoDialog != null && !ShenHeActivity.this.isFinishing() && tiJIaoDialog.isShowing()) {
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

                                    if (!ShenHeActivity.this.isFinishing()) {
                                        Toast tastyToast = TastyToast.makeText(ShenHeActivity.this, "删除成功", TastyToast.LENGTH_LONG, TastyToast.INFO);
                                        tastyToast.setGravity(Gravity.CENTER, 0, 0);
                                        tastyToast.show();
                                        finish();
                                        // sendBroadcast(new Intent("shanchu"));
                                    }


                                }
                            });

                        } else {
                            if (!ShenHeActivity.this.isFinishing()) {
                                Toast tastyToast = TastyToast.makeText(ShenHeActivity.this, "删除失败", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                tastyToast.setGravity(Gravity.CENTER, 0, 0);
                                tastyToast.show();
                            }
                        }

                    } catch (Exception e) {
                        dengLuGuoQi(ss);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (tiJIaoDialog != null && !ShenHeActivity.this.isFinishing()) {
                                    tiJIaoDialog.dismiss();
                                    tiJIaoDialog = null;
                                }
                            }
                        });
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                Toast tastyToast = TastyToast.makeText(ShenHeActivity.this, "提交失败,请检查网络", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                tastyToast.setGravity(Gravity.CENTER, 0, 0);
                                tastyToast.show();

                            }
                        });
                        Log.d("WebsocketPushMsg", e.getMessage());
                    }
                }
            });
        }else {
            Toast tastyToast = TastyToast.makeText(ShenHeActivity.this, "账户ID为空!,请设置帐户ID", TastyToast.LENGTH_LONG, TastyToast.ERROR);
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
//                                    if (jiaZaiDialog!=null && jiaZaiDialog.isShowing()){
//                                        jiaZaiDialog.dismiss();
//                                    }
                        Toast tastyToast= TastyToast.makeText(ShenHeActivity.this,"登陆过期,或账号在其它机器登陆,请重新登陆",TastyToast.LENGTH_LONG,TastyToast.ERROR);
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
