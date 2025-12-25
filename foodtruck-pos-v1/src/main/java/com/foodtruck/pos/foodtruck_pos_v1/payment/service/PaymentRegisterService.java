package com.foodtruck.pos.foodtruck_pos_v1.payment.service;

import com.foodtruck.pos.foodtruck_pos_v1.payment.domain.Payment;
import com.foodtruck.pos.foodtruck_pos_v1.payment.domain.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PaymentRegisterService {
    private final PaymentRepository paymentRepository;
    @Transactional
    public void registerPayment(Payment payment) {
        paymentRepository.save(payment);
    }
}
