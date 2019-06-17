package com.app.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.R;
import com.app.friendCircleMain.adapter.MyListAdapter;
import com.app.friendCircleMain.custonListView.CustomListView;
import com.app.friendCircleMain.domain.FirendMicroListDatas;
import com.app.friendCircleMain.domain.FriendMicroList;
import com.app.friendCircleMain.domain.FriendsMicro;
import com.app.friendcircle.PublishedActivity;
import com.app.model.Constant;
import com.app.model.MessageEvent;
import com.app.request.GetPostListFromGroupRequest;
import com.punuo.sys.app.activity.BaseSwipeBackActivity;
import com.punuo.sys.app.httplib.HttpManager;
import com.punuo.sys.app.httplib.RequestListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.app.model.Constant.devid1;

public class FamilyCircleActivity extends BaseSwipeBackActivity implements MyListAdapter.PositionListener {

    @Bind(R.id.iv_back7)
    ImageView ivBack7;
    @Bind(R.id.titleset)
    TextView titleset;
    @Bind(R.id.iv_fatie)
    ImageView ivFatie;
//    @Bind(R.id.pull_to_refresh)
//    PullToRefreshRecyclerView mPullToRefreshRecyclerView;

    TextView title;

    String SdCard = Environment.getExternalStorageDirectory().getAbsolutePath();

    private static final String TAG = "MicroActivity";
    int now = 0;
    private int pageNum = 1; //
    private List<FirendMicroListDatas> listdatas = new ArrayList<FirendMicroListDatas>();//json数据
    public CustomListView listview;
    public MyListAdapter mAdapter;//这是真正的
    private int i;
    private static int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_family_circle);
        ButterKnife.bind(this);

        init();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//因为不是所有的系统都可以设置颜色的，在4.4以下就不可以。。有的说4.1，所以在设置的时候要检查一下系统版本是否是4.1以上
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.newbackground));
        }
    }

    private GetPostListFromGroupRequest mGetPostListFromGroupRequest;
    private void getPostList(int page) {
        if (mGetPostListFromGroupRequest != null && !mGetPostListFromGroupRequest.isFinish()) {
            return;
        }
        boolean isFirstPage = (page == 1);
        showLoadingDialog("正在加载...");
        mGetPostListFromGroupRequest = new GetPostListFromGroupRequest();
        mGetPostListFromGroupRequest.addUrlParam("id", Constant.id);
        mGetPostListFromGroupRequest.addUrlParam("currentPage", page);
        mGetPostListFromGroupRequest.addUrlParam("groupid", Constant.groupid);
        mGetPostListFromGroupRequest.setRequestListener(new RequestListener<FriendsMicro>() {
            @Override
            public void onComplete() {
                dismissLoadingDialog();
            }

            @Override
            public void onSuccess(FriendsMicro result) {
                if (result == null) {
                    return;
                }
                FriendMicroList friendMicroList = result.getPostList();
                if (friendMicroList == null) {
                    return;
                }
                List<FirendMicroListDatas> datas = friendMicroList.getDatas();
                if (isFirstPage) {
                    listdatas.clear();
                }
                if (datas == null || datas.isEmpty()) {
                    if (isFirstPage) {
                        listview.onRefreshComplete();
                    } else {
                        listview.onLoadMoreComplete(false);
                    }
                } else {
                    if (isFirstPage) {
                        listview.onRefreshComplete();
                    } else {
                        listview.onLoadMoreComplete();
                    }
                    listdatas.addAll(datas);
                }
                int k = listdatas.size();
                now = k > 0 ? k - 1 : 0;
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Exception e) {
                if (isFirstPage) {
                    listview.onRefreshComplete();
                } else {
                    listview.onLoadMoreComplete();
                }
            }
        });
        HttpManager.addRequest(mGetPostListFromGroupRequest);
    }


    public void refresh() {
        pageNum = 1;
        getPostList(pageNum);
    }

    private void init() {
//        mPullToRefreshRecyclerView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<RecyclerView>() {
//            @Override
//            public void onRefresh(PullToRefreshBase<RecyclerView> refreshView) {
//                refresh();
//            }
//        });
//        RecyclerView recyclerView = mPullToRefreshRecyclerView.getRefreshableView();
        listview = (CustomListView) findViewById(R.id.list);
        listview.setVerticalScrollBarEnabled(false);
        listview.setDivider(null);
        mAdapter = new MyListAdapter(this, listdatas);
        listview.setAdapter(mAdapter);
        mAdapter.setPositionListener(this);
        if ((devid1 == null) || ("".equals(devid1))) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this)
                    .setTitle("请先绑定设备")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
            dialog.show();

        } else {
            listdatas.clear();
            refresh();
        }
        //下拉刷新
        listview.setOnRefreshListener(new CustomListView.OnRefreshListener() {

            @Override
            public void onRefresh() {
                refresh();
            }

        });
        //上拉加载更多
        listview.setOnLoadListener(new CustomListView.OnLoadMoreListener() {

            public void onLoadMore() {
                pageNum = pageNum + 1;
                getPostList(pageNum);
            }
        });
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        if (event.getMessage().equals("刷新")) {
            refresh();
        } else if (event.getMessage().equals("刷新点赞")) {
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void setPosition(int position) {
        i = position;
    }

    @OnClick({R.id.iv_back7, R.id.iv_fatie})
    public void onClock(View v) {
        switch (v.getId()) {
            case R.id.iv_back7:
                finish();
                break;
            case R.id.iv_fatie:
                startActivity(new Intent(this, PublishedActivity.class));
                break;
        }
    }

}
