package com.android.forum.ui.notifications

import androidx.lifecycle.*
import com.android.forum.global.*
import com.android.forum.store.AppInfoManage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotificationsViewModel : ViewModel() {


    init {

        UsernameData.value = AppInfoManage.username
        PersonalSignatureData.value = AppInfoManage.personal_signature
        CityData.value = AppInfoManage.city
        PhoneNumberData.value = AppInfoManage.phoneNumber
        MailboxData.value = AppInfoManage.mailbox

    }

    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text

    /** 未读系统通知 */
    val username: LiveData<String> = UsernameData.map {
        it.orEmpty()
    }
    val mailbox: LiveData<String> = MailboxData.map {
        it.orEmpty()
    }
    val phoneNumber: LiveData<String> = PhoneNumberData.map {
        it.orEmpty()
    }
    val city: LiveData<String> = CityData.map {
        it.orEmpty()
    }
    val personalSignature: LiveData<String> = PersonalSignatureData.map {
        it.orEmpty()
    }
}