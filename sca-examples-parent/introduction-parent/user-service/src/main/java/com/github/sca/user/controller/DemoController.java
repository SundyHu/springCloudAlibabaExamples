package com.github.sca.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/users")
public class DemoController {

	@GetMapping(value = "/demo")
	public String demo() {

		return "demo";
	}
}