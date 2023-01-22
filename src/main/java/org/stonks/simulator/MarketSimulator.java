package org.stonks.simulator;

import org.json.JSONObject;
import org.stonks.TransactionListener;

import java.time.*;
import java.util.ArrayList;
import java.util.List;

public class MarketSimulator {

    public SimulatedClock simulatedClock = new SimulatedClock();

    private TransactionReader transactionReader = new TransactionReader();

    public MarketSimulator(){}


    // FOR DEBUG
    public void printBook(int number_of_transactions) {
        System.out.println("Printing!");

        for (int i = 0; i < number_of_transactions; i++) {
            System.out.println(transactionReader.transactionBook.get(i).toString());
        }
    }

    public void start() throws InterruptedException {
        // TODO: Create internal simulated clock
        Clock simulator_clock = simulatedClock.generateClock();


        for (int i = 0; i <  transactionReader.transactionBook.length(); i++) {

            JSONObject jsonObject = transactionReader.transactionBook.getJSONObject(i);
            String timeStamp = jsonObject.getString("TimeStamp");
            LocalDateTime transactionTime = SimulatedClock.timeStampToLocalDateTime(timeStamp);

            while (!transactionTime.isBefore(SimulatedClock.clockToLocalDateTime(simulator_clock))) {
//                Thread.sleep(100);
            }
            //System.out.println("TRANSACTION IN : " + jsonObject.getString("Symbol") + " at " + timeStamp);
            TransactionEventEmitter.onTransactionGeneric( jsonObject );

        }


    }





}
