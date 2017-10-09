package com.example.ruitongapp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;


/**
 * 自定义ListView 控件
 * @author Administrator
 *
 */
public class GaoduListview extends ListView {

    public GaoduListview(Context context) {
        super(context);
    }

    public GaoduListview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GaoduListview(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,  MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

}