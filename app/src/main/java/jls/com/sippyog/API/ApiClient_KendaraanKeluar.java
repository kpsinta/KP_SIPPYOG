package jls.com.sippyog.API;

import jls.com.sippyog.ListData.LD_KendaraanKeluar;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiClient_KendaraanKeluar {

    String baseURL = "http://sippyog.jasonfw.com/";
    // --------------------- C R E A T E --------------------- //

    @POST("api/transaksi")
    @FormUrlEncoded
    Call<ResponseBody> create(
            @Field("id_tiket_fk")Integer id_tiket_fk,
            @Field("waktu_transaksi")String waktu_transaksi,
            @Field("total_transaksi")Double total_transaksi,
            @Field("status_tiket")String status_tiket);

    @POST("api/pegawaionduty/create_kendaraan_keluar")
    @FormUrlEncoded
    Call<ResponseBody> create_kendaraan_keluar(
            @Field("id_tiket_fk")Integer id_tiket_fk,
            @Field("id_shift_fk")Integer id_shift_fk,
            @Field("id_pegawai_fk")Integer id_pegawai_fk,
            @Field("id_transaksi_fk")Integer id_transaksi_fk);


    // --------------------- C R E A T E --------------------- //
    // ----------------------- R E A D ----------------------- //

    @GET("api/transaksi/show")
    Call<LD_KendaraanKeluar> show();

    @GET("api/transaksi/showToday")
    Call<LD_KendaraanKeluar> showToday();

    // ----------------------- R E A D ----------------------- //
}
