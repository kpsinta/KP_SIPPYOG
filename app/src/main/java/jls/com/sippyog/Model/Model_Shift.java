package jls.com.sippyog.Model;

public class Model_Shift {
    Integer id_shift;
    String nama_shift, jam_masuk, jam_keluar;

    public Model_Shift(Integer id_shift, String nama_shift, String jam_masuk, String jam_keluar) {
        this.id_shift = id_shift;
        this.nama_shift = nama_shift;
        this.jam_masuk = jam_masuk;
        this.jam_keluar = jam_keluar;
    }

    public Integer getId_shift() {
        return id_shift;
    }

    public void setId_shift(Integer id_shift) {
        this.id_shift = id_shift;
    }

    public String getNama_shift() {
        return nama_shift;
    }

    public void setNama_shift(String nama_shift) {
        this.nama_shift = nama_shift;
    }

    public String getJam_masuk() {
        return jam_masuk;
    }

    public void setJam_masuk(String jam_masuk) {
        this.jam_masuk = jam_masuk;
    }

    public String getJam_keluar() {
        return jam_keluar;
    }

    public void setJam_keluar(String jam_keluar) {
        this.jam_keluar = jam_keluar;
    }
}
