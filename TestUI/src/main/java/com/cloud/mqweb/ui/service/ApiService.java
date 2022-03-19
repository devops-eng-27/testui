package com.cloud.mqweb.ui.service;

public interface ApiService {

    public String createApi(String userId, String userPw, String name, String location);
    public String deleteApi(String userId, String userPw, String vmGuestIp);
    public String agentApi(String URI, String kafka, String zookeeper, String kafdrop, String control);

}
