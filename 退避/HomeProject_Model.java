package com.example.nanai.projectx.Model;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.nanai.projectx.Database.BuyDatabaseManager;
import com.example.nanai.projectx.R;
import com.example.nanai.projectx.activity.HomeProject;
import com.example.nanai.projectx.activity.buy_custom_history;
import com.example.nanai.projectx.activity.buy_custom_home;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/*--------------------------------------------------*/
// ホームモデルクラス
/*--------------------------------------------------*/
public class HomeProject_Model extends Super_Model {

    /* fields */
    public static  final String[] PROG_FROM = {"programName", "name"};
    public static  final int[] PROG_TO= {R.id.tvProgName, R.id.tvProgMenu};

    private String[][] _prog_data = {
            {"買いたいものリスト", "SQLiteを使ったデータベースプログラムです。"},
            {"購入履歴", "買いたいものリストから購入したものを履歴に残すプログラムです。"},
            {"サンプル_2", "作成中のサンプルプログラムです。"},
            {"サンプル_3", "作成中のサンプルプログラムです。"},
            {"サンプル_4", "作成中のサンプルプログラムです。"},
            {"サンプル_5", "作成中のサンプルプログラムです。"},
            {"サンプル_6", "作成中のサンプルプログラムです。"},
            {"サンプル_7", "作成中のサンプルプログラムです。"},
            {"サンプル_8", "作成中のサンプルプログラムです。"},
            {"サンプル_9", "作成中のサンプルプログラムです。"},
            {"サンプル_10", "作成中のサンプルプログラムです。"},
    };

    /* コンストラクタ */
    public HomeProject_Model(AppCompatActivity activity){
        super(activity);
        _activity.deleteDatabase("buylist_data.db");
        _activity.setContentView(R.layout.activity_home_project);
        _database = new BuyDatabaseManager( _activity );
    }

    /* 初期化 */
    public void Init(){
        setListView();
        setLayout();
        setDatabase();
    }

    /* マップ生成 */
    private Map<String, String> createMap(String programName, String name){
        Map<String, String> city = new HashMap<>();

        city.put(HomeProject_Model.PROG_FROM[0], programName);
        city.put(HomeProject_Model.PROG_FROM[1], name);

        return city;
    }

    /*　選択IDの取得 */
    public int getItemID(MenuItem item){
        /* 長押しされたビューに関する情報が格納されたオブジェクトを取得 */
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        /* 長押しされたリストのポジションを取得 */
        _programID = info.position;
        /* ポジションから長押しされたメニュー情報Mapオブジェクトを取得 */
        Map<String, String> menu = _progList.get(_programID);
        /* 選択されたメニューのIDを取得 */
        int itemID = item.getItemId();

        return itemID;
    }

    /* リストビュー設定 */
    public void setListView(){
        HomeProject ac = (HomeProject)_activity;
        _lvView = ac.findViewById(R.id.lvProgList);
        _progList = new ArrayList<>();

        for(int i=0; i<_prog_data.length; i++){
            _progList.add(createMap(_prog_data[i][0], _prog_data[i][1]));
        }

        /* R.layout.rowメニュー項目それぞれのレイアウト */
        SimpleAdapter adapter = new SimpleAdapter( ac, _progList,
                R.layout.program_list, PROG_FROM, PROG_TO);

        _lvView.setAdapter(adapter);
    }

    /* レイアウト設定 */
    public void setLayout(){
        HomeProject ac = (HomeProject)_activity;
        /* フッターに日付埋め込み処理 */
        TextView view = ac.findViewById(R.id.tv_home_footer);
        view.setText(new Date().toString());

        /* 長押し操作セット */
        ac.registerForContextMenu(_lvView);

        /* クリック操作セット */
        _lvView.setOnItemClickListener(ac.createClickListener());
    }

    /* データベースの仮設定 */
    public void setDatabase(){
        /* テスト用 */
        String time = this.getDate();
        _database.insert(BuyDatabaseManager._TABLE_NAME, 1,"独習シリーズ", "本、雑誌", 2, "学習用参考書です", 1000,time);
        _database.insert(BuyDatabaseManager._TABLE_NAME,2,"りんご", "飲食物", 1, "赤いまん丸果実です。",200, time);
    }

    /* 画面遷移 */
    public void changeActivity(){
        Intent intent = null;
        /* インテントオブジェクトを生成 */
        switch(_programID){
            case 0: /* 購入画面 */
                intent = new Intent( _activity, buy_custom_home.class);
                break;
            case 1: /* 購入履歴画面 */
                intent = new Intent( _activity, buy_custom_history.class);
                break;
        }

        if(intent != null) {
            /* 次画面の起動 */
            _activity.startActivity(intent);
        }
    }

    /* 長押しして操作一覧 */
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo){
        HomeProject ac = (HomeProject)_activity;

        MenuInflater inflater = ac.getMenuInflater();
        inflater.inflate(R.menu.home_context_menu, menu);
        menu.setHeaderTitle(R.string.menu_home_context_header);
    }

    /* 長押しして操作一覧から得られた結果の処理 */
    public void onContextItemSelected(MenuItem item){
        if(getItemID(item) == R.id.menuHomeContextYes){
            changeActivity();
        }
    }
}
