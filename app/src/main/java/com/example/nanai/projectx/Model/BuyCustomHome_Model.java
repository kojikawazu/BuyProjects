package com.example.nanai.projectx.Model;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.nanai.projectx.Database.BuyDatabaseManager;
import com.example.nanai.projectx.Data.Select_data;
import com.example.nanai.projectx.Database.EasyDatabaseManager;
import com.example.nanai.projectx.R;
import com.example.nanai.projectx.Use.Loader;
import com.example.nanai.projectx.activity.buy_custom_add;
import com.example.nanai.projectx.activity.buy_custom_history;
import com.example.nanai.projectx.activity.buy_custom_home;
import com.example.nanai.projectx.activity.buy_custom_ok;
import com.example.nanai.projectx.dialog.Buy_or_Delete_dialog;
import com.example.nanai.projectx.dialog.Desc_dialog;
import com.example.nanai.projectx.dialog.NumUpdate_Dialog;
import com.example.nanai.projectx.dialog.Selecting_dialog;
import com.example.nanai.projectx.dialog.Super_dialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static android.support.v4.provider.FontsContractCompat.FontRequestCallback.RESULT_OK;
import static com.example.nanai.projectx.Database.BuyDatabaseManager._TABLE_NAME;

/*--------------------------------------------------*/
// 購入編集画面モデル
/*--------------------------------------------------*/
public class BuyCustomHome_Model extends BuyCustom_Model {

    /* fields */
    public static final String BUY_CLASS_ALL = "すべて選択";

    private Select_data _select_data;
    private Button _btnBuy;
    private TextView _tvSum;
    private TextView _tvSumEn;

    private int _buySumCnt;
    private int _buySumYen;

    /* コンストラクタ */
    public BuyCustomHome_Model(AppCompatActivity activity){
        super(activity);

        // DEBUG用
        //_activity.deleteDatabase("buylist_data.db");

        _activity.setContentView(R.layout.activity_buy_custom);
        _database = new BuyDatabaseManager( _activity );
        _select_data = new Select_data();
    }


    public void setup(){
        // TODO すべて選択処理を押された時のデータの処理
        _lvView = _activity.findViewById(R.id.lvBuyList);
        _btnBuy = _activity.findViewById(R.id.btnBuyorDelete);
        _tvSum  = _activity.findViewById(R.id.tvSum);
        _tvSumEn  = _activity.findViewById(R.id.tvSumEn);

        _buySumCnt = 0;
        _buySumYen = 0;
    }

    public void setDatabase(){
        // TODO データベースの仮設定
        String time = this.getDate();

        // debug用
        //_database.insert(BuyDatabaseManager._TABLE_NAME, 1, "独習シリーズ", "本、雑誌", 2, "学習用参考書です", 1000, time);
        //_database.insert(BuyDatabaseManager._TABLE_NAME, 2, "りんご", "飲食物", 1, "赤いまん丸果実です。", 200, time);
    }


    public void setLayout(){
        // TODO レイアウトの設定
        buy_custom_home ac = (buy_custom_home)_activity;

        //戻るメニューの設定
        ActionBar actionbar = ac.getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);

        //長押し操作セット
        ac.registerForContextMenu(_lvView);

        //クリック操作セット
        _btnBuy.setOnClickListener(ac.createClickListener());

