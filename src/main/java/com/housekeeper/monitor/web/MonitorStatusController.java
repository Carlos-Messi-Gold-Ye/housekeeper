package com.housekeeper.monitor.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.housekeeper.monitor.server.MonitorStatus;
import com.housekeeper.monitor.server.ServerStatus;
import com.housekeeper.monitor.server.ServiceMonitor;
import com.housekeeper.monitor.server.ServiceStatusView;


/**
 * @author yezy
 * @since 2018/7/11
 */
@RestController
public class MonitorStatusController {

    @Autowired
    private ServerStatus serverStatus;
    @Autowired(required = false)
    private List<ServiceMonitor> serviceMonitors;

    @RequestMapping(value = "/monitor/status", produces = "application/xml", method = RequestMethod.GET)
    public ServiceStatusView monitorStatus() {
        MonitorStatus status = new MonitorStatus();
        status.check(serviceMonitors);
        return status.toXML(serverStatus);
    }

}
