package com.lilcode.aop.p4c01.youtube

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.lilcode.aop.p4c01.youtube.adapter.VideoAdapter
import com.lilcode.aop.p4c01.youtube.databinding.ActivityMainBinding
import com.lilcode.aop.p4c01.youtube.databinding.FragmentPlayerBinding
import com.lilcode.aop.p4c01.youtube.dto.VideoDto
import com.lilcode.aop.p4c01.youtube.service.VideoService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.abs

class PlayerFragment : Fragment(R.layout.fragment_player) {
    private lateinit var videoAdapter: VideoAdapter

    private var binding: FragmentPlayerBinding? = null
    private lateinit var mainBinding: ActivityMainBinding
    private var player: SimpleExoPlayer? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentPlayerBinding = FragmentPlayerBinding.bind(view)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        binding = fragmentPlayerBinding

        initMotionLayout()
        initRecyclerView()
        initPlayer()

        getVideoList()
    }




    private fun initMotionLayout() {
        binding!!.playerMotionLayout.setTransitionListener(object :
            MotionLayout.TransitionListener {
            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {
            }

            override fun onTransitionChange(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int,
                progress: Float
            ) {
                /**
                 * 메인 엑티비티 모션 레이아웃에 값을 전달
                 */
                /**
                 * Fragment 는 자기 단독으로 존재할 수 없기 떄문에 activity 가 존재 할수밖에 없고
                 * activity 를 가져오면 해당 Fragment 가 attach 되어있는 액티비티를 가져온다.
                 */
                (activity as MainActivity).also { mainActivity ->
                    mainActivity.findViewById<MotionLayout>(mainBinding.mainMotionLayout.id).progress =
                        abs(progress)
                }
            }

            override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
            }

            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {
            }

        })
    }

    private fun initRecyclerView() {

        videoAdapter = VideoAdapter(callback = { url, title ->

            play(url, title)

        })

        binding!!.fragmentRecyclerView.apply {
            adapter = videoAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun initPlayer() {
        context?.let{
            player = SimpleExoPlayer.Builder(it).build()
        }

        binding!!.playerView.player = player
    }

    private fun getVideoList() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://run.mocky.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(VideoService::class.java).also {
            it.listVideos()
                .enqueue(object : Callback<VideoDto> {
                    override fun onResponse(call: Call<VideoDto>, response: Response<VideoDto>) {
                        if (response.isSuccessful.not()) {
                            Log.e("MainActivity", "response fail")
                            return
                        }

                        response.body()?.let { videoDto ->
                            videoAdapter.submitList(videoDto.videos)
                        }

                    }

                    override fun onFailure(call: Call<VideoDto>, t: Throwable) {
                        // 예외처리
                    }

                })
        }
    }

    // 동영상 아이템 눌렀을 때 처리
    fun play(url: String, title: String) {

        context?.let{
            val dataSourceFactory = DefaultDataSourceFactory(it)
            val mediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(MediaItem.fromUri(Uri.parse(url)))
            player?.setMediaSource(mediaSource)
            player?.prepare()
            player?.play()
        }

        binding?.let{
            it.playerMotionLayout.transitionToEnd() // 열기
            it.bottomTitleTextView.text = title
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        binding = null
    }
}