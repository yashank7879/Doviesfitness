package com.doviesfitness.ui.authentication.signup

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.doviesfitness.R
import com.doviesfitness.ui.home_tab.HomeTabActivity
import com.doviesfitness.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_welcome.*

class WelcomeActivity : BaseActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        initView()
        val name = intent.getStringExtra("UserName")
        tv_name.text = name
    }

    private fun initView() {
        iv_next_btn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_next_btn -> {
                iv_next_btn.isEnabled = false
                intent = Intent(this,HomeTabActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    override fun onBackPressed() {
    }
}
