package main;

import java.io.IOException;
import java.math.BigDecimal;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import domain.Cashier;
import domain.CreditCard;

public class Main {

  public static void main(String args[]) throws IOException {

    // injecting by spring, package private works for notifications and payment
    // but I cannot restrict notifications and payment to reference other classes from domain
    @SuppressWarnings("resource")
    var context = new ClassPathXmlApplicationContext("injection-config.xml");
    Cashier cashier = context.getBean(domain.Cashier.class);
    cashier.pay(new BigDecimal(1000), new CreditCard("362-658-125", "985"),
        "user@em.com");
  }
}
