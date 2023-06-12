package com.android.forum.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.forum.data.AppDatabase
import com.android.forum.data.PostEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DashboardViewModel : ViewModel() {

    val appDatabase = AppDatabase.getInstance().postDao()


    private val _text = MutableLiveData<List<PostEntity>>()

    val posts: LiveData<List<PostEntity>> = _text


 fun initData(){
     viewModelScope.launch {

         val list =  withContext(Dispatchers.IO){
             appDatabase.getAll()
         }
         _text.value = list
     }
 }
}