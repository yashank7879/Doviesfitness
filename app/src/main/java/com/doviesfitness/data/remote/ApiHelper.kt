package com.doviesfitness.data.remote
import com.androidnetworking.common.ANRequest

interface ApiHelper {
    fun doLogin(param : HashMap<String, String>): ANRequest<out ANRequest<*>>?
    fun doSignup(param : HashMap<String, String>): ANRequest<out ANRequest<*>>?
    fun singleJobCreate(param : HashMap<String, String>): ANRequest<out ANRequest<*>>?
    fun skills(): ANRequest<out ANRequest<*>>?
}