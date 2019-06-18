package com.doviesfitness.data.remote

import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.ANRequest
import com.androidnetworking.common.Priority

class AppApiHelper: ApiHelper {
    companion object{
        private val instance = AppApiHelper()

        fun getAppApiHelper(): AppApiHelper {
            return instance
        }
    }

    override fun doSignup(param: HashMap<String, String>): ANRequest<out ANRequest<*>>? {
        return  AndroidNetworking.upload(Webservice.USER_SIGNUP_API)
            .addMultipartParameter(param)
            .setPriority(Priority.HIGH)
            .build()
    }

    override fun doLogin(param : HashMap<String, String>): ANRequest<out ANRequest<*>>? {
        return AndroidNetworking.post(Webservice.USER_LOGIN_API)
            .addBodyParameter(param)
            .setPriority(Priority.MEDIUM)
            .build()
    }


    override fun singleJobCreate(param: HashMap<String, String>): ANRequest<out ANRequest<*>>? {
        return AndroidNetworking.post(Webservice.CREATE_JOB_API)
            .addBodyParameter(param)
            .setPriority(Priority.MEDIUM)
            .build()
    }

    override fun skills(): ANRequest<out ANRequest<*>>? {
        return AndroidNetworking.get(Webservice.CATEGORY_LIST_API)
            .setPriority(Priority.MEDIUM)
            .build()
    }

}