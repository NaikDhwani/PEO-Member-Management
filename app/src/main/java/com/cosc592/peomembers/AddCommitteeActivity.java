package com.cosc592.peomembers;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddCommitteeActivity extends AppCompatActivity {

    final Calendar calendar = Calendar.getInstance();
    EditText committeeTitle, committeeDescription;
    Spinner committeeInstitute;
    DatabaseManager dbManager = MainActivity.dbManager;
    boolean notNullCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_committee);

        committeeTitle = findViewById(R.id.committeeTitle);
        committeeDescription = findViewById(R.id.committeeDescription);
        committeeInstitute = findViewById(R.id.committeeInstitute);
    }

    public void Back(View view) {
        finish();
    }

    public void Clear(View view) {
        clear();
    }

    public void Add(View view) {
        notNullChecking();
        if (notNullCheck == true){
            try{
                SimpleDateFormat simpledateformat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                String title = committeeTitle.getText().toString();
                String description = committeeDescription.getText().toString();
                int institute = committeeInstitute.getSelectedItemPosition();

                CommitteeManagement committeeManagement = new CommitteeManagement(institute, title, description, simpledateformat.format(calendar.getTime()));
                dbManager.addCommittee(committeeManagement);
                Toast.makeText(getApplicationContext(), "Successfully Added", Toast.LENGTH_SHORT).show();
                clear();
            }catch (Exception e){
                Toast.makeText(getApplicationContext(),"Insert Valid Inputs",Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void notNullChecking(){
        notNullCheck = true;
        if (committeeTitle.getText().toString().equals("")) {
            committeeTitle.setError("Required");
            notNullCheck = false;
        }

        if (committeeDescription.getText().toString().equals("")) {
            committeeDescription.setError("Required");
            notNullCheck = false;
        }

        if (committeeInstitute.getSelectedItem().equals("Select Institute")) {
            Toast.makeText(getApplicationContext(),"Institute Selection is Required",Toast.LENGTH_LONG).show();
            notNullCheck = false;
        }
    }

    public void clear(){
        committeeTitle.setText("");
        committeeDescription.setText("");
        committeeInstitute.setSelection(0);
    }
}
