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
import java.util.HashMap;
import java.util.Map;

public class StockPriceSocketHandler extends TextWebSocketHandler implements TransactionListener {
    HashMap<WebSocketSession, String> webSocketSessionManager = new HashMap<>();
    private final String DEFAULT_SUBSCRIPTION_SYMBOL = "ALL";


    @Override
    public void afterConnectionEstablished(WebSocketSession session){
        webSocketSessionManager.put(session, DEFAULT_SUBSCRIPTION_SYMBOL);
        TransactionEventEmitter.addListener(this);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws InterruptedException, IOException {
        String payload = message.getPayload();
        try {
            JSONObject jsonRequest = new JSONObject(payload);
            webSocketSessionManager.put(session, jsonRequest.getString("subscription_symbol"));
            // send history of new stock
            if (!jsonRequest.getString("subscription_symbol").equals("ALL")){
                this.sendStockTradeHistory(session, jsonRequest.getString("subscription_symbol"));
            }

//            // EXTRA POUR GET LHISORIQUE COMPLET
//            if ((subsciption_symbol.equals("SUU"))) {
//                for (ArrayList<JSONObject> arrayList : new ArrayList<>(TransactionReader.repertoryStockTradeHistory.values())) {
//                    for (int i = 0; i < arrayList.size(); i++) {
//                        session.sendMessage(new TextMessage(arrayList.get(i).toString()));
//                    }
//                }
//                this.subsciption_symbol= "";
//            }

        } catch (JSONException e){
            System.err.println("Bad Request");
            session.sendMessage(new TextMessage("BAD REQUEST"));
        }

    }

    private void sendStockTradeHistory(WebSocketSession session, String subsciption_symbol) throws IOException {
        try {
            ArrayList<JSONObject> ptrStock = TransactionReader.repertoryStockTradeHistory.get(subsciption_symbol);
            ArrayList<JSONObject> stockHistory = new ArrayList<>(ptrStock);
            for (JSONObject transaction: stockHistory) {
                if (transaction.getString("MessageType").equals("Trade")){
                    session.sendMessage(new TextMessage(transaction.toString()));
                }
            }
        } catch (NullPointerException e){
            System.err.println("Stock doesnt exist yet");
        }


    }



    @Override
    public void onTransactionTradeEvent(JSONObject transactionJson) {

        for (Map.Entry<WebSocketSession, String> client: webSocketSessionManager.entrySet()) {
            if (client.getKey().isOpen()){
                try {
                    if (client.getValue().equals("ALL")){
                        client.getKey().sendMessage(new TextMessage(transactionJson.toString()));
                    } else if (transactionJson.getString("Symbol").equals(client.getValue())) {
                        client.getKey().sendMessage(new TextMessage(transactionJson.toString()));
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }


    }

    @Override
    public void onTransactionGenericEvent(JSONObject transactionJson) {

    }
}