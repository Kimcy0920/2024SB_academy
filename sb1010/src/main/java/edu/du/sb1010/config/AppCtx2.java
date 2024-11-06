package edu.du.sb1010.config;

import edu.du.sb1010.spring2.Client;
import edu.du.sb1010.spring2.Client2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

// p.144 AppCtx2
@Configuration
public class AppCtx2 {

	@Bean
	@Scope("singleton") // client1 == client2 : true, default값 싱글톤
//	@Scope("prototype") // client1 == client2 : false
	// 객체를 별도로 사용하려면 @스코프("프로토타입") 사용
	public Client client() {
		Client client = new Client();
		client.setHost("host");
		return client;
	}

	@Bean(destroyMethod = "close")
	public Client2 client2() {
		Client2 client = new Client2();
		client.setHost("host");
		client.connect(); // Bean에 이름등록해서 사용하지 않고, 아래에도 가능하나 두 번 반복될 수도 있음.
		return client;
	}

}
