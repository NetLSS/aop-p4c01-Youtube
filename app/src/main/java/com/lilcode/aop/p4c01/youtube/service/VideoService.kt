package com.lilcode.aop.p4c01.youtube.service

import com.lilcode.aop.p4c01.youtube.dto.VideoDto
import retrofit2.Call
import retrofit2.http.GET


interface VideoService {

    @GET("/v3/66f12ec7-a2e6-4070-b7cc-7f3563fbe962")
    fun listVideos(): Call<VideoDto>
}