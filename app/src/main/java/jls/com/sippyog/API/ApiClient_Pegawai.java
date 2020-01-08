package jls.com.sippyog.API;

import jls.com.sippyog.ListData.LD_Pegawai;
import jls.com.sippyog.Model.Model_Pegawai;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiClient_Pegawai {

    String baseURL = "http://sippyog.000webhostapp.com/";

    // --------------------- C R E A T E --------------------- //

    @POST("api/pegawai")
    @FormUrlEncoded
    Call<Model_Pegawai> create(
            @Field("id_role_fk")Integer id_role_fk,
            @Field("nama_pegawai")String nama_pegawai,
            @Field("nip_pegawai")String nip_pegawai,
            @Field("username_pegawai")String username_pegawai,
            @Field("password_pegawai")String password_pegawai);

    // --------------------- C R E A T E --------------------- //

    // ----------------------- R E A D ----------------------- //

    @GET("api/pegawai/show")
    Call<LD_Pegawai> show();

    @GET("api/pegawai/{id_pegawai}")
    Call<LD_Pegawai> showById(
            @Path("id_pegawai")Integer id_pegawai);

    // ----------------------- R E A D ----------------------- //

    // --------------------- U P D A T E --------------------- //

    @PUT("api/pegawai/{id_pegawai}")
    @FormUrlEncoded
    Call<ResponseBody>update(
            @Field("id_role_fk")Integer id_role_fk,
            @Field("nama_pegawai")String nama_pegawai,
            @Field("nip_pegawai")String nip_pegawai,
            @Field("username_pegawai")String username_pegawai,
            @Field("password_pegawai")String password_pegawai,
            @Path("id_pegawai")Integer id_pegawai);

    // --------------------- U P D A T E --------------------- //

    // --------------------- D E L E T E --------------------- //

    @DELETE("api/pegawai/{id_pegawai}")
    Call<ResponseBody>delete(@Path("id_pegawai") Integer id_pegawai);

    // --------------------- D E L E T E --------------------- //

}
