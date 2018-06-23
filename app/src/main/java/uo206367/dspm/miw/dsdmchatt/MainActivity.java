package uo206367.dspm.miw.dsdmchatt;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;
import uo206367.dspm.miw.dsdmchatt.model.Data;
import uo206367.dspm.miw.dsdmchatt.model.Message;
import uo206367.dspm.miw.dsdmchatt.model.User;
import uo206367.dspm.miw.dsdmchatt.service.AppDatabase;
import uo206367.dspm.miw.dsdmchatt.util.MessageService;

public class MainActivity extends Activity {

    private User userLogged;
    private Fragment loginFragment;
    private Fragment chatFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gson = new Gson();

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        loginFragment = new LoginFragment();
        chatFragment = new ChatFragment();

        ft.add(R.id.login_fragment, loginFragment);

        output = findViewById(R.id.output);
        input = findViewById(R.id.input);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(0, TimeUnit.SECONDS);

        client = new OkHttpClient.Builder()
                .pingInterval(5000, TimeUnit.MILLISECONDS)
                .build();

        ft.commit();
    }

    @Override
    public void onDestroy() {
        closeConnection();
        ws.close(NORMAL_CLOSURE_STATUS, "Bye");
        super.onDestroy();
    }

    public void onLogin(View view) {
        EditText userNameET = findViewById(R.id.login_inputUserName);
        EditText userPasswordET = findViewById(R.id.login_inputPassword);
        if (userNameET.getText().toString().length() > 0 && userPasswordET.getText().toString().length() > 0) {
            User user = new User();
            user.setUserName(userNameET.getText().toString());
            user.setPassword(userPasswordET.getText().toString());
            try {
                User userFromDatabase = AppDatabase.getAppDatabase(view.getContext()).userDao().findByName(user.getUserName());

                if (userFromDatabase != null) {
                    //User userFromRest = doLogin(userFromDatabase, view);
                    MessageService.createToast(view, R.string.login_connected);
                    userLogged = userFromDatabase;
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.remove(loginFragment);
                    ft.replace(R.id.chat_fragment, new ChatFragment());
                    ft.commit();
                } else {
                    MessageService.createToast(view, R.string.login_error_information);
                }
            } catch (Exception ex) {
                MessageService.createToast(view, R.string.login_error_db);
            }
        } else {
            MessageService.createToast(view, R.string.login_error_information);
        }

    }

    public void onRegister(View view) {
        EditText userNameET = findViewById(R.id.login_inputUserName);
        EditText userPasswordET = findViewById(R.id.login_inputPassword);
        if (userNameET.getText().toString() != null && userPasswordET.getText().toString() != null) {
            User user = new User();
            user.setUserName(userNameET.getText().toString());
            user.setPassword(userPasswordET.getText().toString());
            AppDatabase.getAppDatabase(view.getContext()).userDao().insertAll(user);
        }

    }

    private final String URL_WS = "ws://156.35.98.50:3000";

    //components
    public TextView output;
    public TextView errOutput;
    public EditText input;
    private OkHttpClient client;
    private Gson gson;

    private static final int NORMAL_CLOSURE_STATUS = 1000;
    private final class EchoWebSocketListener extends WebSocketListener {


        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            Message msg = new Message("people", new Data("connected", userLogged.getUserName(), userLogged.getUserName(), ""));
            String message = gson.toJson(msg);
            webSocket.send(message);
            errOutput(getResources().getString(R.string.login_connected), true);

            if(ws == null)
                ws = webSocket;
        }

        @Override
        public void onMessage(WebSocket webSocket, String text) {
            Message msg = gson.fromJson(text, Message.class);
            output(msg.printMessage());
        }

        @Override
        public void onMessage(WebSocket webSocket, ByteString bytes) {
            output("Receiving bytes : " + bytes.hex());
        }

        @Override
        public void onClosing(WebSocket webSocket, int code, String reason) {
            closeConnection();
        }

        @Override
        public void onFailure(WebSocket webSocket, Throwable t, Response response) {
            errOutput(getResources().getString(R.string.login_error_ws), false);
            Log.e("connection", t.getMessage());
        }
    }

    private WebSocket ws;
    private EchoWebSocketListener listener;

    public void start(View view) {
        if (ws != null && ws.request() != null) {
            closeConnection();
        }
            Request request = new Request.Builder().url(URL_WS).build();
            listener = new EchoWebSocketListener();
            ws = client.newWebSocket(request, listener);


            client.dispatcher().executorService();
    }

    public void send(View view) {
        if (input == null)
            input = findViewById(R.id.input);

        sendMessage(input.getText().toString());
    }

    private void sendMessage(String text) {
        String message = gson.toJson(new Message(userLogged.getUserName(), text));
        ws.send(message);
    }

    private void output(final String txt) {
        if (output == null) {
            output = findViewById(R.id.output);
            output.setMovementMethod(new ScrollingMovementMethod());
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                output.setText(output.getText().toString() + "\n\n" + txt);
            }
        });
    }

    private void errOutput(final String txt, boolean ok){
        if (errOutput == null) {
            errOutput = findViewById(R.id.status);
            errOutput.setMovementMethod(new ScrollingMovementMethod());
            if(ok)
                errOutput.setTextColor(Color.GREEN);
            else
                errOutput.setTextColor(Color.RED);
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                errOutput.setText(txt);
            }
        });
    }

    private void closeConnection(){
        Message msg = new Message("people", new Data("disconnected", userLogged.getUserName(), userLogged.getUserName(), ""));
        String message = gson.toJson(msg);
        ws.send(message);
        ws.close(NORMAL_CLOSURE_STATUS, "Bye");
        errOutput(getResources().getString(R.string.login_connected), false);
    }
}
