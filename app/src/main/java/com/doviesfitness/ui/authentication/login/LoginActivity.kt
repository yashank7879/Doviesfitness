package com.doviesfitness.ui.authentication.login


import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.widget.EditText
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.doviesfitness.R
import com.doviesfitness.data.model.LoginResponce
import com.doviesfitness.data.model.UserInfoBean
import com.doviesfitness.ui.authentication.signup.SignupActivity
import com.doviesfitness.utils.Validation
import com.doviesfitness.ui.base.BaseActivity
import com.doviesfitness.ui.home_tab.HomeTabActivity
import com.doviesfitness.utils.Constant
import com.doviesfitness.utils.Constant.Companion.showCustomToast
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.error_pass
import kotlinx.android.synthetic.main.activity_login.et_pass
import kotlinx.android.synthetic.main.activity_signup.*
import org.json.JSONObject

class LoginActivity : BaseActivity(), View.OnClickListener {
    private var mLastClickTime: Long = 0
    lateinit var validation: Validation
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        inItView()
    }

    fun inItView() {
        validation = Validation(this)
        btn_login.setOnClickListener(this)
        iv_back.setOnClickListener(this)
        tv_new_account.setOnClickListener(this)
        sub_main_layout.setOnClickListener(this)
    }

    override fun onClick(p0: View) {
        when (p0.id) {
            R.id.btn_login -> {
                loginValidation()

                /*if (validation.isUserNameValid(et_username) && validation.isPasswordValid(et_pass)) {
                    loginApi()
                }*/
            }
            R.id.iv_back -> {
                onBackPressed()
            }
            R.id.tv_new_account -> {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    return
                } else {
                    mLastClickTime = SystemClock.elapsedRealtime()
                }
                intent = Intent(this, SignupActivity::class.java)
                startActivity(intent)
            }
            R.id.sub_main_layout ->{
                Constant.hideSoftKeyBoard(this,et_username)
            }

        }
    }

    private fun loginValidation() {
        userNameValidation(et_username)
        passValidation(et_pass)

        checkAllValidation()
    }

    private fun checkAllValidation() {
         if (!userNameValidation(et_username)) {
            error_username.visibility = View.VISIBLE

        } else if (!passValidation(et_pass)) {
            error_pass.visibility = View.VISIBLE

        } else {
             if (Constant.isNetworkAvailable(this, sv_main)) {
                 loginApi()
                 btn_login.isEnabled = false
             }
         }
    }

    private fun userNameValidation(et_username: EditText):Boolean {
        if (et_username.text.toString().trim().isEmpty()) {
            error_username.visibility = View.VISIBLE
            error_username.text = getString(R.string.please_enter_your_user_name)
            return false
        } else if (et_username.text.toString().trim().length < 3) {
            error_username.visibility = View.VISIBLE
            error_username.text = getString(R.string.please_enter_your_user_name)
            return false
        } else {
            error_username.visibility = View.GONE
            return true
        }

    }


    private fun passValidation(pass: EditText): Boolean {
        if (pass.text.toString().trim().isEmpty()) {
            error_pass.visibility = View.VISIBLE
            error_pass.text = getString(R.string.please_enter_a_password)
            return false
        } else if (pass.text.toString().trim().length < 3) {
            error_pass.visibility = View.VISIBLE
            error_pass.text = getString(R.string.please_enter_a_password)
            return false
        } else {
            error_pass.visibility = View.GONE
            return true
        }
    }

    private fun loginApi() {
        val header = HashMap<String, String>()
        header.put("device_id", "")
        header.put("device_type", "Android")
        header.put("password", et_pass.text.toString().trim())
        header.put("user_name", et_username.text.toString().trim())
        setLoading(true)
        getDataManager().doLogin(header)?.getAsJSONObject(object : JSONObjectRequestListener {
            override fun onResponse(response: JSONObject?) {
                try {
                    setLoading(false)
                    val jsonObject: JSONObject? = response?.getJSONObject("settings")
                    val success: String? = jsonObject?.getString("success")
                    val message: String? = jsonObject?.getString("message")
                    if (success.equals("1")) {
                        val data = getDataManager().mGson!!.fromJson(response.toString(), LoginResponce::class.java)
                        val userInfoBean = UserInfoBean(
                            data.data.get(0).is_admin
                            , data.data.get(0).customer_id
                            , data.data.get(0).customer_profile_image
                            , data.data.get(0).customer_auth_token
                            , data.data.get(0).customer_email_verified
                            , data.data.get(0).customer_status
                            , data.data.get(0).customer_notification
                            , data.data.get(0).customer_full_name
                            , data.data.get(0).customer_weight
                            , data.data.get(0).customer_height
                            , data.data.get(0).customer_mobile_number
                            , data.data.get(0).customer_gender
                            , data.data.get(0).customer_email
                            , data.data.get(0).customer_social_network
                            , data.data.get(0).customer_social_network_id
                            , data.data.get(0).customer_notify_remainder
                            , data.data.get(0).customer_user_name
                            , data.data.get(0).customer_units
                            , data.data.get(0).customer_country_id
                            , data.data.get(0).customer_country_name
                            , data.data.get(0).customer_isd_code
                            , data.data.get(0).notification_status
                            , data.data.get(0).dob
                            , data.data.get(0).title
                            , data.data.get(0).is_subscribed
                        )
                        getDataManager().setUserInfo(userInfoBean)
                        showCustomToast(this@LoginActivity, message!!)
                        finishAffinity()
                        intent = Intent(this@LoginActivity, HomeTabActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        btn_login.isEnabled = true
                        showCustomToast(this@LoginActivity, message!!)
                    }
                } catch (exce: Exception) {
                   btn_login.isEnabled = true
                    setLoading(false)
                    showCustomToast(this@LoginActivity, getString(R.string.something_wrong))
                }
            }

            override fun onError(anError: ANError?) {
                btn_login.isEnabled = true
                setLoading(false)
            }
        })
    }


}


