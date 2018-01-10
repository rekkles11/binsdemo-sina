package com.example.wangbin.binsdemo.Utils.weiboContent;

import android.graphics.drawable.Drawable;
import android.text.style.ImageSpan;
import android.view.View;

/**
 * Created by momo on 2018/1/3.
 */

public abstract class ClickableImageSpan extends ImageSpan {
    public ClickableImageSpan(Drawable b, int verticalAlignment) {
        super(b, verticalAlignment);
    }

    public abstract void onClick(View view);
}
