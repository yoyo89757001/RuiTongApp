package com.example.ruitongapp.ui;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.FaceDetector;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.ruitongapp.MyApplication;
import com.example.ruitongapp.R;
import com.example.ruitongapp.adapters.PopupWindowAdapter;
import com.example.ruitongapp.beans.BaoCunBean;
import com.example.ruitongapp.beans.BaoCunBeanDao;
import com.example.ruitongapp.beans.BenDiYuanGong;
import com.example.ruitongapp.beans.BuMenBeans;
import com.example.ruitongapp.beans.FuWuQiBean;
import com.example.ruitongapp.beans.FuWuQiBeanDao;
import com.example.ruitongapp.beans.MoRenFanHuiBean;
import com.example.ruitongapp.beans.ShouFangBean;
import com.example.ruitongapp.beans.YuanGongBean;
import com.example.ruitongapp.dialogs.PaiZhaoDialog;
import com.example.ruitongapp.dialogs.PaiZhaoDialog2;
import com.example.ruitongapp.dialogs.QueRenDialog;
import com.example.ruitongapp.dialogs.TiJIaoDialog;
import com.example.ruitongapp.dialogs.UpdataTouXiangDialog;
import com.example.ruitongapp.utils.FileUtil;
import com.example.ruitongapp.utils.GlideCircleTransform;
import com.example.ruitongapp.utils.GsonUtil;
import com.example.ruitongapp.view.DividerItemDecoration;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.sdsmdg.tastytoast.TastyToast;

import org.parceler.Parcels;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;


