import java.util.ArrayList;
import java.util.List;

public class SistemEnergi {

    private List<Ruangan> daftarRuangan;

  
    public SistemEnergi() {
        daftarRuangan = new ArrayList<>();
    }

  
    public void tambahRuangan(Ruangan ruangan) {
        daftarRuangan.add(ruangan);
    }


    public List<Ruangan> getDaftarRuangan() {
        return daftarRuangan;
    }


    public double hitungKwhBulanan() {
        double total = 0;

        for (Ruangan ruangan : daftarRuangan) {
            total += ruangan.hitungKwhRuangan() * 30;
        }

        return total;
    }


    public double hitungTagihan() {
        double totalKwh = hitungKwhBulanan();
        double biaya;

        if (totalKwh <= 100) {
            biaya = totalKwh * 1200;
        } else if (totalKwh <= 200) {
            biaya = (100 * 1200)
                    + ((totalKwh - 100) * 1500);
        } else {
            biaya = (100 * 1200)
                    + (100 * 1500)
                    + ((totalKwh - 200) * 1800);
        }

     
        double pajakPJU = biaya * 0.05;

        return biaya + pajakPJU;
    }


    public Perangkat perangkatTerboros() {
        Perangkat terboros = null;
        double konsumsiTerbesar = 0;

        for (Ruangan ruangan : daftarRuangan) {
            for (Perangkat perangkat : ruangan.getDaftarPerangkat()) {

                double konsumsi = perangkat.hitungKwhHarian();

                if (konsumsi > konsumsiTerbesar) {
                    konsumsiTerbesar = konsumsi;
                    terboros = perangkat;
                }
            }
        }

        return terboros;
    }


    public void tampilkanLaporan() {

        System.out.println("\n");
        System.out.println("==============================================================");
        System.out.println("             LAPORAN KONSUMSI ENERGI RUMAH PINTAR");
        System.out.println("==============================================================");


        for (Ruangan ruangan : daftarRuangan) {

            System.out.println("\nRuangan: " + ruangan.getNama());

            System.out.println("--------------------------------------------------------------");

            System.out.printf("%-18s %-10s %-12s %-10s %-12s%n",
                    "Perangkat",
                    "Daya (W)",
                    "Jam/hari",
                    "Status",
                    "kWh/hari");

            System.out.println("--------------------------------------------------------------");

            for (Perangkat perangkat : ruangan.getDaftarPerangkat()) {

                String status = perangkat.isMenyala() ? "Nyala" : "Mati";

                System.out.printf("%-18s %-10.1f %-12.1f %-10s %-12.3f%n",
                        perangkat.getNama(),
                        perangkat.getDayaWatt(),
                        perangkat.getJamPakaiPerHari(),
                        status,
                        perangkat.hitungKwhHarian());
            }

            System.out.println("--------------------------------------------------------------");

            System.out.printf("%-48s %.3f kWh%n",
                    "Total ruangan (kWh/hari)",
                    ruangan.hitungKwhRuangan());

            System.out.println("==============================================================");
        }

   
        double totalKwh = hitungKwhBulanan();
        double totalTagihan = hitungTagihan();

        System.out.println("\nRINGKASAN TAGIHAN");
        System.out.println("--------------------------------------------------------------");

        System.out.printf("%-30s : %.2f kWh%n",
                "Total konsumsi bulanan",
                totalKwh);

        System.out.printf("%-30s : Rp%,.0f%n",
                "Tagihan termasuk PJU 5%",
                totalTagihan);


        Perangkat p = perangkatTerboros();

        if (p != null) {

            System.out.printf("%-30s : %s (%.3f kWh/hari)%n",
                    "Perangkat terboros",
                    p.getNama(),
                    p.hitungKwhHarian());

        } else {

            System.out.printf("%-30s : Belum ada perangkat menyala%n",
                    "Perangkat terboros");
        }

        System.out.println("==============================================================");


        if (totalTagihan > 500000 && p != null) {

            System.out.println("\n*** PERINGATAN: Tagihan melebihi Rp500.000 ***");

            double jamAwal = p.getJamPakaiPerHari();
            double kwhAwal = totalKwh;
            double tagihanAwal = totalTagihan;


            double jamBaru = jamAwal * 0.8;

            p.setJamPakaiPerHari(jamBaru);


            double kwhBaru = hitungKwhBulanan();
            double tagihanBaru = hitungTagihan();

            double penghematan = tagihanAwal - tagihanBaru;

           System.out.println("\n==============================================================");
System.out.println("                 PERINGATAN TAGIHAN");
System.out.println("==============================================================");

System.out.printf("%-25s %s sebesar 20%% dari %.1f jam menjadi %.1f jam per hari.%n",
        "Simulasi:",
        "kurangi pemakaian " + p.getNama(),
        jamAwal,
        jamBaru);

System.out.printf("%-25s %.2f kWh menjadi %.2f kWh%n",
        "Konsumsi bulanan:",
        kwhAwal,
        kwhBaru);

System.out.printf("%-25s Rp%,.0f menjadi Rp%,.0f%n",
        "Tagihan:",
        tagihanAwal,
        tagihanBaru);

System.out.printf("%-25s Rp%,.0f per bulan%n",
        "Penghematan:",
        penghematan);

System.out.println("==============================================================");

System.out.println("==============================================================");


            p.setJamPakaiPerHari(jamAwal);
        }

        System.out.println("\n==============================================================");
        System.out.println("             === SMART HOME ENERGY MONITOR ===");
        System.out.println("==============================================================");
    }
}