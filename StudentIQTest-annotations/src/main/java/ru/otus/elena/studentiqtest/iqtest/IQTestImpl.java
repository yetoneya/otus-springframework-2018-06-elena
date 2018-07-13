
package ru.otus.elena.studentiqtest.iqtest;

import com.opencsv.CSVIterator;
import com.opencsv.CSVReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class IQTestImpl implements IQTest{
     
    @Autowired
    @Value("${app.csv}")String fileName;
    
    @Override
    public int IQtest() {
        Scanner scanner = new Scanner(System.in);
        return doTest(readCSV(),scanner);
    }
    
    private Map<String, Integer> readCSV() {
        try {
            CSVReader reader = null;
            reader = new CSVReader(new FileReader(fileName));
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
                        System.out.println("You are right!" + studentAnswer);
                        counter[0]++;
                    } else {
                        System.out.println("Sorry, you are wrong.");
                    }
                }
            });
            return counter[0];
        } catch (Exception ex) {
            return 0;
        }
    }
}
