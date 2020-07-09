package com.springboot.springboot.loadbalan;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class TestWeightRandom {
    //    1.定义map, key-ip,value-weight
    static Map<String, Integer> ipMap = new HashMap<>();

    static {
        ipMap.put("192.168.13.1", 1);
        ipMap.put("192.168.13.2", 1);
        ipMap.put("192.168.13.3", 1);
    }

    public String weightRandom() {
        Map<String, Integer> ipServerMap = new ConcurrentHashMap<>();
        ipServerMap.putAll(ipMap);

        Set<String> ipSet = ipServerMap.keySet();
        Iterator<String> ipIterator = ipSet.iterator();

        //定义一个list放所有server
        ArrayList<String> ipArrayList = new ArrayList<String>();

        //循环set，根据set中的可以去得知map中的value，给list中添加对应数字的server数量
        while (ipIterator.hasNext()) {
            String serverName = ipIterator.next();
            Integer weight = ipServerMap.get(serverName);
            for (int i = 0; i < weight; i++) {
                ipArrayList.add(serverName);
            }
        }

        //循环随机数
        Random random = new Random();
        //随机数在list数量中取（1-list.size）
        int pos = random.nextInt(ipArrayList.size());
        String serverNameReturn = ipArrayList.get(pos);
        return serverNameReturn;
    }

    public static void main(String[] args) {
        TestWeightRandom testWeightRandom = new TestWeightRandom();
        for (int i = 0; i < 10; i++) {
            String server = testWeightRandom.weightRandom();
            System.out.println(server);
        }


    }

}
