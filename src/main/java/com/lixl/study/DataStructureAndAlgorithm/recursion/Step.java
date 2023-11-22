package com.lixl.study.DataStructureAndAlgorithm.recursion;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : lixl
 * @date : 2020-11-07 13:18:56
 **/
public class Step {

    static Map<Integer, Integer> tempHashMap = new HashMap<>();

    public static int getKindNumber(int stepNumber) {
        if (stepNumber == 1) {
            return 1;
        }
        if (stepNumber == 2) {
            return 2;
        }
        if (tempHashMap.containsKey(stepNumber)) {
            return tempHashMap.get(stepNumber);
        }
        int i = getKindNumber(stepNumber - 1) + getKindNumber(stepNumber - 2);
        tempHashMap.put(stepNumber, i);
        return i;
    }

    public static void main(String[] args) {
        int kindNumber = getKindNumber(21);
        System.out.println(kindNumber);
    }
}
