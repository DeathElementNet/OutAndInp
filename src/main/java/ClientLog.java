package main.java;

import au.com.bytecode.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClientLog {
    private List<String[]> log = new ArrayList<>();
    //private String log = "";


    public void Log(int productNum, int amount) {
      //  log += String.format("%d,%d\n", productNum, amount);
        log.add(new String[] {"" + productNum, "" + amount});
    }
    public void exportAsCSV(File txtFile){
        if (!txtFile.exists()){
           // log = "product,amount\n" + log;
            log.add(0,new String[] {"productNum,amount"} );

        }
//        try (FileWriter writer = new FileWriter(txtFile, true)) {
//
//            writer.write(log);
//        } catch (IOException e){
//            throw new RuntimeException(e);
//        }

        try (CSVWriter writer = new CSVWriter(new FileWriter(txtFile, true))){
            writer.writeAll(log);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}