package com.sqy.guacamole.server.service;

import org.apache.guacamole.GuacamoleException;
import org.apache.guacamole.net.GuacamoleSocket;
import org.apache.guacamole.net.GuacamoleTunnel;
import org.apache.guacamole.net.InetGuacamoleSocket;
import org.apache.guacamole.net.SimpleGuacamoleTunnel;
import org.apache.guacamole.protocol.ConfiguredGuacamoleSocket;
import org.apache.guacamole.protocol.GuacamoleConfiguration;
import org.apache.guacamole.servlet.GuacamoleHTTPTunnelServlet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

@WebServlet(urlPatterns = "/tunnel")
public class HttpTunnelServlet extends GuacamoleHTTPTunnelServlet {

    /**
     *
     */
    private static final long serialVersionUID = -3224942386695394818L;

    @Value("${guacd.host}") //guacamole server地址
    private String host;
    @Value("${guacd.port}")
    private String port; //guacamole server端口

    @Override
    protected GuacamoleTunnel doConnect(HttpServletRequest request) throws GuacamoleException {
        //System.out.println("request:"+request.getParameterMap());
        //System.out.println("request2:"+request.getParameterNames());
        GuacamoleConfiguration configuration = new GuacamoleConfiguration();
        // ssh暂不考虑
        configuration.setProtocol("rdp"); // 远程连接协议

//        System.out.println(request.getContextPath());
//        System.out.println(request.getServletPath());
//        System.out.println(request.getPathInfo());
        String serverType = request.getParameter("type");
        // serverHost及其他信息之后从数据库或其他地方获取（如果server和用户绑定可能需要加上用户信息）
        String serverHost = null;
        if( serverType.equalsIgnoreCase("uft") )
            serverHost = "192.168.1.16";
        else if( serverType.equalsIgnoreCase("lr") )
            serverHost = "192.168.1.14";
        else if( serverType.equalsIgnoreCase("fortify") )
            serverHost = "192.168.1.15";
        configuration.setParameter("hostname", serverHost);
        configuration.setParameter("port", "3389");
        // 后面开放修改用户名密码服务后此处代码需修改
        configuration.setParameter("username", "Administrator");
        configuration.setParameter("password", "Quatum2018");
        // 后面开放修改用户名密码服务后此处代码需修改
        configuration.setParameter("ignore-cert", "true");

        GuacamoleSocket socket = new ConfiguredGuacamoleSocket(
                new InetGuacamoleSocket(host, Integer.valueOf(port)),
                configuration
        );
        GuacamoleTunnel tunnel = new SimpleGuacamoleTunnel(socket);
        return tunnel;
    }
}
