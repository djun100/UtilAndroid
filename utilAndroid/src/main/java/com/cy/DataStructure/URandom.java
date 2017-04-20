package com.cy.DataStructure;

/**
 * Created by cy on 2017/4/20.
 */

public class URandom {
    public static int getInt(int min,int max){
        int count = (int) (Math.random() * (max - min + 1)) + min;
        return count;
    }

    public static void main(String[] args) {
        System.out.println(getInt(1,3));
    }
}
