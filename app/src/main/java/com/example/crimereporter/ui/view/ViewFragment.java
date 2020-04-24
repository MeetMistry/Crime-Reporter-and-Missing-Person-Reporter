package com.example.crimereporter.ui.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.crimereporter.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class ViewFragment extends Fragment {

    private ViewViewModel viewViewModel;
    ListView listView;
    MyAdapter adapter;
    public static ArrayList<ReportView> reportViewArrayList = new ArrayList<>();
    String url = "https://crimereporterandmmissingpersonreporter.000webhostapp.com/viewReport.php";
    ReportView reportView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewViewModel =
                ViewModelProviders.of(this).get(ViewViewModel.class);
        View root = inflater.inflate(R.layout.fragment_view, container, false);
        //final TextView textView = root.findViewById(R.id.text_view);
        viewViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });

        listView = root.findViewById(R.id.myListView);
        adapter = new MyAdapter(getActivity(),reportViewArrayList);
        listView.setAdapter(adapter);

        retrieveData();

        return root;
    }

    public void retrieveData() {
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        reportViewArrayList.clear();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("data");

                            if (success.equals("1")) {
                                for(int i=0;i<jsonArray.length();i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String id = object.getString("id");
                                    String address = object.getString("address");
                                    String city = object.getString("city");
                                    String pincode = object.getString("pincode");
                                    String subject = object.getString("subject");
                                    String details = object.getString("details");
                                    String status = object.getString("status");

                                    reportView = new ReportView(id,address,city,pincode,subject,details,status);
                                    reportViewArrayList.add(reportView);
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        }catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(),error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(request);
    }
}
