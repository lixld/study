package com.lixl.study.RPC_diy.serverSide;

import com.alibaba.fastjson.JSON;
import com.lixl.study.RPC_diy.clientSide.ServiceInfo;
import com.lixl.study.zookeeper.MyZkSerializer;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.Map;

/**
 * @author : lixl
 * @date : 2021-01-14 10:17:16
 **/
public class ZkExportServiceRegister extends DefaultServiceRegister implements ServiceRegister {

    ZkClient zkClient;
    private String centerRootPath = "/Rpc-framework";
    private Map<String,ServiceObject> serviceObjectMap;
    public ZkExportServiceRegister() {
        String address = "localhost:2181";
        this.zkClient = new ZkClient(address);
        zkClient.setZkSerializer(new MyZkSerializer());
        this.serviceObjectMap = super.getServiceObjectMap();
    }

    /**
     * 就是生成ServiceInfo对象
     * @param serviceObject
     * @param protocol
     * @param port
     */
    @Override
    public void register(ServiceObject serviceObject, String protocol, int port) {
        /**
         * 把服务注册到Map中，方便使用策略模式,其实只用到参数serviceObjects
         *
         */
        super.register(serviceObject, protocol, port);


        ServiceInfo serviceInfo = new ServiceInfo();
        try {
            String host = InetAddress.getLocalHost().getHostAddress();
            String address = host + ":" + port;
            serviceInfo.setAddress(address);
            serviceInfo.setName(serviceObject.getInterf().getName());
            serviceInfo.setProtocol(protocol);
            this.exportSevice(serviceInfo);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发布就是把服务对象注册到zk
     * @param serviceInfo 把整个服务对象--注册到zk的节点上去
     *
     *
     */
    private void exportSevice(ServiceInfo serviceInfo) {
        String serviceName = serviceInfo.getName();
        String uri = JSON.toJSONString(serviceInfo);//把整个服务对象--注册到zk的节点上去
        try {
            //为了避免服务中有中文出现乱码，这里其使用指定的编码格式将字符串转换为application/x-www-form-ulrencoded格式
            uri = URLEncoder.encode(uri, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //创建该服务的持久节点
        String servicePath = centerRootPath + "/" + serviceName + "service";
        if (!zkClient.exists(servicePath)) {
            zkClient.createPersistent(servicePath, true);
        }

        //在上面创建的该服务的持久节点下--创建该服务的零时节点，value:整个服务对象本身（json串形式）
        String uriPath = servicePath + "/" + uri;
        if (zkClient.exists(uriPath)) {//如果存在，则先删除
            zkClient.delete(uriPath);
        }
        zkClient.createEphemeral(uriPath);

        /**
         * 同时这里zkClient要添加订阅事件subscribe。
         * 来监控服务的状况
         * 如果服务提供者发生了问题（如：不能提供服务了，从注册中心剔除，则同步把策略中的对象也删除，让调用方及时发现）
         */
        zkClient.subscribeDataChanges(uriPath, new IZkDataListener() {
            @Override
            public void handleDataChange(String s, Object o) throws Exception {
                serviceObjectMap.put(s,(ServiceObject)o);//注册中心数据变动后，重新放置到过程中心
            }

            @Override
            public void handleDataDeleted(String s) throws Exception {
                serviceObjectMap.remove(s);//把注册中心中的值删掉
            }
        });
    }




}
