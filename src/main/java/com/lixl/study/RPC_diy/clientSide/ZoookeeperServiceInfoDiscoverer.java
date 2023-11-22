package com.lixl.study.RPC_diy.clientSide;

import com.alibaba.fastjson.JSON;
import com.lixl.study.zookeeper.MyZkSerializer;
import org.I0Itec.zkclient.ZkClient;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : lixl
 * @date : 2021-01-13 21:38:17
 **/
public class ZoookeeperServiceInfoDiscoverer implements ServiceInfoDiscover {

    ZkClient zkClient;
    private String centerRootPath = "/Rpc-framework";

    public ZoookeeperServiceInfoDiscoverer() {
        String address = "localhost:2181";
        this.zkClient = new ZkClient(address);
        zkClient.setZkSerializer(new MyZkSerializer());
    }

    @Override
    public List<ServiceInfo> getServerInfos(String name) {
        String servicePath = centerRootPath+ "/"+name+"/service";
        List<String> children = zkClient.getChildren(servicePath);
        List<ServiceInfo> serverInfos = new ArrayList<>();
        for (String child : children) {
            String decode = null;
            try {
                decode = URLDecoder.decode(child, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            ServiceInfo serviceInfo = JSON.parseObject(decode, ServiceInfo.class);
            serverInfos.add(serviceInfo);
        }

        return serverInfos;
    }
}
