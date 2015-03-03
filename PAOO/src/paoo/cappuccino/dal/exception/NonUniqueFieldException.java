package paoo.cappuccino.dal.exception;

/**
 * Exception used when it is impossible to insert an entity in the database because one of it's
 * fields is unique but already exists. (ie: unique constraint violation)
 *
 * @author Guylian Cox
 */
public class NonUniqueFieldException extends DalException {

  public NonUniqueFieldException(String message) {
    super(message);
  }

  public NonUniqueFieldException(String message, Throwable cause) {
    super(message, cause);
  }
}
