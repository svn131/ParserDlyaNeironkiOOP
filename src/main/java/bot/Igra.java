package bot;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.*;

public class Igra {

    String o1;
    String o2;
    boolean zamokO1O2 = true;

    private long startanula = System.currentTimeMillis();

    long vremyaPoslednegoVzaimodestviya = System.currentTimeMillis();

    boolean zamokResult = true;
    boolean zamokWrite = false;
    boolean zamokSamounichtoghitel = false;

    long samounochtogitel = 2000000000000L;


    int seriinik;

    int time;



    Minute masivMinut[];

    int resultTotal;



    public Igra(int seriinik) {
        this.seriinik = seriinik;
        masivMinut = new Minute[]{new Minute(), new Minute(), new Minute(), new Minute(), new Minute(), new Minute(), new Minute(), new Minute(), new Minute()};
    }

    public String getStartanula() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date(startanula);
        return "Стартанула " + sdf.format(date);
    }

    public void setO1iO2(String o1,String o2) {
        if(zamokO1O2) {
            this.o1 = o1;
            this.o2 = o2;
            zamokO1O2 = false;
        }
    }



    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Igra other = (Igra) obj;
        return Objects.equals(seriinik, other.seriinik);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seriinik);
    }

    @Override
    public String toString() {
        return "Igra{" +
                "o1='" + o1 + '\'' +
                ", o2='" + o2 + '\'' +
                ", vremyaPoslednegoVzaimodestviya=" + vremyaPoslednegoVzaimodestviya +
                ", zamokResult=" + zamokResult +
                ", zamokWrite=" + zamokWrite +
                ", zamokSamounichtoghitel=" + zamokSamounichtoghitel +
                ", samounochtogitel=" + samounochtogitel +
                ", seriinik=" + seriinik +
                ", time=" + time +
                ", resultTotal=" + resultTotal +
                getStartanula() +
                "лист минут" + Arrays.toString(masivMinut) +
                "time "+ time +
                '}';
    }


    public void setPredlagaemyiBukmekermTotal(int timess, double predlagaemyiBukmekermTotal) {
        switch (time / 60) {
            case 1:
                masivMinut[0].setPredlagaemyiBukmekermTotal(predlagaemyiBukmekermTotal);
                break;

            case 2:
                masivMinut[1].setPredlagaemyiBukmekermTotal(predlagaemyiBukmekermTotal);
                break;

            case 3:
                masivMinut[2].setPredlagaemyiBukmekermTotal(predlagaemyiBukmekermTotal);
                break;
            case 4:
                masivMinut[3].setPredlagaemyiBukmekermTotal(predlagaemyiBukmekermTotal);
                break;

            case 5:
                masivMinut[4].setPredlagaemyiBukmekermTotal(predlagaemyiBukmekermTotal);
                break;

            case 6:
                masivMinut[5].setPredlagaemyiBukmekermTotal(predlagaemyiBukmekermTotal);
                break;
            case 7:
                masivMinut[6].setPredlagaemyiBukmekermTotal(predlagaemyiBukmekermTotal);
                break;

            case 8:
                masivMinut[7].setPredlagaemyiBukmekermTotal(predlagaemyiBukmekermTotal);
                break;

            case 9:
                masivMinut[8].setPredlagaemyiBukmekermTotal(predlagaemyiBukmekermTotal);
                break;
        }




    }
    public void setTotalOneandTwo(int timess,  int totalOne, int totalTwo){


        switch (time / 60) {
            case 1:
                masivMinut[0].setTotalOne(totalOne);
                masivMinut[0].setTotalTwo(totalTwo);
                break;
            case 2:
                masivMinut[1].setTotalOne(totalOne);
                masivMinut[1].setTotalTwo(totalTwo);
                break;
            case 3:
                masivMinut[2].setTotalOne(totalOne);
                masivMinut[2].setTotalTwo(totalTwo);
                break;
            case 4:
                masivMinut[3].setTotalOne(totalOne);
                masivMinut[3].setTotalTwo(totalTwo);
                break;
            case 5:
                masivMinut[4].setTotalOne(totalOne);
                masivMinut[4].setTotalTwo(totalTwo);
                break;
            case 6:
                masivMinut[5].setTotalOne(totalOne);
                masivMinut[5].setTotalTwo(totalTwo);
                break;
            case 7:
                masivMinut[6].setTotalOne(totalOne);
                masivMinut[6].setTotalTwo(totalTwo);
                break;
            case 8:
                masivMinut[7].setTotalOne(totalOne);
                masivMinut[7].setTotalTwo(totalTwo);
                break;
            case 9:
                masivMinut[8].setTotalOne(totalOne);
                masivMinut[8].setTotalTwo(totalTwo);
                break;


        }
    }


    public void setResultTotal(int timess,  int totalOne, int totalTwo){
        if(timess >= 600 && zamokResult){
            resultTotal = totalOne + totalTwo;
            zamokWrite = true;
        }
    }







//////////////////////////////////////////////////////////////////////////////
    public class Minute {

        double predlagaemyiBukmekermTotal;
        int totalOne;
        int totalTwo;


        public void setPredlagaemyiBukmekermTotal(double predlagaemyiBukmekermTotal) {
            if (0.0 != predlagaemyiBukmekermTotal) {
                this.predlagaemyiBukmekermTotal = predlagaemyiBukmekermTotal;
            }
        }

        public void setTotalOne(int totalOne) {
            if (totalOne != 0) {
                this.totalOne = totalOne;
            }
        }

        public void setTotalTwo(int totalTwo) {
            if (totalTwo != 0) {
                this.totalTwo = totalTwo;
            }
        }


        @Override
        public String toString() {
            return "Minute{" +
                    "predlagaemyiBukmekermTotal=" + predlagaemyiBukmekermTotal +
                    ", totalOne=" + totalOne +
                    ", totalTwo=" + totalTwo +
                    '}';
        }
    }







}