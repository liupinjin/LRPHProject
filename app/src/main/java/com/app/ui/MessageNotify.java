package com.app.ui;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.app.R;
import com.app.http.GetPostUtil;
import com.app.model.Constant;
import com.punuo.sys.app.activity.BaseActivity;
import com.punuo.sys.app.util.ToastUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.app.model.Constant.isNotify;


public class MessageNotify extends BaseActivity {

    @Bind(R.id.btn_switch)
    SwitchCompat btnSwitch;
    @Bind(R.id.iv_back1)
    ImageView ivBack1;
    @Bind(R.id.titleset)
    TextView titleset;
    String response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_notify);
        ButterKnife.bind(this);
        titleset.setText("新消息通知");
        if("1".equals(isNotify)) {
            btnSwitch.setChecked(true);
        }else
            if("2".equals(isNotify)){
                btnSwitch.setChecked(false);
            }

        TextPaint tp=titleset.getPaint();
        tp.setFakeBoldText(true);
        initView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//因为不是所有的系统都可以设置颜色的，在4.4以下就不可以。。有的说4.1，所以在设置的时候要检查一下系统版本是否是4.1以上
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.newbackground));
        }
    }

    private void initView() {
        btnSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isNotify="1";
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            response= GetPostUtil.sendGet1111(Constant.URL_updateNotify,"id="+Constant.id
                                    +"&notify="+isNotify);
                            if((response!=null)&&!("".equals(response))){
                                JSONObject  obj= JSON.parseObject(response);
                                String msg=obj.getString("msg");
                                if(msg.equals("success"))
                                    Log.i("messageNotify","成功");
                            }
                        }
                    }).start();
//                    response = GetPostUtil.sendGet1111(Constant.URL_updateNotify, "id=" + Constant.id
//                            + "&notify" + 1);
//                    if ((response != null) && !("".equals(response))) {
//                        JSONObject obj = JSON.parseObject("msg");
//                        String msg = obj.toString();
//                        if (msg.equals("success"))
//                            Log.i("messageNotify", "成功");
//                    }
                } else {
                    isNotify="2";
                   new Thread(new Runnable() {
                       @Override
                       public void run() {
                           response= GetPostUtil.sendGet1111(Constant.URL_updateNotify,"id="+Constant.id
                                   +"&notify="+isNotify);
                           if((response!=null)&&!("".equals(response))){
                               JSONObject  obj= JSON.parseObject(response);
                               String msg=obj.getString("msg");
                               if(msg.equals("success"))
                                   Log.i("messageNotify","成功");
                           }
                       }
                   }).start();
//                    response = GetPostUtil.sendGet1111(Constant.URL_updateNotify, "id=" + Constant.id
//                            + "&notify" + 1);
//                    if ((response != null) && !("".equals(response))) {
//                        JSONObject obj = JSON.parseObject("msg");
//                        String msg = obj.toString();
//                        if (msg.equals("success"))
//                            Log.i("messageNotify", "成功");
//                    }
                }
            }
        });
    }

    private Runnable updateNotify=new Runnable(){
        @Override
        public void run()
        {
            response= GetPostUtil.sendGet1111(Constant.URL_updateNotify,"id="+Constant.id
                    +"&notify"+1);
            if((response!=null)&&!("".equals(response))){
                JSONObject  obj= JSON.parseObject("msg");
                String msg=obj.toString();
                if(msg.equals("success"))
                    ToastUtils.showToastShort("成功");
            }
            ToastUtils.showToastShort("开");
        }
    };

    @OnClick({R.id.iv_back1})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back1:
                finish();
                break;
        }
    }
}
