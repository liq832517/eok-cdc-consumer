package com.koe.cdc.task;

import com.koe.cdc.config.KafkaProducter;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

@Component
@Configuration
@EnableScheduling
public class Task {

    //@Autowired
    private KafkaProducter producter;

    //@Scheduled(fixedRate = 5000)
    public void testTask(){
        String str=String.valueOf(System.currentTimeMillis());
        producter.sendDataToKafka("test",str);

    }

}
