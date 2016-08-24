package com.wirelesspienetwork.overviewexample;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by zqwei on 8/17/16.
 */
public class MyViewPager extends ViewPager {


    public MyViewPager(Context context) {
        super(context);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    float startX, startY, endX, endY;

    private boolean isTouchView(float x, float y, View view) {
        Rect rect = new Rect();
        view.getGlobalVisibleRect(rect);
        Log.d("MyViewPager", rect.left + ", " + rect.top + ", " + rect.right + ", " + rect.bottom);
        return rect.contains((int)x, (int)y);

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(getChildCount() <= 1) {
            return false;
        }

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = ev.getX();
                startY = ev.getY();
                if(getCurrentItem() == 0 && isTouchView(startX,startY, getChildAt(1))) {
                    return true;
                }

                if(getCurrentItem() == 1 && isTouchView(startX,startY, getChildAt(0))) {
                    return true;
                }
            case MotionEvent.ACTION_UP:
                endY = 0;
                endX = 0;
                break;
            case MotionEvent.ACTION_MOVE:
                endX = ev.getX();
                endY = ev.getY();
                isTouchView(endX, endY, getChildAt(0));

//                Log.d("xxxxx", ",,startX: " + startX + ", endX: " + endX  + ", startY: " + startY + ", endY: " + endY);

                if((getCurrentItem() == 0) && endX > startX) {
                    return false;
                }
                if((getCurrentItem() == 1) && endX < startX) {
                    return false;
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }
}
