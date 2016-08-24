package com.wirelesspienetwork.overviewexample;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.wirelesspienetwork.overview.model.OverviewAdapter;
import com.wirelesspienetwork.overview.model.ViewHolder;
import com.wirelesspienetwork.overview.views.Overview;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by zqwei on 8/17/16.
 */
public class MyOverView extends FrameLayout implements Overview.RecentsViewCallbacks{

    private Context mContext;

    public MyOverView(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    public MyOverView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        mContext = context;
        initView();
    }



    private void initView(){
        Overview overview = new Overview(mContext);
        overview.setCallbacks(this);
        ArrayList<Integer> models = new ArrayList<>();
        for(int i = 0; i < 10; ++i)
        {
            Random random = new Random();
            random.setSeed(i);
            int color = Color.argb(255, random.nextInt(255), random.nextInt(255), random.nextInt(255));
            models.add(color);
        }

        final OverviewAdapter stack = new OverviewAdapter<ViewHolder<View, Integer>, Integer>(models)
        {
            @Override
            public ViewHolder onCreateViewHolder(Context context, ViewGroup parent) {
                View v = View.inflate(context, R.layout.recents_dummy, null);
                return new ViewHolder<View, Integer>(v);
            }

            @Override
            public void onBindViewHolder(ViewHolder<View, Integer> viewHolder) {
                viewHolder.itemView.setBackgroundColor(viewHolder.model);
                viewHolder.itemView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(mContext, "sss", Toast.LENGTH_SHORT).show();
                        scaleAnimation(view);
                    }
                });
            }
        };

        overview.setTaskStack(stack);

        addView(overview);
    }

    private void scaleAnimation(final View view) {
        int width = view.getWidth();
        final int height = view.getHeight();
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        final int screenHeight = dm.heightPixels;
        ValueAnimator animator = ValueAnimator.ofInt(width, screenWidth);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                Log.d("MyOverview", valueAnimator.getAnimatedValue() +"---");
                ViewGroup.LayoutParams params = view.getLayoutParams();
                params.width = (int)valueAnimator.getAnimatedValue();
                params.height = (int) (height + valueAnimator.getAnimatedFraction() * (screenHeight - height));
                view.setLayoutParams(params);
            }
        });
        animator.setDuration(300);
        animator.start();
    }

    @Override
    public void onAllCardsDismissed() {
    }

    @Override
    public void onCardDismissed(int position) {

    }
}
