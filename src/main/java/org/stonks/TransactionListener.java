package org.stonks;

import org.json.JSONObject;

public interface TransactionListener {
    void onTransactionTradeEvent(JSONObject transactionJson);

    void onTransactionGenericEvent(JSONObject transactionJson);

}
