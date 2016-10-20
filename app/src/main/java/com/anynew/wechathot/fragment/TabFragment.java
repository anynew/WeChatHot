package com.anynew.wechathot.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.anynew.wechathot.R;
import com.anynew.wechathot.model.HomeSource;
import com.anynew.wechathot.model.PicSource;
import com.anynew.wechathot.parser.Parser;
import com.anynew.wechathot.ui.Kanner;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class TabFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.mRecycleView)
    RecyclerView mRecycleView;
    @Bind(R.id.mSwipeRefresh)
    SwipeRefreshLayout mSwipeRefresh;

    private CommonAdapter<String> mAdapter;

    private HeaderAndFooterWrapper mHeaderAndFooterWrapper;

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

        //flag = 0代表获取到的首页fragment
        if (flag == 0) {
            initSwipeRefresh();
            requestData();
        } else {

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
    public static final int MSG_PICSOURCE_COMPLETED = 2;    //College对象获取成功标志
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
                case MSG_PICSOURCE_COMPLETED:

                    break;
                case MSG_RECEIVE_OUTTIME:
                    break;
            }
        }
    };

    /**
     * 获取首页fragment的数据
     */
    private void requestData() {

        new ImageNetThread().start();

    }

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
                holder.setText(R.id.mTitle, homeSource.getListTitle().get(position-1));
                ImageView iv = holder.getView(R.id.mIllustrator);
                Glide.with(getActivity())
                        .load(homeSource.getListIllustrator().get(position-1))
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
        kanner.setImagesUrl(urls,listTitle);

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
     * 下拉刷新的回调
     */
    @Override
    public void onRefresh() {
        requestData();
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
                /*for (int i = 0; i < imgUrl.size(); i++) {
                    Log.e("THeradasd", "run: " + imgUrl.get(i));
                }*/

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }



}
