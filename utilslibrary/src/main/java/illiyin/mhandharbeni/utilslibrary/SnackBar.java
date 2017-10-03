package illiyin.mhandharbeni.utilslibrary;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by root on 9/27/17.
 */

public class SnackBar {
    Context context;
    View view;
    Snackbar snackBar;
    String message;
    int duration;
    String id = "8888";
    public SnackBar(Context context) {
        this.context = context;
    }
    public SnackBar view(View view){
        this.view = view;
        return this;
    }
    public SnackBar message(String message){
        this.message = message;
        return this;
    }
    public SnackBar build(){
        this.snackBar = Snackbar.make(view, message, duration);
        return this;
    }
    public SnackBar show(){
        this.snackBar.show();
        return this;
    }
}
