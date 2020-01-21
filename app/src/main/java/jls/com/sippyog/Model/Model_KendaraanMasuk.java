package jls.com.sippyog.Model;

public class Model_KendaraanMasuk {
    Integer id_tiket, id_kendaraan_fk;
    String kode_tiket, waktu_masuk, waktu_keluar, no_plat, status_parkir, status_tiket, jenis_kendaraan;
    Double biaya_parkir, biaya_denda;

    public Model_KendaraanMasuk(Integer id_tiket, Integer id_kendaraan_fk, String kode_tiket, String waktu_masuk, String waktu_keluar, String no_plat, String status_parkir, String status_tiket, String jenis_kendaraan, Double biaya_parkir, Double biaya_denda) {
        this.id_tiket = id_tiket;
        this.id_kendaraan_fk = id_kendaraan_fk;
        this.kode_tiket = kode_tiket;
        this.waktu_masuk = waktu_masuk;
        this.waktu_keluar = waktu_keluar;
        this.no_plat = no_plat;
        this.status_parkir = status_parkir;
        this.status_tiket = status_tiket;
        this.jenis_kendaraan = jenis_kendaraan;
        this.biaya_parkir = biaya_parkir;
        this.biaya_denda = biaya_denda;
    }

    public Integer getId_tiket() {
        return id_tiket;
    }

    public void setId_tiket(Integer id_tiket) {
        this.id_tiket = id_tiket;
    }

    public Integer getId_kendaraan_fk() {
        return id_kendaraan_fk;
    }

    public void setId_kendaraan_fk(Integer id_kendaraan_fk) {
        this.id_kendaraan_fk = id_kendaraan_fk;
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

    public String getJenis_kendaraan() {
        return jenis_kendaraan;
    }

    public void setJenis_kendaraan(String jenis_kendaraan) {
        this.jenis_kendaraan = jenis_kendaraan;
    }

    public Double getBiaya_parkir() {
        return biaya_parkir;
    }

    public void setBiaya_parkir(Double biaya_parkir) {
        this.biaya_parkir = biaya_parkir;
    }

    public Double getBiaya_denda() {
        return biaya_denda;
    }

    public void setBiaya_denda(Double biaya_denda) {
        this.biaya_denda = biaya_denda;
    }
}

