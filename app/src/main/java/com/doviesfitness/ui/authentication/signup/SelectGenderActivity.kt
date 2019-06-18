package com.doviesfitness.ui.authentication.signup

import android.os.Bundle
import android.view.View
import com.doviesfitness.R
import com.doviesfitness.ui.base.BaseActivity
import com.doviesfitness.utils.Constant
import kotlinx.android.synthetic.main.activity_select_gender.*

class SelectGenderActivity : BaseActivity() ,View.OnClickListener{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_gender)
        iniatalizeView()
    }

    private fun iniatalizeView() {
        btn_female.setOnClickListener(this)
        btn_male.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id){
            R.id.btn_male ->{
                Constant.showCustomToast(this ," male")

            }R.id.btn_female ->{
            Constant.showCustomToast(this ," female")
        }
        }
    }
}
