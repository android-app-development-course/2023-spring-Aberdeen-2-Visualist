package com.android.forum.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.TranslateAnimation
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.forum.databinding.FragmentForumBinding
import com.android.forum.ui.dashboard.adapter.VerticalAdapter

class ForumFragment : Fragment() {


    private var _binding: FragmentForumBinding? = null

    private val mBinding get() = _binding!!

    lateinit var mViewModel:  ForumViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mViewModel = ViewModelProvider(this)[ForumViewModel::class.java]

        _binding = FragmentForumBinding.inflate(inflater, container, false)

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mRecyclerView = mBinding.verticalRecyclerView
        mRecyclerView.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )
        mViewModel.userAndPost.observe(this){
            val adapter = VerticalAdapter(requireContext(),it){

                toForumDetailActivity()
            }
            mRecyclerView.adapter = adapter
        }

        mBinding.fabAdd.setOnClickListener {
            toEditActivity()
        }
        initAnimation()
    }

    private fun toEditActivity() {
        val toPictureActivity = Intent(requireContext(), EditActivity::class.java)
        startActivity(toPictureActivity)
    }
    private fun toForumDetailActivity() {
        val toPictureActivity = Intent(requireContext(), ForumDetailActivity::class.java)
        startActivity(toPictureActivity)
    }
    private fun initAnimation() {
        val translateAnimation = TranslateAnimation(0f, 0f, 190f, 0f)
        translateAnimation.duration = 1000;  // 时长1s
        translateAnimation.fillAfter = true; // 保持动画结束状态
        mBinding.verticalRecyclerView.startAnimation(translateAnimation)

    }


    override fun onResume() {
        super.onResume()
        mViewModel.initData()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
