package com.example.alarmjava3.feature_alarm.domain;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.alarmjava3.R;
import com.example.alarmjava3.feature_alarm.domain.models.Alarm;

import java.util.Locale;

public class AddAlarmActivity extends AppCompatActivity {

    private TimePicker timePicker;
    private CheckBox repeatWeekly;
    private CheckBox[] days;
    private SeekBar volumeControl;
    private TextView volumePercentage;
    private EditText alarmName;
    private Button buttonSave;
    private Button buttonCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alarm);
        findViews();
        setListeners();
    }

    private void findViews() {
        timePicker = findViewById(R.id.time_picker);
        repeatWeekly = findViewById(R.id.repeat_weekly);
        volumeControl = findViewById(R.id.volume_control);
        volumePercentage = findViewById(R.id.volume_percentage);
        alarmName = findViewById(R.id.alarm_name);
        days = new CheckBox[]{
                findViewById(R.id.day_monday),
                findViewById(R.id.day_tuesday),
                findViewById(R.id.day_wednesday),
                findViewById(R.id.day_thursday),
                findViewById(R.id.day_friday),
                findViewById(R.id.day_saturday),
                findViewById(R.id.day_sunday)
        };

        buttonSave = findViewById(R.id.btn_save);
        buttonCancel = findViewById(R.id.btn_cancel);
    }
    private void setListeners() {
        buttonSave.setOnClickListener(v -> {
            Alarm alarm = getNewAlarm();
            if(alarm == null) {
                return;
            }

            Intent intent = new Intent(AddAlarmActivity.this, MainActivity.class);
            intent.putExtra("new_alarm", alarm);
            setResult(RESULT_OK, intent);
            finishWithAnimation();
        });

        buttonCancel.setOnClickListener(v -> finishWithAnimation());

        setVolumePercentageListener();
    }

    private void setVolumePercentageListener() {
        var initialVolumeText = volumeControl.getProgress() + "%";
        volumePercentage.setText(initialVolumeText);

        volumeControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                var progressText = progress + "%";
                volumePercentage.setText(progressText);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    private Alarm getNewAlarm() {
        int hour = timePicker.getHour();
        int minute = timePicker.getMinute();

        var stringDays = getStringDays();
        if(stringDays.isEmpty()) {
            return null;
        }
        return new Alarm(
                String.format(Locale.getDefault(), "%02d:%02d", hour, minute),
                repeatWeekly.isChecked(),
                stringDays,
                volumeControl.getProgress(),
                alarmName.getText().toString().isEmpty() ? "Alarm" : alarmName.getText().toString().trim()
        );
    }

    private String getStringDays() {
        StringBuilder selectedDays = new StringBuilder();
        String[] dayNames = {"Pon", "Wt", "Śr", "Czw", "Pt", "Sob", "Nd"};

        for (int i = 0; i < days.length; i++) {
            if (days[i].isChecked()) {
                selectedDays.append(dayNames[i]).append(", ");
            }
        }
        if (selectedDays.length() > 0) {
            selectedDays.setLength(selectedDays.length() - 2); // Remove ", " at the end
        }

        if(selectedDays.toString().isEmpty()) {
            Toast.makeText(this, "Wybierz co najmniej jeden dzień tygodnia!", Toast.LENGTH_SHORT).show();
            return "";
        }

        return selectedDays.toString();
    }

    private void finishWithAnimation() {
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}