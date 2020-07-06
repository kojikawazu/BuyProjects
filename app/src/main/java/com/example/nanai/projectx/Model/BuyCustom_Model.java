package com.example.nanai.projectx.Model;

import android.support.v7.app.AppCompatActivity;
import android.widget.SimpleAdapter;;
import com.example.nanai.projectx.Data.Buy_data;
import com.example.nanai.projectx.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*--------------------------------------------------*/
// 購入編集スーパーモデルクラス
/*--------------------------------------------------*/
public class BuyCustom_Model extends Super_Model {

    /* field */
    protected static  final String[] BUY_FROM = {"buyID","buyName", "buyClass", "buyNumber", "buySelected", "buyPrice", "buyDate"};
    protected static  final int[] BUY_TO = {R.id.lvBuyID, R.id.lvBuyName, R.id.lvBuyClass, R.id.lvBuyNumber, R.id.lvBuySelected, R.id.lvBuyPrice, R.id.lvBuyDate};
    public static final String[] BUY_CLASS_LIST = {"本、雑誌", "飲食物", "家電", "ゲーム", "生活必需品"};

    /* コンストラクタ */
    public BuyCustom_Model(AppCompatActivity activity){
        super(activity);
    }


    protected Map<String, Object> createMap(int buyID, String buyName, String buyClass, int buyNumber, int buyPrice, String buyDate){
        // TODO マッピング処理
        Map<String, Object> city = new HashMap<>();

        city.put(BUY_FROM[0], buyID);
        city.put(BUY_FROM[1], buyName);
        city.put(BUY_FROM[2], buyClass);
        city.put(BUY_FROM[3], buyNumber);
        city.put(BUY_FROM[4], false);
        city.put(BUY_FROM[5], buyPrice);
        city.put(BUY_FROM[6], buyDate);

        return city;
    }


    protected List<Map<String, Object>> getViewDataList(String table, String cls){
        // TODO ビューデータ取得
        List<Map<String, Object>> buyList = new ArrayList<>();
        ArrayList<Buy_data> buy_dataList = _database.select(table, cls);

        if(!buy_dataList.isEmpty()){
            int size = buy_dataList.size();
            for(int i=0;i<size; i++) {
                Buy_data data = buy_dataList.get(i);
                buyList.add(createMap( data.getID(), data.getNam(), data.getClas(), data.getNum(), data.getPrice(), data.getDat()) );
            }
        }
        return buyList;
    }


    protected ArrayList<Integer> getSelectedIDList(){
        // TODO 選択IDリスト取得
        ArrayList<Integer> list = new ArrayList<>();
        SimpleAdapter adapter = (SimpleAdapter)_lvView.getAdapter();
        Map<String, Object> data;
        for(int i=0; i<adapter.getCount(); i++){
            data = (Map<String, Object>)adapter.getItem(i);
            if((boolean)data.get(BUY_FROM[4]) == true){
                list.add((Integer) data.get(BUY_FROM[0]));
            }
        }
        return list;
    }
}
