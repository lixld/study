package com.lixl.study.idempotence.idempotence;

import org.assertj.core.util.VisibleForTesting;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

/**
 * @author : lixl
 * @date : 2020-08-03 09:54:18
 **/
public class RedisClusterIdempotenceStorage implements IdempotenceStorage {
    private JedisCluster jedisCluster;

    /**
     * Constructor * @param redisClusterAddress
     * the format is 128.91.12.1:3455;128.91.12.2:3452;289.13.2.12:8978
     * @param config should not be null
     */
//    public RedisIdempotenceStorage(String redisClusterAddress, GenericObjectPoolConfig config) {
//        Set redisNodes = parseHostAndPorts(redisClusterAddress);
//        this.jedisCluster = new JedisCluster(redisNodes, config);
//    }
//
//    public RedisIdempotenceStorage(JedisCluster jedisCluster) {
//        this.jedisCluster = jedisCluster;
//    }

    /**
     * Save {@idempotenceId} into storage if it does not exist.
     * @param idempotenceId the idempotence ID
     * @return true if the {@idempotenceId} is saved, otherwise return false
     */
    @Override
    public boolean saveIfAbsent(String idempotenceId) {
        Long success = jedisCluster.setnx(idempotenceId, "1");
        return success == 1;
    }

    @Override
    public void delete(String idempotenceId) {
        jedisCluster.del(idempotenceId);
    }

    @VisibleForTesting
    protected Set parseHostAndPorts(String redisClusterAddress) {
        String[] addressArray = redisClusterAddress.split(";");
        Set redisNodes = new HashSet<>();
        for (String address : addressArray) {
            String[] hostAndPort = address.split(":");
//            redisNodes.add(new HostAndPort(hostAndPort[0], Integer.valueOf(hostAndPort[1])));
        }
        return redisNodes;
    }
}
