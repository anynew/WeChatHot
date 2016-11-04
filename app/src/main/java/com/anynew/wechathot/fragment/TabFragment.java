package com.anynew.wechathot.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.anynew.wechathot.R;
import com.anynew.wechathot.activity.WxContentActivity;
import com.anynew.wechathot.adapter.DividerItemDecoration;
import com.anynew.wechathot.model.HomeSource;
import com.anynew.wechathot.model.PicSource;
import com.anynew.wechathot.parser.Parser;
import com.anynew.wechathot.ui.Kanner;
import com.anynew.wechathot.utils.Constant;
import com.anynew.wechathot.utils.NetUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.ybq.android.spinkit.SpinKitView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;
import com.zhy.adapter.recyclerview.wrapper.LoadMoreWrapper;

import butterknife.Bind;
import butterknife.ButterKnife;


public class TabFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, Kanner.OnItemClickListener {

    @Bind(R.id.mRecycleView)
    RecyclerView mRecycleView;
    @Bind(R.id.mSwipeRefresh)
    SwipeRefreshLayout mSwipeRefresh;
//    @Bind(R.id.mNestedScrollView)
//    NestedScrollView mNestedScrollView;

    private CommonAdapter<String> mAdapter;

    private HeaderAndFooterWrapper mHeaderAndFooterWrapper;

    private LoadMoreWrapper mLoadMoreWrapper;

    private String TAG = this.getClass().getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        int flag = getArguments().getInt("flag");
        mSwipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        //flag = 0代表获取到的首页fragment
        if (flag == 0) {
            if (NetUtils.isConnected(getActivity())) {

              /*  mNestedScrollView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        switch (event.getAction()){
                            case MotionEvent.ACTION_UP:

                                if (mNestedScrollView.getChildAt(0).getMeasuredHeight() <= mNestedScrollView.getHeight() + mNestedScrollView.getScrollY()){
                                    Log.e(TAG, "onTouch: 底部");
                                    Log.e(TAG, "onTouch: " +  "mNestedScrollView.getChildAt(0).getMeasuredHeight() = " + mNestedScrollView.getChildAt(0).getMeasuredHeight()
                                            +"\n"+"mNestedScrollView.getHeight() = " +mNestedScrollView.getHeight()
                                            + "\n" + "mNestedScrollView ScrollY = " +mNestedScrollView.getScrollY());

                                }
                                break;
                            case MotionEvent.ACTION_DOWN:

                                break;
                            case MotionEvent.ACTION_MOVE:


                                break;
                        }
                        return false;

                    }
                });*/
                initSwipeRefresh();
                requestData();
            } else {
                //无网络处理
                showSnack();
                mSwipeRefresh.setOnRefreshListener(this);
            }
        } else {
            //这里处理其他fragment

        }
    }

    /**
     * 弹出提示没有网络
     */
    private void showSnack() {
        if (onNotifyListener != null) {
            onNotifyListener.onSnack(1, "(⊙ ︿ ⊙)  网络开小差了");
        }
    }

    /**
     * 获取首页fragment的数据
     */
    private void requestData() {
        if (NetUtils.isConnected(getActivity())) {
            new ImageNetThread().start();
        } else {
            mSwipeRefresh.setRefreshing(false);
        }
    }


    /**
     * 下拉刷新的回调
     */
    @Override
    public void onRefresh() {
        requestData();
        if (!NetUtils.isConnected(getActivity())) {
            showSnack();
        }
    }

    /**
     * 下拉刷新
     */
    private void initSwipeRefresh() {
        mSwipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefresh.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefresh.setRefreshing(true);

            }
        });
        mSwipeRefresh.setOnRefreshListener(this);
    }

    /**
     * 下面为爬虫获取到的数据
     */

    public static final int MSG_HOMESOURCE_COMPLETED = 1;      //LIST集合成功标志
    public static final int MSG_RECEIVE_OUTTIME = 3;      //超时连接

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_HOMESOURCE_COMPLETED:

                    mSwipeRefresh.setRefreshing(false);
                    Parser parser = (Parser) msg.obj;
                    initRecycler(parser);
                    break;
                case MSG_RECEIVE_OUTTIME:
                    Toast.makeText(getActivity(), "获取超时", Toast.LENGTH_SHORT).show();
