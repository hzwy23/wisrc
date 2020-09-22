package com.wisrc.merchandise.utils;

import com.wisrc.merchandise.entity.MskuInfoEpitaphEntity;
import com.wisrc.merchandise.vo.EpitaphSaveVo;

public class VoToEntity {
    public static MskuInfoEpitaphEntity epitaphVoToEntity(EpitaphSaveVo epitaphSaveVo) {
        MskuInfoEpitaphEntity epitaph = new MskuInfoEpitaphEntity();
        epitaph.setId(epitaphSaveVo.getId());
        epitaph.setEpitaph(epitaphSaveVo.getEpitaph());
        return epitaph;
    }
}
