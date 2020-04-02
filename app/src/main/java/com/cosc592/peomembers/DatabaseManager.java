package com.cosc592.peomembers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;

public class DatabaseManager extends SQLiteOpenHelper {

    final Calendar calendar = Calendar.getInstance();

    //Database Name and Version
    private static final String DATABASE_NAME = "PEO_MEMBER_DATABASE";
    private static final int DATABASE_VERSION = 1;

    //Table Names
    private static final String INSTITUTE_TABLE_NAME = "INSTITUTE";
    private static final String MEMBER_TABLE_NAME = "MEMBER";
    private static final String COMMITTEE_TABLE_NAME = "COMMITTEE";
    private static final String COMMITTEE_MEMBER_TABLE_NAME = "COMMITTEE_MEMBER";
    private static final String MEETING_TABLE_NAME = "MEETING";

    //Create Table Strings
    private static final String instituteTable = "create table " + INSTITUTE_TABLE_NAME + "(institute_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, address TEXT NOT NULL, " +
            "contact_number TEXT NOT NULL, email TEXT NOT NULL, UNIQUE(name, address, contact_number, email)) ";
    private static final String memberTable = "create table " + MEMBER_TABLE_NAME + "(member_id TEXT PRIMARY KEY, first_name TEXT NOT NULL, middle_name TEXT, last_name TEXT NOT NULL, " +
            "address TEXT NOT NULL, zip_code INTEGER NOT NULL, city TEXT NOT NULL, state TEXT NOT NULL, country TEXT NOT NULL, contact_mobile TEXT NOT NULL, " +
            "contact_residence TEXT, contact_office TEXT, email TEXT NOT NULL, date_of_birth TEXT NOT NULL, is_alive INTEGER NOT NULL, registration_date TEXT DEFAULT CURRENT_TIMESTAMP NOT NULL, UNIQUE(contact_mobile, email))";
    private static final String committeeTable = "create table " + COMMITTEE_TABLE_NAME + "(committee_id  INTEGER PRIMARY KEY AUTOINCREMENT, institute_id INTEGER NOT NULL, title TEXT NOT NULL, " +
            "description TEXT NOT NULL, date TEXT DEFAULT CURRENT_TIMESTAMP NOT NULL, FOREIGN KEY (institute_id) REFERENCES " + INSTITUTE_TABLE_NAME + " (institute_id))";
    private static final String committee_memberTable = "create table " + COMMITTEE_MEMBER_TABLE_NAME + "(committee_id INTEGER NOT NULL, member_id TEXT NOT NULL, " +
            "FOREIGN KEY (committee_id) REFERENCES " + COMMITTEE_TABLE_NAME + " (committee_id), " +
            "FOREIGN KEY (member_id) REFERENCES " + MEMBER_TABLE_NAME + " (member_id))";
    private static final String meetingTable = "create table " + MEETING_TABLE_NAME + "(meeting_id INTEGER PRIMARY KEY AUTOINCREMENT, committee_id INTEGER NOT NULL, title TEXT NOT NULL, " +
            "address TEXT NOT NULL, zip_code INTEGER NOT NULL, city TEXT NOT NULL, state TEXT NOT NULL, country TEXT NOT NULL, data_time TEXT NOT NULL, is_active INTEGER NOT NULL, " +
            "agenda TEXT NOT NULL, note TEXT, FOREIGN KEY (committee_id) REFERENCES " + COMMITTEE_TABLE_NAME + " (committee_id))";

    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(instituteTable);
        db.execSQL(memberTable);
        db.execSQL(committeeTable);
        db.execSQL(committee_memberTable);
        db.execSQL(meetingTable);

