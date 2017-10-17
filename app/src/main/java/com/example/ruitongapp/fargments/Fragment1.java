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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ruitongapp.MyApplication;
import com.example.ruitongapp.R;
import com.example.ruitongapp.adapters.YuanGongAdapter;
import com.example.ruitongapp.beans.BaoCunBean;
import com.example.ruitongapp.beans.BaoCunBeanDao;
import com.example.ruitongapp.beans.BenDiYuanGong;
import com.example.ruitongapp.beans.BenDiYuanGongDao;
import com.example.ruitongapp.beans.MoRenFanHuiBean;
import com.example.ruitongapp.beans.YuanGongBean;
import com.example.ruitongapp.dialogs.TiJIaoDialog;
import com.example.ruitongapp.heimingdanbean.HeiMingDanBean;
import com.example.ruitongapp.ui.SouSuoActivity;
import com.example.ruitongapp.ui.XiuGaiYuanGongActivity;
import com.example.ruitongapp.utils.GsonUtil;
import com.example.ruitongapp.view.SideBar;
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

import org.parceler.Parcels;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import opensource.jpinyin.PinyinHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment1 extends Fragment {
    @BindView(R.id.tianjia)
    ImageView tianjia;
    @BindView(R.id.sousuo)
    ImageView sousuo;
    Unbinder unbinder;
    private TextView txtShowCurrentLetter;
    private SideBar sideBar;
    private WrapContentLinearLayoutManager linearLayoutManager = null;
    private LRecyclerView lRecyclerView;
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private List<YuanGongBean.ObjectsBean> dataList;
    private YuanGongAdapter taiZhangAdapter;
    private TiJIaoDialog jiaZaiDialog=null;
    private Call call=null;
    private BaoCunBeanDao baoCunBeanDao=null;
    private BaoCunBean baoCunBean=null;
    private String ss=null;
    private int dangQianYe=1;
    private int qingQiuYe=1;
    //定义一个过滤器；
    private IntentFilter intentFilter;
    //定义一个广播监听器；
    private NetChangReceiver netChangReceiver;
    private int piotions=-1;

    public Fragment1() {
        dataList = new ArrayList<>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        baoCunBeanDao=MyApplication.myAppLaction.getDaoSession().getBaoCunBeanDao();
        if (baoCunBeanDao!=null){
            baoCunBean=baoCunBeanDao.load(123456L);
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

        View view = inflater.inflate(R.layout.fragment_fragment1, container, false);
        sideBar = (SideBar) view.findViewById(R.id.side_bar);
        txtShowCurrentLetter = (TextView) view.findViewById(R.id.txt_show_current_letter);
        linearLayoutManager = new WrapContentLinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);

        lRecyclerView = (LRecyclerView) view.findViewById(R.id.recyclerView);
        taiZhangAdapter = new YuanGongAdapter(dataList,getContext(),baoCunBean.getDizhi());
        lRecyclerViewAdapter = new LRecyclerViewAdapter(taiZhangAdapter);

        lRecyclerView.setLayoutManager(linearLayoutManager);

        lRecyclerView.setAdapter(lRecyclerViewAdapter);

        DividerDecoration divider = new DividerDecoration.Builder(getContext())
                .setHeight(R.dimen.default_divider_height2)
                .setPadding(R.dimen.default_divider_padding2)
                .setColorResource(R.color.transparent)
                .build();
        lRecyclerView.addItemDecoration(divider);
        //设置头部加载颜色
        lRecyclerView.setHeaderViewColor(R.color.colorAccent, R.color.blake ,android.R.color.white);
        lRecyclerView.setRefreshProgressStyle(ProgressStyle.LineSpinFadeLoader);
        lRecyclerView.setFooterViewColor(R.color.textcolor, R.color.blake ,android.R.color.white);
        //设置底部加载文字提示
        lRecyclerView.setFooterViewHint("拼命加载中","--------我是有底线的--------","网络不给力...");
        lRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);

        lRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                piotions=position;
                Bundle bundle = new Bundle();
                bundle.putParcelable("chuansong", Parcels.wrap(dataList.get(position)));
                startActivity(new Intent(getContext(),XiuGaiYuanGongActivity.class).putExtra("type",2).putExtras(bundle));

            }
        });
        setCallbackInterface();

        unbinder = ButterKnife.bind(this, view);

        lRecyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                //下拉刷新
                Log.d("Fragment144444", "下拉刷新");
                dangQianYe=1;
                qingQiuYe=1;
                link_liebiao("",qingQiuYe);
            }
        });

        lRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                qingQiuYe++;
                //加载更多
                link_liebiao("",qingQiuYe);
            }
        });



        lRecyclerView.forceToRefresh();

        return view;
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
                if (piotions!=-1){
                    dataList.remove(piotions);
                    piotions=-1;
                    taiZhangAdapter.notifyDataSetChanged();
                }

            }
            if (intent.getAction().equals("shuaxing")) {
                Log.d("NetChangReceiver", "刷新tretret");

                lRecyclerView.forceToRefresh();
            }
        }
    }



    public void setCallbackInterface() {
        //回调接口
        sideBar.setOnCurrentLetterListener(new SideBar.OnCurrentLetterListener() {
            @Override
            public void showCurrentLetter(String currentLetter) {
                txtShowCurrentLetter.setVisibility(View.VISIBLE);
                txtShowCurrentLetter.setText(currentLetter);

                int position = taiZhangAdapter.getCurrentLetterPosition(currentLetter);

                if (position != -1) {
                    linearLayoutManager.scrollToPositionWithOffset(position + 1, 1);
                }

            }

            @Override
            public void hideCurrentLetter() {
                txtShowCurrentLetter.setVisibility(View.GONE);
            }
        });
    }


    /**
     * 将中文转化为拼音
     */
    public static void chineseToPinyin(List<YuanGongBean.ObjectsBean> list) {
        for (int i = 0; i < list.size(); i++) {
            YuanGongBean.ObjectsBean contactsModel1 = list.get(i);
            //将汉字转换为拼音
            String pinyinString = PinyinHelper.getShortPinyin(list.get(i).getName());
            //将拼音字符串转换为大写拼音
            String upperCasePinyinString = String.valueOf(pinyinString.charAt(0)).toUpperCase();
            //获取大写拼音字符串的第一个字符
            char tempChar = upperCasePinyinString.charAt(0);

            if (tempChar < 'A' || tempChar > 'Z') {
                contactsModel1.setFirstLetter("#");
            } else {
                // Log.d("TabFragment1", String.valueOf(tempChar));
                contactsModel1.setFirstLetter(String.valueOf(tempChar));
            }
        }
    }

    private void paixu() {

        //将联系人列表的标题字母排序
        Collections.sort(dataList, new Comparator<YuanGongBean.ObjectsBean>() {
            @Override
            public int compare(YuanGongBean.ObjectsBean lhs, YuanGongBean.ObjectsBean rhs) {
                return lhs.getFirstLetter().compareTo(rhs.getFirstLetter());
            }
        });
    }

    @Override
    public void onDestroyView() {
        getActivity().unregisterReceiver(netChangReceiver);
        super.onDestroyView();
        unbinder.unbind();

    }

    @OnClick({R.id.tianjia, R.id.sousuo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tianjia:
                startActivity(new Intent(getContext(),XiuGaiYuanGongActivity.class).putExtra("type",1));

                break;
            case R.id.sousuo:
                startActivity(new Intent(getContext(),SouSuoActivity.class));
                break;
        }
    }

    private void link_liebiao(String name,int pageNum) {

//        jiaZaiDialog=new TiJIaoDialog(getActivity());
//        if (!getActivity().isFinishing()){
//            jiaZaiDialog.show();
//        }

        try {

        RequestBody body = new FormBody.Builder()
                .add("accountId",baoCunBean.getSid())
                .add("subject_type", "0")
                .add("status","1")
                .add("name",name )
                .add("pageNum",pageNum+"")
                .add("pageSize", "20")
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
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        if (jiaZaiDialog!=null && jiaZaiDialog.isShowing()){
//                            jiaZaiDialog.dismiss();
//                        }
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                lRecyclerView.refreshComplete(20);// REQUEST_COUNT为每页加载数量
                                taiZhangAdapter.notifyDataSetChanged();

                            }
                        });
                        Toast tastyToast= TastyToast.makeText(getActivity(),"网络错误.",TastyToast.LENGTH_LONG,TastyToast.ERROR);
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
                    if (qingQiuYe==dangQianYe){
                        if (dataList.size()!=0){
                            dataList.clear();
                        }
                        dataList.addAll(zhaoPianBean.getObjects());
                        chineseToPinyin(dataList);
                        paixu();
                        taiZhangAdapter.setLetters();

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                lRecyclerView.refreshComplete(dataList.size());// REQUEST_COUNT为每页加载数量
                                taiZhangAdapter.notifyDataSetChanged();

                            }
                        });


                    }else {
                        int size=zhaoPianBean.getObjects().size();
                        for (int i=0;i<size;i++){
                            dataList.add(zhaoPianBean.getObjects().get(i));
                        }
                        chineseToPinyin(dataList);
                        paixu();
                        taiZhangAdapter.setLetters();

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                lRecyclerView.refreshComplete(20);// REQUEST_COUNT为每页加载数量
                                taiZhangAdapter.notifyDataSetChanged();
                            }
                        });


                    }
                    if (zhaoPianBean.getObjects().size()==0){
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                lRecyclerView.setNoMore(true);
                            }
                        });

                    }

                }catch (Exception e){
                    Log.d("WebsocketPushMsg", e.getMessage()+"");
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

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            if (jiaZaiDialog!=null && jiaZaiDialog.isShowing()){
//                                jiaZaiDialog.dismiss();
//                            }
                            Toast tastyToast= TastyToast.makeText(getActivity(),"网络错误.",TastyToast.LENGTH_LONG,TastyToast.ERROR);
                            tastyToast.setGravity(Gravity.CENTER,0,0);
                            tastyToast.show();
                        }
                    });


                }
            }
        });

        }catch (Exception e){
            Log.d("Fragment1", e.getMessage()+"");
        }
    }


}
