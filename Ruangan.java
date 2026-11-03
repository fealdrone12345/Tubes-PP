
import java.util.ArrayList;
import java.util.List;

public class Ruangan {

    private String nama;
    private List<Perangkat> daftarPerangkat;

    public Ruangan(String nama) {
        this.nama = nama;
        daftarPerangkat = new ArrayList<>();
    }


    public void tambahPerangkat(Perangkat perangkat) {
        daftarPerangkat.add(perangkat);
    }

    public double hitungKwhRuangan() {
        double total = 0;

        for (Perangkat perangkat : daftarPerangkat) {
            total += perangkat.hitungKwhHarian();
        }

        return total;
    }


    public String getNama() {
        return nama;
    }

    public List<Perangkat> getDaftarPerangkat() {
        return daftarPerangkat;
    }
}
