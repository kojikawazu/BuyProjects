package com.example.nanai.projectx.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.nanai.projectx.Model.BuyCustomAdd_Model;
import com.example.nanai.projectx.Model.BuyCustomOK_Model;
import com.example.nanai.projectx.Model.Super_Model;
import com.example.nanai.projectx.R;
import com.example.nanai.projectx.Use.Loader;

import java.util.ArrayList;

/*--------------------------------------------------*/
// 購入後画面
/*--------------------------------------------------*/
public class buy_custom_ok extends AppCompatActivity {

    /* --fields-- */
    Super_Model _md;

    /* --method-- */
    public buy_custom_ok.BuyOK_ClickListener createButtonListener(){ return new buy_custom_ok.BuyOK_ClickListener();  }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO 全体生成
        super.onCreate(savedInstanceState);
        _md = (BuyCustomOK_Model)Loader.getModelInstance("BuyCustomOK_Model", this);
        _md.Init();

        ((BuyCustomOK_Model)_md).getIntentData(getIntent().getIntegerArrayListExtra("buyList"));
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        // TODO 右上オプションメニューの結果処理
        _md.onOptionsItemSelected(item);
        return super.onOptionsItemSelected(item);
    }

    /* クリックリスナー------------------------------------------------------------------------------ */
    /* リストがタップされた時の処理が記述されたメンバクラス                                              */
    private class BuyOK_ClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            _md.goback();
        }
    }
}
