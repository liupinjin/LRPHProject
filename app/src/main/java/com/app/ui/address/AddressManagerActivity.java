package com.app.ui.address;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.R;
import com.app.adapter.AddressItemAdapter;
import com.app.model.AddressResult;
import com.app.model.Constant;
import com.app.model.MessageEvent;
import com.app.request.GetAddressListRequest;
import com.app.sip.SipInfo;
import com.punuo.sys.app.activity.BaseActivity;
import com.punuo.sys.app.httplib.HttpManager;
import com.punuo.sys.app.httplib.RequestListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddressManagerActivity extends BaseActivity {
    @Bind(R.id.iv_back1)
    ImageView ivBack1;
    @Bind(R.id.titleset)
    TextView titleset;
    @Bind(R.id.rv_addressDispaly)
    RecyclerView rvAddressDispaly;
    @Bind(R.id.iv_addressicon)
    ImageView ivAddressicon;
    @Bind(R.id.tv_noAddress)
    TextView tvNoAddress;
    @Bind(R.id.btn_newAddress)
    Button btnNewAddress;

    private AddressItemAdapter mAddressItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_supervise);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        init();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvAddressDispaly.setLayoutManager(layoutManager);
        mAddressItemAdapter = new AddressItemAdapter(this, new ArrayList<>());
        rvAddressDispaly.setAdapter(mAddressItemAdapter);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//因为不是所有的系统都可以设置颜色的，在4.4以下就不可以。。有的说4.1，所以在设置的时候要检查一下系统版本是否是4.1以上
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.white));
        }

    }

    private void init() {
        titleset.setText("地址管理");
        TextPaint tp = titleset.getPaint();
        tp.setFakeBoldText(true);
        getAddressList();
    }

    private GetAddressListRequest mGetAddressListRequest;

    //获取收货地址
    public void getAddressList() {
        if (mGetAddressListRequest != null && !mGetAddressListRequest.isFinish()) {
            return;
        }
        mGetAddressListRequest = new GetAddressListRequest();
        mGetAddressListRequest.addUrlParam("id", Constant.id);
        mGetAddressListRequest.setRequestListener(new RequestListener<AddressResult>() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onSuccess(AddressResult result) {
                if (result == null) {
                    return;
                }
                if (result.mAddressItems == null || result.mAddressItems.isEmpty()) {
                    rvAddressDispaly.setVisibility(View.INVISIBLE);
                    ivAddressicon.setVisibility(View.VISIBLE);
                    tvNoAddress.setVisibility(View.VISIBLE);
                } else {
                    ivAddressicon.setVisibility(View.INVISIBLE);
                    tvNoAddress.setVisibility(View.INVISIBLE);
                    rvAddressDispaly.setVisibility(View.VISIBLE);
                    mAddressItemAdapter.appendData(result.mAddressItems);

                    SipInfo.addressList = result.mAddressItems;
                }
            }

            @Override
            public void onError(Exception e) {

            }
        });
        HttpManager.addRequest(mGetAddressListRequest);
    }


    @OnClick({R.id.btn_newAddress, R.id.iv_back1})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_newAddress:
                SipInfo.isEditor = false;
                startActivity(new Intent(this, AddressDetailActivity.class));
                break;
            case R.id.iv_back1:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        if (event.getMessage().equals("编辑")) {
            SipInfo.isEditor = true;
            startActivity(new Intent(this, AddressDetailActivity.class));
        } else if (event.getMessage().equals("刷新")) {
            getAddressList();
//            mAddressItemAdapter.appendData(addressList);
        }
    }
}
