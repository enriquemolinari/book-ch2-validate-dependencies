package app.domain;

import java.math.BigDecimal;
import app.domain.api.Notify;
import app.domain.api.PaymentGateway;

public class Cashier {

  private PaymentGateway gateway;
  private Notify queue;

  public Cashier(PaymentGateway gateway, Notify queue) {
    this.gateway = gateway;
    this.queue = queue;
  }

  public void pay(BigDecimal amountToCharge, CreditCard card,
      String userEmail) {
    this.gateway.pay(amountToCharge, card.number(), card.securityCode());
    this.queue.publish(userEmail);
  }
}