public class XiuGaiYuanGongActivity extends Activity {
    @BindView(R.id.leftim)
    ImageView leftim;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.righttv)
    TextView righttv;
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.xingbie2)
    ImageView xingbie2;
    @BindView(R.id.xingbie)
    ImageView xingbie;
    @BindView(R.id.gonghao)
    EditText gonghao;
    @BindView(R.id.bumen)
    TextView bumen;
    @BindView(R.id.zhiwei)
    EditText zhiwei;
    @BindView(R.id.youxiang)
    EditText youxiang;
    @BindView(R.id.dianhua)
    EditText dianhua;
    @BindView(R.id.beizhu)
    EditText beizhu;
    @BindView(R.id.ruzhishijian)
    TextView ruzhishijian;
    @BindView(R.id.ruzhi)
    RelativeLayout ruzhi;
    @BindView(R.id.yuangongshengri)
    TextView yuangongshengri;
    @BindView(R.id.shengri)
    RelativeLayout shengri;
    @BindView(R.id.touxiang)
    ImageView touxiang;
    @BindView(R.id.r6)
    RelativeLayout r6;
    @BindView(R.id.shanchu)
    Button shanchu;
    private int type;


    //定义一个过滤器；
    private IntentFilter intentFilter;
    //定义一个广播监听器；
    private NetChangReceiver netChangReceiver;
    private TiJIaoDialog tiJIaoDialog=null;
    private BenDiYuanGong vipBean = null;
    private List<String> photosLists = new ArrayList<>();
    public final static int ALBUM_REQUEST_CODE = 1;
    public final static int ALBUM_REQUEST_CODE2 = 4;
    String cameraPath = null;
    private RecyclerView recyclerView;
    private PaiZhaoDialog dialog = null;
    private PaiZhaoDialog2 dialog2 = null;
    private int wz;
    private MyAdapter adapter = null;
    private File mFile;
    private static final int PHOTO_REQUEST_CUT = 9;
    private static final int PHOTO_REQUEST_CUT2 = 6;
    private UpdataTouXiangDialog dialog88 = null;
    private FuWuQiBeanDao fuWuQiBeanDao = null;
    private List<FuWuQiBean> fuWuQiBeanList = null;
    private FuWuQiBean fuWuQiBean = null;
    private int nannv = 0;
    private String touxiangPath="";
    private String shibiePaths="";
    private BaoCunBeanDao baoCunBeanDao=null;
    private BaoCunBean baoCunBean=null;
  //  private boolean isTiJiao=false;
    private String ss;
    private YuanGongBean.ObjectsBean benDiYuanGong=null;
    private PopupWindow popupWindow=null;
    private RelativeLayout r3;
    private List<String> stringList=new ArrayList<>();
    private int p1=-1;
    private PopupWindowAdapter adapterss;


    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 3) {
                if (dialog != null) {
                    dialog.dismiss();
                    dialog = null;
                }
                if (dialog2 != null) {
                    dialog2.dismiss();
                    dialog2 = null;
                }

                if (dialog88 != null) {
                    dialog88.dismiss();
                    dialog88 = null;
                }


            }

            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_xiu_gai_yuan_gong);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.titlecolor);
        }
        benDiYuanGong= Parcels.unwrap(getIntent().getParcelableExtra("chuansong"));
        baoCunBeanDao=MyApplication.myAppLaction.getDaoSession().getBaoCunBeanDao();
        if (baoCunBeanDao!=null){
            baoCunBean=baoCunBeanDao.load(123456L);
        }
        link_bumen();
        stringList.add("省公司领导");
        stringList.add("市公司领导");
        stringList.add("特邀嘉宾");
        stringList.add("技术部");
        r3= (RelativeLayout) findViewById(R.id.r3);
        r3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View contentView = LayoutInflater.from(XiuGaiYuanGongActivity.this).inflate(R.layout.xiangmu_po_item, null);
                popupWindow=new PopupWindow(contentView,400, 560);
                ListView listView= (ListView) contentView.findViewById(R.id.dddddd);
                adapterss=new PopupWindowAdapter(XiuGaiYuanGongActivity.this,stringList);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        p1=position;
                        bumen.setText(stringList.get(position));
                        popupWindow.dismiss();
                    }
                });
                listView.setAdapter(adapterss);

                popupWindow.setFocusable(true);//获取焦点
                popupWindow.setOutsideTouchable(true);//获取外部触摸事件
                popupWindow.setTouchable(true);//能够响应触摸事件
                popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));//设置背景
                popupWindow.showAsDropDown(r3,bumen.getRight(),0);
            }
        });


        fuWuQiBeanDao = MyApplication.myAppLaction.getDaoSession().getFuWuQiBeanDao();
        if (fuWuQiBeanDao != null) {
            fuWuQiBeanList = fuWuQiBeanDao.loadAll();
            for (int i = 0; i < fuWuQiBeanList.size(); i++) {
                if (fuWuQiBeanList.get(i).getIsTrue()) {
                    fuWuQiBean = fuWuQiBeanList.get(i);
                }
            }
        }

        type = getIntent().getIntExtra("type", 0);
        if (type == 1) {
            title.setText("添加员工");
            shanchu.setVisibility(View.GONE);
            for (int i = 3; i > 0; i--) {

                photosLists.add("http://192.168.168.168.rui");
                if (photosLists.size() == 3) {
                    break;
                }
            }

            Message message = new Message();
            message.what = 3;
            message.obj = vipBean;
            handler.sendMessage(message);

        } else {
            title.setText("员工信息");
        }
        righttv.setVisibility(View.VISIBLE);
        righttv.setText("保 存");

        mFile = new File(getExternalFilesDir(null), "pic.jpg");
        //实例化过滤器；
        intentFilter = new IntentFilter();
        //添加过滤的Action值；
        intentFilter.addAction("shangchuanzhaopian");
        intentFilter.addAction("shangchuanzhaopian2");
        intentFilter.addAction("paizhang_sb");
        intentFilter.addAction("paizhang_tx");
        //实例化广播监听器；
        netChangReceiver = new NetChangReceiver();
        //将广播监听器和过滤器注册在一起；
        registerReceiver(netChangReceiver, intentFilter);

        recyclerView = (RecyclerView) findViewById(R.id.recylerview);
        adapter = new MyAdapter(R.layout.fggg, photosLists);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(final BaseQuickAdapter adapter, View view, int position) {
                wz = position;
                // 有-1表示是空白照片的项。
                if (photosLists.get(position).equals("http://192.168.168.168.rui")) {
                    dialog2 = new PaiZhaoDialog2(XiuGaiYuanGongActivity.this);
                    dialog2.setOnUpdataXiangceListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            xiangce(88);
                        }
                    });
                    dialog2.setOnUpdataPaiZhaoListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // paizhao();
                            startActivity(new Intent(XiuGaiYuanGongActivity.this, PaiZhaoActivity.class));
                        }
                    });

                    dialog2.show();
                } else {
                    //有照片的项。
                    dialog = new PaiZhaoDialog(XiuGaiYuanGongActivity.this);
                    dialog.setOnUpdataXiangceListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            xiangce(88);
                        }
                    });
                    dialog.setOnUpdataPaiZhaoListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //  paizhao();

                            startActivity(new Intent(XiuGaiYuanGongActivity.this, PaiZhaoActivity.class));
                        }
                    });
                    dialog.setOnDeleterListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //删除照片
                            int pp = photosLists.size();
                            for (int i = 0; i < pp; i++) {
                                if (wz == i) {
                                    photosLists.remove(wz);
                                    break;
                                }
                            }
                            StringBuilder buffer = new StringBuilder();
                            for (int i = 0; i < photosLists.size(); i++) {
                                buffer.append(photosLists.get(i));
                                buffer.append(",");
                            }
                            String str = buffer.toString();
                            int indx = str.lastIndexOf(",");
                            if (indx != -1) {
                                str = str.substring(0, indx) + str.substring(indx + 1, str.length());
                            }
                          //  Log.d("UserInfoActivity", str + "删除得得得");
                            shibiePaths=str;
                            String s[] =shibiePaths.split(",");
                            int sss=s.length;
                            if (photosLists.size()!=0){
                                photosLists.clear();
                            }
                            if (sss<3){
                                for (int k=0;k<3;k++){
                                    if (k<=sss-1){
                                        photosLists.add(s[k]);
                                    }else {
                                        photosLists.add("http://192.168.168.168.rui");
                                    }
                                }
                                adapter.notifyDataSetChanged();
                                dialog.dismiss();

                            }else {

                                Collections.addAll(photosLists, s);
                                adapter.notifyDataSetChanged();
                                dialog.dismiss();
                            }
                        }
                    });
                    dialog.show();
                }
            }
        });


        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        //  recyclerView.addItemDecoration(new MyDecoration(this,LinearLayoutManager.HORIZONTAL));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL));

        if (benDiYuanGong != null) {
            name.setText(benDiYuanGong.getName());
            if (benDiYuanGong.getGender() == 0) {
                xingbie.setImageResource(R.drawable.ic_select);
                xingbie2.setImageResource(R.drawable.ic_select);
            } else if (benDiYuanGong.getGender() == 1) {
                xingbie.setImageResource(R.drawable.ic_selected);
                xingbie2.setImageResource(R.drawable.ic_select);
            } else {
                xingbie.setImageResource(R.drawable.ic_select);
                xingbie2.setImageResource(R.drawable.ic_selected);
            }
            gonghao.setText(benDiYuanGong.getJob_number());
            bumen.setText(benDiYuanGong.getDepartment());
            zhiwei.setText(benDiYuanGong.getTitle());
            dianhua.setText(benDiYuanGong.getPhone());
            beizhu.setText(benDiYuanGong.getRemark());
            ruzhishijian.setText(benDiYuanGong.getEntry_date2());
            if (benDiYuanGong.getAvatar()!=null && !benDiYuanGong.getAvatar().equals("")){
                Glide.with(XiuGaiYuanGongActivity.this)
                        .load(fuWuQiBean.getDizhi() + "/upload/avatar/" + benDiYuanGong.getAvatar())
                        .transform(new GlideCircleTransform(XiuGaiYuanGongActivity.this, 0.6f, Color.parseColor("#ffffffff")))
                        .into(touxiang);
            }else {
                Glide.with(XiuGaiYuanGongActivity.this)
                        .load(R.drawable.morentouxiang)
                        .transform(new GlideCircleTransform(XiuGaiYuanGongActivity.this, 0.6f, Color.parseColor("#ffffffff")))
                        .into(touxiang);
            }
            youxiang.setText(benDiYuanGong.getEmail());
            if (!benDiYuanGong.getPhoto_ids().equals("")){
                String s[] =benDiYuanGong.getPhoto_ids().split(",");
                int ss=s.length;
                if (ss<3){
                    for (int k=0;k<3;k++){
                        if (k<=ss-1){
                            photosLists.add(s[k]);
                        }else {
                            photosLists.add("http://192.168.168.168.rui");
                        }
                    }

                    adapter.notifyDataSetChanged();
                }else {

                    Collections.addAll(photosLists, s);
                    adapter.notifyDataSetChanged();
                }
                shibiePaths=benDiYuanGong.getPhoto_ids();
                touxiangPath=benDiYuanGong.getAvatar();
                yuangongshengri.setText(benDiYuanGong.getBirthday2());


            }

        }

    }



    private class NetChangReceiver extends BroadcastReceiver {

        //重写onReceive方法，该方法的实体为，接收到广播后的执行代码；
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("shangchuanzhaopian")) {
                cameraPath = intent.getStringExtra("path");
                addPhoto(1);
            } else if (intent.getAction().equals("shangchuanzhaopian2")) {
                cameraPath = intent.getStringExtra("path");
                addPhoto222(1);
            }
            if (intent.getAction().equals("paizhang_sb")) {
                cameraPath = intent.getStringExtra("path");
                addPhoto(1);
            }
            if (intent.getAction().equals("paizhang_tx")) {
                cameraPath = intent.getStringExtra("path");
                addPhoto222(1);
            }
        }
    }


    @OnClick({R.id.xingbie2, R.id.xingbie, R.id.ruzhi, R.id.shengri, R.id.r6, R.id.leftim, R.id.righttv,R.id.shanchu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.xingbie2: //女

                nannv = 2;
                xingbie.setImageResource(R.drawable.ic_select);
                xingbie2.setImageResource(R.drawable.ic_selected);

                break;
            case R.id.xingbie:

                nannv = 1;
                xingbie2.setImageResource(R.drawable.ic_select);
                xingbie.setImageResource(R.drawable.ic_selected);

                break;
            case R.id.ruzhi:

                Intent intent = new Intent(XiuGaiYuanGongActivity.this, DatePickActivity.class).putExtra("yyy",1);
                startActivityForResult(intent, 2);

                break;
            case R.id.shengri:
                Intent intent2 = new Intent(XiuGaiYuanGongActivity.this, DatePickActivity.class).putExtra("yyy",2);
                startActivityForResult(intent2, 2);

                break;
            case R.id.r6: //头像

                dialog88 = new UpdataTouXiangDialog(XiuGaiYuanGongActivity.this);
                dialog88.setOnUpdataPaiZhaoListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) { //拍照
                        startActivity(new Intent(XiuGaiYuanGongActivity.this, PaiZhaoActivity2.class));
                    }
                });
                dialog88.setOnUpdataXiangceListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) { //相册
                        xiangce(99);
                    }
                });
                dialog88.show();
                break;
            case R.id.leftim:
                finish();
                break;
            case R.id.righttv:
                //保存
                if (!name.getText().toString().trim().equals("") && !shibiePaths.equals("") && p1!=-1){
                    link_gengxinTuPian();
                }else {
                    TastyToast.makeText(getApplicationContext(),
                            "请填写完整信息!", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                }


                break;
            case R.id.shanchu:
                //删除
                final QueRenDialog dialog=new QueRenDialog(XiuGaiYuanGongActivity.this);
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

    private class MyAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


        private MyAdapter(int layoutResId, List<String> data) {
            super(layoutResId, data);

        }


        @Override
        protected void convert(BaseViewHolder helper, String item) {

            if (item.equals("http://192.168.168.168.rui")) {
                helper.setText(R.id.fffff, "添加照片");

                Glide.with(XiuGaiYuanGongActivity.this)
                        .load(R.drawable.tuijianyisheng)
//                    .bitmapTransform(new GlideCircleTransform(MyApplication.getAppContext()))
                        .into((ImageView) helper.getView(R.id.mmmmm));

            } else {
                Log.d(TAG, fuWuQiBean.getDizhi() + "/upload/compare/" + item);
                helper.setText(R.id.fffff, "修改照片");
                Glide.with(XiuGaiYuanGongActivity.this)
                        .load(fuWuQiBean.getDizhi() + "/upload/compare/" + item)
//                    .bitmapTransform(new GlideCircleTransform(MyApplication.getAppContext()))
                        .error(R.drawable.tuijianyisheng)
                        .into((ImageView) helper.getView(R.id.mmmmm));

            }

        }

    }

