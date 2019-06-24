package com.doviesfitness.ui.authentication.signup

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import android.util.Log
import android.view.View
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.bumptech.glide.Glide
import com.doviesfitness.R
import com.doviesfitness.data.model.LoginResponce
import com.doviesfitness.data.model.SignupInfo
import com.doviesfitness.data.model.UserInfoBean
import com.doviesfitness.ui.base.BaseActivity
import com.doviesfitness.ui.home_tab.HomeTabActivity
import com.doviesfitness.utils.Constant
import com.doviesfitness.utils.Constant.Companion.savebitmap
import com.doviesfitness.utils.Constant.Companion.showCustomToast
import com.doviesfitness.utils.ImageRotator
import kotlinx.android.synthetic.main.activity_add_profile_photo.*
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject
import java.io.File
import java.lang.StringBuilder
import java.util.*
import kotlin.collections.HashMap

class AddProfilePhotoActivity : BaseActivity(), View.OnClickListener {
    var tmpUri: Uri? = null
    var userImageFile: File? = null
    lateinit var signupInfo: SignupInfo
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_profile_photo)
        initViews()
    }

    private fun initViews() {
        signupInfo = intent.getSerializableExtra("SignUpInfo") as SignupInfo
        tv_skip.setOnClickListener(this)
        iv_next_btn.setOnClickListener(this)
        iv_upload.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val imagFile = HashMap<String, File?>()

        when (v?.id) {

            R.id.tv_skip -> {
                signupApi(imagFile)
            }
            R.id.iv_next_btn -> {
                iv_next_btn.isEnabled = false
                if (userImageFile != null) {
                    imagFile.put("profile_pic", userImageFile)
                }
                signupApi(imagFile)
            }
            R.id.iv_upload -> {
                getImagePickerDialog()
            }
        }
    }

    override fun onBackPressed() {
    }

    /*Signup api calling*/
    private fun signupApi(imagFile: HashMap<String, File?>) {
        if (Constant.isNetworkAvailable(this, rl_main)) {
            setLoading(true)
            getDataManager().doSignup(signupInfo, imagFile)?.getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject?) {
                    setLoading(false)
                    try {
                        val json: JSONObject? = response?.getJSONObject("settings")
                        val status = json!!.get("success")
                        val msg = json.get("message")
                        if (status.equals("1")) {
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
                            showCustomToast(this@AddProfilePhotoActivity, "" + msg)
                            finishAffinity()
                            intent = Intent(this@AddProfilePhotoActivity, HomeTabActivity::class.java)
                            intent.putExtra("UserName", data!!.data.get(0).customer_user_name)
                            startActivity(intent)
                            finish()

                     /*Log.e("TAG", response.toString())
                    val loginResponce = getDataManager().mGson?.fromJson(response.toString(), LoginResponce::class.java)
                    val intent = Intent(this@AddProfilePhotoActivity, WelcomeActivity::class.java)
                    startActivity(intent)
                    finish()*/
                        } else {
                            setLoading(false)
                            showCustomToast(this@AddProfilePhotoActivity, "" + msg)
                        }
                    } catch (ec: Exception) {
                        showCustomToast(this@AddProfilePhotoActivity, getString(R.string.something_wrong))
                    }
                }

                override fun onError(anError: ANError?) {
                    showCustomToast(this@AddProfilePhotoActivity, getString(R.string.something_wrong))
                    setLoading(false)
                }
            })
        }
    }

    /*Image picker code*/
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constant.GALLERY && resultCode == Activity.RESULT_OK && data != null) {
            tmpUri = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, tmpUri)
            userImageFile = savebitmap(this, bitmap, UUID.randomUUID().toString() + ".jpg")
            Glide.with(profile_img.context).load(bitmap).into(profile_img)
            // profile_img.setImageBitmap(bitmap)
            profile_img.borderWidth = 0
            iv_placeholder.visibility = View.GONE
        } else if (requestCode == Constant.CAMERA && resultCode == Activity.RESULT_OK) {
            val imageFile = getTemporalFile(this)
            val photoURI = Uri.fromFile(imageFile)
            var bm = Constant.getImageResized(this, photoURI) ///Image resizer
            val rotation = ImageRotator.getRotation(this, photoURI, true)
            bm = ImageRotator.rotate(bm, rotation)
            val profileImagefile = File(this.externalCacheDir, UUID.randomUUID().toString() + ".jpg")
            tmpUri = FileProvider.getUriForFile(
                this, this.applicationContext.packageName + ".fileprovider",
                profileImagefile
            )
            userImageFile =
                savebitmap(getActivity(), bm, StringBuilder().append(UUID.randomUUID()).append(".jpg").toString())
            iv_placeholder.visibility = View.GONE
            profile_img.borderWidth = 0
            profile_img.setImageBitmap(bm)
        }
    }
}
