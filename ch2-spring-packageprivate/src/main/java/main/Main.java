package main;

import java.math.BigDecimal;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import domain.Cashier;
import domain.CreditCard;

public class Main {

  public static void main(String args[]) {

    // injecting by spring, package private works for notifications and payment
    // but I cannot restrict notifications and payment to reference other classes from domain
    @SuppressWarnings("resource")
    var context = new ClassPathXmlApplicationContext("injection-config.xml");
    Cashier cashier = context.getBean(domain.Cashier.class);
    cashier.pay(new BigDecimal(1000), new CreditCard("362-658-125", "985"),
        "user@em.com");

    // Class<?> notify = Class.forName("notifications.BigQueueNotify");
    // Constructor<?> constNotify =
    // notify.getConstructor(String.class, String.class);
    // constNotify.setAccessible(true);
    // Notify notifyInstance =
    // (Notify) constNotify.newInstance("/home/enrique", "notification-queue");
    //
    // Class<?> payment = Class.forName("payment.PayMePleasePaymentGateway");
    // Constructor<?> constPayment = payment.getConstructor(String.class);
    // constPayment.setAccessible(true);
    // PaymentGateway paymentInstance = (PaymentGateway) constPayment
    // .newInstance("https://paymeplease-gateway.com");
    //
    // new Cashier(paymentInstance, notifyInstance).pay(new BigDecimal(1000),
    // new CreditCard("232-965", "258"), "email@user.com");

  }
}
