package com.kartik.newsapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<MyModel> articles;
    MyAdapter adapter;
    public static final String API_KEY = "[ PASTE YOUR API KEY ]";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences preferences = getSharedPreferences("theme_pref", MODE_PRIVATE);
        boolean isDarkMode = preferences.getBoolean("is_dark_mode", false);

        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.myRecycler);
        articles = new ArrayList<>();
        adapter = new MyAdapter(articles, this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        MyInterface newsApiInterface = MyCaller.getInstance().getNewsApiInterface();
        Call<NewsData> call = newsApiInterface.getTopHeadlines("in", API_KEY);
        call.enqueue(new Callback<NewsData>() {
            @Override
            public void onResponse(@NonNull Call<NewsData> call, @NonNull Response<NewsData> response) {
                if (response.isSuccessful()) {
                    NewsData newsResponse = response.body();
                    if (newsResponse != null) {
                        articles.clear();
                        articles.addAll(newsResponse.getArticles());
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(MainActivity.this, "No articles found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Failed to fetch news", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<NewsData> call, @NonNull Throwable throwable) {
                Toast.makeText(MainActivity.this, "Error Occurred", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_toggle_theme) {
            toggleTheme();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void toggleTheme() {
        SharedPreferences preferences = getSharedPreferences("theme_pref", MODE_PRIVATE);
        boolean isDarkMode = preferences.getBoolean("is_dark_mode", false);
        SharedPreferences.Editor editor = preferences.edit();

        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            editor.putBoolean("is_dark_mode", false);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            editor.putBoolean("is_dark_mode", true);
        }

        editor.apply();
        recreate();
    }
}
