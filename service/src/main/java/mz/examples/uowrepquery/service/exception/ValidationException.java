package mz.examples.uowrepquery.service.exception;

// Please, refer to
// https://www.linkedin.com/post/edit/using-exceptions-just-exceptional-notification-pattern-manuel-zamboni
// for a betters ways to represent errors.
public class ValidationException extends RuntimeException {
  public ValidationException(String message) {
    super(message);
  }
}