//    private void link(int id) {
//
//        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
//
//        OkHttpClient okHttpClient = new OkHttpClient();
//
//        RequestBody body = new FormBody.Builder()
//                .add("id", id + "")
//                .build();
//        Request.Builder requestBuilder = new Request.Builder()
//                // .header("Content-Type", "application/json")
//                .post(body)
//                .url(fuWuQiBean.getDizhi() + "/getSubject.do");
//        //    .url(HOST+"/mobile-admin/subjects");
//
//        // step 3：创建 Call 对象
//        Call call = okHttpClient.newCall(requestBuilder.build());
//
//        //step 4: 开始异步请求
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.d("AllConnects", "请求失败" + e.getMessage());
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                try {
//
//                    Log.d("AllConnects", "请求成功" + call.request().toString());
//                    //获得返回体
//                    ResponseBody body = response.body();
//
//                    String ss = body.string();
//                    Log.d("AllConnects", ss);
//                    JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
//                    Gson gson = new Gson();
//                    if (photosLists.size() > 0) {
//                        photosLists.clear();
//                    }
//
//                    //vipBean=gson.fromJson(jsonObject,EmployeesInfoBean.class);
//                    String s = vipBean.getPhoto_ids();
//                    if (null != s && !s.equals("")) {
//                        String p[] = s.split(",");
//                        photoSize = p.length;
//                        for (int i = 0; i < photoSize; i++) {
//                            photosLists.add(p[i]);
//                        }
//                    }
//                    int pp = photosLists.size();
//                    if (pp < 3) {
//                        for (int i = 3; i > pp; i--) {
//
//                            photosLists.add("http://192.168.168.168");
//                            if (photosLists.size() == 3) {
//                                break;
//                            }
//                        }
//                    }
//
//
//                    Message message = new Message();
//                    message.what = 3;
//                    message.obj = vipBean;
//                    handler.sendMessage(message);
//
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//        });
//
//    }

    private void addPhoto(final int type) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (dialog != null) {
                    dialog.setL1Gone();
                }
                if (dialog2 != null) {
                    dialog2.setL1Gone();
                }

            }
        });

        if (cameraPath!=null) {
            try {

                Luban.with(this)
                        .load(cameraPath)   // 传人要压缩的图片列表
                        .ignoreBy(100)   // 忽略不压缩图片的大小
                        .setTargetDir(FileUtil.SDPATH + File.separator + FileUtil.PATH + File.separator)  // 设置压缩后文件存储位置
                        .setCompressListener(new OnCompressListener() { //设置回调
                            @Override
                            public void onStart() {
                                Log.d("BaoZhangDengJiActivity", "开始压缩");
                            }

                            @Override
                            public void onSuccess(File file) {

                                OkHttpClient okHttpClient = MyApplication.getOkHttpClient();
                                RequestBody fileBody1 = null;
                                String file1Name = null;
                              /* 第一个要上传的file */

                                    fileBody1 = RequestBody.create(MediaType.parse("application/octet-stream"), file);
                                    //  file1Name = System.currentTimeMillis()+"testFile1.jpg";
                                    file1Name = System.currentTimeMillis() + "testFile1.jpg";


                            //    /* 第二个要上传的文件,这里偷懒了,和file1用的一个图片 */
                            //        File file2 = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/a.jpg");
                            //        RequestBody fileBody2 = RequestBody.create(MediaType.parse("application/octet-stream") , file2);
                            //        String file2Name = "testFile2.txt";


                            //    /* form的分割线,自己定义 */
                    //        String boundary = "xx--------------------------------------------------------------xx";

                                MultipartBody mBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
            /* 底下是上传了两个文件 */
                                        .addFormDataPart("voiceFile", file1Name, fileBody1)
                  /* 上传一个普通的String参数 */
                                        //  .addFormDataPart("subject_id" , subject_id+"")
//                .addFormDataPart("file" , file2Name , fileBody2)
                                        .build();
                                Request.Builder requestBuilder = new Request.Builder()
                                        //  .header("Content-Type", "application/json")
                                        .post(mBody)
                                        .url(fuWuQiBean.getDizhi() + "/AppFileUploadServlet?FilePathPath=compareFilePath&AllowFileType=.jpg,.gif,.jpeg,.bmp,.png&MaxFileSize=10");
                                //    .url(HOST+"/mobile-admin/subjects");

                                // step 3：创建 Call 对象
                                Call call = okHttpClient.newCall(requestBuilder.build());
                                //step 4: 开始异步请求
                                call.enqueue(new Callback() {
                                    @Override
                                    public void onFailure(Call call, IOException e) {
                                        Log.d("AllConnects", "照片上传失败" + e.getMessage());
                                        cameraPath = null;
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {

                                                if (dialog != null) {
                                                    dialog.dismiss();
                                                    dialog = null;
                                                }
                                                if (dialog2 != null) {
                                                    dialog2.dismiss();
                                                    dialog2 = null;
                                                }

                                            }
                                        });
                                    }

                                    @Override
                                    public void onResponse(Call call, Response response) throws IOException { //识别照片
                                        Log.d("AllConnects", "照片上传成功" + call.request().toString());
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {

                                                if (dialog != null) {
                                                    dialog.dismiss();
                                                    dialog = null;
                                                }
                                                if (dialog2 != null) {
                                                    dialog2.dismiss();
                                                    dialog2 = null;
                                                }

                                            }
                                        });
                                        try {

                                            cameraPath = null;
                                            //获得返回体
                                            ResponseBody body = response.body();
                                            String ss=body.string();
                                            Log.d("AllConnects", "aa   "+ss);
                                            JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                                            // Gson gson=new Gson();
                                            int code = jsonObject.get("exId").getAsInt();
                                            if (code == 0) {
                                                String array = jsonObject.get("exDesc").getAsString();
                                                link_zhiliang(array);

                                            } else {

                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {

                                                        if (dialog != null) {
                                                            dialog.dismiss();
                                                            dialog = null;
                                                        }
                                                        if (dialog2 != null) {
                                                            dialog2.dismiss();
                                                            dialog2 = null;
                                                        }
                                                        TastyToast.makeText(getApplicationContext(),
                                                                "上传失败", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                                    }
                                                });

                                            }
                                        }catch (Exception e){
                                            Log.d("XiuGaiYuanGongActivity", e.getMessage()+"");
                                        }

                                    }
                                });

                            }

                            @Override
                            public void onError(Throwable e) {

                                showMSG("压缩图片出现错误"+e, 4);
                            }
                        }).launch();    //启动压缩

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    private void showMSG(final String s,final int i){

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                Toast tastyToast= TastyToast.makeText(XiuGaiYuanGongActivity.this,s,TastyToast.LENGTH_LONG,i);
                tastyToast.setGravity(Gravity.CENTER,0,0);
                tastyToast.show();

            }
        });
    }


    private void addPhoto222(final int type) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (dialog88 != null) {
                    dialog88.setL1Gone();
                    if (!XiuGaiYuanGongActivity.this.isFinishing())
                    dialog88.show();
                }

            }
        });


        OkHttpClient okHttpClient =MyApplication.getOkHttpClient();
        RequestBody fileBody1 = null;
        String file1Name = null;
         /* 第一个要上传的file */
        if (cameraPath != null) {
            File file1 = new File(cameraPath);
            fileBody1 = RequestBody.create(MediaType.parse("application/octet-stream"), file1);
            file1Name = System.currentTimeMillis() + "testFile1.jpg";
        }

