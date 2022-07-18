package co.kr.woowahan_repo.data.model.response

import co.kr.woowahan_repo.domain.model.GithubNotification
import com.google.gson.annotations.SerializedName

data class GithubNotificationResponse(
    val id: String,
    @SerializedName("last_read_at")
    val lastReadAt: String,
    val reason: String,
    val repository: Repository,
    val subject: Subject,
    @SerializedName("subscription_url")
    val subscriptionUrl: String,
    val unread: Boolean,
    @SerializedName("updated_at")
    val updatedAt: String,
    val url: String
) {
    data class Repository(
        @SerializedName("archive_url")
        val archiveUrl: String,
        @SerializedName("assignees_url")
        val assigneesUrl: String,
        @SerializedName("blobs_url")
        val blobsUrl: String,
        @SerializedName("branches_url")
        val branchesUrl: String,
        @SerializedName("collaborators_url")
        val collaboratorsUrl: String,
        @SerializedName("comments_url")
        val commentsUrl: String,
        @SerializedName("commits_url")
        val commitsUrl: String,
        @SerializedName("compare_url")
        val compareUrl: String,
        @SerializedName("contents_url")
        val contentsUrl: String,
        @SerializedName("contributors_url")
        val contributorsUrl: String,
        @SerializedName("deployments_url")
        val deploymentsUrl: String,
        val description: String,
        @SerializedName("downloads_url")
        val downloadsUrl: String,
        @SerializedName("events_url")
        val eventsUrl: String,
        val fork: Boolean,
        @SerializedName("forks_url")
        val forksUrl: String,
        @SerializedName("full_name")
        val fullName: String,
        @SerializedName("git_commits_url")
        val gitCommitsUrl: String,
        @SerializedName("git_refs_url")
        val gitRefsUrl: String,
        @SerializedName("git_tags_url")
        val gitTagsUrl: String,
        @SerializedName("git_url")
        val gitUrl: String,
        @SerializedName("hooks_url")
        val hooksUrl: String,
        @SerializedName("html_url")
        val htmlUrl: String,
        val id: Long,
        @SerializedName("issue_comment_url")
        val issueCommentUrl: String,
        @SerializedName("issue_events_url")
        val issueEventsUrl: String,
        @SerializedName("issues_url")
        val issuesUrl: String,
        @SerializedName("keys_url")
        val keysUrl: String,
        @SerializedName("labels_url")
        val labelsUrl: String,
        @SerializedName("languages_url")
        val languagesUrl: String,
        @SerializedName("merges_url")
        val mergesUrl: String,
        @SerializedName("milestones_url")
        val milestonesUrl: String,
        val name: String,
        @SerializedName("node_id")
        val nodeId: String,
        @SerializedName("notifications_url")
        val notificationsUrl: String,
        val owner: Owner,
        val private: Boolean,
        @SerializedName("pulls_url")
        val pullsUrl: String,
        @SerializedName("releases_url")
        val releasesUrl: String,
        @SerializedName("ssh_url")
        val sshUrl: String,
        @SerializedName("stargazers_url")
        val stargazersUrl: String,
        @SerializedName("statuses_url")
        val statusesUrl: String,
        @SerializedName("subscribers_url")
        val subscribersUrl: String,
        @SerializedName("subscription_url")
        val subscriptionUrl: String,
        @SerializedName("tags_url")
        val tagsUrl: String,
        @SerializedName("teams_url")
        val teamsUrl: String,
        @SerializedName("trees_url")
        val treesUrl: String,
        val url: String
    ) {
        data class Owner(
            @SerializedName("avatar_url")
            val avatarUrl: String,
            @SerializedName("events_url")
            val eventsUrl: String,
            @SerializedName("followers_url")
            val followersUrl: String,
            @SerializedName("following_url")
            val followingUrl: String,
            @SerializedName("gists_url")
            val gistsUrl: String,
            @SerializedName("gravatar_id")
            val gravatarId: String,
            @SerializedName("html_url")
            val htmlUrl: String,
            val id: Long,
            val login: String,
            @SerializedName("node_id")
            val nodeId: String,
            @SerializedName("organizations_url")
            val organizationsUrl: String,
            @SerializedName("received_events_url")
            val receivedEventsUrl: String,
            @SerializedName("repos_url")
            val reposUrl: String,
            @SerializedName("site_admin")
            val siteAdmin: Boolean,
            @SerializedName("starred_url")
            val starredUrl: String,
            @SerializedName("subscriptions_url")
            val subscriptionsUrl: String,
            val type: String,
            val url: String
        )
    }

    data class Subject(
        @SerializedName("latest_comment_url")
        val latestCommentUrl: String,
        val title: String,
        val type: String,
        val url: String
    )

    fun toEntity(): GithubNotification {
        return GithubNotification(
            id = this.id,
            repositoryName = this.repository.fullName,
            number = "#${this.subject.url.split('/').last()}",
            updatedAt = this.updatedAt,
            repositoryImage = this.repository.owner.avatarUrl,
            title = subject.title,
            comments = "10"
        )
    }
}