package com.example.firebasedemo.core.remoteconfig

sealed interface RemoteConfigRefreshResult {
    data object Activated : RemoteConfigRefreshResult
    data object Unchanged : RemoteConfigRefreshResult
    data class Failed(val errorType: String) : RemoteConfigRefreshResult
}
