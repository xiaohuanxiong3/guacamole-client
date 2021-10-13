package com.sqy.guacamole.server;

import com.sqy.guacamole.server.config.GuacamoleServerConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GuacamoleApplicationTests {

    @Autowired
    private GuacamoleServerConfig guacamoleServer;
    @Value("${guacd.host}")
    private String host;
    @Test
    void contextLoads() {
        System.out.println(guacamoleServer.getPort());
        System.out.println(host);
    }

}
