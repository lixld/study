package com.lixl.study.beautyController.VO;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import com.lixl.study.beautyController.utils.Retain0Decimal;
import com.lixl.study.beautyController.utils.TransBoolean2Int;
import com.lixl.study.beautyController.utils.TransNumber2Txt;
import lombok.Data;

@Data
public class BrandBillBoardVO implements Comparable {

    private Integer brandId;

    private String brandCode;

    private String brandName;

    @JsonSerialize(using = TransBoolean2Int.class)
    private Boolean hasSubscribe = Boolean.FALSE.booleanValue();//已订阅,默认未订阅
    /**
     * 销量
     */
    @JsonSerialize(using = Retain0Decimal.class)
    private Double salesSum;

    /**
     * 增速
     */
    @JsonSerialize(using = TransNumber2Txt.class)
    private Integer salesIncrRate ;

    /**
     * 平均价格
     */
    private Double avgPrice;

    /**
     * SKU数
     */
    private Integer upNew = 0;


    /**
     * 好评率
     */
    private Double avgPositiveRate;


    @Override
    public int compareTo(Object o) {
        BrandBillBoardVO o1 = (BrandBillBoardVO) o;
        return o1.salesIncrRate - this.salesIncrRate;
    }
}
