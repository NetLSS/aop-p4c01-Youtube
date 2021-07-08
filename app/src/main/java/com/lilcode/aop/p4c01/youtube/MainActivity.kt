package com.lilcode.aop.p4c01.youtube

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.lilcode.aop.p4c01.youtube.databinding.ActivityMainBinding
import com.lilcode.aop.p4c01.youtube.dto.VideoDto
import com.lilcode.aop.p4c01.youtube.service.VideoService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    /**
     * 먼저, 리사이클러뷰를 어뎁터와 리니어레이아웃 연결
     * 프레임 레이아웃에 프레그먼트를 어테치
     */
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, PlayerFragment())
            .commit()

        getVideoList()

    }

    private fun getVideoList(){
        val retrofit = Retrofit.Builder()
            .baseUrl("https://run.mocky.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(VideoService::class.java).also{
            it.listVideos()
                .enqueue(object: Callback<VideoDto>{
                    override fun onResponse(call: Call<VideoDto>, response: Response<VideoDto>) {
                        if(response.isSuccessful.not()){
                            Log.e("MainActivity","response fail")
                            return
                        }

                        response.body()?.let{
                            Log.d("MainActiviy", it.toString())
                        }
                    }

                    override fun onFailure(call: Call<VideoDto>, t: Throwable) {
                        // 예외처리
                    }

                })
        }
    }
}