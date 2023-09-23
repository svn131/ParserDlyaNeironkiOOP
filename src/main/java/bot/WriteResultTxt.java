package bot;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WriteResultTxt {
    //todo что если поля нуллили привышают диапазон ?
    public void writeResultTxt(Igra igra) {

        System.out.println("Write nighe igra v----------------------------------------- ");

        String str = igra.toString();
        System.out.println(str);

        String timeFormat = "HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(timeFormat);
        String currentTime = sdf.format(new Date());

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("C:/output.txt", true))) {
            writer.write(currentTime + " - " + str);
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


        a = vidachaArrOneMinutes(igra.masivMinut[0].totalOne, igra.masivMinut[0].totalTwo, igra.masivMinut[0].predlagaemyiBukmekermTotal, 1, igra.resultTotal);
        b = vidachaArrOneMinutes(igra.masivMinut[1].totalOne, igra.masivMinut[1].totalTwo, igra.masivMinut[1].predlagaemyiBukmekermTotal, 2, igra.resultTotal);
        c = vidachaArrOneMinutes(igra.masivMinut[2].totalOne, igra.masivMinut[2].totalTwo, igra.masivMinut[2].predlagaemyiBukmekermTotal, 3, igra.resultTotal);
        d = vidachaArrOneMinutes(igra.masivMinut[3].totalOne, igra.masivMinut[3].totalTwo, igra.masivMinut[3].predlagaemyiBukmekermTotal, 4, igra.resultTotal);
        e = vidachaArrOneMinutes(igra.masivMinut[4].totalOne, igra.masivMinut[4].totalTwo, igra.masivMinut[4].predlagaemyiBukmekermTotal, 5, igra.resultTotal);
        f = vidachaArrOneMinutes(igra.masivMinut[5].totalOne, igra.masivMinut[5].totalTwo, igra.masivMinut[5].predlagaemyiBukmekermTotal, 6, igra.resultTotal);
        g = vidachaArrOneMinutes(igra.masivMinut[6].totalOne, igra.masivMinut[6].totalTwo, igra.masivMinut[6].predlagaemyiBukmekermTotal, 7, igra.resultTotal);
        i = vidachaArrOneMinutes(igra.masivMinut[7].totalOne, igra.masivMinut[7].totalTwo, igra.masivMinut[7].predlagaemyiBukmekermTotal, 8, igra.resultTotal);
        j = vidachaArrOneMinutes(igra.masivMinut[8].totalOne, igra.masivMinut[8].totalTwo, igra.masivMinut[8].predlagaemyiBukmekermTotal, 9, igra.resultTotal);

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

        byte[] resArr = new byte[300];
        for (int i = 0; i < 230; i++) {
            resArr[i] = 0;
        }
        resArr[tot1] = 1; //  от нуля до 49
        resArr[tot2 + 50] = 1;// от нуля до 49
        resArr[(int) (rTot * 2 + 100)] = 1;// от 0 до 94,5 до 289 индекса включительноведь минута менее 1 не будет
        resArr[minut + 289] = 1;// от 1 до 9
        resArr[299] = (byte) (res < rTot ? 1 : 0);//

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


    public void tempWriteOtladka(){

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

