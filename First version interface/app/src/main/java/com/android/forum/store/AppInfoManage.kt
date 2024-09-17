package com.android.forum.store

/**
 * @Description:
 * @Author: JIULANG
 * @Data: 2023/4/20 18:48
 */
object AppInfoManage {

    private const val mPhoneNumber = "phone_key"
    private const val mMailbox = "mailbox_key"
    private const val mUsername = "username_key"
    private const val mCity = "city_key"
    private const val mPersonal_signature = "personal signature_key"
    private const val mAvatar = "avatar_key"

    var phoneNumber: String
        get() = DataStoreUtils.readStringData(mPhoneNumber, "未填写")
        set(value) = DataStoreUtils.saveSyncStringData(mPhoneNumber, value = value)

    var mailbox: String
        get() = DataStoreUtils.readStringData(mMailbox, "未填写")
        set(value) = DataStoreUtils.saveSyncStringData(mMailbox, value = value)

    var username: String
        get() = DataStoreUtils.readStringData(mUsername, "滑翔芝士")
        set(value) = DataStoreUtils.saveSyncStringData(mUsername, value = value)

    var city: String
        get() = DataStoreUtils.readStringData(mCity, "广州")
        set(value) = DataStoreUtils.saveSyncStringData(mCity, value = value)

    var personal_signature: String
        get() = DataStoreUtils.readStringData(mPersonal_signature, "个性签名")
        set(value) = DataStoreUtils.saveSyncStringData(mPersonal_signature, value = value)


    var avatar: String
        get() = DataStoreUtils.readStringData(mAvatar, "")
        set(value) = DataStoreUtils.saveSyncStringData(mAvatar, value = value)

}