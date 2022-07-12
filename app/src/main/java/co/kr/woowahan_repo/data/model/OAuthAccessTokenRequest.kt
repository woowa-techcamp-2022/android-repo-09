package co.kr.woowahan_repo.data.model

import com.google.gson.annotations.SerializedName

data class OAuthAccessTokenRequest(
    @SerializedName("client_id")
    val clientId: String,
    @SerializedName("client_secret")
    val clientSecret: String,
    val code: String
)
