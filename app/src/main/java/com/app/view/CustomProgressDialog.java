package com.app.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.app.R;


public class CustomProgressDialog extends Dialog {

    public CustomProgressDialog(Context context, int themeResId) {
        super(context, themeResId);
    }
    public CustomProgressDialog(Context context) {
        this(context,  R.style.CustomProgressDialog);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customprogressdialog);
    }
}
