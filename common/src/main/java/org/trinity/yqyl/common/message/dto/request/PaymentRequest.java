package org.trinity.yqyl.common.message.dto.request;

import org.trinity.common.dto.request.AbstractRequest;
import org.trinity.common.dto.validator.OnValid;
import org.trinity.yqyl.common.message.dto.domain.PaymentDto;

public class PaymentRequest extends AbstractRequest {
    private PaymentDto payment;

    @OnValid
    public PaymentDto getPayment() {
        return payment;
    }

    public void setPayment(final PaymentDto payment) {
        this.payment = payment;
    }
}
