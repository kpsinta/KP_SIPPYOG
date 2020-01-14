package jls.com.sippyog.API;

import jls.com.sippyog.ListData.LD_Kendaraan;
import jls.com.sippyog.ListData.LD_KendaraanMasuk;
import jls.com.sippyog.Model.Model_Kendaraan;
import jls.com.sippyog.Model.Model_KendaraanMasuk;
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
    Call<Model_KendaraanMasuk> create(
            @Field("waktu_masuk")String waktu_masuk,
            @Field("no_plat")String no_plat,
            @Field("id_kendaraan_fk")Integer id_kendaraan_fk);

    // --------------------- C R E A T E --------------------- //

    // ----------------------- R E A D ----------------------- //

    @GET("api/tiket/show")
    Call<LD_KendaraanMasuk> show();

    @GET("api/tiket/{id_tiket}")
    Call<LD_KendaraanMasuk> showById(
            @Path("id_tiket")Integer id_tiket);

    // ----------------------- R E A D ----------------------- //
}
