package com.example.asus_cp.dongmanbuy.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.asus_cp.dongmanbuy.R;
import com.example.asus_cp.dongmanbuy.adapter.JingPinAdapter;
import com.example.asus_cp.dongmanbuy.adapter.XianShiAdapter;
import com.example.asus_cp.dongmanbuy.customview.MyGridView;
import com.example.asus_cp.dongmanbuy.model.Good;
import com.example.asus_cp.dongmanbuy.util.JsonHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    private MyGridView xianShiMiaoShaGridView;

    //精品推荐的gridview
    private MyGridView jingPinTuiJianGridview;

    private JsonHelper jsonHelper;//json解析的帮助类

    private Handler handler = new MyHandler();

    class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case SCROLL_BINNER:
                    //让viewPager 滑动到下一页
                    viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
                    handler.sendEmptyMessageDelayed(SCROLL_BINNER, 2000);
                    //Log.d(tag, "接收定时消息");
                    break;
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.home_fragment_layout,null);
        initView();
        return v;
    }

    /**
     * 初始化view
     */
    private void initView() {
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
                return false;//这里必须返回false，否则viewpager的ontouchenevnt接收不到事件
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
        jsonHelper=new JsonHelper();
        //限时秒杀
        jsonHelper.convertTxtToString("xianshimiaosha.txt", new JsonHelper.ConvertTxtCallBack() {
                    List<Good> goods = new ArrayList<Good>();
                    @Override
                    public void handleString(String s) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                Good good = new Good();
                                JSONObject js = jsonArray.getJSONObject(i);
                                good.setGoodId(js.getString("goods_id"));
                                good.setGoodsImg(js.getString("goods_img"));
                                good.setGoodsThumb(js.getString("goods_thumb"));
                                good.setPromoteEndDate(js.getString("promote_end_date"));
                                good.setPromotePrice(js.getString("promote_price"));
                                good.setIsPromote(js.getString("is_promote"));
                                good.setPromoteStartDate(js.getString("promote_start_date"));
                                good.setGoodName(JsonHelper.decodeUnicode(js.getString("goods_name")));
                                good.setMarket_price(js.getString("market_price"));
                                good.setShopPrice(JsonHelper.decodeUnicode(js.getString("shop_price")));
                                good.setGoodsNumber(js.getString("goods_number"));
                                goods.add(good);
                            }
                            //限时秒杀的gridview
                            xianShiMiaoShaGridView = (MyGridView) v.findViewById(R.id.grid_view_xian_shi_miao_sha);
                            if (goods.size() > 0) {
                                XianShiAdapter xianShiAdapter = new XianShiAdapter(context, getElementsFromList(goods, 4));
                                xianShiMiaoShaGridView.setAdapter(xianShiAdapter);
                                xianShiMiaoShaGridView.setOnItemClickListener(new XianShiOnItemClickListener());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

        //精品推荐的异步加载
        jsonHelper.convertTxtToString("jingpintuijie.txt", new JsonHelper.ConvertTxtCallBack() {
            List<Good> goods = new ArrayList<Good>();
            @Override
            public void handleString(String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Good good = new Good();
                        JSONObject js = jsonArray.getJSONObject(i);
                        good.setGoodId(js.getString("goods_id"));
                        good.setUserId(js.getString("user_id"));
                        good.setGoodName(JsonHelper.decodeUnicode(js.getString("goods_name")));
                        good.setWarehousePrice(js.getString("warehouse_price"));
                        good.setWarehousePromotePrice(js.getString("warehouse_promote_price"));
                        good.setRegionPrice(js.getString("region_price"));
                        good.setPromotePrice(js.getString("region_promote_price"));
                        good.setModel_price(js.getString("model_price"));
                        good.setModel_attr(js.getString("model_attr"));
                        good.setGoods_name_style(js.getString("goods_name_style"));
                        good.setCommentsNumber(js.getString("comments_number"));
                        good.setSalesVolume(js.getString("sales_volume"));
                        good.setMarket_price(js.getString("market_price"));
                        good.setIsNew(js.getString("is_new"));
                        good.setIsBest(js.getString("is_best"));
                        good.setIsHot(js.getString("is_hot"));
                        good.setGoodsNumber(js.getString("goods_number"));
                        good.setOrgPrice(js.getString("org_price"));
                        good.setShopPrice(JsonHelper.decodeUnicode(js.getString("shop_price")));
                        good.setPromotePrice(JsonHelper.decodeUnicode(js.getString("promote_price")));
                        good.setGoodType(js.getString("goods_type"));
                        good.setPromoteStartDate(js.getString("promote_start_date"));
                        good.setPromoteEndDate(js.getString("promote_end_date"));
                        good.setIsPromote(js.getString("is_promote"));
                        good.setGoodsBrief(js.getString("goods_brief"));
                        good.setGoodsThumb(js.getString("goods_thumb"));
                        good.setGoodsImg(js.getString("goods_img"));
                        goods.add(good);
                    }
                    //精品推荐的gridview
                    jingPinTuiJianGridview = (MyGridView) v.findViewById(R.id.grid_view_jing_pin_tui_jian);
                    if (goods.size() > 0) {
                        JingPinAdapter jingPinAdapter = new JingPinAdapter(context, getElementsFromList(goods, 4));
                        jingPinTuiJianGridview.setAdapter(jingPinAdapter);
                        jingPinTuiJianGridview.setOnItemClickListener(new JingPinOnItemClickListener());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

            /**
             * 从good集合里面取出前面n个元素
             *
             */
            public List<Good> getElementsFromList(List<Good> goods, int num) {
                List<Good> list = new ArrayList<Good>();
                for (int i = 0; i < num; i++) {
                    list.add(goods.get(i));
                }
                return list;
            }

            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.ll_my_wallet:
                        Toast.makeText(context, "点击了我的钱包", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.ll_my_order:
                        Toast.makeText(context, "点击了我的订单", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.ll_browse_history:
                        Toast.makeText(context, "点击了浏览记录", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.ll_ship_address:
                        Toast.makeText(context, "点击了收货地址", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.ll_xian_shi_te_you:
                        Toast.makeText(context, "点击了限时特优", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.ll_help:
                        Toast.makeText(context, "点击了帮助中心", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.ll_brand_page:
                        Toast.makeText(context, "点击了品牌页面", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.ll_you_hui_huo_dong:
                        Toast.makeText(context, "点击了优惠活动", Toast.LENGTH_SHORT).show();
                        break;
                }

            }


            @Override
            public void onStop() {
                super.onStop();
                handler.removeMessages(SCROLL_BINNER);
            }

    /**
     * 限时秒杀gridview的项目点击监听器
     */
    public class XianShiOnItemClickListener implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Toast.makeText(context,position+"",Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 精品推荐gridview的项目点击监听器
     */
    public class JingPinOnItemClickListener implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Toast.makeText(context,position+"",Toast.LENGTH_SHORT).show();
        }
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
                    View view = imageViews.get(position % imageViews.size());
                    ViewParent vp = view.getParent();
                    if (vp != null) {
                        ViewGroup parent = (ViewGroup) vp;
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
                    if (view == object) {
//                Log.d(tag, String.valueOf(view==object));
                        return true;
                    } else {
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
