package com.example.ruitongapp.fargments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ruitongapp.R;
import com.example.ruitongapp.adapters.HeiMingDanAdapter;
import com.example.ruitongapp.adapters.YuanGongAdapter;
import com.example.ruitongapp.beans.YuanGongBean;
import com.example.ruitongapp.ui.XiuGaiFangKeActivity;
import com.example.ruitongapp.view.SideBar;
import com.github.jdsjlzx.ItemDecoration.DividerDecoration;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import opensource.jpinyin.PinyinHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment3 extends Fragment {
    private LinearLayoutManager linearLayoutManager = null;
    private LRecyclerView lRecyclerView;
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private List<YuanGongBean> dataList;
    private HeiMingDanAdapter taiZhangAdapter;
    private TextView txtShowCurrentLetter;
    private SideBar sideBar;


    private String[] letterStrings = {"#", "dA", "gB", "tC", "aD", "bE", "hF", "jG", "kH", "lI", "oJ", "pK",
            "qL", "wM", "dN", "aO", "jP", "bQ", "cR", "zS", "eT", "rU", "uV", "iW", "hX", "jY", "kZ"};

    public Fragment3() {
        dataList = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            YuanGongBean bean = new YuanGongBean();
            bean.setName(letterStrings[i]);
            dataList.add(bean);
        }

        chineseToPinyin(dataList);
        paixu();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment3, container, false);
        sideBar = (SideBar) view.findViewById(R.id.side_bar);
        txtShowCurrentLetter = (TextView) view.findViewById(R.id.txt_show_current_letter);

        linearLayoutManager = new LinearLayoutManager(getContext());

        lRecyclerView = (LRecyclerView) view.findViewById(R.id.recyclerView);
        taiZhangAdapter = new HeiMingDanAdapter(dataList);
        taiZhangAdapter.setLetters();
        lRecyclerViewAdapter = new LRecyclerViewAdapter(taiZhangAdapter);

        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        lRecyclerView.setLayoutManager(linearLayoutManager);

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

                startActivity(new Intent(getContext(), XiuGaiFangKeActivity.class).putExtra("type", 2));

            }
        });


        setCallbackInterface();

        return view;
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
    public static void chineseToPinyin(List<YuanGongBean> list) {
        for (int i = 0; i < list.size(); i++) {
            YuanGongBean contactsModel1 = list.get(i);
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
        Collections.sort(dataList, new Comparator<YuanGongBean>() {
            @Override
            public int compare(YuanGongBean lhs, YuanGongBean rhs) {
                return lhs.getFirstLetter().compareTo(rhs.getFirstLetter());
            }
        });
    }
}
