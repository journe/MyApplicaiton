package com.example.lvchen.myapplication.bean

/**
 * Created by journey on 2019-10-24.
 */
data class TopicListBean(
    val `data`: TopicData,
    val err: Err,
    val success: Boolean
)

data class TopicData(
    val list: List<TopicItem>,
    val nextPage: Boolean,
    val pageNum: Int,
    val total: Int
)

data class TopicItem(
    val assemEnum: Int,
    val `data`: TopicItemBean,
    val id: String
)

data class TopicItemBean(
    val likeStatus: Int,
    val path: String,
    val topicAbstract: String,
    val topicId: String,
    val topicLikeCount: Int,
    val topicPic: String,
    val topicStatus: Int,
    val topicTitle: String,
    val topicUserId: String,
    val topicVideo: String,
    val userAvatar: String,
    val userName: String
)
