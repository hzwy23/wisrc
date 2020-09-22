package com.wisrc.quality.webapp.service.impl;

import com.wisrc.quality.webapp.service.SamplingPlanService;
import com.wisrc.quality.webapp.dao.SamplingPlanDao;
import com.wisrc.quality.webapp.utils.Result;
import com.wisrc.quality.webapp.vo.samplingPlan.GetAcReVO;
import com.wisrc.quality.webapp.vo.samplingPlan.GetCodeAndQuantity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

@Service
public class SamplingPlanImplService implements SamplingPlanService {
    //样本字码 数量范围界限值
    private static final int FIRST_NUMBER = 2;
    //样本字码 数量范围界限值
    private static final int SECOND_NUMBER = 500001;
    //拼接数据库表的column字段名使用的前缀与后缀
    private static final String PREFIX_FIRST = "c_";
    private static final String SUFFIX_FIRST = "_ac";
    private static final String PREFIX_SECOND = "c_";
    private static final String SUFFIXSECOND = "_re";
    private final Logger logger = LoggerFactory.getLogger(SamplingPlanImplService.class);
    @Autowired
    private SamplingPlanDao samplingPlanDao;


    @Override
    public Result getCodeAndQuantity(@Valid GetCodeAndQuantity vo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Result.failure(423, bindingResult.getFieldError().getDefaultMessage(), vo);
        }

        //检验水平参数
        Integer inspectionLevelCd = vo.getInspectionLevelCd();
        //抽样方案参数
        Integer samplingPlanCd = vo.getSamplingPlanCd();
        //实际检验数量
        Integer actualInspectionQuantity = vo.getActualInspectionQuantity();
        // 一 获取样本字码
        String levelColumnName = LevelCdColumn.get(inspectionLevelCd);
        Map paraMapOne = new HashMap();
        paraMapOne.put("columnName", levelColumnName);
        paraMapOne.put("quantity", actualInspectionQuantity);
        String code;
        //前面已经做检验 actualInspectionQuantity必须大于2的int
        if (actualInspectionQuantity >= FIRST_NUMBER && actualInspectionQuantity < SECOND_NUMBER) {
            code = samplingPlanDao.getCodeOne(paraMapOne);
        } else {
            code = samplingPlanDao.getCodeTwo(paraMapOne);
        }

        //根据code去查抽样数量
        Integer sampleQuantity = null;
        if (samplingPlanCd == null) {
            return new Result(9999, "抽样方案参数[samplingPlanCd]不能为空", null);
        } else if (samplingPlanCd == 1) {
            //正常国标
            sampleQuantity = samplingPlanDao.getSampleQuantityOne(code);
        } else if (samplingPlanCd == 2) {
            //加严国标
            sampleQuantity = samplingPlanDao.getSampleQuantityTwo(code);
        } else if (samplingPlanCd == 3) {
            //放宽国标
            sampleQuantity = samplingPlanDao.getSampleQuantityThree(code);
        } else {
            return new Result(9999, "抽样方案参数[samplingPlanCd]参数不再1，2，3之间，请确认后重试", null);
        }
        if (sampleQuantity == null) {
            return new Result(9999, "未找到抽样数，请确认查询条件后重试", null);
        } else {
            Map result = new HashMap();
            result.put("code", code);
            result.put("sampleQuantity", sampleQuantity);
            return Result.success(result);
        }
    }

    @Override
    public Result getAcRe(@Valid GetAcReVO vo, BindingResult bindingResult) {
        Double AQL = vo.getAQL();
        String code = vo.getCode();
        Integer samplingPlanCd = vo.getSamplingPlanCd();
        // 二 获取Ac, Re
        //将数字转为查询的字段名字符
        String aQLStr = null;
        try {
            aQLStr = AQLColumn.get(AQL);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("AQL条件不正确", e);
            return new Result(9999, "AQL条件不正确", null);
        }
        String dataColumnNameFirst = PREFIX_FIRST + aQLStr + SUFFIX_FIRST;
        String dataColumnNameSecond = PREFIX_SECOND + aQLStr + SUFFIXSECOND;

        Map paraMapTwo = new HashMap();
        paraMapTwo.put("dataColumnNameFirst", dataColumnNameFirst);
        paraMapTwo.put("dataColumnNameSecond", dataColumnNameSecond);
        paraMapTwo.put("code", code);
        Map resultMap;
        if (samplingPlanCd == null) {
            return new Result(9999, "抽样方案参数[samplingPlanCd]不能为空", null);
        } else if (samplingPlanCd == 1) {
            //正常国标
            resultMap = samplingPlanDao.getAcReNormal(paraMapTwo);
        } else if (samplingPlanCd == 2) {
            //加严国标
            resultMap = samplingPlanDao.getAcReStrict(paraMapTwo);
        } else if (samplingPlanCd == 3) {
            //放宽国标
            resultMap = samplingPlanDao.getAcReLax(paraMapTwo);
        } else {
            return new Result(9999, "抽样方案参数[samplingPlanCd]参数不再1，2，3之间，请确认后重试", null);
        }
        return Result.success(resultMap);
    }


    private Boolean checkAQL(@NotNull(message = "[AQL]不能为空") Double aql) {
        return true;
    }

}

