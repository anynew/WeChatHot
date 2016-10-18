package com.anynew.wechathot.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.os.AsyncTaskCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anynew.wechathot.Parser.Parser;
import com.anynew.wechathot.R;
import com.anynew.wechathot.model.PicSource;
import com.anynew.wechathot.network.getData;
import com.anynew.wechathot.ui.Kanner;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.IOException;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class TabFragment extends Fragment {


    @Bind(R.id.kanner)
    Kanner kanner;

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
        System.out.println("flag == === "+flag);
        if (flag == 0) {
            kanner.setVisibility(View.VISIBLE);
            requestData();
        } else {

        }
    }

    public static final int MSG_LIST_COMPLETED = 1;      //LIST集合成功标志
    public static final int MSG_COLLEGE_COMPLETED = 2;	//College对象获取成功标志
    public static final int MSG_RECEIVE_OUTTIME = 3;      //超时连接

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case MSG_LIST_COMPLETED:
                    List<String> imgUrl = (List<String>) msg.obj;
                    String[] urls = imgUrl.toArray(new String[imgUrl.size()]);
                    kanner.setImagesUrl(urls);
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
        /*kanner.setImagesUrl(new String[] {
                "http://img03.muzhiwan.com/2015/06/05/upload_557165f4850cf.png",
                "http://img02.muzhiwan.com/2015/06/11/upload_557903dc0f165.jpg",
                "http://img04.muzhiwan.com/2015/06/05/upload_5571659957d90.png",
                "http://img03.muzhiwan.com/2015/06/16/upload_557fd2a8da7a3.jpg" });*/
       /* new Thread(new Runnable() {
            @Override
            public void run() {
                Parser parser = new Parser();
                PicSource source = parser.getSource();
                List<String> imgUrl = source.getListImg();
                urls = imgUrl.toArray(new String[imgUrl.size()]);
                for (String s : imgUrl) {
                    Log.e("TAG", "run: " + s);
                }
            }
        }).start();*/

    }
    private class ImageNetThread extends Thread{
        @Override
        public void run() {
            super.run();
            try{
                Parser parser = new Parser();
                PicSource source = parser.getSource();
                List<String> imgUrl = source.getListImg();
                handler.sendMessage(handler.obtainMessage(MSG_LIST_COMPLETED,imgUrl));
                for (int i = 0; i < imgUrl.size(); i++) {
                    Log.e("THeradasd", "run: " + imgUrl.get(i) );
                }

            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
