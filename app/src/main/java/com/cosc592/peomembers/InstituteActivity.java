package com.cosc592.peomembers;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.LinkedList;

public class InstituteActivity extends AppCompatActivity {

    Intent newActivity;
    DatabaseManager dbManager = MainActivity.dbManager;
    InstituteListAdapter instituteAdapter;
    static Context c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_institute);

        c = InstituteActivity.this;

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        displayAllInstitutes();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (Build.VERSION.SDK_INT > 11) {
            invalidateOptionsMenu();
            menu.findItem(R.id.instituteOption).setVisible(false);
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
            case R.id.committeeOption:
                newActivity = new Intent(this, CommitteeActivity.class);
                startActivity(newActivity);
                return true;
            case R.id.memberOption:
                newActivity = new Intent(this, MemberActivity.class);
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

    public void makeCall(String phoneNumber) {
        newActivity = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null));
        c.startActivity(newActivity);
    }

    private void displayAllInstitutes() {
        LinkedList<InstituteManagement> list = dbManager.showAllInstitute();
        ListView instituteList = findViewById(R.id.instituteList);
        instituteList.removeAllViewsInLayout();
        instituteAdapter = new InstituteListAdapter(getApplicationContext(), list);
        instituteList.setAdapter(instituteAdapter);
    }
}
