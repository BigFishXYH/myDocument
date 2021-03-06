package com.xhx.java;


import org.junit.Test;

import java.util.Arrays;

/**
 * 插入排序
 * 每次取出一个新的元素，插入到已经有序的数组中，并保持有序
 * 例如 6 3 5 9 1
 * 第一个元素为6，可以认为6为已经有序的元素，第一个元素就不用取了
 * 取第二个元素3，排序后
 * 3 6 5 9 1
 * 取第三个元素5，排序后
 * 3 5 6 9 1
 * 取第四个元素9，排序后
 * 3 5 6 9 1
 * 取第五个元素1，排序后
 * 1 3 5 6 9
 * <p>
 * 核心思想，每次在无序的序列中取出一个元素放到前面有序的序列中，并依旧保持前面序列为有序
 */
public class TestApp {


    @Test
    public void test01() {
        int[] array = {6, 3, 5, 9, 1};
       // sort1(array);
        sort2(array);
        System.out.println(Arrays.toString(array));

    }

    public int[] sort1(int[] array) {
        //第一个元素默认有序，从第二个元素开始，往有序元素中插
        for (int i = 1; i < array.length; i++) {

            int j = i - 1;

            //寻找插入位置
            while (j >= 0 && array[j] > array[i]) {
                j--;
            }
            //因为不满足上面while条件时退出，所以多减了1
            j++;

            if (j <= i - 1) {
                int k = i;
                int tem = array[k];
                while (k > j) {
                    array[k] = array[--k];
                }
                array[k] = tem;
            }

        }
        return array;
    }


    //优化，在找位置的时候，直接把元素替换了
    public int[] sort2(int[] array) {
        for (int i = 1; i < array.length; i++) {
            int j = i - 1;

            //要插入的元素
            int tem = array[i];
            while (j >= 0 && array[j] > tem) {
                //移动元素
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = tem;
        }
        return array;
    }

}
