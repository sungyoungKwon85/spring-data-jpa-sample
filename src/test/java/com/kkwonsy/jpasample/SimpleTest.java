package com.kkwonsy.jpasample;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class SimpleTest {

    @Test
    public void stream_sum_test() {
        List<Integer> list = new ArrayList<>();
        list.add(10);
        list.add(290);
        list.add(30);
        list.add(40);
        list.add(50);
        System.out.println(list.stream().reduce(0, Integer::sum));
    }
}
