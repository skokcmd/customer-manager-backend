package com.skokcmd.fraudCheck;

import com.skokcmd.fraud.FraudstersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class FraudCheckService {
  private final FraudCheckHistoryRepository fraudCheckHistoryRepository;
  private final FraudstersService fraudstersService;

  // sees if customer's email is in the fraudsters list & records the check
  public boolean isCustomerFraudsterAndMakeRecord(String customerEmail) {

    boolean isFraudster = fraudstersService.isFraudster(customerEmail);

    // creates fraud check
    FraudCheckHistory fraudCheck =
        FraudCheckHistory.builder()
            .customerEmail(customerEmail)
            .passed(!isFraudster)
            .triggeredAt(LocalDateTime.now())
            .build();
    fraudCheckHistoryRepository.saveAndFlush(fraudCheck);
    return isFraudster;
  }
}
