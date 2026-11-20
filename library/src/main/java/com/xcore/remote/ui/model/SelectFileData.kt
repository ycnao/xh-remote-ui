package com.xcore.remote.ui.model


/**
 * a data class to represent a storage
 */
data class SelectFileData(
    val name: String,
    val path: String,
    val type: String,
    val totalSize: Long,
    val available: Long
)
