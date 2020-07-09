package com.springboot.springboot.loadbalan;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 负载均衡方法随机的把负载分配到各个可用的服务器上，通过随机数生成算法选取一个服务器
 */
public class TestRandom {
//    定义map, key-ip,value-weight

    static Map<String,Integer> ipMap = new HashMap<>();
    static {
        ipMap.put("192.168.1.1",1);
        ipMap.put("192.168.1.2",2);
        ipMap.put("192.168.1.3",4);
    }

    public String Random(){
        Map<String,Integer> ipServerMap=new ConcurrentHashMap<>();
        ipServerMap.putAll(ipMap);
        Set<String> ipSet = ipServerMap.keySet();
        //定义一个list放所有server
        ArrayList<String> ipArrayList=new ArrayList<String>();
        ipArrayList.addAll(ipSet);
        //循环随机数
        Random random=new Random();
        //随机数在list数量中取（1-list.size）
        int pos=random.nextInt(ipArrayList.size());
        String serverNameReturn= ipArrayList.get(pos);
        return  serverNameReturn;
    }

    public static void main(String[] args) {
        TestRandom testRandom=new TestRandom();
        for (int i =0;i<10;i++){
            String server=testRandom.Random();
            System.out.println(server);
        }
    }
}
