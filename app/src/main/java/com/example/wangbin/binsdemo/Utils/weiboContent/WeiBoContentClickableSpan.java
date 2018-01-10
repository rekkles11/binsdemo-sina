package com.example.wangbin.binsdemo.Utils.weiboContent;

import android.content.Context;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import com.example.wangbin.binsdemo.R;

/**
 * Created by wenmingvs on 16/4/15.
 */
public class WeiBoContentClickableSpan extends ClickableSpan {

    private Context mContext;

    public WeiBoContentClickableSpan(Context context) {
        mContext = context;
    }

    @Override
    public void onClick(View widget) {
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
        ds.setColor(mContext.getResources().getColor(R.color.home_weiboitem_content_keyword));
        ds.setUnderlineText(false);
    }


}
