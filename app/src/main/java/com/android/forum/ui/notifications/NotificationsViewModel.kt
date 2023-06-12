package com.android.forum.ui.notifications

import androidx.lifecycle.*
import com.android.forum.data.AppDatabase
import com.android.forum.data.GlobalData
import com.android.forum.data.User
import com.android.forum.data.WorksEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotificationsViewModel : ViewModel() {

    val userDao = AppDatabase.getInstance().userDao()
    val worksDao = AppDatabase.getInstance().worksDao()

    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }

    val text: LiveData<String> = _text

    val works: LiveData<List<WorksEntity>> = worksDao.getAll(GlobalData.uid)

    private val user: LiveData<User> = userDao.getUserLive(GlobalData.uid)

    /** 未读系统通知 */
    val username: LiveData<String> = user.map {
        it.username
    }

    val phoneNumber: LiveData<String> = user.map {
        it.phone
    }
    val avatar: LiveData<String> = user.map {
        it.avatar
    }
    val personalSignature: LiveData<String> = user.map {
        it.signature
    }

    fun upDataAvatar(path: String){
        viewModelScope.launch(Dispatchers.IO) {
            user.value?.avatar = path
            user.value?.let {
                userDao.update(it)
            }
        }
    }

}