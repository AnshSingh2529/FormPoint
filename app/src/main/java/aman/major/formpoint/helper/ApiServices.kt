package aman.major.formpoint.helper

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiServices {
    @FormUrlEncoded
    @POST("APIs/registration")
    fun registration(
        @Field("username") username: String,
        @Field("email") email: String,
        @Field("mobile") mobile: String,
        @Field("password") password: String,
        @Field("confirm_password") cnfPass: String,
    ): Call<JsonObject>

    @FormUrlEncoded
    @POST("APIs/login")
    fun login(
        @Field("mobile") mobile: String,
        @Field("password") password: String,
    ): Call<JsonObject>

}