package com.allven.barrage;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.util.Pools;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*****************************************************
 * Author: Allven
 *2018/10/5 0005 10:11 Created by Allven 
 ********************************************************/
public class BarrageView extends LinearLayout {

    private Context mContext;
    private LinearLayout llContainer;
    private List<String> barrageData;
    private Pools.SimplePool<BarrageItemView> textViewPool;
    private static final int BARRAGE_COUNT = 3; //滚动的条数

    public BarrageView(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    public BarrageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    private void initView() {
        barrageData = new ArrayList<>();
        View convertView = LayoutInflater.from(mContext).inflate(R.layout.view_barrage,null,false);
        addView(convertView);

        llContainer = convertView.findViewById(R.id.ll_container);
        LayoutTransition transition = new LayoutTransition();
        PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat("scaleY", 0, 1);
        PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat("scaleX", 0, 1);
        ObjectAnimator valueAnimator = ObjectAnimator.ofPropertyValuesHolder(null, new PropertyValuesHolder[]{scaleX, scaleY})
                .setDuration(transition.getDuration(LayoutTransition.APPEARING));
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                ObjectAnimator objectAnimator = (ObjectAnimator) animation;
                View view = (View) objectAnimator.getTarget();
                view.setPivotX(0f);
                view.setPivotY(view.getMeasuredHeight());
            }
        });
        transition.setAnimator(LayoutTransition.APPEARING, valueAnimator);
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(null, "alpha", 0, 1).setDuration(LayoutTransition.DISAPPEARING);
        transition.setAnimator(LayoutTransition.DISAPPEARING, objectAnimator);
        llContainer.setLayoutTransition(transition);
    }

    private BarrageItemView obtainTextView() {
        BarrageItemView textView = textViewPool.acquire();
        if (textView == null) {
            textView = new BarrageItemView(mContext);
            textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        }
        return textView;
    }

    int index = 0;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == 0 && llContainer.getChildCount() == BARRAGE_COUNT) {
                llContainer.removeViewAt(0);

            }
            if (index == barrageData.size()) {
                index = 0;
            }
            BarrageItemView textView = obtainTextView();
            textView.setItemText(barrageData.get(index));

            GradientDrawable drawable = (GradientDrawable)textView.getRoot().getBackground();
            drawable.setColor(randomColor());
            textView.getRoot().setBackground(drawable);

            drawable = (GradientDrawable)textView.getItemImageView().getBackground();
            drawable.setColor(randomColor());
            textView.getItemImageView().setBackground(drawable);


            llContainer.addView(textView);
            if (llContainer.getChildCount() == BARRAGE_COUNT) {
                BarrageItemView itemView = (BarrageItemView) llContainer.getChildAt(0);
                itemView.startAnimation(AnimationUtils.loadAnimation(mContext,R.anim.alpha_out_2s));
            }
            sendEmptyMessageDelayed(0, 2000);
            index++;
        }
    };

    public void setBarrageData(List<String> barrages) {
        if (barrageData != null && barrages != null && barrages.size() > 0) {
            barrageData.clear();
            barrageData.addAll(barrages);
            textViewPool = new Pools.SimplePool<>(barrageData.size());
            handler.sendEmptyMessage(0);
        }
    }

    public void stopBarrage(){
        handler.removeMessages(0);
    }


    private int randomColor() {
        String r,g,b;
        Random random = new Random();
        r = Integer.toHexString(random.nextInt(256)).toUpperCase();
        g = Integer.toHexString(random.nextInt(256)).toUpperCase();
        b = Integer.toHexString(random.nextInt(256)).toUpperCase();
        r = r.length()==1 ? "0" + r : r ;
        g = g.length()==1 ? "0" + g : g ;
        b = b.length()==1 ? "0" + b : b ;
        return Color.parseColor("#" + r + g + b);
    }

}
