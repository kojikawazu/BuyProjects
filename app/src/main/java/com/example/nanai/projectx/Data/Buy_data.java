package com.example.nanai.projectx.Data;

/*--------------------------------------------------*/
// 購入データ
/*--------------------------------------------------*/
public class Buy_data {
    private  int        _id;                     /* 商品ID */
    private  String     _buyName;                /* 商品名 */
    private  String     _buyClass;               /* 商品ジャンル */
    private  int        _buyNumber;              /* 商品番号 */
    private  String     _buyDesc;                /* 商品説明 */
    private int         _buyPrice;               /* 商品値段 */
    private  String     _buyDate;                /* 売る日付 */



    public void setData(int id, String name, String clas, int num, String desc, int price,  String date){
        // TODO setter
        _id = id;
        _buyName = name;
        _buyClass = clas;
        _buyNumber = num;
        _buyDesc = desc;
        _buyPrice = price;
        _buyDate = date;
    }

    /* getter */
    public int getID(){  return _id;            }
    public String getNam(){  return _buyName;   }
    public String getClas(){  return _buyClass; }
    public int getNum(){  return _buyNumber;    }
    public String getDesc(){  return _buyDesc;  }
    public String getDat(){  return _buyDate;   }
    public int getPrice(){  return _buyPrice;   }
}
