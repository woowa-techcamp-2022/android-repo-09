package co.kr.woowahan_repo.domain.repository

import co.kr.woowahan_repo.domain.model.GithubProfileModel

interface GithubProfileRepository {
    suspend fun fetchProfileUrl(): Result<String>
    suspend fun fetchProfile(): Result<GithubProfileModel>
}