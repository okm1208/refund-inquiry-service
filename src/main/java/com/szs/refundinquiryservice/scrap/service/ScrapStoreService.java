package com.szs.refundinquiryservice.scrap.service;

import com.szs.refundinquiryservice.auth.service.SecretService;
import com.szs.refundinquiryservice.common.data.entity.User;
import com.szs.refundinquiryservice.common.data.entity.UserIncome;
import com.szs.refundinquiryservice.common.data.entity.UserTaxDeduction;
import com.szs.refundinquiryservice.common.data.entity.UserTaxDeductionMonth;
import com.szs.refundinquiryservice.common.data.repository.UserIncomeRepository;
import com.szs.refundinquiryservice.common.data.repository.UserRepository;
import com.szs.refundinquiryservice.common.data.repository.UserTaxDeductionRepository;
import com.szs.refundinquiryservice.common.domain.ApiResponseCode;
import com.szs.refundinquiryservice.common.domain.BusinessException;
import com.szs.refundinquiryservice.common.domain.enums.TaxDeductionType;
import com.szs.refundinquiryservice.external.domain.response.ScrapApiData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : km.oh
 * @date : 6/15/24
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ScrapStoreService {

    private final UserRepository userRepository;

    private final UserIncomeRepository userIncomeRepository;

    private final UserTaxDeductionRepository userTaxDeductionRepository;

    private final SecretService secretService;

    @Transactional
    public void saveScrapInfo(final String regNo, final ScrapApiData scrapApiData) {
        final String encryptRegNo = secretService.encryptRegNo(regNo);

        final User user = userRepository.findUserByRegNo(encryptRegNo)
                .orElseThrow(() -> new BusinessException(ApiResponseCode.NOT_FOUND, "사용자가 존재 하지 않습니다."));

        final ScrapApiData.IncomeDeduction incomeDeduction = scrapApiData.getIncomeDeduction();

        final String baseYear = incomeDeduction.getBaseYear();
        userIncomeRepository.findUserIncomeByUserAndBaseYear(user, baseYear)
                .ifPresent(userIncome -> {
                    throw new BusinessException(ApiResponseCode.BAD_REQUEST, "이미 스크래핑 완료된 정보 입니다.");
                });

        final UserIncome userIncome = new UserIncome(user, baseYear, scrapApiData.getComprehensiveIncomeAmount());
        userIncomeRepository.save(userIncome);

        final UserTaxDeduction userTaxDeduction = new UserTaxDeduction(
                user,
                incomeDeduction.convertTaxDeductionAmountToBigDecimal(),
                baseYear,
                new ArrayList<>()
        );

        userTaxDeductionRepository.save(userTaxDeduction);

        final List<UserTaxDeductionMonth> nssUserTaxDeductionMonthList = convertNssTaxDeductionToUserTaxDeductionMonthList(
                user,
                userTaxDeduction,
                incomeDeduction.getNssTaxDeductionList()
        );

        final List<UserTaxDeductionMonth> creditCardUserTaxDeductionMonthList = convertCreditCardTaxDeductionToUserTaxDeductionMonthList(
                user,
                userTaxDeduction,
                incomeDeduction.getCreditCardTaxDeduction()
        );

        userTaxDeduction.addUserTaxDeductionMonth(nssUserTaxDeductionMonthList);
        userTaxDeduction.addUserTaxDeductionMonth(creditCardUserTaxDeductionMonthList);

    }

    private List<UserTaxDeductionMonth> convertNssTaxDeductionToUserTaxDeductionMonthList(
            final User user,
            final UserTaxDeduction userTaxDeduction,
            final List<ScrapApiData.NssTaxDeduction> nssTaxDeductionList
    ) {

        return nssTaxDeductionList.stream()
                .map(v -> new UserTaxDeductionMonth(
                        user, v.getYearMonth(), v.convertDeductionAmountToBigDecimal(), TaxDeductionType.NSS, userTaxDeduction
                ))
                .collect(Collectors.toList());
    }

    private List<UserTaxDeductionMonth> convertCreditCardTaxDeductionToUserTaxDeductionMonthList(
            final User user,
            final UserTaxDeduction userTaxDeduction,
            final ScrapApiData.CreditCardTaxDeduction creditCardTaxDeduction) {

        return creditCardTaxDeduction.convertMonthListToStringList()
                .stream().map(v -> new UserTaxDeductionMonth(
                        user, v.getYearMonth(), v.convertDeductionAmountToBigDecimal(), TaxDeductionType.CREDIT_CARD, userTaxDeduction
                )).collect(Collectors.toList());
    }

}
