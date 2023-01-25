package org.stonks.messagingwebsocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new StockPriceSocketHandler(), "/ws/stock").setAllowedOrigins("*");
        registry.addHandler(new StockPriceSocketHandler(), "/ws/stock").setAllowedOrigins("*").withSockJS();;
        //registry.addHandler(new ExchangeSocketHandler(), "/ws/exchange");
    }


}