package com.example.asus_cp.dongmanbuy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus_cp.dongmanbuy.R;
import com.example.asus_cp.dongmanbuy.model.AboutMeModel;

import java.util.List;

/**
 * 该类作废
 * 首页上面，我的图片，我的订单等8个按钮对应的gridview的dapter,
 * Created by asus-cp on 2016-05-20.
 */
public class AboutMeAdapter extends BaseAdapter {
    private Context context;
    private List<AboutMeModel> aboutMeModels;
    private LayoutInflater inflater;

    public AboutMeAdapter(Context context, List<AboutMeModel> aboutMeModels) {
        this.context = context;
        this.aboutMeModels = aboutMeModels;
        inflater=LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return aboutMeModels.size();
    }

    @Override
    public Object getItem(int position) {
        return aboutMeModels.get(position);
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
            v=inflater.inflate(R.layout.grid_view_about_me_item_layout,null);
            viewHolder=new ViewHolder();
            viewHolder.imageView= (ImageView) v.findViewById(R.id.img_grid_about_me_item);
            viewHolder.textView= (TextView) v.findViewById(R.id.text_grid_about_me_item);
            v.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) v.getTag();
        }
        viewHolder.imageView.setImageResource(aboutMeModels.get(position).getImg());
        viewHolder.textView.setText(aboutMeModels.get(position).getName());
        return v;
    }

    class ViewHolder{
        private ImageView imageView;
        private TextView textView;
    }
}
