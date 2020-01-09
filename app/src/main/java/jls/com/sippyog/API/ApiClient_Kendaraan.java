package jls.com.sippyog.API;

import jls.com.sippyog.ListData.LD_Kendaraan;
import jls.com.sippyog.Model.Model_Kendaraan;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiClient_Kendaraan {

    String baseURL = "http://sippyog.jasonfw.com/";

    // --------------------- C R E A T E --------------------- //

    @POST("api/kendaraan")
    @FormUrlEncoded
    Call<Model_Kendaraan> create(
            @Field("jenis_kendaraan")String jenis_kendaraan,
            @Field("kapasitas_maksimum")Integer kapasitas_maksimum,
            @Field("biaya_parkir")String biaya_parkir,
            @Field("biaya_denda")String biaya_denda);

    // --------------------- C R E A T E --------------------- //

    // ----------------------- R E A D ----------------------- //

    @GET("api/kendaraan/show")
    Call<LD_Kendaraan> show();

    @GET("api/kendaraan/{id_kendaraan}")
    Call<LD_Kendaraan> showById(
            @Path("id_kendaraan")Integer id_kendaraan);

    // ----------------------- R E A D ----------------------- //

    // --------------------- U P D A T E --------------------- //

    @PUT("api/kendaraan/{id_kendaraan}")
    @FormUrlEncoded
    Call<ResponseBody>update(
            @Field("jenis_kendaraan")String jenis_kendaraan,
            @Field("kapasitas_maksimum")Integer kapasitas_maksimum,
            @Field("biaya_parkir")Double biaya_parkir,
            @Field("biaya_denda")Double biaya_denda,
            @Path("id_kendaraan")Integer id_kendaraan);

    // --------------------- U P D A T E --------------------- //

    // --------------------- D E L E T E --------------------- //

    @DELETE("api/kendaraan/{id_kendaraan}")
    Call<ResponseBody>delete(@Path("id_kendaraan") Integer id_kendaraan);

    // --------------------- D E L E T E --------------------- //
}
