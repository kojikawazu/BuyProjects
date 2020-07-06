package com.example.nanai.projectx.dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Switch;

import com.example.nanai.projectx.Data.Select_data;
import com.example.nanai.projectx.Model.BuyCustomAdd_Model;
import com.example.nanai.projectx.Model.BuyCustomHome_Model;
import com.example.nanai.projectx.Model.Super_Model;
import com.example.nanai.projectx.R;
import com.example.nanai.projectx.activity.buy_custom_home;

/*--------------------------------------------------*/
// 編集ダイアログ
/*--------------------------------------------------*/
public class Selecting_dialog extends Super_dialog {

    /* fields */
    private View  _layout;
    private Spinner _spinner;
    private Switch _sw;

    private boolean _selected_flg;
    private String  _selected_class;

    /* コンストラクタ */
    public Selecting_dialog(){  super(); }
    @SuppressLint("ValidFragment")
    public Selecting_dialog(Select_data in){
        super();

        _selected_flg = in.IsSelected();
        _selected_class = in.GetSelectClass();
    }

    /* スピナーレイアウトのセッティング */
    private void setSpinner(FragmentActivity ac){
        /* 部品の取得 */
        _spinner = _layout.findViewById(R.id.sper_select);
        _sw = _layout.findViewById(R.id.select_switch);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(ac,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        adapter.add(BuyCustomHome_Model.BUY_CLASS_ALL );
        for(int i=0, size=BuyCustomHome_Model.BUY_CLASS_LIST.length; i<size; i++){
            adapter.add(BuyCustomHome_Model.BUY_CLASS_LIST[i]);
        }

        _spinner.setAdapter(adapter);
    }

    /* データのセッティング */
    private void setData(){
        _spinner.setEnabled(_selected_flg);
        _sw.setChecked(_selected_flg);

        if(_selected_class == BuyCustomHome_Model.BUY_CLASS_ALL){
            _spinner.setSelection(0);
        }
        else {
            for(int i=0, size=BuyCustomHome_Model.BUY_CLASS_LIST.length; i<size; i++){
                if(_selected_class == BuyCustomHome_Model.BUY_CLASS_LIST[i]){
                    _spinner.setSelection(i+1);
                    return ;
                }
            }
        }
    }

    /* クリック操作のセッティング */
    private void setClickListener(){
        _sw.setOnCheckedChangeListener(new SwitchButtonClickListener() );
        _spinner.setOnItemSelectedListener(new SpinnerItemSelectedListener());
    }

    /*　ダイアログ生成 */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        //アラートダイアログを生成
        FragmentActivity ac = getActivity();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //カスタムビューを設定
        LayoutInflater inflater = (LayoutInflater)ac.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //レイアウトのインフレーター
        _layout = inflater.inflate(R.layout.selecting_dialog_layout, (ViewGroup)ac.findViewById(R.id.dialog_root));
        //ダイアログのレイアウトを適用
        builder.setView(_layout);

        builder.setPositiveButton(R.string.dialog_select_yes, new Selecting_dialog.DialogButtonClickListener());
        AlertDialog dialog = builder.create();

        /* 設定 */
        setSpinner(ac);
        setData();
        setClickListener();

        return dialog;
    }

    /* オンオフが切り替わった時の処理のクラスリスナー */
    private class SwitchButtonClickListener implements CompoundButton.OnCheckedChangeListener{
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            _spinner.setEnabled(_selected_flg = isChecked);
        }
    }

    /* ボタンのリスナー */
    private class DialogButtonClickListener implements DialogInterface.OnClickListener{
        @Override
        public void onClick(DialogInterface dialog, int which){
            switch(which){
                case DialogInterface.BUTTON_POSITIVE:

                    Select_data data = new Select_data();
                    data.setSelect(_selected_flg, _selected_class);

                    buy_custom_home ac = (buy_custom_home)getActivity();
                    ac.onReturnSelectData(data);

                    dialog.dismiss();
                    break;
            }
        }
    }

    /* スピナーのリスナー */
    private class SpinnerItemSelectedListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
            _selected_class = (position == 0 ?
                                BuyCustomHome_Model.BUY_CLASS_ALL
                                : BuyCustomHome_Model.BUY_CLASS_LIST[position-1]);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent){

        }
    }
}
