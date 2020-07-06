package com.example.nanai.projectx.Data;

import static com.example.nanai.projectx.Model.BuyCustomHome_Model.BUY_CLASS_ALL;

/*--------------------------------------------------*/
// 選択データ
/*--------------------------------------------------*/
public class Select_data {
    private boolean _select_flg;      /* セレクトフラグ */
    private String _select_class;     /* セレクトクラス */

    /* 判定 */
    public boolean  IsSelected(){       return _select_flg;  }
    public String   GetSelectClass(){   return _select_class;}


    public Select_data(){
        // TODO コンストラクタ
        _select_flg = false;
        _select_class = BUY_CLASS_ALL;
    }


    public void  setSelect(boolean flg, String cls){
        // TODO setter
        _select_flg = flg;
        _select_class =  (!flg ? BUY_CLASS_ALL : cls);
    }
}
