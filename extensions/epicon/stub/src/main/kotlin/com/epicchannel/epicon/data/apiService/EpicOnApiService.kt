package com.epicchannel.epicon.data.apiService

import com.epicchannel.epicon.data.model.showURL.ShowUrlResponse
import com.epicchannel.epicon.data.model.videoURL.PlayUrlInputData
import retrofit2.Response

interface EpicOnApiService {
    suspend fun showURL(playUrlInputData: PlayUrlInputData): Response<ShowUrlResponse>
}
