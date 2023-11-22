package com.lixl.study.zookeeper.master选举;

import com.lixl.study.zookeeper.MyZkSerializer;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;

import java.util.concurrent.CountDownLatch;

public class MasterElectionDemo {

    static class Server {
        private String cluster, name, address;
        private String path, value;
        private String master;

        public Server(String cluster, String name, String address) {
            this.cluster = cluster;
            this.name = name;
            this.address = address;
            path = "/" + this.cluster + "Master";
            value = "interfName:" + name + " address:" + address;

            ZkClient zkClient = new ZkClient("localhost:2181");
            zkClient.setZkSerializer(new MyZkSerializer());

            /**
             * 应该做为一个守护线程一直在跑，
             *
             */
            //必须要交个另外一个新的线程去做，如果不用多线程，则会阻塞在这里
            new Thread(new Runnable() {
                @Override
                public void run() {
                    electionMaster(zkClient);
                }
            }).start();
        }

        private void electionMaster(ZkClient zkClient) {
            try {
                zkClient.createEphemeral(path,value);
                master = zkClient.readData(path);
                System.out.println(value+"创建节点成功，成为master");
            } catch (ZkNodeExistsException e) {
                e.printStackTrace();
                master = zkClient.readData(path);
            }


            //为阻塞自己等待而用
            CountDownLatch countDownLatch = new CountDownLatch(1);

            //注册watcher
            IZkDataListener listener = new IZkDataListener() {
                @Override
                public void handleDataChange(String s, Object o) throws Exception {

                }

                @Override
                public void handleDataDeleted(String s) throws Exception {
                    System.out.println("----临时节点被删除（也就是master挂了，此时需要重新发起master选举）----");
                    countDownLatch.countDown();
                }
            };

            zkClient.subscribeDataChanges(path,listener);

            //让自己阻塞
            if (zkClient.exists(path)) {
                try {
                    countDownLatch.await();//TODO ---只有调用了上面的额countDown()方法，这里的await()才会被触发，醒来
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            //醒来后，取消watcher
            zkClient.unsubscribeDataChanges(path,listener);

            //递归的调用自己（下一次选举）
            electionMaster(zkClient);

        }

        public static void main(String[] args) {

            /**
             * 先启动第1台---很自然的成为master  ----服务1启动
             *
             * 再启动第2台，去注册发现有了，直接取值，得到master为第1台----服务2启动
             *
             * 再启动第3台，去注册发现有了，直接取值，得到master为第1台----服务3启动
             *
             * 此时----把服务1停掉-----等一会（也就是两倍tickTime） 重新发起选择，此时第2台获得主动权，成为master
             *
             */
            Server server = new Server("cluster1", "server1", "192.168.1.11:8991");
//            Server server = new Server("cluster1", "server1", "192.168.1.11:8992");
//            Server server = new Server("cluster1", "server1", "192.168.1.11:8993");
//            Server server = new Server("cluster1", "server1", "192.168.1.11:8994");
        }
    }



}
