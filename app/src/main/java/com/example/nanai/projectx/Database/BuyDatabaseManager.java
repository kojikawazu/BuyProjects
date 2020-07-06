package com.example.nanai.projectx.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.os.Debug;
import android.util.Log;

import com.example.nanai.projectx.Data.Buy_data;
import com.example.nanai.projectx.Model.BuyCustomHome_Model;
import com.example.nanai.projectx.R;

import java.util.ArrayList;

import static com.example.nanai.projectx.Model.BuyCustomHome_Model.BUY_CLASS_ALL;

/*--------------------------------------------------*/
// 商品データベースマネージャ
/*--------------------------------------------------*/
public class BuyDatabaseManager extends SuperDatabaseManager {


    public void insert(String table, int in_id,String in_name, String in_class, int in_num, String in_desc, int in_price, String in_date){
        // TODO データの追加処理(ID選択)
        /* データベースヘルパーオブジェクトからデータベース接続オブジェクトを取得 */
        SQLiteDatabase db = getWritableDatabase();

        /* データID入ってたら飛ばす */
        if( IsDataID(db, table, in_id) ) return ;
        super.insert(table, in_id, in_name, in_class, in_num, in_desc, in_price, in_date);
    }


    public void update_int(String table, String colm, int id, int new_data){
        // TODO int型更新処理
        /* データベースヘルパーオブジェクトからデータベース接続オブジェクトを取得 */
        SQLiteDatabase db = getWritableDatabase();

        try{
            /* インサート用SQL文字列の用意 */
            String sql = "UPDATE " + table + " SET " +
                    colm + " = " + new_data + " WHERE " + _WORD_LIST[0] + "=" + id + ";";
            SQLiteStatement stmt = db.compileStatement(sql);

            /* インサートSQLの実行 */
            stmt.executeUpdateDelete();
        }
        finally {
            db.close();
        }
    }


    private String getSelectClassSQL(String table, String cls){
        // TODO ジャンルで選択SQL文の取得
        String sql = "SELECT * FROM "  + table;
        if(cls != BUY_CLASS_ALL){
            sql = sql + " WHERE " + _WORD_LIST[2] + " = \"" + cls + "\";";
        }
        return sql;
    }


    private String getSelectIDSQL(String table, ArrayList<Integer> data){
        // TODO IDで選択SQL文の取得
        String sql = "SELECT * FROM "  + table;
        if(!data.isEmpty()){
            sql = sql + " WHERE " + _WORD_LIST[0] + " IN(" + data.get(0);
            for(int i=1, size=data.size(); i<size; i++){
                sql = sql + "," + data.get(i);
            }
            sql = sql + ");";
        }
        return sql;
    }


    public ArrayList<Buy_data> select(String table, ArrayList<Integer> data){
        // TODO セレクト処理
        /* データベースの取得 */
        SQLiteDatabase db = getWritableDatabase();
        ArrayList<Buy_data> buyList = null;

        try{
            String sql = getSelectIDSQL(table, data);

            /*SQLの実行 */
            Cursor cursor = db.rawQuery(sql, null);
            if(cursor != null)
                buyList = select(cursor);
        }finally {
            db.close();
        }
        return buyList;
    }


    public ArrayList<Buy_data> select(String table, String cls_data){
        // TODO すべてのテーブルデータのリストを取得
        /* データベースの取得 */
        SQLiteDatabase db = getWritableDatabase();
        ArrayList<Buy_data> buyList = null;

        try{
            String sql = getSelectClassSQL(table, cls_data);

            /* SQLの実行 */
            Cursor cursor = db.rawQuery(sql, null);
            if(cursor != null)
                buyList = select(cursor);
        }finally {
            db.close();
        }
        return buyList;
    }


    public String get_selectID_String(String table, String data, int ID){
        // TODO IDに値する説明文の取得(データベース)
        String out = null;
        /* データベースの取得 */
        SQLiteDatabase db = getWritableDatabase();
        try{
            String sql = "SELECT " + data + " FROM "  + table + " WHERE " + _WORD_LIST[0] + " = " + ID;

            /* SQLの実行 */
            Cursor cursor = db.rawQuery(sql, null);

            /* SQL分実行の戻り値であるカーソルオブジェクトをループさせて */
            /* データベース内のデータを取得                          */
            while(cursor.moveToNext()){
                out = cursor.getString(0);
            }
        }finally {
            db.close();
        }

        return out;
    }


    public int get_selectID_Int(String table, String data, int ID){
        // TODO IDに値する個数の取得(データベース)
        int out = 0;
        /* データベースの取得 */
        SQLiteDatabase db = getWritableDatabase();
        try{
            String sql = "SELECT " + data + " FROM "  + table + " WHERE " + _WORD_LIST[0] + " = " + ID;

            /* SQLの実行 */
            Cursor cursor = db.rawQuery(sql, null);

            /* SQL分実行の戻り値であるカーソルオブジェクトをループさせて */
            /* データベース内のデータを取得                          */
            while(cursor.moveToNext()){
                out = cursor.getInt(0);
            }
        }finally {
            db.close();
        }

        return out;
    }


    public BuyDatabaseManager(Context context){
        // TODO コンストラクタ
        super(context);
    }

    /* オーバーライド */
    @Override
    public void onCreate(SQLiteDatabase db){
        setTable(db, _TABLE_NAME);
        setTable(db, _TABLE_BACK_NAME);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){}
}
