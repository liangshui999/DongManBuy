package com.example.asus_cp.dongmanbuy.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by asus-cp on 2016-05-21.
 */
public class MyScrollView extends ScrollView {
    private String tag="MyScrollView";
    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     *
     * 拦截事件的方法
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
        /*boolean intercept=false;
        float x=ev.getX();
        float y=ev.getY();
        float interceptX=0;
        float interceptY=0;
        switch (ev.getAction()){
            case MotionEvent.ACTION_MOVE:
                float deltaX=x-interceptX;
                float deltaY=y-interceptY;
                if(Math.abs(deltaX)>Math.abs(deltaY)){
                    intercept=false;
                }else {
                    intercept=true;
                }
                interceptX=x;
                interceptY=y;
                break;
        }
        return intercept;*/
    }
}
