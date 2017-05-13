package com.pingan.demo.loadframe;

import android.text.format.DateUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.pingan.demo.R;
import com.pingan.demo.base.BaseActivity;
import com.pingan.demo.refreshlist.XListView;
import com.pingan.http.framework.model.response.BaseResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by xra on 16/3/30.
 */
public class ListDataLoadHandler implements View.OnClickListener, AdapterView.OnItemClickListener,
                                            XListView.IXListViewListener,
                                            XListView.OnScrollAutoLoad {
    /**
     * 当前容器
     */
    private BaseActivity activity;


    // refresh标示符
    public static final int INIT_DATA = 0;
    public static final int PULL_DOWN_TO_REFRESH = 1;
    public static final int PULL_UP_TO_DOWNLOAD = 2;
    public static final int PAGE_SIZE = 10;// 分页的每页显示数量
    public static final int NO_DATA = 0;
    public static final int NO_NET = 1;
    public static final int EXCEPTION = 2;

    /**
     * 当前页
     */
    private int currentPage;
    /**
     * 页长度
     */
    private int pagesize = PAGE_SIZE;
    /**
     * 数据返回接口
     */
    private IParamProvider paramProvider;
    /**
     * 数据总量
     */
    private int totalCount;
    /**
     * 错误类型(当网络请求中,出现的一些情况)
     */
    private int errorType;
    /**
     * 下拉刷新时候的布局
     */
    private View refreshView;
    /**
     * 列表控件
     */
    private XListView listView;
    /**
     * 网络请求中回调接口
     */
    private ILoadCallback loadCallback;
    /**
     * 数据容器
     */
    private Map<String, Object> mParams;
    /**
     * 数据返回的集合
     */
    private List<?> listData;
    /**
     * 列表适配器
     */
    private BaseAdapter adapter;
    /**
     * 接口路径
     */
    private String url;

    /**
     * 没有数据时候的提示语
     */
    private int noDataMessage;

    /**
     * 没有该数据的类型logo
     */
    private int nodataDrawable;

    /**
     * list更新时候的接口适配
     */
    private IListRefresh listRefresh;

    /**
     * 非列表请求成功后的接口回调
     */
    private ILoadSuccessCallback loadSuccessCallback;

    /**
     * 非列表请求失败后的接口回调
     */
    private ILoadFailCallback loadFailCallback;


    private ICallbackItemView callbackItemView;
    private ISelfNotify selfNotify;


    /**
     * 回到顶部按钮
     */
    private Button mToTopShortcut;

    private Class mClass;

    boolean isItemEnable = true;

    /**
     * 是否显示回到顶部按钮
     */
    private boolean isEnableTotop = true;

    private int mCurrentFlag = -1;

    public ListDataLoadHandler() {
    }


    public void setListView(XListView listView) {
        this.listView = listView;
        this.listView.hindleFootView();
        this.listView.setXListViewListener(this);
        this.listView.setOnItemClickListener(this);
        this.listView.setScrollAutoLoad(this);
    }

    /**
     * 针对网络请求后的异常处理显示 @param icon @param title @param msg @param action
     */
    public void showExceptionView(int icon, int title, String msg, int action) {
        changeShortcut(false);
        if (listView != null && mCurrentFlag == INIT_DATA && refreshView != null) {
            refreshView.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
            ImageView refreshViewImg = (ImageView) refreshView
                    .findViewById(R.id.widget_request_img);
            refreshViewImg.setImageResource(icon);
            TextView refreshViewTitle = (TextView) refreshView.findViewById(R.id.widget_title_text);
            refreshViewTitle.setText(activity.getString(title));
            TextView refreshViewBody = (TextView) refreshView.findViewById(R.id.widget_body_text);
            refreshViewBody.setText(msg);
            TextView refreshViewBtn = (TextView) refreshView
                    .findViewById(R.id.widget_refresh_button);
            if (action == -1) {
                refreshViewBtn.setVisibility(View.INVISIBLE);
            }
            refreshViewBtn.setOnClickListener(this);
        }
    }

    /**
     * 没有数据//
     */
    private void noData(int message) {
        errorType = NO_DATA;
        showExceptionView(nodataDrawable, message, activity.getString(R.string.alert_message_body),
                -1);
    }

    /**
     * 无更多数据
     */
    private void noMoreData() {
        listView.hindleFootView();
    }


    /**
     * 没有网络
     */
    public void noNet() {
        errorType = NO_NET;
        showExceptionView(R.drawable.wifi, R.string.alert_no_internet,
                activity.getString(R.string.alert_check_internet), R.string.response_again);
    }

    /**
     * 服务器异常
     */
    private void exception(String msg) {
        errorType = EXCEPTION;
        showExceptionView(R.drawable.wifi, R.string.request_error, msg, R.string.response_again);
    }

    /**
     * 停止加载数据
     */
    private void stopLoad() {
        if (listView != null) {
            listView.stopRefresh();
            listView.stopLoadMore();
            String time = DateUtils.formatDateTime(activity, System.currentTimeMillis(),
                    DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE
                            | DateUtils.FORMAT_ABBREV_ALL);
            listView.setRefreshTime(time);

            if (currentPage == 0) {
                listView.setSelection(0);
            }

            if (listView.getFirstVisiblePosition() == 0) {
                changeShortcut(false);
            } else {
                changeShortcut(true);
            }
        }
    }


    /**
     * 列表接口调用 @param flag
     */
    private void getData(final int flag) {

        if (refreshView != null) {
            refreshView.setVisibility(View.GONE);
        }

        if (flag == INIT_DATA || flag == PULL_DOWN_TO_REFRESH) {
            currentPage = 0;
        }
        totalCount = paramProvider.getCount();
        if (totalCount > 0 && totalCount <= pagesize) {
            listView.setPullLoadEnable(false);
        }
        List list = paramProvider.getList();
        if (listData == null) {
            listData = new ArrayList<Object>();
        }
        if (totalCount > 0) {
            listView.setVisibility(View.VISIBLE); /* 显示listView*/
            if (flag != PULL_UP_TO_DOWNLOAD) {
                listData.clear();
            }
            listData.addAll(list);

            if (selfNotify != null) {
                selfNotify.onSelfNotify(listData, flag);
            } else {
                if (adapter == null) {
                    adapter = paramProvider.getAdapter(listData);
                    listView.setAdapter(adapter);
                } else {
                    adapter.notifyDataSetChanged();
                }
            }

            setTopShortcutOnClickListener();
            stopLoad();
        }
        if (totalCount == 0 && loadCallback != null) {
            noData(noDataMessage);
        }
        if (totalCount > 0 && list.size() == 0) {
            noMoreData();
        }
    }

    private void setTopShortcutOnClickListener() {
        if (isEnableTotop) {

            //            if (mToTopShortcut == null) {
            //                mToTopShortcut = activity.getToTopShortcut();
            //            }
            mToTopShortcut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listView.setSelection(0);
                    mToTopShortcut.setVisibility(View.GONE);
                }
            });
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.widget_refresh_button:
                if (errorType == NO_NET || errorType == EXCEPTION) {
                    refreshView.setVisibility(View.GONE);
                    getData(INIT_DATA);
                }
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        if (isItemEnable) {
            isItemEnable = false;
            if (position >= (listData.size() + 1)) {
                if (pagesize * (currentPage + 1) <= totalCount) {
                    currentPage++; /*加载更多*/
                    getData(PULL_UP_TO_DOWNLOAD);
                } else {
                    Toast.makeText(activity, R.string.no_more_item_data, Toast.LENGTH_SHORT).show();
                    listView.hindleFootView();
                }
            } else {
                //                if (activity.isActive()) {
                if (loadCallback != null) {
                    loadCallback.onItemClick(position, listData.get(position - 1));
                }
                if (callbackItemView != null) {
                    callbackItemView.onItemClick(position, listData.get(position - 1), view);
                }
                //                }
            }

            isItemEnable = true;
        }
    }

    @Override
    public void onLoadMore() {
        if (pagesize * (currentPage + 1) <= totalCount) {
            currentPage++;
            getData(PULL_UP_TO_DOWNLOAD);
        } else {
            listView.setPullLoadEnable(false);
            stopLoad();
        }
    }


    @Override
    public void changeShortcut(boolean isShow) {

        if (isEnableTotop) {
            if (mToTopShortcut != null) {
                if (isShow && mToTopShortcut.getVisibility() != View.VISIBLE) {
                    mToTopShortcut.setVisibility(View.VISIBLE);
                } else if (!isShow && mToTopShortcut.getVisibility() == View.VISIBLE) {
                    mToTopShortcut.setVisibility(View.GONE);
                }
            }
        }

    }


    @Override
    public void onRefresh() {
        listView.setPullLoadEnable(true);
        getData(PULL_DOWN_TO_REFRESH);
        if (listRefresh != null) {
            listRefresh.onListRefresh();
        }
    }

    @Override
    public void onItemLoadCall(int lastVisibleItem) {

        if (listData != null && lastVisibleItem == listData.size() - 2) {
            onLoadMore();
        }


    }

    public void setNoDataMessage(int noDataMessage) {
        this.noDataMessage = noDataMessage;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setRefreshView(View refreshView) {
        this.refreshView = refreshView;
    }

    public void setContext(BaseActivity context) {
        this.activity = context;
    }

    public void setParams(Map<String, Object> params) {
        this.mParams = params;
    }

    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }

    public void setLoadCallback(ILoadCallback iLoadCallback) {
        this.loadCallback = iLoadCallback;
    }

    public void setParamProvider(IParamProvider paramProvider) {
        this.paramProvider = paramProvider;
    }

    public void setNodataDrawable(int nodataDrawable) {
        this.nodataDrawable = nodataDrawable;
    }

    public void setListRefresh(IListRefresh listRefresh) {
        this.listRefresh = listRefresh;
    }

    public void loadData() {
        getData(INIT_DATA);
    }

    public void setIsEnableTotop(boolean isEnableTotop) {
        this.isEnableTotop = isEnableTotop;
    }

    public void setLoadSuccessCallback(ILoadSuccessCallback successCallback) {
        this.loadSuccessCallback = successCallback;
    }

    public void setCallbackItemView(ICallbackItemView callbackItemView) {
        this.callbackItemView = callbackItemView;
    }

    public void setSelfNotify(ISelfNotify notify) {
        this.selfNotify = notify;
    }

    public void setClass(Class type) {
        mClass = type;
    }

    public int getCurrentPage() {
        return currentPage;
    }
}