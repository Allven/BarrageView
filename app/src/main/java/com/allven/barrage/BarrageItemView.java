package com.allven.barrage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/*****************************************************
 * Author: Allven
 *2018/10/5 0005 10:42 Created by Allven 
 ********************************************************/
public class BarrageItemView extends LinearLayout {

    private Context mContext;
    private LinearLayout llItemPannel;
    private TextView tvItemText;
    private ImageView ivItemImg;

    public BarrageItemView(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    private void initView() {
        View llContent = LayoutInflater.from(mContext).inflate(R.layout.view_barrage_item, null, false);
        addView(llContent);
        ivItemImg = llContent.findViewById(R.id.iv_item_img);
        tvItemText = llContent.findViewById(R.id.tv_item_text);
        llItemPannel = llContent.findViewById(R.id.ll_item_pannel);
    }

    public ImageView getItemImageView() {
        return ivItemImg;
    }

    public TextView getItemTextView() {
        return tvItemText;
    }

    public void setItemImg(String strUrl, int resourceId) {
        this.ivItemImg = ivItemImg;
    }

    public void setItemText(String text) {
        tvItemText.setText(text);
    }

    public LinearLayout getRoot() {
        return llItemPannel;
    }
}
