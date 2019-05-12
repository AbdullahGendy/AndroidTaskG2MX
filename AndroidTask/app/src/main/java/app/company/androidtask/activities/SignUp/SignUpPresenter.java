package app.company.androidtask.activities.SignUp;


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

class SignUpPresenter {

    private Context mContext;
    private SharedPreferences mSharedPreferences;


    SignUpPresenter(Context mContext) {
        this.mContext = mContext;
        mSharedPreferences = mContext.getSharedPreferences("MySharedPreference", MODE_PRIVATE);

    }

    void validation(String name, String username, String password, String confirmPassword, String mail, int gender, String mobile) {
        if (!Validation.validateFields(name)) {
            ErrorDialog((Activity) mContext, "Name is Empty");
        } else if (!Validation.validateFields(username)) {
            ErrorDialog((Activity) mContext, "username is Empty");
        } else if (!Validation.validateFields(password)) {
            ErrorDialog((Activity) mContext, "Password is Empty");
        } else if (!Validation.validatePassword(password)) {
            ErrorDialog((Activity) mContext, "Password must be greater than 6");
        } else if (!Validation.validateFields(confirmPassword)) {
            ErrorDialog((Activity) mContext, "confirmed Password is Empty");
        } else if (!Validation.validatePassword(confirmPassword)) {
            ErrorDialog((Activity) mContext, "confirmed Password must be greater than 6");
        } else if (!password.equals(confirmPassword)) {
            ErrorDialog((Activity) mContext, "password not match");
        } else if (!Validation.validateFields(mail)) {
            ErrorDialog((Activity) mContext, "mail is Empty");
        } else if (!Validation.validateEmail(mail)) {
            ErrorDialog((Activity) mContext, "mail format not correct");
        } else if (!Validation.validateFields(mobile)) {
            ErrorDialog((Activity) mContext, "phone is Empty");
        } else if (!Validation.validatePhone(mobile)) {
            ErrorDialog((Activity) mContext, "please check phone");
        } else {
            SharedPreferences.Editor edit = mSharedPreferences.edit();
            edit.putString(Constant.name, name);
            edit.putString(Constant.userName, username);
            // edit.putString(Constant.accessToken, username);
            edit.putString(Constant.password, password);
            edit.putString(Constant.mail, mail);
            edit.putString(Constant.gender, String.valueOf(gender));
            edit.putString(Constant.mobile, mobile);
            edit.apply();

            /*Intent intent = new Intent(mContext, HomeActivity.class);
            mContext.startActivity(intent);
            ((Activity) mContext).finish();*/
            Toast.makeText(mContext, "Static SignUp ... No API", Toast.LENGTH_SHORT).show();


        }
    }


}

