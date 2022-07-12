package co.kr.woowahan_repo.data.service

import co.kr.woowahan_repo.data.model.OAuthAccessTokenRequest
import co.kr.woowahan_repo.data.model.OAuthAccessTokenResponse
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface GithubOAuthAccessTokenService {
    @Headers("Accept: application/json")
    @POST("login/oauth/access_token")
    suspend fun requestAccessToken(
        @Body body: OAuthAccessTokenRequest
    ): OAuthAccessTokenResponse
}