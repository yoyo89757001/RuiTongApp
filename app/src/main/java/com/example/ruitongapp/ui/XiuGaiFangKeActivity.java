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
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ruitongapp.MyApplication;
import com.example.ruitongapp.R;
import com.example.ruitongapp.adapters.PopupWindowAdapter;
import com.example.ruitongapp.beans.BaoCunBean;
import com.example.ruitongapp.beans.BaoCunBeanDao;
import com.example.ruitongapp.beans.BuMenBeans;
import com.example.ruitongapp.beans.MoRenFanHuiBean;
import com.example.ruitongapp.beans.ShouFangBean;
import com.example.ruitongapp.dialogs.PaiZhaoDialog2;
import com.example.ruitongapp.dialogs.QueRenDialog;
import com.example.ruitongapp.dialogs.TiJIaoDialog;
import com.example.ruitongapp.dialogs.UpdataTouXiangDialog;
import com.example.ruitongapp.fangkebean.FangKeBean;
import com.example.ruitongapp.utils.DateUtils;
import com.example.ruitongapp.utils.GlideCircleTransform;
import com.example.ruitongapp.utils.GsonUtil;
import com.example.ruitongapp.utils.Utils;
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

public class XiuGaiFangKeActivity extends Activity {


    @BindView(R.id.ruzhi)
    RelativeLayout ruzhi;
    @BindView(R.id.vip)
    RelativeLayout vip;
    @BindView(R.id.r6)
    RelativeLayout r6;
    @BindView(R.id.leftim)
    ImageView leftim;
    @BindView(R.id.righttv)
    TextView righttv;
    @BindView(R.id.ee63)
    ImageView vip_im;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.shanchu)
    Button shanchu;
    @BindView(R.id.fangkeruku)
    ImageView fangkeruku;
    @BindView(R.id.touxiang)
    ImageView touxiang;
    @BindView(R.id.beifangren)
    EditText beifangren;
    @BindView(R.id.ruzhishijian)
    TextView ruzhishijian;
    @BindView(R.id.shouji)
    TextView shouji;     //部门
    @BindView(R.id.laifangren)
    EditText laifangren;
    @BindView(R.id.zhiwei)
    TextView zhiwei;  //来访目的
    @BindView(R.id.laifangren1)
    EditText laifangren1;  //来访人电话
    @BindView(R.id.beifangren2)
    EditText beifangren2;  //被访人电话

    private boolean isVip = false;
    private int type;
    private BaoCunBeanDao baoCunBeanDao = null;
    private BaoCunBean baoCunBean = null;
    private FangKeBean.ObjectsBean benDiYuanGong = null;
    private String touxiangPath = "";
    private String shibiePaths = "";
    private File mFile;
    private static final int PHOTO_REQUEST_CUT = 9;
    private static final int PHOTO_REQUEST_CUT2 = 6;
    private UpdataTouXiangDialog dialog88 = null;
    public final static int ALBUM_REQUEST_CODE = 1;
    public final static int ALBUM_REQUEST_CODE2 = 4;
    private PaiZhaoDialog2 dialog2 = null;
    private TiJIaoDialog tiJIaoDialog = null;
    //定义一个过滤器；
    private IntentFilter intentFilter;
    //定义一个广播监听器；
    private NetChangReceiver netChangReceiver;
    private String cameraPath = null;
    private String ss;
    private int vipNum = 2;
    private List<String> stringList=new ArrayList<>();
    private List<String> stringList2=new ArrayList<>();
    private PopupWindowAdapter adapterss;
    private PopupWindowAdapter adapterss2;
    private PopupWindow popupWindow=null;
    private EditText qiye;
    private RelativeLayout ruizhi2;
    private TextView riqi2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xiu_gai_fang_ke);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.titlecolor);
        }
        stringList2.add("业务");
        stringList2.add("面试");
        stringList2.add("合作");
        stringList2.add("其它");

        ruizhi2= (RelativeLayout) findViewById(R.id.ruzhi2);
        riqi2= (TextView) findViewById(R.id.jieshushijian);
        qiye= (EditText) findViewById(R.id.qiye);
        benDiYuanGong = Parcels.unwrap(getIntent().getParcelableExtra("chuansong"));
        baoCunBeanDao = MyApplication.myAppLaction.getDaoSession().getBaoCunBeanDao();
        if (baoCunBeanDao != null) {
            baoCunBean = baoCunBeanDao.load(123456L);
        }
        //实例化过滤器；
        intentFilter = new IntentFilter();
        //添加过滤的Action值；
        intentFilter.addAction("paizhang_sb");
        intentFilter.addAction("paizhang_tx");
        //实例化广播监听器；
        netChangReceiver = new NetChangReceiver();
        //将广播监听器和过滤器注册在一起；
        registerReceiver(netChangReceiver, intentFilter);
        mFile = new File(getExternalFilesDir(null), "pic.jpg");

        type = getIntent().getIntExtra("type", 0);
        if (type == 1) {
            title.setText("添加访客");
            shanchu.setVisibility(View.GONE);

        } else {
            title.setText("访客信息");
        }
        righttv.setVisibility(View.VISIBLE);
        righttv.setText("保 存");

        if (benDiYuanGong != null) {
            laifangren.setText(benDiYuanGong.getName());
            beifangren2.setText(benDiYuanGong.getHomeNumber()==null?"":benDiYuanGong.getHomeNumber());
            laifangren1.setText(benDiYuanGong.getPhone()==null?"":benDiYuanGong.getPhone());
            if (benDiYuanGong.getVisitDate()!=0)
                ruzhishijian.setText(DateUtils.time(benDiYuanGong.getVisitDate() + ""));
            if (benDiYuanGong.getVisitEndDate()!=0)
                riqi2.setText(DateUtils.time(benDiYuanGong.getVisitEndDate() + ""));
            qiye.setText(benDiYuanGong.getCompanyName());
            beifangren.setText(benDiYuanGong.getVisitPerson());
            shouji.setText(benDiYuanGong.getVisitDepartment());
            zhiwei.setText(benDiYuanGong.getVisitIncident());
            if (benDiYuanGong.getSubjectType() == 1) {
                vip_im.setImageResource(R.drawable.guan);
            } else {
                vip_im.setImageResource(R.drawable.kai);
            }

            if (benDiYuanGong.getAvatar()!=null && !benDiYuanGong.getAvatar().equals("")) {
                Glide.with(XiuGaiFangKeActivity.this)
                        .load(baoCunBean.getDizhi() + "/upload/avatar/" + benDiYuanGong.getAvatar())
                        .transform(new GlideCircleTransform(XiuGaiFangKeActivity.this, 0.6f, Color.parseColor("#ffffffff")))
                        .into(touxiang);
            }
            if (!benDiYuanGong.getScanPhoto().equals("")) {

                Glide.with(XiuGaiFangKeActivity.this)
                        .load(baoCunBean.getDizhi() + "/upload/compare/" + benDiYuanGong.getScanPhoto())
                        .into(fangkeruku);
            }
            shibiePaths = benDiYuanGong.getScanPhoto();
            if (benDiYuanGong.getAvatar()!=null){
                touxiangPath=benDiYuanGong.getAvatar();
            }

        }
        link_bumen();

    }


    @OnClick({R.id.ruzhi, R.id.vip, R.id.r6, R.id.leftim, R.id.righttv, R.id.fangkeruku,R.id.shanchu,R.id.shouji,R.id.zhiwei,R.id.ruzhi2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ruzhi:
                //来访时间
                Intent intent = new Intent(XiuGaiFangKeActivity.this, DatePickActivity.class).putExtra("yyy", 1);
                startActivityForResult(intent, 2);
                break;
            case R.id.ruzhi2:
                //来访时间
                Intent intent2 = new Intent(XiuGaiFangKeActivity.this, DatePickActivity.class).putExtra("yyy", 1);
                startActivityForResult(intent2, 3);
                break;
            case R.id.vip:

                if (isVip) {
                    vip_im.setImageResource(R.drawable.kai);
                    isVip = false;
                    vipNum = 2;
                } else {
                    isVip = true;
                    vipNum = 1;
                    vip_im.setImageResource(R.drawable.guan);
                }

                break;
            case R.id.r6:
                dialog88 = new UpdataTouXiangDialog(XiuGaiFangKeActivity.this);
                dialog88.setOnUpdataPaiZhaoListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) { //拍照
                        startActivity(new Intent(XiuGaiFangKeActivity.this, PaiZhaoActivity2.class));
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
                if (!laifangren.getText().toString().trim().equals("") && !ruzhishijian.getText().toString().trim().equals("") && !shibiePaths.equals("") && !riqi2.getText().toString().trim().equals("")) {
                    link_gengxinTuPian();
                } else {
                    Utils.showToast(XiuGaiFangKeActivity.this, "姓名,来访时间,识别照片,必须填写", 3);
                }

                break;
            case R.id.fangkeruku:
                dialog2 = new PaiZhaoDialog2(XiuGaiFangKeActivity.this);
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
                        startActivity(new Intent(XiuGaiFangKeActivity.this, PaiZhaoActivity.class));
                    }
                });

                dialog2.show();

                break;
            case R.id.shanchu:
                final QueRenDialog dialog=new QueRenDialog(XiuGaiFangKeActivity.this);
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
            case R.id.shouji:
                if (stringList.size()>0) {
                    View contentView = LayoutInflater.from(XiuGaiFangKeActivity.this).inflate(R.layout.xiangmu_po_item, null);

                    ListView listView = (ListView) contentView.findViewById(R.id.dddddd);
                    adapterss = new PopupWindowAdapter(XiuGaiFangKeActivity.this, stringList);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            shouji.setText(stringList.get(position));
                            popupWindow.dismiss();
                        }
                    });
                    listView.setAdapter(adapterss);
                    popupWindow = new PopupWindow(contentView, 400, setListViewHeightBasedOnChildren(listView));
                    popupWindow.setFocusable(true);//获取焦点
                    popupWindow.setOutsideTouchable(true);//获取外部触摸事件
                    popupWindow.setTouchable(true);//能够响应触摸事件
                    popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));//设置背景
                    popupWindow.showAsDropDown(shouji, shouji.getRight(), 0);
                }else {
                    Toast tastyToast= TastyToast.makeText(XiuGaiFangKeActivity.this,"暂无部门数据，请从后台添加",TastyToast.LENGTH_LONG,4);
                    tastyToast.setGravity(Gravity.CENTER,0,0);
                    tastyToast.show();
                }
                break;
            case R.id.zhiwei:
                View contentView2 = LayoutInflater.from(XiuGaiFangKeActivity.this).inflate(R.layout.xiangmu_po_item, null);

                ListView listView2= (ListView) contentView2.findViewById(R.id.dddddd);
                adapterss2=new PopupWindowAdapter(XiuGaiFangKeActivity.this,stringList2);
                listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        zhiwei.setText(stringList2.get(position));
                        popupWindow.dismiss();
                    }
                });
                listView2.setAdapter(adapterss2);
                popupWindow=new PopupWindow(contentView2,400, setListViewHeightBasedOnChildren(listView2));
                popupWindow.setFocusable(true);//获取焦点
                popupWindow.setOutsideTouchable(true);//获取外部触摸事件
                popupWindow.setTouchable(true);//能够响应触摸事件
                popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));//设置背景
                popupWindow.showAsDropDown(zhiwei,zhiwei.getRight(),0);
                break;
        }
    }


    public int setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return 0;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            // listItem.measure(0, 0);
            listItem.measure(
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            totalHeight += listItem.getMeasuredHeight();
        }

