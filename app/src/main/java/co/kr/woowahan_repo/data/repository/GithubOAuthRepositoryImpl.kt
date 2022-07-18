package co.kr.woowahan_repo.data.repository

import co.kr.woowahan_repo.BuildConfig
import co.kr.woowahan_repo.data.model.request.OAuthAccessTokenRequest
import co.kr.woowahan_repo.data.service.GithubOAuthAccessTokenService
import co.kr.woowahan_repo.di.ServiceLocator
import co.kr.woowahan_repo.domain.repository.GithubOAuthRepository
import co.kr.woowahan_repo.domain.model.OAuthAccessTokenInfo

class GithubOAuthRepositoryImpl: GithubOAuthRepository {
    private val githubAccessTokenService: GithubOAuthAccessTokenService = ServiceLocator.getOAuthAccessTokenService()

    override fun getOAuthActionViewUrl(clientId: String, scope: Array<String>): Result<String> {
        return kotlin.runCatching {
            var url =  BuildConfig.GITHUB_OAUTH_BASE_URL +
                    "login/oauth/authorize" +
                    "?client_id=$clientId"
            if(scope.isNotEmpty()){
                url += "&scope="
                scope.forEachIndexed { i, v ->
                    url += if(i==scope.size-1)
                        v
                    else
                        "${v},"
                }
            }
            url
        }
    }

    override suspend fun requestAccessToken(
        clientId: String,
        clientSecret: String,
        code: String
    ): Result<OAuthAccessTokenInfo> {
        return kotlin.runCatching {
            githubAccessTokenService.requestAccessToken(
                OAuthAccessTokenRequest(
                    clientId, clientSecret, code
                )
            ).toEntity()
        }
    }
}