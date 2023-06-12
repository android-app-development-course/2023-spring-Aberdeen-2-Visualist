package com.android.forum.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.android.forum.R
import com.android.forum.data.GlobalData
import com.android.forum.data.PostEntity
import com.android.forum.databinding.FragmentDashboardBinding


class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    private val binding get() = _binding!!

    lateinit var mViewModel: DashboardViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mViewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)

        return binding.root
    }

    private val itemClickListener = object : PostItemClickListener {
        override fun onItemClick(post: PostEntity) {
            GlobalData.picture = post.path
            toPicture()
        }
    }

    fun toPicture() {
        val pictureFragment = PictureFragment()
        val fm = childFragmentManager
        fm.beginTransaction()
            .add(R.id.fragment_content, pictureFragment)
            .addToBackStack("PictureFragment")
            .commit()
    }

    override fun onResume() {
        super.onResume()
        mViewModel.initData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mViewModel.posts.observe(this) {
            val mAdapter = PostAdapter(it,itemClickListener)
            binding.postRecyclerView.adapter = mAdapter
        }

        binding.fabAdd.setOnClickListener {
            toEditActivity()
        }
    }

    private fun toEditActivity() {
        val toPictureActivity = Intent(requireContext(), EditActivity::class.java)
        startActivity(toPictureActivity)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}