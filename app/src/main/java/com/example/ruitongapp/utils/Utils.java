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

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Toast;

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

    /**
     * Shows a (long) toast
     */
    public static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    /**
     * Shows a (long) toast.
     */
    public static void showToast(Context context, int resourceId) {
        Toast.makeText(context, context.getString(resourceId), Toast.LENGTH_LONG).show();
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


}
