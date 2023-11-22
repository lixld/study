package com.lixl.study.beautyController.VO;


import com.lixl.study.beautyController.aop.TargetEnumValidatorAnnocation;
import com.lixl.study.beautyController.enums.BrandTargetEnum;
import lombok.Data;

@Data
public class BrandBillboardRequestDTO {

    @TargetEnumValidatorAnnocation(clazz = BrandTargetEnum.class, method = "getName", message = "品牌指标-有错误：必须是下面枚举值 SALE,AVGPRICE,SKUNUMBER,POSITIVERATE,SALE_GROWTHRATE")
//    @ApiModelProperty(value = "品牌指标-枚举", required = true,allowableValues = "SALE(销量),AVGPRICE(平均价格),SKUNUMBER(SKU数),POSITIVERATE(好评率),SALE_GROWTHRATE(增速)")
    private String brandTarget;

}
