package Project1;

import java.util.Random;
import java.awt.GridLayout;
import java.io.FileNotFoundException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 * BenchmarkSorts.java
 * 
 * A class designed to test sorting algorithms.
 */

public class BenchmarkSorts {
  private final int TESTCASES = 10; //Number of test cases
  private final int TRIALS = 50; //Number of trials per test case
  
  //Array to store the data
  private int[] sizes;
  private int[][][] testData;
  private int[][] iterativeCountResults = new int[this.TESTCASES][this.TRIALS];
  private long[][] iterativeTimeResults = new long[this.TESTCASES][this.TRIALS];
  private int[][] recursiveCountResults = new int[this.TESTCASES][this.TRIALS];
  private long[][] recursiveTimeResults = new long[this.TESTCASES][this.TRIALS];
  private Object[][] stats = new Object[this.TESTCASES][9];

  java.io.File iCountFile, iTimeFile, rCountFile, rTimeFile;
  java.io.PrintWriter iCountOutput, iTimeOutput, rCountOutput, rTimeOutput;
  
  /**
   * Class constructor
   * @param sizes 
   */  
  public BenchmarkSorts(int[] sizes) {
    this.sizes = sizes;
    createTestData();
  }
  
 /**
  * Creates the array and generates random integers to fill the array
  */
  private void createTestData() {
    Random randomGenerator = new Random();
    
    this.testData = new int[this.TESTCASES][this.TRIALS][];
   
    for(int i = 0; i < this.TESTCASES; i++) {
      for(int j = 0; j < this.TRIALS; j++) {
        testData[i][j] = new int[this.sizes[i]];
      
        for(int k = 0; k < this.sizes[i]; k++) {
          testData[i][j][k] = randomGenerator.nextInt(100);
        }        
      }
    } 
  }
  
    
  /**
   *  Run the sorts and store the results in the array and files
   * @throws FileNotFoundException 
   */
  public void runSorts() throws FileNotFoundException {
   
    this.iCountFile = new java.io.File("iCountFile.dat");
    this.iTimeFile = new java.io.File("iTimeFile.dat");
    this.rCountFile = new java.io.File("rCountFile.dat");
    this.rTimeFile = new java.io.File("rTimeFile.dat");    
    
    this.iCountOutput = new java.io.PrintWriter(this.iCountFile);
    this.iTimeOutput = new java.io.PrintWriter(this.iTimeFile);
    this.rCountOutput = new java.io.PrintWriter(this.rCountFile);
    this.rTimeOutput = new java.io.PrintWriter(this.rTimeFile);    
        
    
    System.out.print("Starting Tests");
    
   //Loop through the test cases
    for(int i = 0; i < this.TESTCASES; i++) {
      
      System.out.println("\nSorting List Size " + this.sizes[i] + ".");
      
      //Repeat for number of trails
      for(int j = 0; j < this.TRIALS; j++) {
        
        //Create a new radixSort
        SortInterface sort = new YourSort();
        
        //Clone the test data list
        int[] list = (int[])testData[i][j].clone();
       
        //Iteratively sort the list
        sort.iterativeSort(list);
        
        this.iterativeCountResults[i][j] = sort.getCount();
        this.iterativeTimeResults[i][j] = sort.getTime();
        
      
        this.iCountOutput.print(sort.getCount() + " ");
        this.iTimeOutput.print(sort.getTime() + " ");
        
        
               
        try {
          verifySorted(list);
        } catch (UnsortedException e) {
          
          e.printStackTrace();
        }
        
        //Create a YourSort for running the recusiveSort Test
        sort = new YourSort();
        
        //Clone the test data list
        list = (int[])testData[i][j].clone();
        
        //Recursively sort the list
        sort.recursiveSort(list);
        
        
        this.recursiveCountResults[i][j] = sort.getCount();
        this.recursiveTimeResults[i][j] = sort.getTime();
        
     
        this.rCountOutput.print(sort.getCount() + " ");
        this.rTimeOutput.print(sort.getTime() + " ");
        
       
        try {
          verifySorted(list);
        } catch (UnsortedException e) {
       
          e.printStackTrace();
        }
               
      }
      
   
      this.iCountOutput.print("\n");
      this.iTimeOutput.print("\n");
      this.rCountOutput.print("\n");
      this.rTimeOutput.print("\n");
    }
    
    System.out.println("\nTests Complete.");
    
   
    this.iCountOutput.close();
    this.iTimeOutput.close();
    this.rCountOutput.close();
    this.rTimeOutput.close();    
  }
  
  
  /**
   * display the results in a jtable
   */
  public void displayReport() {
  
    calculateStats();
    
    String[] columnNames1 ={"Data Set Size n", "Iterative", "Recursive"};
    String[] columnNames = {"Data Set Size n", "Iterative Average Critical Operation Count", "Iterative Standard Deviation of Count",
        "Iterative Average Execution Time (ms)", "Iterative Standard Deviation of Time", "Recursive Average Critical Operation Count", 
        "Recursive Standard Deviation of Count", "Recursive Average Execution Time (ms)", "Recursive Standard Deviation of Time(ms)"
    };
    
    JPanel jpanel = new JPanel(new GridLayout(1,0));
    
    final JTable table = new JTable(this.stats,columnNames);
    JScrollPane scrollpane = new JScrollPane(table);
    table.setFillsViewportHeight(true);
    
    jpanel.add(scrollpane);    
    jpanel.setOpaque(true);
    
    JFrame frame = new JFrame("Test Results");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setContentPane(jpanel);
    
    frame.pack();
    frame.setVisible(true);    
  }
  
