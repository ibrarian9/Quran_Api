package com.app.uas_quran;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.app.uas_quran.Model.SurahModel.Chapters;
import com.app.uas_quran.Model.SurahModel.ChaptersItem;
import com.app.uas_quran.Retrofit.ApiBase;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private MainAdapter mainAdapter;
    private RecyclerView recyclerView;
    private List<ChaptersItem> hasil = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getDataFromApi();
        setUpView();
        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        mainAdapter = new MainAdapter(hasil);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mainAdapter);
    }

    private void setUpView() {
        recyclerView = findViewById(R.id.rvSurah);
    }

    private void getDataFromApi() {
        ApiBase.endpoint().getSurah().enqueue(new Callback<Chapters>() {
            @Override
            public void onResponse(Call<Chapters> call, Response<Chapters> response) {
                if (response.isSuccessful()){
                    List<ChaptersItem> hasil = response.body().getChapters();
                    Log.d("Main", hasil.toString());
                    mainAdapter.setData(hasil);
                }
            }

            @Override
            public void onFailure(Call<Chapters> call, Throwable t) {
                Log.d("ErrorMain", t.toString());
            }
        });
    }
}