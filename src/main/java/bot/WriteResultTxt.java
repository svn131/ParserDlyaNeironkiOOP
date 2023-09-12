package bot;

public class WriteResultTxt {
//todo что если поля нуллили привышают диапазон ?
    public void writeResultTxt(Igra igra){

//igra.


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
}
