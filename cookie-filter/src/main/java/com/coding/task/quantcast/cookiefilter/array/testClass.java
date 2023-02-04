package com.coding.task.quantcast.cookiefilter.array;

import java.util.Arrays;
import java.util.PriorityQueue;
class ArrayBucket implements Comparable<ArrayBucket>{
   int[] arr;
   int index;
   public ArrayBucket(int[] arr, int index){
      this.arr = arr;
      this.index = index;
   }
   @Override
   public int compareTo(ArrayBucket o){
      return this.arr[this.index] - o.arr[o.index];
   }
}
public class testClass{
   public static int[] mergeSortedArray(int[][] arr){
      PriorityQueue<ArrayBucket> queue = new
      PriorityQueue<ArrayBucket>();
      int total = 0;
      for (int i = 0; i < arr.length; i++){
         queue.add(new ArrayBucket(arr[i], 0));
         total = total + arr[i].length;
      }
      int m = 0;
      int result[] = new int[total];
      while (!queue.isEmpty()){
         ArrayBucket ac = queue.poll();
         result[m++] = ac.arr[ac.index];
         if (ac.index < ac.arr.length - 1){
            queue.add(new ArrayBucket(ac.arr, ac.index + 1));
         }
      }
      return result;
   }
   public static void main(String[] args){
      int[] arr1 = { 1, 3, 5, 7 };
      int[] arr2 = { 2, 4, 6, 8 };
      int[] arr3 = { 0, 9, 10, 11 };
      int[] result = mergeSortedArray(new int[][] { arr1, arr2, arr3 });
      System.out.println("The final merged sorted array is :- "+Arrays.toString(result));
   }
}