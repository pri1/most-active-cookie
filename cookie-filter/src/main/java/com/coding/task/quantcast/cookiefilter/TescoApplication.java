package com.coding.task.quantcast.cookiefilter;

import java.util.Arrays;
import java.util.List;

public class TescoApplication {

  public static void main(String[] args) {

    Interval interval1 = new Interval(8, 10);

    Interval interval2 = new Interval(9, 12);

    Interval interval3 = new Interval(14, 16);

    Interval interval4 = new Interval(8, 10);


    List<Interval> list = Arrays.asList(interval4, interval3, interval1, interval2);

    ShiftTime shiftTime = new ShiftTimeImpl();

    int result[][] = shiftTime.get(list);

    for (int[] interval : result) {
      System.out.println("{" + interval[0] + "," + interval[1] + "}");

    }
  }

}
