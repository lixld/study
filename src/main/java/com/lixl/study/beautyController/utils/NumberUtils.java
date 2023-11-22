package com.lixl.study.beautyController.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class NumberUtils {

    public static BigDecimal round(Double val, int decimalPlaces) {
        if (val == null) {
            return null;
        }
        BigDecimal bd = new BigDecimal(val);
        bd = bd.setScale(decimalPlaces, RoundingMode.HALF_UP); // HALF_EVEN: 银行家舍入法, HALF_UP: 四舍五入
        return bd;
    }

    public static Integer getGrowthRateNumber(Double salesSum, Double preSalesSum) {
        if (salesSum == null || preSalesSum == null) {
            return 0;
        }
        Integer rate;
        if (salesSum >= preSalesSum) {
            BigDecimal bigDecimal = new BigDecimal(salesSum);
            BigDecimal bigDecimal1 = new BigDecimal(preSalesSum);
            bigDecimal = bigDecimal.subtract(bigDecimal1);
            BigDecimal multiply = bigDecimal.divide(bigDecimal1,2,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
            rate = multiply.intValue();
        } else {
            BigDecimal bigDecimal = new BigDecimal(preSalesSum);//110
            BigDecimal bigDecimal1 = new BigDecimal(salesSum);//93
            BigDecimal subtract = bigDecimal.subtract(bigDecimal1);//17
            BigDecimal multiply = subtract.divide(bigDecimal, 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
            rate = multiply.intValue();
            rate = (~(rate - 1));
        }
        return rate;
    }
}
