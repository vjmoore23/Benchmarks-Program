package Project1;
/**
 * Simple exception class used by the benchmarkSorts class. Is thrown when a list
 * is unexpectedly unsorted.
 */

public class UnsortedException extends Exception {
  private static final long serialVersionUID = 1L;
  
  public UnsortedException(String msg) {
    super(msg);
  }
}