//        ViewGroup.LayoutParams params = listView.getLayoutParams();
//        params.height = totalHeight
//                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
//        listView.setLayoutParams(params);
        return totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
    }

    private class NetChangReceiver extends BroadcastReceiver {

        //重写onReceive方法，该方法的实体为，接收到广播后的执行代码；
        @Override
        public void onReceive(Context context, Intent intent) {

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

    private void xiangce(int type) {
        if (type == 99) {
            //更新头像
            Intent intent = new Intent();
            // intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            intent.setAction(Intent.ACTION_PICK);
            intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, ALBUM_REQUEST_CODE2);

        } else {
            //更新识别照片
            Intent intent = new Intent();
            // intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            intent.setAction(Intent.ACTION_PICK);
            intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, ALBUM_REQUEST_CODE);
        }

    }


    private void link_gengxinTuPian() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (tiJIaoDialog == null && !XiuGaiFangKeActivity.this.isFinishing()) {
                    tiJIaoDialog = new TiJIaoDialog(XiuGaiFangKeActivity.this);
                    tiJIaoDialog.show();
                }
            }
        });
//        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        OkHttpClient okHttpClient = new OkHttpClient();

        RequestBody body = null;
        if (type == 1) {
            //添加
            body = new FormBody.Builder()
                    .add("name", laifangren.getText().toString().trim())
                    .add("phone", laifangren1.getText().toString().trim())
                    .add("visitDate2", ruzhishijian.getText().toString().trim())
                    .add("visitPerson", beifangren.getText().toString().trim())
                    .add("avatar", touxiangPath)
                    .add("visitNum", "1")
                    .add("visitDepartment", shouji.getText().toString().trim())
                    .add("scanPhoto", shibiePaths)
                    .add("visitIncident", zhiwei.getText().toString().trim())
                    .add("cardNumber", "rt" + System.currentTimeMillis())
                    .add("source", "2")
                    .add("subjectType", vipNum + "")
                    .add("token", baoCunBean.getToken())
                    .add("homeNumber", beifangren2.getText().toString().trim())
                    .add("accountId", baoCunBean.getSid())
                    .add("visitEndDate2", riqi2.getText().toString().trim())
                    .add("companyName", qiye.getText().toString().trim())
                    .build();

        } else {
            //修改
            body = new FormBody.Builder()
                    .add("id", benDiYuanGong.getId() + "")
                    .add("name", laifangren.getText().toString().trim())
                    .add("phone", laifangren1.getText().toString().trim())
                    .add("visitDate2", ruzhishijian.getText().toString().trim())
                    .add("visitPerson", beifangren.getText().toString().trim())
                    .add("avatar", touxiangPath)
                    .add("visitNum", "1")
                    .add("visitDepartment", shouji.getText().toString().trim())
                    .add("homeNumber", beifangren2.getText().toString().trim())
                    .add("scanPhoto", shibiePaths)
                    .add("visitIncident", zhiwei.getText().toString().trim())
                    .add("source", "2")
                    .add("cardNumber", benDiYuanGong.getCardNumber())
                    .add("subjectType", vipNum + "")
                    .add("token", baoCunBean.getToken())
                    .add("accountId", baoCunBean.getSid())
                    .add("visitEndDate2", riqi2.getText().toString().trim())
                    .add("companyName", qiye.getText().toString().trim())
                    .build();

        }

        Request.Builder requestBuilder = new Request.Builder()
                // .header("Content-Type", "application/json")
                .post(body)
                .url(baoCunBean.getDizhi() + "/saveCompareVisit.do");

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
                        TastyToast.makeText(XiuGaiFangKeActivity.this, "网络错误,请求失败", Toast.LENGTH_LONG, TastyToast.INFO).show();
                        if (tiJIaoDialog != null && !XiuGaiFangKeActivity.this.isFinishing() && tiJIaoDialog.isShowing()) {
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
                            if (tiJIaoDialog != null && !XiuGaiFangKeActivity.this.isFinishing() && tiJIaoDialog.isShowing()) {
                                tiJIaoDialog.dismiss();
                                tiJIaoDialog = null;
                            }
                        }
                    });

                    //获得返回体
                    ResponseBody body = response.body();
                    ss = body.string();
                    Log.d("AllConnects", "aa   " + ss);
                    JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
