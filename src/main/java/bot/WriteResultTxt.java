package bot;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class WriteResultTxt {
//todo что если поля нуллили привышают диапазон ?
    public void writeResultTxt(Igra igra){

  byte[] a = new byte[230];
  byte[] b = new byte[230];
  byte[] c = new byte[230];
  byte[] d = new byte[230];
  byte[] e = new byte[230];
  byte[] f = new byte[230];
  byte[] g = new byte[230];
  byte[] i= new byte[230];
  byte[] j = new byte[230];


 a = vidachaArrOneMinutes(igra.totalOne1, igra.totalTwo1, igra.predlagaemyiTotalNa1Min, 1, igra.resultTotal);
 b = vidachaArrOneMinutes(igra.totalOne2, igra.totalTwo2, igra.predlagaemyiTotalNa2Min, 1, igra.resultTotal);
 c = vidachaArrOneMinutes(igra.totalOne3, igra.totalTwo3, igra.predlagaemyiTotalNa3Min, 1, igra.resultTotal);
 d = vidachaArrOneMinutes(igra.totalOne4, igra.totalTwo4, igra.predlagaemyiTotalNa4Min, 1, igra.resultTotal);
 e = vidachaArrOneMinutes(igra.totalOne5, igra.totalTwo5, igra.predlagaemyiTotalNa5Min, 1, igra.resultTotal);
 f = vidachaArrOneMinutes(igra.totalOne6, igra.totalTwo6, igra.predlagaemyiTotalNa6Min, 1, igra.resultTotal);
 g = vidachaArrOneMinutes(igra.totalOne7, igra.totalTwo7, igra.predlagaemyiTotalNa7Min, 1, igra.resultTotal);
 i = vidachaArrOneMinutes(igra.totalOne8, igra.totalTwo8, igra.predlagaemyiTotalNa8Min, 1, igra.resultTotal);
 j = vidachaArrOneMinutes(igra.totalOne9, igra.totalTwo9, igra.predlagaemyiTotalNa9Min, 1, igra.resultTotal);

 String aString = byteArrayToString(a);
 String bString = byteArrayToString(a);
 String cString = byteArrayToString(a);
 String dString = byteArrayToString(a);
 String eString = byteArrayToString(a);
 String fString = byteArrayToString(a);
 String gString = byteArrayToString(a);
 String iString = byteArrayToString(a);
 String jString = byteArrayToString(a);



        try (BufferedWriter writer = new BufferedWriter(new FileWriter("C:/output.txt"))) {
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






    }

    public byte[] vidachaArrOneMinutes(int tot1 , int tot2 , double rTot, int minut, int res){

        // todo провера всех полей на нулл . Нужна ли?

        byte[] resArr = new byte[230];
        for (int i = 0; i < 230; i++) {
           resArr[i] = 0;
        }
        resArr[tot1-1] = 1;
        resArr[tot2+49] = 1;
        resArr[(int) (rTot*2+99)] = 1;
        resArr[minut+219] = 1;
        resArr[229] = (byte) (res < rTot ? 1 : 0);
        return resArr;
    }




    private static String byteArrayToString(byte[] array) {
        StringBuilder builder = new StringBuilder();
        for (byte value : array) {
            builder.append(value).append(" ");
        }
        return builder.toString();
    }
}

