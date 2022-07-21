package co.kr.woowahan_repo.data.model.response

import co.kr.woowahan_repo.domain.model.OAuthAccessTokenInfo
import com.google.gson.annotations.SerializedName

data class OAuthAccessTokenResponse(
    @SerializedName("access_token")
    val accessToken: String,
    val scope: String,
    @SerializedName("token_type")
    val tokenType: String
){
    fun toEntity(): OAuthAccessTokenInfo {
        return OAuthAccessTokenInfo(
            accessToken = accessToken,
            scope = scope,
            tokenType = tokenType
        )
    }
}
