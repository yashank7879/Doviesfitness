package com.doviesfitness.ui.base


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BaseFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
open class BaseFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    //private var dataManager : AppDataManager? = null
    private var mActivity: BaseActivity? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return TextView(activity).apply {
            setText(" ")
        }
    }


    /*fun getDataManager() : AppDataManager{
         dataManager = AppDataManager.getAppDataManager(activity!!.applicationContext)
        return  dataManager as AppDataManager

    }*/


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is BaseActivity) {
            mActivity = context
        }
    }

    protected fun getBaseActivity(): BaseActivity? {
        return mActivity
    }

    fun setLoading(isload : Boolean){
        mActivity?.setLoading(isload)
    }

    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BaseFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)

                }
            }
    }
}
