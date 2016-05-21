package com.example.asus_cp.dongmanbuy.util;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * 主要用于返回一个imagloader对象
 * Created by asus-cp on 2016-05-21.
 */
public class ImageLoadHelper {
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

    /**
     * 返回imageloader
     */
    public ImageLoader getImageLoader(){
        ImageLoader imageLoader=new ImageLoader(MyApplication.getRequestQueue(),new BitmapCache());
        return imageLoader;
    }
}
