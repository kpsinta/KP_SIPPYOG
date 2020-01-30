package jls.com.sippyog.Model;

public class Model_KendaraanKeluar {
    Integer id_transaksi, id_tiket_fk, id_kendaraan_fk;
    String waktu_transaksi, jenis_kendaraan, kode_tiket, waktu_masuk, waktu_keluar, no_plat, status_parkir, status_tiket;
    Double total_transaksi;

    public Model_KendaraanKeluar(Integer id_transaksi, Integer id_tiket_fk, Integer id_kendaraan_fk, String waktu_transaksi, String jenis_kendaraan, String kode_tiket, String waktu_masuk, String waktu_keluar, String no_plat, String status_parkir, String status_tiket, Double total_transaksi) {
        this.id_transaksi = id_transaksi;
        this.id_tiket_fk = id_tiket_fk;
        this.id_kendaraan_fk = id_kendaraan_fk;
        this.waktu_transaksi = waktu_transaksi;
        this.jenis_kendaraan = jenis_kendaraan;
        this.kode_tiket = kode_tiket;
        this.waktu_masuk = waktu_masuk;
        this.waktu_keluar = waktu_keluar;
        this.no_plat = no_plat;
        this.status_parkir = status_parkir;
        this.status_tiket = status_tiket;
        this.total_transaksi = total_transaksi;
    }

    public Integer getId_transaksi() {
        return id_transaksi;
    }

    public void setId_transaksi(Integer id_transaksi) {
        this.id_transaksi = id_transaksi;
    }

    public Integer getId_tiket_fk() {
        return id_tiket_fk;
    }

    public void setId_tiket_fk(Integer id_tiket_fk) {
        this.id_tiket_fk = id_tiket_fk;
    }

    public Integer getId_kendaraan_fk() {
        return id_kendaraan_fk;
    }

    public void setId_kendaraan_fk(Integer id_kendaraan_fk) {
        this.id_kendaraan_fk = id_kendaraan_fk;
    }

    public String getWaktu_transaksi() {
        return waktu_transaksi;
    }

    public void setWaktu_transaksi(String waktu_transaksi) {
        this.waktu_transaksi = waktu_transaksi;
    }

    public String getJenis_kendaraan() {
        return jenis_kendaraan;
    }

    public void setJenis_kendaraan(String jenis_kendaraan) {
        this.jenis_kendaraan = jenis_kendaraan;
    }

    public String getKode_tiket() {
        return kode_tiket;
    }

    public void setKode_tiket(String kode_tiket) {
        this.kode_tiket = kode_tiket;
    }

    public String getWaktu_masuk() {
        return waktu_masuk;
    }

    public void setWaktu_masuk(String waktu_masuk) {
        this.waktu_masuk = waktu_masuk;
    }

    public String getWaktu_keluar() {
        return waktu_keluar;
    }

    public void setWaktu_keluar(String waktu_keluar) {
        this.waktu_keluar = waktu_keluar;
    }

    public String getNo_plat() {
        return no_plat;
    }

    public void setNo_plat(String no_plat) {
        this.no_plat = no_plat;
    }

    public String getStatus_parkir() {
        return status_parkir;
    }

    public void setStatus_parkir(String status_parkir) {
        this.status_parkir = status_parkir;
    }

    public String getStatus_tiket() {
        return status_tiket;
    }

    public void setStatus_tiket(String status_tiket) {
        this.status_tiket = status_tiket;
    }

    public Double getTotal_transaksi() {
        return total_transaksi;
    }

    public void setTotal_transaksi(Double total_transaksi) {
        this.total_transaksi = total_transaksi;
    }
}
