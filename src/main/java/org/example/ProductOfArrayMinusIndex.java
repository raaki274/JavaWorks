package org.example;

import java.util.Arrays;

public class ProductOfArrayMinusIndex {
    public long[] productExceptSelf(long[] nums) {
        int n = nums.length;
        long[] result = new long[n];

        // Step 1: Forward pass - store the product of all elements to the LEFT of index i
        long prefix = 1;
        for (int i = 0; i < n; i++) {
            result[i] = prefix;
            System.out.println("result[i] forward: "+result[i]);
            prefix *= nums[i];
        }

        // Step 2: Backward pass - multiply by the product of all elements to the RIGHT of index i
        long suffix = 1;
        for (int i = n - 1; i >= 0; i--) {
            System.out.println("result[i] before: "+result[i]+" and suffix: "+suffix);
            result[i] *= suffix;
            System.out.println("result[i] after: "+result[i]);
            suffix *= nums[i];
        }
        return result;
    }

    public static void main(String[] args) {
        long[] nums = {3, 8, 4, 6, 5, 9, 12, 18, 7, 24, 68};
        System.out.println(Arrays.toString(new ProductOfArrayMinusIndex().productExceptSelf(nums)));
    }
}
