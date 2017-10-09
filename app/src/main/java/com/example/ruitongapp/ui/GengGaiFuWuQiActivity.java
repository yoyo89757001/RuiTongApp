package com.example.ruitongapp.ui;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ruitongapp.MyApplication;
import com.example.ruitongapp.R;
import com.example.ruitongapp.adapters.FuWuQiAdapter;
import com.example.ruitongapp.beans.FuWuQiBean;
import com.example.ruitongapp.beans.FuWuQiBeanDao;
import com.example.ruitongapp.view.PopupList;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GengGaiFuWuQiActivity extends Activity {

    @BindView(R.id.leftim)
    ImageView leftim;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.righttv)
    TextView righttv;
    @BindView(R.id.listview)
    ListView listview;


    private List<String> popupMenuItemList = new ArrayList<>();
    private FuWuQiBeanDao fuWuQiBeanDao = null;
    private List<FuWuQiBean> fuWuQiBeanList = null;
    private FuWuQiAdapter adapter;
    private SensorInfoReceiver sensorInfoReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geng_gai_fu_wu_qi);

        IntentFilter intentFilter1 = new IntentFilter();
        intentFilter1.addAction("guanbi");
        sensorInfoReceiver = new SensorInfoReceiver();
        registerReceiver(sensorInfoReceiver, intentFilter1);


        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.titlecolor);
        }
        fuWuQiBeanDao = MyApplication.myAppLaction.getDaoSession().getFuWuQiBeanDao();
        if (fuWuQiBeanDao != null) {
            fuWuQiBeanList = fuWuQiBeanDao.loadAll();
            if (fuWuQiBeanList.size() == 0) {
                FuWuQiBean bean = new FuWuQiBean();
                bean.setId(System.currentTimeMillis());
                bean.setDizhi("http://192.168.2.2");
                bean.setIsTrue(true);
                fuWuQiBeanDao.insert(bean);
                FuWuQiBean bean2 = new FuWuQiBean();
                bean2.setId(System.currentTimeMillis());
                bean2.setDizhi("http://192.168.2.2");
                bean2.setIsTrue(false);
                fuWuQiBeanDao.insert(bean2);

                fuWuQiBeanList = fuWuQiBeanDao.loadAll();
            }
        }

        title.setText("更改服务器");
        righttv.setVisibility(View.VISIBLE);
        adapter=new FuWuQiAdapter(GengGaiFuWuQiActivity.this,fuWuQiBeanList);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i=0;i<fuWuQiBeanList.size();i++){
                    fuWuQiBeanList.get(i).setIsTrue(false);
                    fuWuQiBeanDao.update(fuWuQiBeanList.get(i));
                }
                fuWuQiBeanList.get(position).setIsTrue(true);
                adapter.notifyDataSetChanged();
                fuWuQiBeanDao.update(fuWuQiBeanList.get(position));
            }
        });

        popupMenuItemList.add("删除");
        final PopupList popupList = new PopupList(this);

        popupList.bind(listview, popupMenuItemList, new PopupList.PopupListListener() {
            @Override
            public boolean showPopupList(View adapterView, View contextView, int contextPosition) {
                return true;
            }

            @Override
            public void onPopupListClick(View contextView, int contextPosition, int position) {
              //  Log.d("BianJiFuWuQiActivity", "dsds"+contextPosition);
                fuWuQiBeanDao.deleteByKey(fuWuQiBeanList.get(contextPosition).getId());
                fuWuQiBeanList.remove(contextPosition);
                adapter.notifyDataSetChanged();
            }
        });


    }

    @OnClick({R.id.leftim, R.id.righttv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.leftim:
                finish();
                break;
            case R.id.righttv:
        startActivity(new Intent(GengGaiFuWuQiActivity.this,TianJiaFuWuQiActivity.class));
                break;
        }
    }

    private class SensorInfoReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();
            if (action.equals("guanbi")){
                if (fuWuQiBeanDao!=null){
                    adapter=null;
                    fuWuQiBeanList=fuWuQiBeanDao.loadAll();
                    adapter=new FuWuQiAdapter(GengGaiFuWuQiActivity.this,fuWuQiBeanList);
                    listview.setAdapter(adapter);

                }

            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(sensorInfoReceiver);
    }
}
