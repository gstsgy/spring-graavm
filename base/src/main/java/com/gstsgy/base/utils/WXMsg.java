package com.gstsgy.base.utils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class WXMsg {

    public static void SendMsg(String msg,RestTemplate restTemplate){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String,Object> body = new HashMap<>();
        body.put("msgtype","text");

        Map<String,Object> text = new HashMap<>();
        text.put("content",msg);
        text.put("mentioned_list",new String[]{"@all"});
        body.put("text",text);
        System.out.println("发送消息"+JSON.toJson(body));
        HttpEntity<Object> request = new HttpEntity<>(body, headers);

        restTemplate.exchange(
                "https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=881c4331-220a-4b33-b7b9-30b0b6083497",
                HttpMethod.POST,
                request,
                String.class
        );
    }
}
