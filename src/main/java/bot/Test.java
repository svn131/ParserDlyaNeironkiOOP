package bot;

import org.w3c.dom.ls.LSOutput;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {
    public static void main(String[] args) {


        long timestamp = 1694854248381L; // Значение System.currentTimeMillis()
//                         1694860677249
//        6428862
        Date date = new Date(timestamp);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = sdf.format(date);
        System.out.println(formattedDate);
        System.out.println(System.currentTimeMillis());
    }
}