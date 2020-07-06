package com.example.nanai.projectx.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.nanai.projectx.Data.Select_data;
import com.example.nanai.projectx.Model.BuyCustomHome_Model;
import com.example.nanai.projectx.Model.Super_Model;
import com.example.nanai.projectx.Use.Loader;

/*--------------------------------------------------*/
// 購入編集画面
/*--------------------------------------------------*/
public class buy_custom_home extends AppCompatActivity {

    /* --fields-- */
    private Super_Model _model;

    public buy_custom_home.BuyClickListener createClickListener(){
        // TODO クリックリスナー
        return (new buy_custom_home.BuyClickListener());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO 全体生成
        super.onCreate(savedInstanceState);
        _model = (BuyCustomHome_Model)Loader.getModelInstance("BuyCustomHome_Model", this );
        _model.Init();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        // TODO 右上メニュー生成
        _model.onCreateOptionsMenu(menu);

        /* 親クラスの同名メソッドを呼び出し、その戻り値を返却 */
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo){
        // TODO 長押しして操作一覧
        super.onCreateContextMenu(menu, view, menuInfo);
        _model.onCreateContextMenu(menu, view, menuInfo);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        // TODO 右上オプションメニューの結果処理
        _model.onOptionsItemSelected(item);
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // TODO 長押しして操作一覧から得られた結果の処理
        _model.onContextItemSelected(item);
        return super.onContextItemSelected(item);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent){
        // TODO 追加画面から帰ってきた時の処理
        super.onActivityResult(requestCode, resultCode, intent);

        BuyCustomHome_Model md = (BuyCustomHome_Model)_model;
        md.onActivityResult(requestCode, resultCode, intent);
    }

    public void onReturnValue(int new_data){
        // TODO 追加画面から帰ってきた時の処理
        BuyCustomHome_Model md = (BuyCustomHome_Model)_model;
        md.onUpdateNumber(new_data);
    }


    public void onReturnSelectData(Select_data data){
        // TODO データの選択
        BuyCustomHome_Model md = (BuyCustomHome_Model)_model;
        md.onUpdateSelect(data);
    }


    public void onDeleteSelectedData(){
        // TODO データの削除
        BuyCustomHome_Model md = (BuyCustomHome_Model)_model;
        md.onDelete_selectedData();
    }

    public void onIntent_BuyOK(){
        // TODO 購入処理
        BuyCustomHome_Model md = (BuyCustomHome_Model)_model;
        md.onBuy();
    }

    /* クリックリスナー------------------------------------------------------------------------------ */
    /* リストがタップされた時の処理が記述されたメンバクラス                                              */
    private class BuyClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            _model.onClickListener(view);
        }
    }
}
