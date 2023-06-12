package com.android.forum.file

import android.net.Uri
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.contract.ActivityResultContracts

open class XActivityLauncher(caller: ActivityResultCaller) :
    XResultLauncher<String, Uri?>(caller, ActivityResultContracts.GetContent())



open class XActivityTakePicture(caller: ActivityResultCaller) :
    XResultLauncher<Uri,Boolean>(caller, ActivityResultContracts.TakePicture())
