package bot;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TestMetod {
    public static void main(String[] args) {

        readOutputFile();


    }

    public static void readOutputFile() {
        try (BufferedReader br = new BufferedReader(new FileReader("C:/output.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();

                int team1Goals = countOnes(line.substring(0, 50));
                int team2Goals = countOnes(line.substring(50, 51));

                int totalIndex = line.substring(100, 220).indexOf('1');
                double total = totalIndex * 0.5;

                StringBuilder minutes = new StringBuilder();
                for (int i = 220; i < 230; i++) {
                    if (line.charAt(i) == '1') {
                        minutes.append(i - 219).append(" ");
                    }
                }

                String result = (line.charAt(229) == '1') ? "Выигрыш" : "Проигрыш";

                System.out.println("Строка: " + line);
                System.out.println("Команда 1: " + team1Goals + " голов");
                System.out.println("Команда 2: " + team2Goals + " голов");
                System.out.println("Тотал: " + total);
                System.out.println("Минуты: " + minutes.toString().trim());
                System.out.println("Результат: " + result);
                System.out.println("------------------------------");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int countOnes(String str) {
        int count = 0;
        for (char c : str.toCharArray()) {
            if (c == '1') {
                count++;
            }
        }
        return count;
    }




}
