package app.domain.api;

@SuppressWarnings("serial")
public class DomainException extends RuntimeException {

  public DomainException(String msg, Exception e) {
    super(msg, e);
  }

}
