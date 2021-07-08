package com.lilcode.aop.p4c01.youtube

import android.os.Bundle
import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.Fragment
import com.lilcode.aop.p4c01.youtube.databinding.ActivityMainBinding
import com.lilcode.aop.p4c01.youtube.databinding.FragmentPlayerBinding
import kotlin.math.abs

class PlayerFragment : Fragment(R.layout.fragment_player) {

    private var binding: FragmentPlayerBinding? = null
    private lateinit var mainBinding: ActivityMainBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentPlayerBinding = FragmentPlayerBinding.bind(view)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        binding = fragmentPlayerBinding

        fragmentPlayerBinding.playerMotionLayout.setTransitionListener(object :
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
                binding?.let {
                    /**
                     * Fragment 는 자기 단독으로 존재할 수 없기 떄문에 activity 가 존재 할수밖에 없고
                     * activity 를 가져오면 해당 Fragment 가 attach 되어있는 액티비티를 가져온다.
                     */
                    (activity as MainActivity).also { mainActivity ->
                        mainActivity.findViewById<MotionLayout>(mainBinding.mainMotionLayout.id).progress =
                            abs(progress)
                    }
                }
            }

            override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
            }

            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {
            }

        })

    }

    override fun onDestroy() {
        super.onDestroy()

        binding = null
    }
}