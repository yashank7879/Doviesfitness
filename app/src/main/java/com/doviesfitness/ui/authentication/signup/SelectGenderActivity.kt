package com.doviesfitness.ui.authentication.signup

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.doviesfitness.R
import com.doviesfitness.data.model.SignupInfo
import com.doviesfitness.ui.base.BaseActivity
import com.doviesfitness.utils.Constant
import kotlinx.android.synthetic.main.activity_select_gender.*

class SelectGenderActivity : BaseActivity(), View.OnClickListener {

    lateinit var signupInfo:SignupInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_gender)

        signupInfo = intent.getSerializableExtra("SignUpInfo") as SignupInfo
        iniatalizeView()
    }

    private fun iniatalizeView() {
        btn_female.setOnClickListener(this)
        btn_male.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_male -> {
                btn_male.isEnabled = false
                btn_female.isEnabled = false
                heightWeightActivityIntent("Male")
            }
            R.id.btn_female -> {
                btn_female.isEnabled = false
                btn_male.isEnabled = false
                heightWeightActivityIntent("Female")
            }
        }
    }

    override fun onBackPressed() {

    }

    private fun heightWeightActivityIntent( gender : String) {
        signupInfo.setGender(gender)
        Log.e("EMAIL", signupInfo.getEmail())

        val intent = Intent(this, HeightAndWeightActivity::class.java)
        intent.putExtra("SignUpInfo", signupInfo)
        startActivity(intent)
        finish()
    }
}
