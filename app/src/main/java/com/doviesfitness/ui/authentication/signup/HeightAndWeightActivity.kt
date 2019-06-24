package com.doviesfitness.ui.authentication.signup

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import com.doviesfitness.R
import com.doviesfitness.data.model.SignupInfo
import com.doviesfitness.ui.authentication.signup.dialog.HeightAndWeightDialog
import com.doviesfitness.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_height_and_weight.*

class HeightAndWeightActivity : BaseActivity(), View.OnClickListener, HeightAndWeightDialog.HeightWeightCallBack {
   lateinit var signupInfo :SignupInfo
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_height_and_weight)
        inItView()
    }

    fun inItView() {
        //btn_opn_dialog.setOnClickListener(this)
        signupInfo = intent.getSerializableExtra("SignUpInfo") as SignupInfo

        ft_btn.setOnClickListener(this)
        cm_btn.setOnClickListener(this)
        kg_btn.setOnClickListener(this)
        lbs_btn.setOnClickListener(this)
        ll_feet.setOnClickListener(this)
        ll_inch.setOnClickListener(this)
        ll_cm.setOnClickListener(this)
        ll_lbs.setOnClickListener(this)
        ll_kg.setOnClickListener(this)
        iv_next_btn.setOnClickListener(this)
        tv_skip.setOnClickListener(this)
    }

    override fun onClick(p0: View) {
        when (p0.id) {
            /* R.id.btn_opn_dialog -> {
                 //val openDialog = HeightAndWeightDialog.newInstance(100, 200,"DONE",this).show(supportFragmentManager)
             }*/
            R.id.ft_btn -> {
                tv_cm.text = "0"
                ll_cm.setVisibility(View.GONE)
                ll_ft.setVisibility(View.VISIBLE)
                ft_btn.background = ContextCompat.getDrawable(this, R.drawable.rectangle_back_white)
                cm_btn.background = ContextCompat.getDrawable(this, R.drawable.rigth_trans_corner)
                ft_btn.setTextColor(ContextCompat.getColor(this, R.color.colorBlack))
                cm_btn.setTextColor(ContextCompat.getColor(this, R.color.colorWhite))
            }
            R.id.cm_btn -> {
                tv_feet.text = "0"
                tv_inch.text = "0"
                ll_cm.setVisibility(View.VISIBLE)
                ll_ft.setVisibility(View.GONE)
                ft_btn.background = ContextCompat.getDrawable(this, R.drawable.left_trans_corner)
                cm_btn.background = ContextCompat.getDrawable(this, R.drawable.right_rectangle_back_white)
                ft_btn.setTextColor(ContextCompat.getColor(this, R.color.colorWhite))
                cm_btn.setTextColor(ContextCompat.getColor(this, R.color.colorBlack))
            }
            R.id.lbs_btn -> {
                tv_kg.text = "0"
                ll_kg.setVisibility(View.GONE)
                ll_lbs.setVisibility(View.VISIBLE)
                lbs_btn.background = ContextCompat.getDrawable(this, R.drawable.rectangle_back_white)
                kg_btn.background = ContextCompat.getDrawable(this, R.drawable.rigth_trans_corner)
                lbs_btn.setTextColor(ContextCompat.getColor(this, R.color.colorBlack))
                kg_btn.setTextColor(ContextCompat.getColor(this, R.color.colorWhite))
            }
            R.id.kg_btn -> {
                tv_lbs.text = "0"
                ll_kg.visibility = View.VISIBLE
                ll_lbs.visibility = View.GONE
                lbs_btn.background = ContextCompat.getDrawable(this, R.drawable.left_trans_corner)
                kg_btn.background = ContextCompat.getDrawable(this, R.drawable.right_rectangle_back_white)
                lbs_btn.setTextColor(ContextCompat.getColor(this, R.color.colorWhite))
                kg_btn.setTextColor(ContextCompat.getColor(this, R.color.colorBlack))
            }
            R.id.ll_feet -> {
                val openDialog =
                    HeightAndWeightDialog.newInstance(3, 7, "HeightInFeet", this).show(supportFragmentManager)

            }
            R.id.ll_inch -> {
                val openDialog =
                    HeightAndWeightDialog.newInstance(1, 11, "HeightInInch", this).show(supportFragmentManager)

            }
            R.id.ll_cm -> {
                val openDialog =
                    HeightAndWeightDialog.newInstance(91, 242, "HeightInCm", this).show(supportFragmentManager)

            }
            R.id.ll_lbs -> {
                val openDialog =
                    HeightAndWeightDialog.newInstance(30, 500, "WeightInLbs", this).show(supportFragmentManager)

            }
            R.id.ll_kg -> {
                val openDialog =
                    HeightAndWeightDialog.newInstance(13, 227, "WeightInKg", this).show(supportFragmentManager)
            }
            R.id.iv_next_btn -> {
                iv_next_btn.isEnabled = false
                signupInfo.setHeight(tv_cm.text.toString().trim())
                signupInfo.setWeight(tv_lbs.text.toString().trim())

                val intent = Intent(this, CreateUserActivity::class.java)
                intent.putExtra("SignUpInfo",signupInfo)
                startActivity(intent)
                finish()

            }
            R.id.tv_skip -> {
                tv_skip.isEnabled = false
                intent = Intent(this, CreateUserActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    override fun onBackPressed() {

    }

    /*Callback value listener
    * @param: value (number picker selcted value
    * @param: type (Type of value)
    * */
    override fun valueOnClick(value: Int, type: String) {
        if (type.equals("HeightInFeet")) {
            tv_feet.text = "" + value
        } else if (type.equals("HeightInInch")) {
            tv_inch.text = "" + value
        } else if (type.equals("HeightInCm")) {
            tv_cm.text = "" + value
        } else if (type.equals("WeightInLbs")) {
            tv_lbs.text = "" + value
        } else if (type.equals("WeightInKg")) {
            tv_kg.text = "" + value
        } else {

        }
    }

}
