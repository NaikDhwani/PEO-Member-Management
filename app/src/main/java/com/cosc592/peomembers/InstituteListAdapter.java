package com.cosc592.peomembers;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

public class InstituteListAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private List<InstituteManagement> instituteList;
    TextView instituteNameText, instituteAddressText;
    ImageButton callButton;

    public InstituteListAdapter(Context context, List<InstituteManagement> instituteList) {
        layoutInflater =LayoutInflater.from(context);
        this.instituteList = instituteList;
    }

    @Override
    public int getCount() {
        return instituteList.size();
    }

    @Override
    public Object getItem(int position) {
        return instituteList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.institute_list_item, null);

                instituteNameText = convertView.findViewById(R.id.instituteName);
                instituteAddressText = convertView.findViewById(R.id.instituteAddress);
                callButton = convertView.findViewById(R.id.callButton);

                InstituteManagement instituteManagement = instituteList.get(position);

                ButtonHandler handler = new ButtonHandler(position);
                callButton.setOnClickListener(handler);

                instituteNameText.setText(instituteManagement.getName());
                instituteAddressText.setText(instituteManagement.getAddress());
            }else{
                convertView.getTag();
            }
        return convertView;
    }

    private class ButtonHandler implements View.OnClickListener{

        private  int rowNumber;

        public ButtonHandler (int rowNumber){
            this.rowNumber = rowNumber;
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == callButton.getId()){
                InstituteManagement instituteManagement = instituteList.get(rowNumber);
                new InstituteActivity().makeCall(instituteManagement.getContact_number());
                Log.d("Phone",instituteManagement.getContact_number());
            }
        }
    }
}
