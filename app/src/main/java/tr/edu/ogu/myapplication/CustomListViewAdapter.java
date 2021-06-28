package tr.edu.ogu.myapplication;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomListViewAdapter extends ArrayAdapter<Movie> {

    private final LayoutInflater inflater;
    private final Context context;
    private ViewHolder holder;
    private final ArrayList<Movie> movies;
    private final ArrayList<Movie> likedMovies;


    public CustomListViewAdapter(Context context, ArrayList<Movie> movies, ArrayList<Movie> likedMovies) {
        super(context,0, movies);
        this.context = context;
        this.movies = movies;
        this.likedMovies = likedMovies;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public Movie getItem(int position) {
        return movies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return movies.get(position).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {

            convertView = inflater.inflate(R.layout.movie_item, null);

            holder = new ViewHolder();
            holder.personNameLabel = (TextView) convertView.findViewById(R.id.twname);
            holder.personIdLabel = (TextView) convertView.findViewById(R.id.twid);
            holder.personEmailLabel = (TextView) convertView. findViewById(R.id.twemail);
            holder.personImage = (ImageView) convertView. findViewById(R.id.imageView);
            holder.personReleaseDateLabel = (TextView) convertView.findViewById(R.id.twReleaseDate);
            holder.personTranslatedTitle = (TextView) convertView.findViewById(R.id.twTitleTurkish);
            holder.fbAdd = (FloatingActionButton) convertView.findViewById(R.id.fbAdd);
            convertView.setTag(holder);

        }
        else{
            holder = (ViewHolder)convertView.getTag();
        }
        Movie movie = movies.get(position);
        if(movie != null){
            holder.personNameLabel.setText(movie.getName());
            holder.personIdLabel.setText(movie.getId());
            //holder.personImage(person.getBody());
            holder.personEmailLabel.setText(movie.getEmail());

            Picasso.with(context)
                    .load(movie.getBody())
                    //.resize(450,450)
                    .resize(300,400)
                    .into(holder.personImage);
            holder.personReleaseDateLabel.setText(movie.getReleaseDate());
            holder.personTranslatedTitle.setText(movie.getTranslatedTitle());

            holder.fbAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.rate);
                    dialog.show();
                    RatingBar ratingBar = dialog.findViewById(R.id.ratingBar);
                    ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                        @Override
                        public void onRatingChanged(RatingBar arg0, float rateValue, boolean arg2) {
                           movie.setRating((double) rateValue);
                           saveData();
                        }
                    });
                    likedMovies.add(movie);
                }
            });
        }

        return convertView;
    }
    public void updateData(ArrayList<Movie> list){
        movies.clear();
        movies.addAll(list);
        notifyDataSetChanged();
    }

    public void getRealId(ArrayList<Movie> list){

    }

    //View Holder Pattern for better performance
    private static class ViewHolder {
        TextView personNameLabel;
        TextView personIdLabel;
        ImageView personImage;
        TextView personEmailLabel;
        TextView personReleaseDateLabel;
        TextView personTranslatedTitle;
        FloatingActionButton fbAdd;
    }
    private void saveData() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("shared preferences", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(likedMovies);
        editor.putString("task list", json);
        editor.apply();
    }

}
