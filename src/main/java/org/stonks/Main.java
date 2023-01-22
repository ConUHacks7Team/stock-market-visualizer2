package org.stonks;

import org.stonks.simulator.MarketSimulator;
import org.stonks.simulator.TransactionEventEmitter;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Starting!");
        Responder responder = new Responder();
        MarketSimulator marketSimulator = new MarketSimulator();
//        transactionEventSimulator.printBook(5);
        TransactionEventEmitter.addListener(responder);
//        transactionEventSimulator.tradeOccured();

        marketSimulator.start();

    }
}