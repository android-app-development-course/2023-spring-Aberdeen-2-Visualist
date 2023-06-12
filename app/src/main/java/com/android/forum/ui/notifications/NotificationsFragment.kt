package com.android.forum.ui.notifications

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.TranslateAnimation
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.forum.*
import com.android.forum.databinding.FragmentNotificationsBinding
import com.android.forum.file.XActivityLauncher
import com.android.forum.file.uriToFile

import com.android.forum.ui.notifications.adapter.GridAdapter

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null

    private val binding get() = _binding!!
    lateinit var mViewModel: NotificationsViewModel
    private val launcher = XActivityLauncher(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mViewModel = ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root


        /*info*/
        mViewModel.username.observe(viewLifecycleOwner) {
            binding.username.text = it
        }

        mViewModel.personalSignature.observe(viewLifecycleOwner) {
            binding.signature.text = it
        }
        mViewModel.avatar.observe(viewLifecycleOwner) {
            if (!TextUtils.isEmpty(it)){
                val photoBmp: Bitmap = BitmapFactory.decodeFile(it);
                binding.ivAvatar.setImageBitmap(photoBmp)
            }

        }

        binding.ivAvatar.setOnClickListener { view->
            launcher.launch("image/*") { data->
                data?.let { it ->

                    uriToFile(data,requireContext()).let {file ->
                        if (file != null) {

                            mViewModel.upDataAvatar(file.path)
                        }

                    }

                    val photoBmp: Bitmap = MediaStore.Images.Media.getBitmap(
                        requireContext().contentResolver,
                        it
                    )
                    binding.ivAvatar.setImageBitmap(photoBmp)

                }
            }
        }

        binding.imageView7.setOnClickListener {
            toSetActivity()
        }

        binding.imageView3.setOnClickListener {

            toEditInfoActivity()
        }
        binding.imageView4.setOnClickListener {

            toMyPostActivity()
        }
        binding.imageView5.setOnClickListener {

            toActivityExamine()
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mRecyclerView = binding.recyclerView
        mRecyclerView.layoutManager =  GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)

        mViewModel.works.observe(this){
            mRecyclerView.adapter = GridAdapter(requireContext(),it){

            }

        }
        initAnimation()
    }



    private fun toSetActivity() {
        val toPictureActivity = Intent(requireContext(), SetActivity::class.java)
        startActivity(toPictureActivity)
    }

    private fun toMyPostActivity() {
        val toPictureActivity = Intent(requireContext(), MyPostActivity::class.java)
        startActivity(toPictureActivity)
    }

    private fun toActivityExamine() {
        val toPictureActivity = Intent(requireContext(), ActivityExamine::class.java)
        startActivity(toPictureActivity)
    }

    private fun toEditInfoActivity() {
        val toPictureActivity = Intent(requireContext(), EditInfoActivity::class.java)
        startActivity(toPictureActivity)
    }
    private fun initAnimation() {
        val translateAnimation = TranslateAnimation(0f, 0f, 170f, 0f)
        translateAnimation.duration = 600;  // 时长1s
        translateAnimation.fillAfter = true; // 保持动画结束状态
        binding.imageView3.startAnimation(translateAnimation)
        binding.ivAvatar.startAnimation(translateAnimation)
        binding.username.startAnimation(translateAnimation)
        binding.signature.startAnimation(translateAnimation)
        binding.textView12.startAnimation(translateAnimation)
        binding.textView13.startAnimation(translateAnimation)
        binding.textView14.startAnimation(translateAnimation)
        binding.textView8.startAnimation(translateAnimation)
        binding.imageView3.startAnimation(translateAnimation)
        binding.textView11.startAnimation(translateAnimation)
        binding.textView10.startAnimation(translateAnimation)

        val edAnimation = TranslateAnimation(0f, 0f, 100f, 0f)
        edAnimation.duration = 600;  // 时长1s
        edAnimation.fillAfter = true; // 保持动画结束状态
        binding.linearLayout.startAnimation(edAnimation)
        binding.linearLayoutText.startAnimation(edAnimation)
        val btnAnimation = TranslateAnimation(0f, 0f, 130f, 0f)
        btnAnimation.duration = 600;  // 时长1s
        btnAnimation.fillAfter = true; // 保持动画结束状态
        binding.recyclerView.startAnimation(btnAnimation)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}