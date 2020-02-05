package jls.com.sippyog.Model;

public class Model_PegawaiOnDuty {
    Integer id_shift_fk, id_tiket_fk,id_transaksi_fk,id_pegawai_fk;
    String nama_pegawai;

    public Model_PegawaiOnDuty(Integer id_shift_fk, Integer id_tiket_fk, Integer id_transaksi_fk, Integer id_pegawai_fk, String nama_pegawai) {
        this.id_shift_fk = id_shift_fk;
        this.id_tiket_fk = id_tiket_fk;
        this.id_transaksi_fk = id_transaksi_fk;
        this.id_pegawai_fk = id_pegawai_fk;
        this.nama_pegawai = nama_pegawai;
    }

    public Integer getId_shift_fk() {
        return id_shift_fk;
    }

    public void setId_shift_fk(Integer id_shift_fk) {
        this.id_shift_fk = id_shift_fk;
    }

    public Integer getId_tiket_fk() {
        return id_tiket_fk;
    }

    public void setId_tiket_fk(Integer id_tiket_fk) {
        this.id_tiket_fk = id_tiket_fk;
    }

    public Integer getId_transaksi_fk() {
        return id_transaksi_fk;
    }

    public void setId_transaksi_fk(Integer id_transaksi_fk) {
        this.id_transaksi_fk = id_transaksi_fk;
    }

    public Integer getId_pegawai_fk() {
        return id_pegawai_fk;
    }

    public void setId_pegawai_fk(Integer id_pegawai_fk) {
        this.id_pegawai_fk = id_pegawai_fk;
    }

    public String getNama_pegawai() {
        return nama_pegawai;
    }

    public void setNama_pegawai(String nama_pegawai) {
        this.nama_pegawai = nama_pegawai;
    }
}
