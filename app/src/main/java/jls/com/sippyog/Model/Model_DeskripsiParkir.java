package jls.com.sippyog.Model;

public class Model_DeskripsiParkir {
    Integer id_deskripsi_parkir;
    String waktu_operasional, alamat_tkp, nomor_telepon, deskripsi;

    public Model_DeskripsiParkir(Integer id_deskripsi_parkir, String waktu_operasional, String alamat_tkp, String nomor_telepon, String deskripsi) {
        this.id_deskripsi_parkir = id_deskripsi_parkir;
        this.waktu_operasional = waktu_operasional;
        this.alamat_tkp = alamat_tkp;
        this.nomor_telepon = nomor_telepon;
        this.deskripsi = deskripsi;
    }

    public Integer getId_deskripsi_parkir() {
        return id_deskripsi_parkir;
    }

    public void setId_deskripsi_parkir(Integer id_deskripsi_parkir) {
        this.id_deskripsi_parkir = id_deskripsi_parkir;
    }

    public String getWaktu_operasional() {
        return waktu_operasional;
    }

    public void setWaktu_operasional(String waktu_operasional) {
        this.waktu_operasional = waktu_operasional;
    }

    public String getAlamat_tkp() {
        return alamat_tkp;
    }

    public void setAlamat_tkp(String alamat_tkp) {
        this.alamat_tkp = alamat_tkp;
    }

    public String getNomor_telepon() {
        return nomor_telepon;
    }

    public void setNomor_telepon(String nomor_telepon) {
        this.nomor_telepon = nomor_telepon;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }
}
