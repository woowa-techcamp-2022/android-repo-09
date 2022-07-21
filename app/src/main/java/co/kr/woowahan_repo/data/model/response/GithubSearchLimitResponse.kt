package co.kr.woowahan_repo.data.model.response


import com.google.gson.annotations.SerializedName

data class GithubSearchLimitResponse(
    @SerializedName("rate")
    val rate: Rate,
    @SerializedName("resources")
    val resources: Resources
) {
    fun getSearchLimit(): Int = resources.search.remaining

    data class Rate(
        @SerializedName("limit")
        val limit: Int,
        @SerializedName("remaining")
        val remaining: Int,
        @SerializedName("reset")
        val reset: Int,
        @SerializedName("used")
        val used: Int
    )

    data class Resources(
        @SerializedName("code_scanning_upload")
        val codeScanningUpload: CodeScanningUpload,
        @SerializedName("core")
        val core: Core,
        @SerializedName("graphql")
        val graphql: Graphql,
        @SerializedName("integration_manifest")
        val integrationManifest: IntegrationManifest,
        @SerializedName("search")
        val search: Search
    ) {
        data class CodeScanningUpload(
            @SerializedName("limit")
            val limit: Int,
            @SerializedName("remaining")
            val remaining: Int,
            @SerializedName("reset")
            val reset: Int,
            @SerializedName("used")
            val used: Int
        )

        data class Core(
            @SerializedName("limit")
            val limit: Int,
            @SerializedName("remaining")
            val remaining: Int,
            @SerializedName("reset")
            val reset: Int,
            @SerializedName("used")
            val used: Int
        )

        data class Graphql(
            @SerializedName("limit")
            val limit: Int,
            @SerializedName("remaining")
            val remaining: Int,
            @SerializedName("reset")
            val reset: Int,
            @SerializedName("used")
            val used: Int
        )

        data class IntegrationManifest(
            @SerializedName("limit")
            val limit: Int,
            @SerializedName("remaining")
            val remaining: Int,
            @SerializedName("reset")
            val reset: Int,
            @SerializedName("used")
            val used: Int
        )

        data class Search(
            @SerializedName("limit")
            val limit: Int,
            @SerializedName("remaining")
            val remaining: Int,
            @SerializedName("reset")
            val reset: Int,
            @SerializedName("used")
            val used: Int
        )
    }
}