package com.example.asus_cp.dongmanbuy.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.asus_cp.dongmanbuy.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus-cp on 2016-05-19.
 */
public class HomeFragment extends Fragment {
    private String tag="HomeFragment";
    private ViewPager viewPager;
    private List<ImageView> imageViews;//装载imageview的集合
    private int[] imageIds={R.drawable.guanggao1,R.drawable.guanggao2,R.drawable.guanggao3};//装imagview图片id的数组
    private Context context;
    private LinearLayout pointGroup;//装载指示点的集合
    private View v;
    protected int lastPosition;//上一个页面的位置
    private boolean isRunning = false;//判断是否自动滚动

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.home_fragment_layout,null);
        init();
        viewPager= (ViewPager) v.findViewById(R.id.viewpager_binner);
        viewPager.setAdapter(new MyPagerAdapter());
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            /**
             * 页面切换后调用
             * position  新的页面位置
             */
            public void onPageSelected(int position) {
                position = position % imageViews.size();
                //改变指示点的状态
                //把当前点enbale 为true
                pointGroup.getChildAt(position).setEnabled(true);
                //把上一个点设为false
                pointGroup.getChildAt(lastPosition).setEnabled(false);
                lastPosition = position;
            }

            @Override
            /**
             * 页面正在滑动的时候，回调
             */
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
            }

            @Override
            /**
             * 当页面状态发生变化的时候，回调
             */
            public void onPageScrollStateChanged(int state) {

            }
        });
        return v;
    }

    /**
     * 初始化方法
     */
    private void init() {
        context=getActivity();
        imageViews=new ArrayList<ImageView>();
        pointGroup= (LinearLayout) v.findViewById(R.id.ll_point_group);
        for(int i=0;i<imageIds.length;i++){
            ImageView imageView=new ImageView(context);
            imageView.setImageResource(imageIds[i]);
            imageViews.add(imageView);

            //添加指示点
            /*ImageView point =new ImageView(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.rightMargin = 20;
            point.setLayoutParams(params);
            point.setBackgroundResource(R.drawable.point);
            if(i==0){
                point.setEnabled(true);
            }else{
                point.setEnabled(false);
            }
            pointGroup.addView(point);*/
        }
    }

   /* private Handler handler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            //让viewPager 滑动到下一页
            viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
            if(isRunning){
                handler.sendEmptyMessageDelayed(0, 2000);
            }
        }
    };
*/
    /*@Override
    public void onDestroy() {
        super.onDestroy();
        isRunning=false;
    }*/

    /**
     * viewpager的适配器
     */
    private class MyPagerAdapter extends PagerAdapter {

        @Override
        /**
         * 获得页面的总数
         */
        public int getCount() {
            return imageViews.size();
        }

        @Override
        /**
         * 获得相应位置上的view
         * container  view的容器，其实就是viewpager自身
         * position 	相应的位置
         */
        public Object instantiateItem(ViewGroup container, int position) {

            // 给 container 添加一个view
            container.addView(imageViews.get(position%imageViews.size()));
            //返回一个和该view相对的object
            Log.d(tag,imageViews.size()+"");
            return imageViews.get(position%imageViews.size());
        }

        @Override
        /**
         * 判断 view和object的对应关系
         */
        public boolean isViewFromObject(View view, Object object) {
            if(view == object){
                Log.d(tag, String.valueOf(view==object));
                return true;
            }else{
                return false;
            }
        }

        @Override
        /**
         * 销毁对应位置上的object
         */
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
            object = null;
        }
    }
}
