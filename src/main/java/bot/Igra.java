package bot;

import java.util.Objects;

public class Igra {

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

    @Override
    public String toString() {
        return "Igra{" +
                "seriinik=" + seriinik +
                ", time=" + time +
                ", vremyaPoslednegoVzaimodestviya=" + vremyaPoslednegoVzaimodestviya + /// todo Убрать ?
                '}';
    }
}
