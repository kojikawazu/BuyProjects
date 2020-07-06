package com.example.nanai.projectx.activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.nanai.projectx.Model.BuyCustomAdd_Model;
import com.example.nanai.projectx.Model.BuyCustomHome_Model;
import com.example.nanai.projectx.Model.Super_Model;
import com.example.nanai.projectx.R;
import com.example.nanai.projectx.Use.Loader;
import com.example.nanai.projectx.activity.buy_custom_home;
import com.example.nanai.projectx.dialog.Add_dialog;

/*--------------------------------------------------*/
// 商品追加画面
/*--------------------------------------------------*/
public class buy_custom_add extends AppCompatActivity {

    /* --fields-- */
    Super_Model _model;

    /* --method-- */
    public BuyAdd_ClickListener createButtonListener(){ return new BuyAdd_ClickListener();  }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO 全体生成
        super.onCreate(savedInstanceState);
        _model = (BuyCustomAdd_Model)Loader.getModelInstance("BuyCustomAdd_Model", this);
        _model.Init();
    }


    public void onGoback(){
        // TODO 戻る
        _model.goback();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        // TODO 右上オプションメニューの結果処理
        _model.onOptionsItemSelected(item);
        return super.onOptionsItemSelected(item);
    }

    /* 決定ボタンでデータを元の画面に送り、このアクティビティを終了する。       */
    /* リストがタップされた時の処理が記述されたメンバクラス                  */
    private class BuyAdd_ClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            _model.onClickListener(view);
        }
    }
}
