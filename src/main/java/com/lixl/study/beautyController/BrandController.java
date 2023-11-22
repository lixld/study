package com.lixl.study.beautyController;


import com.lixl.study.beautyController.VO.BrandBillBoardVO;
import com.lixl.study.beautyController.VO.BrandBillboardRequestDTO;
import com.lixl.study.beautyController.aop.CacheAnnocation;
import com.lixl.study.beautyController.enums.CacheTypeEnum;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Spring 中 @RestControllerAdvice 注解
 * 可以拦截+获取带有 @Controller 或 @RestController 注解类的异常，
 * 通过 @ExceptionHandler 注解设置捕获异常类型。
 */
@RestController
@RequestMapping("/brand")
public class BrandController {


    /**
     * 品牌榜单默认展示12个品牌的排名
     * @param brandRequestDTO
     * @return
     */
    @CacheAnnocation(expireSecond = 3600, type = CacheTypeEnum.REDIS)
    @PostMapping("/billBoard")
    public List<BrandBillBoardVO> billBoard(@Validated @RequestBody BrandBillboardRequestDTO brandRequestDTO) {
        List<BrandBillBoardVO> billBoardByTarget = null;
        return billBoardByTarget;
    }



}
