package com.example.alarmjava3.feature_alarm.domain;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.alarmjava3.R;
import com.example.alarmjava3.feature_alarm.domain.adapters.AlarmListAdapter;
import com.example.alarmjava3.feature_alarm.domain.models.Alarm;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private AlarmListAdapter alarmAdapter;
    private List<Alarm> alarmList;

    private ActivityResultLauncher<Intent> addAlarmLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViewsAndData();
        setListeners();
    }

    private void setupViewsAndData() {
        ListView listViewAlarms = findViewById(R.id.alarm_list);
        alarmList = new ArrayList<>();
        alarmAdapter = new AlarmListAdapter(this, alarmList);
        listViewAlarms.setAdapter(alarmAdapter);

        addAlarmLauncher =
                registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            Alarm newAlarm = data.getParcelableExtra("new_alarm"); // Or use Parcelable
                            if (newAlarm != null) {
                                alarmList.add(newAlarm);
                                alarmAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
    }

    private void setListeners() {
        Button addAlarmButton = findViewById(R.id.btn_add_alarm);
        Button exitButton = findViewById(R.id.btn_exit);

        addAlarmButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddAlarmActivity.class);
            addAlarmLauncher.launch(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        });

        exitButton.setOnClickListener(v -> finish());
    }


}