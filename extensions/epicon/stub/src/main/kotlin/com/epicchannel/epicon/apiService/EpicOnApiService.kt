package com.epicchannel.epicon.apiService

import com.epicchannel.epicon.model.show_url.ShowUrlResponse
import com.epicchannel.epicon.model.videoURL.PlayUrlInputData
import retrofit2.Response

interface EpicOnApiService {
    @Suppress("ktlint:standard:function-naming")
    suspend fun show_url(playUrlInputData: PlayUrlInputData): Response<ShowUrlResponse>
}
