package com.app.uas_quran;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.app.uas_quran.Model.AyatModel.Verses;
import com.app.uas_quran.Model.AyatModel.VersesItem;
import com.app.uas_quran.Model.Terjemahan.Indo;
import com.app.uas_quran.Model.Terjemahan.TranslationsItem;
import com.app.uas_quran.Retrofit.ApiBase;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailSurahActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private AyatAdapter ayatAdapter;
    private IndoAdapter indoAdapter;
    private ConcatAdapter concatAdapter;
    private final List<VersesItem> versesItemList = new ArrayList<>();
    private final List<TranslationsItem> translationsItemList = new ArrayList<>();

    @Override
    protected void onCreate (Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_detail_surah);

            int id = getIntent().getIntExtra("id", 1);

            setUpView();
            setUpRecyclerView();
            getVersesData(id);
            getTranslateData(id);
        }
    private void getTranslateData(int id) {
            ApiBase.endpoint().getIndo(id).enqueue(new Callback<Indo>() {
                @Override
                public void onResponse(Call<Indo> call, Response<Indo> response) {
                if (response.isSuccessful()){
                    List<TranslationsItem> result = response.body().getTranslations();
                    Log.d("Indo", result.toString());
                    indoAdapter.setData(result);
                    }
                }
                @Override
                public void onFailure(Call<Indo> call, Throwable t) {
                    Log.d("error", t.toString());
                }
            });
        }
    private void getVersesData (int id){
        ApiBase.endpoint().getAyat(id).enqueue(new Callback<Verses>() {
            @Override
            public void onResponse(Call<Verses> call, Response<Verses> response) {
            if (response.isSuccessful()){
                List<VersesItem> versesItems = response.body().getVerses();
                ayatAdapter.setData(versesItems);
                }
            }
            @Override
            public void onFailure(Call<Verses> call, Throwable t) {
            }
        });
    }
    private void setUpRecyclerView () {
        ayatAdapter = new AyatAdapter(versesItemList);
        indoAdapter = new IndoAdapter(translationsItemList);
        concatAdapter = new ConcatAdapter(ayatAdapter, indoAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(concatAdapter);
    }
    private void setUpView () {
        recyclerView = findViewById(R.id.rvAyat);
    }
}

