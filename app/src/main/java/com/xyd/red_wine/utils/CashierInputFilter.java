package com.xyd.red_wine.utils;

import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: zhaoxiaolei
 * @date: 2017/8/9
 * @time: 10:51
 * @description:  金额输入过滤器
 */


public class CashierInputFilter implements InputFilter {
    Pattern mPattern;
    Pattern mPattern2;
    //输入的最大金额
    private static final int MAX_VALUE = Integer.MAX_VALUE;
    //小数点后的位数
    private int POINTER_LENGTH = 2;


    private static final String POINTER = ".";


    private static final String ZERO = "0";


    public CashierInputFilter(int POINTER_LENGTH) {
        this.POINTER_LENGTH=POINTER_LENGTH;
        mPattern = Pattern.compile("([0-9]|\\.)*");
        mPattern2 = Pattern.compile("(\\.)*");
    }


    /**
     * @param source 新输入的字符串
     * @param start  新输入的字符串起始下标，一般为0
     * @param end    新输入的字符串终点下标，一般为source长度-1
     * @param dest   输入之前文本框内容
     * @param dstart 原内容起始坐标，一般为0
     * @param dend   原内容终点坐标，一般为dest长度-1
     * @return 输入内容
     */
    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        String sourceText = source.toString();
        String destText = dest.toString();
        try {
            //验证删除等按键
            if (TextUtils.isEmpty(sourceText)) {


                if (dstart == 0 && destText.indexOf(POINTER) == 1) {
                    return "0";
                }
                return "";
            }


            Matcher matcher = mPattern.matcher(source);
            Matcher matcher2 = mPattern2.matcher(source);
            //已经输入小数点的情况下，只能输入数字
            if (destText.contains(POINTER)) {
                if (!matcher.matches()) {
                    return "";
                } else {
                    if (POINTER.equals(source)) {  //只能输入一个小数点
                        return "";
                    }
                }


                //验证小数点精度，保证小数点后只能输入两位
                int index = destText.indexOf(POINTER);
                int length = destText.trim().length() - index;


                if (length > POINTER_LENGTH && dstart > index) {
                    return "";
                }
            } else {
                //没有输入小数点的情况下，只能输入小数点和数字，但首位不能输入小数点和0
                if (!matcher.matches()) {
                    return "";
                } else {


                    if ((matcher2.matches()) && dstart == 0) {
                        return "0.";
                    }
                    //                if ((POINTER.equals(source)) && dstart==0) {
                    //                    return "0.";
                    //                }
                }
            }
            //验证输入金额的大小
            double sumText = Double.parseDouble(destText + sourceText);
            if (sumText > MAX_VALUE) {
                return dest.subSequence(dstart, dend);
            }
            return dest.subSequence(dstart, dend) + sourceText;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            try {
                return dest.subSequence(dstart, dend) + sourceText;
            } catch (Exception e1) {
                e1.printStackTrace();
                return "";
            }
        }
    }
}
