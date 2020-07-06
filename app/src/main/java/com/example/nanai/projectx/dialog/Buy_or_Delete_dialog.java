package com.example.nanai.projectx.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.example.nanai.projectx.R;
import com.example.nanai.projectx.Use.Loader;
import com.example.nanai.projectx.activity.buy_custom_home;

/*--------------------------------------------------*/
// 購入or削除ダイアログ
/*--------------------------------------------------*/
public class Buy_or_Delete_dialog extends Super_dialog {

    public Buy_or_Delete_dialog(){  super();    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        // TODO　ダイアログ生成
        FragmentActivity ac = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(ac);

        builder.setTitle(R.string.dialog_buyordelete_message);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(ac,
                android.R.layout.simple_list_item_single_choice);
        // adapter.add(R.string.dialog_buyordelete_yes);
        // adapter.add(R.string.dialog_buyordelete_no);
         adapter.add("買う");
         adapter.add("削除する");
        builder.setSingleChoiceItems(adapter,0, new Buy_or_Delete_dialog.OnClickListener());

        AlertDialog dialog = builder.create();
        return dialog;
    }

    /*　クリックリスナー */
    private class OnClickListener implements DialogInterface.OnClickListener{
        @Override
        public void onClick(DialogInterface dialog, int which){
            buy_custom_home ac = (buy_custom_home)getActivity();
            FinalAnswer_dialog dg;

            if(which == 0 || which == 1) {
                dg = (FinalAnswer_dialog) Loader.getDialogInstance("FinalAnswer_dialog", which);
                dg.show(getFragmentManager(), "FinalAnswer_dialog");
            }

            dialog.dismiss();
        }
    }

}
