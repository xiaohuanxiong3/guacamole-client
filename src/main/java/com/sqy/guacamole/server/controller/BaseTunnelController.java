//package com.sqy.guacamole.server.controller;
//
//import com.sqy.guacamole.server.service.WebSocketTunnel;
//import org.apache.guacamole.GuacamoleException;
//import org.apache.guacamole.net.GuacamoleTunnel;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.websocket.EndpointConfig;
//import javax.websocket.Session;
//
//@Controller
//@RestController
//@RequestMapping("/server")
//public class BaseTunnelController {
//    @Autowired
//    private WebSocketTunnel webSocketTunnel;
//
//    @GetMapping("/BaseTunnel")
//    public GuacamoleTunnel getBaseTunnel(HttpServletRequest request) throws GuacamoleException {
//        GuacamoleTunnel tunnel = webSocketTunnel.createTunnel(request.getSession());
//        return tunnel;
//    }
//
//    @GetMapping("/test")
//    public Object test(){
//        return "test";
//    }
//}