//                Gson gson=new Gson();
                    int code = jsonObject.get("dtoResult").getAsInt();
                    if (code == 0) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                TastyToast.makeText(XiuGaiFangKeActivity.this, "修改成功！", Toast.LENGTH_LONG, TastyToast.INFO).show();
                                finish();
                                //  sendBroadcast(new Intent("shuaxing3"));
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                TastyToast.makeText(XiuGaiFangKeActivity.this, "修改失败", Toast.LENGTH_LONG, TastyToast.INFO).show();
                            }
                        });
                    }

                } catch (Exception e) {
                    dengLuGuoQi(ss);
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            TastyToast.makeText(XiuGaiFangKeActivity.this, "发生未知错误", Toast.LENGTH_LONG, TastyToast.INFO).show();
                            if (tiJIaoDialog != null && !XiuGaiFangKeActivity.this.isFinishing() && tiJIaoDialog.isShowing()) {
                                tiJIaoDialog.dismiss();
                                tiJIaoDialog = null;
                            }
                        }
                    });
                }

            }
        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == 2) {
            // 选择预约时间的页面被关闭
            String date = data.getStringExtra("date");
            int yyy = data.getIntExtra("yyy", 0);
            if (yyy == 1) {
                ruzhishijian.setText(date);
            }
        }
        if (resultCode == Activity.RESULT_OK && requestCode == 3) {
            // 选择预约时间的页面被关闭
            String date = data.getStringExtra("date");
            int yyy = data.getIntExtra("yyy", 0);
            if (yyy == 1) {
                riqi2.setText(date);
            }
        }

        //相册，更新识别照片
        if (requestCode == ALBUM_REQUEST_CODE) {
            try {
                Uri uri = data.getData();
                final String absolutePath = getAbsolutePath(XiuGaiFangKeActivity.this, uri);
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
                final String absolutePath = getAbsolutePath(XiuGaiFangKeActivity.this, uri);
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
                                    Toast tastyToast = TastyToast.makeText(XiuGaiFangKeActivity.this, "没有检测到人脸,请重新选择", TastyToast.LENGTH_LONG, TastyToast.ERROR);
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
                if (tiJIaoDialog == null && !XiuGaiFangKeActivity.this.isFinishing()) {
                    tiJIaoDialog = new TiJIaoDialog(XiuGaiFangKeActivity.this);
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
                            if (tiJIaoDialog != null && !XiuGaiFangKeActivity.this.isFinishing() && tiJIaoDialog.isShowing()) {
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

                        if (zhaoPianBean.getDtoResult() != 0) {

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    if (!XiuGaiFangKeActivity.this.isFinishing()) {
                                        Toast tastyToast = TastyToast.makeText(XiuGaiFangKeActivity.this, "照片质量不符合入库要求,请拍正面照!", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                        tastyToast.setGravity(Gravity.CENTER, 0, 0);
                                        tastyToast.show();
                                    }


                                }
                            });

                        } else {
                            shibiePaths = path;
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    Glide.with(XiuGaiFangKeActivity.this)
                                            //cameraPath
                                            .load(baoCunBean.getDizhi() + "/upload/compare/" + shibiePaths)
                                            //    .transform(new GlideCircleTransform(XiuGaiHeiMingDanActivity.this, 0.6f, Color.parseColor("#ffffffff")))
                                            .into(fangkeruku);
                                }
                            });

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

                                Toast tastyToast = TastyToast.makeText(XiuGaiFangKeActivity.this, "提交失败,请检查网络", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                tastyToast.setGravity(Gravity.CENTER, 0, 0);
                                tastyToast.show();

                            }
                        });
                        Log.d("WebsocketPushMsg", e.getMessage());
                    }
                }
            });
        } else {
            Toast tastyToast = TastyToast.makeText(XiuGaiFangKeActivity.this, "账户ID为空!,请设置帐户ID", TastyToast.LENGTH_LONG, TastyToast.ERROR);
            tastyToast.setGravity(Gravity.CENTER, 0, 0);
            tastyToast.show();
        }


    }

    private void link_delect() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (tiJIaoDialog == null && !XiuGaiFangKeActivity.this.isFinishing()) {
                    tiJIaoDialog = new TiJIaoDialog(XiuGaiFangKeActivity.this);
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
                    .add("token", baoCunBean.getToken())
                    .add("accountId", baoCunBean.getSid())
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
                            if (tiJIaoDialog != null && !XiuGaiFangKeActivity.this.isFinishing() && tiJIaoDialog.isShowing()) {
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

                                    if (!XiuGaiFangKeActivity.this.isFinishing()) {
                                        Toast tastyToast = TastyToast.makeText(XiuGaiFangKeActivity.this, "删除成功", TastyToast.LENGTH_LONG, TastyToast.INFO);
                                        tastyToast.setGravity(Gravity.CENTER, 0, 0);
                                        tastyToast.show();
                                        finish();
                                        //  sendBroadcast(new Intent("shanchu3"));
                                    }


                                }
                            });

                        } else {
                            if (!XiuGaiFangKeActivity.this.isFinishing()) {
                                Toast tastyToast = TastyToast.makeText(XiuGaiFangKeActivity.this, "删除失败", TastyToast.LENGTH_LONG, TastyToast.ERROR);
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

                                Toast tastyToast = TastyToast.makeText(XiuGaiFangKeActivity.this, "提交失败,请检查网络", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                tastyToast.setGravity(Gravity.CENTER, 0, 0);
                                tastyToast.show();

                            }
                        });
                        Log.d("WebsocketPushMsg", e.getMessage());
                    }
                }
            });
        } else {
            Toast tastyToast = TastyToast.makeText(XiuGaiFangKeActivity.this, "账户ID为空!,请设置帐户ID", TastyToast.LENGTH_LONG, TastyToast.ERROR);
            tastyToast.setGravity(Gravity.CENTER, 0, 0);
            tastyToast.show();
        }
    }

    private void addPhoto(final int type) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (dialog2 != null) {
                    dialog2.setL1Gone();
                }

            }
        });

        OkHttpClient okHttpClient = MyApplication.getOkHttpClient();
        RequestBody fileBody1 = null;
        String file1Name = null;
         /* 第一个要上传的file */
        if (cameraPath != null) {
            File file1 = new File(cameraPath);
            fileBody1 = RequestBody.create(MediaType.parse("application/octet-stream"), file1);
            //  file1Name = System.currentTimeMillis()+"testFile1.jpg";
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
                //  .addFormDataPart("subject_id" , subject_id+"")
//                .addFormDataPart("file" , file2Name , fileBody2)
                .build();
        Request.Builder requestBuilder = new Request.Builder()
                //  .header("Content-Type", "application/json")
                .post(mBody)
                .url(baoCunBean.getDizhi() + "/AppFileUploadServlet?FilePathPath=compareFilePath&AllowFileType=.jpg,.gif,.jpeg,.bmp,.png&MaxFileSize=10");
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


                        if (dialog2 != null && !XiuGaiFangKeActivity.this.isFinishing()) {
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


                        if (dialog2 != null && !XiuGaiFangKeActivity.this.isFinishing()) {
                            dialog2.dismiss();
                            dialog2 = null;
                        }

                    }
                });

                try {


                    cameraPath = null;
                    //获得返回体
                    ResponseBody body = response.body();
                    String ss = body.string();
                    Log.d("AllConnects", "aa   " + ss);
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

                                if (dialog2 != null && !XiuGaiFangKeActivity.this.isFinishing()) {
                                    dialog2.dismiss();
                                    dialog2 = null;
                                }
                                TastyToast.makeText(getApplicationContext(),
                                        "上传失败", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                            }
                        });

                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });

    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(netChangReceiver);
        super.onDestroy();

    }

    private void addPhoto222(final int type) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (dialog88 != null) {
                    dialog88.setL1Gone();
                }

            }
        });


        OkHttpClient okHttpClient = MyApplication.getOkHttpClient();
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
                .url(baoCunBean.getDizhi() + "/AppFileUploadServlet?FilePathPath=avatarFilePath&AllowFileType=.jpg,.gif,.jpeg,.bmp,.png&MaxFileSize=10");
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
                        if (!XiuGaiFangKeActivity.this.isFinishing() && dialog88 != null && dialog88.isShowing()) {
                            dialog88.dismiss();
                            dialog88 = null;
                        }
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!XiuGaiFangKeActivity.this.isFinishing() && dialog88 != null && dialog88.isShowing()) {
                            dialog88.dismiss();
                            dialog88 = null;
                        }
                    }
                });
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

                        touxiangPath = jsonObject.get("exDesc").getAsString();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Glide.with(XiuGaiFangKeActivity.this)
                                        .load(baoCunBean.getDizhi() + "/upload/avatar/" + touxiangPath)
                                        .transform(new GlideCircleTransform(XiuGaiFangKeActivity.this, 0.6f, Color.parseColor("#ffffffff")))
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
                                        if (!XiuGaiFangKeActivity.this.isFinishing() && dialog88 != null && dialog88.isShowing()) {
                                            dialog88.dismiss();
                                            dialog88 = null;
                                        }
                                    }
                                });
                                TastyToast.makeText(getApplicationContext(),
                                        "上传头像失败", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                            }
                        });
                    }
                }catch (Exception e){
                    Log.d("XiuGaiFangKeActivity", e.getMessage()+"");
                }
            }
        });

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
                        Log.d("DengJiActivity", ss+"hhhh");

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
                                if (tiJIaoDialog != null && !XiuGaiFangKeActivity.this.isFinishing()) {
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
            Toast tastyToast = TastyToast.makeText(XiuGaiFangKeActivity.this, "账户ID为空!,请设置帐户ID", TastyToast.LENGTH_LONG, TastyToast.ERROR);
            tastyToast.setGravity(Gravity.CENTER, 0, 0);
            tastyToast.show();
        }
    }

    private void dengLuGuoQi(String ss) {
        try {
            JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
            Gson gson = new Gson();
            final MoRenFanHuiBean zhaoPianBean = gson.fromJson(jsonObject, MoRenFanHuiBean.class);
            if (zhaoPianBean.getDtoResult() == -33) {
                //登陆过期
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                                    if (jiaZaiDialog!=null && jiaZaiDialog.isShowing()){
//                                        jiaZaiDialog.dismiss();
//                                    }
                        Toast tastyToast = TastyToast.makeText(XiuGaiFangKeActivity.this, "登陆过期,或账号在其它机器登陆,请重新登陆", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                        tastyToast.setGravity(Gravity.CENTER, 0, 0);
                        tastyToast.show();
                    }
                });
            }
        } catch (Exception e1) {
            Log.d("Fragment1", "e1:" + e1);
        }
    }

}
