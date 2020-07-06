package com.example.nanai.projectx.Model;

import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.nanai.projectx.Database.BuyDatabaseManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/*--------------------------------------------------*/
// Model基礎クラス
/*--------------------------------------------------*/
public class Super_Model {

    /* fields */
    protected AppCompatActivity _activity;
    protected ListView _lvView;
    protected List<Map<String, String>> _progList;
    protected BuyDatabaseManager _database;
    protected int _programID ;

    /* getter */
    public AppCompatActivity get_activity(){            return _activity;   }
    public ListView getProgramView(){                   return _lvView;     }
    public List<Map<String, String>> getProgramList(){  return _progList;   }
    public BuyDatabaseManager getDatabaseManager(){     return _database;   }
    public int getProgramID(){                          return _programID;  }

    /* setter */
    public void setProgramID(int id){                        this._programID = id;   }

    /* コンストラクタ */
    public Super_Model(AppCompatActivity activity){
        _activity = activity;
    }


    public void Change_data( Super_Model _model ){
        // TODO モデルデータ変更
        _database = _model._database;
        _lvView = null;
        _progList = null;
        _database = null;
        _programID = 0;
    }


    protected int getDataID(){
        // TODO データベースのIDの取得
        Map<String, Object> data = (Map<String, Object>)_lvView.getItemAtPosition(_programID);
        return  (int)data.get(BuyCustomHome_Model.BUY_FROM[0]);
    }


    protected  String getDate(){
        // TODO 日付の取得
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return format.format(new Date());
    }


    protected void showToost(String msg){
        // TODO 通知表示
        Toast.makeText(_activity, msg, Toast.LENGTH_LONG).show();
    }


    protected String getSelectedDesc(){
        // TODO データベースから説明文の取得
        return _database.get_selectID_String(BuyDatabaseManager._TABLE_NAME, BuyDatabaseManager._WORD_LIST[4], getDataID());
    }


    protected int getSelectedNumber(){
        // TODO データベースから個数の取得
        return _database.get_selectID_Int(BuyDatabaseManager._TABLE_NAME, BuyDatabaseManager._WORD_LIST[3], getDataID());
    }


    /* abstract */
    public void Init(){}
    public void Update(){}
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo){}
    public void onCreateOptionsMenu(Menu menu){}
    public void onContextItemSelected(MenuItem item){}
    public void onOptionsItemSelected(MenuItem item){}
    public void goback(){}
    public void onClickListener(View view){}
}
