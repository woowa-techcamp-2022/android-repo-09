package co.kr.woowahan_repo.data.model.response

import co.kr.woowahan_repo.domain.model.GithubComments
import com.google.gson.annotations.SerializedName

data class GithubCommentsResponse(
    @SerializedName("review_comments")
    val reviewComments: Int,
    val comments: Int
) {
    fun toEntity(): GithubComments {
        return GithubComments(
            reviewComments = this.reviewComments,
            comments = this.comments
        )
    }
}
