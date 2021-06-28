package tr.edu.ogu.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static String BASE_URL=  "https://api.themoviedb.org";
    public String posterBaseUrl = "https://image.tmdb.org/t/p/w500/";
    public ArrayList<Movie> movies;
    public ArrayList<Movie> filteredMovies;
    public static ArrayList<Movie> likedMovies;
    private ListView listView;
    private ListView dialogListView;
    private CustomListViewAdapter listViewAdapter;
    private RatedMoviesAdapter dialogListViewAdapter;
    public Integer PAGE = 1;
    EditText edtSearch;
    Button btn;
    private String SearchText = "";
    Dialog dialog;

    public static String API_KEY ="39c5465cd4d393531f1e739433a8e360";
    public static String CATEGORY ="popular";
    public static String LANGUAGE ="tr-tr";

    Integer a = 45;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movies = new ArrayList<Movie>();
        filteredMovies = new ArrayList<Movie>();

        //likedMovies = new ArrayList<>();

        loadData();
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.rated_movies);
        dialogListView = dialog.findViewById(R.id.lvRatedMovies);
        dialogListViewAdapter = new RatedMoviesAdapter(dialog.getContext(), likedMovies);
        dialogListView.setAdapter(dialogListViewAdapter);

        listView =findViewById(R.id.lwitems);
        edtSearch = findViewById(R.id.edtSearch);
        btn = findViewById(R.id.button);

        listViewAdapter = new CustomListViewAdapter(MainActivity.this, filteredMovies,likedMovies);
            listView.setAdapter(listViewAdapter);

        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(BASE_URL)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

        ApiInterface myInterface = retrofit.create(ApiInterface.class);

        for (int i=0; i<500;i++) {
            getPageOfMovies(myInterface);
            PAGE++;
        }
        //filterAndNotify();


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a = likedMovies.size();
                Toast.makeText(MainActivity.this, a.toString(), Toast.LENGTH_LONG).show();
                dialog.show();
            }
        });

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                for(int i = 0; i< filteredMovies.size(); i++){
                        for(int j = 0; j< movies.size(); j++)
                        {
                            if(filteredMovies.get(i).getId()== movies.get(j).getId())
                            {
                                movies.get(j).setChecked(filteredMovies.get(i).isChecked());
                            }
                        }
                }
                filteredMovies.clear();
               if(edtSearch.getText().length()>=3){
               for(int i = 0; i< movies.size(); i++){

                   if((movies.get(i).getId()+ movies.get(i).getTranslatedTitle()).toLowerCase().contains(edtSearch.getText().toString().toLowerCase()))
                   {
                       filteredMovies.add(movies.get(i));
                   }
               }
               listViewAdapter.notifyDataSetChanged();
               }

               //saveData();
            }
        });
    }

    public void getPageOfMovies(ApiInterface myInterface){

        Call<MovieResults> call = myInterface.listOfMovies(CATEGORY, API_KEY, LANGUAGE, PAGE);
        call.enqueue(new Callback<MovieResults>() {
            @Override
            public void onResponse(Call<MovieResults> call, Response<MovieResults> response) {
                MovieResults results = response.body();
                List<MovieResults.Result> listOfMovies = results.getResults();
                //Toast.makeText(MainActivity.this, count.toString(), Toast.LENGTH_SHORT).show();
                Call<MovieResults> callResults = myInterface.listOfMovies(CATEGORY, API_KEY, LANGUAGE, PAGE);

                for (int i = 0; i < listOfMovies.size(); i++) {
                    //if(listOfMovies.get(i).getTitle().contains(text)) {
                        Movie p = new Movie(listOfMovies.get(i).getVoteAverage().toString(), listOfMovies.get(i).getOriginalTitle(), posterBaseUrl + listOfMovies.get(i).getPosterPath(), listOfMovies.get(i).getOverview(),listOfMovies.get(i).getReleaseDate(),listOfMovies.get(i).getTitle(),listOfMovies.get(i).getId(),0.0);
                        movies.add(p);
                        listOfMovies.get(i).getPosterPath();
                    //}
                }
            }
            @Override
            public void onFailure(Call<MovieResults> call, Throwable t) {
            }
        });

    }
    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task list", null);
        Type type = new TypeToken<ArrayList<Movie>>() {}.getType();
        likedMovies = gson.fromJson(json, type);
        if (likedMovies == null) {
            likedMovies = new ArrayList<>();
        }
    }

}
