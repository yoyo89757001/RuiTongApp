package com.example.ruitongapp.fargments;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.example.ruitongapp.MyApplication;
import com.example.ruitongapp.R;
import com.example.ruitongapp.adapters.GongGaoAdapter;
import com.example.ruitongapp.beans.BaoCunBean;
import com.example.ruitongapp.beans.BaoCunBeanDao;
import com.example.ruitongapp.beans.MoRenFanHuiBean;
import com.example.ruitongapp.beans.ShouFangBean;
import com.example.ruitongapp.dialogs.TiJIaoDialog;
import com.example.ruitongapp.gonggaobean.GongGaoBean;
import com.example.ruitongapp.interfaces.ClickIntface;
import com.example.ruitongapp.ui.FaBuGongGaoActivity;
import com.example.ruitongapp.utils.GsonUtil;
import com.example.ruitongapp.view.WrapContentLinearLayoutManager;
import com.github.jdsjlzx.ItemDecoration.DividerDecoration;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sdsmdg.tastytoast.TastyToast;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment4 extends Fragment implements ClickIntface {

    @BindView(R.id.fabu)
    TextView fabu;
    @BindView(R.id.bianji)
    TextView bianji;
    Unbinder unbinder;
    private WrapContentLinearLayoutManager linearLayoutManager = null;
    private LRecyclerView lRecyclerView;
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private List<GongGaoBean.ObjectsBean> dataList;
    private GongGaoAdapter taiZhangAdapter;
    private TiJIaoDialog jiaZaiDialog = null;
    private Call call = null;
    private BaoCunBeanDao baoCunBeanDao = null;
    private BaoCunBean baoCunBean = null;
    private String ss = null;
    private int dangQianYe = 1;
    private int qingQiuYe = 1;
    //定义一个过滤器；
    private IntentFilter intentFilter;
    //定义一个广播监听器；
    private NetChangReceiver netChangReceiver;
    private int piotions = -1;
    private boolean isShanChu=false;
    private TiJIaoDialog tiJIaoDialog=null;


    public Fragment4() {
        dataList = new ArrayList<>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = null;
        try {

            baoCunBeanDao = MyApplication.myAppLaction.getDaoSession().getBaoCunBeanDao();
            if (baoCunBeanDao != null) {
                baoCunBean = baoCunBeanDao.load(123456L);
            }
            //实例化过滤器；
            intentFilter = new IntentFilter();
            //添加过滤的Action值；
            intentFilter.addAction("shanchu");
            intentFilter.addAction("shuaxing");
            //实例化广播监听器；
            netChangReceiver = new NetChangReceiver();
            //将广播监听器和过滤器注册在一起；
            getActivity().registerReceiver(netChangReceiver, intentFilter);

            view = inflater.inflate(R.layout.fragment_fragment4, container, false);
            linearLayoutManager = new WrapContentLinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

            lRecyclerView = (LRecyclerView) view.findViewById(R.id.recyclerView);
            taiZhangAdapter = new GongGaoAdapter(dataList, getContext(), baoCunBean.getDizhi());
            taiZhangAdapter.setClickIntface(this);
            lRecyclerViewAdapter = new LRecyclerViewAdapter(taiZhangAdapter);

            lRecyclerView.setLayoutManager(linearLayoutManager);

            lRecyclerView.setAdapter(lRecyclerViewAdapter);

            final DividerDecoration divider = new DividerDecoration.Builder(getContext())
                    .setHeight(R.dimen.default_divider_height2)
                    .setPadding(R.dimen.default_divider_padding2)
                    .setColorResource(R.color.transparent)
                    .build();
            lRecyclerView.addItemDecoration(divider);
            //设置头部加载颜色
            lRecyclerView.setHeaderViewColor(R.color.colorAccent, R.color.blake, android.R.color.white);
            lRecyclerView.setRefreshProgressStyle(ProgressStyle.LineSpinFadeLoader);
            lRecyclerView.setFooterViewColor(R.color.textcolor, R.color.blake, android.R.color.white);
            //设置底部加载文字提示
            lRecyclerView.setFooterViewHint("拼命加载中", "--------我是有底线的--------", "网络不给力...");
            lRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);

            lRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
//                    if (dataList.size() != 0) {
//                        piotions = position;
//                        Bundle bundle = new Bundle();
//                        bundle.putParcelable("chuansong", Parcels.wrap(dataList.get(position)));
//                        startActivity(new Intent(getContext(), XiuGaiYuanGongActivity.class).putExtra("type", 2).putExtras(bundle));
//                    }
                }
            });


            lRecyclerView.setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh() {
                    //下拉刷新
                    Log.d("Fragment144444", "下拉刷新");
                    dangQianYe = 1;
                    qingQiuYe = 1;
                    link_liebiao("", qingQiuYe);
                }
            });

            lRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore() {
                    qingQiuYe++;
                    //加载更多
                    link_liebiao("", qingQiuYe);
                }
            });

            //  lRecyclerView.forceToRefresh();
            unbinder = ButterKnife.bind(this, view);
        } catch (IllegalArgumentException e) {
            Log.d("Fragment4", e.getMessage() + "");
        }

        return view;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getActivity().unregisterReceiver(netChangReceiver);
        unbinder.unbind();
    }

    @OnClick({R.id.fabu, R.id.bianji})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fabu:
                if (isShanChu){
                    fabu.setText("发 布");
                    bianji.setText("编 辑");
                    isShanChu=false;
                    int size=dataList.size();
                    if (size!=0){
                        for (int i=0;i<size;i++){
                            dataList.get(i).setShanchu(false);
                        }
                        lRecyclerViewAdapter.notifyDataSetChanged();
                    }

                }else {
                    fabu.setText("发 布");
                    isShanChu=false;
                    int size=dataList.size();
                    if (size!=0){
                        for (int i=0;i<size;i++){
                            dataList.get(i).setShanchu(false);
                        }
                        lRecyclerViewAdapter.notifyDataSetChanged();
                    }
                //跳到发布界面
                    startActivity(new Intent(getActivity(), FaBuGongGaoActivity.class));

                }

                break;
            case R.id.bianji:
                if (bianji.getText().equals("编 辑")){
                    bianji.setText("删 除");
                    fabu.setText("取 消");
                    isShanChu=true;

                    int size=dataList.size();
                if (size!=0){
                    for (int i=0;i<size;i++){
                        dataList.get(i).setShanchu(true);
                    }
                    lRecyclerViewAdapter.notifyDataSetChanged();
                }

                }else {
                    //删除
                    bianji.setText("编 辑");
                    fabu.setText("发 布");
                    isShanChu=false;
                    int size=dataList.size();
                    if (size>0){
                        for (int i=size-1;i>=0;i--){
                            dataList.get(i).setShanchu(false);
                            if (dataList.get(i).isShanChu2()){
                                link_delect(dataList.get(i).getId()+"");
                                dataList.remove(i);
                            }

                        }
                        lRecyclerViewAdapter.notifyDataSetChanged();
                    }

                }
                break;
        }
    }

    @Override
    public void BackId(int id) {

    }

    @Override
    public void onResume() {
        lRecyclerView.forceToRefresh();
        super.onResume();

    }
    private class NetChangReceiver extends BroadcastReceiver {

        //重写onReceive方法，该方法的实体为，接收到广播后的执行代码；
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("shanchu")) {
                if (piotions != -1) {
                    dataList.remove(piotions);
                    piotions = -1;
                    taiZhangAdapter.notifyDataSetChanged();
                }

            }
            if (intent.getAction().equals("shuaxing")) {
                Log.d("NetChangReceiver", "刷新tretret");

                lRecyclerView.forceToRefresh();
            }
        }
    }

    private void link_liebiao(String name, int pageNum) {

        try {

            RequestBody body = new FormBody.Builder()
                    .add("accountId", baoCunBean.getSid())
                    .add("pageNum", pageNum + "")
                    .add("pageSize", "15")
                    .add("token", baoCunBean.getToken())
                    .build();

            Request.Builder requestBuilder = new Request.Builder()
                    // .header("Content-Type", "application/json")
                    .post(body)
                    .url(baoCunBean.getDizhi() + "/queryNotice.do");


            // step 3：创建 Call 对象
            call = MyApplication.getOkHttpClient().newCall(requestBuilder.build());

            //step 4: 开始异步请求
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    if (getActivity() != null) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                if (dataList.size() != 0) {
                                    dataList.clear();
                                }
                                 lRecyclerView.refreshComplete(0);// REQUEST_COUNT为每页加载数量
                                taiZhangAdapter.notifyDataSetChanged();

                                Toast tastyToast = TastyToast.makeText(getActivity(), "获取数据失败.", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                tastyToast.setGravity(Gravity.CENTER, 0, 0);
                                tastyToast.show();
                            }
                        });
                    }
                    Log.d("AllConnects", "请求识别失败" + e.getMessage());
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
                    Log.d("AllConnects", "请求识别成功" + call.request().toString());
                    //获得返回体
                    try {

                        ResponseBody body = response.body();
                        // Log.d("AllConnects", "识别结果返回"+response.body().string());
                        ss = body.string();
                        Log.d("Fragment1", ss);
                        JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                        Gson gson = new Gson();
                        final GongGaoBean zhaoPianBean = gson.fromJson(jsonObject, GongGaoBean.class);
                        if (qingQiuYe == dangQianYe) {

                            if (getActivity() != null) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (dataList.size() != 0) {
                                            dataList.clear();
                                        }
                                        dataList.addAll(zhaoPianBean.getObjects() != null ? zhaoPianBean.getObjects() : new ArrayList<GongGaoBean.ObjectsBean>());

                                        lRecyclerView.refreshComplete(dataList.size());// REQUEST_COUNT为每页加载数量
                                        taiZhangAdapter.notifyDataSetChanged();

                                    }
                                });
                            }


                        } else {

                            if (getActivity() != null) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        int size = zhaoPianBean.getObjects().size();
                                        for (int i = 0; i < size; i++) {
                                            dataList.add(zhaoPianBean.getObjects().get(i));
                                        }

                                        lRecyclerView.refreshComplete(15);// REQUEST_COUNT为每页加载数量
                                        taiZhangAdapter.notifyDataSetChanged();
                                    }
                                });
                            }

                        }
                        if (zhaoPianBean.getObjects().size() == 0 && dataList.size()>=15) {
                            if (getActivity() != null) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        lRecyclerView.setNoMore(true);
                                    }
                                });
                            }

                        }

                    } catch (Exception e) {
                        if (getActivity() != null) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    if (dataList.size() != 0) {
                                        dataList.clear();
                                    }
                                    // lRecyclerView.refreshComplete(0);// REQUEST_COUNT为每页加载数量
                                    taiZhangAdapter.notifyDataSetChanged();
                                }
                            });
                        }

                        Log.d("WebsocketPushMsg", e.getMessage() + "");
                        try {
                            JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                            Gson gson = new Gson();
                            final MoRenFanHuiBean zhaoPianBean = gson.fromJson(jsonObject, MoRenFanHuiBean.class);
                            if (zhaoPianBean.getDtoResult() == -33) {
                                //登陆过期
                                if (getActivity() != null) {
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast tastyToast = TastyToast.makeText(getActivity(), "登陆过期,或账号在其它机器登陆,请重新登陆", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                            tastyToast.setGravity(Gravity.CENTER, 0, 0);
                                            tastyToast.show();
                                        }
                                    });
                                }
                            }
                        } catch (Exception e1) {
                            Log.d("Fragment1", "e1:" + e1);
                        }
                        if (getActivity() != null) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast tastyToast = TastyToast.makeText(getActivity(), "获取数据失败.", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                    tastyToast.setGravity(Gravity.CENTER, 0, 0);
                                    tastyToast.show();
                                }
                            });
                        }

                    }
                }
            });

        } catch (Exception e) {
            Log.d("Fragment1", e.getMessage() + " rrrrrr");
        }
    }

    private void link_delect(String id) {

                if (tiJIaoDialog==null && !getActivity().isFinishing()){
                    tiJIaoDialog=new TiJIaoDialog(getActivity());
                    tiJIaoDialog.show();
                }


        //final MediaType JSON=MediaType.parse("application/json; charset=utf-8");
        //http://192.168.2.4:8080/sign?cmd=getUnSignList&subjectId=jfgsdf
        OkHttpClient okHttpClient= MyApplication.getOkHttpClient();

        if (null!=baoCunBean.getSid()) {


            //    /* form的分割线,自己定义 */
            //        String boundary = "xx--------------------------------------------------------------xx";
            RequestBody body = new FormBody.Builder()
                    .add("id",id )
                    .add("token",baoCunBean.getToken())
                    .add("accountId",baoCunBean.getSid())
                    .build();

            Request.Builder requestBuilder = new Request.Builder()
                    // .header("Content-Type", "application/json")
                    .post(body)
                    .url(baoCunBean.getDizhi() + "/delNotice.do");

            // step 3：创建 Call 对象
            Call call = okHttpClient.newCall(requestBuilder.build());

            //step 4: 开始异步请求
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.d("AllConnects", "请求识别失败" + e.getMessage());
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (tiJIaoDialog != null && !getActivity().isFinishing() && tiJIaoDialog.isShowing()) {
                                tiJIaoDialog.dismiss();
                                tiJIaoDialog = null;
                            }
                        }
                    });

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    getActivity().runOnUiThread(new Runnable() {
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

                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    if (!getActivity().isFinishing()) {
                                        Toast tastyToast = TastyToast.makeText(getActivity(), "删除成功", TastyToast.LENGTH_LONG, TastyToast.INFO);
                                        tastyToast.setGravity(Gravity.CENTER, 0, 0);
                                        tastyToast.show();

                                    }


                                }
                            });

                        } else {
                            if (!getActivity().isFinishing()) {
                                Toast tastyToast = TastyToast.makeText(getActivity(), "删除失败", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                tastyToast.setGravity(Gravity.CENTER, 0, 0);
                                tastyToast.show();
                            }
                        }

                    } catch (Exception e) {
                        dengLuGuoQi(ss);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (tiJIaoDialog != null && !getActivity().isFinishing()) {
                                    tiJIaoDialog.dismiss();
                                    tiJIaoDialog = null;
                                }
                            }
                        });
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                Toast tastyToast = TastyToast.makeText(getActivity(), "提交失败,请检查网络", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                tastyToast.setGravity(Gravity.CENTER, 0, 0);
                                tastyToast.show();

                            }
                        });
                        Log.d("WebsocketPushMsg", e.getMessage());
                    }
                }
            });
        }else {
            Toast tastyToast = TastyToast.makeText(getActivity(), "账户ID为空!,请设置帐户ID", TastyToast.LENGTH_LONG, TastyToast.ERROR);
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
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                                    if (jiaZaiDialog!=null && jiaZaiDialog.isShowing()){
//                                        jiaZaiDialog.dismiss();
//                                    }
                        Toast tastyToast= TastyToast.makeText(getActivity(),"登陆过期,或账号在其它机器登陆,请重新登陆",TastyToast.LENGTH_LONG,TastyToast.ERROR);
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
