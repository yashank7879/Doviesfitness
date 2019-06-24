package com.doviesfitness.ui.authentication.signup

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.doviesfitness.R
import com.doviesfitness.data.model.SignupInfo
import com.doviesfitness.ui.base.BaseActivity
import com.doviesfitness.utils.Constant
import com.doviesfitness.utils.OnSwipeListener
import kotlinx.android.synthetic.main.activity_create_user.*
import kotlinx.android.synthetic.main.activity_create_user.iv_next_btn
import kotlinx.android.synthetic.main.activity_height_and_weight.*
import kotlinx.android.synthetic.main.activity_signup.*
import org.json.JSONObject
import java.lang.Exception

class CreateUserActivity : BaseActivity(), View.OnClickListener {


    private lateinit var gestureDetector: GestureDetector
    lateinit var signupInfo: SignupInfo
    var isUsername: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user)
        initView()
    }

    private fun initView() {
        iv_next_btn.setOnClickListener(this)
        main_layout1.setOnClickListener(this)
        //iv_next_btn.setOnTouchListener(this)


        et_username.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //To change body of created functions use File | Settings | File Templates.
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //To change body of created functions use File | Settings | File Templates.
            }

            override fun afterTextChanged(s: Editable?) {
                if (!s.toString().isEmpty() && s.toString().length > 2) {
                    userNameValidation(et_username)
                }
            }
        })
    }



    override fun onClick(v: View?) {
        when (v?.id) {
            /* @param:isUsername : false "so it will not go next screen"
            * @param:isUsername : true "so it will go next screen"
            * */
            R.id.iv_next_btn -> {
                if (isUsername && et_username.text.toString().trim().length > 2) {
                    signupInfo = intent.getSerializableExtra("SignUpInfo") as SignupInfo
                    signupInfo.setUser_name(et_username.text.toString().trim())

                    val intent = Intent(this, AddProfilePhotoActivity::class.java)
                    intent.putExtra("SignUpInfo", signupInfo)
                    startActivity(intent)
                    finish()
                    iv_next_btn.isEnabled = false
                }
            }
            R.id.main_layout1 -> {
                Constant.hideSoftKeyBoard(this, et_username)
            }
        }
    }

    override fun onBackPressed() {

    }

    /*chcek the user name is available or not
    * @param: user_name
    *
    * @GetValue from server
    * @ is_available: 1 "so you can go to next step"
    * @ is_available: 0 "enter your username"
    * */
    private fun userNameValidation(et_username: EditText) {
        iv_next_btn.isEnabled = false
        if (et_username.text.toString().trim().length > 2) {
            val pram = HashMap<String, String>()
            pram.put("user_name", et_username.text.toString().trim())
            getDataManager().checkUserNameAvailability(pram)?.getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject?) {
                    try {
                        val json: JSONObject? = response?.getJSONObject("data")
                        if (json!!.get("is_available").equals("1")) {
                            tv_username_status.visibility = View.VISIBLE
                            tv_username_status.setTextColor(
                                ContextCompat.getColor(
                                    this@CreateUserActivity,
                                    R.color.colorGreen
                                )
                            )
                            tv_username_status.text = json.getString("message")
                            tv_username_status.visibility = View.VISIBLE
                            isUsername = true
                            iv_next_btn.isEnabled = true
                        } else {
                            tv_username_status.visibility = View.VISIBLE
                            tv_username_status.setTextColor(
                                ContextCompat.getColor(
                                    this@CreateUserActivity,
                                    R.color.colorOrange
                                )
                            )
                            tv_username_status.text = json.getString("message")
                            isUsername = false
                            iv_next_btn.isEnabled = true
                        }
                    } catch (exce: Exception) {
                        iv_next_btn.isEnabled = true
                        Constant.showCustomToast(this@CreateUserActivity, getString(R.string.something_wrong))
                    }
                }

                override fun onError(anError: ANError?) {
                    iv_next_btn.isEnabled = true
                    Constant.showCustomToast(this@CreateUserActivity, getString(R.string.something_wrong))
                    Log.e("Error", "" + anError?.localizedMessage)
                }

            })
        }

    }

}
