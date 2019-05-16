package com.lx;

import java.math.BigDecimal;

public class testBigDecimal {
    public static void main(String[] args){


        BigDecimal bd=new BigDecimal(1000000000);
        System.out.println(bd);


            BigDecimal a = new BigDecimal("0.89");
            BigDecimal b = new BigDecimal("1.23");
            BigDecimal c = new BigDecimal("0.89");

            int result1 = a.compareTo(b);
            int result2 = a.compareTo(c);
            int result3 = b.compareTo(a);

            System.out.println(result1);
            System.out.println(result2);
            System.out.println(result3);

        }



}
