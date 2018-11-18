package cn.zzuisa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "cn.zzuisa.mapper")
@ComponentScan(basePackages = { "cn.zzuisa", "org.n3r.idworker" })
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}