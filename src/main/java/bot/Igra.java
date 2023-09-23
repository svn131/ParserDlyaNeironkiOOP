package bot;

import java.util.Objects;

public class Igra {

    String o1;
    String o2;

    int seriinik;

    int time;

    int totalOne1;
    int totalOne2;
    int totalOne3;
    int totalOne4;
    int totalOne5;
    int totalOne6;
    int totalOne7;
    int totalOne8;
    int totalOne9;


    int totalTwo1;
    int totalTwo2;
    int totalTwo3;
    int totalTwo4;
    int totalTwo5;
    int totalTwo6;
    int totalTwo7;
    int totalTwo8;
    int totalTwo9;

    int resultTotal;

//    long startanula = System.currentTimeMillis();

    long vremyaPoslednegoVzaimodestviya = System.currentTimeMillis();

    ///////


    double predlagaemyiTotalNa1Min;
    double predlagaemyiTotalNa2Min;
    double predlagaemyiTotalNa3Min;
    double predlagaemyiTotalNa4Min;
    double predlagaemyiTotalNa5Min;
    double predlagaemyiTotalNa6Min;
    double predlagaemyiTotalNa7Min;
    double predlagaemyiTotalNa8Min;
    double predlagaemyiTotalNa9Min;




    boolean zamok1 = true;
    boolean zamok2 = true;
    boolean zamok3 = true;
    boolean zamok4 = true;
    boolean zamok5 = true;
    boolean zamok6 = true;
    boolean zamok7 = true;
    boolean zamok8 = true;
    boolean zamok9 = true;

boolean zamokResult = true;
boolean zamokWrite = false;
//boolean zamokSamounichtoghitel = false;

long samounochtogitel = 2000000000000L;





    public Igra(int seriinik) {
        this.seriinik = seriinik;
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

//    @Override
//    public String toString() {
//        return "Igra{" +
//                "seriinik=" + seriinik +
//                ", time=" + time +
//                ", vremyaPoslednegoVzaimodestviya=" + vremyaPoslednegoVzaimodestviya + /// todo Убрать ?
//                '}';
//    }


    @Override
    public String toString() {
        return "Igra{" +
                "seriinik=" + seriinik +
                ", time=" + time +
                ", totalOne1=" + totalOne1 +
                ", totalOne2=" + totalOne2 +
                ", totalOne3=" + totalOne3 +
                ", totalOne4=" + totalOne4 +
                ", totalOne5=" + totalOne5 +
                ", totalOne6=" + totalOne6 +
                ", totalOne7=" + totalOne7 +
                ", totalOne8=" + totalOne8 +
                ", totalOne9=" + totalOne9 +
                ", totalTwo1=" + totalTwo1 +
                ", totalTwo2=" + totalTwo2 +
                ", totalTwo3=" + totalTwo3 +
                ", totalTwo4=" + totalTwo4 +
                ", totalTwo5=" + totalTwo5 +
                ", totalTwo6=" + totalTwo6 +
                ", totalTwo7=" + totalTwo7 +
                ", totalTwo8=" + totalTwo8 +
                ", totalTwo9=" + totalTwo9 +
                ", resultTotal=" + resultTotal +
                ", vremyaPoslednegoVzaimodestviya=" + vremyaPoslednegoVzaimodestviya +
                ", predlagaemyiTotalNa1Min=" + predlagaemyiTotalNa1Min +
                ", predlagaemyiTotalNa2Min=" + predlagaemyiTotalNa2Min +
                ", predlagaemyiTotalNa3Min=" + predlagaemyiTotalNa3Min +
                ", predlagaemyiTotalNa4Min=" + predlagaemyiTotalNa4Min +
                ", predlagaemyiTotalNa5Min=" + predlagaemyiTotalNa5Min +
                ", predlagaemyiTotalNa6Min=" + predlagaemyiTotalNa6Min +
                ", predlagaemyiTotalNa7Min=" + predlagaemyiTotalNa7Min +
                ", predlagaemyiTotalNa8Min=" + predlagaemyiTotalNa8Min +
                ", predlagaemyiTotalNa9Min=" + predlagaemyiTotalNa9Min +
                ", zamok1=" + zamok1 +
                ", zamok2=" + zamok2 +
                ", zamok3=" + zamok3 +
                ", zamok4=" + zamok4 +
                ", zamok5=" + zamok5 +
                ", zamok6=" + zamok6 +
                ", zamok7=" + zamok7 +
                ", zamok8=" + zamok8 +
                ", zamok9=" + zamok9 +
                ", zamokResult=" + zamokResult +
                '}';
    }
}
