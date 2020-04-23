package com.example.crimereporter;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class SignupActivity extends AppCompatActivity {

    EditText editTextname, editTextaddress, editTextmobile, editTextemail, editTextpassword, editTexttype;
    Button register,loginpage;
    String Name_Holder, Address_Holder, MobileHolder, EmailHolder, PasswordHolder, typeHolder;
    String finalResult;
    String HttpURL = "https://crimereporterandmmissingpersonreporter.000webhostapp.com/register.php";
    Boolean CheckEditText;
    ProgressDialog progressDialog;
    HashMap<String, String> hashMap = new HashMap<>();
    HttpParse  httpParse = new HttpParse();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        editTextname = (EditText)findViewById(R.id.nameEditText);
        editTextaddress = (EditText)findViewById(R.id.addressEditText);
        editTextmobile = (EditText)findViewById(R.id.mobileEditText);
        editTextemail = (EditText)findViewById(R.id.emailEditText);
        editTextpassword = (EditText)findViewById(R.id.passwordEditText);
        editTexttype = (EditText)findViewById(R.id.typeEditText);
        register = (Button)findViewById(R.id.registerButton);
        loginpage = (Button)findViewById(R.id.loginPageButton);

        loginpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckEditTextIsEmptyOrNot();

                if(CheckEditText){
                    UserRegistrationFunction(Name_Holder, Address_Holder, MobileHolder, EmailHolder, PasswordHolder, typeHolder);
                } else {
                    Toast.makeText(SignupActivity.this, "Please Fill all the fields", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void CheckEditTextIsEmptyOrNot(){

        Name_Holder = editTextname.getText().toString();
        Address_Holder = editTextaddress.getText().toString();
        MobileHolder = editTextmobile.getText().toString();
        EmailHolder = editTextemail.getText().toString();
        PasswordHolder = editTextpassword.getText().toString();
        typeHolder = editTexttype.getText().toString();

        if(TextUtils.isEmpty(Name_Holder) || TextUtils.isEmpty(Address_Holder) ||
                TextUtils.isEmpty(MobileHolder) || TextUtils.isEmpty(EmailHolder) ||
                TextUtils.isEmpty(PasswordHolder) || TextUtils.isEmpty(typeHolder)){

            CheckEditText = false;
        } else {

            CheckEditText = true;
        }
    }

    public void UserRegistrationFunction(final String Name, final String Address, final String Mobile
    , final String Email, final String Password, final String type){

        class UserRegistrationFunctionClass extends AsyncTask<String, Void, String>{

            @Override
            protected void onPreExecute(){
                super.onPreExecute();
                progressDialog = progressDialog.show(SignupActivity.this, "Loading Data", null, true, true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg){
                super.onPostExecute(httpResponseMsg);
                progressDialog.dismiss();
                Toast.makeText(SignupActivity.this, httpResponseMsg.toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(String... params) {

                hashMap.put("Name", params[0]);
                hashMap.put("Address", params[1]);
                hashMap.put("Mobile", params[2]);
                hashMap.put("Email", params[3]);
                hashMap.put("Password", params[4]);
                hashMap.put("Type", params[5]);

                finalResult = httpParse.postRequest(hashMap, HttpURL);
                Log.i("Check", "Uploading");
                //Toast.makeText(getApplicationContext(),"Upload", Toast.LENGTH_LONG).show();
                return finalResult;
            }
        }

        UserRegistrationFunctionClass userRegistrationFunctionClass = new UserRegistrationFunctionClass();
        userRegistrationFunctionClass.execute(Name, Address, Mobile, Email, Password, type);
    }
}
