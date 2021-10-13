package com.sqy.guacamole.server.service;

import com.sqy.guacamole.server.config.GuacamoleServerConfig;
import org.apache.guacamole.GuacamoleException;
import org.apache.guacamole.net.GuacamoleSocket;
import org.apache.guacamole.net.GuacamoleTunnel;
import org.apache.guacamole.net.InetGuacamoleSocket;
import org.apache.guacamole.net.SimpleGuacamoleTunnel;
import org.apache.guacamole.protocol.ConfiguredGuacamoleSocket;
import org.apache.guacamole.protocol.GuacamoleConfiguration;
import org.apache.guacamole.websocket.GuacamoleWebSocketTunnelEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.EndpointConfig;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/webSocket/{type}", subprotocols = "guacamole")
@Component
public class WebSocketTunnel extends GuacamoleWebSocketTunnelEndpoint {

    /**
     * Returns a new tunnel for the given session. How this tunnel is created
     * or retrieved is implementation-dependent.
     *
     * @param session        The session associated with the active WebSocket
     *                       connection.
     * @param endpointConfig information associated with the instance of
     *                       the endpoint created for handling this single connection.
     * @return A connected tunnel, or null if no such tunnel exists.
     * @throws GuacamoleException If an error occurs while retrieving the
     *                            tunnel, or if access to the tunnel is denied.
     */
    // 解决无法注入的问题
    private static GuacamoleServerConfig guacamoleServer;
    @Autowired
    public void setGuacamoleServer(GuacamoleServerConfig gs){
        guacamoleServer = gs;
    }

    @Override
    public GuacamoleTunnel createTunnel(Session session, EndpointConfig endpointConfig) throws GuacamoleException {
        GuacamoleConfiguration configuration = new GuacamoleConfiguration();
        configuration.setProtocol("rdp");

        // servertype 目前只有uft,lr,fortify三种类型
        String serverType = session.getPathParameters().get("type");
        // serverHost及其他信息之后从数据库或其他地方获取（如果server和用户绑定可能需要加上用户信息）
        String serverHost = null;
        if( serverType.equalsIgnoreCase("uft") )
            serverHost = "192.168.1.16";
        else if( serverType.equalsIgnoreCase("lr") )
            serverHost = "192.168.1.14";
        else if( serverType.equalsIgnoreCase("fortify") )
            serverHost = "192.168.1.15";
        // 远程windows服务的地址
        configuration.setParameter("hostname", serverHost);
        configuration.setParameter("port", "3389");
        // 后面开放修改用户名密码服务后此处代码需修改
        configuration.setParameter("username", "Administrator");
        configuration.setParameter("password", "Quantum2018");
        // 后面开放修改用户名密码服务后此处代码需修改
        configuration.setParameter("ignore-cert", "true");
//        configuration.setParameter("height","865");
//        configuration.setParameter("width","1707");


        GuacamoleSocket socket = new ConfiguredGuacamoleSocket(
                new InetGuacamoleSocket(guacamoleServer.getHost(), Integer.valueOf(guacamoleServer.getPort())),
                configuration
        );

        GuacamoleTunnel tunnel = new SimpleGuacamoleTunnel(socket);
        return tunnel;
    }
}
