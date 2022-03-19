package com.cloud.mqweb.ui.dto;

import lombok.Data;

@Data
public class AgentForm {

    private String kafka;
    private String zookeeper;
    private String kafdrop;
    private String start;
    private String stop;
    private String restart;

    public AgentForm(String kafka, String zookeeper, String kafdrop, String start, String stop, String restart) {
        this.kafka = kafka;
        this.zookeeper = zookeeper;
        this.kafdrop = kafdrop;
        this.start = start;
        this.stop = stop;
        this.restart = restart;
    }
}
