package co.kr.woowahan_repo.data.repository

import co.kr.woowahan_repo.domain.model.GithubProfileModel

interface GithubProfileRepository {
    fun fetchProfileUrl(): Result<String>
    fun fetchProfile(): Result<GithubProfileModel>
}