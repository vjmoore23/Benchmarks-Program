package Project1;

import java.util.ArrayList;
import java.util.Stack;
/**
 * YourSort.java implements the QuickSort recursively and iteratively.
 * 
 */
public class YourSort implements SortInterface {
  
  private ArrayList<Integer> listOne = new ArrayList<Integer>();
  private ArrayList<Integer> listTwo = new ArrayList<Integer>();

  private static int count = 0;

  private long startTime, stopTime;
  

/**
 * Method implements the recursive sort.
 * @param list
 * @return 
 */
  public int[] recursiveSort(int[] list) {
    
    //Start time
    this.startTime = System.nanoTime();
     RecursiveSort(list, 0, list.length-1);
    
    //Stop the time
    this.stopTime = System.nanoTime();
    
    return list;
  }
  /**
     * method RecursiveSort
     * @param list
     * @param start
     * @param stop 
     */
    private static void RecursiveSort(int[] list, int start, int stop) {
        if (start >= stop) {
            return;
        }
        int pivotValue = list[start];
        int startingValue = start - 1;
        int stoppingValue = stop + 1;
        while (startingValue < stoppingValue) {
            startingValue++;
            count++;
            while (list[startingValue] < pivotValue) { startingValue++; }
            stoppingValue--;
            while (list[stoppingValue] > pivotValue) { stoppingValue--; }
            if (startingValue < stoppingValue) {
                SwapElement(list, startingValue, stoppingValue);
            }
        }
        RecursiveSort(list, start, stoppingValue);
        RecursiveSort(list, stoppingValue + 1, stop);
    }
  
  /**
   * 
   * @param list
   * @param x
   * @return 
   */
  private ArrayList<Integer> recursiveSort(ArrayList<Integer> list, int x) {
    
    //Create temp array lists
    ArrayList<Integer> tempOne = new ArrayList<Integer>();
    ArrayList<Integer> tempTwo = new ArrayList<Integer>();
    
    // Base case
    if(x <= 0) {
      return list;
    }
    x  = x >> 1;
    for(int i = 0; i < list.size(); i++) {
         count++;
      if((x & list.get(i)) == x) {
        tempTwo.add(list.get(i));
      } else {
        tempOne.add(list.get(i));
      }      
    }
    
    //Call the recursive method on each list
    recursiveSort(tempOne, x);
    recursiveSort(tempTwo, x);
    
     //Copy the list back to the originals
    for(int k = 0; k < list.size(); k++) {
      if(!tempOne.isEmpty()) {
        list.set(k, tempOne.remove(0));
      } else {
        list.set(k, tempTwo.remove(0));
      }
    }   
    
    return list;
  }
  

 /**
  * Implements the iterative sort.
  * @param list
  * @return 
  */
  @Override
  public int[] iterativeSort(int[] list) {
    
    //Start the time
    this.startTime = System.nanoTime();
    
    Stack stack = new Stack();
            int pivotValue;
            int pivotValueIndex = 0;
            int leftIndex = pivotValueIndex + 1;
            int rightIndex = list.length - 1;

            stack.push(pivotValueIndex);
            stack.push(rightIndex);

            int tempLeftIndex;
            int tempRightIndex;

            while (stack.size() > 0){
                tempRightIndex = (int)stack.pop();
                tempLeftIndex = (int)stack.pop();

                leftIndex = tempLeftIndex + 1;
                pivotValueIndex = tempLeftIndex;
                rightIndex = tempRightIndex;

                pivotValue = list[pivotValueIndex];

                if (leftIndex > rightIndex)
                    continue;

                while (leftIndex < rightIndex){
                    while ((leftIndex <= rightIndex) && (list[leftIndex] <= pivotValue))
                        leftIndex++;
                    while ((leftIndex <= rightIndex) && (list[rightIndex] >= pivotValue))
                        rightIndex--;
                    if (rightIndex >= leftIndex)   	
                        SwapElement(list, leftIndex, rightIndex);
                }

                if (pivotValueIndex <= rightIndex)
                    if( list[pivotValueIndex] > list[rightIndex])
                        SwapElement(list, pivotValueIndex, rightIndex);
               
                if (tempLeftIndex < rightIndex){
                    stack.push(tempLeftIndex);
                    stack.push(rightIndex - 1);
                }

                if (tempRightIndex > rightIndex){
                    stack.push(rightIndex + 1);
                    stack.push(tempRightIndex);
                }
            }
    
    //Stop the time
    this.stopTime = System.nanoTime();
       
    return list;
  }
  
    /**
         * method SwapElement
         * @param list
         * @param leftElement
         * @param rightElement 
         */
        private static void SwapElement( int[] list, int leftElement, int rightElement){
            int temp = list[leftElement];
            list[leftElement] = list[rightElement];
            list[rightElement] = temp;
        }

  @Override
  public int getCount() {
    return count;
  }

  @Override
  public long getTime() {
    return this.stopTime - this.startTime;
  }
}
