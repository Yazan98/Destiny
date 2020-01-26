package com.yazan98.culttrip.domain.action

import io.vortex.android.VortexAction

open class CommentAction: VortexAction {
    class GetCommentsByRecipeId(private val id: Long): CommentAction() {
        fun get() = id
    }

    class SubmitComment(private val message: String, private val id: Long): CommentAction() {
        fun getId() = id
        fun getMessage() = message
    }

}