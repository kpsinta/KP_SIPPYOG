package jls.com.sippyog.API;

import jls.com.sippyog.ListData.LD_Kendaraan;
import jls.com.sippyog.ListData.LD_KendaraanMasuk;
import jls.com.sippyog.ListData.LD_PengawaiOnDuty;
import jls.com.sippyog.Model.Model_Kendaraan;
import jls.com.sippyog.Model.Model_KendaraanMasuk;
import jls.com.sippyog.Model.Model_PegawaiOnDuty;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiClient_KendaraanMasuk {

    String baseURL = "http://sippyog.jasonfw.com/";

    // --------------------- C R E A T E --------------------- //

    @POST("api/tiket")
    @FormUrlEncoded
    Call<ResponseBody> create(
            @Field("waktu_masuk")String waktu_masuk,
            @Field("no_plat")String no_plat,
            @Field("id_kendaraan_fk")Integer id_kendaraan_fk);

    @POST("api/pegawaionduty/create_kendaraan_masuk")
    @FormUrlEncoded
    Call<ResponseBody> create_pod_kendaraan_masuk(
            @Field("id_tiket_fk")Integer id_tiket_fk,
            @Field("id_shift_fk")Integer id_shift_fk,
            @Field("id_pegawai_fk")Integer id_pegawai_fk);


    // --------------------- C R E A T E --------------------- //

    // ----------------------- R E A D ----------------------- //

    @GET("api/tiket/show")
    Call<LD_KendaraanMasuk> show();

    @GET("api/tiket/{id_tiket}")
    Call<LD_KendaraanMasuk> showById(
            @Path("id_tiket")Integer id_tiket);
    @GET("api/pegawaionduty/showByIdTiket/{id}")
    Call<LD_PengawaiOnDuty> showByIdTiket(
            @Path("id")Integer id);
    @GET("api/tiket/showByStatusParkir/{status}")
    Call<LD_KendaraanMasuk> showByStatusParkir(
            @Path("status")Integer status);

    @GET("api/tiket/showByStatusTiket/{status}")
    Call<LD_KendaraanMasuk> showByStatusTiket(
            @Path("status")Integer status);

    @GET("api/tiket/showByKendaraanWhereStatusSedangParkir/{id}")
    Call<LD_KendaraanMasuk> showByKendaraanWhereStatusSedangParkir(
            @Path("id")Integer id);
    // ----------------------- R E A D ----------------------- //
}
