package com.example.nanai.projectx.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.example.nanai.projectx.Model.Super_Model;
import com.example.nanai.projectx.R;
import com.example.nanai.projectx.activity.buy_custom_add;

/*--------------------------------------------------*/
// 追加ダイアログ
/*--------------------------------------------------*/
public class Add_dialog extends Super_dialog {

    public Add_dialog(){    super();    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        // TODO　ダイアログ生成
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.dialog_add_title);
        builder.setMessage(R.string.dialog_add_message);
        builder.setPositiveButton(R.string.dialog_select_yes, new OnClickListener());
        builder.setNegativeButton(R.string.dialog_select_no, new OnClickListener());

        AlertDialog dialog = builder.create();
        return dialog;
    }

    /*　クリックリスナー */
    private class OnClickListener implements DialogInterface.OnClickListener{
        @Override
        public void onClick(DialogInterface dialog, int which){
            switch(which){
                case DialogInterface.BUTTON_POSITIVE:
                    buy_custom_add ac = (buy_custom_add)getActivity();
                    ac.onGoback();
                    dialog.dismiss();
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }
        }
    }
}
