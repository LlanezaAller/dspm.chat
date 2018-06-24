package uo206367.dspm.miw.dsdmchatt

import android.app.Activity
import android.app.Fragment
import android.graphics.Color
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

import com.google.gson.Gson

import java.util.concurrent.TimeUnit

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString
import uo206367.dspm.miw.dsdmchatt.model.Data
import uo206367.dspm.miw.dsdmchatt.model.Message
import uo206367.dspm.miw.dsdmchatt.model.User
import uo206367.dspm.miw.dsdmchatt.service.AppDatabase
import uo206367.dspm.miw.dsdmchatt.util.MessageService

class MainActivity : Activity() {

    private var userLogged: User? = null
    private var loginFragment: Fragment? = null
    private var chatFragment: Fragment? = null

    private val URL_WS = "ws://localhost:3000"

    //components
    private var output: TextView? = null
    private var errOutput: TextView? = null
    private var input: EditText? = null
    private var client: OkHttpClient? = null
    private var gson: Gson? = null

    private var ws: WebSocket? = null
    private var listener: EchoWebSocketListener? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gson = Gson()

        val ft = fragmentManager.beginTransaction()
        loginFragment = LoginFragment()
        chatFragment = ChatFragment()

        ft.add(R.id.login_fragment, loginFragment)

        output = findViewById(R.id.output)
        input = findViewById(R.id.input)
        val builder = OkHttpClient.Builder()
        builder.connectTimeout(0, TimeUnit.SECONDS)

        client = OkHttpClient.Builder()
                .pingInterval(5000, TimeUnit.MILLISECONDS)
                .build()

        ft.commit()
    }

    public override fun onDestroy() {
        closeConnection()
        ws!!.close(NORMAL_CLOSURE_STATUS, "Bye")
        super.onDestroy()
    }

    fun onLogin(view: View) {
        val userNameET = findViewById<EditText>(R.id.login_inputUserName)
        val userPasswordET = findViewById<EditText>(R.id.login_inputPassword)
        if (userNameET.text.toString().isNotEmpty() && userPasswordET.text.toString().isNotEmpty()) {
            val user = User()
            user.userName = userNameET.text.toString()
            user.password = userPasswordET.text.toString()
            try {
                val userFromDatabase = AppDatabase.getAppDatabase(view.context).userDao().findByName(user.userName)

                if (userFromDatabase != null) {
                    MessageService.createToast(view, R.string.login_connected)
                    userLogged = userFromDatabase
                    val ft = fragmentManager.beginTransaction()
                    ft.remove(loginFragment)
                    ft.replace(R.id.chat_fragment, ChatFragment())
                    ft.commit()
                } else {
                    MessageService.createToast(view, R.string.login_error_information)
                }
            } catch (ex: Exception) {
                MessageService.createToast(view, R.string.login_error_db)
            }

        } else {
            MessageService.createToast(view, R.string.login_error_information)
        }

    }

    fun onRegister(view: View) {
        val userNameET = findViewById<EditText>(R.id.login_inputUserName)
        val userPasswordET = findViewById<EditText>(R.id.login_inputPassword)
        if (userNameET.text.toString() != null && userPasswordET.text.toString() != null) {
            val user = User()
            user.userName = userNameET.text.toString()
            user.password = userPasswordET.text.toString()
            AppDatabase.getAppDatabase(view.context).userDao().insertAll(user)
        }

    }

    private inner class EchoWebSocketListener : WebSocketListener() {


        override fun onOpen(webSocket: WebSocket, response: Response?) {
            val msg = Message("people", Data("connected", userLogged!!.userName, userLogged!!.userName, ""))
            val message = gson!!.toJson(msg)
            webSocket.send(message)
            errOutput(resources.getString(R.string.login_connected), true)

            if (ws == null)
                ws = webSocket
        }

        override fun onMessage(webSocket: WebSocket?, text: String?) {
            val msg = gson!!.fromJson(text, Message::class.java)
            var connect = false
            if (msg.data?.operation == "connected")
                connect = true
            output(msg.printMessage(if (connect) resources.getString(R.string.userConnected) else resources.getString(R.string.userDisconnected)))
        }

        override fun onMessage(webSocket: WebSocket?, bytes: ByteString) {
            output("Receiving bytes : " + bytes.hex())
        }

        override fun onClosing(webSocket: WebSocket?, code: Int, reason: String?) {
            closeConnection()
        }

        override fun onFailure(webSocket: WebSocket?, t: Throwable, response: Response?) {
            errOutput(resources.getString(R.string.login_error_ws), false)
            Log.e("connection", t.message)
        }
    }


    fun start(view: View) {
        if (ws != null && ws!!.request() != null) {
            closeConnection()
        }
        val request = Request.Builder().url(URL_WS).build()
        listener = EchoWebSocketListener()
        ws = client!!.newWebSocket(request, listener)


        client!!.dispatcher().executorService()
    }

    fun send(view: View) {
        if (input == null)
            input = findViewById(R.id.input)

        sendMessage(input!!.text.toString())
    }

    private fun sendMessage(text: String) {
        val message = gson!!.toJson(Message(userLogged!!.userName, text))
        ws!!.send(message)
    }

    private fun output(txt: String) {
        if (output == null) {
            output = findViewById(R.id.output)
            output!!.movementMethod = ScrollingMovementMethod()
        }

        runOnUiThread { output!!.text = output!!.text.toString() + "\n\n" + txt }
    }

    private fun errOutput(txt: String, ok: Boolean) {
        if (errOutput == null) {
            errOutput = findViewById(R.id.status)
            errOutput!!.movementMethod = ScrollingMovementMethod()
            if (ok)
                errOutput!!.setTextColor(Color.GREEN)
            else
                errOutput!!.setTextColor(Color.RED)
        }

        runOnUiThread { errOutput!!.text = txt }
    }

    private fun closeConnection() {
        val msg = Message("people", Data("disconnected", userLogged!!.userName, userLogged!!.userName, ""))
        val message = gson!!.toJson(msg)
        ws!!.send(message)
        ws!!.close(NORMAL_CLOSURE_STATUS, "Bye")
        errOutput(resources.getString(R.string.login_connected), false)
    }

    companion object {

        private val NORMAL_CLOSURE_STATUS = 1000
    }
}
