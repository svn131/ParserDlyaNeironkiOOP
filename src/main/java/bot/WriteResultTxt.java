package bot;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WriteResultTxt {
    //todo что если поля нуллили привышают диапазон ?
    public void writeResultTxt(Igra igra) {

        System.out.println("Write nighe igra v ");
       String vi = "Ee seryinik " + igra.seriinik + " " + igra.o1 +" "+ igra.o2 +
                " ТоталО1-1 "+ igra.totalOne1 +
                " ТоталО1-2 "+ igra.totalOne2 +
                " ТоталО1-3 "+ igra.totalOne3 +
                " ТоталО1-4 "+ igra.totalOne4 +
                " ТоталО1-5 "+ igra.totalOne5 +
                " ТоталО1-6 "+ igra.totalOne6 +
                " ТоталО1-7 "+ igra.totalOne7 +
                " ТоталО1-8 "+ igra.totalOne8 +
                " ТоталО1-9 "+ igra.totalOne9 +

                " ТоталО2-1 "+ igra.totalTwo1 +
                " ТоталО2-2 "+ igra.totalTwo2 +
                " ТоталО2-3 "+ igra.totalTwo3 +
                " ТоталО2-4 "+ igra.totalTwo4 +
                " ТоталО2-5 "+ igra.totalTwo5 +
                " ТоталО2-6 "+ igra.totalTwo6 +
                " ТоталО2-7 "+ igra.totalTwo7 +
                " ТоталО2-8 "+ igra.totalTwo8 +
                " ТоталО2-9 "+ igra.totalTwo9 +


                " Тотал давала бк-1 "+ igra.predlagaemyiTotalNa1Min +
                " Тотал давала бк-2 "+ igra.predlagaemyiTotalNa2Min +
                " Тотал давала бк-3 "+ igra.predlagaemyiTotalNa3Min +
                " Тотал давала бк-4 "+ igra.predlagaemyiTotalNa4Min +
                " Тотал давала бк-5 "+ igra.predlagaemyiTotalNa5Min +
                " Тотал давала бк-6 "+ igra.predlagaemyiTotalNa6Min +
                " Тотал давала бк-7 "+ igra.predlagaemyiTotalNa7Min +
                " Тотал давала бк-8 "+ igra.predlagaemyiTotalNa8Min +
                " Тотал давала бк-9 "+ igra.predlagaemyiTotalNa9Min ;

        System.out.println(vi);

////////////////////////////////
// На:
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter("C:/output.txt", true))) {
//            writer.write("Ee seryinik " + igra.seriinik);
//            writer.newLine();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        String timeFormat = "HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(timeFormat);
        String currentTime = sdf.format(new Date());

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("C:/output.txt", true))) {
            writer.write(currentTime + " - " + vi);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }


        /////////////////////

        System.out.println(igra);


        System.out.println("Writeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee1");

        byte[] a = new byte[230];
        byte[] b = new byte[230];
        byte[] c = new byte[230];
        byte[] d = new byte[230];
        byte[] e = new byte[230];
        byte[] f = new byte[230];
        byte[] g = new byte[230];
        byte[] i = new byte[230];
        byte[] j = new byte[230];


        a = vidachaArrOneMinutes(igra.totalOne1, igra.totalTwo1, igra.predlagaemyiTotalNa1Min, 1, igra.resultTotal);
        b = vidachaArrOneMinutes(igra.totalOne2, igra.totalTwo2, igra.predlagaemyiTotalNa2Min, 2, igra.resultTotal);
        c = vidachaArrOneMinutes(igra.totalOne3, igra.totalTwo3, igra.predlagaemyiTotalNa3Min, 3, igra.resultTotal);
        d = vidachaArrOneMinutes(igra.totalOne4, igra.totalTwo4, igra.predlagaemyiTotalNa4Min, 4, igra.resultTotal);
        e = vidachaArrOneMinutes(igra.totalOne5, igra.totalTwo5, igra.predlagaemyiTotalNa5Min, 5, igra.resultTotal);
        f = vidachaArrOneMinutes(igra.totalOne6, igra.totalTwo6, igra.predlagaemyiTotalNa6Min, 6, igra.resultTotal);
        g = vidachaArrOneMinutes(igra.totalOne7, igra.totalTwo7, igra.predlagaemyiTotalNa7Min, 7, igra.resultTotal);
        i = vidachaArrOneMinutes(igra.totalOne8, igra.totalTwo8, igra.predlagaemyiTotalNa8Min, 8, igra.resultTotal);
        j = vidachaArrOneMinutes(igra.totalOne9, igra.totalTwo9, igra.predlagaemyiTotalNa9Min, 9, igra.resultTotal);

        String aString = byteArrayToString(a);
        String bString = byteArrayToString(b);
        String cString = byteArrayToString(c);
        String dString = byteArrayToString(d);
        String eString = byteArrayToString(e);
        String fString = byteArrayToString(f);
        String gString = byteArrayToString(g);
        String iString = byteArrayToString(i);
        String jString = byteArrayToString(j);

        System.out.println("Writeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee2");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("C:/output.txt", true))) {
            writer.write(aString);
            writer.newLine();
            writer.write(bString);
            writer.newLine();
            writer.write(cString);
            writer.newLine();
            writer.write(dString);
            writer.newLine();
            writer.write(eString);
            writer.newLine();
            writer.write(fString);
            System.out.println("Writeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee3");
            writer.newLine();
            writer.write(gString);
            writer.newLine();
            writer.write(iString);
            writer.newLine();
            writer.write(jString);
            writer.newLine();
            // ... Записываем каждую строку в новой строке в файл
        } catch (IOException ee) {
            ee.printStackTrace();
        }

        System.out.println("Writeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee4");


    }

    public byte[] vidachaArrOneMinutes(int tot1, int tot2, double rTot, int minut, int res) {
        System.out.println("Writeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee5");
        // todo провера всех полей на нулл . Нужна ли?
        // роврка по максимальным значения перед записью todo что бы не записало своии единички на ячейку часы например при большом тотале

        byte[] resArr = new byte[230];
        for (int i = 0; i < 230; i++) {
            resArr[i] = 0;
        }
        resArr[tot1] = 1; //  от нуля до 49
        resArr[tot2 + 50] = 1;// от нуля до 49
        resArr[(int) (rTot * 2 + 100)] = 1;// от 0 до 59,5
        resArr[minut + 219] = 1;// от 1 до 9
        resArr[229] = (byte) (res < rTot ? 1 : 0);//

        System.out.println("Writeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee6");
        return resArr;
    }


    private static String byteArrayToString(byte[] array) {
        System.out.println("Writeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee7");
        StringBuilder builder = new StringBuilder();
        for (byte value : array) {
            builder.append(value).append(" ");
        }
        System.out.println("Writeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee8");
        return builder.toString();
    }

//    public void tempWriteOtladka() {
//        String timeFormat = "HH:mm:ss";
//        SimpleDateFormat sdf = new SimpleDateFormat(timeFormat);
//        String currentTime = sdf.format(new Date());
//
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter("C:/output.txt", true))) {
//            writer.write(currentTime + " - Ee seryinik " + igra.seriinik);
//            writer.newLine();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    public void tempWriteOtladka(){
//        byte[] otladkaArr = new byte[230];
//        for (int i = 0; i < 229; i++) {
//            otladkaArr[i] = (byte) i;
//        }
//
//        String tempS = byteArrayToString(otladkaArr);
//        try (BufferedWriter writerTemp = new BufferedWriter(new FileWriter("C:/output.txt",true))){
//            writerTemp.write(tempS);
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
        String timeFormat = "HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(timeFormat);
        String currentTime = sdf.format(new Date());

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("C:/output.txt", true))) {
            writer.write(currentTime + " - Старт программы ");
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    }

