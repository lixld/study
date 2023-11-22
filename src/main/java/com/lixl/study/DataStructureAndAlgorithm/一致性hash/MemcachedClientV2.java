package com.lixl.study.DataStructureAndAlgorithm.一致性hash;

import java.util.*;

public class MemcachedClientV2 {
    private static List<String> serverList = new ArrayList<>();

    //所有的虚拟节点信息（map信息去保存---key虚拟节点hash值，value物理节点信息）
//    private static List<Integer> vServerMap = new ArrayList<>();
    private static SortedMap<Integer, String> vServerMap = new TreeMap<>();


    //构建虚拟节点和真实节点的对应关系 （真实节点--虚拟节点  对应）
    private static SortedMap<String, List<Integer>> serverMap = new TreeMap<>();


    static {
        String hostInfo = "192.168.100.101";
        String hostInfo2 = "192.168.100.102";
        String hostInfo3 = "192.168.100.103";
        serverList.add(hostInfo);
        serverList.add(hostInfo2);
        serverList.add(hostInfo3);


        addServer(hostInfo);
        addServer(hostInfo2);
        addServer(hostInfo3);

    }

    public static void addServer(String hostInfo) {
        //构建虚拟节点 和真实节点的映射关系
        List<Integer> vNodes = new ArrayList<>();
        for (int i = 0; i < 200; i++) {//TODO 这里就是为实例节点 分配的槽个数slot,该数值越大越平均
            //服务器信息摘要，唯一标识
            String key = hostInfo + "&" + new Random().nextInt(1000);
            int vHash = hash(key);
            vNodes.add(vHash);
//            vServerMap.add(vHash);
            vServerMap.put(vHash, hostInfo);
        }
        serverMap.put(hostInfo, vNodes);
    }

    //hash算法
    public static int hash(String content) {
        //--可以有不同的hash算法（为了解决数据倾斜的问题）

//        int hash = Math.abs(content.hashCode());//TODO 这样太有序了不够分散，
        int hash = FNV1_32_HASH.getHash(content);
//        System.out.println(content+" 对应的hash值 "+hash);
        return hash;
    }


    public void set(String key, String value) {
        String server = findServer(key);



        //TODO 忽略与memecached的连接，操作

        System.out.println("set---选择一个服务器进行操作：" + server);


    }

    private String findServer(String key) {
        String server;
        /**
         * 获取真实节点：方式二
         * int index = hasValue % serverList.size();
         * server = serverList.get(index);
         */

        /**
         * 获取真实节点：方式一
         */
        int hasValue = hash(key);
        //通过虚拟节点获取真实节点（换了思路）--获取到所有大于该节点的hashValue的节点
        SortedMap<Integer, String> subMap = vServerMap.tailMap(hasValue);
        if (subMap.isEmpty()) {
            server = vServerMap.get(vServerMap.firstKey());
        } else {
            Integer vhash = subMap.firstKey();
            server = subMap.get(vhash);//真实节点
        }
        return server;
    }

    public void get(String key) {
        String server = findServer(key);

        //TODO 忽略与memecached的连接，操作
        System.out.println("get---选择一个服务器进行操作：" + server);

    }

    public static void main(String[] args) {
        MemcachedClientV2 memcachedClientV2 = new MemcachedClientV2();
//        for (int i = 0; i < 10; i++) {
//            System.out.println("######"+hash("hello"+i));
//        }
        for (int i = 0; i < 10; i++) {
            memcachedClientV2.set("hello"+i,"lixl");
        }

        //服务器变多了
        addServer("192.168.100.104");

        System.out.println("更新之后");

        for (int i = 0; i < 10; i++) {
            memcachedClientV2.get("hello"+i);
        }

    }
}