        db.execSQL("INSERT INTO " + INSTITUTE_TABLE_NAME + " (name, address, contact_number, email) VALUES ('BRCM ElementarySchool', '592 Golf Dr. Malden, MA 02148', '+1(989) 898-9898', 'brcm@peo.edu');");
        db.execSQL("INSERT INTO " + INSTITUTE_TABLE_NAME + " (name, address, contact_number, email) VALUES ('SDK Middle School', '664 Delaware St. West Deptford, NJ 08096', '+1(982) 510-0111', 'sdk@peo.edu');");
        db.execSQL("INSERT INTO " + INSTITUTE_TABLE_NAME + " (name, address, contact_number, email) VALUES ('JFK High School', '79 Smith St. Coachella, CA 92236', '+1(987) 654-3210', 'jfk@peo.edu');");
        db.execSQL("INSERT INTO " + INSTITUTE_TABLE_NAME + " (name, address, contact_number, email) VALUES ('Galen Medical College', '535 Roosevelt Ave. Sandusky, OH 44870', '+1(901) 234-5678', 'gm@peo.edu');");
        db.execSQL("INSERT INTO " + INSTITUTE_TABLE_NAME + " (name, address, contact_number, email) VALUES ('Albert Science College', '7287 High Point Ave. Bartlett, IL 60103', '+1(998) 877-6655', 'as@peo.edu');");
        db.execSQL("INSERT INTO " + INSTITUTE_TABLE_NAME + " (name, address, contact_number, email) VALUES ('VA Commerce College', '45 Trusel Dr. West Fargo, ND 58078', '+1(987) 501-2346', 'va@peo.edu');");
        db.execSQL("INSERT INTO " + INSTITUTE_TABLE_NAME + " (name, address, contact_number, email) VALUES ('Diego Arts College', '8633 Sunset Dr. Sacramento, CA 95828', '+1(989) 877-6534', 'da@peo.edu');");
        db.execSQL("INSERT INTO " + INSTITUTE_TABLE_NAME + " (name, address, contact_number, email) VALUES ('MG Research Institute', '978 Johnson Dr. Fort Worth, TX 76112', '+1(989) 898-9898', 'mg@peo.edu');");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }

    //All Institute List
    public LinkedList<InstituteManagement> showAllInstitute(){
        SQLiteDatabase db = getWritableDatabase();
        LinkedList<InstituteManagement> list = new LinkedList<>();
        Cursor cursor = db.query(INSTITUTE_TABLE_NAME, new String[]{"institute_id", "name", "address", "contact_number", "email"},null,null,null,null,null);
        while (cursor.moveToNext()){
            int institute_id = cursor.getInt(0);
            String name = cursor.getString(1);
            String address = cursor.getString(2);
            String contact_number = cursor.getString(3);
            String email = cursor.getString(4);
            InstituteManagement instituteManagement = new InstituteManagement(institute_id, name, address, contact_number, email);
            list.addLast(instituteManagement);
        }
        cursor.close();
        db.close();
        return list;
    }

    //All Member List
    public LinkedList<MemberManagement> showAllMember(){
        SQLiteDatabase db = getWritableDatabase();
        LinkedList<MemberManagement> list = new LinkedList<>();
        Cursor cursor = db.query(MEMBER_TABLE_NAME, new String[]{"member_id", "first_name", "middle_name", "last_name"},null,null,null,null,null);
        while (cursor.moveToNext()){
            String member_id = cursor.getString(0);
            String first_name = cursor.getString(1);
            String middle_name = cursor.getString(2);
            String last_name = cursor.getString(3);
            MemberManagement memberManagement = new MemberManagement(member_id, first_name, middle_name, last_name);
            list.addLast(memberManagement);
        }
        cursor.close();
        db.close();
        return list;
    }

    public LinkedList<MemberManagement> showAllBirthDayMember(){
        SQLiteDatabase db = getWritableDatabase();
        LinkedList<MemberManagement> list = new LinkedList<>();
        SimpleDateFormat simpledateformat = new SimpleDateFormat("MM/dd");
        Cursor cursor =  db.rawQuery( "select member_id, first_name, middle_name, last_name, email from " + MEMBER_TABLE_NAME
                + " where " + " date_of_birth LIKE'" + simpledateformat.format(calendar.getTime()) + '%' + "'" + " AND is_alive ='" + 1 + "'",null);
        while (cursor.moveToNext()){
            String member_id = cursor.getString(0);
            String first_name = cursor.getString(1);
            String middle_name = cursor.getString(2);
            String last_name = cursor.getString(3);
            String email = cursor.getString(4);
            MemberManagement memberManagement = new MemberManagement(member_id, first_name, middle_name, last_name, email);
            list.addLast(memberManagement);
        }
        cursor.close();
        db.close();
        return list;
    }

    public LinkedList<MemberManagement> searchMember(String searchText, int i){
        SQLiteDatabase db = getWritableDatabase();
        LinkedList<MemberManagement> list = new LinkedList<>();
        Cursor cursor;
        if(i == 1) {
            cursor = db.rawQuery("select member_id, first_name, middle_name, last_name from " + MEMBER_TABLE_NAME
                    + " where " + "member_id LIKE'" + '%' + searchText + '%' + "'" + " OR first_name LIKE'" + '%' + searchText + '%' + "'"
                    + " OR middle_name LIKE'" + '%' + searchText + '%' + "'" + " OR last_name LIKE'" + '%' + searchText + '%' + "'", null);
        }else if (i ==2){
            cursor = db.rawQuery("select member_id, first_name, middle_name, last_name from " + MEMBER_TABLE_NAME
                    + " where " + "is_alive ='" + 1 + "'", null);
        } else {
            cursor = db.rawQuery("select member_id, first_name, middle_name, last_name from " + MEMBER_TABLE_NAME
                    + " where " + "is_alive ='" + 0 + "'", null);
        }
        while (cursor.moveToNext()){
            String member_id = cursor.getString(0);
            String first_name = cursor.getString(1);
            String middle_name = cursor.getString(2);
            String last_name = cursor.getString(3);
            MemberManagement memberManagement = new MemberManagement(member_id, first_name, middle_name, last_name);
            list.addLast(memberManagement);
        }
        cursor.close();
        db.close();
        return list;
    }

    public LinkedList<MemberManagement> sortMembers(int i){
        SQLiteDatabase db = getWritableDatabase();
        LinkedList<MemberManagement> list = new LinkedList<>();

        Cursor cursor;
        if (i ==1){
            cursor = db.rawQuery("select member_id, first_name, middle_name, last_name from " + MEMBER_TABLE_NAME
                    + " ORDER BY first_name ASC", null);
        } else if (i == 2){
            cursor = db.rawQuery("select member_id, first_name, middle_name, last_name from " + MEMBER_TABLE_NAME
                    + " ORDER BY last_name ASC", null);
        }  else if (i == 3) {
            cursor = db.rawQuery("select member_id, first_name, middle_name, last_name from " + MEMBER_TABLE_NAME
                    + " ORDER BY datetime(registration_date) DESC", null);
        } else {
            cursor = db.rawQuery("select member_id, first_name, middle_name, last_name from " + MEMBER_TABLE_NAME
                    + " ORDER BY datetime(registration_date) ASC", null);
        }

        while (cursor.moveToNext()){
            String member_id = cursor.getString(0);
            String first_name = cursor.getString(1);
            String middle_name = cursor.getString(2);
            String last_name = cursor.getString(3);
            MemberManagement memberManagement = new MemberManagement(member_id, first_name, middle_name, last_name);
            list.addLast(memberManagement);
        }
        cursor.close();
        db.close();
        return list;
    }

    //Last Member
    public String getLastMember(){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT member_id FROM " + MEMBER_TABLE_NAME +" ORDER BY registration_date DESC LIMIT 1",null);
        String lastId = "";
        if(cursor.moveToFirst())
            lastId = cursor.getString(0);
        cursor.close();
        db.close();
        return lastId;
    }

    //get Member by ID
    public String[] getMember(String memberId){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + MEMBER_TABLE_NAME + " where " + "member_id='" + memberId + "'" , null);
        String[] memberInfo = new String[16];
        if(cursor.moveToFirst()) {
            memberInfo[0] = cursor.getString(0);
            memberInfo[1] = cursor.getString(1);
            memberInfo[2] = cursor.getString(2);
            memberInfo[3] = cursor.getString(3);
            memberInfo[4] = cursor.getString(4);
            memberInfo[5] = cursor.getInt(5)+"";
            memberInfo[6] = cursor.getString(6);
            memberInfo[7] = cursor.getString(7);
            memberInfo[8] = cursor.getString(8);
            memberInfo[9] = cursor.getString(9);
            memberInfo[10] = cursor.getString(10);
            memberInfo[11] = cursor.getString(11);
            memberInfo[12] = cursor.getString(12);
            memberInfo[13] = cursor.getString(13);
            memberInfo[14] = cursor.getInt(14)+"";
            memberInfo[15] = cursor.getString(15);
        }
        cursor.close();
        db.close();
        return memberInfo;
    }

    //Add a new Member
    public void addMember(MemberManagement memberManagement){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues row = new ContentValues();
        row.put("member_id", memberManagement.getMemberId());
        row.put("first_name", memberManagement.getFirst_name());
        row.put("middle_name", memberManagement.getMiddle_name());
        row.put("last_name", memberManagement.getLast_name());
        row.put("address", memberManagement.getAddress());
        row.put("zip_code", memberManagement.getZip_code());
        row.put("city", memberManagement.getCity());
        row.put("state", memberManagement.getState());
        row.put("country", memberManagement.getCountry());
        row.put("contact_mobile", memberManagement.getContact_mobile());
        row.put("contact_residence", memberManagement.getContact_residence());
        row.put("contact_office", memberManagement.getContact_office());
        row.put("email", memberManagement.getEmail());
        row.put("date_of_birth", memberManagement.getDate_of_birth());
        row.put("is_alive", 1);
        db.insert(MEMBER_TABLE_NAME,null,row);
        db.close();
    }

    //Update existing Member
    public void updateMember(MemberManagement memberManagement){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues row = new ContentValues();
        row.put("address", memberManagement.getAddress());
        row.put("zip_code", memberManagement.getZip_code());
        row.put("city", memberManagement.getCity());
        row.put("state", memberManagement.getState());
        row.put("country", memberManagement.getCountry());
        row.put("contact_mobile", memberManagement.getContact_mobile());
        row.put("contact_residence", memberManagement.getContact_residence());
        row.put("contact_office", memberManagement.getContact_office());
        row.put("email", memberManagement.getEmail());
        row.put("date_of_birth", memberManagement.getDate_of_birth());
        row.put("is_alive", memberManagement.getIs_alive());
        db.update(MEMBER_TABLE_NAME,row," member_id = ?", new String[]{memberManagement.getMemberId()});
        db.close();
    }

    //Delete selected Member
    public void deleteMember(String memberId){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(MEMBER_TABLE_NAME,"member_id = ?",new String[]{memberId});
        db.close();
    }

    //All Committee List
    public LinkedList<CommitteeManagement> showAllCommittee(){
        SQLiteDatabase db = getWritableDatabase();
        LinkedList<CommitteeManagement> list = new LinkedList<>();
        //INNER Join query
        Cursor cursor = db.rawQuery("SELECT committee_id, name, title FROM " + COMMITTEE_TABLE_NAME + " INNER JOIN "
                + INSTITUTE_TABLE_NAME + " ON " + COMMITTEE_TABLE_NAME + ".institute_id = " + INSTITUTE_TABLE_NAME + ".institute_id",null);
        while (cursor.moveToNext()){
            int committee_id = cursor.getInt(0);
            String name = cursor.getString(1);
            String title = cursor.getString(2);
            CommitteeManagement committeeManagement = new CommitteeManagement(committee_id, name, title);
            list.addLast(committeeManagement);
        }
        cursor.close();
        db.close();
        return list;
    }

    //Add a new Committee
    public void addCommittee(CommitteeManagement committeeManagement){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues row = new ContentValues();
        row.put("institute_id", committeeManagement.getInstitute_id());
        row.put("title", committeeManagement.getTitle());
        row.put("description", committeeManagement.getDescription());
        row.put("date", committeeManagement.getDate());
        db.insert(COMMITTEE_TABLE_NAME,null,row);
        db.close();
    }

    //get Committee by ID
    public String[] getCommittee(int committeeId){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("select *  from " + COMMITTEE_TABLE_NAME + " where " + "committee_id='" + committeeId + "'" , null);
        String[] committeeInfo = new String[4];
        if(cursor.moveToFirst()) {
            committeeInfo[0] = cursor.getInt(0)+"";
            committeeInfo[1] = cursor.getInt(1)+"";
            committeeInfo[2] = cursor.getString(2);
            committeeInfo[3] = cursor.getString(3);
        }
        cursor.close();
        db.close();
        return committeeInfo;
    }

    //Update existing Committee
    public void updateCommittee(CommitteeManagement committeeManagement){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues row = new ContentValues();
        row.put("description", committeeManagement.getDescription());
        db.update(COMMITTEE_TABLE_NAME,row," committee_id = ?", new String[]{String.valueOf(committeeManagement.getCommittee_id())});
        db.close();
    }

    //Delete selected Committee
    public void deleteCommittee(String committeeId){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(COMMITTEE_TABLE_NAME,"committee_id = ?",new String[]{committeeId});
        db.close();
    }

    public LinkedList<CommitteeManagement> searchCommittee(String searchText){
        SQLiteDatabase db = getWritableDatabase();
        LinkedList<CommitteeManagement> list = new LinkedList<>();
        Cursor cursor = db.rawQuery("select committee_id, name, title from " + COMMITTEE_TABLE_NAME + " INNER JOIN "
                + INSTITUTE_TABLE_NAME + " ON " + COMMITTEE_TABLE_NAME + ".institute_id = " + INSTITUTE_TABLE_NAME + ".institute_id"
                + " where " + "title LIKE'" + '%' + searchText + '%' + "'" + " OR name LIKE'" + '%' + searchText + '%' + "'", null);
        while (cursor.moveToNext()){
            int committee_id = cursor.getInt(0);
            String name = cursor.getString(1);
            String title = cursor.getString(2);
            CommitteeManagement committeeManagement = new CommitteeManagement(committee_id, name, title);
            list.addLast(committeeManagement);
        }
        cursor.close();
        db.close();
        return list;
    }

    public LinkedList<CommitteeMemberManagement> showAllCommitteeMember(){
        SQLiteDatabase db = getWritableDatabase();
        LinkedList<CommitteeMemberManagement> list = new LinkedList<>();
        Cursor cursor = db.query(COMMITTEE_MEMBER_TABLE_NAME, new String[]{"committee_id", "member_id"},null,null,null,null,null);
        while (cursor.moveToNext()){
            int committee_id = cursor.getInt(0);
            String member_id = cursor.getString(1);
            CommitteeMemberManagement committeeMemberManagement = new CommitteeMemberManagement(committee_id, member_id);
            list.addLast(committeeMemberManagement);
        }
        cursor.close();
        db.close();
        return list;
    }

    public String getAllCommitteeMember(int committeeId){
        SQLiteDatabase db = getWritableDatabase();
        String member_id ="";
        Cursor cursor = db.rawQuery("select member_id from " + COMMITTEE_MEMBER_TABLE_NAME +" where committee_id = " + committeeId, null);
        while (cursor.moveToNext()){
            member_id = cursor.getString(0);
        }
        cursor.close();
        db.close();
        return member_id;
    }

    public String getMemberName(String id){
        SQLiteDatabase db = getWritableDatabase();
        String memberName ="";
        Cursor cursor = db.rawQuery("select first_name, middle_name, last_name from " + MEMBER_TABLE_NAME +" where member_id = '" + id + "'", null);
        while (cursor.moveToNext()){
            memberName = cursor.getString(0) + " " + cursor.getString(1) + " " + cursor.getString(2);
        }
        cursor.close();
        db.close();
        return memberName;
    }

    public boolean alreadyExist(int committeeId){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + COMMITTEE_MEMBER_TABLE_NAME +" where committee_id = " + committeeId, null);
        if (cursor.getCount() == 0)
            return false;
        return true;
    }

    public boolean isMember(int committeeId){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("select member_id from " + COMMITTEE_MEMBER_TABLE_NAME +" where committee_id = " + committeeId, null);
        String id="";
        while (cursor.moveToNext()){
            id = cursor.getString(0);
        }
        if (id.equals(""))
            return false;
        else
            return true;
    }

    public boolean alreadyExistMember(String memberId){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + COMMITTEE_MEMBER_TABLE_NAME +" where member_id LIKE'" + '%' + memberId + '%' + "'", null);
        if (cursor.getCount() == 0)
            return false;
        return true;
    }

    public LinkedList<CommitteeMemberManagement> getCommitteeList(){
        SQLiteDatabase db = getWritableDatabase();
        LinkedList<CommitteeMemberManagement> list = new LinkedList<>();
        Cursor cursor = db.rawQuery("select "+COMMITTEE_MEMBER_TABLE_NAME+".committee_id, member_id, "+ COMMITTEE_TABLE_NAME+".title from " + COMMITTEE_MEMBER_TABLE_NAME + " INNER JOIN "
                + COMMITTEE_TABLE_NAME + " ON " + COMMITTEE_MEMBER_TABLE_NAME + ".committee_id = " + COMMITTEE_TABLE_NAME + ".committee_id", null);
        while (cursor.moveToNext()){
            int committee_id = cursor.getInt(0);
            String member_id =cursor.getString(1);
            String committeeTitle = cursor.getString(2);
            CommitteeMemberManagement committeeMemberManagement = new CommitteeMemberManagement(committee_id, member_id, committeeTitle);
            list.addLast(committeeMemberManagement);
        }
        cursor.close();
        db.close();
        return list;
    }

    //Add a new Committee Member
    public void addCommitteeMember(CommitteeMemberManagement committeeMemberManagement){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues row = new ContentValues();
        row.put("committee_id", committeeMemberManagement.getCommittee_id());
        row.put("member_id", committeeMemberManagement.getMember_id());
        db.insert(COMMITTEE_MEMBER_TABLE_NAME,null,row);
        db.close();
    }

    //Update existing Committee Member
    public void updateCommitteeMember(CommitteeMemberManagement committeeMemberManagement){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues row = new ContentValues();
        row.put("member_id", committeeMemberManagement.getMember_id());
        db.update(COMMITTEE_MEMBER_TABLE_NAME,row," committee_id = ?", new String[]{String.valueOf(committeeMemberManagement.getCommittee_id())});
        db.close();
    }

    //Delete selected Committee
    public void deleteCommitteeMember(String committeeId){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(COMMITTEE_MEMBER_TABLE_NAME,"committee_id = ?",new String[]{committeeId});
        db.close();
    }

    //All Meeting List
    public LinkedList<MeetingManagement> showAllMeeting(){
        SQLiteDatabase db = getWritableDatabase();
        LinkedList<MeetingManagement> list = new LinkedList<>();
        Cursor cursor = db.rawQuery("SELECT meeting_id," + MEETING_TABLE_NAME +".title," + COMMITTEE_TABLE_NAME + ".title FROM " + MEETING_TABLE_NAME + " INNER JOIN "
                + COMMITTEE_TABLE_NAME + " ON " + MEETING_TABLE_NAME + ".committee_id = " + COMMITTEE_TABLE_NAME + ".committee_id",null);
        while (cursor.moveToNext()){
            int meeting_id = cursor.getInt(0);
            String title = cursor.getString(1);
            String name = cursor.getString(2);
            MeetingManagement meetingManagement = new MeetingManagement(meeting_id, title, name);
            list.addLast(meetingManagement);
        }
        cursor.close();
        db.close();
        return list;
    }

    public LinkedList<MeetingManagement> searchMeeting(String searchText, int i){
        SQLiteDatabase db = getWritableDatabase();
        LinkedList<MeetingManagement> list = new LinkedList<>();
        Cursor cursor = null;
        if (i == 1){
            cursor = db.rawQuery("SELECT meeting_id," + MEETING_TABLE_NAME +".title," + COMMITTEE_TABLE_NAME + ".title FROM " + MEETING_TABLE_NAME + " INNER JOIN "
                    + COMMITTEE_TABLE_NAME + " ON " + MEETING_TABLE_NAME + ".committee_id = " + COMMITTEE_TABLE_NAME + ".committee_id"
                    + " where " + MEETING_TABLE_NAME + ".title LIKE'" + '%' + searchText + '%' + "'" + " OR " + COMMITTEE_TABLE_NAME + ".title LIKE'" + '%' + searchText + '%' + "'",null);
        }else if(i == 2){
            cursor = db.rawQuery("SELECT meeting_id," + MEETING_TABLE_NAME +".title," + COMMITTEE_TABLE_NAME + ".title FROM " + MEETING_TABLE_NAME + " INNER JOIN "
                    + COMMITTEE_TABLE_NAME + " ON " + MEETING_TABLE_NAME + ".committee_id = " + COMMITTEE_TABLE_NAME + ".committee_id"
                    + " where is_active = 1",null);
        }else if(i == 3){
            cursor = db.rawQuery("SELECT meeting_id," + MEETING_TABLE_NAME +".title," + COMMITTEE_TABLE_NAME + ".title FROM " + MEETING_TABLE_NAME + " INNER JOIN "
                    + COMMITTEE_TABLE_NAME + " ON " + MEETING_TABLE_NAME + ".committee_id = " + COMMITTEE_TABLE_NAME + ".committee_id"
                    + " where is_active = 0" ,null);
        }

        while (cursor.moveToNext()){
            int meeting_id = cursor.getInt(0);
            String title = cursor.getString(1);
            String name = cursor.getString(2);
            MeetingManagement meetingManagement = new MeetingManagement(meeting_id, title, name);
            list.addLast(meetingManagement);
        }
        cursor.close();
        db.close();
        return list;
    }

    //get Meeting by ID
    public String[] getMeeting(int meetingId){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("select *,"+ COMMITTEE_TABLE_NAME +".title from " + MEETING_TABLE_NAME + " INNER JOIN "
                + COMMITTEE_TABLE_NAME + " ON " + MEETING_TABLE_NAME + ".committee_id = " + COMMITTEE_TABLE_NAME + ".committee_id"
                + " where " + "meeting_id='" + meetingId + "'" , null);
        String[] meetingInfo = new String[13];
        if(cursor.moveToFirst()) {
            meetingInfo[0] = cursor.getInt(0)+"";
            meetingInfo[1] = cursor.getInt(1)+"";
            meetingInfo[2] = cursor.getString(2);
            meetingInfo[3] = cursor.getString(3);
            meetingInfo[4] = cursor.getInt(4)+"";
            meetingInfo[5] = cursor.getString(5);
            meetingInfo[6] = cursor.getString(6);
            meetingInfo[7] = cursor.getString(7);
            meetingInfo[8] = cursor.getString(8);
            meetingInfo[9] = cursor.getInt(9)+"";
            meetingInfo[10] = cursor.getString(10);
            meetingInfo[11] = cursor.getString(11);
            meetingInfo[12] =cursor.getString(12);
        }
        cursor.close();
        db.close();
        return meetingInfo;
    }

    //Add a new Meeting
    public void addMeeting(MeetingManagement meetingManagement){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues row = new ContentValues();
        row.put("committee_id", meetingManagement.getCommittee_id());
        row.put("title", meetingManagement.getTitle());
        row.put("address", meetingManagement.getAddress());
        row.put("zip_code", meetingManagement.getZip_code());
        row.put("city", meetingManagement.getCity());
        row.put("state", meetingManagement.getState());
        row.put("country", meetingManagement.getCountry());
        row.put("data_time", meetingManagement.getData_time());
        row.put("is_active", meetingManagement.getIs_active());
        row.put("agenda", meetingManagement.getAgenda());
        row.put("note", meetingManagement.getNote());
        db.insert(MEETING_TABLE_NAME,null,row);
        db.close();
    }

    public String getEmail(String memberId) {
        SQLiteDatabase db = getWritableDatabase();
        String Email ="";
        Cursor cursor = db.rawQuery("select email from " + MEMBER_TABLE_NAME +" where member_id = '" + memberId + "'", null);
        while (cursor.moveToNext()){
            Email = cursor.getString(0);
        }
        cursor.close();
        db.close();
        return Email;
    }

    //Update existing Meeting
    public void updateMeeting(MeetingManagement meetingManagement){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues row = new ContentValues();
        row.put("committee_id", meetingManagement.getCommittee_id());
        row.put("address", meetingManagement.getAddress());
        row.put("zip_code", meetingManagement.getZip_code());
        row.put("city", meetingManagement.getCity());
        row.put("state", meetingManagement.getState());
        row.put("country", meetingManagement.getCountry());
        row.put("data_time", meetingManagement.getData_time());
        row.put("is_active", meetingManagement.getIs_active());
        row.put("agenda", meetingManagement.getAgenda());
        row.put("note", meetingManagement.getNote());
        db.update(MEETING_TABLE_NAME,row," meeting_id = ?", new String[]{String.valueOf(meetingManagement.getMeeting_id())});
        db.close();
    }

    //Delete selected Meeting
    public void deleteMeeting(int meetingId){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(MEETING_TABLE_NAME,"meeting_id = ?",new String[]{String.valueOf(meetingId)});
        db.close();
    }
}
