package com.housekeeper.monitor.server;

public interface ServiceMonitor {

    ServiceStatus getServiceStatus();

    String getServiceName();
}