//                    new ImageNetThread().start();
                    break;
            }
        }
    };


    /**
     * 初始化首页列表
     */
    private void initRecycler(final Parser parser) {
        //轮播实体
        final PicSource picSource = parser.getPicSource();
        //首页新闻列表实体
        final HomeSource homeSource = parser.getHomeSource();

        mRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecycleView.setItemAnimator(new DefaultItemAnimator());
        mRecycleView.setNestedScrollingEnabled(false);
        mRecycleView.setHasFixedSize(false);


        mAdapter = new CommonAdapter<String>(getActivity(), R.layout.item_layout_home_list, homeSource.getListTitle()) {
            @Override
            protected void convert(final ViewHolder holder, String s, final int position) {
                holder.setText(R.id.mTitle, homeSource.getListTitle().get(position - 1));
                holder.setText(R.id.mViews, homeSource.getListViews().get(position - 1));
                holder.setText(R.id.mTvFrom, homeSource.getListFrom().get(position - 1));
                holder.setOnClickListener(R.id.mItem, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(getActivity(), WxContentActivity.class);

                        int[] startingLocation = new int[2];
                        v.getLocationOnScreen(startingLocation);
                        startingLocation[0] += v.getWidth() / 2;

                        intent.putExtra(Constant.START_LOCATION, startingLocation);
                        intent.putExtra("urlLink", homeSource.getListGoto().get(position - 1));
                        intent.putExtra("source", homeSource.getListFrom().get(position - 1));
                        intent.putExtra("img", homeSource.getListIllustrator().get(position - 1));
                        startActivity(intent);
                    }
                });
                ImageView iv = holder.getView(R.id.mIllustrator);
                Glide.with(getActivity())
                        .load(homeSource.getListIllustrator().get(position - 1))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(iv);
            }
        };
        //设置HeadView
        View headView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_headview, null);
        Kanner kanner = (Kanner) headView.findViewById(R.id.kanner);
        kanner.setTopEntities(picSource);
        kanner.setOnItemClickListener(this);

        //设置LoadMore
        View loadMore = LayoutInflater.from(getActivity()).inflate(R.layout.layout_loadmore, null);
        SpinKitView mProgressBar = (SpinKitView) loadMore.findViewById(R.id.mProgressBar);

        mHeaderAndFooterWrapper = new HeaderAndFooterWrapper(mAdapter);
        mHeaderAndFooterWrapper.addHeaderView(headView);

        mLoadMoreWrapper = new LoadMoreWrapper(mHeaderAndFooterWrapper);
        mLoadMoreWrapper.setLoadMoreView(loadMore);
        mLoadMoreWrapper.setOnLoadMoreListener(new LoadMoreWrapper.OnLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                Log.e(TAG, "onLoadMoreRequested: is" );
            }
        });

        mRecycleView.setAdapter(mLoadMoreWrapper);

        //添加分割线
        mRecycleView.addItemDecoration(new DividerItemDecoration(
                getActivity(), DividerItemDecoration.VERTICAL_LIST));

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void click(View v, int position, PicSource picSource) {
        Intent intent = new Intent(getActivity(), WxContentActivity.class);
        Log.e(TAG, "click: " + picSource.getListImg().get(position));
        int[] startingLocation = new int[2];
        v.getLocationOnScreen(startingLocation);
        startingLocation[0] += v.getWidth() / 2;

        intent.putExtra(Constant.START_LOCATION, startingLocation);
        intent.putExtra("urlLink", picSource.getListLink().get(position));
        intent.putExtra("source", picSource.getListSource().get(position));
        intent.putExtra("img", picSource.getListImg().get(position));
        startActivity(intent);
    }


    /**
     * 开启网络获取数据
     */
    private class ImageNetThread extends Thread {
        @Override
        public void run() {
            super.run();
            try {
                Parser parser = new Parser();
                handler.sendMessage(handler.obtainMessage(MSG_HOMESOURCE_COMPLETED, parser));

            } catch (Exception e) {
                handler.sendMessage(handler.obtainMessage(MSG_RECEIVE_OUTTIME));
                Log.e(TAG, "run: Exception " + e.toString());
                e.printStackTrace();
            }

        }
    }

    /***
     * 下面代码用与宿主Activity进行通信
     *
     * @param activity
     */


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnNotifyListener) {
            onNotifyListener = (OnNotifyListener) activity;
        } else {
            throw new IllegalArgumentException("activity must implements OnNotifyListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onNotifyListener = null;
    }

    private OnNotifyListener onNotifyListener;

    public interface OnNotifyListener {
        //flag代表传入snackbar是否纯文本及类型
        void onSnack(int flag, String tips);
    }

}
