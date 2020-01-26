package com.yazan98.culttrip.data.models.request

data class CommentBody(
    val profileId: Long,
    val recipeId: Long,
    val comment: String
)