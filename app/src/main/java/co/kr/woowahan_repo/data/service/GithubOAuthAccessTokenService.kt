package co.kr.woowahan_repo.data.service

import co.kr.woowahan_repo.data.model.request.OAuthAccessTokenRequest
import co.kr.woowahan_repo.data.model.response.OAuthAccessTokenResponse
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