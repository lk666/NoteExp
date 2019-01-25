package com.cn.lk.androidexp.annotation.processor;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.cn.lk.androidexp.R;

import lk.cn.com.lib_annotation.InjectView;
import lk.cn.com.lib_annotation.InjectViewer;

public class BindActivity extends Activity {
    @InjectView(R.id.tv)
    TextView tv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind);
        InjectViewer.bind(this);

        tv.setText("绑定");
    }
}
