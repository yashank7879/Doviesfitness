package com.doviesfitness.ui.authentication.signup

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import android.util.Patterns
import android.view.View
import android.widget.EditText
import com.doviesfitness.R
import com.doviesfitness.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_signup.*
import android.app.Activity
import android.app.DatePickerDialog
import android.os.SystemClock
import android.text.Editable
import android.text.TextWatcher
import android.view.WindowManager
import android.widget.DatePicker
import android.widget.Toast
import com.doviesfitness.utils.Constant
import com.doviesfitness.utils.Constant.Companion.SECOND_ACTIVITY_REQUEST_CODE
import kotlinx.android.synthetic.main.activity_country_selection.*
import java.text.SimpleDateFormat
import java.util.*


class SignupActivity : BaseActivity(), View.OnClickListener {
    private var startDateTime = Calendar.getInstance()
    var mDay: Int = 0
    var mMonth: Int = 0
    private var mLastClickTime: Long = 0
    var mYear: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_signup)

        intialize()

        privacyPolicyAndTemsOfUse()

        fieldTextWatcher()
    }


    private fun fieldTextWatcher() {
        et_fullname.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

                if (!s.toString().isEmpty()) {
                    fullNameValidation(et_fullname)
                }

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //To change body of created functions use File | Settings | File Templates.
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //To change body of created functions use File | Settings | File Templates.
            }

        })


        et_email.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (!s.toString().isEmpty()) {
                    emailValidation(et_email)
                }

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //To change body of created functions use File | Settings | File Templates.
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //To change body of created functions use File | Settings | File Templates.
            }

        })

        et_pass.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (!s.toString().isEmpty()) {
                    passValidation(et_pass)
                }

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //To change body of created functions use File | Settings | File Templates.
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //To change body of created functions use File | Settings | File Templates.
            }

        })

        et_confirm_pass.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (!s.toString().isEmpty()) {
                    confirmPassValidation(et_pass,et_confirm_pass)
                }

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //To change body of created functions use File | Settings | File Templates.
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //To change body of created functions use File | Settings | File Templates.
            }

        })

    }

    /* privacy policy and terms of use of font family underline
     //and
    click of both text:- "privacy policy" and  "Terms Of Use"*/
    private fun privacyPolicyAndTemsOfUse() {
        val builder = SpannableStringBuilder()
        val text1 = SpannableString("By creating an account, you agree to Doviesfitness's ")

        builder.append(text1)
        val privacyPolicy = SpannableString("Privacy policy")

        privacyPolicy.setSpan(StyleSpan(Typeface.BOLD), 0, privacyPolicy.length, 0)
        privacyPolicy.setSpan(UnderlineSpan(), 0, privacyPolicy.length, 0)
        privacyPolicy.setSpan(object : ClickableSpan() {
            override fun onClick(widget: View) {

            }
        }, 0, privacyPolicy.length, 0)
        privacyPolicy.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorGray3)),
            0,
            privacyPolicy.length,
            0
        )
        builder.append(privacyPolicy)

        val and = SpannableString(" and ")
        builder.append(and)

        val terms = SpannableString("Terms Of Use")

        terms.setSpan(object : ClickableSpan() {
            override fun onClick(widget: View) {

            }
        }, 0, terms.length, 0)
        terms.setSpan(ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorGray3)), 0,
            terms.length,
            0
        )
        terms.setSpan(UnderlineSpan(), 0, terms.length, 0)

        terms.setSpan(StyleSpan(Typeface.BOLD), 0, terms.length, 0)
        builder.append(terms)

        tv_terms_and_privacy.setMovementMethod(LinkMovementMethod.getInstance());
        tv_terms_and_privacy.text = builder
    }

    private fun intialize() {
        btn_create_acc.setOnClickListener(this)
        ll_country.setOnClickListener(this)
        iv_close.setOnClickListener(this)
        ll_dob.setOnClickListener(this)
        main_layout.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_create_acc -> {
                signUpValidation()
            }
            R.id.ll_country -> {
                intent = Intent(this, CountrySelectionActivity::class.java)
                startActivityForResult(intent, SECOND_ACTIVITY_REQUEST_CODE)
            }
            R.id.iv_close -> {
                onBackPressed()
            }
            R.id.ll_dob -> {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    return
                } else {
                    mLastClickTime = SystemClock.elapsedRealtime()

                }
                openStartDateDialog()
            }
            R.id.main_layout -> {
                Constant.hideSoftKeyBoard(this,et_email)
            }

        }
    }

    /*get the result from the CountrySelectionActivity.class
     * @param :- get the country flag
      * @param :- get the  country code
      * @param:- get the  country name
      * */
    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SECOND_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                Constant.hideSoftKeyBoard(this, et_email)

                val country_flag = data!!.getIntExtra("country_flag", -1)
                val myCode = data.getStringExtra("country_code")
                val myCountryName = data.getStringExtra("country_Name")
                val country_code = "+$myCode"
                tv_country.text = myCountryName
                countryValidation()
                println("*******$country_flag" + "myName")
                /*if (country_flag != -1) {
                    ivCountryFlag.setImageResource(country_flag)
                }*/
            }
        }
    }


    /*select date of birth of the user*/
    private fun openStartDateDialog() {
        val calendar = Calendar.getInstance()
        mYear = calendar.get(Calendar.YEAR)
        mMonth = calendar.get(Calendar.MONTH)
        mDay = calendar.get(Calendar.DAY_OF_MONTH)
        Constant.hideSoftKeyBoard(this, et_email)
        val startDateDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                startDateTime = Calendar.getInstance()
                startDateTime.set(Calendar.YEAR, year)
                startDateTime.set(Calendar.MONTH, month)
                startDateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                //******** Date time Format **************//
                val sdf1 = SimpleDateFormat("dd MMM, yyyy")
                val startDateString = sdf1.format(startDateTime.getTime())

                tv_dob.setText(sdf1.format(startDateTime.getTime()))

            }, mYear, mMonth, mDay
        )

        startDateDialog.datePicker.maxDate = System.currentTimeMillis() - 1000

        startDateDialog.show()
    }


    /*SIGNUP validation
    * in this sign up all the validation in If condition
    * */
    private fun signUpValidation() {

        fullNameValidation(et_fullname)
        emailValidation(et_email)
        countryValidation()
        passValidation(et_pass)
        confirmPassValidation(et_pass,et_confirm_pass)

        checkAllValidation()
    }

    private fun checkAllValidation() {
        if(!fullNameValidation(et_fullname)){
            error_fullname.visibility = View.VISIBLE
        }else if (!emailValidation(et_email)){
            error_email.visibility = View.VISIBLE

        }else if(!countryValidation()){
            error_country.visibility = View.VISIBLE

        }else if(!passValidation(et_pass)){
            error_pass.visibility = View.VISIBLE

        }else if (!confirmPassValidation(et_pass, et_confirm_pass)){
            error_confirm_pass.visibility = View.VISIBLE

        }else {
            intent = Intent(this, SelectGenderActivity::class.java)
            startActivity(intent)
            finish()
        }
    }


    private fun fullNameValidation(et_fullname : EditText): Boolean{
        if (et_fullname.text.trim().toString().isEmpty()) {
            error_fullname.visibility = View.VISIBLE
            error_fullname.text = getString(R.string.please_enter_your_full_name)
            return false
        } else {
            error_fullname.visibility = View.GONE
            return true
        }
    }

    private fun emailValidation(email: EditText): Boolean {
        if (email.text.trim().isEmpty()) {
            error_email.visibility = View.VISIBLE
            error_email.text = getString(R.string.please_enter_email_address)
            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email.text).matches()) {
            error_email.visibility = View.VISIBLE
            error_email.text = getString(R.string.please_enter_valid_email_address)
            return false
        } else {
            error_email.visibility = View.GONE
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


    private fun confirmPassValidation(pass: EditText, cPass: EditText): Boolean {
        if (cPass.text.toString().trim().isEmpty()) {
            error_confirm_pass.visibility = View.VISIBLE
            error_confirm_pass.text = getString(R.string.re_enter_password)
            return false
        } else if (!pass.text.toString().trim().equals(cPass.text.toString().trim())) {
            error_confirm_pass.visibility = View.VISIBLE
            error_confirm_pass.text = getString(R.string.pass_does_not_match_the_confirm_pass)
            return false
        } else {
            error_confirm_pass.visibility = View.GONE
            return true
        }

    }

    private fun countryValidation(): Boolean{
        if (tv_country.text.toString().equals("Country")) {
            error_country.visibility = View.VISIBLE
            return false
        } else {
            error_country.visibility = View.GONE
            return true
        }
    }

}
