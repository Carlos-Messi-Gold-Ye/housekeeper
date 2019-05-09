package com.housekeeper.monitor;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.housekeeper.monitor.server.ServerStatus;
import com.housekeeper.monitor.server.ServiceMonitor;
import com.housekeeper.monitor.web.MonitorStatusController;


/**
 * @author yezy
 * @since 2018/7/11
 */
@Configuration
@ConditionalOnClass(ServiceMonitor.class)
public class MonitorConfiguration {

    @Bean
    public ServerStatus serverStatus() {
        return new ServerStatus();
    }

    @Bean
    public MonitorStatusController monitorStatusController() {
        return new MonitorStatusController();
    }
}
