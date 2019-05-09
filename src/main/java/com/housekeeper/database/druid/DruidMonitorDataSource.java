package com.housekeeper.database.druid;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.pool.DruidDataSource;
import com.housekeeper.monitor.server.ServiceMonitor;
import com.housekeeper.monitor.server.ServiceStatus;

/**
 * @author yezy
 * @since 2018/7/11
 */
public class DruidMonitorDataSource extends DruidDataSource implements ServiceMonitor {
    private static Logger logger = LoggerFactory.getLogger(DruidMonitorDataSource.class);

    private static final String DEFAULT_SERVICE_NAME = "DB";

    private final String serviceName;

    public DruidMonitorDataSource(String serviceName) {
        this.serviceName = Optional.ofNullable(serviceName).orElse(DEFAULT_SERVICE_NAME);
    }

    @Override
    public ServiceStatus getServiceStatus() {
        try (Connection connection = this.getConnection(); //get connection
                PreparedStatement psd = connection.prepareStatement(this.getValidationQuery())) {
            psd.execute();
            return ServiceStatus.UP;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("load db status failed, msg:" + e.getMessage());
        }
    }

    @Override
    public String getServiceName() {
        return serviceName;
    }
}