//    /* 第二个要上传的文件,这里偷懒了,和file1用的一个图片 */
//        File file2 = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/a.jpg");
//        RequestBody fileBody2 = RequestBody.create(MediaType.parse("application/octet-stream") , file2);
//        String file2Name = "testFile2.txt";


//    /* form的分割线,自己定义 */
//        String boundary = "xx--------------------------------------------------------------xx";

        MultipartBody mBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
            /* 底下是上传了两个文件 */
                .addFormDataPart("voiceFile", file1Name, fileBody1)
                  /* 上传一个普通的String参数 */
                // .addFormDataPart("subject_id" , id+"")
//                .addFormDataPart("file" , file2Name , fileBody2)
                .build();
        Request.Builder requestBuilder = new Request.Builder()
                // .header("Content-Type", "application/json")
                .post(mBody)
                .url(fuWuQiBean.getDizhi() + "/AppFileUploadServlet?FilePathPath=avatarFilePath&AllowFileType=.jpg,.gif,.jpeg,.bmp,.png&MaxFileSize=10");
        //    .url(HOST+"/mobile-admin/subjects");

        // step 3：创建 Call 对象
        Call call = okHttpClient.newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "头像上传失败" + e.getMessage());
                cameraPath = null;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!XiuGaiYuanGongActivity.this.isFinishing() && dialog88!=null && dialog88.isShowing()){
                            dialog88.dismiss();
                            dialog88=null;
                        }
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!XiuGaiYuanGongActivity.this.isFinishing() && dialog88!=null && dialog88.isShowing()){
                            dialog88.dismiss();
                            dialog88=null;
                        }
                    }
                });
                Log.d("AllConnects", "头像上传成功" + call.request().toString());
                try {


                cameraPath = null;
                //获得返回体
                ResponseBody body = response.body();
                String ss = body.string();
                Log.d("AllConnects", "aa   " + ss);
                JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                Gson gson = new Gson();
                int code = jsonObject.get("exId").getAsInt();
                if (code == 0) {

                    touxiangPath= jsonObject.get("exDesc").getAsString();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Glide.with(XiuGaiYuanGongActivity.this)
                                    .load(fuWuQiBean.getDizhi() + "/upload/avatar/" + touxiangPath)
                                    .transform(new GlideCircleTransform(XiuGaiYuanGongActivity.this, 0.6f, Color.parseColor("#ffffffff")))
                                    .into(touxiang);
                        }
                    });

                    //link_gengxinTuPian();

                } else {


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (!XiuGaiYuanGongActivity.this.isFinishing() && dialog88!=null && dialog88.isShowing()){
                                        dialog88.dismiss();
                                        dialog88=null;
                                    }
                                }
                            });
                            TastyToast.makeText(getApplicationContext(),
                                    "上传头像失败", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                        }
                    });
                }
                }catch (Exception e){
                    Log.d("XiuGaiYuanGongActivity", e.getMessage()+"");
                }
            }
        });

    }

    private void link_gengxinTuPian() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (tiJIaoDialog==null && !XiuGaiYuanGongActivity.this.isFinishing()){
                    tiJIaoDialog=new TiJIaoDialog(XiuGaiYuanGongActivity.this);
                    tiJIaoDialog.show();
                }
            }
        });
