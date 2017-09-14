package illiyin.mhandharbeni.utilslibrary;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.yarolegovich.lovelydialog.LovelyInfoDialog;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

/**
 * Created by root on 17/07/17.
 */

public class NiceDialog {
    Context context;
    NiceDialogListener niceDialogListener;

    public NiceDialog(Context context, NiceDialogListener niceDialogListener) {
        this.context = context;
        this.niceDialogListener = niceDialogListener;
    }

    public void standart(String message, final int code){
        new LovelyStandardDialog(context)
                .setTopColorRes(R.color.colorAccent)
                .setButtonsColorRes(R.color.colorPrimary)
                .setTitle(message)
                .setPositiveButton(android.R.string.ok, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        niceDialogListener.positiveClicked(code);
                    }
                })
                .setNegativeButton(android.R.string.no, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        niceDialogListener.negativeClicked(code);
                    }
                })
                .show();
    }

    public void info(String title, String message){
        new LovelyInfoDialog(context)
                .setTopColorRes(R.color.colorAccent)
                .setNotShowAgainOptionEnabled(0)
                .setNotShowAgainOptionChecked(true)
                .setTitle(title)
                .setMessage(message)
                .show();
    }
}
