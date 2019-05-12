package app.company.androidtask.Utills;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;


import app.company.androidtask.R;

public class Constant {
    public static final String BASE_URL_HTTP = "https://restcountries.eu/";
    public static final String facebookID = "facebookID";
    public static String accessToken = "accessToken";
    public static String userName = "userName";
    public static String name = "";
    public static String password = "";
    public static String mail = "";
    public static String mobile = "";
    public static String gender = "0";
    public static String rememberMe = "rememberMe";


    public static AlertDialog.Builder buildDialog(Activity c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("No Internet connection.");
        builder.setMessage("You have no internet connection");
        builder.setMessage("please check internet connection");
        builder.setIcon(R.drawable.ic_warning);
        builder.setCancelable(false);

        builder.setPositiveButton("Reload", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                c.finish();
                c.startActivities(new Intent[]{c.getIntent()});
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                c.finish();
            }
        });
        return builder;
    }

    public static AlertDialog.Builder ErrorDialog(Activity c, String errorMessage) {

        android.support.v7.app.AlertDialog.Builder builder;
        android.support.v7.app.AlertDialog dialog;
        builder = new android.support.v7.app.AlertDialog.Builder(c);

        @SuppressLint("InflateParams")
        View mview = c.getLayoutInflater().inflate(R.layout.dialog_error, null);
        builder.setView(mview);
        dialog = builder.create();
        Window window = dialog.getWindow();
        if (window != null) {
            window.setGravity(Gravity.CENTER);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        TextView message = mview.findViewById(R.id.message);
        TextView cancel = mview.findViewById(R.id.cancel);
        TextView ok = mview.findViewById(R.id.ok);
        message.setText(errorMessage);
        cancel.setOnClickListener(v2 -> {
            dialog.dismiss();
        });
        ok.setOnClickListener(v2 -> {
            dialog.dismiss();
        });
        return builder;
    }

    public static void errorBuildDialog(Activity c, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("Error");
        builder.setMessage(message);
        builder.setIcon(R.drawable.ic_warning);
        builder.setCancelable(false);
        builder.setNegativeButton("Cancel", (dialogInterface, i) -> c.finish());
    }


}
