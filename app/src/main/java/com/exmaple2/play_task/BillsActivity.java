package com.exmaple2.play_task;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.exmaple2.play_task.data.BillsAdapter;
import com.exmaple2.play_task.data.BillsItem;
import com.exmaple2.play_task.data.DataBank;
import com.exmaple2.play_task.data.ScoreHistoryItem;

import java.util.ArrayList;
import java.util.List;

public class BillsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private BillsAdapter billsAdapter;
    private List<BillsItem> billsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bills);

        // Enable the Up button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        recyclerView = findViewById(R.id.recyclerView_bills);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadBillsData();
        billsAdapter = new BillsAdapter(billsList);
        recyclerView.setAdapter(billsAdapter);
    }

    private void loadBillsData() {
        DataBank dataBank = new DataBank();
        ArrayList<ScoreHistoryItem> scoreHistory = dataBank.loadScoreHistory(this);

        billsList = new ArrayList<>();
        for (ScoreHistoryItem item : scoreHistory) {
            billsList.add(new BillsItem(item.getName(), item.getScore()));
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Respond to the action bar's Up/Home button
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
