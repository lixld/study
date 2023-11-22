package com.lixl.study.beautyController.dto;



import com.lixl.study.beautyController.enums.CycleEnum;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter

public class BaseRequestDTO {

    @NotNull(message = "品类必选")
    private String category;

    private String market;

    private String[] channels;

    private Integer year;

    private CycleEnum cycleEnum;

    private Integer cycleNum;

    private String startDate;

    private String endDate;

    private String preStartDate;

    private String preEndDate;

    public void fill() {
        this.cycleEnum.fillBaseRequest(this);
    }
}
