package org.stonks.simulator;

import org.json.JSONObject;
import org.stonks.TransactionListener;

import java.util.ArrayList;
import java.util.List;

public class TransactionEventEmitter {
    static private List<TransactionListener> listeners = new ArrayList<TransactionListener>();

    static public void addListener(TransactionListener toAdd) {
        listeners.add(toAdd);
    }

    static public void onTransactionGeneric(JSONObject transactionJson){
        //System.out.println("onTransactionGeneric");

        String messageType = transactionJson.getString("MessageType");
        for (TransactionListener hl : listeners)
            hl.onTransactionGenericEvent(transactionJson);

        switch (messageType){
            case "Trade":
                onTransactionTrade(transactionJson);
                break;
            default:
                break;
        }
    }

    static public void onTransactionTrade(JSONObject transactionJson) {
        System.out.println("onTransactionTrade");

        // Notify everybody that may be interested.
        for (TransactionListener hl : listeners)
            hl.onTransactionTradeEvent(transactionJson);
    }


}
