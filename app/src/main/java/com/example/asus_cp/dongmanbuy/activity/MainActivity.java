package com.example.asus_cp.dongmanbuy.activity;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus_cp.dongmanbuy.R;
import com.example.asus_cp.dongmanbuy.customview.SlidingMenu;
import com.example.asus_cp.dongmanbuy.fragment.CategoryFragment;
import com.example.asus_cp.dongmanbuy.fragment.FindFragment;
import com.example.asus_cp.dongmanbuy.fragment.HomeFragment;
import com.example.asus_cp.dongmanbuy.fragment.ShopStreetFragment;
import com.example.asus_cp.dongmanbuy.fragment.ShoppingCarFragment;
import com.example.asus_cp.dongmanbuy.util.MyLog;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private android.widget.SearchView searchView;
    private ImageButton loginButton;//登录按钮
    private ImageButton messageButton;//消息按钮
    private ViewPager viewPager;
    private LinearLayout homell;//首页标签
    private LinearLayout categoryll;//分类标签
    private LinearLayout findll;//发现标签
    private LinearLayout shopStreetll;//店铺街标签
    private LinearLayout shoppingCarll;//购物车标签
    private LinearLayout messageAndSao;//消息和扫一扫
    private LinearLayout messagell;//消息
    private LinearLayout sao;//消息和扫一扫
    private List<Fragment> fragments;
    private FragmentPagerAdapter fragmentPagerAdapter;
    private ImageView homeImg;
    private ImageView categoryImg;
    private ImageView findImg;
    private ImageView shopStreetImg;
    private ImageView shoppingCarImg;
    private TextView homeText;
    private TextView categoryText;
    private TextView findText;
    private TextView shopStreetText;
    private TextView shoppingCarText;

    //常量
    public static final int HOME=0;
    public static final int CATEGORY=1;
    public static final int FIND=2;
    public static final int SHOP_STREET=3;
    public static final int SHOPPING_CAR=4;

    private SlidingMenu slidingMenu;

    private String tag="MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//不要标题栏
        setContentView(R.layout.activity_main);
        initData();
        initView();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        fragments=new ArrayList<Fragment>();
        Fragment homeFragment=new HomeFragment();
        Fragment categoryFragment=new CategoryFragment();
        Fragment findFragment=new FindFragment();
        Fragment shopStreetFragment=new ShopStreetFragment();
        Fragment shoppingCarFragment=new ShoppingCarFragment();
        fragments.add(homeFragment);
        fragments.add(categoryFragment);
        fragments.add(findFragment);
        fragments.add(shopStreetFragment);
        fragments.add(shoppingCarFragment);
        fragmentPagerAdapter=new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }


        };

    }

    /**
     * 初始化view
     */
    private void initView() {
        searchView= (android.widget.SearchView) findViewById(R.id.search_view);
        searchView.setIconifiedByDefault(false);
        searchView.setFocusable(true);//默认不聚焦
        searchView.setFocusableInTouchMode(true);
        searchView.clearFocus();
        searchView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    //searchView.clearFocus();
                }else{
                    //searchView.requestFocus();
                }
            }
        });
        //searchView.setFocusableInTouchMode(false);
        //searchView.setEnabled(false);
        loginButton= (ImageButton) findViewById(R.id.img_btn_login);
        messageButton= (ImageButton) findViewById(R.id.img_btn_message);
        messageButton.setOnClickListener(this);
        loginButton.setOnClickListener(this);
        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        //searchView = (SearchView) findViewById(R.id.search_view);
        // 前面的包名是根节点下的包名，不要带activity
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo
                    (new ComponentName("com.example.asus_cp.dongmanbuy",
                            "com.example.asus_cp.dongmanbuy.activity.MySearchActivity")));
            searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default
            searchView.clearFocus();
        }



        //初始化底部标签
        homell = (LinearLayout) findViewById(R.id.ll_home);
        categoryll = (LinearLayout) findViewById(R.id.ll_category);
        findll = (LinearLayout) findViewById(R.id.ll_find);
        shopStreetll = (LinearLayout) findViewById(R.id.ll_shop_street);
        shoppingCarll = (LinearLayout) findViewById(R.id.ll_shopping_car);
        homell.setOnClickListener(this);
        categoryll.setOnClickListener(this);
        findll.setOnClickListener(this);
        shopStreetll.setOnClickListener(this);
        shoppingCarll.setOnClickListener(this);
        homeImg= (ImageView) findViewById(R.id.img_home);
        categoryImg= (ImageView) findViewById(R.id.img_category);
        findImg= (ImageView) findViewById(R.id.img_find);
        shopStreetImg= (ImageView) findViewById(R.id.img_shop_street);
        shoppingCarImg= (ImageView) findViewById(R.id.img_shopping_car);
        homeText= (TextView) findViewById(R.id.text_home);
        categoryText= (TextView) findViewById(R.id.text_category);
        findText= (TextView) findViewById(R.id.text_find);
        shopStreetText= (TextView) findViewById(R.id.text_shop_street);
        shoppingCarText= (TextView) findViewById(R.id.text_shoppingz_car);

        //将首页的文字和图片设置为红色
        homeImg.setImageResource(R.mipmap.home_selected);
        homeText.setTextColor(getResources().getColor(R.color.bottom_lable_color));


        //初始化消息和扫一扫
        messageAndSao= (LinearLayout) findViewById(R.id.ll_message_and_sao);
        messagell= (LinearLayout) findViewById(R.id.ll_message);
        sao= (LinearLayout) findViewById(R.id.ll_sao);
        messageAndSao.setOnClickListener(this);
        messagell.setOnClickListener(this);
        sao.setOnClickListener(this);

        //初始化viewpager
        viewPager= (ViewPager) findViewById(R.id.viewpager_main);
        viewPager.setAdapter(fragmentPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                resetLabel();
                switch (position) {
                    case HOME:
                        homeImg.setImageResource(R.mipmap.home_selected);
                        homeText.setTextColor(getResources().getColor(R.color.bottom_lable_color));
                        break;
                    case CATEGORY:
                        categoryImg.setImageResource(R.mipmap.category_selected);
                        categoryText.setTextColor(getResources().getColor(R.color.bottom_lable_color));
                        break;
                    case FIND:
                        findImg.setImageResource(R.mipmap.find_selected);
                        findText.setTextColor(getResources().getColor(R.color.bottom_lable_color));
                        break;
                    case SHOP_STREET:
                        shopStreetImg.setImageResource(R.mipmap.shopstreet_selected);
                        shopStreetText.setTextColor(getResources().getColor(R.color.bottom_lable_color));
                        break;
                    case SHOPPING_CAR:
                        shoppingCarImg.setImageResource(R.mipmap.shoppingcar_selected);
                        shoppingCarText.setTextColor(getResources().getColor(R.color.bottom_lable_color));
                        break;

                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        slidingMenu= (SlidingMenu) findViewById(R.id.id_menu);

    }

    /**
     * 将底部标签设置为初始状态
     *
     */
    public void resetLabel(){
        //将标签图片设置为初始状态
        homeImg.setImageResource(R.mipmap.home_normal);
        categoryImg.setImageResource(R.mipmap.category_normal);
        findImg.setImageResource(R.mipmap.find_normal);
        shopStreetImg.setImageResource(R.mipmap.shopstreet_normal);
        shoppingCarImg.setImageResource(R.mipmap.shoppingcar_normal);

        //将文字颜色设置为初始状态
        homeText.setTextColor(Color.BLACK);
        categoryText.setTextColor(Color.BLACK);
        findText.setTextColor(Color.BLACK);
        shopStreetText.setTextColor(Color.BLACK);
        shoppingCarText.setTextColor(Color.BLACK);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_btn_login://登录按钮的点击事件
                //Toast.makeText(this,"点击了登录按钮",Toast.LENGTH_SHORT).show();
                slidingMenu.toggle();
                break;
            case R.id.img_btn_message://消息按钮的点击事件
                messageAndSao.setVisibility(View.VISIBLE);//让下拉框弹出来
                break;
            case R.id.ll_home:
                viewPager.setCurrentItem(HOME);
                resetLabel();
                homeImg.setImageResource(R.mipmap.home_selected);
                homeText.setTextColor(getResources().getColor(R.color.bottom_lable_color));
                break;
            case R.id.ll_category:
                viewPager.setCurrentItem(CATEGORY);
                resetLabel();
                categoryImg.setImageResource(R.mipmap.category_selected);
                categoryText.setTextColor(getResources().getColor(R.color.bottom_lable_color));
                break;
            case R.id.ll_find:
                viewPager.setCurrentItem(FIND);
                resetLabel();
                findImg.setImageResource(R.mipmap.find_selected);
                findText.setTextColor(getResources().getColor(R.color.bottom_lable_color));
                break;
            case R.id.ll_shop_street:
                viewPager.setCurrentItem(SHOP_STREET);
                resetLabel();
                shopStreetImg.setImageResource(R.mipmap.shopstreet_selected);
                shopStreetText.setTextColor(getResources().getColor(R.color.bottom_lable_color));
                break;
            case R.id.ll_shopping_car:
                viewPager.setCurrentItem(SHOPPING_CAR);
                resetLabel();
                shoppingCarImg.setImageResource(R.mipmap.shoppingcar_selected);
                shoppingCarText.setTextColor(getResources().getColor(R.color.bottom_lable_color));
                break;
            case R.id.ll_message:
                Toast.makeText(this,"点击了消息",Toast.LENGTH_SHORT).show();
                messageAndSao.setVisibility(View.GONE);
                break;
            case R.id.ll_sao:
                Toast.makeText(this,"点击了扫一扫",Toast.LENGTH_SHORT).show();
                messageAndSao.setVisibility(View.GONE);
                break;

        }
    }

    @Override
    public void onBackPressed() {
        searchView.clearFocus();
        MyLog.d(tag, "返回键");
        super.onBackPressed();
    }

   /* @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            MyLog.d(tag, "keydown返回键");
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }*/
}
