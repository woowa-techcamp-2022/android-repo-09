package co.kr.woowahan_repo.data.api.interceptor

import co.kr.woowahan_repo.di.ServiceLocator
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = when (ServiceLocator.accessToken) {
            "" -> {
                chain.request().newBuilder()
                    .build()
            }
            else -> {
                chain.request().newBuilder()
                    .addHeader("Authorization", ServiceLocator.accessToken)
                    .build()
            }
        }
        return chain.proceed(request)
    }
}