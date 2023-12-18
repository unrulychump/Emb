package com.example.emb.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * WebSocket配置
 *
 * @author yueli.liao
 * @date 2019-03-12 11:25
 */
@Configuration
public class WebSocketConfig {

    /**
     * 开启WebSocket功能
     *
     * @return
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

}
