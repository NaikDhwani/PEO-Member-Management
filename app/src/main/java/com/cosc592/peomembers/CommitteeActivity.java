package com.cosc592.peomembers;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import java.util.LinkedList;

public class CommitteeActivity extends AppCompatActivity {

    Intent newActivity;
    CommitteeListAdapter committeeAdapter;
    DatabaseManager dbManager = MainActivity.dbManager;
    static Context c;
    AlertDialog dialogBox;
    Button delete, cancel;
    static EditText loginKey, searchCommittee;
    static LoginManagement loginManagement;
    static ListView committeeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_committee);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        c = CommitteeActivity.this;
        loginManagement = new LoginManagement(this);

        committeeList = findViewById(R.id.committeeList);

        EditActionListener handler = new EditActionListener();
        searchCommittee = findViewById(R.id.searchEditText);
        searchCommittee.addTextChangedListener(handler);
    }

    protected void onStart(){
        super.onStart();
        updateView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(Build.VERSION.SDK_INT > 11) {
            invalidateOptionsMenu();
            menu.findItem(R.id.committeeOption).setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         /*Handle action bar item clicks here. The action bar will
         automatically handle clicks on the Home/Up button, so long
         as you specify a parent activity in AndroidManifest.xml.*/

        switch (item.getItemId()) {
            case R.id.meetingOption:
                newActivity = new Intent(this, MeetingActivity.class);
                startActivity(newActivity);
                return true;
            case R.id.memberOption:
                newActivity = new Intent(this, MemberActivity.class);
                startActivity(newActivity);
                return true;
            case R.id.instituteOption:
                newActivity = new Intent(this, InstituteActivity.class);
                startActivity(newActivity);
                return true;
            case R.id.loginKeyOption:
                newActivity = new Intent(this, LoginKeyActivity.class);
                startActivity(newActivity);
                return true;
            case R.id.birthdayOption:
                newActivity = new Intent(this, BirthdayActivity.class);
                startActivity(newActivity);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void Add(View view) {
        newActivity = new Intent(this, AddCommitteeActivity.class);
        startActivity(newActivity);
    }

    private void updateView(){
        LinkedList<CommitteeManagement> list = dbManager.showAllCommittee();
        committeeList.removeAllViewsInLayout();
        committeeAdapter = new CommitteeListAdapter(c, list);
        committeeList.setAdapter(committeeAdapter);
    }

    public void Update(String committeeId) {
        newActivity = new Intent(c, UpdateCommitteeActivity.class);
        newActivity.putExtra("committeeId", committeeId);
        c.startActivity(newActivity);
    }

    public void showDialogBox(String committeeId) {
        dialogBox = new AlertDialog.Builder(c).create();
        LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.custom_dialog, null);

        loginKey = dialogView.findViewById(R.id.loginKey);
        delete = dialogView.findViewById(R.id.delete);
        cancel = dialogView.findViewById(R.id.cancel);

        CommitteeActivity.DialogBoxListener handler = new CommitteeActivity.DialogBoxListener(committeeId);
        delete.setOnClickListener(handler);
        cancel.setOnClickListener(handler);
        dialogBox.setView(dialogView);
        dialogBox.show();
    }

    public void ViewMember(int committee_id, String title) {
        newActivity = new Intent(c, ViewMemberActivity.class);
        newActivity.putExtra("committee_id", committee_id);
        newActivity.putExtra("title", title);
        c.startActivity(newActivity);
    }

    public class DialogBoxListener implements View.OnClickListener{
        private String id;
        public DialogBoxListener(String committeeId){
            this.id = committeeId;
        }

        @Override
        public void onClick(View v) {

            if(v.getId() == cancel.getId()){
                dialogBox.dismiss();
            }else if(v.getId() == delete.getId()) {
                if(loginKey.getText().toString().equals(loginManagement.getLoginKey())){
                    dbManager.deleteCommittee(id);
                    dbManager.deleteCommitteeMember(id);
                }
                Toast.makeText(c, "Successfully Deleted", Toast.LENGTH_SHORT).show();
                dialogBox.dismiss();
                updateView();
            }
        }
    }

    public class EditActionListener implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) { }

        @Override
        public void afterTextChanged(Editable s) {
            if (searchCommittee.getText().toString().equals(""))
                updateView();
            else {
                String searchText = searchCommittee.getText().toString();
                LinkedList<CommitteeManagement> list = dbManager.searchCommittee(searchText);
                committeeList.removeAllViewsInLayout();
                committeeAdapter = new CommitteeListAdapter(c, list);
                committeeList.setAdapter(committeeAdapter);
            }
        }
    }
}
