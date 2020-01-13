package jls.com.sippyog.API;

import java.sql.Time;

import jls.com.sippyog.ListData.LD_Shift;
import jls.com.sippyog.Model.Model_Shift;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiClient_Shift {String baseURL = "http://sippyog.jasonfw.com/";

    // --------------------- C R E A T E --------------------- //

    @POST("api/shift")
    @FormUrlEncoded
    Call<Model_Shift> create(
            @Field("nama_shift")String nama_shift,
            @Field("jam_masuk")String jam_masuk,
            @Field("jam_keluar")String jam_keluar);

    // --------------------- C R E A T E --------------------- //

    // ----------------------- R E A D ----------------------- //

    @GET("api/shift/show")
    Call<LD_Shift> show();

    @GET("api/shift/{id_shift}")
    Call<LD_Shift> showById(
            @Path("id_shift")Integer id_shift);

    // ----------------------- R E A D ----------------------- //

    // --------------------- U P D A T E --------------------- //

    @PUT("api/shift/{id_shift}")
    @FormUrlEncoded
    Call<ResponseBody>update(
            @Field("nama_shift")String nama_shift,
            @Field("jam_masuk")String jam_masuk,
            @Field("jam_keluar")String jam_keluar,
            @Path("id_shift")Integer id_shift);

    // --------------------- U P D A T E --------------------- //

    // --------------------- D E L E T E --------------------- //

    @DELETE("api/shift/{id_shift}")
    Call<ResponseBody>delete(@Path("id_shift") Integer id_shift);

    // --------------------- D E L E T E --------------------- //
}