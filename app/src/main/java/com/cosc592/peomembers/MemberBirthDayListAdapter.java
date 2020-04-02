package com.cosc592.peomembers;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

public class MemberBirthDayListAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private List<MemberManagement> memberBirthDayList;
    TextView memberNameText;
    ImageButton emailMember;

    public MemberBirthDayListAdapter(Context context, List<MemberManagement> memberList) {
        layoutInflater =LayoutInflater.from(context);
        this.memberBirthDayList = memberList;
    }

    @Override
    public int getCount() {
        return memberBirthDayList.size();
    }

    @Override
    public Object getItem(int position) {
        return memberBirthDayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.birthday_member_list_item, null);

                memberNameText = convertView.findViewById(R.id.memberName);
                emailMember = convertView.findViewById(R.id.emailMember);

                MemberManagement memberManagement = memberBirthDayList.get(position);

                ButtonHandler handler = new ButtonHandler(position);
                emailMember.setOnClickListener(handler);

                memberNameText.setText(memberManagement.getFirst_name() + " " + memberManagement.getMiddle_name() + " " + memberManagement.getLast_name());
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
            if (v.getId() == emailMember.getId()){
                MemberManagement memberManagement = memberBirthDayList.get(rowNumber);
                Log.d("Email", memberManagement.getEmail());
                new BirthdayActivity().sendEmail(memberManagement.getEmail(), memberManagement.getLast_name());
                /*MemberActivity m = new MemberActivity();
                m.Update(memberManagement.getMemberId()+"");*/
            }
        }
    }
}
