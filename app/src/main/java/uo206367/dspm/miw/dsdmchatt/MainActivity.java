package uo206367.dspm.miw.dsdmchatt;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import uo206367.dspm.miw.dsdmchatt.model.User;
import uo206367.dspm.miw.dsdmchatt.service.AppDatabase;
import uo206367.dspm.miw.dsdmchatt.util.MessageService;

public class MainActivity extends Activity {

    private User userLogged;
    private Fragment loginFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Begin the transaction
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        // Replace the contents of the container with the new fragment
        loginFragment = new LoginFragment();
        ft.add(R.id.login_fragment, loginFragment);
        // or ft.add(R.id.your_placeholder, new FooFragment());
        // Complete the changes added above
        ft.commit();
    }

    @Override
    public void onDestroy() {
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

                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.remove(loginFragment);
                    ft.replace(R.id.chat_fragment, new ChatFragment());
                    ft.commit();
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
        EditText userNameET = findViewById(R.id.login_inputUserName);
        EditText userPasswordET = findViewById(R.id.login_inputPassword);
        if (userNameET.getText().toString() != null && userPasswordET.getText().toString() != null) {
            User user = new User();
            user.setUserName(userNameET.getText().toString());
            user.setPassword(userPasswordET.getText().toString());
            AppDatabase.getAppDatabase(view.getContext()).userDao().insertAll(user);
        }

    }
}
