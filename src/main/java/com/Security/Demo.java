package com.Security;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Demo {
@GetMapping("/")
	public String getdemo()
	{
		return "welcome";
	}
@GetMapping("/user")
public String getuser()
{
	return "welcome user";
}
@GetMapping("/Admin")
public String getAdmin()
{
	return "welcome Admin";
}
}
