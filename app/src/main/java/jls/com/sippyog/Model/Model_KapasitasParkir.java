package jls.com.sippyog.Model;

public class Model_KapasitasParkir {
    Integer id_kapasitasParkir,jumlah_kapasitas;
    String jenis_kendaraan;
    Float biaya_parkir;

    public Model_KapasitasParkir(Integer id_kapasitasParkir, Integer jumlah_kapasitas, String jenis_kendaraan, Float biaya_parkir) {
        this.id_kapasitasParkir = id_kapasitasParkir;
        this.jumlah_kapasitas = jumlah_kapasitas;
        this.jenis_kendaraan = jenis_kendaraan;
        this.biaya_parkir = biaya_parkir;
    }

    public Integer getId_kapasitasParkir() {
        return id_kapasitasParkir;
    }

    public void setId_kapasitasParkir(Integer id_kapasitasParkir) {
        this.id_kapasitasParkir = id_kapasitasParkir;
    }

    public Integer getJumlah_kapasitas() {
        return jumlah_kapasitas;
    }

    public void setJumlah_kapasitas(Integer jumlah_kapasitas) {
        this.jumlah_kapasitas = jumlah_kapasitas;
    }

    public String getJenis_kendaraan() {
        return jenis_kendaraan;
    }

    public void setJenis_kendaraan(String jenis_kendaraan) {
        this.jenis_kendaraan = jenis_kendaraan;
    }

    public Float getBiaya_parkir() {
        return biaya_parkir;
    }

    public void setBiaya_parkir(Float biaya_parkir) {
        this.biaya_parkir = biaya_parkir;
    }
}
