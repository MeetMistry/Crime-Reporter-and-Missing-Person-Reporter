package com.example.crimereporter.ui.missingperson;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.crimereporter.HttpParse;
import com.example.crimereporter.R;

import java.util.HashMap;

public class MissingPersonFragment extends Fragment {

    private MissingPersonViewModel missingPersonViewModel;
    EditText editTextname, editTextage, editTextgender, editTextlastseen, editTextdetails;
    Button report;
    String NameHolder, AgeHolder, GenderHolder, LastseenHolder, DetailsHolder;
    String finalResult;
    String HttpURL = "https://crimereporterandmmissingpersonreporter.000webhostapp.com/missingPerson.php";
    Boolean CheckEditText;
    ProgressDialog progressDialog;
    HashMap<String, String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        missingPersonViewModel =
                ViewModelProviders.of(this).get(MissingPersonViewModel.class);
        View root = inflater.inflate(R.layout.fragment_missingperson, container, false);
        //final TextView textView = root.findViewById(R.id.text_missingperson);
        missingPersonViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });

        editTextname = (EditText)root.findViewById(R.id.fullNameEditText);
        editTextage = (EditText)root.findViewById(R.id.ageEditText);
        editTextgender = (EditText)root.findViewById(R.id.genderEditText);
        editTextdetails = (EditText)root.findViewById(R.id.personDetailsEditText);
        editTextlastseen = (EditText)root.findViewById(R.id.lastSeenEditText);
        report = (Button)root.findViewById(R.id.personSubmitButton);

        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CheckEditTextIsEmptyOrNot();

                if(CheckEditText){
                    MissingPersonFunction(NameHolder, AgeHolder, GenderHolder, LastseenHolder, DetailsHolder);
                } else {
                    Toast.makeText(getActivity(), "Please Fill all the fields", Toast.LENGTH_LONG).show();
                }
            }
        });

        return root;
    }

    public void CheckEditTextIsEmptyOrNot(){

        NameHolder = editTextname.getText().toString();
        AgeHolder = editTextage.getText().toString();
        GenderHolder = editTextgender.getText().toString();
        LastseenHolder = editTextlastseen.getText().toString();
        DetailsHolder = editTextdetails.getText().toString();

        if(TextUtils.isEmpty(NameHolder) || TextUtils.isEmpty(AgeHolder) ||
                TextUtils.isEmpty(GenderHolder) || TextUtils.isEmpty(LastseenHolder) ||
                TextUtils.isEmpty(DetailsHolder)){

            CheckEditText = false;
        } else {
            CheckEditText = true;
        }
    }

    public void MissingPersonFunction(final String Name, final String Age,
                                    final String Gender, final String Lastseen,
                                    final String Details){

        class ReportCrimeFunctionClass extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute(){
                super.onPreExecute();
                progressDialog = progressDialog.show(getActivity(), "Loading Data", null, true, true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg){
                super.onPostExecute(httpResponseMsg);
                progressDialog.dismiss();
                Toast.makeText(getActivity(), httpResponseMsg.toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(String... params) {

                hashMap.put("Name", params[0]);
                hashMap.put("Age", params[1]);
                hashMap.put("Gender", params[2]);
                hashMap.put("Lastseen", params[3]);
                hashMap.put("Details", params[4]);

//                Log.i("Name",params[0]);
//                Log.i("Address", params[1]);
//                Log.i("Mobile", params[2]);
//                Log.i("Email", params[3]);
//                Log.i("Password", params[4]);
//                Log.i("Type", params[5]);
                //Toast.makeText(SignupActivity.this,"DATA", Toast.LENGTH_LONG).show();

                finalResult = httpParse.postRequest(hashMap, HttpURL);
                Log.i("Check", "Uploading");
                //Toast.makeText(getApplicationContext(),"Upload", Toast.LENGTH_LONG).show();
                return finalResult;
            }
        }

        ReportCrimeFunctionClass reportCrimeFunctionClass = new ReportCrimeFunctionClass();
        reportCrimeFunctionClass.execute(Name, Age, Gender, Lastseen, Details);
    }
}
