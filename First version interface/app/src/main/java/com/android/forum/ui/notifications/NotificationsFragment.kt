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
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.android.forum.*
import com.android.forum.databinding.FragmentNotificationsBinding
import com.android.forum.file.XActivityLauncher
import com.android.forum.file.uriToFile
import com.android.forum.global.Constants
import com.android.forum.store.AppInfoManage
import com.google.android.material.imageview.ShapeableImageView
import org.w3c.dom.Text
import java.io.File

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val launcher = XActivityLauncher(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val avatar: ShapeableImageView = binding.avatar

        avatar.setImageResource(R.drawable.ic_avatar)

        /*info*/
        notificationsViewModel.username.observe(viewLifecycleOwner) {
            binding.tvUserName.text = it
        }
        notificationsViewModel.phoneNumber.observe(viewLifecycleOwner) {
            binding.tvPhoneNumber.text = it
        }
        notificationsViewModel.mailbox.observe(viewLifecycleOwner) {
            binding.tvMailbox.text = it
        }
        notificationsViewModel.city.observe(viewLifecycleOwner) {
            binding.tvCity.text = it
        }
        notificationsViewModel.personalSignature.observe(viewLifecycleOwner) {
            binding.tvPersonalSignature.text = it
        }

        if (AppInfoManage.avatar.isNotEmpty()) {
            val photoBmp: Bitmap = BitmapFactory.decodeFile(AppInfoManage.avatar);
            binding.avatar.setImageBitmap(photoBmp)
        }
        binding.avatar.setOnClickListener { view->
            launcher.launch("image/*") { data->
                data?.let { it ->

                    uriToFile(data,requireContext()).let {file ->
                        if (file != null) {
                            AppInfoManage.avatar = file.path
                        }

                    }

                    val photoBmp: Bitmap = MediaStore.Images.Media.getBitmap(
                        requireContext().contentResolver,
                        it
                    )
                    binding.avatar.setImageBitmap(photoBmp)


                }
            }
        }

        binding.llSet.setOnClickListener {
            toSetActivity()
        }

        binding.cardViewPost.setOnClickListener {

            toMyPostActivity()
        }
        binding.ivEdit.setOnClickListener {

            toEditInfoActivity()
        }
        return root
    }


    private fun toSetActivity() {
        val toPictureActivity = Intent(requireContext(), SetActivity::class.java)
        startActivity(toPictureActivity)
    }

    private fun toMyPostActivity() {
        val toPictureActivity = Intent(requireContext(), MyPostActivity::class.java)
        startActivity(toPictureActivity)
    }

    private fun toEditInfoActivity() {
        val toPictureActivity = Intent(requireContext(), EditInfoActivity::class.java)
        startActivity(toPictureActivity)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}