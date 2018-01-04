package com.example.ruitongapp.fargments;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ruitongapp.MyApplication;
import com.example.ruitongapp.R;
import com.example.ruitongapp.adapters.FangKeAdapter;
import com.example.ruitongapp.beans.BaoCunBean;
import com.example.ruitongapp.beans.BaoCunBeanDao;
import com.example.ruitongapp.beans.MoRenFanHuiBean;
import com.example.ruitongapp.fangkebean.FangKeBean;
import com.example.ruitongapp.ui.ShenHeActivity;
import com.example.ruitongapp.ui.SouSuo2Activity;
import com.example.ruitongapp.ui.XiuGaiFangKeActivity;
import com.example.ruitongapp.utils.DateUtils;
import com.example.ruitongapp.utils.GsonUtil;
import com.example.ruitongapp.utils.Utils;
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

import org.parceler.Parcels;

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
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment2 extends Fragment {

    @BindView(R.id.sousuo)
    ImageView sousuo;
    @BindView(R.id.tianjia)
    ImageView tianjia;
    Unbinder unbinder;
    @BindView(R.id.t1)
    TextView t1;
    @BindView(R.id.l1)
    LinearLayout l1;
    @BindView(R.id.t2)
    TextView t2;
    @BindView(R.id.l2)
    LinearLayout l2;
    @BindView(R.id.t3)
    TextView t3;
    @BindView(R.id.l3)
    LinearLayout l3;
    @BindView(R.id.t4)
    TextView t4;
    @BindView(R.id.l4)
    LinearLayout l4;
    Unbinder unbinder1;

    private WrapContentLinearLayoutManager linearLayoutManager = null;
    private LRecyclerView lRecyclerView;
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private List<FangKeBean.ObjectsBean> dataList;
    private ImageView i1,i2,i3,i4;
    private FangKeAdapter taiZhangAdapter;
    private BaoCunBeanDao baoCunBeanDao = null;
    private BaoCunBean baoCunBean = null;
    private int dangQianYe = 1;
    private int qingQiuYe = 1;
    private String ss;
    private Call call;
    private int  shenhe=-1;
    public Fragment2() {
        dataList = new ArrayList<>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment2, container, false);
        i1= (ImageView) view.findViewById(R.id.i1);
        i2= (ImageView) view.findViewById(R.id.i2);
        i3= (ImageView) view.findViewById(R.id.i3);
        i4= (ImageView) view.findViewById(R.id.i4);
        try {

            baoCunBeanDao = MyApplication.myAppLaction.getDaoSession().getBaoCunBeanDao();
            if (baoCunBeanDao != null) {
                baoCunBean = baoCunBeanDao.load(123456L);
            }

            lRecyclerView = (LRecyclerView) view.findViewById(R.id.recyclerView);
            taiZhangAdapter = new FangKeAdapter(dataList, getContext(), baoCunBean.getDizhi());
            lRecyclerViewAdapter = new LRecyclerViewAdapter(taiZhangAdapter);

            linearLayoutManager = new WrapContentLinearLayoutManager(getContext());
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            lRecyclerView.setLayoutManager(linearLayoutManager);
            //设置头部加载颜色
            lRecyclerView.setHeaderViewColor(R.color.colorAccent, R.color.blake, android.R.color.white);
            lRecyclerView.setRefreshProgressStyle(ProgressStyle.LineSpinFadeLoader);
            lRecyclerView.setFooterViewColor(R.color.textcolor, R.color.blake, android.R.color.white);
            //设置底部加载文字提示
            lRecyclerView.setFooterViewHint("拼命加载中", "--------我是有底线的--------", "网络不给力...");
            lRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);

            lRecyclerView.setAdapter(lRecyclerViewAdapter);

            DividerDecoration divider = new DividerDecoration.Builder(getContext())
                    .setHeight(R.dimen.default_divider_height2)
                    .setPadding(R.dimen.default_divider_padding2)
                    .setColorResource(R.color.transparent)
                    .build();
            lRecyclerView.addItemDecoration(divider);


            lRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    if (dataList.size() != 0) {
                        if (dataList.get(position).getAudit().equals("0")) {
                            Bundle bundle = new Bundle();
                            bundle.putParcelable("chuansong", Parcels.wrap(dataList.get(position)));
                            startActivity(new Intent(getContext(), ShenHeActivity.class).putExtra("type", 2).putExtras(bundle));
                        } else {
                            Bundle bundle = new Bundle();
                            bundle.putParcelable("chuansong", Parcels.wrap(dataList.get(position)));
                            startActivity(new Intent(getContext(), XiuGaiFangKeActivity.class).putExtra("type", 2).putExtras(bundle));
                        }
                    }

                }
            });

            unbinder = ButterKnife.bind(this, view);

            lRecyclerView.setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh() {
                    //下拉刷新
                    Log.d("Fragment2", "下拉刷新");
                    dangQianYe = 1;
                    qingQiuYe = 1;
                    if (shenhe==-1){
                        link_liebiao("", qingQiuYe, shenhe, false);
                    }else {
                        link_liebiao("", qingQiuYe, shenhe, true);
                    }
                }
            });

            lRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore() {
                    qingQiuYe++;
                    //加载更多
                    if (shenhe==-1){
                        link_liebiao("", qingQiuYe, shenhe, false);
                    }else {
                        link_liebiao("", qingQiuYe, shenhe, true);
                    }

                }
            });


        } catch (IllegalArgumentException e) {
            Log.d("Fragment2", e.getMessage() + "");
        }
        unbinder1 = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onResume() {
        lRecyclerView.forceToRefresh();
        super.onResume();

    }

