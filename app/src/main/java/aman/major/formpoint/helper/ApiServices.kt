package aman.major.formpoint.helper

import com.google.gson.JsonObject
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

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

    @GET("APIs/slider")
    fun slider() : Call<JsonObject>

    @GET("APIs/youtube")
    fun video() : Call<JsonObject>


    @GET("APIs/forms")
    fun getOnlineForms(
        @Query("type") type:String
    ): Call<JsonObject>

    @FormUrlEncoded
    @POST("APIs/get_form")
    fun getSingleFormData(
        @Field("form_id") id:String
    ):Call<JsonObject>

    @Multipart
    @POST("APIs/edit_profile")
    fun updateProfile(
        @Part("name") name: RequestBody,
        @Part profile: MultipartBody.Part,
        @Part("email") email: RequestBody,
        @Part("user_id") user_id: RequestBody
    ): Call<JsonObject>

    @Multipart
    @POST("APIs/upload_document")
    fun uploadDocument(
        @Part("user_id") userId: RequestBody,
        @Part photo: MultipartBody.Part,
        @Part aadhar: MultipartBody.Part,
        @Part signature: MultipartBody.Part,
        @Part marksheeet: MultipartBody.Part,
        @Part incomeCertificate: MultipartBody.Part
    ): Call<JsonObject>

    @GET("APIs/notification")
    fun getNotifications(
    ): Call<JsonObject>

    @GET("APIs/document_fields")
    fun getDocumentField(
    ): Call<JsonObject>

    @GET("APIs/all_formname")
    fun getAllFormsName(
    ): Call<JsonObject>




}