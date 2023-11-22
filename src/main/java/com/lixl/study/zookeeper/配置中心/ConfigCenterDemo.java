package com.lixl.study.zookeeper.配置中心;

import com.lixl.study.zookeeper.MyZkSerializer;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.BytesPushThroughSerializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ConfigCenterDemo {

    //将单个配置项放到zookeeper
    public void put2ZK() {
        ZkClient zkClient = new ZkClient("localhost:2181;");
        zkClient.setZkSerializer(new MyZkSerializer());
        String configPath = "/config1";
        String value = "111111";
        boolean exists = zkClient.exists(configPath);
        if (exists) {
            zkClient.writeData(configPath, value);
        } else {
            zkClient.createPersistent(configPath, value);
        }
        zkClient.close();
    }

    //将整个配置文件存放到zk节点上
    public void putConfigFile2ZK() throws IOException {
        File file = new File(this.getClass().getResource("/config.xml").getFile());
        byte[] datas = new byte[0];

        FileInputStream fileInputStream = new FileInputStream(file);
        datas = new byte[(int) file.length()];
        fileInputStream.read(datas);
        fileInputStream.close();


        ZkClient zkClient = new ZkClient("localhost:2181;");
        zkClient.setZkSerializer(new BytesPushThroughSerializer());
        String configPath = "/config2";

        boolean exists = zkClient.exists(configPath);
        if (exists) {
            zkClient.writeData(configPath, datas);
        } else {
            zkClient.createPersistent(configPath, datas);
        }
        zkClient.close();
    }


    //从zk节点上读取配置
    public void getConfigFromZK() throws IOException {


        ZkClient zkClient = new ZkClient("localhost:2181");
        zkClient.setZkSerializer(new MyZkSerializer());
        String configPath = "/config1";
        String value = zkClient.readData(configPath);
        System.out.println("从zookeeper取到的值是：" + value);
        zkClient.subscribeDataChanges(configPath, new IZkDataListener() {
            @Override
            public void handleDataChange(String s, Object o) throws Exception {
                System.out.println("获取到新数据：" + o);
            }

            @Override
            public void handleDataDeleted(String s) throws Exception {
                System.out.println();
            }
        });


        //这里为了演示 实时获取去配值更新而加的等待，实际项目中根据具体场景写（可以用阻塞方式）
        try {
            Thread.sleep(5 * 60 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws IOException {

        ConfigCenterDemo configCenterDemo = new ConfigCenterDemo();
        configCenterDemo.put2ZK();
        ;
        configCenterDemo.putConfigFile2ZK();
        configCenterDemo.getConfigFromZK();
    }
}