//        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        OkHttpClient okHttpClient = new OkHttpClient();

        RequestBody body=null;
        if (type==1){
             //添加
            body= new FormBody.Builder()
                    .add("name", name.getText().toString().trim())
                    .add("subject_type", 0 + "")
                    .add("department", bumen.getText().toString().trim())
                    .add("title", zhiwei.getText().toString().trim())
                    .add("email", youxiang.getText().toString().trim())
                    .add("job_number",gonghao.getText().toString().trim())
                    .add("phone", dianhua.getText().toString().trim())
                    .add("entry_date2", ruzhishijian.getText().toString().trim())
                    .add("birthday2",yuangongshengri.getText().toString().trim())
                    //.add("description", beizhu.getText().toString().trim())
                    .add("remark", beizhu.getText().toString().trim())
                    .add("gender", nannv+"")
                    .add("avatar", touxiangPath==null?"":touxiangPath)
                    .add("photo_ids", shibiePaths + "")
                    .add("token",baoCunBean.getToken())
                    .add("accountId",baoCunBean.getSid())
                    .build();
          //  Log.d("XiuGaiYuanGongActivity", "添加");
        }else {
            //修改
             body = new FormBody.Builder()
                    .add("id", benDiYuanGong.getId()+"")
                    .add("name", name.getText().toString().trim())
                    .add("subject_type", 0 + "")
                    .add("department", bumen.getText().toString().trim())
                    .add("title", zhiwei.getText().toString().trim())
                    .add("email", youxiang.getText().toString().trim())
                    .add("job_number",gonghao.getText().toString().trim())
                    .add("phone", dianhua.getText().toString().trim())
                    .add("entry_date2", ruzhishijian.getText().toString().trim())
                    .add("birthday2",yuangongshengri.getText().toString().trim())
                    //.add("description", beizhu.getText().toString().trim())
                    .add("remark", beizhu.getText().toString().trim())
                    .add("gender", nannv+"")
                    .add("avatar", touxiangPath+ "")
                    .add("photo_ids", shibiePaths+ "")
                     .add("token",baoCunBean.getToken())
                     .add("accountId",baoCunBean.getSid())
                    .build();
         //   Log.d("XiuGaiYuanGongActivity", "修改");
        }


        Request.Builder requestBuilder = new Request.Builder()
                // .header("Content-Type", "application/json")
                .post(body)
                .url(fuWuQiBean.getDizhi() + "/addSubject.do");

        // step 3：创建 Call 对象
        Call call = okHttpClient.newCall(requestBuilder.build());

        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TastyToast.makeText(XiuGaiYuanGongActivity.this, "网络错误,请求失败", Toast.LENGTH_LONG, TastyToast.INFO).show();
                        if (tiJIaoDialog != null && !XiuGaiYuanGongActivity.this.isFinishing() && tiJIaoDialog.isShowing()) {
                            tiJIaoDialog.dismiss();
                            tiJIaoDialog = null;
                        }
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("AllConnects", "请求成功" + call.request().toString());
                try {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (tiJIaoDialog != null && !XiuGaiYuanGongActivity.this.isFinishing() && tiJIaoDialog.isShowing()) {
                                tiJIaoDialog.dismiss();
                                tiJIaoDialog = null;
                            }
                        }
                    });

                    //获得返回体
                    ResponseBody body = response.body();
                     ss=body.string();
                     // Log.d("AllConnects", "aa   "+ss);
                    JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
