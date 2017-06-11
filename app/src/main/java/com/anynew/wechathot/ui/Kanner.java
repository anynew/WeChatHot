package com.anynew.wechathot.ui;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.anynew.wechathot.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

public class Kanner extends FrameLayout {
    private int count;
    private List<ImageView> imageViews;
    private List<TextView> imageViewTxts;
    private List<FrameLayout> listViews;
    private Context context;
    private ViewPager vp;
    private boolean isAutoPlay;
    private int currentItem;
    private int delayTime;
    private LinearLayout ll_dot;
    private List<ImageView> iv_dots;
    private Handler handler = new Handler();
    private String TAG = "Kanner";
    private List<String> textsUrl;

    public Kanner(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        initData();
    }

    public Kanner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Kanner(Context context) {
        this(context, null);
    }

    private void initData() {
        imageViews = new ArrayList<>();
        imageViewTxts = new ArrayList<>();
        iv_dots = new ArrayList<ImageView>();
        delayTime = 2000;
    }

    public void setImagesUrl(String[] imagesUrl,List<String> textsUrl) {
        initLayout();
        initImgFromNet(imagesUrl,textsUrl);
        showTime();
    }


    public void setImagesRes(int[] imagesRes) {
        initLayout();
        initImgFromRes(imagesRes);
        showTime();
    }

    private void initLayout() {
        imageViews.clear();
        imageViewTxts.clear();

        /*View viewLayout = LayoutInflater.from(context).inflate(
                R.layout.item_pager_layout,this,true);
        mImgPager = (ImageView)viewLayout.findViewById(R.id.mImgPager);
        mTvPager = (TextView)viewLayout.findViewById(R.id.mTvPager);
        mFrameLayout = new FrameLayout(context);*/

        View view = LayoutInflater.from(context).inflate(
                R.layout.kanner_layout, this, true);
        vp = (ViewPager) view.findViewById(R.id.vp);
        ll_dot = (LinearLayout) view.findViewById(R.id.ll_dot);
        ll_dot.removeAllViews();
        /////

    }

    /**
     *  从资源文件初始化图片
     * @param imagesRes
     */
    private void initImgFromRes(int[] imagesRes) {
        count = imagesRes.length;
        for (int i = 0; i < count; i++) {
            ImageView iv_dot = new ImageView(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.leftMargin = 5;
            params.rightMargin = 5;
            iv_dot.setImageResource(R.mipmap.dot_blur);
            ll_dot.addView(iv_dot, params);
            iv_dots.add(iv_dot);
        }
        iv_dots.get(0).setImageResource(R.mipmap.dot_focus);

        for (int i = 0; i <= count + 1; i++) {
            ImageView iv = new ImageView(context);
            iv.setScaleType(ScaleType.FIT_XY);
//            iv.setBackgroundResource(R.drawable.loading);
            if (i == 0) {
                iv.setImageResource(imagesRes[count - 1]);
            } else if (i == count + 1) {
                iv.setImageResource(imagesRes[0]);
            } else {
                iv.setImageResource(imagesRes[i - 1]);
            }
            imageViews.add(iv);

        }

    }

    /**
     * 从网络初始化图片
     * @param imagesUrl
     * @param textsUrl
     */
    private void initImgFromNet(String[] imagesUrl,List<String> textsUrl) {
        this.textsUrl = textsUrl;
        count = imagesUrl.length;
        for (int i = 0; i < count; i++) {
            ImageView iv_dot = new ImageView(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.leftMargin = 5;
            params.rightMargin = 5;
            iv_dot.setImageResource(R.mipmap.dot_blur);
            ll_dot.addView(iv_dot, params);
            iv_dots.add(iv_dot);

        }
        iv_dots.get(0).setImageResource(R.mipmap.dot_focus);

        //添加网络资源url
        for (int i = 0; i <= count + 1; i++) {
            ImageView iv = new ImageView(context);
            TextView tv  = new TextView(context);

            iv.setScaleType(ScaleType.FIT_XY);

            if (i == 0) {
                Glide.with(context)
                        .load(imagesUrl[count -1])
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(iv);
                tv.setText(textsUrl.get(count - 1));

            } else if (i == count + 1) {
                Glide.with(context)
                        .load(imagesUrl[0])
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(iv);
                tv.setText(textsUrl.get(0));

            } else {
                Glide.with(context)
                        .load(imagesUrl[i -1])
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(iv);
                tv.setText(textsUrl.get(i - 1));
            }

            imageViews.add(iv);
            imageViewTxts.add(tv);


        }
    }

    private void initDescribeFromNet(String[] textUrl){
        count = textUrl.length;
        for (int i = 0; i < count; i++) {

        }
    }

    private void showTime() {
        KannerPagerAdapter adapter = new KannerPagerAdapter();
        vp.setAdapter(adapter);
        vp.setFocusable(true);
        vp.setCurrentItem(1);
        currentItem = 1;
        vp.addOnPageChangeListener(new MyOnPageChangeListener());
        startPlay();
    }

    private void startPlay() {
        isAutoPlay = true;
        handler.postDelayed(task, 2000);
    }

    /**
     * 定时任务
     */
    private final Runnable task = new Runnable() {

        @Override
        public void run() {
            if (isAutoPlay) {
                currentItem = currentItem % (count + 1) + 1;
                if (currentItem == 1) {
                    vp.setCurrentItem(currentItem, false);
                    handler.post(task);
                } else {
                    vp.setCurrentItem(currentItem);
                    handler.postDelayed(task, 3000);
                }
            } else {
                handler.postDelayed(task, 5000);
            }
        }
    };

    /**
     * viewpager的适配器
     */
    class KannerPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            Log.e(TAG, "listViews Size: "+  imageViews.size() );
            return imageViews.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(imageViews.get(position));
            container.addView(imageViewTxts.get(position));
            return imageViews.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(imageViews.get(position));
            container.removeView(imageViewTxts.get(position));
        }

    }

    class MyOnPageChangeListener implements OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {
            switch (arg0) {
                case 1:
                    isAutoPlay = false;
                    break;
                case 2:
                    isAutoPlay = true;
                    break;
                case 0:
                    if (vp.getCurrentItem() == 0) {
                        vp.setCurrentItem(count, false);
                    } else if (vp.getCurrentItem() == count + 1) {
                        vp.setCurrentItem(1, false);
                    }
                    currentItem = vp.getCurrentItem();
                    isAutoPlay = true;
                    break;
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageSelected(int arg0) {

            for (int i = 0; i < iv_dots.size(); i++) {
                if (i == arg0 - 1) {
                    iv_dots.get(i).setImageResource(R.mipmap.dot_focus);

                } else {
                    iv_dots.get(i).setImageResource(R.mipmap.dot_blur);

                }
            }
        }

    }

}
