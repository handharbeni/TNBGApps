package illiyin.mhandharbeni.utilslibrary;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

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
    private SnackBarListener snackBarListener;
    private View views;
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
    private SnackBar position(int position){
        if (position == Gravity.BOTTOM){
            views = this.snackBar.getView();
            FrameLayout.LayoutParams params =(FrameLayout.LayoutParams)view.getLayoutParams();
            params.gravity = Gravity.BOTTOM;
            views.setLayoutParams(params);
        }else{
            views = this.snackBar.getView();
            FrameLayout.LayoutParams params =(FrameLayout.LayoutParams)view.getLayoutParams();
            params.gravity = Gravity.TOP;
            views.setLayoutParams(params);
        }
        return this;
    }
    public SnackBar listener(final SnackBarListener snackBarListener){
        this.snackBarListener = snackBarListener;
        this.snackBar.addCallback(new Snackbar.Callback(){
            @Override
            public void onDismissed(Snackbar transientBottomBar, int event) {
                super.onDismissed(transientBottomBar, event);
                snackBarListener.onDismiss();
            }
        });
        return this;
    }
    public SnackBar build(){
        this.snackBar = Snackbar.make(view, message, duration);
        this.position(Gravity.TOP);
        return this;
    }
    public SnackBar build(int position){
        this.snackBar = Snackbar.make(view, message, duration);
        this.position(position);
        return this;
    }
    public SnackBar show(){
        this.snackBar.show();
        return this;
    }
}
