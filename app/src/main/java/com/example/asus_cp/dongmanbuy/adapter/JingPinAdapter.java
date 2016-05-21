package com.example.asus_cp.dongmanbuy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.example.asus_cp.dongmanbuy.R;
import com.example.asus_cp.dongmanbuy.model.Good;
import com.example.asus_cp.dongmanbuy.util.ImageLoadHelper;

import java.util.List;

/**
 * 精品推荐gridview的adapter
 * Created by asus-cp on 2016-05-21.
 */
public class JingPinAdapter extends BaseAdapter{
    private Context context;
    private List<Good> goods;
    private LayoutInflater inflater;
    private ImageLoader imageLoader;

    public JingPinAdapter(Context context, List<Good> goods) {
        this.context = context;
        this.goods = goods;
        inflater=LayoutInflater.from(context);
        imageLoader=new ImageLoadHelper().getImageLoader();
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
            v=inflater.inflate(R.layout.jing_pin_tui_jian_item_layout,null);
            viewHolder=new ViewHolder();
            viewHolder.imageView= (ImageView) v.findViewById(R.id.img_jing_pin_tui_jian);
            viewHolder.nameTextView= (TextView) v.findViewById(R.id.text_jing_pin_name);
            viewHolder.shopPriceTextView= (TextView) v.findViewById(R.id.text_jing_pin_shop_price);
            v.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) v.getTag();
        }
        ImageLoader.ImageListener imageListener=ImageLoader.getImageListener(viewHolder.imageView,R.mipmap.ic_launcher,
                R.mipmap.ic_launcher);
        imageLoader.get(goods.get(position).getGoodsImg(), imageListener, 320, 320);
        viewHolder.nameTextView.setText(goods.get(position).getGoodName());
        viewHolder.shopPriceTextView.setText(goods.get(position).getShopPrice());
        return v;
    }

    class ViewHolder{
        private ImageView imageView;
        private TextView nameTextView;
        private TextView shopPriceTextView;
    }

}
