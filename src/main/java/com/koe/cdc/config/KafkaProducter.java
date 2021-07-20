package com.koe.cdc.config;


import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducter {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducter.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendDataToKafka(String topic, Object obj) {
        try {
            Gson gson = new Gson();
            String json = gson.toJson(obj);
            kafkaTemplate.send(topic, json);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("发送数据到kafka出现异常");
        }
    }

}
