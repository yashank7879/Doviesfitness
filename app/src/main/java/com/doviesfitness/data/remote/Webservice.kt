package com.doviesfitness.data.remote

class Webservice {
    companion object{
        val BASE_URL = "https://dev.doviesfitness.com/WS/"
        val CONTENT_TYPE1 = "application/x-www-form-urlencoded"
        val EMAIL_AVAILABILITY = BASE_URL + "email_availability"
        val USERNAME_AVAILABILITY = BASE_URL + "user_availability"
        val USER_LOGIN_API = BASE_URL+"customer_login"
        val USER_SIGNUP_API =BASE_URL+ "sign_up"
        val CREATE_JOB_API = ""
        val CATEGORY_LIST_API = ""
    }
}
