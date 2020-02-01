package com.example.crimereporter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class SignupActivity extends AppCompatActivity{

    EditText editTextname, editTextusername, editTextaddress, editTextmobile, editTextemail, editTextpassword, editTexttype;
    Button register,loginpage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //if the user is already logged in we will directly start the profile activity
        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, ReportActivity.class));
            return;
        }

        editTextname = (EditText)findViewById(R.id.nameEditText);
        editTextusername = (EditText)findViewById(R.id.usernameEditText);
        editTextaddress = (EditText)findViewById(R.id.addressEditText);
        editTextmobile = (EditText)findViewById(R.id.mobileEditText);
        editTextemail = (EditText)findViewById(R.id.emailEditText);
        editTextpassword = (EditText)findViewById(R.id.passwordEditText);
        editTexttype = (EditText)findViewById(R.id.typeEditText);
        register = (Button)findViewById(R.id.registerButton);
        loginpage = (Button)findViewById(R.id.loginPageButton);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if user pressed on button register
                //here we will register the user to server
                registerUser();
            }
        });

        loginpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if user pressed on login
                //we will open the login screen
                finish();
                startActivity(new Intent(SignupActivity.this, MainActivity.class));
            }
        });
    }

    private void registerUser() {
        final String name = editTextname.getText().toString().trim();
        final String username = editTextusername.getText().toString().trim();
        final String address = editTextaddress.getText().toString().trim();
        final String email = editTextemail.getText().toString().trim();
        final String password = editTextpassword.getText().toString().trim();
        final String mobile = editTextmobile.getText().toString().trim();
        final String type = editTexttype.getText().toString().trim();

        //first we will do the validations

        if (TextUtils.isEmpty(name)) {
            editTextname.setError("Please enter name");
            editTextname.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(username)) {
            editTextusername.setError("Please enter username");
            editTextusername.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(address)) {
            editTextaddress.setError("Please enter address");
            editTextaddress.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(email)) {
            editTextemail.setError("Please enter your email");
            editTextemail.requestFocus();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextemail.setError("Enter a valid email");
            editTextemail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            editTextpassword.setError("Enter a password");
            editTextpassword.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(mobile)) {
            editTextpassword.setError("Enter a Mobile no.");
            editTextpassword.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(type)) {
            editTexttype.setError("Enter a type");
            editTexttype.requestFocus();
            return;
        }

        //if it passes all the validations

        class RegisterUser extends AsyncTask<Void, Void, String> {

            private ProgressBar progressBar;

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("username", username);
                params.put("address", address);
                params.put("email", email);
                params.put("password", password);
                params.put("mobile", mobile);
                params.put("type", type);

                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_REGISTER, params);
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //displaying the progress bar while user registers on the server
                progressBar = (ProgressBar) findViewById(R.id.progressBar);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //hiding the progressbar after completion
                progressBar.setVisibility(View.GONE);

                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                        //getting the user from the response
                        JSONObject userJson = obj.getJSONObject("user");

                        //creating a new user object
                        User user = new User(
                                userJson.getString("name"),
                                userJson.getString("username"),
                                userJson.getString("address"),
                                userJson.getString("email"),
                                userJson.getInt("mobile"),
                                userJson.getString("type")
                        );

                        //storing the user in shared preferences
                        SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);

                        //starting the profile activity
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Some error occurred", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        //executing the async task
        RegisterUser ru = new RegisterUser();
        ru.execute();
    }
}
