
package ru.otus.elena.studentiqtest.iqtest;

import com.opencsv.CSVIterator;
import com.opencsv.CSVReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class IQTestImpl implements IQTest{

    @Override
    public int IQtest() {
        try {
            CSVReader reader = null;
            final int[] counter = new int[1];
            reader = new CSVReader(new FileReader("src/main/resources/questions.csv"));
            CSVIterator iterator = (CSVIterator) reader.iterator();
            Map<String, Integer> map = new HashMap<>();
            while (iterator.hasNext()) {
                String[] line = iterator.next();
                int ans = Integer.parseInt(line[1]);
                map.putIfAbsent(line[0], ans);
            }
            reader.close();
            Scanner scanner = new Scanner(System.in);
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
                scanner.nextLine();
            });
            //scanner.close();
            return counter[0];
        } catch (Exception ex) {
            return 0;
        }
    }
    
}