//                Gson gson=new Gson();
                    int code = jsonObject.get("dtoResult").getAsInt();
                    if (code == 0) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                TastyToast.makeText(XiuGaiYuanGongActivity.this, "修改成功！", Toast.LENGTH_LONG, TastyToast.INFO).show();
                               // sendBroadcast(new Intent("shuaxing"));
                                finish();
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                TastyToast.makeText(XiuGaiYuanGongActivity.this, "修改失败", Toast.LENGTH_LONG, TastyToast.INFO).show();
                            }
                        });
                    }

                } catch (Exception e) {
                    dengLuGuoQi(ss);
                    e.printStackTrace();

                }

            }
        });


    }

    @Override
    public void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        //销毁Activity时取消注册广播监听器；
        unregisterReceiver(netChangReceiver);

        super.onDestroy();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == 2) {
            // 选择预约时间的页面被关闭
            String date = data.getStringExtra("date");
            int yyy=data.getIntExtra("yyy",0);
            if (yyy==1){
                ruzhishijian.setText(date);
            }else {
                yuangongshengri.setText(date);
            }

        }



        //相册，更新识别照片
        if (requestCode == ALBUM_REQUEST_CODE) {
            try {
                Uri uri = data.getData();
                final String absolutePath = getAbsolutePath(XiuGaiYuanGongActivity.this, uri);
                if (absolutePath != null) {
                    cameraPath = absolutePath;
                    Intent intent = new Intent("com.android.camera.action.CROP");
                    intent.setDataAndType(uri, "image/*");
                    //下面这个crop=true是设置在开启的Intent中设置显示的view可裁剪
                    intent.putExtra("crop", "true");
                    // aspectX aspectY 是宽高的比例
                    intent.putExtra("aspectX", 1);
                    intent.putExtra("aspectY", 1);
                    // outputX outputY 是裁剪图片宽高
                    intent.putExtra("outputX", 600);
                    intent.putExtra("outputY", 600);
                    //是否将数据保留在Bitmap中返回
                    intent.putExtra("return-data", false);
                    // 当图片的宽高不足时，会出现黑边，去除黑边
                    intent.putExtra("scale", true);
                    intent.putExtra("scaleUpIfNeeded", true);
                    // intent.putExtra("noFaceDetection", false);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mFile));
                    intent.putExtra("outputFormat", "JPEG");// 返回格式
                    startActivityForResult(intent, PHOTO_REQUEST_CUT);

                    // addPhoto(1);
                } else {
                    TastyToast.makeText(getApplicationContext(),
                            "请选择(相册图库)", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                }

                // Log.d("dddd","path=" + absolutePath);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //相册，更新头像
        if (requestCode == ALBUM_REQUEST_CODE2) {
            try {
                Uri uri = data.getData();
                final String absolutePath = getAbsolutePath(XiuGaiYuanGongActivity.this, uri);
                if (absolutePath != null) {
                    cameraPath = absolutePath;
                    Intent intent = new Intent("com.android.camera.action.CROP");
                    intent.setDataAndType(uri, "image/*");
                    //下面这个crop=true是设置在开启的Intent中设置显示的view可裁剪
                    intent.putExtra("crop", "true");
                    // aspectX aspectY 是宽高的比例
                    intent.putExtra("aspectX", 1);
                    intent.putExtra("aspectY", 1);
                    // outputX outputY 是裁剪图片宽高
                    intent.putExtra("outputX", 800);
                    intent.putExtra("outputY", 800);
                    //是否将数据保留在Bitmap中返回
                    intent.putExtra("return-data", false);
                    // 当图片的宽高不足时，会出现黑边，去除黑边
                    intent.putExtra("scale", true);
                    intent.putExtra("scaleUpIfNeeded", true);
                    // intent.putExtra("noFaceDetection", false);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mFile));
                    intent.putExtra("outputFormat", "JPEG");// 返回格式
                    startActivityForResult(intent, PHOTO_REQUEST_CUT2);

                } else {
                    TastyToast.makeText(getApplicationContext(),
                            "请选择(相册图库)", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //裁剪识别照片
        if (requestCode == PHOTO_REQUEST_CUT && resultCode == RESULT_OK && data != null) { //裁剪
            //imagview来显示裁剪后的图片
            if (mFile != null) {

                //因为这是一个耗时的操作，所以放到另一个线程中运行
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        FaceDetector.Face[] faces = new FaceDetector.Face[3];
                        //格式必须为RGB_565才可以识别
                        Bitmap bmp = BitmapFactory.decodeFile(mFile.getPath()).copy(Bitmap.Config.RGB_565, true);
                        //返回识别的人脸数
                        int faceCount = new FaceDetector(bmp.getWidth(), bmp.getHeight(), 3).findFaces(bmp, faces);
                        bmp.recycle();
                        bmp = null;
                        //  Log.e("tag", "识别的人脸数:" + faceCount);

                        if (faceCount <= 0) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast tastyToast = TastyToast.makeText(XiuGaiYuanGongActivity.this, "没有检测到人脸,请重新选择", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                    tastyToast.setGravity(Gravity.CENTER, 0, 0);
                                    tastyToast.show();
                                }
                            });
                        } else {
                            cameraPath = mFile.getAbsolutePath();
                            addPhoto(1);
                        }
                    }
                }).start();

            }
        }

        //裁剪头像
        if (requestCode == PHOTO_REQUEST_CUT2 && resultCode == RESULT_OK && data != null) { //裁剪

            if (mFile != null) {

                cameraPath = mFile.getAbsolutePath();
                addPhoto222(1);

            }
        }
    }


