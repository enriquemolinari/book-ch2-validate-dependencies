package domain;

public class CreditCard {

  private String number;
  private String securityCode;

  public CreditCard(String number, String securityCode) {
    this.number = number;
    this.securityCode = securityCode;
  }

  public String securityCode() {
    return this.securityCode;
  }

  public String number() {
    return this.number;
  }

}
