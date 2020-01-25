package com.yazan98.culttrip.domain.action

import io.vortex.android.VortexAction

open class CommentAction: VortexAction {
    class GetCommentsByRecipeId(private val id: Long): CommentAction() {
        fun get() = id
    }
}