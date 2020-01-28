package jls.com.sippyog.API;

import jls.com.sippyog.ListData.LD_KendaraanKeluar;
import jls.com.sippyog.ListData.LD_KendaraanMasuk;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiClient_Laporan {
    String baseURL = "http://sippyog.jasonfw.com/";

    // ----------------------- R E A D ----------------------- //
    // --------- Show Jumlah Kendaraan & Pendapatan  --------- //

    @GET("api/laporan/showTransaksiAll_Harian/{waktu_transaksi}")
    Call<LD_KendaraanKeluar> showTransaksiAll_Harian(
            @Path("waktu_transaksi")String waktu_transaksi);

    @GET("api/laporan/showTransaksiAll_Bulanan/{waktu_transaksi}")
    Call<LD_KendaraanKeluar> showTransaksiAll_Bulanan(
            @Path("waktu_transaksi")String waktu_transaksi);

    @GET("api/laporan/showTransaksiAll_Tahunan/{waktu_transaksi}")
    Call<LD_KendaraanKeluar> showTransaksiAll_Tahunan(
            @Path("waktu_transaksi")String waktu_transaksi);

    // ----------------- Show By Status Tiket  ---------------- //

    @GET("api/laporan/showByStatusTiketAll_Harian/{status}/{waktu_keluar}")
    Call<LD_KendaraanMasuk> showByStatusTiketAll_Harian(
            @Path("status")String status,
            @Path("waktu_keluar")String waktu_keluar);

    @GET("api/laporan/showByStatusTiketAll_Bulanan/{status}/{waktu_keluar}")
    Call<LD_KendaraanMasuk> showByStatusTiketAll_Bulanan(
            @Path("status")String status,
            @Path("waktu_keluar")String waktu_keluar);

    @GET("api/laporan/showByStatusTiketAll_Tahunan/{status}/{waktu_keluar}")
    Call<LD_KendaraanMasuk> showByStatusTiketAll_Tahunan(
            @Path("status")String status,
            @Path("waktu_keluar")String waktu_keluar);
    // ----------------------- R E A D ----------------------- //
}
