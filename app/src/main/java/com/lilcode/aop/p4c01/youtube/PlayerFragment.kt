package com.lilcode.aop.p4c01.youtube

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
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
        initControlButton()

        getVideoList()
    }




    private fun initMotionLayout() {
        binding!!.playerMotionLayout.setTransitionListener(object :
            MotionLayout.TransitionListener {
            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {
            }

            override fun onTransitionChange( // ???????????? ?????? ?????? ????????????(?????? ????????????)??? ????????????.
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int,
                progress: Float
            ) {
                /**
                 * ?????? ???????????? ?????? ??????????????? ?????? ??????
                 */
                /**
                 * Fragment ??? ?????? ???????????? ????????? ??? ?????? ????????? activity ??? ?????? ???????????? ??????
                 * activity ??? ???????????? ?????? Fragment ??? attach ???????????? ??????????????? ????????????.
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

    private fun initPlayer() =with(binding!!){
        context?.let {
            player = SimpleExoPlayer.Builder(it).build()
        }

        playerView.player = player
        val oo = object : Player.Listener {
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                super.onIsPlayingChanged(isPlaying)
            }
        }

        player?.addListener(object: Player.EventListener{

            // play ????????? ?????? ??? ?????? ?????? (???????????? ????????? ??????)

            override fun onIsPlayingChanged(isPlaying: Boolean) {
                super.onIsPlayingChanged(isPlaying)

                if(isPlaying){
                    bottomPlayerControlButton.setImageResource(R.drawable.ic_baseline_pause_24)

                }else{
                    bottomPlayerControlButton.setImageResource(R.drawable.ic_baseline_play_arrow_24)
                }
            }
        })

    }

    private fun initControlButton()=with(binding!!) {
        bottomPlayerControlButton.setOnClickListener {
            // player ??? ?????? ?????? ???????????? ??????
            val player = player?:return@setOnClickListener

            if(player.isPlaying){
                player.pause()
            }else{
                player.play()
            }
        }
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
                        // ????????????
                    }

                })
        }
    }

    // ????????? ????????? ????????? ??? ??????
    fun play(url: String, title: String) {

        context?.let {
            val dataSourceFactory = DefaultDataSourceFactory(it)
            val mediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(MediaItem.fromUri(Uri.parse(url)))
            player?.setMediaSource(mediaSource)
            player?.prepare()
            player?.play()
        }

        binding?.let {
            it.playerMotionLayout.transitionToEnd() // ??????
            it.bottomTitleTextView.text = title
        }
    }

    override fun onStop() {
        super.onStop()

        player?.pause()
    }

    override fun onDestroy() {
        super.onDestroy()

        binding = null
        player?.release()
    }
}