//    private void paizhao(){
//        // 指定相机拍摄照片保存地址
//        String state = Environment.getExternalStorageState();
//        if (state.equals(Environment.MEDIA_MOUNTED)) {
//            cameraPath = SAVED_IMAGE_DIR_PATH + System.currentTimeMillis() + ".jpg";
//            Intent intent = new Intent();
//            // 指定开启系统相机的Action
//            intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
//            String out_file_path = SAVED_IMAGE_DIR_PATH;
//            File dir = new File(out_file_path);
//            if (!dir.exists()) {
//                dir.mkdir();
//            } // 把文件地址转换成Uri格式
//            Uri uri = Uri.fromFile(new File(cameraPath));
//            // 设置系统相机拍摄照片完成后图片文件的存放地址
//            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//            // 此值在最低质量最小文件尺寸时是0，在最高质量最大文件尺寸时是１
//            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0.6);
//            startActivityForResult(intent, CAMERA_REQUEST_CODE);
//        } else {
//            Toast.makeText(getApplicationContext(), "请确认已经插入SD卡",
//                    Toast.LENGTH_LONG).show();
//        }
//    }

    private void xiangce(int type) {
        if (type == 99) {
            //更新头像
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, ALBUM_REQUEST_CODE2);

        } else {
            //更新识别照片
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, ALBUM_REQUEST_CODE);
        }


    }

    public String getAbsolutePath(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri,
                    new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    private void link_zhiliang(final String path) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (tiJIaoDialog==null && !XiuGaiYuanGongActivity.this.isFinishing()){
                    tiJIaoDialog=new TiJIaoDialog(XiuGaiYuanGongActivity.this);
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
                    .add("scanPhoto", path)
                    .add("accountId", baoCunBean.getSid())
                    .build();


            Request.Builder requestBuilder = new Request.Builder()
                    // .header("Content-Type", "application/json")
                    .post(body)
                    .url(baoCunBean.getDizhi() + "/faceQuality.do");

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
                            if (tiJIaoDialog != null && !XiuGaiYuanGongActivity.this.isFinishing() && tiJIaoDialog.isShowing()) {
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

                        if (zhaoPianBean.getDtoResult() != 0) {

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    if (!XiuGaiYuanGongActivity.this.isFinishing()) {
                                        Toast tastyToast = TastyToast.makeText(XiuGaiYuanGongActivity.this, "照片质量不符合入库要求,请拍正面照!", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                        tastyToast.setGravity(Gravity.CENTER, 0, 0);
                                        tastyToast.show();
                                    }


                                }
                            });

                        } else {
                            int sz = photosLists.size();
                            StringBuilder buffer = new StringBuilder();

                            for (int i = 0; i < sz; i++) {
                                if (photosLists.get(i).contains(".jpg") && wz != i) {
                                    buffer.append(photosLists.get(i));
                                    buffer.append(",");
                                } else {
                                    if (wz == i) {
                                        buffer.append(path);
                                        buffer.append(",");
                                    }
                                }

                            }
                            String str = buffer.toString();
                            int indx = str.lastIndexOf(",");
                            if (indx != -1) {
                                str = str.substring(0, indx) + str.substring(indx + 1, str.length());
                            }
                            shibiePaths=str;

                                String s[] =shibiePaths.split(",");
                                int sss=s.length;
                            if (photosLists.size()!=0){
                                photosLists.clear();
                            }
                                if (sss<3){
                                    for (int k=0;k<3;k++){
                                        if (k<=sss-1){
                                            photosLists.add(s[k]);
                                        }else {
                                            photosLists.add("http://192.168.168.168.rui");
                                        }
                                    }
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            adapter.notifyDataSetChanged();
                                        }
                                    });

                                }else {

                                    Collections.addAll(photosLists, s);
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            adapter.notifyDataSetChanged();
                                        }
                                    });

                                }
                           // Log.d("UserInfoActivity", str);
                            //isTiJiao = true;
                        }

                    } catch (Exception e) {
                        dengLuGuoQi(ss);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (tiJIaoDialog != null && !XiuGaiYuanGongActivity.this.isFinishing()) {
                                    tiJIaoDialog.dismiss();
                                    tiJIaoDialog = null;
                                }
                            }
                        });
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                Toast tastyToast = TastyToast.makeText(XiuGaiYuanGongActivity.this, "提交失败,请检查网络", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                tastyToast.setGravity(Gravity.CENTER, 0, 0);
                                tastyToast.show();

                            }
                        });
                        Log.d("WebsocketPushMsg", e.getMessage());
                    }
                }
            });
        }else {
            Toast tastyToast = TastyToast.makeText(XiuGaiYuanGongActivity.this, "账户ID为空!,请设置帐户ID", TastyToast.LENGTH_LONG, TastyToast.ERROR);
            tastyToast.setGravity(Gravity.CENTER, 0, 0);
            tastyToast.show();
        }


    }

    private void link_delect() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (tiJIaoDialog==null && !XiuGaiYuanGongActivity.this.isFinishing()){
                    tiJIaoDialog=new TiJIaoDialog(XiuGaiYuanGongActivity.this);
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
                    .url(baoCunBean.getDizhi() + "/delSubject.do");

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
                            if (tiJIaoDialog != null && !XiuGaiYuanGongActivity.this.isFinishing() && tiJIaoDialog.isShowing()) {
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

                                    if (!XiuGaiYuanGongActivity.this.isFinishing()) {
                                        Toast tastyToast = TastyToast.makeText(XiuGaiYuanGongActivity.this, "删除成功", TastyToast.LENGTH_LONG, TastyToast.INFO);
                                        tastyToast.setGravity(Gravity.CENTER, 0, 0);
                                        tastyToast.show();
                                        finish();
                                       // sendBroadcast(new Intent("shanchu"));
                                    }


                                }
                            });

                        } else {

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    if (!XiuGaiYuanGongActivity.this.isFinishing()) {
                                        Toast tastyToast = TastyToast.makeText(XiuGaiYuanGongActivity.this, "删除失败", TastyToast.LENGTH_LONG, TastyToast.ERROR);
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
                                if (tiJIaoDialog != null && !XiuGaiYuanGongActivity.this.isFinishing()) {
                                    tiJIaoDialog.dismiss();
                                    tiJIaoDialog = null;
                                }
                            }
                        });

                        Log.d("WebsocketPushMsg", e.getMessage());
                    }
                }
            });
        }else {
            Toast tastyToast = TastyToast.makeText(XiuGaiYuanGongActivity.this, "账户ID为空!,请设置帐户ID", TastyToast.LENGTH_LONG, TastyToast.ERROR);
            tastyToast.setGravity(Gravity.CENTER, 0, 0);
            tastyToast.show();
        }
    }

    private void link_bumen() {

        //final MediaType JSON=MediaType.parse("application/json; charset=utf-8");
        //http://192.168.2.4:8080/sign?cmd=getUnSignList&subjectId=jfgsdf
        OkHttpClient okHttpClient= MyApplication.getOkHttpClient();

        if (null!=baoCunBean.getSid()) {

            //    /* form的分割线,自己定义 */
            //        String boundary = "xx--------------------------------------------------------------xx";
            RequestBody body = new FormBody.Builder()
                    .add("status","1")
                    .add("token",baoCunBean.getToken())
                    .add("accountId",baoCunBean.getSid())
                    .build();

            Request.Builder requestBuilder = new Request.Builder()
                    // .header("Content-Type", "application/json")
                    .post(body)
                    .url(baoCunBean.getDizhi() + "/queryAllDept.do");

            // step 3：创建 Call 对象
            Call call = okHttpClient.newCall(requestBuilder.build());

            //step 4: 开始异步请求
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.d("AllConnects", "请求识别失败" + e.getMessage());

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    Log.d("AllConnects", "请求识别成功" + call.request().toString());
                    //获得返回体
                    try {

                        ResponseBody body = response.body();
                        ss = body.string().trim();
                        Log.d("DengJiActivity", ss);

                        JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                        Gson gson = new Gson();
                        BuMenBeans zhaoPianBean = gson.fromJson(jsonObject, BuMenBeans.class);
                        int size=zhaoPianBean.getObjects().size();
                        if (stringList.size()>0){
                            stringList.clear();
                        }
                        for (int i=0;i<size;i++){
                            stringList.add(zhaoPianBean.getObjects().get(i).getDeptName());
                        }
                        Collections.reverse(stringList); // 倒序排列


                    } catch (Exception e) {
                        dengLuGuoQi(ss);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (tiJIaoDialog != null && !XiuGaiYuanGongActivity.this.isFinishing()) {
                                    tiJIaoDialog.dismiss();
                                    tiJIaoDialog = null;
                                }
                            }
                        });

                        Log.d("WebsocketPushMsg", e.getMessage());
                    }
                }
            });
        }else {
            Toast tastyToast = TastyToast.makeText(XiuGaiYuanGongActivity.this, "账户ID为空!,请设置帐户ID", TastyToast.LENGTH_LONG, TastyToast.ERROR);
            tastyToast.setGravity(Gravity.CENTER, 0, 0);
            tastyToast.show();
        }
    }

    private void dengLuGuoQi(String ss){
        try {
            JsonObject jsonObject= GsonUtil.parse(ss).getAsJsonObject();
            Gson gson=new Gson();
            final MoRenFanHuiBean zhaoPianBean=gson.fromJson(jsonObject,MoRenFanHuiBean.class);

                //登陆过期
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast tastyToast= TastyToast.makeText(XiuGaiYuanGongActivity.this,zhaoPianBean.getDtoDesc(),TastyToast.LENGTH_LONG,TastyToast.ERROR);
                        tastyToast.setGravity(Gravity.CENTER,0,0);
                        tastyToast.show();
                    }
                });

        }catch (Exception e1){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    TastyToast.makeText(XiuGaiYuanGongActivity.this, "发生未知错误", Toast.LENGTH_LONG, TastyToast.INFO).show();
                    if (tiJIaoDialog != null && !XiuGaiYuanGongActivity.this.isFinishing() && tiJIaoDialog.isShowing()) {
                        tiJIaoDialog.dismiss();
                        tiJIaoDialog = null;
                    }
                }
            });
        }
    }

}
