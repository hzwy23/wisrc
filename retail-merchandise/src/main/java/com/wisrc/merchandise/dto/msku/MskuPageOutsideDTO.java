package com.wisrc.merchandise.dto.msku;

import lombok.Data;

import java.util.List;

@Data
public class MskuPageOutsideDTO {
    private List<GetMskuPageOutsideDTO> mskuList;
    private long total;
    private Integer pages;
}
