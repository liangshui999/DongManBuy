package com.example.asus_cp.dongmanbuy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus_cp.dongmanbuy.R;
import com.example.asus_cp.dongmanbuy.model.MessageModel;

import java.util.List;

/**
 * 消息下拉列表的adapter，该类作废
 * Created by asus-cp on 2016-05-18.
 */
public class MessageSpinnerAdapter extends BaseAdapter{
    private Context context;
    private List<MessageModel> messageModels;
    private LayoutInflater inflater;
    public MessageSpinnerAdapter(Context context,List<MessageModel> messageModels){
        this.context=context;
        this.messageModels=messageModels;
        inflater=LayoutInflater.from(context);
    }
    public MessageSpinnerAdapter() {
    }

    @Override
    public int getCount() {
        return messageModels.size();
    }

    @Override
    public Object getItem(int position) {
        return messageModels.get(position);
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
           v=inflater.inflate(R.layout.message_spinner_item,null) ;
           viewHolder=new ViewHolder();
            viewHolder.imageView= (ImageView) v.findViewById(R.id.img_spinner_item);
            viewHolder.textView= (TextView) v.findViewById(R.id.text_spinner_item);
            v.setTag(viewHolder);
        }else{
           viewHolder= (ViewHolder) v.getTag();
        }
        viewHolder.imageView.setImageResource(messageModels.get(position).getImage());
        viewHolder.textView.setText(messageModels.get(position).getName());
        return v;
    }

    /**
     * viewHolder类
     */
    class ViewHolder{
        private ImageView imageView;
        private TextView textView;
    }
}
