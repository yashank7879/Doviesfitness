package com.doviesfitness.ui.home_tab

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.doviesfitness.R
import com.doviesfitness.ui.authentication.IntroSliderActivity
import com.doviesfitness.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_home_tab.*

class HomeTabActivity : BaseActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_logout ->{
                finishAffinity()
                getDataManager().logout(this)
                intent = Intent(this, IntroSliderActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_tab)
        btn_logout.setOnClickListener(this)
    }

    override fun onBackPressed() {

    }
}
