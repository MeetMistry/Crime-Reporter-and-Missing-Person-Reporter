package com.example.crimereporter.ui.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.crimereporter.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static com.example.crimereporter.R.*;

public class MyAdapter extends ArrayAdapter<ReportView> {

    Context context;
    List<ReportView> arrayReportList;

    public MyAdapter(@NonNull Context context, List<ReportView> arrayReportList) {
        super(context, R.layout.custom_item_list,arrayReportList);

        this.context = context;
        this.arrayReportList = arrayReportList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_list,null,true);

        TextView txaddress = view.findViewById(id.txt_address);
        TextView txcity = view.findViewById(id.txt_city);
        TextView txpincode = view.findViewById(id.txt_pincode);
        TextView txsubject = view.findViewById(id.txt_subject);
        TextView txdetails = view.findViewById(id.txt_details);
        TextView txstatus = view.findViewById(id.txt_status);

        txaddress.setText(arrayReportList.get(position).getAddress());
        txcity.setText(arrayReportList.get(position).getCity());
        txpincode.setText(arrayReportList.get(position).getPincode());
        txsubject.setText(arrayReportList.get(position).getSubject());
        txdetails.setText(arrayReportList.get(position).getDetails());
        txstatus.setText(arrayReportList.get(position).getStatus());

        return view;
    }
}
