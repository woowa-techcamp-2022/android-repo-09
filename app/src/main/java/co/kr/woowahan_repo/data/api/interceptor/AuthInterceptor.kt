package co.kr.woowahan_repo.data.api.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val accessToken: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = when (accessToken) {
            "" -> {
                chain.request().newBuilder()
                    .build()
            }
            else -> {
                chain.request().newBuilder()
                    .addHeader("Accept", "application/vnd.github+json")
                    .addHeader("Authorization", "token $accessToken")
                    .build()
            }
        }
        return chain.proceed(request)
    }
}