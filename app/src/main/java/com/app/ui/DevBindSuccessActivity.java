package com.app.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.R;
import com.app.model.Constant;
import com.app.model.PNBaseModel;
import com.app.request.UnBindDevRequest;
import com.app.sip.BodyFactory;
import com.app.sip.SipInfo;
import com.app.sip.SipMessageFactory;
import com.punuo.sys.app.activity.BaseActivity;
import com.punuo.sys.app.httplib.HttpManager;
import com.punuo.sys.app.httplib.RequestListener;
import com.punuo.sys.app.util.ToastUtils;

import org.zoolu.sip.address.NameAddress;
import org.zoolu.sip.address.SipURL;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.app.model.Constant.devid1;
import static com.app.sip.SipInfo.devName;


public class DevBindSuccessActivity extends BaseActivity {

    @Bind(R.id.iv_bindsuccess)
    ImageView ivBindsuccess;
    @Bind(R.id.tv_devname)
    TextView tvDevname;
    @Bind(R.id.tv_devnumber)
    TextView tvDevnumber;
    @Bind(R.id.bt_unbind1)
    Button btUnbind1;
    @Bind(R.id.iv_back8)
    ImageView ivBack8;
    @Bind(R.id.textView4)
    TextView textView4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dev_bind_success);
        ButterKnife.bind(this);
        changStatusIconColor(true);
        TextPaint tp = textView4.getPaint();
        tp.setFakeBoldText(true);
        if (devid1 != null) {
            tvDevnumber.setText(SipInfo.paddevId);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//因为不是所有的系统都可以设置颜色的，在4.4以下就不可以。。有的说4.1，所以在设置的时候要检查一下系统版本是否是4.1以上
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.white));
        }

    }

    @OnClick({R.id.bt_unbind1, R.id.iv_back8})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back8:
                finish();
            case R.id.bt_unbind1:
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setCancelable(false)
                        .setMessage("是否解绑")
                        .setNegativeButton("否", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if ((devid1 != null) && !("".equals(devid1))) {
                                    unBindDev();
                                }
                            }
                        }).create();
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
                break;
        }
    }

    private UnBindDevRequest mUnBindDevRequest;

    private void unBindDev() {
        if (mUnBindDevRequest != null && mUnBindDevRequest.isFinish()) {
            return;
        }
        showLoadingDialog();
        mUnBindDevRequest = new UnBindDevRequest();
        mUnBindDevRequest.addUrlParam("id", Constant.id);
        mUnBindDevRequest.addUrlParam("groupid", Constant.id);
        mUnBindDevRequest.addUrlParam("devid", Constant.devid1);
        mUnBindDevRequest.setRequestListener(new RequestListener<PNBaseModel>() {
            @Override
            public void onComplete() {
                dismissLoadingDialog();
            }

            @Override
            public void onSuccess(PNBaseModel result) {
                if (result == null) {
                    return;
                }
                if (result.isSuccess()) {
                    String devId = SipInfo.paddevId;
                    SipURL sipURL = new SipURL(devId, SipInfo.serverIp, SipInfo.SERVER_PORT_USER);
                    SipInfo.toDev = new NameAddress(devName, sipURL);
                    org.zoolu.sip.message.Message query = SipMessageFactory.createNotifyRequest(SipInfo.sipUser, SipInfo.toDev,
                            SipInfo.user_from, BodyFactory.createListUpdate("addsuccess"));
                    SipInfo.sipUser.sendMessage(query);
                    devid1 = "";
                    ToastUtils.showToastShort("解绑成功");
                    finish();
                } else {
                    onError(null);
                }
            }

            @Override
            public void onError(Exception e) {
                ToastUtils.showToastShort("解绑失败,请重试");
            }
        });
        HttpManager.addRequest(mUnBindDevRequest);
    }

    public void changStatusIconColor(boolean setDark) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decorView = getWindow().getDecorView();
            if (decorView != null) {
                int vis = decorView.getSystemUiVisibility();
                if (setDark) {
                    vis |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                } else {
                    vis &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                }
                decorView.setSystemUiVisibility(vis);
            }
        }
    }
}
