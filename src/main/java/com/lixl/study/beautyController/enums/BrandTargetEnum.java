package com.lixl.study.beautyController.enums;

public enum BrandTargetEnum {
    SALE(1,"销量"),
    AVGPRICE(2,"平均价格"),
    SKUNUMBER(3,"SKU数"),
    POSITIVERATE(4,"好评率"),
    SALE_GROWTHRATE(5,"增速");
    private Integer code;
    private String target;//维度

    BrandTargetEnum(Integer code, String target) {
        this.code = code;
        this.target = target;
    }

    public String getTarget() {
        return target;
    }

    public String getName(){
        return this.name();
    }
}
