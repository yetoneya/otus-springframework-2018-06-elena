
package ru.otus.elena.studentiqtest.iqtest;

import com.opencsv.CSVIterator;
import com.opencsv.CSVReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;

public class IQTestImpl implements IQTest{    
    MessageSource messageSource;
    final private String language;
    final private String enFile;
    final private String ruFile;
    
    @Autowired
    public IQTestImpl(MessageSource messageSource, @Value("${app.lang}")String language, @Value("${app.enfile}")String enFile, @Value("${app.rufile}")String ruFile) {
        this.messageSource = messageSource;
        this.language = language;
        this.enFile = enFile;
        this.ruFile = ruFile;
    }


    
    @Override
    public int IQtest() {
        Scanner scanner = new Scanner(System.in);
        return doTest(readCSV(), scanner);
    }

    private Map<String, Integer> readCSV() {
        try {
            CSVReader reader = null;
            reader = new CSVReader(new InputStreamReader(new FileInputStream(getFileName()),"UTF-8"));
            CSVIterator iterator = (CSVIterator) reader.iterator();
            Map<String, Integer> map = new HashMap<>();
            while (iterator.hasNext()) {
                String[] line = iterator.next();
                int ans = Integer.parseInt(line[1]);
                map.putIfAbsent(line[0], ans);
            }
            reader.close();
            return map;
        } catch (Exception ex) {
            return null;
        }
    }
        private int doTest(Map<String, Integer> map,Scanner scanner) {
        try {            
            final int[] counter = new int[1];
            map.forEach((k, v) -> {
                System.out.println(k + ">");
                if (scanner.hasNextInt()) {
                    int studentAnswer = scanner.nextInt();
                    if (studentAnswer == v) {
                        printMessage("iqtest.right");
                        counter[0]++;
                    } else {
                        printMessage("iqtest.wrong");
                    }
                }
            });
            return counter[0];
        } catch (Exception ex) {
            return 0;
        }
    }
    
    
    private void printMessage(String res) {
        if (language.equalsIgnoreCase("ru")) {
            System.out.println(
                    messageSource.getMessage(res, new String[]{}, new Locale("ru", "RU")));
        } else {
            System.out.println(
                    messageSource.getMessage(res, new String[]{}, Locale.ENGLISH));
        }
    }

    private String getFileName(){
        if(language.equalsIgnoreCase("en")){
            return enFile;
        }
        else{
           return ruFile;
        }
    }


}
