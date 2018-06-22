package uo206367.dspm.miw.dsdmchatt;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import uo206367.dspm.miw.dsdmchatt.model.User;
import uo206367.dspm.miw.dsdmchatt.service.AppDatabase;
import uo206367.dspm.miw.dsdmchatt.util.MessageService;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onLogin(View view) {
        EditText userNameET = (EditText) findViewById(R.id.login_inputUserName);
        EditText userPasswordET = (EditText) findViewById(R.id.login_inputPassword);
        if (userNameET.getText().toString().length() > 0 && userPasswordET.getText().toString().length() > 0) {
            User user = new User();
            user.setUserName(userNameET.getText().toString());
            user.setPassword(userPasswordET.getText().toString());
            try {
                User userFromDatabase = AppDatabase.getAppDatabase(view.getContext()).userDao().findByName(user.getUserName());

                if (userFromDatabase != null) {
                    //User userFromRest = doLogin(userFromDatabase, view);
                    MessageService.createToast(view, R.string.login_connected);
                    ConstraintLayout chatLayout = findViewById(R.id.chatLayout);
                    ConstraintLayout loginView = findViewById(R.id.chatLayout);

                    loginView.setVisibility(View.GONE);
                    chatLayout.setVisibility(View.VISIBLE);
                } else {
                    MessageService.createToast(view, R.string.login_error_information);
                }
            }catch (Exception ex){
                MessageService.createToast(view, R.string.login_error_db);
            }
        } else{
            MessageService.createToast(view, R.string.login_error_information);
        }

    }

    public void onRegister(View view) {
        EditText userNameET = (EditText) findViewById(R.id.login_inputUserName);
        EditText userPasswordET = (EditText) findViewById(R.id.login_inputPassword);
        if (userNameET.getText().toString() != null && userPasswordET.getText().toString() != null) {
            User user = new User();
            user.setUserName(userNameET.getText().toString());
            user.setPassword(userPasswordET.getText().toString());
            AppDatabase.getAppDatabase(view.getContext()).userDao().insertAll(user);
        }

    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }
    /**
    public User doLogin(User user, View view){
        NetConnection doConnection = new NetConnection();
        User result = doConnection.doInBackground(view);
    }

    private class NetConnection extends  AsyncTask<View, Integer, User>{

        @Override
        protected User doInBackground(View... views) {
            try {
                // Create URL
                URL loginEndpoint = new URL("https://api.github.com/");

                // Create connection
                HttpsURLConnection myConnection =
                        (HttpsURLConnection) loginEndpoint.openConnection();



                return null;
            }catch (Exception ex){
                MessageService.createToast(views[0], R.string.login_error_information);
            }
        }
    }
     **/
}
