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
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.ruitongapp.MyApplication;
import com.example.ruitongapp.R;
import com.example.ruitongapp.adapters.BaoGaoAdapter2;
import com.example.ruitongapp.beans.BaoCunBean;
import com.example.ruitongapp.beans.BaoCunBeanDao;
import com.example.ruitongapp.beans.BenDiYuanGong;
import com.example.ruitongapp.beans.BenDiYuanGongDao;
import com.example.ruitongapp.interfaces.ClickIntface;
import com.example.ruitongapp.view.MyEditTextWrite;
import com.github.jdsjlzx.ItemDecoration.DividerDecoration;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    private List<BenDiYuanGong> benDiYuanGongList;
    private BaoCunBeanDao baoCunBeanDao=null;
    private BaoCunBean baoCunBean=null;


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

                break;
            case R.id.sousuo:

                break;
        }
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
                List<BenDiYuanGong> b= benDiYuanGongDao.queryBuilder().where(BenDiYuanGongDao.Properties.Name.like("%" + s + "%")).list();
                if (benDiYuanGongList.size()!=0){
                    benDiYuanGongList.clear();
                }
                benDiYuanGongList.addAll(b);
                baoGaoAdapter2.notifyDataSetChanged();
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
            startActivity(new Intent(SouSuoActivity.this,XiuGaiYuanGongActivity.class).putExtra("idid",benDiYuanGongList.get(id).getId()));

    }
}
