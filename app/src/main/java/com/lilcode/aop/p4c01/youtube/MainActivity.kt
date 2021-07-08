package com.lilcode.aop.p4c01.youtube

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lilcode.aop.p4c01.youtube.databinding.ActivityMainBinding

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
    }
}