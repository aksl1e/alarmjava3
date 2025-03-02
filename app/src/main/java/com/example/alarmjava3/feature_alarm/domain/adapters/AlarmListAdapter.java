package com.example.alarmjava3.feature_alarm.domain.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.alarmjava3.R;
import com.example.alarmjava3.feature_alarm.domain.models.Alarm;

import java.util.List;

public class AlarmListAdapter extends ArrayAdapter<Alarm> {
    public AlarmListAdapter(Context context, List<Alarm> alarms) {
        super(context, 0, alarms);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.alarm_item, parent, false);
        }

        Alarm alarm = getItem(position);

        TextView tvAlarmTime = convertView.findViewById(R.id.tv_alarm_time_name);
        TextView tvRepeatWeekly = convertView.findViewById(R.id.tv_repeat_weekly);
        TextView tvDays = convertView.findViewById(R.id.tv_days);
        TextView tvVolume = convertView.findViewById(R.id.tv_volume);


        String alarmTimeName = alarm.getTime() + " - " + alarm.getName();
        String repeatWeekly = alarm.isRepeatWeekly() ? "Powtarzaj co tydzień" : "Nie powtarzaj";
        String days = "Dni: " + alarm.getDays();
        String volume = "Głośność: " + alarm.getVolume() + "%";

        tvAlarmTime.setText(alarmTimeName);
        tvRepeatWeekly.setText(repeatWeekly);
        tvDays.setText(days);
        tvVolume.setText(volume);

        return convertView;
    }
}
