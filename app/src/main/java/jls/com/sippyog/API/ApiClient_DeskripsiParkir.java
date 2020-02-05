package jls.com.sippyog.API;

import jls.com.sippyog.ListData.LD_DeskripsiParkir;
import jls.com.sippyog.Model.Model_DeskripsiParkir;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiClient_DeskripsiParkir {

    String baseURL = "http://sippyog.jasonfw.com/";

    // --------------------- C R E A T E --------------------- //

    @POST("api/deskripsi")
    @FormUrlEncoded
    Call<Model_DeskripsiParkir> create(
            @Field("waktu_operasional")String waktu_operasional,
            @Field("alamat_tkp")String alamat_tkp,
            @Field("nomor_telepon")String nomor_telepon,
            @Field("deskripsi")String deskripsi);

    // --------------------- C R E A T E --------------------- //

    // ----------------------- R E A D ----------------------- //

    @GET("api/deskripsi/show")
    Call<LD_DeskripsiParkir> show();

    // ----------------------- R E A D ----------------------- //

    // --------------------- U P D A T E --------------------- //

    @PUT("api/deskripsi/{id_deskripsi_kendaraan}")
    @FormUrlEncoded
    Call<ResponseBody>update(
            @Field("waktu_operasional")String waktu_operasional,
            @Field("alamat_tkp")String alamat_tkp,
            @Field("nomor_telepon")String nomor_telepon,
            @Field("deskripsi")String deskripsi,
            @Path("id_deskripsi_kendaraan")Integer id_deskripsi_kendaraan);

    // --------------------- U P D A T E --------------------- //

    // --------------------- D E L E T E --------------------- //

    @DELETE("api/deskripsi/{id_deskripsi_kendaraan}")
    Call<ResponseBody>delete(@Path("id_deskripsi_kendaraan") Integer id_deskripsi_kendaraan);

    // --------------------- D E L E T E --------------------- //
}