//    /**
//     * 将中文转化为拼音
//     */
//    public static void chineseToPinyin(List<FangKeBean.ObjectsBean> list) {
//        for (int i = 0; i < list.size(); i++) {
//            FangKeBean.ObjectsBean contactsModel1 = list.get(i);
//            //将汉字转换为拼音
//            String pinyinString = PinyinHelper.getShortPinyin(list.get(i).getName());
//            //将拼音字符串转换为大写拼音
//            String upperCasePinyinString = String.valueOf(pinyinString.charAt(0)).toUpperCase();
//            //获取大写拼音字符串的第一个字符
//            char tempChar = upperCasePinyinString.charAt(0);
//
//            if (tempChar < 'A' || tempChar > 'Z') {
//                contactsModel1.setFirstLetter("#");
//            } else {
//                // Log.d("TabFragment1", String.valueOf(tempChar));
//                contactsModel1.setFirstLetter(String.valueOf(tempChar));
//            }
//        }
//    }

//    private void paixu() {
//
//        //将联系人列表的标题字母排序
//        Collections.sort(dataList, new Comparator<FangKeBean.ObjectsBean>() {
//            @Override
//            public int compare(FangKeBean.ObjectsBean lhs, FangKeBean.ObjectsBean rhs) {
//                return lhs.getFirstLetter().compareTo(rhs.getFirstLetter());
//            }
//        });
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder1.unbind();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick({R.id.sousuo, R.id.tianjia,R.id.l1, R.id.l2, R.id.l3, R.id.l4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sousuo:
                startActivity(new Intent(getContext(), SouSuo2Activity.class));
                break;
            case R.id.tianjia:
                startActivity(new Intent(getContext(), XiuGaiFangKeActivity.class).putExtra("type", 1));
                break;
            case R.id.l1:
                setColoer();
                t1.setTextColor(Color.parseColor("#111111"));
                i1.setImageResource(R.drawable.xia2);
                dangQianYe = 1;
                qingQiuYe = 1;
                shenhe=0;
                lRecyclerView.forceToRefresh();

                break;
            case R.id.l2:
                setColoer();
                i2.setImageResource(R.drawable.xia2);
                t2.setTextColor(Color.parseColor("#111111"));
                dangQianYe = 1;
                qingQiuYe = 1;
                shenhe=1;
                lRecyclerView.forceToRefresh();

                break;
            case R.id.l3:
                setColoer();
                i3.setImageResource(R.drawable.xia2);
                t3.setTextColor(Color.parseColor("#111111"));
                dangQianYe = 1;
                qingQiuYe = 1;
                shenhe=2;
                lRecyclerView.forceToRefresh();

                break;
            case R.id.l4:
                setColoer();
                i4.setImageResource(R.drawable.xia2);
                t4.setTextColor(Color.parseColor("#111111"));
                dangQianYe = 1;
                qingQiuYe = 1;
                shenhe=-1;
                lRecyclerView.forceToRefresh();

                break;
        }
    }

    private void setColoer(){
        t1.setTextColor(Color.parseColor("#a7a6a6"));
        t2.setTextColor(Color.parseColor("#a7a6a6"));
        t3.setTextColor(Color.parseColor("#a7a6a6"));
        t4.setTextColor(Color.parseColor("#a7a6a6"));
        i1.setImageResource(R.drawable.xia);
        i2.setImageResource(R.drawable.xia);
        i3.setImageResource(R.drawable.xia);
        i4.setImageResource(R.drawable.xia);
    }

    private void link_liebiao(String name, int pageNum, int audit, boolean chaxun) {


        try {
            RequestBody body = null;
            if (chaxun) {

                body = new FormBody.Builder()
                        .add("accountId", baoCunBean.getSid())
                        .add("audit", audit + "")
                        .add("compareTimeStart", "2016-01-01 11:22")
                        .add("compareTimeEnd", DateUtils.time(System.currentTimeMillis() + ""))
                        .add("name", name)
                        .add("pageNum", pageNum + "")
                        .add("pageSize", "20")
                        .add("token", baoCunBean.getToken())
                        .build();
            } else {

                body = new FormBody.Builder()
                        .add("accountId", baoCunBean.getSid())
                        .add("compareTimeStart", "2016-01-01 11:22")
                        .add("compareTimeEnd", DateUtils.time(System.currentTimeMillis() + ""))
                        .add("name", name)
                        .add("pageNum", pageNum + "")
                        .add("pageSize", "20")
                        .add("token", baoCunBean.getToken())
                        .build();
            }


            Request.Builder requestBuilder = new Request.Builder()
                    // .header("Content-Type", "application/json")
                    .post(body)
                    .url(baoCunBean.getDizhi() + "/queryCompares.do");


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
                                // lRecyclerView.refreshComplete(0);// REQUEST_COUNT为每页加载数量
                                taiZhangAdapter.notifyDataSetChanged();

                                Utils.showToast(getContext(), "获取数据失败", 3);
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
                        Log.d("Fragment2", "请求数据成功"+ss);
                        JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                        Gson gson = new Gson();
                        final FangKeBean zhaoPianBean = gson.fromJson(jsonObject, FangKeBean.class);
                        if (qingQiuYe == dangQianYe) {

                            if (getActivity() != null) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (dataList.size() != 0) {
                                            dataList.clear();
                                        }
                                        dataList.addAll(zhaoPianBean.getObjects() != null ? zhaoPianBean.getObjects() : new ArrayList<FangKeBean.ObjectsBean>());
                                        taiZhangAdapter.setLetters();
                                        lRecyclerView.refreshComplete(dataList.size());// REQUEST_COUNT为每页加载数量
                                        taiZhangAdapter.notifyDataSetChanged();
                                        //   Log.d("Fragment3", "d进来得到");

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
                                        taiZhangAdapter.setLetters();
                                        lRecyclerView.refreshComplete(20);// REQUEST_COUNT为每页加载数量
                                        taiZhangAdapter.notifyDataSetChanged();

                                    }
                                });
                            }

                        }
                        if (zhaoPianBean.getObjects().size() == 0 && dataList.size() >= 20) {

                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    lRecyclerView.setNoMore(true);
                                }
                            });

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
                                            Utils.showToast(getContext(), "登陆过期,或账号在其它机器登陆,请重新登陆", 3);
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
                                    Utils.showToast(getContext(), "获取数据失败", 3);
                                }
                            });
                        }

                        Log.d("WebsocketPushMsg", e.getMessage());
                    }
                }
            });

        } catch (Exception e) {
            Log.d("Fragment1", e.getMessage() + "");
        }
    }


}
