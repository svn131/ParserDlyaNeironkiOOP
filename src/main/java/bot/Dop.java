package bot;

import java.util.Objects;

public class Dop {

    int seriinik;

    String chetvert;


    public Dop(int seriinik) {
        this.seriinik = seriinik;
    }

    public int getSeriinik() {
        return seriinik;
    }

    public void setSeriinik(int seriinik) {
        this.seriinik = seriinik;
    }

    public String getChetvert() {
        return chetvert;
    }

    public void setChetvert(String chetvert) {
        this.chetvert = chetvert;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Dop other = (Dop) obj;
        return Objects.equals(seriinik, other.seriinik);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seriinik);
    }
}
