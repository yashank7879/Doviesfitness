package com.doviesfitness.ui.base

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.SystemClock
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.content.FileProvider
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.inputmethod.InputMethodManager
import com.doviesfitness.utils.Constant
import com.doviesfitness.utils.ProgressDialog
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

open class BaseActivity : AppCompatActivity() {
    private var tmpUri: Uri? = null
    val TAG: String = BaseActivity::class.java.name
    private var progressDialog: ProgressDialog? = null

    private var activity: Activity = this
    private var mLastClickTime: Long = 0


    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        progressDialog = ProgressDialog(this)
    }

    fun setLoading(isLoading: Boolean) {
        if (isLoading) progressDialog?.show() else progressDialog?.hide()
    }

    fun getActivity(): Activity {
        return activity
    }

   /* fun getDataManager(): AppDataManager {
        *//* dataManager = AppDataManager.getAppDataManager(activity)
         return dataManager as AppDataManager*//*
        return Livewire.getDataManager()
    }*/



    /*
    replace fragment
    //1 @param - fragmentHolder : Fragment Name
    //2 @param- layoutId : Container Id(FrameLayout)

    */
    fun replaceFragment(fragmentHolder: Fragment, layoutId: Int?) {
        try {
            val fragmentManager = supportFragmentManager
            fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            val fragmentName = fragmentHolder.javaClass.name
            val fragmentTransaction = fragmentManager.beginTransaction()

            fragmentTransaction.replace(layoutId!!, fragmentHolder, fragmentName)
            fragmentTransaction.addToBackStack(fragmentName)
            fragmentTransaction.commit()
            hideKeyboard()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    /*
   replace fragment
   //1 @param - fragmentHolder : Fragment Name
   //2 @param- layoutId : Container Id(FrameLayout)
   //3 @param - addToBackStack : true/false (add to back stack)
   */
    fun addFragment(fragment: Fragment, layoutId: Int, addToBackStack: Boolean) {
        try {
            val fragmentName = fragment.javaClass.name

            val fragmentTransaction = supportFragmentManager.beginTransaction()

            /*  fragmentTransaction.setCustomAnimations(
                  R.anim.enter_from_right,
                  R.anim.exit_to_left,
                  R.anim.enter_from_left,
                  R.anim.exit_to_right
              )*/
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.enterTransition = null
            }
            fragmentTransaction.add(layoutId, fragment, fragmentName)
            if (addToBackStack) fragmentTransaction.addToBackStack(fragmentName)
            fragmentTransaction.commit()

            hideKeyboard()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun hideKeyboard() {
        if (currentFocus == null) return
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
    }


    fun getImagePickerDialog() {
        val options = arrayOf<CharSequence>("Take Photo", "Choose from Gallery")

        val builder = AlertDialog.Builder(this@BaseActivity)

        builder.setTitle("Add Photo")

        builder.setItems(options) { dialog, which ->
            if (options[which] == "Take Photo") {

                val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                //***** Ensure that there's a camera activity to handle the intent ****//
                if (takePictureIntent.resolveActivity(packageManager) != null) {
                    //** Create the File where the photo should go *****//
                    var photoFile: File? = null
                    try {
                        photoFile = createImageFile()
                    } catch (ex: IOException) {
                        Log.d(TAG, ex.message)
                    }

                    if (photoFile != null) {
                        tmpUri = FileProvider.getUriForFile(
                            this@BaseActivity,
                            applicationContext.packageName + ".fileprovider",
                            getTemporalFile(this@BaseActivity)
                        )
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, tmpUri)
                        startActivityForResult(takePictureIntent, Constant.CAMERA)
                    }
                }

            } else if (options[which] == "Choose from Gallery") {
                val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(intent, Constant.GALLERY)
            }
        }
        builder.show()
    }


    private fun createImageFile(): File? {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val storageDir = externalCacheDir
        return File.createTempFile(
            imageFileName, /* prefix */
            ".jpg", /* suffix */
            storageDir      /* directory */
        )

    }

    fun getTemporalFile(baseActivity: BaseActivity): File {
        return File(baseActivity.getExternalCacheDir(), "tmp.jpg")
    }


    fun firstClick(){
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
            return
        } else {
            mLastClickTime = SystemClock.elapsedRealtime()

        }
    }
}