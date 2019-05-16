package com.lx;

import com.alibaba.fastjson.JSON;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public  class TestFastJson {
    private  List<Person> listOfPersons = new ArrayList<Person>();
    @Before
    public void setUp() {
        listOfPersons.add(new Person(15, "John Doe", new Date()));
        listOfPersons.add(new Person(20, "Janette Doe", new Date()));
    }
   @Test
    public void whenJavaList_thanConvertToJsonCorrect() {
        String jsonOutput= JSON.toJSONString(listOfPersons);
        System.out.println(listOfPersons);
        //为何没有输出东西？
        System.out.println(jsonOutput.charAt(0));
    }

//    public static void main(String[] args){
//        TestFastJson.whenJavaList_thanConvertToJsonCorrect();
//    }
}
