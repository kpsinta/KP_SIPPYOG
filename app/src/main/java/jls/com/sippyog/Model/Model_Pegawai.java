package jls.com.sippyog.Model;

public class Model_Pegawai {
    Integer id_pegawai, id_role_fk;
    String nama_pegawai, nip_pegawai, username_pegawai, password_pegawai;

    public Model_Pegawai(int id_pegawai, int id_role_fk, String nama_pegawai, String nip_pegawai, String username_pegawai, String password_pegawai) {
        this.id_pegawai = id_pegawai;
        this.id_role_fk = id_role_fk;
        this.nama_pegawai = nama_pegawai;
        this.nip_pegawai = nip_pegawai;
        this.username_pegawai = username_pegawai;
        this.password_pegawai = password_pegawai;
    }

    public Integer getId_pegawai() {
        return id_pegawai;
    }

    public void setId_pegawai(int id_pegawai) {
        this.id_pegawai = id_pegawai;
    }

    public int getId_role_fk() {
        return id_role_fk;
    }

    public void setId_role_fk(int id_role_fk) {
        this.id_role_fk = id_role_fk;
    }

    public String getNama_pegawai() {
        return nama_pegawai;
    }

    public void setNama_pegawai(String nama_pegawai) {
        this.nama_pegawai = nama_pegawai;
    }

    public String getNip_pegawai() {
        return nip_pegawai;
    }

    public void setNip_pegawai(String nip_pegawai) {
        this.nip_pegawai = nip_pegawai;
    }

    public String getUsername_pegawai() {
        return username_pegawai;
    }

    public void setUsername_pegawai(String username_pegawai) {
        this.username_pegawai = username_pegawai;
    }

    public String getPassword_pegawai() {
        return password_pegawai;
    }

    public void setPassword_pegawai(String password_pegawai) {
        this.password_pegawai = password_pegawai;
    }
}