class LevelCdColumn {
    private static LevelCdColumn instance = null;
    private Map<Integer, String> codesMap = new HashMap<Integer, String>();

    public LevelCdColumn() {
        codesMap.put(1, "a");
        codesMap.put(2, "b");
        codesMap.put(3, "c");
        codesMap.put(4, "d");
        codesMap.put(5, "e");
        codesMap.put(6, "f");
        codesMap.put(7, "g");
    }

    public static String get(Integer code) {
        if (instance == null) {
            instance = new LevelCdColumn();
        }
        return instance.getMsg(code);
    }

    private String getMsg(Integer code) {
        return codesMap.get(code);
    }
}

class AQLColumn {
    private static AQLColumn instance = null;
    private final Logger logger = LoggerFactory.getLogger(AQLColumn.class);
    private Map<Double, String> codesMap = new HashMap<Double, String>();

    public AQLColumn() {
        codesMap.put(0.010, "0d010");
        codesMap.put(0.015, "0d015");
        codesMap.put(0.025, "0d025");
        codesMap.put(0.040, "0d040");
        codesMap.put(0.065, "0d065");
        codesMap.put(0.10, "0d10");
        codesMap.put(0.15, "0d15");
        codesMap.put(0.25, "0d25");
        codesMap.put(0.40, "0d40");
        codesMap.put(0.65, "0d65");
        codesMap.put(1.0, "1d0");
        codesMap.put(1.5, "1d5");
        codesMap.put(2.5, "2d5");
        codesMap.put(4.0, "4d0");
        codesMap.put(6.5, "6d5");
        codesMap.put(10.0, "10");
        codesMap.put(15.0, "15");
        codesMap.put(25.0, "25");
        codesMap.put(40.0, "40");
        codesMap.put(65.0, "65");
        codesMap.put(100.0, "100");
        codesMap.put(150.0, "150");
        codesMap.put(250.0, "250");
        codesMap.put(400.0, "400");
        codesMap.put(650.0, "650");
        codesMap.put(1000.0, "1000");
    }

    public static String get(Double code) throws Exception {
        if (instance == null) {
            instance = new AQLColumn();
        }
        return instance.getMsg(code);
    }

    private String getMsg(Double code) throws Exception {
        String msg = null;
        for (Map.Entry<Double, String> entry : codesMap.entrySet()) {
            Double key = entry.getKey();
            if (key.equals(code)) {
                msg = entry.getValue();
                break;
            }
        }
        if (msg != null) {
            return msg;
        } else {
            logger.info("前段的AQL的值：" + code + "非法,请确认");
            throw new Exception("前段的AQL的值：" + code + "非法,请确认");
        }

    }


}


