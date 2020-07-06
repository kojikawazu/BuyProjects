package com.example.nanai.projectx.Use;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.nanai.projectx.Model.Super_Model;
import com.example.nanai.projectx.dialog.NumUpdate_Dialog;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/*--------------------------------------------------*/
// クラスローダー
/*--------------------------------------------------*/
public class Loader {
    private static final String defaultModel = "com.example.nanai.projectx.Model.";
    private static final String defaultDialog = "com.example.nanai.projectx.dialog.";

    public Loader(){}

    /* クラスインスタンス */
    public static final Object getModelInstance(String className, AppCompatActivity activity){
        Object newClass = null;
        try {
            Class<?> cls = Class.forName(defaultModel + className);
            Constructor<?> cons = cls.getConstructor(AppCompatActivity.class);
            newClass = cons.newInstance(activity);
        }
        catch(ClassNotFoundException ex){
            System.out.println(ex.toString());
        }
        catch(InstantiationException ex){
            System.out.println(ex.toString());
        }
        catch(IllegalAccessException ex){
            System.out.println(ex.toString());
        }
        catch(NoSuchMethodException ex){
            System.out.println(ex.toString());
        }
        catch(InvocationTargetException ex){
            System.out.println(ex.toString());
        }
        return newClass;
    }

    /* ダイアログ取得 */
    public static final Object getDialogInstance(String className, Object obj){
        Object newClass = null;
        try {
            Class<?> cls = Class.forName(defaultDialog + className);
            if(className == "Selecting_Dialog" || className == "Desc_dialog" ||
                    className == "NumUpdate_Dialog" || className == "FinalAnswer_dialog"){
                Class<?> thClass = null;
                switch(className){
                    case "Selecting_Dialog":
                        thClass = Class.forName("com.example.nanai.projectx.Data.Select_data");
                        break;
                    case "Desc_dialog":
                        thClass = String.class;
                        break;
                    case "NumUpdate_Dialog":
                    case "FinalAnswer_dialog":
                        thClass = int.class;
                        break;
                    default:
                        return null;
                }
                Constructor<?> cons = cls.getConstructor(thClass);
                newClass = cons.newInstance(obj);
            }
            else{
                newClass = cls.newInstance();
            }
        }
        catch(ClassNotFoundException ex){
            System.out.println(ex.toString());
        }
        catch(InstantiationException ex){
            System.out.println(ex.toString());
        }
        catch(IllegalAccessException ex){
            System.out.println(ex.toString());
        }
        catch(NoSuchMethodException ex){
            System.out.println(ex.toString());
        }
        catch(InvocationTargetException ex){
            System.out.println(ex.toString());
        }
        return newClass;
    }
}
