package com.example.ruitongapp.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.ruitongapp.MyApplication;
import com.example.ruitongapp.R;
import com.example.ruitongapp.adapters.BaoGaoAdapter2;
import com.example.ruitongapp.beans.BaoCunBean;
import com.example.ruitongapp.beans.BaoCunBeanDao;
import com.example.ruitongapp.beans.BenDiYuanGong;
import com.example.ruitongapp.beans.BenDiYuanGongDao;
import com.example.ruitongapp.beans.MoRenFanHuiBean;
import com.example.ruitongapp.beans.YuanGongBean;
import com.example.ruitongapp.dialogs.TiJIaoDialog;
import com.example.ruitongapp.interfaces.ClickIntface;
import com.example.ruitongapp.utils.GsonUtil;
import com.example.ruitongapp.view.MyEditTextWrite;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.sdsmdg.tastytoast.TastyToast;

import org.parceler.Parcels;

import java.io.IOException;
import java.util.ArrayList;
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

public class SouSuoActivity extends Activity implements ClickIntface {

    @BindView(R.id.fg)
    ImageView fg;
    @BindView(R.id.sousuo_et)
    MyEditTextWrite sousuoEt;
    @BindView(R.id.sousuo)
    ImageView sousuo;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;


    private BenDiYuanGongDao benDiYuanGongDao = null;
    private BenDiYuanGong benDiYuanGong = null;
    private BaoGaoAdapter2 baoGaoAdapter2=null;
    private List<YuanGongBean.ObjectsBean> benDiYuanGongList;
    private BaoCunBeanDao baoCunBeanDao=null;
    private BaoCunBean baoCunBean=null;
    private Call call;
    private String ss;
    private TiJIaoDialog jiaZaiDialog=null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sou_suo);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.titlecolor);
        }
        benDiYuanGongDao= MyApplication.myAppLaction.getDaoSession().getBenDiYuanGongDao();
        baoCunBeanDao=MyApplication.myAppLaction.getDaoSession().getBaoCunBeanDao();
        if (baoCunBeanDao!=null){
            baoCunBean=baoCunBeanDao.load(123456L);
        }

        benDiYuanGongList=new ArrayList<>();

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        baoGaoAdapter2 = new BaoGaoAdapter2(benDiYuanGongList,SouSuoActivity.this,baoCunBean.getDizhi());
        baoGaoAdapter2.setClickIntface(this);
        recyclerView.setAdapter(baoGaoAdapter2);

        sousuoEt.addTextChangedListener(textWatcher);



    }

    @OnClick({R.id.fg, R.id.sousuo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fg:
                finish();
                break;
            case R.id.sousuo:

                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    private TextWatcher textWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (!s.toString().equals("")){
                link_liebiao(s.toString(),1);

            }else {

                if (benDiYuanGongList.size()!=0){
                    benDiYuanGongList.clear();
                }
                baoGaoAdapter2.notifyDataSetChanged();
            }

        }
    };

    @Override
    public void BackId(int id) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("chuansong", Parcels.wrap(benDiYuanGongList.get(id)));
        startActivity(new Intent(SouSuoActivity.this,XiuGaiYuanGongActivity.class).putExtra("type",2).putExtras(bundle));

    }


    private void link_liebiao(String name,int pageNum) {

        jiaZaiDialog=new TiJIaoDialog(SouSuoActivity.this);
        if (!SouSuoActivity.this.isFinishing()){
            jiaZaiDialog.show();
        }

        try {

            RequestBody body = new FormBody.Builder()
                    .add("accountId",baoCunBean.getSid())
                    .add("subject_type", "0")
                    .add("status","1")
                    .add("name",name )
                    .add("pageNum",pageNum+"")
                    .add("pageSize", "50")
                    .add("token", baoCunBean.getToken())
                    .build();

            Request.Builder requestBuilder = new Request.Builder()
                    // .header("Content-Type", "application/json")
                    .post(body)
                    .url(baoCunBean.getDizhi() + "/querySubjects.do");


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
                            Toast tastyToast= TastyToast.makeText(SouSuoActivity.this,"网络错误.",TastyToast.LENGTH_LONG,TastyToast.ERROR);
                            tastyToast.setGravity(Gravity.CENTER,0,0);
                            tastyToast.show();
                        }
                    });
                    Log.d("AllConnects", "请求识别失败"+e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (jiaZaiDialog!=null && jiaZaiDialog.isShowing()){
//                            jiaZaiDialog.dismiss();
//                        }
//                    }
//                });
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
                        ss=body.string();
                        Log.d("Fragment1", ss);
                        JsonObject jsonObject= GsonUtil.parse(ss).getAsJsonObject();
                        Gson gson=new Gson();
                        final YuanGongBean zhaoPianBean=gson.fromJson(jsonObject,YuanGongBean.class);
                        if (benDiYuanGongList.size()!=0){
                            benDiYuanGongList.clear();
                        }
                        benDiYuanGongList.addAll(zhaoPianBean.getObjects());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                baoGaoAdapter2.notifyDataSetChanged();
                            }
                        });

                    }catch (Exception e){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (jiaZaiDialog!=null && jiaZaiDialog.isShowing()){
                                    jiaZaiDialog.dismiss();
                                }

                            }
                        });
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
                                        Toast tastyToast= TastyToast.makeText(SouSuoActivity.this,"登陆过期,或账号在其它机器登陆,请重新登陆",TastyToast.LENGTH_LONG,TastyToast.ERROR);
                                        tastyToast.setGravity(Gravity.CENTER,0,0);
                                        tastyToast.show();
                                    }
                                });
                            }
                        }catch (Exception e1){
                            Log.d("Fragment1", "e1:" + e1);
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
//                            if (jiaZaiDialog!=null && jiaZaiDialog.isShowing()){
//                                jiaZaiDialog.dismiss();
//                            }
                                Toast tastyToast= TastyToast.makeText(SouSuoActivity.this,"网络错误.",TastyToast.LENGTH_LONG,TastyToast.ERROR);
                                tastyToast.setGravity(Gravity.CENTER,0,0);
                                tastyToast.show();
                            }
                        });

                        Log.d("WebsocketPushMsg", e.getMessage());
                    }
                }
            });

        }catch (Exception e){
            Log.d("Fragment1", e.getMessage()+"");
        }
    }

}
