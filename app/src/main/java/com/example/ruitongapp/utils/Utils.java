/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.example.ruitongapp.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.Toast;

import com.sdsmdg.tastytoast.TastyToast;

import java.io.File;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * A collection of utility methods, all static.
 */
public class Utils {

    /*
     * Making sure public utility methods remain static
     */
    private Utils() {
    }

    /**
     * Returns the screen/display size
     */
    public static Point getDisplaySize(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size;
    }



    public static int convertDpToPixel(Context ctx, int dp) {
        float density = ctx.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }

    /**
     * Formats time in milliseconds to hh:mm:ss string format.
     */
    public static String formatMillis(int millis) {
        String result = "";
        int hr = millis / 3600000;
        millis %= 3600000;
        int min = millis / 60000;
        millis %= 60000;
        int sec = millis / 1000;
        if (hr > 0) {
            result += hr + ":";
        }
        if (min >= 0) {
            if (min > 9) {
                result += min + ":";
            } else {
                result += "0" + min + ":";
            }
        }
        if (sec > 9) {
            result += sec;
        } else {
            result += "0" + sec;
        }
        return result;
    }

    //将图片地址的字符串进行MD5编码，作为图片名
    public static String hashKeyForDisk(String key) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

    private static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte aByte : bytes) {
            String hex = Integer.toHexString(0xFF & aByte);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

        public static String jiami(String mingwen){

            String miWen = encode(mingwen).substring(8, 24);
            String pwd1 = miWen.substring(0, 8) ;//8个字符
            String pwd2 = miWen.substring(8, 16) ;//8个字符
            miWen = generateMixed(4)+pwd1+generateMixed(4)+pwd2+generateMixed(4);
            StringBuilder sb=new StringBuilder();
            sb.append(miWen);
            sb.reverse();
            miWen = sb.toString();

            return miWen;

        }
        private static String generateMixed(int n){
            char chars[] = new char[]{'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
            String res="";

            for(int i = 0; i < n ; i ++) {
                int id = (int) Math.ceil(Math.random()*35);
                res += chars[id];
            }
            return res;
        }


        // 进行md5的加密运算
        private static String encode(String password) {
            // MessageDigest专门用于加密的类
            try {
                MessageDigest messageDigest = MessageDigest.getInstance("MD5");
                byte[] result = messageDigest.digest(password.getBytes()); // 得到加密后的字符组数

                StringBuilder sb = new StringBuilder();

                for (byte b : result) {
                    int num = b & 0xff; // 这里的是为了将原本是byte型的数向上提升为int型，从而使得原本的负数转为了正数
                    String hex = Integer.toHexString(num); //这里将int型的数直接转换成16进制表示
                    //16进制可能是为1的长度，这种情况下，需要在前面补0，
                    if (hex.length() == 1) {
                        sb.append(0);
                    }
                    sb.append(hex);
                }

                return sb.toString();

            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                return null;
            }
        }
        public static void showToast(Context context,String ss,int ii){
            Toast tastyToast= TastyToast.makeText(context,ss,TastyToast.LENGTH_LONG,ii);
            tastyToast.setGravity(Gravity.CENTER,0,0);
            tastyToast.show();
        }

    public static File cameraFile;

    /**
     * 获取屏幕高度和宽带
     *
     * @param mContext
     * @return int[高，宽]
     */
    public static int[] getScreen(Context mContext) {
        DisplayMetrics dm = new DisplayMetrics();
        // 取得窗口属性
        // getWindowManager().getDefaultDisplay().getMetrics(dm);
        ((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(dm);

        // 窗口的宽度
        int screenWidth = dm.widthPixels;

        // 窗口高度
        int screenHeight = dm.heightPixels;
        int screen[] = { screenHeight, screenWidth };
        return screen;

    }

    public static int getSecreenW(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        dm = null;
        return width;
    }

    /**
     *
     * @Title: dip2px @Description: TODO(dp转px) @param @param
     *         context @param @param dpValue @param @return 设定文件 @return int
     *         返回类型 @throws
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }







    // 返回是否有SD卡
    public static boolean GetSDState() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }

    /**
     *
     * @Title: deleteFile @Description: TODO(删除文件) @param @param file
     *         设定文件 @return void 返回类型 @throws
     */
    public static void deleteFile(File file) {
        if (file.exists()) { // 判断文件是否存在
            if (file.isFile()) { // 判断是否是文件
                file.delete(); // delete()方法 你应该知道 是删除的意思;
            } else if (file.isDirectory()) { // 否则如果它是一个目录
                File files[] = file.listFiles(); // 声明目录下所有的文件 files[];
                for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件
                    deleteFile(files[i]); // 把每个文件 用这个方法进行迭代
                }
            }
            file.delete();
        } else {

        }
    }

    /**
     * 显示toast
     *
     * @param
     *
     * @param content
     *            显示的内容
     */

    public static void showToast(Context mContext, String content) {
        Toast.makeText(mContext, content, Toast.LENGTH_SHORT).show();
    }

    /**
     * actionbar高度
     *
     * @param context
     * @return
     */
    public static int getBarHeight(Context context) {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, sbar = 38;// 默认为38，貌似大部分是这样的

        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            sbar = context.getResources().getDimensionPixelSize(x);

        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return sbar;
    }


    /**
     * 获取时间戳
     * @return
     */
    public static long getSeq() {
        return System.currentTimeMillis();
    }

    /**
     * 检查网络状态
     */
    public static boolean isNetUseable(Context context) {
        boolean have=false;
        ConnectivityManager cwjManager=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cwjManager.getActiveNetworkInfo();
        if (info != null && info.isAvailable()){
            have=true;
        }
        else
        {

            showToast(context, "无网络连接");
        }
        return have;
    }
}
