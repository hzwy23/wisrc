package com.wisrc.merchandise.utils.valid.validation;

import com.wisrc.merchandise.utils.valid.PositiveDouble;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author Shinelon
 */
public class PositiveDoubleConstraintValidator implements ConstraintValidator<PositiveDouble, Object> {

    @Override
    public boolean isValid(Object arg0, ConstraintValidatorContext arg1) {
        // 要验证的字符串
        String str = arg0.toString();
        // 验证规则
        String regEx = "^[1-9]\\d*\\.\\d{2}|0\\.([1-9][0-9]|[0-9][1-9])$";
        // 编译正则表达式
        Pattern pattern = Pattern.compile(regEx);
        // 忽略大小写的写法
        // Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str);
        // 字符串是否与正则表达式相匹配
        boolean rs = matcher.matches();

        //返回true或者false表示是否校验成功
        return rs;
    }

    //初始化
    @Override
    public void initialize(PositiveDouble arg0) {
    }

}
