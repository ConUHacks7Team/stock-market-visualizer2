package org.stonks.simulator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class TransactionReader {
    static final private String transacton_folder_path = "./transactions/";
    static public  JSONArray transactionBook = loadSaveFile();

    static public HashMap<String, ArrayList<JSONObject>> repertoryStockTradeHistory = new HashMap<>();


    public TransactionReader(){}


    static private JSONArray loadSaveFile() {
        JSONArray jsonArray = new JSONArray();
        try {
            JSONTokener parser = new JSONTokener(new FileInputStream(transacton_folder_path+"TSXData.json"));
            jsonArray = (JSONArray) parser.nextValue();
        } catch (FileNotFoundException e){
            System.err.println("File non-existing.");
        } catch (JSONException e) {
            System.err.println("Incorrect file format.");
        }
        return jsonArray;
    }

}
