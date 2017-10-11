package com.example.ruitongapp.view;

/**
 * Created by xiaojun on 2016/5/25.
 */
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

public class MyEditText extends android.support.v7.widget.AppCompatEditText{

    private Paint mPaint;
    /**
     * @param context
     * @param attrs
     */
    public MyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        mPaint = new Paint();

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLUE);
    }

    @Override
    public void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

//      画底线
        canvas.drawLine(0,this.getHeight()-1,  this.getWidth()-1, this.getHeight()-1, mPaint);
    }

}