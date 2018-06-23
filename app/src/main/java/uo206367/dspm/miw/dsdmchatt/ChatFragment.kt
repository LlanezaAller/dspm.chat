package uo206367.dspm.miw.dsdmchatt

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


class ChatFragment : Fragment() {



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }



    companion object {

        fun newInstance(param1: String, param2: String): ChatFragment {
            return ChatFragment()
        }
    }

}// Required empty public constructor
