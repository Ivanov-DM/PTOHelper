package ru.ivanov.pto_helper;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        AOSRContent aosrContent = new AOSRContent();
        LinkedHashMap<String, ArrayList<String>> map = aosrContent.getAosrContent();
        for(Map.Entry<String, ArrayList<String>> entry : map.entrySet()) {
            System.out.println("KEY - " + entry.getKey() + ", VALUE SIZE = " + entry.getValue().size());


        }
    }
}
