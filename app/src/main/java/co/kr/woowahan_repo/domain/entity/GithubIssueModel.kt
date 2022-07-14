package co.kr.woowahan_repo.domain.entity

import kotlin.random.Random

data class GithubIssueModel(
    val repositoryName: String,
    val issueTitle: String,
    val state: IssueState,
    val lastUpdateDate: String // 2011-01-26T19:14:43Z, "yyyy-MM-dd'T'HH:mm:ss'Z'"
) {
    companion object {
        fun getDummy(state: IssueState, seed: Int? = null): GithubIssueModel {
            val seedValue = seed ?: Random.nextInt(100)
            return GithubIssueModel(
                "Repository Name[$seedValue]",
                "Issue Title[$seedValue]",
                if(state == IssueState.All) IssueState.values()[Random.nextInt(2)] else state,
                getDummyDate()
            )
        }
        private fun getDummyDate(): String{
            val example1 = "2011-01-26T19:14:43Z"
            val example2 = "2022-01-26T19:14:43Z"
            val example3 = "2022-06-26T19:14:43Z"
            val examples = arrayOf(
                example1, example2, example3
            )
            return examples[Random.nextInt(examples.size)]
        }
    }
    enum class IssueState(
        val key: String
    ){
        Open("open"),
        Closed("closed"),
        All("all"),
        Unknown("unknown");
        companion object {
            fun get(any: String): IssueState {
                values().forEach {
                    if(any == it.key || any == it.toString())
                        return it
                }
                return Unknown
            }
        }
    }
}