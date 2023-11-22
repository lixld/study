package com.lixl.study.幂等框架;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.assertj.core.util.VisibleForTesting;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

public class RedisClusterIdempotenceStorage implements IdempotenceStorage {
    private JedisCluster jedisCluster;

    /**
     * Constructor * @param redisClusterAddress the format is 128.91.12.1:3455;128.91.12.2:3452;289.13.2.12:8978 * @param config should not be null
     */
    public RedisClusterIdempotenceStorage(String redisClusterAddress, GenericObjectPoolConfig config) {
        Set redisNodes = parseHostAndPorts(redisClusterAddress);
        this.jedisCluster = new JedisCluster(redisNodes, config);
    }

    public RedisClusterIdempotenceStorage(JedisCluster jedisCluster) {
        this.jedisCluster = jedisCluster;
    }

    /**
     * Save {@idempotenceId} into storage if it does not exist. * @param idempotenceId the idempotence ID * @return true if the {@idempotenceId} is saved, otherwise return false
     */
    public boolean saveIfAbsent(String idempotenceId) {
        Long success = jedisCluster.setnx(idempotenceId, "1");
        return success == 1;
    }

    public void delete(String idempotenceId) {
        jedisCluster.del(idempotenceId);
    }

    @VisibleForTesting
    protected Set parseHostAndPorts(String redisClusterAddress) {
        String[] addressArray = redisClusterAddress.split(";");
        Set redisNodes = new HashSet<>();
        for (String address : addressArray) {
            String[] hostAndPort = address.split(":");
            redisNodes.add(new HostAndPort(hostAndPort[0], Integer.valueOf(hostAndPort[1])));
        }
        return redisNodes;
    }
}