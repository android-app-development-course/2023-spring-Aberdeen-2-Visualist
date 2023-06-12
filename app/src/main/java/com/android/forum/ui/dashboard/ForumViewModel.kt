package com.android.forum.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.forum.data.AppDatabase
import com.android.forum.data.PostEntity
import com.android.forum.data.UserAndPostEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ForumViewModel : ViewModel() {

    val postDao = AppDatabase.getInstance().postDao()
    val userDao = AppDatabase.getInstance().userDao()


    private val _userAndPost = MutableLiveData<List<UserAndPostEntity>>()

    val userAndPost: LiveData<List<UserAndPostEntity>> = _userAndPost


    init {
        initData()
    }

     fun initData() {
        viewModelScope.launch {
            val list = withContext(Dispatchers.IO) {
                val data = mutableListOf<UserAndPostEntity>()
                userDao.getAll().forEach { user ->
                    val posts = postDao.getAllByUid(user.uid)
                    if (posts.isNotEmpty()){
                        data.add(UserAndPostEntity(user, posts))
                    }
                }
                data
            }
            list.reverse()
            _userAndPost.value = list
        }
    }
}