package uo206367.dspm.miw.dsdmchatt

import android.app.Fragment
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString


class ChatFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    /**
     * // TODO: Rename method, update argument and hook method into UI event
     * public void onButtonPressed(Uri uri) {
     * if (mListener != null) {
     * mListener.onFragmentInteraction(uri);
     * }
     * }
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        /**if (context instanceof OnFragmentInteractionListener) {
         * mListener = (OnFragmentInteractionListener) context;
         * } else {
         * throw new RuntimeException(context.toString()
         * + " must implement OnFragmentInteractionListener");
         * }
         */
    }

    override fun onDetach() {
        super.onDetach()
        //mListener = null;
    }

    companion object {

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ChatFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String, param2: String): ChatFragment {
            return ChatFragment()
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
    /**
     * public interface OnFragmentInteractionListener {
     * // TODO: Update argument type and name
     * void onFragmentInteraction(Uri uri);
     * }
     */
}// Required empty public constructor
