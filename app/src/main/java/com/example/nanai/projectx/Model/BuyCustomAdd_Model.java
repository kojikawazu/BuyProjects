package com.example.nanai.projectx.Model;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.nanai.projectx.R;
import com.example.nanai.projectx.Use.Loader;
import com.example.nanai.projectx.activity.buy_custom_add;
import com.example.nanai.projectx.dialog.Add_dialog;

import static android.support.v4.provider.FontsContractCompat.FontRequestCallback.RESULT_OK;

/*--------------------------------------------------*/
// 商品追加モデル
/*--------------------------------------------------*/
public class BuyCustomAdd_Model extends Super_Model {

    /*--fields--*/
    //ジャンルドロップダウンリスト
    private EditText _etName;
    private Spinner _sp_buy_class;
    private EditText _etNumber;
    private EditText _etPrice;
    private EditText _etDesc;
    private Button _btn_buy;

    //--data--
    private String _name;
    private String _desc;
    private int _number;
    private int _price;

    //--確認ダイアログ--
    private Add_dialog _dialog;
    public static  final String[] POST_DATA = { "buy_Name", "buy_Class", "buy_Number", "buy_Desc", "buy_Price"};

    /* コンストラクタ */
    public BuyCustomAdd_Model(AppCompatActivity activity){
        super(activity);
        _activity.setContentView(R.layout.activity_buy_custom_add);
    }


    private void setSpiiner(){
        // TODO ジャンルのセッティング
        ArrayAdapter<String> adapter = new ArrayAdapter<>(_activity,  R.layout.support_simple_spinner_dropdown_item,
                BuyCustomHome_Model.BUY_CLASS_LIST);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        _sp_buy_class.setAdapter(adapter);
    }


    public void setLayout(){
        // TODO レイアウトのセッティング
        buy_custom_add ac = (buy_custom_add)_activity;
        _dialog = null;
        _etName = ac.findViewById((R.id.et_buyName));
        _sp_buy_class = ac.findViewById(R.id.sp_buy_class);
        _etPrice = ac.findViewById(R.id.et_buyPrice);
        _etNumber = ac.findViewById(R.id.et_buyNumber);
        _etDesc = ac.findViewById(R.id.etDesc);
        _btn_buy = ac.findViewById(R.id.btn_menu_custom_add);

        //戻るメニューの設定
        ActionBar actionbar = ac.getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);

        _btn_buy.setOnClickListener(ac.createButtonListener());

        setSpiiner();
    }


    public void goback(){
        // TODO 戻る
        Intent intent = new Intent();
        intent.putExtra( POST_DATA[0], _name);
        intent.putExtra( POST_DATA[1], _sp_buy_class.getSelectedItem().toString());
        intent.putExtra( POST_DATA[2], _number);
        intent.putExtra( POST_DATA[3], _desc);
        intent.putExtra( POST_DATA[4], _price);

        _activity.setResult(RESULT_OK, intent);
        _activity.finish();
    }


    public void Init(){
        // TODO 初期化
        setLayout();
    }


    public void onOptionsItemSelected(MenuItem item){
        // TODO オプション選択処理
        switch(item.getItemId()){
            case android.R.id.home:
                _activity.finish();
                break;
        }
    }

    /* クリックリスナー */
    public void onClickListener(View view){
        buy_custom_add ac = (buy_custom_add)_activity;

        _name = _etName.getText().toString();
        String num = _etNumber.getText().toString();
        _desc = _etDesc.getText().toString();
        String price = _etPrice.getText().toString();
        if( _name.isEmpty()  || num.isEmpty() || _desc.isEmpty() || price.isEmpty()){
            return ;
        }

        _number = Integer.parseInt(num);
        _price = Integer.parseInt(price);
        if(_number <= 0 || _price <= 0){
            return ;
        }

        _dialog = (Add_dialog)Loader.getDialogInstance("Add_dialog", null);
        _dialog.show(ac.getSupportFragmentManager(), "Add_dialog");
    }

}
