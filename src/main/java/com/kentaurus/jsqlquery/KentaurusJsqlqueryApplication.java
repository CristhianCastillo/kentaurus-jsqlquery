package com.kentaurus.jsqlquery;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import com.kentaurus.jsqlquery.view.InterfazApp;

@SpringBootApplication
public class KentaurusJsqlqueryApplication {

	public static void main(String[] args) {
//		SpringApplication.run(KentaurusJsqlqueryApplication.class, args);
		@SuppressWarnings("unused")
		ConfigurableApplicationContext context = new SpringApplicationBuilder(InterfazApp.class).headless(false)
				.run(args);
		InterfazApp application = new InterfazApp();
		application.setVisible(true);
	}

}
