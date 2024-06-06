package com.sopt.now.data.remote.repository

import com.sopt.now.data.remote.response.FriendsResponse
import com.sopt.now.data.remote.service.FriendService
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FriendRepository
    @Inject
    constructor(
        private val friendService: FriendService,
    ) {
        suspend fun getFriends(page: Int): Response<FriendsResponse> {
            return friendService.getFriends(page)
        }
    }
