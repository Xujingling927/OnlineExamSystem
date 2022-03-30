package com.examination.util;

import com.examination.entity.FillQuestion;
import com.examination.entity.JudgeQuestion;
import com.examination.entity.MultiQuestion;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSVReader {

    public static String[] readLine(String file){
        ArrayList<String> res = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(isr);
            String line = "";
            while ((line = br.readLine()) != null) {
                res.add(line);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return  res.toArray(new String[0]);
    }


    public static void main(String[] args) {
        String file = "/Users/xujingling/Developer/OnlineExamSystem/src/main/resources/import/questionImprt.csv";//文件位置
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            String line = "";
            while ((line = br.readLine()) != null) {

                String[] words = line.split(",");


            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
