package com.doviesfitness.data.local

import android.app.Activity
import com.doviesfitness.data.model.UserInfoBean
import java.util.HashMap

interface PreferencesHelper {
    fun setInfo(info:String)
     fun isLoggedIn(): Boolean?

     fun getUserInfo(): UserInfoBean

     fun setUserInfo(userInfo: UserInfoBean)

     fun setRememberMe(email: String, pwd: String)

     fun getRememberMe(): Array<String?>

     fun getHeader(): HashMap<String, String>


     fun logout(activity: Activity)
}