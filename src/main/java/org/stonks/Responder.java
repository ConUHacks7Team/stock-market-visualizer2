package org.stonks;

import org.json.JSONObject;

class Responder implements TransactionListener {

    @Override
    public void onTransactionTradeEvent(JSONObject transactionJson) {
        System.out.println("Responder Event called TradeEvent");
        System.out.println(transactionJson.toString(2));
    }

    @Override
    public void onTransactionGenericEvent(JSONObject transactionJson) {
       // System.out.println("Responder Event called GenericEvent");
    }
}