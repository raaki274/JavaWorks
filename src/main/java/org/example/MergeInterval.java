package org.example;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.SequencedCollection;

public class MergeInterval {

    public int[][] mergeOverlapping(int[][] intervals) {
        Arrays.sort(intervals, Comparator.comparingInt(a-> a[0]));
        SequencedCollection<int[]> sc = new LinkedList<>();
        for(int[] current:intervals) {
            if(sc.isEmpty() || sc.getLast()[1] < current[0]) {
                sc.add(current);
            } else {
                sc.getLast()[1] = Math.max(sc.getLast()[1], current[1]);
            }
            System.out.println("current :["+current[0]+", "+current[1]+"]");
            System.out.println("SC :"+Arrays.deepToString(sc.toArray(int[][]::new)));
        }
        return sc.toArray(int[][]::new);
    }

    public static void main(String[] args) {
        int[][] intvs = {{1, 3}, {2, 6}, {8, 10}};
        System.out.println(Arrays.deepToString(new MergeInterval().mergeOverlapping(intvs)));
    }
}

