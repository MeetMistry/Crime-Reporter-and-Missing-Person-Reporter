package com.example.crimereporter.ui.report;

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

public class ReportFragment extends Fragment {

    private ReportViewModel reportViewModel;
    EditText editTextaddress, editTextcity, editTextpincode, editTextsubject, editTextdetails;
    Button report;
    String AddressHolder, CityHolder, PincodeHolder, SubjectHolder, DetailsHolder;
    String finalResult;
    String HttpURL = "https://crimereporterandmmissingpersonreporter.000webhostapp.com/report.php";
    Boolean CheckEditText;
    ProgressDialog progressDialog;
    HashMap<String, String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        reportViewModel =
                ViewModelProviders.of(this).get(ReportViewModel.class);
        View root = inflater.inflate(R.layout.fragment_report, container, false);
        //final TextView textView = root.findViewById(R.id.text_report);
        reportViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });

        editTextaddress = (EditText)root.findViewById(R.id.reportAddressEditText);
        editTextcity = (EditText)root.findViewById(R.id.cityEditText);
        editTextpincode = (EditText)root.findViewById(R.id.pincodeEditText);
        editTextdetails = (EditText)root.findViewById(R.id.reportDetailsEditText);
        editTextsubject = (EditText)root.findViewById(R.id.subjectEditText);
        report = (Button)root.findViewById(R.id.reportSubmitButton);

        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CheckEditTextIsEmptyOrNot();

                if(CheckEditText){
                    ReportCrimeFunction(AddressHolder, CityHolder, PincodeHolder, SubjectHolder, DetailsHolder);
                } else {
                    Toast.makeText(getActivity(), "Please Fill all the fields", Toast.LENGTH_LONG).show();
                }
            }
        });

        return root;
    }

    public void CheckEditTextIsEmptyOrNot(){

        AddressHolder = editTextaddress.getText().toString();
        CityHolder = editTextcity.getText().toString();
        PincodeHolder = editTextpincode.getText().toString();
        SubjectHolder = editTextsubject.getText().toString();
        DetailsHolder = editTextdetails.getText().toString();

        if(TextUtils.isEmpty(AddressHolder) || TextUtils.isEmpty(CityHolder) ||
                TextUtils.isEmpty(PincodeHolder) || TextUtils.isEmpty(SubjectHolder) ||
                TextUtils.isEmpty(DetailsHolder)){

            CheckEditText = false;
        } else {
            CheckEditText = true;
        }
    }

    public void ReportCrimeFunction(final String Address, final String City,
                                    final String Pincode, final String Subject,
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

                hashMap.put("Address", params[0]);
                hashMap.put("City", params[1]);
                hashMap.put("Pincode", params[2]);
                hashMap.put("Subject", params[3]);
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
        reportCrimeFunctionClass.execute(Address, City, Pincode, Subject, Details);
    }
}
