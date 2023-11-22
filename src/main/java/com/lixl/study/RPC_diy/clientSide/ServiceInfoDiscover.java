package com.lixl.study.RPC_diy.clientSide;



import java.util.List;

/**
 * @author : lixl
 * @date : 2021-01-13 20:58:14
 **/
public interface ServiceInfoDiscover {
    List<ServiceInfo> getServerInfos(String name);
}
