
import java.util.Scanner;

public class MainSmartHome {

    static Scanner input = new Scanner(System.in);
    static SistemEnergi sistem = new SistemEnergi();

  
    public static String bacaNama(String pesan) {
        String nama;

        while (true) {
            System.out.print(pesan);
            nama = input.nextLine().trim();

            if (!nama.isEmpty()) {
                return nama;
            }

            System.out.println("Nama tidak boleh kosong!");
        }
    }


    public static double bacaAngka(String pesan) {
        double angka;

        while (true) {
            System.out.print(pesan);

            try {
                angka = Double.parseDouble(input.nextLine());

                if (Double.isFinite(angka) && angka > 0) {
                    return angka;
                }

                System.out.println("Angka harus lebih dari 0!");

            } catch (NumberFormatException e) {
                System.out.println("Input harus berupa angka!");
            }
        }
    }

  
    public static double bacaJam() {
        double jam;

        while (true) {
            jam = bacaAngka("Masukkan jam pemakaian per hari: ");

            if (jam <= 24) {
                return jam;
            }

            System.out.println("Jam pemakaian maksimal 24 jam!");
        }
    }


    public static int bacaPilihan(String pesan) {
        int pilihan;

        while (true) {
            System.out.print(pesan);

            try {
                pilihan = Integer.parseInt(input.nextLine().trim());

                return pilihan;

            } catch (NumberFormatException e) {
                System.out.println("Pilihan harus berupa angka!");
            }
        }
    }

   
    public static void tampilkanDaftarRuangan() {

        System.out.println("\nDAFTAR RUANGAN");

        for (int i = 0; i < sistem.getDaftarRuangan().size(); i++) {

            System.out.println((i + 1) + ". "
                    + sistem.getDaftarRuangan().get(i).getNama());
        }
    }

  
    public static void tambahRuangan() {

        System.out.println("\n--- TAMBAH RUANGAN ---");

        String nama = bacaNama("Masukkan nama ruangan: ");

        Ruangan ruangan = new Ruangan(nama);

        sistem.tambahRuangan(ruangan);

        System.out.println("Ruangan berhasil ditambahkan!");
    }

   
    public static void tambahPerangkat() {

        if (sistem.getDaftarRuangan().isEmpty()) {
            System.out.println("\nTambahkan ruangan terlebih dahulu!");
            return;
        }

        System.out.println("\n--- TAMBAH PERANGKAT ---");

        tampilkanDaftarRuangan();

        int pilihan = bacaPilihan("Pilih nomor ruangan: ");

        if (pilihan < 1 || pilihan > sistem.getDaftarRuangan().size()) {
            System.out.println("Nomor ruangan tidak tersedia!");
            return;
        }

        Ruangan ruangan = sistem.getDaftarRuangan().get(pilihan - 1);

        String nama = bacaNama("Masukkan nama perangkat: ");

        double daya = bacaAngka("Masukkan daya perangkat (Watt): ");

        double jam = bacaJam();

        Perangkat perangkat = new Perangkat(nama, daya, jam);

        ruangan.tambahPerangkat(perangkat);

        System.out.println("Perangkat berhasil ditambahkan!");
    }

    
    public static void ubahStatusPerangkat() {

        if (sistem.getDaftarRuangan().isEmpty()) {
            System.out.println("\nBelum ada ruangan!");
            return;
        }

        System.out.println("\n--- NYALAKAN / MATIKAN PERANGKAT ---");

        tampilkanDaftarRuangan();

        int pilihanRuangan = bacaPilihan("Pilih nomor ruangan: ");

        if (pilihanRuangan < 1
                || pilihanRuangan > sistem.getDaftarRuangan().size()) {

            System.out.println("Nomor ruangan tidak tersedia!");
            return;
        }

        Ruangan ruangan = sistem.getDaftarRuangan()
                .get(pilihanRuangan - 1);

        if (ruangan.getDaftarPerangkat().isEmpty()) {
            System.out.println("Belum ada perangkat di ruangan ini!");
            return;
        }

        System.out.println("\nDAFTAR PERANGKAT");

        for (int i = 0; i < ruangan.getDaftarPerangkat().size(); i++) {

            Perangkat perangkat = ruangan.getDaftarPerangkat().get(i);

            String status = perangkat.isMenyala() ? "Nyala" : "Mati";

            System.out.println((i + 1) + ". "
                    + perangkat.getNama() + " - " + status);
        }

        int pilihanPerangkat = bacaPilihan("Pilih nomor perangkat: ");

        if (pilihanPerangkat < 1
                || pilihanPerangkat > ruangan.getDaftarPerangkat().size()) {

            System.out.println("Nomor perangkat tidak tersedia!");
            return;
        }

        Perangkat perangkat = ruangan.getDaftarPerangkat()
                .get(pilihanPerangkat - 1);

        System.out.println("\n1. Nyalakan");
        System.out.println("2. Matikan");

        int pilihanStatus = bacaPilihan("Pilih tindakan: ");

        if (pilihanStatus == 1) {

            perangkat.nyalakan();

            System.out.println("Perangkat berhasil dinyalakan!");

        } else if (pilihanStatus == 2) {

            perangkat.matikan();

            System.out.println("Perangkat berhasil dimatikan!");

        } else {

            System.out.println("Pilihan tidak tersedia!");
        }
    }

    public static void main(String[] args) {

        int pilihan;

        do {

            System.out.println("\n==============================================");
            System.out.println("          SMART HOME ENERGY MONITOR");
            System.out.println("==============================================");
            System.out.println("1. Tambah Ruangan");
            System.out.println("2. Tambah Perangkat");
            System.out.println("3. Nyalakan / Matikan Perangkat");
            System.out.println("4. Lihat Laporan");
            System.out.println("5. Keluar");
            System.out.println("==============================================");

            pilihan = bacaPilihan("Pilih menu: ");

            switch (pilihan) {

                case 1:
                    tambahRuangan();
                    break;

                case 2:
                    tambahPerangkat();
                    break;

                case 3:
                    ubahStatusPerangkat();
                    break;

                case 4:
                    sistem.tampilkanLaporan();
                    break;

                case 5:
                    System.out.println("\nTerima kasih telah menggunakan SimuSmart!");
                    break;

                default:
                    System.out.println("Menu tidak tersedia!");
            }

        } while (pilihan != 5);

        input.close();
    }
}
