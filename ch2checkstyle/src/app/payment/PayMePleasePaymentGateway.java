package app.payment;

import java.math.BigDecimal;
import app.domain.api.PaymentGateway;

public class PayMePleasePaymentGateway implements PaymentGateway {

  private PayMePlease payme;

  public PayMePleasePaymentGateway(String uri) {
    this.payme = new PayMePlease(uri);
  }

  @Override
  public void pay(BigDecimal amount, String creditCardNumber,
      String securityCode) {
    this.payme.charge(amount, creditCardNumber, securityCode);
  }
}
