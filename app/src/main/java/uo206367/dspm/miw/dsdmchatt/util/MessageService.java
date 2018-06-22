package uo206367.dspm.miw.dsdmchatt.util;

import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

public class MessageService {

    public static void createToast(View view,int resKey){
        Toast toast  = Toast.makeText(view.getContext(), resKey, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0,+300);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }
}
