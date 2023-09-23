//package bot;
//
//import java.io.FileOutputStream;
//import java.io.PrintStream;
//
//public class ConsoleToFile {
//    public static void main(String[] args) {
//        try {
//            // Создание объекта PrintStream и связывание его с текстовым документом
//            PrintStream out = new PrintStream(new FileOutputStream("C:/outputLog.txt"));
//
//            // Перенаправление стандартного потока вывода в объект PrintStream
//            System.setOut(out);
//
//            // Вся последующая информация, выводимая в консоль, будет записана в текстовый документ
//            System.out.println("Пример вывода в текстовый документ");
//
//            // Закрытие объекта PrintStream и освобождение ресурсов
//            out.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//}
//
