package com.example.nanai.projectx.dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.example.nanai.projectx.R;

/*--------------------------------------------------*/
// 説明ダイアログ
/*--------------------------------------------------*/
public class Desc_dialog  extends Super_dialog {

    String _desc;

    public Desc_dialog(){   super();    }
    @SuppressLint("ValidFragment")
    public Desc_dialog(String in){
        super();
        _desc = in;
    }

    /*　ダイアログ生成 */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.dialog_desc_title);
        builder.setMessage(_desc);
        builder.setPositiveButton(R.string.dialog_select_yes, new Desc_dialog.OnClickListener());

        AlertDialog dialog = builder.create();
        return dialog;
    }

    /* クリックリスナー */
    private class OnClickListener implements DialogInterface.OnClickListener{
        @Override
        public void onClick(DialogInterface dialog, int which){
            switch(which){
                case DialogInterface.BUTTON_POSITIVE:
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }
        }
    }
}
