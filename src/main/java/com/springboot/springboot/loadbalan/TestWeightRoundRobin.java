package com.springboot.springboot.loadbalan;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class TestWeightRoundRobin {
    //1.map, key-ip,value-weight
    static Map<String,Integer> ipMap=new HashMap<>();
    static {
        ipMap.put("192.168.13.1",1);
        ipMap.put("192.168.13.2",1);
        ipMap.put("192.168.13.3",1);


    }
    Integer pos=0;
    public String weightRoundRobin(){
        Map<String,Integer> ipServerMap=new ConcurrentHashMap<>();
        ipServerMap.putAll(ipMap);

        Set<String> ipSet=ipServerMap.keySet();
        Iterator<String> ipIterator=ipSet.iterator();

        //定义一个list放所有server
        ArrayList<String> ipArrayList=new ArrayList<String>();

        //循环set，根据set中的可以去得知map中的value，给list中添加对应数字的server数量
        while (ipIterator.hasNext()){
            String serverName=ipIterator.next();
            Integer weight=ipServerMap.get(serverName);
            for (int i = 0;i < weight ;i++){
                ipArrayList.add(serverName);
            }
        }
        String serverName=null;
        if (pos>=ipArrayList.size()){
            pos=0;
        }
        serverName=ipArrayList.get(pos);
        //轮询+1
        pos ++;


        return  serverName;
    }

    public static void main(String[] args) {
        TestWeightRoundRobin testWeightRoundRobin=new TestWeightRoundRobin();
        for (int i =0;i<10;i++){
            String server=testWeightRoundRobin.weightRoundRobin();
            System.out.println(server);
        }


    }
}
