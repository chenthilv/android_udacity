package com.hollywood.movies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sort_movie_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        MainFragment mainFragment = (MainFragment) getFragmentManager().findFragmentById(R.id.fragment);

        int id = item.getItemId();

        if(id == R.id.highly_rated){
            mainFragment.loadMovies("highlyRated");
        }else if(id == R.id.popular_movies){
            mainFragment.loadMovies("popularMovies");
        }

        return super.onOptionsItemSelected(item);
    }
}
