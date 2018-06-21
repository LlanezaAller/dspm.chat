package uo206367.dspm.miw.dsdmchatt;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import uo206367.dspm.miw.dsdmchatt.model.User;
import uo206367.dspm.miw.dsdmchatt.service.AppDatabase;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onLogin(View view){
        EditText userNameET = (EditText) findViewById(R.id.login_inputUserName);
        EditText userPasswordET = (EditText) findViewById(R.id.login_inputPassword);
        if(userNameET.getText().toString() != null &&  userPasswordET.getText().toString() != null) {
            User user = new User(userNameET.getText().toString(), userPasswordET.getText().toString());
            User userFromDatabase = AppDatabase.getAppDatabase(view.getContext()).userDao().findByName(user.getUserName());
        }

    }

    public void onRegister(View view){
        EditText userNameET = (EditText) findViewById(R.id.login_inputUserName);
        EditText userPasswordET = (EditText) findViewById(R.id.login_inputPassword);
        if(userNameET.getText().toString() != null &&  userPasswordET.getText().toString() != null) {
            User user = new User(userNameET.getText().toString(), userPasswordET.getText().toString());
            AppDatabase.getAppDatabase(view.getContext()).userDao().insertAll(user);
        }

    }
}
