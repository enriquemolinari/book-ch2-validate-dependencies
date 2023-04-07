package domain.api;

import java.math.BigDecimal;

public interface PaymentGateway {

  void pay(BigDecimal amount, String number, String securityCode);
}
