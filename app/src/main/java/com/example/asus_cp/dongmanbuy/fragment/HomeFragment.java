package com.example.asus_cp.dongmanbuy.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.asus_cp.dongmanbuy.R;
import com.example.asus_cp.dongmanbuy.adapter.XianShiAdapter;
import com.example.asus_cp.dongmanbuy.model.Good;
import com.example.asus_cp.dongmanbuy.util.JsonHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus-cp on 2016-05-19.
 */
public class HomeFragment extends Fragment implements View.OnClickListener{
    private String tag="HomeFragment";
    private ViewPager viewPager;
    private List<ImageView> imageViews;//装载imageview的集合
    private int[] imageIds={R.drawable.guanggao1,R.drawable.guanggao2,R.drawable.guanggao3};//装imagview图片id的数组
    private Context context;
    private LinearLayout pointGroup;//装载指示点的集合
    private View v;
    protected int lastPosition;//上一个页面的位置
    private boolean isRunning = true;//判断是否自动滚动
    public static final int SCROLL_BINNER=1;//自动滚动消息的what

    //我的钱包，我的订单等8个按钮
    private LinearLayout myWalletll;
    private LinearLayout myOrderll;
    private LinearLayout browseHistoryll;
    private LinearLayout shipAddressll;
    private LinearLayout xianShiTeYoull;
    private LinearLayout helpll;
    private LinearLayout brandPagell;
    private LinearLayout youHuiHuoDongll;

    //限时秒杀的gridview
    private GridView xianShiMiaoShaGridView;


    private JsonHelper jsonHelper;

    private Handler handler = new MyHandler();

    class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case SCROLL_BINNER:
                    //让viewPager 滑动到下一页
                    viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
                    handler.sendEmptyMessageDelayed(SCROLL_BINNER, 2000);
                    Log.d(tag, "接收定时消息");
                    break;
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.home_fragment_layout,null);
        initData();
        initView();
        return v;
    }

    /**
     * 初始化view
     */
    private void initView() {
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
        viewPager.setCurrentItem(Integer.MAX_VALUE / 2);
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float firstX = 0;//手指按下时的x值
                float firstY = 0;
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        firstX = event.getX();
                        firstY = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        float lastX = event.getX();
                        float lastY = event.getY();
                        float distance = (float) Math.sqrt((lastX - firstX) * (lastX - firstX) +
                                (lastY - firstY) * (lastY - firstY));
                        if (distance > 10) {
                            handler.removeMessages(SCROLL_BINNER);//暂停轮播
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        handler.sendEmptyMessage(SCROLL_BINNER);//重新开始轮播
                        break;

                }
                return true;
            }
        });
        handler.sendEmptyMessage(SCROLL_BINNER);

        //初始化我的钱包，我的按钮等8个组件
        myWalletll= (LinearLayout) v.findViewById(R.id.ll_my_wallet);
        myOrderll= (LinearLayout) v.findViewById(R.id.ll_my_order);
        browseHistoryll= (LinearLayout) v.findViewById(R.id.ll_browse_history);
        shipAddressll= (LinearLayout) v.findViewById(R.id.ll_ship_address);
        xianShiTeYoull= (LinearLayout) v.findViewById(R.id.ll_xian_shi_te_you);
        helpll= (LinearLayout) v.findViewById(R.id.ll_help);
        brandPagell= (LinearLayout) v.findViewById(R.id.ll_brand_page);
        youHuiHuoDongll= (LinearLayout) v.findViewById(R.id.ll_you_hui_huo_dong);

        myWalletll.setOnClickListener(this);
        myOrderll.setOnClickListener(this);
        browseHistoryll.setOnClickListener(this);
        shipAddressll.setOnClickListener(this);
        xianShiTeYoull.setOnClickListener(this);
        helpll.setOnClickListener(this);
        brandPagell.setOnClickListener(this);
        youHuiHuoDongll.setOnClickListener(this);

        //接收json数据
        List<Good> goods=jsonHelper.parseJsonFromXianShi();
        for(Good good:goods){
            Log.d(tag,"限时秒杀:"+good.getGoodName()+good.getGoodsImg());
        }

        List<Good> goods1=jsonHelper.parseJsonFromJingPinAndCai("cainixihuan.txt");
        for(Good good:goods1){
            Log.d(tag,"猜你喜欢:"+good.getGoodName()+good.getGoodsImg());
        }

        List<Good> goods2=jsonHelper.parseJsonFromJingPinAndCai("jingpintuijie.txt");
        for(Good good:goods2){
            Log.d(tag,"精品推荐:"+good.getGoodName()+good.getGoodsImg());
        }

        //限时秒杀的gridview
        xianShiMiaoShaGridView= (GridView) v.findViewById(R.id.grid_view_xian_shi_miao_sha);
        XianShiAdapter xianShiAdapter=new XianShiAdapter(context,goods);
        xianShiMiaoShaGridView.setAdapter(xianShiAdapter);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_my_wallet:
                Toast.makeText(context,"点击了我的钱包",Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_my_order:
                Toast.makeText(context,"点击了我的订单",Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_browse_history:
                Toast.makeText(context,"点击了浏览记录",Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_ship_address:
                Toast.makeText(context,"点击了收货地址",Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_xian_shi_te_you:
                Toast.makeText(context,"点击了限时特优",Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_help:
                Toast.makeText(context,"点击了帮助中心",Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_brand_page:
                Toast.makeText(context,"点击了品牌页面",Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_you_hui_huo_dong:
                Toast.makeText(context,"点击了优惠活动",Toast.LENGTH_SHORT).show();
                break;
        }

    }

    /**
     * 初始化方法
     */
    private void initData() {
        context=getActivity();
        imageViews=new ArrayList<ImageView>();
        pointGroup= (LinearLayout) v.findViewById(R.id.ll_point_group);
        for(int i=0;i<imageIds.length;i++){
            ImageView imageView=new ImageView(context);
            imageView.setImageResource(imageIds[i]);
            imageViews.add(imageView);
            //添加指示点
            ImageView point =new ImageView(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.rightMargin = 30;
            point.setLayoutParams(params);
            point.setBackgroundResource(R.drawable.point);
            if(i==0){
                point.setEnabled(true);
            }else{
                point.setEnabled(false);
            }
            pointGroup.addView(point);
        }
        jsonHelper=new JsonHelper();
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeMessages(SCROLL_BINNER);
    }

    /**
     * 广告viewpager的适配器
     */
    private class MyPagerAdapter extends PagerAdapter {

        @Override
        /**
         * 获得页面的总数
         */
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        /**
         * 获得相应位置上的view
         * container  view的容器，其实就是viewpager自身
         * position 	相应的位置
         */
        public Object instantiateItem(ViewGroup container, int position) {
            View view=imageViews.get(position%imageViews.size());
            ViewParent vp =view.getParent();
                if (vp!=null){
                    ViewGroup parent = (ViewGroup)vp;
                    parent.removeView(view);
                }
            // 给 container 添加一个view
            container.addView(view);
            //返回一个和该view相对的object
//            Log.d(tag,imageViews.size()+"");
            return view;
        }

        @Override
        /**
         * 判断 view和object的对应关系
         */
        public boolean isViewFromObject(View view, Object object) {
            if(view == object){
//                Log.d(tag, String.valueOf(view==object));
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
