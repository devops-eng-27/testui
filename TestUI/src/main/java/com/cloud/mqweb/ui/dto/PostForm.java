package com.cloud.mqweb.ui.dto;

import lombok.Data;

@Data
public class PostForm {
    private String name;
    private String location;
    private String cpu;
    private String version;
    private String volume;
    private String broker;
    private String ip;

    public PostForm(String name, String location, String cpu, String version, String volume, String broker, String ip) {
        this.name = name;
        this.location = location;
        this.cpu = cpu;
        this.version = version;
        this.volume = volume;
        this.broker = broker;
        this.ip = ip;
    }
}
