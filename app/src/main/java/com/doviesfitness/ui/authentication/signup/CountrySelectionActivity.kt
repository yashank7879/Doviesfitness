package com.doviesfitness.ui.authentication.signup

import android.os.Bundle
import android.view.View
import com.doviesfitness.ui.base.BaseActivity
import com.doviesfitness.ui.authentication.signup.model.Country
import com.doviesfitness.utils.Utility
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_country_selection.*
import android.support.v7.widget.LinearLayoutManager
import com.doviesfitness.ui.authentication.signup.adapter.CountryAdapter
import kotlin.collections.ArrayList
import android.text.Editable
import android.text.TextWatcher
import android.view.WindowManager
import com.doviesfitness.R
import com.google.gson.reflect.TypeToken


class CountrySelectionActivity : BaseActivity(), View.OnClickListener {

    lateinit var mCountries: ArrayList<Country>
    var countryAdapter: CountryAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_country_selection)

        mCountries = ArrayList()
        val turnsType = object : TypeToken<List<Country>>() {}.type
        val turns = Gson().fromJson<List<Country>>(Utility.loadJSONFromAsset(this, "country_code.json"), turnsType)
        mCountries.addAll(turns)

        etFilterField.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (countryAdapter != null) {
                    countryAdapter?.filter(s.toString())
                    //  customList1.notifyDataSetChanged();
                }
            }

            override fun afterTextChanged(s: Editable) {

            }
        })

        iv_back.setOnClickListener(this)
        setAdapter()
    }

    private fun setAdapter() {
        countryAdapter = CountryAdapter(this, this.mCountries)
        countryListRecyclerView.layoutManager = LinearLayoutManager(this)
        countryListRecyclerView.adapter = countryAdapter

    }

    override fun onBackPressed() {
        super.onBackPressed()
        hideKeyboard()
        finish()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_back -> {
                onBackPressed()
            }

        }
    }
}