        Update();
    }


    public void selectBuyData(){
        // TODO すべて選択しリスト化
        List<Map<String, Object>> buyList = getViewDataList(BuyDatabaseManager._TABLE_NAME, _select_data.GetSelectClass());

        //R.layout.rowメニュー項目それぞれのレイアウト
        SimpleAdapter adapter = new SimpleAdapter( _activity, buyList,
                R.layout.buy_list, BUY_FROM, BUY_TO);

        _lvView.setAdapter(adapter);
        _btnBuy.setEnabled(false);
    }


    private void set_selected_color(int ID, boolean isSelect){
        // TODO 選択モード変更
        View view = _lvView.getChildAt( ID );
        if(isSelect)
            view.setBackgroundResource(R.drawable.selected_no_drawable);
        else
            view.setBackgroundResource(R.drawable.selected_drawable);
    }


    private void change_selected_color(){
        // TODO 選択する背景色の変更
        SimpleAdapter adapter = (SimpleAdapter)_lvView.getAdapter();
        Map<String, Object> data = (Map<String, Object>)adapter.getItem(_programID);
        boolean isSelect = (boolean)data.get(BuyCustomHome_Model.BUY_FROM[4]);
        int count = (int)data.get(BuyCustomHome_Model.BUY_FROM[3]);
        int yen = (int)data.get(BuyCustomHome_Model.BUY_FROM[5]);
        if( isSelect ){
            _buySumCnt -= count;
            _buySumYen -= yen * count;
            if( _buySumCnt < 0 )  _buySumCnt = 0;
            if( _buySumYen < 0 )  _buySumYen = 0;
        }else{
            _buySumCnt += count;
            _buySumYen += yen * count;
        }

        set_selected_color(_programID, isSelect);

        data.remove(BuyCustomHome_Model.BUY_FROM[4]);
        data.put(BuyCustomHome_Model.BUY_FROM[4], !isSelect);
        adapter.notifyDataSetChanged();
    }


    private void change_selected_number(int newCnt){
        // TODO 選択してる商品の個数変更
        SimpleAdapter adapter = (SimpleAdapter)_lvView.getAdapter();
        Map<String, Object> data = (Map<String, Object>)adapter.getItem(_programID);

        boolean isSelect = (boolean)data.get(BuyCustomHome_Model.BUY_FROM[4]);
        if(isSelect){
            int oldCnt = (int)data.get(BuyCustomHome_Model.BUY_FROM[3]);
            int yen = (int)data.get(BuyCustomHome_Model.BUY_FROM[5]);
            _buySumCnt -= oldCnt;
            _buySumCnt += newCnt;
            _buySumYen -= oldCnt * yen;
            _buySumYen += newCnt * yen;
        }

        data.remove(BuyCustomHome_Model.BUY_FROM[3]);
        data.put(BuyCustomHome_Model.BUY_FROM[3], newCnt);
        adapter.notifyDataSetChanged();

        Update();
        showToost( data.get(BUY_FROM[1]) + "の商品の個数が " + newCnt + " に変更されました");
    }


    private void check_selected(){
        // TODO 選択してる商品があるかチェック
        SimpleAdapter adapter = (SimpleAdapter)_lvView.getAdapter();
        Map<String, Object> data;
        boolean isCheck = false;
        for(int i=0, size=adapter.getCount(); i<size; i++){
            data = (Map<String, Object>)adapter.getItem(i);
            if((boolean)data.get(BUY_FROM[4]) == true){
                isCheck = true;
                break;
            }
        }
        _btnBuy.setEnabled(isCheck);
    }

    /* オーバーライド */

    /* 初期化 */
    public void Init(){
        //セットアップ
        setup();

        // データベースの仮登録
        setDatabase();

        //データ用
        selectBuyData();

        //レイアウト用
        setLayout();
    }

    public void Update(){
        _tvSum.setText(String.valueOf(_buySumCnt));
        _tvSumEn.setText(String.valueOf(_buySumYen));
    }


    public void onCreateOptionsMenu(Menu menu){
        // TODO オプションメニューの生成
        buy_custom_home ac = (buy_custom_home)_activity;

        // メニューインフレーターを取得
        MenuInflater inflater = ac.getMenuInflater();
        //オプションメニュー用.xmlファイルをインフレート
        inflater.inflate(R.menu.buy_options_menu, menu);
    }


    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo){
        // TODO コンテキストの生成
        buy_custom_home ac = (buy_custom_home)_activity;

        MenuInflater inflater = ac.getMenuInflater();
        inflater.inflate(R.menu.buy_context_menu, menu);
        menu.setHeaderTitle(R.string.__selecter);
    }


    public void onOptionsItemSelected(MenuItem item){
        // TODO オプションメニューの選択処理
        buy_custom_home ac = (buy_custom_home)_activity;
        int itemID = item.getItemId();

        switch(itemID){
            case android.R.id.home:
                ac.finish();
                break;
                //[すべて表示モード]
            case R.id.menuListOptionAllselect:
                _select_data.setSelect(false, BUY_CLASS_ALL);
                selectBuyData();
                showToost( "[すべて選択モード]になりました" );
                break;
                //[ジャンル検索表示モード]
            case R.id.menuListOptionSearch:
                Super_dialog dialog;
                dialog = (Selecting_dialog)Loader.getDialogInstance("Selecting_dialog",  _select_data);
                dialog.show(ac.getSupportFragmentManager(), "Selecting_dialog");
                break;
                //[追加処理]
            case R.id.menuListOptionInsert:
                Intent intent = new Intent( ac, buy_custom_add.class);
                ac.startActivityForResult(intent, 1000);
                break;
                // [購入履歴]
            case R.id.menuListOptionHistory:
                intent = new Intent( ac, buy_custom_history.class);
                ac.startActivity(intent);
                break;
        }
    }


    public void onContextItemSelected(MenuItem item) {
        // TODO コンテキストメニューの選択処理
        buy_custom_home ac = (buy_custom_home)_activity;

        //長押ししたリストIDの取得
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        _programID = info.position;

        Super_dialog dialog;
        switch (item.getItemId()){
            //[説明文表示]
            case R.id.menuListContext_desc:
                dialog = (Desc_dialog)Loader.getDialogInstance("Desc_dialog", getSelectedDesc());
                dialog.show(ac.getSupportFragmentManager(), "Desc_dialog");
                break;
            //[個数変更]
            case R.id.menuListContext_number:
                dialog = (NumUpdate_Dialog)Loader.getDialogInstance("NumUpdate_Dialog", getSelectedNumber());
                dialog.show(ac.getSupportFragmentManager(), "NumUpdate_Dialog");
                break;
            //[選択or選択解除]
            case R.id.menuListContext_select:
                change_selected_color();
                check_selected();
                Update();
                break;
        }
    }


    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        // TODO 追加画面から戻ってきた時の処理
        //受け取るためのコード
        if( resultCode == RESULT_OK && intent != null){
            if(requestCode == 1000){
                EasyDatabaseManager manager = new EasyDatabaseManager( _activity );

                String name = intent.getStringExtra(    BuyCustomAdd_Model.POST_DATA[0]);
                String cls = intent.getStringExtra(     BuyCustomAdd_Model.POST_DATA[1]);
                int num = intent.getIntExtra(           BuyCustomAdd_Model.POST_DATA[2], 1);
                String desc = intent.getStringExtra(    BuyCustomAdd_Model.POST_DATA[3]);
                int price = intent.getIntExtra(         BuyCustomAdd_Model.POST_DATA[4], 1);

                manager.insert(_TABLE_NAME, name, cls, num, desc, price, getDate());

                selectBuyData();
                showToost("商品が追加されました。");
            }
            else if(requestCode == 2000){
                _select_data.setSelect(false, BuyCustomHome_Model.BUY_CLASS_ALL);
                selectBuyData();
            }
        }
    }


    public void onUpdateNumber(int new_data){
        // TODO 個数変更の処理
        int ID = this.getDataID();
        _database.update_int(BuyDatabaseManager._TABLE_NAME, BuyDatabaseManager._WORD_LIST[3], ID, new_data);
        change_selected_number(new_data);
    }


    public void onUpdateSelect(Select_data data){
        // TODO セレクトモード編集帰ってきた時の処理
        _select_data.setSelect(data.IsSelected(), data.GetSelectClass());
        selectBuyData();

        if( !_select_data.IsSelected() || _select_data.GetSelectClass() == BUY_CLASS_ALL)
            showToost( "[すべて選択モード]に変更してます。");
        else
            showToost( "[ジャンル: " +  data.GetSelectClass() + " 選択モード]に変更してます。");
    }


    @Override
    public void onClickListener(View view){
        // TODO 購入か削除ボタン押された時の処理
        if(_btnBuy.isEnabled()){
            Buy_or_Delete_dialog dialog = (Buy_or_Delete_dialog)Loader.getDialogInstance("Buy_or_Delete_dialog", null);
            dialog.show(_activity.getSupportFragmentManager(), "Buy_or_Delete_dialog");
        }
    }


    public void onBuy(){
        // TODO 購入処理
        ArrayList<Integer> list = getSelectedIDList();
        buy_custom_home ac = (buy_custom_home)_activity;

        Intent intent = new Intent( ac, buy_custom_ok.class);
        intent.putIntegerArrayListExtra("buyList", list);

        //ac.startActivity(intent);
        ac.startActivityForResult(intent, 2000);
        _buySumCnt = 0;
        _buySumYen = 0;
        Update();
    }


    public void onDelete_selectedData(){
        // TODO データ削除処理
        ArrayList<Integer> list = getSelectedIDList();
        (new EasyDatabaseManager(_activity)).delete(BuyDatabaseManager._TABLE_NAME, list);

        selectBuyData();
        showToost(list.size() + "個の商品をリストから削除しました。");
    }
}
