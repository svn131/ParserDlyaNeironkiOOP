package bot;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

public class DebugMode {
    private static PrintStream nullPrintStream = new PrintStream(new OutputStream() {
        @Override
        public void write(int b) {
            // Ничего не делаем, игнорируем вывод
        }
    });

    public static void setDebugMode(boolean debugMode) {
        if (debugMode) {
            try {
                System.setOut(new PrintStream(new FileOutputStream("C:/logOutput.txt")));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            System.setOut(nullPrintStream);
        }
    }
}