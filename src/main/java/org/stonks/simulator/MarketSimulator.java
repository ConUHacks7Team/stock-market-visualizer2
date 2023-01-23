package org.stonks.simulator;

import org.json.JSONObject;
import org.stonks.TransactionListener;

import java.time.*;
import java.util.ArrayList;
import java.util.List;

public class MarketSimulator extends Thread{

    public SimulatedClock simulatedClock = new SimulatedClock();

    public MarketSimulator(){
        new Thread(() -> {
            this.start();
        }).start();
    }


    // FOR DEBUG
    public void printBook(int number_of_transactions) {
        System.out.println("Printing!");

        for (int i = 0; i < number_of_transactions; i++) {
            System.out.println(TransactionReader.transactionBook.get(i).toString());
        }
    }

    public void start() {
        // TODO: Create internal simulated clock
        Clock simulator_clock = simulatedClock.generateClock();


        for (int i = 0; i <  TransactionReader.transactionBook.length(); i++) {

            JSONObject jsonObject = TransactionReader.transactionBook.getJSONObject(i);
            String timeStamp = jsonObject.getString("TimeStamp");
            LocalDateTime transactionTime = SimulatedClock.timeStampToLocalDateTime(timeStamp);

            while (!transactionTime.isBefore(SimulatedClock.clockToLocalDateTime(simulator_clock))) {
//                Thread.sleep(100);
            }
            //System.out.println("TRANSACTION IN : " + jsonObject.getString("Symbol") + " at " + timeStamp);
            TransactionEventEmitter.onTransactionGeneric( jsonObject );

            // HashMap<String, ArrayList<JSONObject>> repertoryStockTradeHistory
            String symbol_name = jsonObject.getString("Symbol");
            ArrayList<JSONObject> stockBook = TransactionReader.repertoryStockTradeHistory.get(symbol_name);

            if (stockBook == null){
                stockBook = new ArrayList<>();
            }
            stockBook.add(jsonObject);
            TransactionReader.repertoryStockTradeHistory.put(symbol_name, stockBook);

//            transactionReader.repertoryStockTradeHistory.put();
        }


    }





}
