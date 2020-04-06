package com.rad.server.web.corona;

import java.net.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.boot.context.event.*;
import org.springframework.context.*;
import org.springframework.core.env.*;

@SpringBootApplication
public class CoronaWebServerApplication implements ApplicationListener<ApplicationReadyEvent>
{
    @Autowired
    private ApplicationContext applicationContext;

	public static void main(String[] args)
	{
		SpringApplication.run(CoronaWebServerApplication.class, args);
	}
	
	@Override
	public void onApplicationEvent(ApplicationReadyEvent event)
	{
		try
		{
			String ip       = InetAddress.getLocalHost().getHostAddress();
			String hostName = InetAddress.getLocalHost().getHostName();
			int port        = applicationContext.getBean(Environment.class).getProperty("server.port", Integer.class, 8080);
			
			System.out.println("*****************************************************");
			System.out.println("* CoronaVirus Web Server is Ready ");
			System.out.println("* Host=" + hostName + ", IP=" + ip + ", Port=" + port);
			System.out.println("*****************************************************");
		}
		catch (UnknownHostException e)
		{
			e.printStackTrace();
		}
	}
}