package com.example.nanai.projectx.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.example.nanai.projectx.Data.Buy_data;

import java.util.ArrayList;

/*--------------------------------------------------*/
// 簡易データベースマネージャ
/*--------------------------------------------------*/
public class EasyDatabaseManager extends SuperDatabaseManager {


    public void insert(String table, String in_name, String in_class, int in_num, String in_desc, int in_price, String in_date) {
        // TODO データの追加処理(ID未選択)
        /* データベースヘルパーオブジェクトからデータベース接続オブジェクトを取得 */
        SQLiteDatabase db = getWritableDatabase();
        int id=1;
        while( IsDataID(db, table, id) ){
            id++;
        }
        insert(table, id, in_name, in_class, in_num, in_desc, in_price, in_date);
    }


    public void delete(String table, ArrayList<Integer> list){
        // TODO データのすべて削除
        SQLiteDatabase db = getWritableDatabase();
        try{
            //まず、リストで選択されたカクテルのメモデータを削除。その後インサートを行う
            //削除用SQL文字列を用意
            String sqlDelete = "DELETE FROM "+ table + " WHERE " + _WORD_LIST[0] + " IN(";

            sqlDelete = sqlDelete + list.get(0);
            if(list.size() != 1){
                for(int i=1, size=list.size(); i<size; i++){
                    sqlDelete = sqlDelete + "," + list.get(i);
                }
            }
            sqlDelete = sqlDelete + ");";

            //SQL文字列をもとにプリベアドステートメントを取得
            SQLiteStatement stmt = db.compileStatement(sqlDelete);
            //削除SQLの実行
            stmt.executeUpdateDelete();
        }
        finally {
            db.close();
        }
    }


    public ArrayList<Buy_data> allselect(String table){
        // TODO データのすべて取得
        SQLiteDatabase db = getWritableDatabase();
        ArrayList<Buy_data> buyList = null;

        try{
            String sql = "SELECT * FROM "  + table;

            /* SQLの実行 */
            Cursor cursor = db.rawQuery(sql, null);
            if(cursor != null)
                buyList = select(cursor);
        }finally {
            db.close();
        }
        return buyList;
    }

    /* コンストラクタ */
    public EasyDatabaseManager(Context context){
        super(context);
    }

    /* オーバーライド */
    @Override
    public void onCreate(SQLiteDatabase db){}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){}
}
