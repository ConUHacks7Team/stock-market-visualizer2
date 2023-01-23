package org.stonks.messagingwebsocket;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.stonks.TransactionListener;
import org.stonks.simulator.TransactionEventEmitter;
import org.stonks.simulator.TransactionReader;

import java.io.IOException;
import java.util.ArrayList;

public class StockPriceSocketHandler extends TextWebSocketHandler implements TransactionListener {
    WebSocketSession webSocketSession;
    String subsciption_symbol = "ALL";

    @Override
    public void afterConnectionEstablished(WebSocketSession session){
        this.webSocketSession = session;
        TransactionEventEmitter.addListener(this);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws InterruptedException, IOException {
        String payload = message.getPayload();
        try {
            JSONObject jsonRequest = new JSONObject(payload);
            this.subsciption_symbol = jsonRequest.getString("subscription_symbol");
        } catch (JSONException e){
            System.err.println("Bad Request");
            session.sendMessage(new TextMessage("BAD REQUEST"));
        }

        // EXTRA POUR GET LHISORIQUE
        if ((subsciption_symbol.equals("SUU"))) {
            this.subsciption_symbol= "A";
            for (ArrayList<JSONObject> arrayList : new ArrayList<>(TransactionReader.repertoryStockTradeHistory.values())) {
                for (int i = 0; i < arrayList.size(); i++) {
                    session.sendMessage(new TextMessage(arrayList.get(i).toString()));
                }
            }
            this.subsciption_symbol= "";
        }

        //session.sendMessage(new TextMessage(jsonResponse.toString()));
    }

    @Override
    public void onTransactionTradeEvent(JSONObject transactionJson) {

        if (webSocketSession.isOpen()){
            try {
                if (subsciption_symbol.equals("ALL")){
                    this.webSocketSession.sendMessage(new TextMessage(transactionJson.toString()));
                } else if (transactionJson.getString("Symbol").equals(subsciption_symbol)) {
                    this.webSocketSession.sendMessage(new TextMessage(transactionJson.toString()));
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void onTransactionGenericEvent(JSONObject transactionJson) {

    }
}