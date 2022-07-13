package co.kr.woowahan_repo.domain

import kotlin.random.Random

data class GithubIssueModel(
    val repositoryName: String,
    val issueTitle: String,
    val state: String,
    val lastUpdateDate: String // 2011-01-26T19:14:43Z, "yyyy-MM-dd'T'HH:mm:ss'Z'"
) {
    companion object {
        fun getDummyDate(): String{
            val example1 = "2011-01-26T19:14:43Z"
            val example2 = "2022-01-26T19:14:43Z"
            val example3 = "2022-06-26T19:14:43Z"
            val examples = arrayOf(
                example1, example2, example3
            )
            return examples[Random.nextInt(examples.size)]
        }
    }
}