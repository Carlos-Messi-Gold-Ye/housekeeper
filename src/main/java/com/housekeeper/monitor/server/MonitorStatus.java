package com.housekeeper.monitor.server;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.housekeeper.core.util.StopWatch;


public class MonitorStatus {
    private static Logger logger = LoggerFactory.getLogger(MonitorStatus.class);

    private Map<String, ServiceDetail> serviceDetails = new HashMap<>();

    public void check(Collection<ServiceMonitor> monitors) {
        for (ServiceMonitor monitor : monitors) {
            check(monitor);
        }
    }

    private void check(ServiceMonitor monitor) {
        StopWatch watch = new StopWatch();
        ServiceDetail detail = new ServiceDetail();
        try {
            ServiceStatus status = monitor.getServiceStatus();
            detail.setStatus(status);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            detail.setErrorMessage(e.getClass().getName() + " " + e.getMessage());
            detail.setStatus(ServiceStatus.DOWN);
        } finally {
            detail.setElapsedTime(watch.elapsedTime());
        }

        serviceDetails.put(monitor.getServiceName(), detail);
    }

    public ServiceStatusView toXML(ServerStatus serverStatus) {
        ServiceStatusView statusXml = new ServiceStatusView();
        statusXml.setServer(serverStatus.status().name());
        for (Map.Entry<String, ServiceDetail> entry : serviceDetails.entrySet()) {
            ServiceStatusView.Service service = new ServiceStatusView.Service();
            service.setName(entry.getKey());
            ServiceDetail detail = entry.getValue();
            service.setStatus(detail.getStatus().name());
            service.setElapsedTime(detail.getElapsedTime());
            service.setErrorMessage(detail.getErrorMessage());
            statusXml.getServices().add(service);
        }
        return statusXml;
    }
}
