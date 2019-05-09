package com.housekeeper.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import com.housekeeper.monitor.server.ServiceMonitor;
import com.housekeeper.monitor.server.ServiceStatus;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisConnectionException;


/**
 * @author yezy
 * @since 2018/8/16
 */
public class JedisMonitorConnectionFactory extends JedisConnectionFactory implements ServiceMonitor {

    private static Logger logger = LoggerFactory.getLogger(JedisMonitorConnectionFactory.class);

    private static final String SUCCESS_STATUS = "PONG";

    public JedisMonitorConnectionFactory(JedisPoolConfig poolConfig) {
        super(poolConfig);
    }

    @Override
    public ServiceStatus getServiceStatus() {
        return isConnected() ? ServiceStatus.UP : ServiceStatus.DOWN;
    }

    private boolean isConnected() throws JedisConnectionException {
        try (Jedis jedis = this.fetchJedisConnector()) {
            if (SUCCESS_STATUS.equalsIgnoreCase(jedis.ping())) {
                return true;
            }
        } catch (JedisConnectionException e) {
            logger.error(e.getMessage(), e);
            return false;
        }
        return false;
    }

    @Override
    public String getServiceName() {
        return "redis";
    }
}
