package com.cy.host;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.view.View;

import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;


/**<pre>使用帮助：
 * 1、初始化
 * setInitPullHeaderView(true);
 * </pre>
 */
public abstract class BasePullLoadMoreActivity extends BaseHostActivity{
    public PtrClassicFrameLayout ptrClassicFrameLayout;


    public int mFirstPage ;
    /**
     * 当前page
     */
    public int page = 0;

    public boolean baseGetSetedCanloadMore() {
        return setedCanloadMore;
    }

    private boolean setedCanloadMore=false;
    private boolean mIsPullEnabled = true;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    /**默认起始页为0
     * @param canLoadmore
     */
    public void baseInitPullLoadMoreView(boolean canLoadmore, @IdRes int r_id_ptrlayout){
        baseInitPullLoadMoreView(canLoadmore,0, r_id_ptrlayout);
    }

    public void baseInitPullLoadMoreView(boolean canLoadmore, int firstPage, @IdRes int r_id_ptrlayout) {
        mFirstPage=firstPage;
        this.page=firstPage;
        setedCanloadMore=canLoadmore;
        baseSetIsPullEnabled(true);

//        ptrClassicFrameLayout = (PtrClassicFrameLayout) findViewById(r_id_ptrlayout);
        baseSetLoadMoreEnable(canLoadmore);
        // default is false
        ptrClassicFrameLayout.setPullToRefresh(false);

        // default is true
        ptrClassicFrameLayout.setKeepHeaderWhenRefresh(true);

        ptrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler() {

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //还原是否可以loadmore状态
                        baseSetLoadMoreEnable(setedCanloadMore);
                        page = mFirstPage;
                        onPullData(mFirstPage);
                        ptrClassicFrameLayout.refreshComplete();
//                        ProgressHide();
                    }
                });
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                if (mIsPullEnabled) {
                    return super.checkCanDoRefresh(frame, content, header);
                } else {
                    return mIsPullEnabled;
                }
            }

        });

        ptrClassicFrameLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void loadMore() {
                handler.post(new Runnable() {

                    @Override
                    public void run() {
                        page++;
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                onPullData(page);
//                                ptrClassicFrameLayout.loadMoreComplete(true);
                            }
                        });
                    }
                });
            }
        });

    }

    protected abstract void onPullData(int page);

    protected void baseSetLoadMoreEnable(boolean value) {
        ptrClassicFrameLayout.setLoadMoreEnable(value);
    }

    protected void baseSetLoadMoreComplete(boolean value) {
        ptrClassicFrameLayout.loadMoreComplete(value);
    }

    /**如果不为0，请设置起始page
     * @param page
     */
    public void baseSetFirstPage(int page) {
        this.mFirstPage = page;
        this.page=page;
    }

    protected void baseSetIsPullEnabled(boolean isEnabled) {
        mIsPullEnabled = isEnabled;
    }
}
