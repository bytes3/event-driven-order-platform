package com.bytes.event_driven_order_platform;

import org.springframework.boot.SpringApplication;

public class TestEventDrivenOrderPlatformApplication {

	public static void main(String[] args) {
		SpringApplication.from(EventDrivenOrderPlatformApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
