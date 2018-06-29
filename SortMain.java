package Project1;


import java.io.FileNotFoundException;

/**
 * SortMain.java
 * 
 * Driver program that runs the BenchmarkSorts methods. 
 */

public class SortMain {

  public static void main(String[] args) {
    //Create data test with 10 different sizes
    int[] sizes = {50, 100, 500, 1000, 1500, 2000, 2500, 3000, 3500, 4000};
    
    // Create a new benchmark class
    BenchmarkSorts benchmark = new BenchmarkSorts(sizes);
    
    // Run the sorts
    try {
      benchmark.runSorts();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    
    // Display the benchmark report
    benchmark.displayReport();
  }
}
