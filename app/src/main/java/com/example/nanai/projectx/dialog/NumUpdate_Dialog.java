package com.example.nanai.projectx.dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Debug;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Adapter;
import android.widget.ArrayAdapter;

import com.example.nanai.projectx.Model.BuyCustomHome_Model;
import com.example.nanai.projectx.Model.Super_Model;
import com.example.nanai.projectx.R;
import com.example.nanai.projectx.activity.buy_custom_add;
import com.example.nanai.projectx.activity.buy_custom_home;

/*--------------------------------------------------*/
// 個数更新ダイアログ
/*--------------------------------------------------*/
public class NumUpdate_Dialog extends Super_dialog {

    /* fields */
    private int _buy_number;

    /* コンストラクタ */
    public NumUpdate_Dialog(){  super();    }
    @SuppressLint("ValidFragment")
    public NumUpdate_Dialog(int in){
        super();
        _buy_number = in;
    }

    /*　ダイアログ生成 */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        FragmentActivity ac = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(ac);
        builder.setTitle(R.string.dialog_number_title);

        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(ac,
                android.R.layout.simple_list_item_single_choice);
        for(Integer i=1;i<=30;i++){
            adapter.add(i);
        }
        builder.setSingleChoiceItems(adapter, _buy_number-1, new NumUpdate_Dialog.OnClickListener());

        AlertDialog dialog = builder.create();
        return dialog;
    }

    /*　クリックリスナー */
    private class OnClickListener implements DialogInterface.OnClickListener{
        @Override
        public void onClick(DialogInterface dialog, int which){
            buy_custom_home ac = (buy_custom_home)getActivity();

            if(_buy_number != (which+1))
                ac.onReturnValue( which+1);

            dialog.dismiss();
        }
    }

}
