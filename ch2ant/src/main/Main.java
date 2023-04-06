package main;

import java.io.IOException;
import java.math.BigDecimal;
import domain.Cashier;
import domain.CreditCard;
import notifications.BigQueueNotify;
import payment.PayMePleasePaymentGateway;

public class Main {

  public static void main(String[] args) throws IOException {
    new Cashier(
        new PayMePleasePaymentGateway("https://paymeplease-gateway.com"),
        new BigQueueNotify("/home/enrique", "notification-queue"))
            .pay(new BigDecimal(1000), new CreditCard("dd", "dd"), "sd");
  }

}
