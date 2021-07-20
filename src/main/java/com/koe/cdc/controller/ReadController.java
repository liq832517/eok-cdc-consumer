package com.koe.cdc.controller;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/reader")
public class ReadController {

	@GetMapping("/test")
	public void reader() {
		InputStream in=this.getClass().getClassLoader().getResourceAsStream("kafka.properties");
		byte[] bytes=new byte[2048];
		try {
			while(in.read(bytes)!=-1) {
				System.out.println("------------------------------"+new String(bytes));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
