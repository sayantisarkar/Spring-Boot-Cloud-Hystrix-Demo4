package com.accenture.lkm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.client.RestTemplate;

import com.accenture.lkm.service.CustomHystrixStateNotifier;
import com.accenture.lkm.service.MyService;
import com.netflix.hystrix.strategy.HystrixPlugins;

@SpringBootApplication
@EnableCircuitBreaker
public class Application {

    public static void main(String[] args) throws Exception {
       HystrixPlugins.getInstance().registerEventNotifier(new CustomHystrixStateNotifier());
       ConfigurableApplicationContext actx= SpringApplication.run(Application.class, args);
       MyService myService = actx.getBean(MyService.class);
       
       testMethod1(myService);
       
      
       actx.close();
    }
    
    public static void testMethod1(MyService myService){
    	 System.out.println("\n\nCall1");
         myService.compute2(2);
         getCircuitHealthStatus();
         
         System.out.println("\n\nCall2");
         myService.compute2(2);
         getCircuitHealthStatus();
         
         System.out.println("\n\nCall3");
         myService.compute2(2);
         getCircuitHealthStatus();
    }
    
    static public void getCircuitHealthStatus(){
    	RestTemplate restTemplate= new RestTemplate();
    	System.out.println(restTemplate.getForObject("http://localhost:7093/health", Object.class));
    }
}
//https://www.logicbig.com/tutorials/spring-framework/spring-cloud/spring-circuit-breaker-hystrix-basics.html