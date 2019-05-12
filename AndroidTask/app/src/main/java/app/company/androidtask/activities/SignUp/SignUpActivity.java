package app.company.androidtask.activities.SignUp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import app.company.androidtask.R;

public class SignUpActivity extends AppCompatActivity {

    Button SignUpButton;
    EditText nameTextView;
    EditText usernameTextView;
    EditText passwordTextView;
    EditText confirmPasswordTextView;
    EditText mailTextView;
    EditText mobileTextView;
    RadioButton maleRadioButton;

    SignUpPresenter signUpPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        nameTextView = findViewById(R.id.name_text_view);
        usernameTextView = findViewById(R.id.username_text_view);
        passwordTextView = findViewById(R.id.password_text_view);
        confirmPasswordTextView = findViewById(R.id.confirm_password_text_view);
        mailTextView = findViewById(R.id.mail_text_view);
        mobileTextView = findViewById(R.id.mobile_text_view);
        maleRadioButton = findViewById(R.id.male_radio_button);

        signUpPresenter =new SignUpPresenter(this);

        SignUpButton = findViewById(R.id.sign_up_button);
        SignUpButton.setOnClickListener(v -> {
            int genderId;
            if (maleRadioButton.isChecked())
            {
                genderId=0;
            }
            else
            {
                genderId=1;
            }
            signUpPresenter.validation(
                    nameTextView.getText().toString()
                    , usernameTextView.getText().toString()
                    , passwordTextView.getText().toString()
                    , confirmPasswordTextView.getText().toString()
                    , mailTextView.getText().toString()
                    ,genderId
                    , mobileTextView.getText().toString()
            );
        });
    }
}
