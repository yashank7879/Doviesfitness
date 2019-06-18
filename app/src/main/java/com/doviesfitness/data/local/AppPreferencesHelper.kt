package com.doviesfitness.data.local

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import com.doviesfitness.data.model.UserInfoBean
import com.doviesfitness.ui.authentication.login.LoginActivity
import java.util.HashMap

class AppPreferencesHelper(context: Context) : PreferencesHelper {
    private var mPrefs: SharedPreferences
    private var mRemPrefs: SharedPreferences
    //Remember me
    private val PREF_KEY_APP_REM_EMAIL = "PREF_KEY_APP_REM_EMAIL"
    private val PREF_KEY_APP_REM_PWD = "PREF_KEY_APP_REM_PWD"
    private val PREF_KEY_AUTH_TOKEN = "PREF_KEY_AUTH_TOKEN"


    //User info
    private val PREF_KEY_APP_USER_ID = "PREF_KEY_APP_USER_ID"
    private val PREF_KEY_APP_NAME = "PREF_KEY_APP_NAME"
    private val PREF_KEY_APP_EMAIL = "PREF_KEY_APP_EMAIL"
    private val PREF_KEY_APP_CONT_NUM = "PREF_KEY_APP_CONT_NUM"
    private val PREF_KEY_APP_LOCATION = "PREF_KEY_APP_LOCATION"
    private val PREF_KEY_APP_LAT = "PREF_KEY_APP_LAT"
    private val PREF_KEY_APP_LNG = "PREF_KEY_APP_LNG"
    private val PREF_KEY_APP_DEVICE_TYPE = "PREF_KEY_APP_DEVICE_TYPE"
    private val PREF_KEY_APP_DEVICE_TOKEN = "PREF_KEY_APP_DEVICE_TOKEN"
    private val PREF_KEY_APP_SOCIAL_ID = "PREF_KEY_APP_SOCIAL_ID"
    private val PREF_KEY_APP_SOCIAL_TYPE = "PREF_KEY_APP_SOCIAL_TYPE"
    private val PREF_KEY_APP_AUTH_TOKEN = "PREF_KEY_APP_AUTH_TOKEN"
    private val PREF_KEY_APP_PROFILE_IMAGE = "PREF_KEY_APP_PROFILE_IMAGE"

    companion object {
        private var instance: AppPreferencesHelper? = null
        private val APP_PREFERENCE = "AppPreference";

        private val PREF_KEY_USER_LOGGED_IN_MODE = "PREF_KEY_USER_LOGGED_IN_MODE";
        private val APP_REM_PREFERENCE = "AppRemPreference"

        fun getAppPreferencesHelper(context: Context): AppPreferencesHelper {
            instance = AppPreferencesHelper(context)
            return instance as AppPreferencesHelper
        }
    }


    override fun setInfo(info: String) {
        mPrefs.edit().putString(PREF_KEY_USER_LOGGED_IN_MODE, info).apply()
    }


    init {
        mPrefs = context.getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE);
        mRemPrefs = context.getSharedPreferences(APP_REM_PREFERENCE, Context.MODE_PRIVATE)

    }


    override fun isLoggedIn(): Boolean? {
        return  mPrefs.getBoolean(PREF_KEY_USER_LOGGED_IN_MODE,false)
    }

    override fun setRememberMe(email: String, pwd: String) {
        mRemPrefs.edit().putString(PREF_KEY_APP_REM_EMAIL, email).apply()
        mRemPrefs.edit().putString(PREF_KEY_APP_REM_PWD, pwd).apply()
    }

    override fun getRememberMe(): Array<String?> {
        val cre = arrayOfNulls<String>(2)
        cre[0] = mRemPrefs.getString(PREF_KEY_APP_REM_EMAIL, "")
        cre[1] = mRemPrefs.getString(PREF_KEY_APP_REM_PWD, "")
        return cre
    }

    override fun getHeader(): HashMap<String, String> {
        val param: HashMap<String, String> = HashMap()
        param.put("authToken", mPrefs.getString(PREF_KEY_AUTH_TOKEN, ""))
        return param
    }


    override fun getUserInfo(): UserInfoBean {
        val userInfo = UserInfoBean(mPrefs.getString(PREF_KEY_APP_USER_ID, ""),
            mPrefs.getString(PREF_KEY_APP_NAME, ""),
            mPrefs.getString(PREF_KEY_APP_EMAIL, ""),
            mPrefs.getString(PREF_KEY_APP_PROFILE_IMAGE, ""),
            mPrefs.getString(PREF_KEY_APP_CONT_NUM, ""),
            mPrefs.getString(PREF_KEY_APP_LOCATION, ""),
            mPrefs.getString(PREF_KEY_APP_LAT, ""),
            mPrefs.getString(PREF_KEY_APP_LNG, ""),
            mPrefs.getString(PREF_KEY_APP_DEVICE_TYPE, ""),
            mPrefs.getString(PREF_KEY_APP_DEVICE_TOKEN, ""),
            mPrefs.getString(PREF_KEY_APP_SOCIAL_ID, ""),
            mPrefs.getString(PREF_KEY_APP_SOCIAL_TYPE, ""),
            mPrefs.getString(PREF_KEY_APP_AUTH_TOKEN, ""))

        return userInfo
    }

    override fun setUserInfo(userInfo: UserInfoBean) {

        mPrefs.edit().putString(PREF_KEY_APP_USER_ID, userInfo.userId).apply()
        mPrefs.edit().putString(PREF_KEY_APP_NAME, userInfo.name).apply()
        mPrefs.edit().putString(PREF_KEY_APP_EMAIL, userInfo.email).apply()
        mPrefs.edit().putString(PREF_KEY_APP_LOCATION, userInfo.location).apply()
        mPrefs.edit().putString(PREF_KEY_APP_LAT, userInfo.latitude).apply()
        mPrefs.edit().putString(PREF_KEY_APP_LNG, userInfo.longitude).apply()
        mPrefs.edit().putString(PREF_KEY_APP_DEVICE_TYPE, userInfo.deviceType).apply()
        mPrefs.edit().putString(PREF_KEY_APP_DEVICE_TOKEN, userInfo.deviceToken).apply()
        mPrefs.edit().putString(PREF_KEY_APP_SOCIAL_ID, userInfo.socialId).apply()
        mPrefs.edit().putString(PREF_KEY_APP_SOCIAL_TYPE, userInfo.socialType).apply()
        mPrefs.edit().putString(PREF_KEY_APP_AUTH_TOKEN, userInfo.authToken).apply()
        mPrefs.edit().putString(PREF_KEY_APP_PROFILE_IMAGE, userInfo.profileImage).apply()
        mPrefs.edit().putBoolean(PREF_KEY_USER_LOGGED_IN_MODE, true).apply()
    }

    override fun logout(activity: Activity) {
        mPrefs.edit().putBoolean(PREF_KEY_USER_LOGGED_IN_MODE, false).apply()
        mPrefs.edit().clear().apply()

        activity.finishAffinity()
        val intent = Intent(activity, LoginActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        activity.startActivity(intent)
        activity.finish()
    }


}
