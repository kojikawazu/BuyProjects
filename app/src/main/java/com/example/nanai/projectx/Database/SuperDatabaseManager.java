package com.example.nanai.projectx.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.example.nanai.projectx.Data.Buy_data;

import java.util.ArrayList;

/*--------------------------------------------------*/
// データベースマネージャ(スーパー)
/*--------------------------------------------------*/
public class SuperDatabaseManager extends SQLiteOpenHelper {

    /* field */
    private static final String DATABASE_NAME = "buylist_data.db";
    private static final int DATABASE_VERSION = 1;

    public static final String _TABLE_NAME = "buylist_data";
    public static final String _TABLE_BACK_NAME = "buylist_bck_data";

    public static final String[] _WORD_LIST = {
            "_id", "buyName", "buyClass", "buyNumber", "buyDesc", "buyPrice", "buyDate"
    };

    public enum _TABLE_CULMN{
        ID, NAME, CLASS, NUMBER, DESC, PRICE, DATE,
    };

    /* ------------------------------------------------------------------------------------------- */

    /* method */


    protected boolean IsDataID(SQLiteDatabase db, String table, int data_id){
        // TODO テーブルに入ってるデータのIDが存在してるかチェック
        String query = "SELECT * FROM " + table + " WHERE _id = " + data_id + ";";
        Cursor c = db.rawQuery(query, null);
        return (c.moveToNext());
    }


    public int IsSum(String table){
        // TODO テーブルに入ってるデータの合計を取得
        /* データベースの取得 */
        SQLiteDatabase db = getWritableDatabase();
        int num = 0;
        try{
            String sql = "SELECT COUNT(*) FROM "  + table;
            /* SQLの実行 */
            Cursor cursor = db.rawQuery(sql, null);
            if(cursor.moveToNext()) {
                num = cursor.getInt(0);
            }
        }finally {
            db.close();
        }
        return num;
    }


    protected void setTable(SQLiteDatabase db, String table){
        // TODO テーブルのセッティング
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE " + table + " (");
        sb.append(_WORD_LIST[0] + " INTEGER PRIMARY KEY,");
        sb.append(_WORD_LIST[1] + " TEXT,");
        sb.append(_WORD_LIST[2] + " TEXT,");
        sb.append(_WORD_LIST[3] + " INTEGER,");
        sb.append(_WORD_LIST[4] + " TEXT,");
        sb.append(_WORD_LIST[5] + " INTEGER,");
        sb.append(_WORD_LIST[6] + " TEXT");
        sb.append(");");
        String sql = sb.toString();

        /* SQLの実行 */
        db.execSQL(sql);
    }


    protected void insert(String table, int in_id, String in_name, String in_class, int in_num, String in_desc, int in_price, String in_date){
        // TODO データ追加
        /* データベースヘルパーオブジェクトからデータベース接続オブジェクトを取得 */
        SQLiteDatabase db = getWritableDatabase();

        try{
            /* インサート用SQL文字列の用意 */
            String sqlInsert = "INSERT INTO " + table + "(" +
                    _WORD_LIST[0] + ", " + _WORD_LIST[1] + ", " + _WORD_LIST[2] + ", "
                    + _WORD_LIST[3] + ", " + _WORD_LIST[4] + ", "+ _WORD_LIST[5] + ", "
                    + _WORD_LIST[6]+ ") VALUES(?,?,?,?,?,?,?);";
            SQLiteStatement stmt = db.compileStatement(sqlInsert);

            /* 変数のバインド */
            stmt.bindLong(1,    in_id);
            stmt.bindString(2,  in_name);
            stmt.bindString(3,  in_class);
            stmt.bindLong(4,    in_num);
            stmt.bindString(5,  in_desc);
            stmt.bindLong(6,    in_price);
            stmt.bindString(7,  in_date);

            /* インサートSQLの実行 */
            stmt.executeInsert();
        }
        finally {
            db.close();
        }
    }

    /* SQL分実行の戻り値であるカーソルオブジェクトをループさせて */
    /* データベース内のデータを取得 */
    protected ArrayList<Buy_data> select(Cursor cursor){
        ArrayList<Buy_data> buyList = new ArrayList<>();
        while(cursor.moveToNext()){
            Buy_data _data = new Buy_data();
            _data.setData(
                    cursor.getInt(cursor.getColumnIndex( _WORD_LIST[0])),
                    cursor.getString(cursor.getColumnIndex(_WORD_LIST[1])),
                    cursor.getString(cursor.getColumnIndex(_WORD_LIST[2])),
                    cursor.getInt(cursor.getColumnIndex(_WORD_LIST[3])),
                    cursor.getString(cursor.getColumnIndex(_WORD_LIST[4])),
                    cursor.getInt(cursor.getColumnIndex(_WORD_LIST[5])),
                    cursor.getString(cursor.getColumnIndex(_WORD_LIST[6]))
            );
            buyList.add(_data);
        }
        return buyList;
    }

    /* コンストラクタ */
    public SuperDatabaseManager(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /* オーバーライド */
    @Override
    public void onCreate(SQLiteDatabase db){}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){}
}
