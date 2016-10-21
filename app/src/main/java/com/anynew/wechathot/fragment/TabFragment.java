package com.anynew.wechathot.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.anynew.wechathot.R;
import com.anynew.wechathot.model.HomeSource;
import com.anynew.wechathot.model.PicSource;
import com.anynew.wechathot.parser.Parser;
import com.anynew.wechathot.ui.Kanner;
import com.anynew.wechathot.utils.NetUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class TabFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.mRecycleView)
    RecyclerView mRecycleView;
    @Bind(R.id.mSwipeRefresh)
    SwipeRefreshLayout mSwipeRefresh;

    private CommonAdapter<String> mAdapter;

    private HeaderAndFooterWrapper mHeaderAndFooterWrapper;

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
                initSwipeRefresh();
                requestData();
            }else {
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
            onNotifyListener.onSnack("(⊙ ︿ ⊙)  网络开小差了");
        }
    }

    /**
     * 获取首页fragment的数据
     */
    private void requestData() {
        if (NetUtils.isConnected(getActivity())){
            new ImageNetThread().start();
        }else{
            mSwipeRefresh.setRefreshing(false);
        }
    }


    /**
     * 下拉刷新的回调
     */
    @Override
    public void onRefresh() {
        requestData();
        if (!NetUtils.isConnected(getActivity())){
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
        final HomeSource homeSource = parser.getHomeSource();
        final PicSource picSource = parser.getPicSource();

        mRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecycleView.setNestedScrollingEnabled(false);
        mRecycleView.setHasFixedSize(false);

        mAdapter = new CommonAdapter<String>(getActivity(), R.layout.item_layout_home_list, homeSource.getListTitle()) {
            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                holder.setText(R.id.mTitle, homeSource.getListTitle().get(position - 1));
                holder.setText(R.id.mViews, homeSource.getListViews().get(position - 1));
                holder.setText(R.id.mTvFrom, homeSource.getListFrom().get(position - 1));
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
        List<String> imgUrl = picSource.getListImg();
        List<String> listTitle = picSource.getListTitle();
        String[] urls = imgUrl.toArray(new String[imgUrl.size()]);
        kanner.setImagesUrl(urls, listTitle);

        mHeaderAndFooterWrapper = new HeaderAndFooterWrapper(mAdapter);
        mHeaderAndFooterWrapper.addHeaderView(headView);
        mRecycleView.setAdapter(mHeaderAndFooterWrapper);
        mHeaderAndFooterWrapper.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
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
     *
     * 下面代码用与宿主Activity进行通信
     *
     * @param activity
     */


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnNotifyListener){
            onNotifyListener = (OnNotifyListener) activity;
        }else{
            throw new IllegalArgumentException("activity must implements OnNotifyListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onNotifyListener = null;
    }

    private OnNotifyListener onNotifyListener;

    public interface OnNotifyListener{
         void onSnack(String tips);
    }
}
