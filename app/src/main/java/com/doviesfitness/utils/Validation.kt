package com.doviesfitness.utils

import android.content.Context
import android.widget.EditText
import android.widget.Toast
import com.doviesfitness.R

/**
 * Created by Ravi Birla on 20,June,2019
 */
class Validation(private val context: Context) {

    private fun getString(editText: EditText): String {
        return editText.text.toString()
    }


    //New Code
    fun isUserNameValid(editText: EditText): Boolean {
        if (getString(editText).isEmpty()) {
            Constant.showCustomToast(context, context.getString(R.string.firstNameEmptyError))
            return false
        } else if (editText.length() < 3) {
            Constant.showCustomToast(context, context.getString(R.string.firstNameLengthError))
            return false
        } else {
            return true
        }
    }


    fun isPasswordValid(editText: EditText): Boolean {
        if (getString(editText).isEmpty()) {
            editText.requestFocus()
            Constant.showCustomToast(context, context.getString(R.string.passEmptyError))
            return false
        }/* else if (editText.text.length >= 6) {
            editText.requestFocus()
            return true
        }*/ else {
            editText.requestFocus()
            return true
        }
    }


}
