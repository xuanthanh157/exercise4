package com.example.xuanthanh.homeexercise_week3;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xuanthanh.homeexercise_week3.Constant;
import com.example.xuanthanh.homeexercise_week3.R;
import com.example.xuanthanh.homeexercise_week3.pojo.Result;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MovieInformationAdapter extends RecyclerView.Adapter<MovieInformationAdapter.ViewHolder> {

    private Context context;
    private List<Result> listMovies;
    private MovieItemClickListener listener;

    public interface MovieItemClickListener {
        void onItemClick(Result result);
    }

    public MovieInformationAdapter(Context context, List<Result> listMovies, MovieItemClickListener listener) {
        this.context = context;
        this.listMovies = listMovies;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_movie_information, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Result result = listMovies.get(position);
        Picasso.with(context)
                .load(Constant.HEADER_URL_IMAGE + result.getPosterPath())
                .fit()
                .centerInside()
                .into(holder.poster);
        holder.title.setText(result.getTitle());
        holder.overview.setText(result.getOverview());
        holder.playMovie.setVisibility(result.getVideo() ? View.VISIBLE : View.GONE);
        holder.itemView.setOnClickListener(v -> listener.onItemClick(result));
    }

    public void clear(){
        listMovies.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Result> listMovies){
        this.listMovies.addAll(listMovies);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listMovies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_poster)
        ImageView poster;
        @BindView(R.id.movie_title)
        TextView title;
        @BindView(R.id.movie_overview)
        TextView overview;
        @BindView(R.id.play_movie)
        ImageView playMovie;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}