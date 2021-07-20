package com.koe.cdc.config;

import com.koe.cdc.websocket.WebSocketServer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumerListener {

    @KafkaListener(topics = {"pod"},groupId = "koe")
    public void receiveRemoteControlResult(String message){
//        String s = new String(record.value());
//        Gson gson = new Gson();
//        String str = gson.fromJson(message, String.class);
//        System.out.println(str);
        WebSocketServer.sendInfo(message);
    }

    public static void main(String[] args) {
    	DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("yyyy-MM-dd");
    	LocalDate date=LocalDate.now();
    	LocalDate firstdayOfYear =date.with(TemporalAdjusters.firstDayOfYear());
    	System.out.println("firstdayOfYear"+dateTimeFormatter.format(firstdayOfYear));
	}
}
