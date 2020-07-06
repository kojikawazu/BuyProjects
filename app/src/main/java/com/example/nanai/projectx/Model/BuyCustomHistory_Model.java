package com.example.nanai.projectx.Model;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.SimpleAdapter;

import com.example.nanai.projectx.Data.Buy_data;
import com.example.nanai.projectx.Database.EasyDatabaseManager;
import com.example.nanai.projectx.Database.SuperDatabaseManager;
import com.example.nanai.projectx.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*--------------------------------------------------*/
// 購入履歴画面モデル
/*--------------------------------------------------*/
public class BuyCustomHistory_Model extends BuyCustom_Model {

    /* コントラクタ */
    public BuyCustomHistory_Model(AppCompatActivity activity){
        super(activity);
        _activity.setContentView(R.layout.activity_buy_custom_history);
    }


    public void Init(){
        // TODO 初期化
        _lvView = _activity.findViewById(R.id.lvBuyHistory);

        //戻るメニューの設定
        ActionBar actionbar = _activity.getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);

        setLayout();
    }


    public void setLayout(){
        // TODO レイアウト設定
        EasyDatabaseManager manager = new EasyDatabaseManager(_activity);
        ArrayList<Buy_data> historyList = manager.allselect(SuperDatabaseManager._TABLE_BACK_NAME);
        List<Map<String, Object>> buyList = new ArrayList<>();

        if(!historyList.isEmpty()){
            int size = historyList.size();
            for(int i=0;i<size; i++) {
                Buy_data data = historyList.get(i);
                buyList.add(createMap( data.getID(), data.getNam(), data.getClas(), data.getNum(), data.getPrice(), data.getDat()) );
            }
        }

        //R.layout.rowメニュー項目それぞれのレイアウト
        SimpleAdapter adapter = new SimpleAdapter( _activity, buyList,
                R.layout.buy_list, BUY_FROM, BUY_TO);

        _lvView.setAdapter(adapter);
    }

    /* オプション選択処理 */
    public void onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case android.R.id.home:
                _activity.finish();
                break;
        }
    }
}
