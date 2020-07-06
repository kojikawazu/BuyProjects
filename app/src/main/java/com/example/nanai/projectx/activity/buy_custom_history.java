package com.example.nanai.projectx.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.nanai.projectx.Model.BuyCustomAdd_Model;
import com.example.nanai.projectx.Model.BuyCustomHistory_Model;
import com.example.nanai.projectx.Model.Super_Model;
import com.example.nanai.projectx.R;
import com.example.nanai.projectx.Use.Loader;

/*--------------------------------------------------*/
// 購入履歴画面
/*--------------------------------------------------*/
public class buy_custom_history extends AppCompatActivity {

    /* --fields-- */
    Super_Model _model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO 全体生成
        super.onCreate(savedInstanceState);
        _model = (BuyCustomHistory_Model) Loader.getModelInstance("BuyCustomHistory_Model", this);
        _model.Init();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        // TODO 右上オプションメニューの結果処理
        _model.onOptionsItemSelected(item);
        return super.onOptionsItemSelected(item);
    }
}
