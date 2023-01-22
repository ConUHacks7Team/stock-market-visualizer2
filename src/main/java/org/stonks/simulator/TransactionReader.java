package org.stonks.simulator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class TransactionReader {
    final private String transacton_folder_path = "./transactions/";
    public JSONArray transactionBook = loadSaveFile();
    public TransactionReader(){}


    private JSONArray loadSaveFile() {
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
