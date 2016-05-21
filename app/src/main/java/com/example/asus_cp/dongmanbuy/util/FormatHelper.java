package com.example.asus_cp.dongmanbuy.util;

import java.text.DecimalFormat;

/**
 * 对数字进行格式化的帮助类
 * Created by asus-cp on 2016-05-21.
 */
public class FormatHelper {
    /**
     * 将数字保留2位小数，且以人民币开头
     */
    public static String getMoneyFormat(String str){
        double d=Double.parseDouble(str);
        DecimalFormat a = new DecimalFormat("￥.##");
        return a.format(d);
    }
}
