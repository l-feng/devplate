/**
 * PicassoTools.java
 * Function： TODO
 *
 * @author zyyin
 * @date 2015-1-14 下午2:26:16
 * Copyright (c) 2015, Netease All Rights Reserved.
 */

package com.example.core.image;

import android.content.Context;

import com.squareup.picasso.LruCache;
import com.squareup.picasso.Picasso;

/**
 * Picasso工具类
 */

public class PicassoTools {
    private static Picasso picasso = null;
    private static LruCache lruCache = null;

    public static Picasso getPicasso(Context context) {
        if (picasso == null) {
            lruCache = new LruCache(context.getApplicationContext());
            picasso = new Picasso.Builder(context).memoryCache(lruCache).build();
        }
        return picasso;
    }

    public static void clearCache() {
        if (lruCache != null) {
            lruCache.clear();
        }
    }

}
