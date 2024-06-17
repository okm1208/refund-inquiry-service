package com.szs.refundinquiryservice.user.validator;

import com.szs.refundinquiryservice.common.domain.ApiConstants;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

/**
 * @author : km.oh
 * @date : 6/15/24
 */
@Component
public class SignUpRegNoCheckValidator implements ConstraintValidator<SignUpRequestCheck, String> {

    @Override
    public void initialize(final SignUpRequestCheck constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(final String regNo, final ConstraintValidatorContext constraintValidatorContext) {
        if (!regNo.matches(ApiConstants.REG_NO_REGEX)) {
            return false;
        }
        return true;
    }

}