  /**
   * Verifies the list is sorted.
   * @param list
   * @throws UnsortedException 
   */
  private void verifySorted(int[] list) throws UnsortedException {
    for(int i = 0; i < list.length - 1; i++) {
      if(list[i] > list[i+1]) {
        String errorMsg = "\nElements not Sorted Correctly.\n"
            + list[i] + " is not less than " + list[i+1] +".\n";
        
        throw new UnsortedException(errorMsg);
      }
    }
  }
  
  
 /**
  * Calculate the mean of the list.
  * @param list
  * @return 
  */
  private double mean(final int list[]) {
    double mean = 0.0;
    
    for(int i = 0; i < list.length; i++) {
      mean += list[i];
    }
    
    return mean / list.length;    
  }
  
  /**
   * Calculate the mean of the list.
   * @param list
   * @return 
   */
  private double mean(final long list[]) {
    double mean = 0.0;
    
    for(int i = 0; i < list.length; i++) {
      mean += list[i];
    }
    
    return mean / list.length;    
  }
  
  /**
   * Calculate the standard deviation of the list.
   * @param list
   * @param mean
   * @return 
   */
  private double standardDeviation(final int list[], final double mean) {
    double sum = 0.0;
    
    for(int i = 0; i < list.length; i++) {
      sum += Math.pow((list[i] - mean), 2);
    }
    
    sum = sum / list.length;
    
    return Math.sqrt(sum); 
  }
  
  /**
   * Calculate the standard deviation of the list.
   * @param list
   * @param mean
   * @return 
   */
  private double standardDeviation(final long list[], final double mean) {
    double sum = 0.0;
    
    for(int i = 0; i < list.length; i++) {
      sum += Math.pow((list[i] - mean), 2);
    }
    
    sum = sum / list.length;
    
    return Math.sqrt(sum);    
  }
  
  /**
   * Calculate all stats for the list.
   */
  private void calculateStats() {
    for(int i = 0; i < this.TESTCASES; i++) {
      // Test Cases
      this.stats[i][0] = this.sizes[i];
      
      // Iterative Results
      this.stats[i][1] = mean(this.iterativeCountResults[i]);
      this.stats[i][2] = standardDeviation(this.iterativeCountResults[i], (double)this.stats[i][1]);
      this.stats[i][3] = mean(this.iterativeTimeResults[i]) / 100000;
      this.stats[i][4] = standardDeviation(this.iterativeTimeResults[i], mean(this.iterativeTimeResults[i])) / 100000;
      
      // Recursive Results
      this.stats[i][5] = mean(this.recursiveCountResults[i]);
      this.stats[i][6] = standardDeviation(this.recursiveCountResults[i], (double)this.stats[i][5]);
      this.stats[i][7] = mean(this.recursiveTimeResults[i]) / 100000;
      this.stats[i][8] = standardDeviation(this.recursiveTimeResults[i], mean(this.recursiveTimeResults[i])) / 100000;
    } 
  }  
}
