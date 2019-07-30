package com.app.ui.fragment;


import android.support.v4.app.Fragment;

import com.app.sip.SipInfo;
import com.punuo.sys.app.fragment.WebViewFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommunityFragment extends WebViewFragment {

    @Override
    public String getUrl() {
        return "http://pet.qinqingonline.com:8889"+ SipInfo.userId;
    }
}

