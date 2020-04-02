package com.cosc592.peomembers;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.LinkedList;

public class BirthdayActivity extends AppCompatActivity {

    Intent newActivity;
    DatabaseManager dbManager = MainActivity.dbManager;
    static Context c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birthday);

        c = BirthdayActivity.this;

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        displayAllBirthDayMember();
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
            menu.findItem(R.id.birthdayOption).setVisible(false);
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
            case R.id.instituteOption:
                newActivity = new Intent(this, InstituteActivity.class);
                startActivity(newActivity);
                return true;
            case R.id.loginKeyOption:
                newActivity = new Intent(this, LoginKeyActivity.class);
                startActivity(newActivity);
                return true;
            case R.id.memberOption:
                newActivity = new Intent(this, MemberActivity.class);
                startActivity(newActivity);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void displayAllBirthDayMember() {
        LinkedList<MemberManagement> list = dbManager.showAllBirthDayMember();
        ListView memberBirthDayList = findViewById(R.id.memberBirthDayList);
        memberBirthDayList.removeAllViewsInLayout();
        MemberBirthDayListAdapter memberAdapter = new MemberBirthDayListAdapter(getApplicationContext(), list);
        memberBirthDayList.setAdapter(memberAdapter);
    }

    public void sendEmail(String email, String name){
        String subject= "Birth Day Wish";
        String body="Dear "+ name +",%3C%2Fbr%3E%3C%2Fbr%3EMay your birthday be the start of a year filled with good luck, good health and much happiness. Happy Birth Day! Enjoy your special day." +
                "%3C%2Fbr%3E%3C%2Fbr%3EThank you.%3C%2Fbr%3EPEO and Team";
        String mailTo = "mailto:" + email +
                "?&subject=" + Uri.encode(subject) +
                "&body=" + Uri.encode(body);
        Intent emailIntent = new Intent(Intent.ACTION_VIEW);
        emailIntent.setData(Uri.parse(mailTo));
        c.startActivity(emailIntent);
    }
}
