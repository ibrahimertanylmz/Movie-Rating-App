package tr.edu.ogu.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RatedMoviesAdapter extends ArrayAdapter<Movie> {
    private final LayoutInflater inflater;
    private final Context context;
    private RatedMoviesAdapter.ViewHolder holder;
    private final ArrayList<Movie> movies;


    public RatedMoviesAdapter(Context context, ArrayList<Movie> movies) {
        super(context,0, movies);
        this.context = context;
        this.movies = movies;
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

            convertView = inflater.inflate(R.layout.rated_movies_item, null);

            holder = new RatedMoviesAdapter.ViewHolder();
            holder.personNameLabel = (TextView) convertView.findViewById(R.id.twRatedname);
            holder.personIdLabel = (TextView) convertView.findViewById(R.id.twRatedid);
            holder.personEmailLabel = (TextView) convertView. findViewById(R.id.twRatedemail);
            holder.personImage = (ImageView) convertView. findViewById(R.id.RatedimageView);
            holder.personReleaseDateLabel = (TextView) convertView.findViewById(R.id.twRatedReleaseDate);
            holder.personTranslatedTitle = (TextView) convertView.findViewById(R.id.twRatedTitleTurkish);
            convertView.setTag(holder);

        }
        else{
            holder = (RatedMoviesAdapter.ViewHolder)convertView.getTag();
        }
        Movie movie = movies.get(position);
        if(movie != null){
            holder.personNameLabel.setText(movie.getName());
            holder.personIdLabel.setText(movie.getId());
            //holder.personImage(person.getBody());
            holder.personEmailLabel.setText("Verilen Oy: "+movie.getRating().toString());

            Picasso.with(context)
                    .load(movie.getBody())
                    //.resize(450,450)
                    .resize(300,400)
                    .into(holder.personImage);
            holder.personReleaseDateLabel.setText(movie.getReleaseDate());
            holder.personTranslatedTitle.setText(movie.getTranslatedTitle());

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
    }
}
