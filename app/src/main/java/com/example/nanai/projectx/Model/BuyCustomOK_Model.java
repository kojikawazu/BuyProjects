package com.example.nanai.projectx.Model;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.SimpleAdapter;

import com.example.nanai.projectx.Database.BuyDatabaseManager;
import com.example.nanai.projectx.Data.Buy_data;
import com.example.nanai.projectx.Database.EasyDatabaseManager;
import com.example.nanai.projectx.R;
import com.example.nanai.projectx.activity.buy_custom_add;
import com.example.nanai.projectx.activity.buy_custom_ok;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static android.support.v4.provider.FontsContractCompat.FontRequestCallback.RESULT_OK;

/*--------------------------------------------------*/
// 購入後画面モデル
/*--------------------------------------------------*/
public class BuyCustomOK_Model extends BuyCustom_Model {

    /* コンストラクタ */
    public BuyCustomOK_Model(AppCompatActivity activity){
        super(activity);
        _activity.setContentView(R.layout.activity_buy_custom_ok2);
        _database = new BuyDatabaseManager( _activity );
    }


    public void Init(){
        // TODO 初期化
        _lvView = _activity.findViewById(R.id.lvBuyOK);

        Button bt = _activity.findViewById(R.id.btnOKList);
        bt.setOnClickListener(((buy_custom_ok)_activity).createButtonListener());

        //戻るメニューの設定
        ActionBar actionbar = _activity.getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
    }


    private List<Map<String, Object>> getBuyList(ArrayList<Integer> in){
        // TODO 購入リスト取得
        List<Map<String, Object>> buyList = new ArrayList<>();
        ArrayList<Buy_data> buydataList = _database.select(BuyDatabaseManager._TABLE_NAME, in);
        String date = getDate();
        EasyDatabaseManager manager = new EasyDatabaseManager( _activity );

        if(!buydataList.isEmpty()){
            int size = buydataList.size();
            for(int i=0;i<size; i++) {
                Buy_data data = buydataList.get(i);
                buyList.add(createMap( data.getID(), data.getNam(), data.getClas(), data.getNum(), data.getPrice(), data.getDat()) );

                //履歴に追加処理
                manager.insert(BuyDatabaseManager._TABLE_BACK_NAME, data.getNam(), data.getClas(), data.getNum(), data.getDesc(), data.getPrice(), date);
            }
        }
        return buyList;
    }


    public void getIntentData(ArrayList<Integer> in){
        // TODO 遷移データ取得
        List<Map<String, Object>> buyList = getBuyList(in);

        //R.layout.rowメニュー項目それぞれのレイアウト
        SimpleAdapter adapter = new SimpleAdapter( _activity, buyList,
               R.layout.buy_list, BUY_FROM, BUY_TO);

        _lvView.setAdapter(adapter);

        (new EasyDatabaseManager(_activity)).delete(BuyDatabaseManager._TABLE_NAME, in);
    }


    public void onOptionsItemSelected(MenuItem item){
        // TODO オプション選択処理
        switch(item.getItemId()){
            case android.R.id.home:
                _activity.finish();
                break;
        }
    }


    public void goback(){
        // TODO 戻る
        Intent intent = new Intent();
        _activity.setResult(RESULT_OK, intent);
        _activity.finish();
    }
}
