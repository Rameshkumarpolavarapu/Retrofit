package com.example.ramesh.retrofit.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.ramesh.retrofit.R;
import com.example.ramesh.retrofit.adapter.MoviesAdapter;
import com.example.ramesh.retrofit.model.Movie;
import com.example.ramesh.retrofit.model.MovieResponse;
import com.example.ramesh.retrofit.rest.ApiClient;
import com.example.ramesh.retrofit.rest.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String Tag =   MainActivity.class.getSimpleName();
    //to get API KEY from the follow site and register then, get API KEY from Settings
    //
    // https://www.themoviedb.org/
    private final static  String API_KEY    =   "d5222d5dfb1614768de916afbc3823e4";//user key to be change


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Toast.makeText(this, ""+Tag, Toast.LENGTH_SHORT).show();
        if (API_KEY.isEmpty()){
            Toast.makeText(this, "Please obtain your API KEY first from themoviedb.org", Toast.LENGTH_SHORT).show();
            return;
        }
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.movies_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<MovieResponse> call    =   apiService.getTopRatedMovies(API_KEY);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                List<Movie> movies  =   response.body().getResults();
                Log.d(Tag,"No of Movies received: "+movies.size());
                movies = response.body().getResults();
                recyclerView.setAdapter(new MoviesAdapter(movies, R.layout.list_item_movie, getApplicationContext()));

            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e(Tag, t.toString());
            }
        });

    }
}
