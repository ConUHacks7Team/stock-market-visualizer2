package org.stonks;

import java.util.ArrayList;
import java.util.List;

public class TransactionEventSimulator {
    private List<TransactionListener> listeners = new ArrayList<TransactionListener>();

    public void addListener(TransactionListener toAdd) {
        listeners.add(toAdd);
    }

    public void sayHello() {
        System.out.println("Hello!");

        // Notify everybody that may be interested.
        for (TransactionListener hl : listeners)
            hl.onTradeEvent();
    }

}
