package com.example.nanai.projectx.dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.example.nanai.projectx.R;
import com.example.nanai.projectx.activity.buy_custom_home;

/*--------------------------------------------------*/
// 確認ダイアログ
/*--------------------------------------------------*/
public class FinalAnswer_dialog extends Super_dialog {

    /* fields */
    int _messageID;
    int _selectNumber;

    /* コンストラクタ */
    public FinalAnswer_dialog(){  super();    }
    @SuppressLint("ValidFragment")
    public FinalAnswer_dialog(int titleNumber){
        super();
        _selectNumber = titleNumber;
        switch(titleNumber){
            case 1:
                _messageID = R.string.dialog_finalanswer_delete;
                break;
            default:
                _messageID = R.string.dialog_finalanswer_buy;
        }
    }

    /*　ダイアログ生成 */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        FragmentActivity ac = getActivity();

        AlertDialog.Builder builder = new AlertDialog.Builder(ac);

        builder.setTitle(R.string.dialog_finalanswer_title);
        builder.setMessage(_messageID);
        builder.setPositiveButton(R.string.dialog_select_yes, new OnClickListener());
        builder.setNegativeButton(R.string.dialog_select_no, new OnClickListener());

        AlertDialog dialog = builder.create();
        return dialog;
    }

    /*　クリックリスナー */
    private class OnClickListener implements DialogInterface.OnClickListener{
        @Override
        public void onClick(DialogInterface dialog, int which){
            buy_custom_home ac = (buy_custom_home)getActivity();
            switch(which){
                case DialogInterface.BUTTON_POSITIVE:
                    if(_selectNumber == 0)
                        ac.onIntent_BuyOK();
                    else
                        ac.onDeleteSelectedData();
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }
            dialog.dismiss();
        }
    }
}
