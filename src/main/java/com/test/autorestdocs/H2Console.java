package com.test.autorestdocs;

import org.h2.tools.Server;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * H2 console.
 *
 * @author Martina Ciefova
 * @since 1.0.0
 */
@Component
@Profile("h2-console")
public class H2Console {

    /**
     * Web server for H2 console.
     *
     * @since 1.0.0
     */
    private Server webServer;

    /**
     * Tcp server for H2 console.
     *
     * @since 1.0.0
     */
    private Server server;

    /**
     * Start servers for H2 console.
     *
     * @throws java.sql.SQLException starting server failure
     * @since 1.0.0
     */
    @EventListener(org.springframework.context.event.ContextRefreshedEvent.class)
    public void start() throws java.sql.SQLException {
        this.webServer
                = Server.createWebServer("-webPort", "7300", "-webAllowOthers")
                        .start();
        this.server
                = Server.createTcpServer("-tcpPort", "9092", "-tcpAllowOthers")
                        .start();
    }

    /**
     * Stop servers.
     *
     * @since 1.0.0
     */
    @EventListener(org.springframework.context.event.ContextClosedEvent.class)
    public void stop() {
        this.webServer.stop();
        this.server.stop();
    }

}
