# Smart Home Energy Monitor

Program Java berbasis konsol untuk memantau pemakaian listrik rumah pintar per ruangan dan per perangkat. Program menghitung konsumsi kWh, tagihan bulanan dengan **tarif progresif**, menentukan perangkat terboros, dan mensimulasikan penghematan ketika tagihan terlalu tinggi.

Proyek ini dibuat sebagai tugas pemrograman berorientasi objek (Studi Kasus 7: Sistem Monitoring Energi Rumah Pintar dengan Tarif Progresif).

## Fitur

- Menambah ruangan dan perangkat (nama, daya dalam watt, lama pakai per hari).
- Menyalakan dan mematikan perangkat. Perangkat yang mati tidak dihitung.
- Menghitung konsumsi kWh harian per perangkat, per ruangan, dan konsumsi bulanan (30 hari).
- Menghitung tagihan bulanan dengan tarif progresif dan pajak penerangan jalan (PJU) 5%.
- Menampilkan laporan berupa tabel per ruangan dan per perangkat.
- Menampilkan perangkat terboros.
- Memberi peringatan jika tagihan melebihi Rp 500.000, lengkap dengan simulasi penghematan bila jam pakai perangkat terboros dikurangi 20%.
- Validasi input: nilai harus positif dan jam pakai maksimal 24 jam.

## Rumus dan tarif

Konsumsi harian satu perangkat:

```
kWh harian = dayaWatt x jamPakaiPerHari / 1000
```

Konsumsi bulanan = total kWh harian seluruh perangkat yang menyala x 30.

| Blok pemakaian bulanan | Tarif per kWh |
|---|---|
| 0 - 100 kWh | Rp 1.200 |
| 101 - 200 kWh | Rp 1.500 |
| di atas 200 kWh | Rp 1.800 |

Tagihan = biaya semua blok + PJU 5%.

Contoh: pemakaian 562,5 kWh

```
100 x 1.200   = Rp   120.000
100 x 1.500   = Rp   150.000
362,5 x 1.800 = Rp   652.500
Subtotal      = Rp   922.500
PJU 5%        = Rp    46.125
Tagihan       = Rp   968.625
```

## Struktur program

```
.
├── Perangkat.java       # Data dan hitungan satu perangkat
├── Ruangan.java         # Kumpulan perangkat dalam satu ruangan
├── SistemEnergi.java    # Hitungan kWh, tagihan, perangkat terboros, laporan
└── MainSmartHome.java   # Program utama (menu, input, validasi, peringatan)
```

| Class | Atribut | Method utama |
|---|---|---|
| `Perangkat` | `nama`, `dayaWatt`, `jamPakaiPerHari`, `menyala` | `hitungKwhHarian()`, `nyalakan()`, `matikan()` |
| `Ruangan` | `nama`, `daftarPerangkat` | `tambahPerangkat()`, `hitungKwhRuangan()` |
| `SistemEnergi` | `daftarRuangan` | `hitungKwhBulanan()`, `hitungTagihan()`, `perangkatTerboros()`, `tampilkanLaporan()` |
| `MainSmartHome` | - | `main()` dengan menu berulang |

## Prasyarat

- JDK 11 atau lebih baru (cek dengan `java -version` dan `javac -version`).

## Cara menjalankan

Simpan keempat file `.java` dalam satu folder, lalu buka terminal di folder tersebut.

```bash
javac *.java
java MainSmartHome
```

Agar file `.class` tidak memenuhi folder, hasil kompilasi bisa diarahkan ke folder lain.

Linux / macOS:

```bash
javac -d out *.java
java -cp out MainSmartHome
```

Windows (PowerShell):

```powershell
javac -d "$env:TEMP\smarthome_out" *.java
java -cp "$env:TEMP\smarthome_out" MainSmartHome
```

## Cara penggunaan

Setelah program berjalan, ketik angka menu lalu tekan Enter.

```
=== SMART HOME ENERGY MONITOR ===
1. Tambah ruangan
2. Tambah perangkat
3. Nyalakan/matikan perangkat
4. Lihat laporan
5. Keluar
```

| Menu | Input yang diminta |
|---|---|
| 1 | Nama ruangan |
| 2 | Nama ruangan (harus sudah ada), nama perangkat, daya (watt), jam pakai per hari (maks 24) |
| 3 | Nama ruangan, nama perangkat, lalu `1` untuk menyalakan atau `2` untuk mematikan |
| 4 | Tidak ada, laporan langsung tampil |
| 5 | Tidak ada, program berhenti |

Contoh urutan input:

```
1
Kamar
1
Ruang Tamu
2
Kamar
AC
1500
12
2
Ruang Tamu
TV
150
5
4
5
```

## Contoh output

```
======================================================================================
                      LAPORAN KONSUMSI ENERGI RUMAH PINTAR
======================================================================================

Ruangan: Kamar
--------------------------------------------------------------------------------------
Perangkat                Daya (W)     Jam/hari     Status     kWh/hari
--------------------------------------------------------------------------------------
AC                         1500.0         12.0      Nyala       18.000
--------------------------------------------------------------------------------------
Total ruangan (kWh/hari)                                        18.000

Ruangan: Ruang Tamu
--------------------------------------------------------------------------------------
Perangkat                Daya (W)     Jam/hari     Status     kWh/hari
--------------------------------------------------------------------------------------
TV                          150.0          5.0      Nyala        0.750
--------------------------------------------------------------------------------------
Total ruangan (kWh/hari)                                         0.750

======================================================================================
Total konsumsi bulanan : 562.50 kWh
Tagihan (termasuk PJU 5%) : Rp 968.625
Perangkat terboros     : AC (18.000 kWh/hari)
======================================================================================

*** PERINGATAN: tagihan melebihi Rp 500.000 ***
Simulasi: kurangi pemakaian AC sebesar 20% (12.0 jam -> 9.6 jam/hari)
  Konsumsi bulanan : 562.50 kWh -> 454.50 kWh
  Tagihan          : Rp 968.625 -> Rp 764.505
  Penghematan      : Rp 204.120 per bulan
```

## Catatan desain

- Perangkat yang baru ditambahkan otomatis berstatus menyala.
- Simulasi penghematan hanya menghitung ulang, tidak mengubah data jam pakai yang tersimpan.
- Perangkat terboros dipilih dari perangkat yang sedang menyala.
- Nama ruangan dan perangkat tidak peka huruf besar-kecil.
