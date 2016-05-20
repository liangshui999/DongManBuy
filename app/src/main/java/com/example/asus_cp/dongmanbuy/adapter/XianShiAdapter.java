package com.example.asus_cp.dongmanbuy.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.example.asus_cp.dongmanbuy.R;
import com.example.asus_cp.dongmanbuy.model.Good;
import com.example.asus_cp.dongmanbuy.util.MyApplication;

import java.util.List;

/**
 * 限时秒杀的adapter
 * Created by asus-cp on 2016-05-20.
 */
public class XianShiAdapter extends BaseAdapter{
    private Context context;
    private List<Good> goods;
    private LayoutInflater inflater;
    private ImageLoader imageLoader;

    public XianShiAdapter(Context context, List<Good> goods) {
        this.context = context;
        this.goods = goods;
        inflater=LayoutInflater.from(context);
        imageLoader=new ImageLoader(MyApplication.getRequestQueue(),new BitmapCache());
    }

    @Override
    public int getCount() {
        return goods.size();
    }

    @Override
    public Object getItem(int position) {
        return goods.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v=convertView;
        ViewHolder viewHolder=null;
        if(v==null){
            v=inflater.inflate(R.layout.xian_shi_miao_sha_item_layout,null);
            viewHolder=new ViewHolder();
            viewHolder.imageView= (ImageView) v.findViewById(R.id.img_xian_shi_pic);
            viewHolder.nameTextView= (TextView) v.findViewById(R.id.text_xian_shi_name);
            viewHolder.shopPriceTextView= (TextView) v.findViewById(R.id.text_xian_shi_shop_price);
            viewHolder.marketPriceTextView= (TextView) v.findViewById(R.id.text_xian_shi_market_price);
            v.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) v.getTag();
        }
        ImageLoader.ImageListener imageListener=ImageLoader.getImageListener(viewHolder.imageView,R.mipmap.ic_launcher,
                R.mipmap.ic_launcher);
        imageLoader.get(goods.get(position).getGoodsThumb(),imageListener);
        viewHolder.nameTextView.setText(goods.get(position).getGoodName());
        viewHolder.shopPriceTextView.setText(goods.get(position).getShopPrice());
        viewHolder.marketPriceTextView.setText(goods.get(position).getMarket_price());
        return v;
    }

    class ViewHolder{
        private ImageView imageView;
        private TextView nameTextView;
        private TextView shopPriceTextView;
        private TextView marketPriceTextView;
    }


    /**
     * imagloader的缓存
     */
    public class BitmapCache implements ImageLoader.ImageCache {
        private LruCache<String, Bitmap> mCache;
        public BitmapCache() {
            int maxSize = 10 * 1024 * 1024;
            mCache = new LruCache<String, Bitmap>(maxSize) {
                @Override
                protected int sizeOf(String key, Bitmap bitmap) {
                    return bitmap.getRowBytes() * bitmap.getHeight();
                }
            };
        }
        @Override
        public Bitmap getBitmap(String url) {
            return mCache.get(url);
        }
        @Override
        public void putBitmap(String url, Bitmap bitmap) {
            mCache.put(url, bitmap);
        }

    }
}
