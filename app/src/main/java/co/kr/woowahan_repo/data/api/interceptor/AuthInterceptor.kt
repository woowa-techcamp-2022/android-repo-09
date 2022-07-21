package co.kr.woowahan_repo.data.api.interceptor

import co.kr.woowahan_repo.domain.GithubTokenDataSource
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val githubTokenDataSource: GithubTokenDataSource
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = when (githubTokenDataSource.fetchToken()) {
            "" -> {
                chain.request().newBuilder()
                    .build()
            }
            else -> {
                chain.request().newBuilder()
                    .addHeader("Accept", "application/vnd.github+json")
                    .addHeader("Authorization", "token ${githubTokenDataSource.fetchToken()}")
                    .build()
            }
        }
        return chain.proceed(request)
    }
}