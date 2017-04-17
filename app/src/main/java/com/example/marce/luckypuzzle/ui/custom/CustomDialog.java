package com.example.marce.luckypuzzle.ui.custom;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;

import com.example.marce.luckypuzzle.R;

/**
 * Created by marce on 16/04/17.
 */

public class CustomDialog extends Dialog {

    public CustomDialog(Context context, int themeResId,int layout) {
        super(context, themeResId);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCancelable(false);
        setContentView(layout);
    }
}
