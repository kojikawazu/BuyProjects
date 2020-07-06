package com.example.nanai.projectx.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.example.nanai.projectx.Model.HomeProject_Model;
import com.example.nanai.projectx.Model.Super_Model;
import com.example.nanai.projectx.Use.Loader;

/*--------------------------------------------------*/
// ホーム画面
/*--------------------------------------------------*/
public class HomeProject extends AppCompatActivity {

    /* --fields-- */
    private Super_Model _model;

    /* --method-- */
    public HomeProject.ListItemClickListener createClickListener(){
        return (new HomeProject.ListItemClickListener());
    }

    /* 全体生成 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _model = (HomeProject_Model)Loader.getModelInstance("HomeProject_Model", this );
        _model.Init();
    }

    /* 長押しして操作一覧 */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, view, menuInfo);
        _model.onCreateContextMenu(menu, view, menuInfo);
    }

    /* 長押しして操作一覧から得られた結果の処理 */
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        /* IDのR値による処理の分岐 */
        _model.onContextItemSelected(item);
        return super.onContextItemSelected(item);
    }

    /* リストがタップされた時の処理が記述されたメンバクラス */
    private class ListItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        }
    }
}
