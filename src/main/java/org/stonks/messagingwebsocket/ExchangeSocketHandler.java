package org.stonks.messagingwebsocket;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.stonks.TransactionListener;
import org.stonks.simulator.TransactionEventEmitter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ExchangeSocketHandler extends TextWebSocketHandler implements TransactionListener {
    WebSocketSession webSocketSession;
    @Override
    public void afterConnectionEstablished(WebSocketSession session){
        this.webSocketSession = session;
        TransactionEventEmitter.addListener(this);
    }


    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws InterruptedException, IOException {
        String payload = message.getPayload();
        JSONObject jsonRequest = new JSONObject(payload);
        JSONObject jsonResponse = new JSONObject();

        jsonResponse.put("response", "exchange");
        jsonResponse.put("exchange", jsonRequest.get("exchange"));
        String[] stockArray = new String[10];

        for (int j = 0; j < stockArray.length; j++) {
            stockArray[j] = "SL" + Integer.toString(j);
        }

        jsonResponse.put("stocks", stockArray);

        session.sendMessage(new TextMessage(jsonResponse.toString()));
    }

    @Override
    public void onTransactionTradeEvent(JSONObject transactionJson) {

    }

    @Override
    public void onTransactionGenericEvent(JSONObject transactionJson) {

    }
}