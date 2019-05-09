/**
 * @(#)ServiceStausXml.java 2017/8/14
 * <p>
 * Copyright 2000-2017 by ChinanetCenter Corporation.
 * <p>
 * All rights reserved.
 * <p>
 * This software is the confidential and proprietary information of
 * ChinanetCenter Corporation ("Confidential Information"). You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with ChinanetCenter.
 */
package com.housekeeper.monitor.server;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author yezy
 * @since 2017/8/14
 */
@XmlRootElement(name = "status")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class ServiceStatusView {

    @XmlElement(name = "server")
    private String server;

    @XmlElementWrapper(name = "services")
    @XmlElement(name = "service")
    private final List<Service> services = new ArrayList<>();

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public List<Service> getServices() {
        return services;
    }

    @XmlRootElement(name = "service")
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Service {

        @XmlAttribute(name = "name")
        private String name;

        @XmlElement(name = "status")
        private String status;

        @XmlElement(name = "elapsedTime")
        private long elapsedTime;

        @XmlElement(name = "errorMessage")
        private String errorMessage;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public long getElapsedTime() {
            return elapsedTime;
        }

        public void setElapsedTime(long elapsedTime) {
            this.elapsedTime = elapsedTime;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public void setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
        }
    }

}
