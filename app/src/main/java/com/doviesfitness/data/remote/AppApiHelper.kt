package com.doviesfitness.data.remote

import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.ANRequest
import com.androidnetworking.common.Priority
import com.doviesfitness.data.model.SignupInfo
import com.doviesfitness.data.remote.Webservice.Companion.CONTENT_TYPE1
import java.io.File

class AppApiHelper: ApiHelper {

    companion object{
        private val instance = AppApiHelper()

        fun getAppApiHelper(): AppApiHelper {
            return instance
        }
    }

    override fun doSignup(param: SignupInfo, profileImage: HashMap<String, File?>): ANRequest<out ANRequest<*>>? {
        return  AndroidNetworking.upload(Webservice.USER_SIGNUP_API)
            .addHeaders("Content-Type","application/json")
            .addHeaders("Accept","application/json")
            .addHeaders("APIKEY","HBDEV")
            .addHeaders("APIVERSION","1")
            .addMultipartParameter(param)
            .addMultipartFile(profileImage)
            .setPriority(Priority.HIGH)
            .build()
    }

    override fun doLogin(param : HashMap<String, String>): ANRequest<out ANRequest<*>>? {
        return AndroidNetworking.post(Webservice.USER_LOGIN_API)
            .addBodyParameter(param)
            .setPriority(Priority.MEDIUM)
            .build()
    }


    override fun checkUserNameAvailability(param: HashMap<String, String>): ANRequest<out ANRequest<*>>? {
        return AndroidNetworking.post(Webservice.USERNAME_AVAILABILITY)
            .addBodyParameter(param)
            .setPriority(Priority.MEDIUM)
            .build()
    }



    override fun checkEmailAvailability(param: HashMap<String, String>): ANRequest<out ANRequest<*>>? {
        return  AndroidNetworking.post(Webservice.EMAIL_AVAILABILITY)
            .setContentType(CONTENT_TYPE1)
            .addBodyParameter(param)
            .setPriority(Priority.HIGH)
            .build()
    }

}