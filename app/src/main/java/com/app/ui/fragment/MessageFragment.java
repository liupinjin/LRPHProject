package com.app.ui.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.R;
import com.app.ui.FamilyCircleActivity;
import com.app.ui.message.SystemNotify;
import com.app.view.CircleImageView;
import com.punuo.sys.app.util.StatusBarUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by maojianhui on 2018/10/18.
 */

public class MessageFragment extends Fragment {
    @Bind(R.id.iv_huifu)
    CircleImageView ivHuifu;
    @Bind(R.id.iv_zan)
    CircleImageView ivZan;
    @Bind(R.id.iv_tongzhi)
    CircleImageView ivTongzhi;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.system_notify)
    TextView systemNotify;
    @Bind(R.id.rl_systemNotify)
    RelativeLayout rlSystemNotify;
    @Bind(R.id.camera111)
    ImageButton camera111;
    @Bind(R.id.btnCall)
    ImageButton btnCall;
    @Bind(R.id.iv_logo)
    ImageView ivLogo;
    @Bind(R.id.rl_huifu)
    RelativeLayout rlHuifu;
    @Bind(R.id.rl_dianzan)
    RelativeLayout rlDianzan;
    @Bind(R.id.status_bar)
    View mStatusBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message1, container, false);
        ButterKnife.bind(this, view);
        title.setText("消息");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mStatusBar.setVisibility(View.VISIBLE);
            mStatusBar.getLayoutParams().height = StatusBarUtil.getStatusBarHeight(getActivity());
            mStatusBar.requestLayout();
        }
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @OnClick({R.id.iv_huifu, R.id.iv_zan, R.id.rl_systemNotify,R.id.rl_dianzan,R.id.rl_huifu})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_huifu:
                startActivity(new Intent(getActivity(), FamilyCircleActivity.class));
                break;
            case R.id.rl_dianzan:
                startActivity(new Intent(getActivity(), FamilyCircleActivity.class));
                break;
            case R.id.rl_systemNotify:
                startActivity(new Intent(getActivity(), SystemNotify.class));
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
