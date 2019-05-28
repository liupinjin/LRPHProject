package com.app.ui.fragment;


import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.R;
import com.maogousoft.ytwebview.YTWebView;
import com.maogousoft.ytwebview.interf.OnRefreshWebViewListener;
import com.punuo.sys.app.util.StatusBarUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommunityFragment extends Fragment {
    @Bind(R.id.ytWebView)
    YTWebView ytWebView;
    @Bind(R.id.status_bar)
    View mStatusBar;
//    @Bind(R.id.ytWebView)


//    @Bind(R.id.web_view)
//    WebView webView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_community, container, false);
//        ButterKnife.bind(this, view);
        ButterKnife.bind(this, view);
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mStatusBar.setVisibility(View.VISIBLE);
            mStatusBar.getLayoutParams().height = StatusBarUtil.getStatusBarHeight(getActivity());
            mStatusBar.requestLayout();
        }
    }

    private void init() {
        ytWebView.setOnRefreshWebViewListener(new OnRefreshWebViewListener() {
            @Override
            public void onRefresh() {
                // 模拟接口调用3秒
                new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        //调用接口结束
                        ytWebView.setRefreshSuccess();
//                        ytWebView.setRefreshFail();
                    }
                }.sendEmptyMessageDelayed(0, 3000);
            }
        });
//        ytWebView.getSettings().setJavaScriptEnabled(true);
//        ytWebView.setWebViewClient(new WebViewClient());
        ytWebView.getWebView().loadUrl("http://118.31.71.150:8888/mobilecommunity/");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}

