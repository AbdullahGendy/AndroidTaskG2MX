package app.company.androidtask.activities.Login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.Objects;

import app.company.androidtask.R;
import app.company.androidtask.Utills.Constant;
import app.company.androidtask.activities.Home.HomeActivity;
import app.company.androidtask.activities.SignUp.SignUpActivity;

public class LoginActivity extends AppCompatActivity {
    TextView signUpTextView;
    Button loginButton;
    LoginButton facebookLoginButton;
    CallbackManager callbackManager;
    SharedPreferences mSharedPreferences;
    LoginPresenter loginPresenter;

    AppCompatEditText username_text_view;
    AppCompatEditText password_text_view;

    CheckBox rememberMeCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FacebookSdk.sdkInitialize(getApplicationContext());

        rememberMeCheckBox = findViewById(R.id.remember_me_check_box);
        facebookLoginButton = findViewById(R.id.login_with_facebook_button);
        signUpTextView = findViewById(R.id.sign_up_text_view);
        loginButton = findViewById(R.id.login_button);
        username_text_view = findViewById(R.id.username_text_view);
        password_text_view = findViewById(R.id.password_text_view);
        loginButton = findViewById(R.id.login_button);
        mSharedPreferences = getSharedPreferences("MySharedPreference", MODE_PRIVATE);

        loginPresenter = new LoginPresenter(this);
        callbackManager = CallbackManager.Factory.create();
        facebookLoginButton.setReadPermissions(Arrays.asList("public_profile", "user_friends", "email"));


        facebookLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                AccessToken accessToken = loginResult.getAccessToken();

                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        (object, response) -> {
                            try {
                                /*
                                String id = object.getString("id");
                                String FirstName = object.getString("first_name");
                                String LastName = object.getString("last_name");
                                String Img_URL = "http://graph.facebook.com/" + id + "/picture?type=large";
                                String email = "";
                                if (object.has("email")) {
                                    email = object.getString("email");
                                }
*/
                                mSharedPreferences = getSharedPreferences("MySharedPreference", MODE_PRIVATE);
                                SharedPreferences.Editor prefsEditor = mSharedPreferences.edit();
                                Gson gson = new Gson();
                                String json = gson.toJson(accessToken);
                                prefsEditor.putString(Constant.accessToken, json);
                                if (rememberMeCheckBox.isChecked()) {
                                    prefsEditor.putBoolean(Constant.rememberMe, true);
                                } else {
                                    prefsEditor.putBoolean(Constant.rememberMe, false);
                                }
                                prefsEditor.apply();

                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(intent);
                                finish();

                            } catch (Exception e) {
                                Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,first_name,last_name, email,location,gender");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                LoginManager.getInstance().logOut();
            }

            @Override
            public void onError(FacebookException exception) {
                Toast.makeText(LoginActivity.this, exception.getMessage(), Toast.LENGTH_SHORT).show();
                LoginManager.getInstance().logOut();


            }

        });
        signUpTextView.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        });
        loginButton.setOnClickListener(v ->
                loginPresenter.validation(Objects.requireNonNull(username_text_view.getText()).toString(),
                        password_text_view.getText().toString()));


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!rememberMeCheckBox.isChecked())
            LoginManager.getInstance().logOut();
    }


}
