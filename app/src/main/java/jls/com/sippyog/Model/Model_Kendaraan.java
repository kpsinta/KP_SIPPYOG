package jls.com.sippyog.Model;

public class Model_Kendaraan {
    Integer id_kendaraan,kapasitas_maksimum;
    String jenis_kendaraan;
    Double biaya_parkir,biaya_denda;

    public Model_Kendaraan(Integer id_kendaraan, Integer kapasitas_maksimum, String jenis_kendaraan, Double biaya_parkir, Double biaya_denda) {
        this.id_kendaraan = id_kendaraan;
        this.kapasitas_maksimum = kapasitas_maksimum;
        this.jenis_kendaraan = jenis_kendaraan;
        this.biaya_parkir = biaya_parkir;
        this.biaya_denda = biaya_denda;
    }

    public Integer getId_kendaraan() {
        return id_kendaraan;
    }

    public void setId_kendaraan(Integer id_kendaraan) {
        this.id_kendaraan = id_kendaraan;
    }

    public Integer getKapasitas_maksimum() {
        return kapasitas_maksimum;
    }

    public void setKapasitas_maksimum(Integer kapasitas_maksimum) {
        this.kapasitas_maksimum = kapasitas_maksimum;
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
