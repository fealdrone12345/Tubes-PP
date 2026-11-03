
public class Perangkat {

    private String nama;
    private double dayaWatt;
    private double jamPakaiPerHari;
    private boolean menyala;


    public Perangkat(String nama, double dayaWatt,
                     double jamPakaiPerHari) {
        this.nama = nama;
        this.dayaWatt = dayaWatt;
        this.jamPakaiPerHari = jamPakaiPerHari;
        this.menyala = true;
    }

  
    public double hitungKwhHarian() {
        if (menyala) {
            return (dayaWatt * jamPakaiPerHari) / 1000;
        }

        return 0;
    }


    public void nyalakan() {
        menyala = true;
    }


    public void matikan() {
        menyala = false;
    }

    public String getNama() {
        return nama;
    }

    public double getDayaWatt() {
        return dayaWatt;
    }

    public double getJamPakaiPerHari() {
        return jamPakaiPerHari;
    }

    public void setJamPakaiPerHari(double jam) {
        jamPakaiPerHari = jam;
    }

    public boolean isMenyala() {
        return menyala;
    }
}
