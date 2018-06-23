package uo206367.dspm.miw.dsdmchatt

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


class LoginFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    companion object {

        fun newInstance(): LoginFragment {
            return LoginFragment()
        }
    }
}
