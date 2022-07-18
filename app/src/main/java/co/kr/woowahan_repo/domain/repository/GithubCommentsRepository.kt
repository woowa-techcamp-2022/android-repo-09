package co.kr.woowahan_repo.domain.repository

import co.kr.woowahan_repo.domain.model.GithubComments

interface GithubCommentsRepository {
    suspend fun fetchComments(url: String): Result<GithubComments>
}