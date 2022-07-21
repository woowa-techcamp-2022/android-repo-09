package co.kr.woowahan_repo.data.model.response

import com.google.gson.annotations.SerializedName

data class GithubNotificationAsReadResponse(
    @SerializedName("Status")
    val status: Int
) {
    fun toEntity(): Int {
        return this.status
    }
}
