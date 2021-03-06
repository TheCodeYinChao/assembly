package cn.bainuo.service.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SpringBootApplication
@EnableEurekaServer
@RestController
public class ServiceManagerApplication {
	public static void main(String[] args) {
		SpringApplication.run(ServiceManagerApplication.class, args);
	}

	@GetMapping("/test")
	public String getTest(HttpServletRequest request, HttpServletResponse response){

		String method = request.getMethod();
		System.out.println(method);

		throw new RuntimeException("测试全局异常是否生效");
//		return "测试异常类";
	}
}
