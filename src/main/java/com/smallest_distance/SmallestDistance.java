package com.smallest_distance;

public class SmallestDistance {

    private static int minDistance(int[] ar){
        int arrayLength = ar.length;
        int minDistance = Integer.MAX_VALUE;

        for(int i = 0; i < arrayLength; i++){
            for(int j = i + 1; j < arrayLength; j++){
                minDistance = Math.min(minDistance, Math.abs(ar[i] - ar[j]));
            }
        }
        return minDistance;
    }

    public static void main(String[] args){
        int[] numArray = new int[] {4, 8, 6, 1, 2, 9, 4};

        System.out.println();
    }
}
