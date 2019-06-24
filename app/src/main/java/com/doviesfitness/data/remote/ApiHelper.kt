package com.doviesfitness.data.remote

import com.androidnetworking.common.ANRequest
import com.doviesfitness.data.model.SignupInfo
import java.io.File

interface ApiHelper {
    fun checkEmailAvailability(param: HashMap<String, String>): ANRequest<out ANRequest<*>>?
    fun checkUserNameAvailability(param: HashMap<String, String>): ANRequest<out ANRequest<*>>?
    fun doLogin(param: HashMap<String, String>): ANRequest<out ANRequest<*>>?
    fun doSignup(param: SignupInfo, profileImage: HashMap<String, File?>): ANRequest<out ANRequest<*>>?
}