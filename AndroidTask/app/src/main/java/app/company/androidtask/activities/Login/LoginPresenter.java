package app.company.androidtask.activities.Login;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import app.company.androidtask.Utills.Constant;
import app.company.androidtask.Utills.Validation;
import app.company.androidtask.activities.Home.HomeActivity;

import static android.content.Context.MODE_PRIVATE;
import static app.company.androidtask.Utills.Constant.ErrorDialog;

class LoginPresenter {


    private Context mContext;
    private SharedPreferences mSharedPreferences;


    LoginPresenter(Context mContext) {
        this.mContext = mContext;
        mSharedPreferences = mContext.getSharedPreferences("MySharedPreference", MODE_PRIVATE);
    }

    void validation(String userName, String password) {
        if (!Validation.validateFields(userName)) {
            ErrorDialog((Activity) mContext,"user Name is Empty");
        } else if (!Validation.validateFields(password)) {
            ErrorDialog((Activity) mContext,"Password is Empty");
        } else if (!Validation.validatePassword(password)) {
            ErrorDialog((Activity) mContext,"Password must be greater than 6");
        } else if (!userName.equals(mSharedPreferences.getString(Constant.userName, ""))) {
            ErrorDialog((Activity) mContext,"user Name is Not correct");
        } else if (userName.equals(mSharedPreferences.getString(Constant.userName, ""))) {

            SharedPreferences.Editor edit = mSharedPreferences.edit();
            edit.putString(Constant.accessToken,userName);
            edit.apply();

            /*Intent intent = new Intent(mContext, HomeActivity.class);
            mContext.startActivity(intent);
            ((Activity) mContext).finish();*/
            Toast.makeText(mContext, "Static Login ... No API", Toast.LENGTH_SHORT).show();
        }
    }


}

