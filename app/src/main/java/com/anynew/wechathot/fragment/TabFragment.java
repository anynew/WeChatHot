package com.anynew.wechathot.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.anynew.wechathot.parser.Parser;
import com.anynew.wechathot.R;
import com.anynew.wechathot.model.HomeSource;
import com.anynew.wechathot.ui.MyLinearLayoutManager;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import butterknife.Bind;
import butterknife.ButterKnife;


public class TabFragment extends Fragment {


    @Bind(R.id.mRecycleView)
    RecyclerView mRecycleView;

    private CommonAdapter<String> mAdapter;
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
        System.out.println("flag == === " + flag);
        if (flag == 0) {
            requestData();
        } else {

        }
    }

    public static final int MSG_LIST_COMPLETED = 1;      //LIST集合成功标志
    public static final int MSG_COLLEGE_COMPLETED = 2;    //College对象获取成功标志
    public static final int MSG_RECEIVE_OUTTIME = 3;      //超时连接

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_LIST_COMPLETED:
//                    List<String> imgUrl = (List<String>) msg.obj;
//                    String[] urls = imgUrl.toArray(new String[imgUrl.size()]);
//                    kanner.setImagesUrl(urls);
                    HomeSource homeSource = (HomeSource) msg.obj;
                    initRecycler(homeSource);

                    break;
                case MSG_COLLEGE_COMPLETED:
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
    private void initRecycler(final HomeSource homeSource) {
        mRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecycleView.setNestedScrollingEnabled(false);
        mRecycleView.setHasFixedSize(false);
//        mRecycleView.setLayoutManager(new MyLinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, true));

        mAdapter = new CommonAdapter<String>(getActivity(),R.layout.item_layout_home_list,homeSource.getListTitle()) {
            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                holder.setText(R.id.mTitle,homeSource.getListTitle().get(position));
                holder.setText(R.id.mContent,homeSource.getListContent().get(position));
                ImageView iv = holder.getView(R.id.mIllustrator);
                Glide.with(getActivity())
                        .load(homeSource.getListIllustrator().get(position))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(iv);
            }
        };
        mRecycleView.setAdapter(mAdapter);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private class ImageNetThread extends Thread {
        @Override
        public void run() {
            super.run();
            try {
                Parser parser = new Parser();
                HomeSource homeSource = parser.getHomeSource();
//                PicSource source = parser.getSource();
//                List<String> imgUrl = source.getListImg();
                handler.sendMessage(handler.obtainMessage(MSG_LIST_COMPLETED, homeSource));
                /*for (int i = 0; i < imgUrl.size(); i++) {
                    Log.e("THeradasd", "run: " + imgUrl.get(i));
                }*/

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

}
