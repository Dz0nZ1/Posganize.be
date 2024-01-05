package com.example.posganize;

import com.example.posganize.config.KeepServerActive;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class PosganizeApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(PosganizeApplication.class, args);
		KeepServerActive keepServerActive = context.getBean(KeepServerActive.class);
		keepServerActive.startKeepingServerActive();
	}

}
