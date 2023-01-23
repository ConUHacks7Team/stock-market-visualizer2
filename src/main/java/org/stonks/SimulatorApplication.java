package org.stonks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.stonks.simulator.MarketSimulator;
import org.stonks.simulator.TransactionEventEmitter;

@SpringBootApplication
public class SimulatorApplication {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Starting!");
        Responder responder = new Responder();

//        transactionEventSimulator.printBook(5);
        TransactionEventEmitter.addListener(responder);
//        transactionEventSimulator.tradeOccured();



        new MarketSimulator();


        SpringApplication.run(SimulatorApplication.class, args);

    }
}