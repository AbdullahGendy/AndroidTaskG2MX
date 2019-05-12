package app.company.androidtask.activities.Home.Fragments.CalendarEventFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.Date;

import app.company.androidtask.R;

public class CalendarEvent extends Fragment {

    Button calenderButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar_event, container, false);

        calenderButton = view.findViewById(R.id.calender_button);
        calenderButton.setOnClickListener(view1 -> {
            Intent i = new Intent();

            i.setType("vnd.android.cursor.item/event");

            i.putExtra("beginTime", new Date().getTime());
            i.putExtra("endTime", new Date().getTime() + DateUtils.HOUR_IN_MILLIS);

            i.setAction(Intent.ACTION_EDIT);
            startActivity(i);
        });
        return view;
    }

}
