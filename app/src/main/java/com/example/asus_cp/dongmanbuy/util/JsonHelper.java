package com.example.asus_cp.dongmanbuy.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.example.asus_cp.dongmanbuy.model.Good;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 加载json格式的txt文件，并将其转换成string
 * Created by asus-cp on 2016-05-20.
 */
public class JsonHelper {
    private Context context=MyApplication.getContext();
    private String tag="JsonHelper";
    private static final int MESSAGE_KEY=1;
    private static final String BUNDLE_KEY="bundle_key";
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MESSAGE_KEY:
                    ConvertTxtCallBack callBack= (ConvertTxtCallBack) msg.obj;
                    Bundle bundle=msg.getData();
                    String str=bundle.getString(BUNDLE_KEY);
                    callBack.handleString(str);
                    break;
            }
        }
    };
    /**
     * 将assets文件夹中的文件加载出来，转换成string
     * @param fileName 文件名称
     */
    public void convertTxtToString(final String fileName, final ConvertTxtCallBack callBack){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String str=null;
                AssetManager assetManager=context.getAssets();
                InputStream in=null;
                ByteArrayOutputStream out=null;
                try {
                    out=new ByteArrayOutputStream();
                    in=new BufferedInputStream(assetManager.open(fileName));
                    int len=0;
                    byte[] buf=new byte[1024];
                    while((len=in.read(buf))!=-1){
                        out.write(buf,0,len);
                    }
                    str=out.toString();
                    Message message=handler.obtainMessage();
                    message.what=MESSAGE_KEY;
                    message.obj=callBack;
                    Bundle bundle=new Bundle();
                    bundle.putString(BUNDLE_KEY,str);
                    message.setData(bundle);
                    handler.sendMessage(message);

                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if(out!=null){
                        try {
                            out.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if(in!=null){
                        try {
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        }).start();
    }

    /**
     * 该方法作废
     * 从限时秒杀中解析json数据到good对象中
     */
    public List<Good> parseJsonFromXianShi(){
        final List<Good> goods=new ArrayList<Good>();
        convertTxtToString("xianshimiaosha.txt", new ConvertTxtCallBack() {
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
                        good.setGoodName(decodeUnicode(js.getString("goods_name")));
                        good.setMarket_price(js.getString("market_price"));
                        good.setShopPrice(decodeUnicode(js.getString("shop_price")));
                        good.setGoodsNumber(js.getString("goods_number"));
                        goods.add(good);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        return goods;
    }

    /**
     * 该方法作废
     * 从精品推荐和猜你喜欢中解析json数据
     */
    public List<Good> parseJsonFromJingPinAndCai(String fileName){
        final List<Good> goods=new ArrayList<Good>();
        convertTxtToString(fileName, new ConvertTxtCallBack() {
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
                        good.setGoodName(decodeUnicode(js.getString("goods_name")));
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
                        good.setShopPrice(decodeUnicode(js.getString("shop_price")));
                        good.setPromotePrice(decodeUnicode(js.getString("promote_price")));
                        good.setGoodType(js.getString("goods_type"));
                        good.setPromoteStartDate(js.getString("promote_start_date"));
                        good.setPromoteEndDate(js.getString("promote_end_date"));
                        good.setIsPromote(js.getString("is_promote"));
                        good.setGoodsBrief(js.getString("goods_brief"));
                        good.setGoodsThumb(js.getString("goods_thumb"));
                        good.setGoodsImg(js.getString("goods_img"));
                        goods.add(good);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        return goods;
    }

    /**
     * 将Unicode转换成中文
     */
    public static String decodeUnicode(String theString) {
        char aChar;
        int len = theString.length();
        StringBuffer outBuffer = new StringBuffer(len);
        for (int x = 0; x < len;) {
            aChar = theString.charAt(x++);
            if (aChar == '\\') {
                aChar = theString.charAt(x++);
                if (aChar == 'u') {
                    // Read the xxxx
                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        aChar = theString.charAt(x++);
                        switch (aChar) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException(
                                        "Malformed   \\uxxxx   encoding.");
                        }

                    }
                    outBuffer.append((char) value);
                } else {
                    if (aChar == 't')
                        aChar = '\t';
                    else if (aChar == 'r')
                        aChar = '\r';
                    else if (aChar == 'n')
                        aChar = '\n';
                    else if (aChar == 'f')
                        aChar = '\f';
                    outBuffer.append(aChar);
                }
            } else
                outBuffer.append(aChar);
        }
        return outBuffer.toString();
    }

    /**
     * 将txt数据转换成string的回调接口
     */
    public interface ConvertTxtCallBack{
        public void handleString(String s);//将返回的string交给需要处理string的方法
    }
}
