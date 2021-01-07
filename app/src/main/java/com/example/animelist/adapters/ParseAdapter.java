package com.example.animelist.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.animelist.activities.ParseDetails;
import com.example.animelist.models.Anime;
import com.example.animelist.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aws on 28/01/2018.
 */

public class ParseAdapter extends RecyclerView.Adapter<ParseAdapter.MyViewHolder> implements Filterable {

    RequestOptions options ;
    private Context mContext ;
    private List<Anime> mData ;
    private List<Anime> mDataFULL ;


    public ParseAdapter(Context mContext, List<Anime> lst) {


        this.mContext = mContext;
        this.mData = lst;
        mDataFULL = new ArrayList<>(lst);
        options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.loading_shape)
                .error(R.drawable.loading_shape);

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.parse_item,parent,false);
        // click listener here
        final MyViewHolder viewHolder = new MyViewHolder(view);
        viewHolder.view_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext, ParseDetails.class);
                i.putExtra("anime_name", mData.get(viewHolder.getAdapterPosition()).getName());
                i.putExtra("anime_description", mData.get(viewHolder.getAdapterPosition()).getDescription());
                i.putExtra("anime_studio", mData.get(viewHolder.getAdapterPosition()).getStudio());
                i.putExtra("anime_category", mData.get(viewHolder.getAdapterPosition()).getCategorie());
                i.putExtra("anime_nb_episode", mData.get(viewHolder.getAdapterPosition()).getNb_episode());
                i.putExtra("anime_img", mData.get(viewHolder.getAdapterPosition()).getImage_url());
                i.putExtra("anime_rating", mData.get(viewHolder.getAdapterPosition()).getRating());

                mContext.startActivity(i);

            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.tvname.setText(mData.get(position).getName());
        holder.tv_rate.setText(mData.get(position).getRating());
        holder.tvstudio.setText(mData.get(position).getStudio());
        holder.tvcat.setText(mData.get(position).getCategorie());
        holder.tv_nb_episode.setText(mData.get(position).getNb_episode());

        // load image from the internet using Glide
        Glide.with(mContext).load(mData.get(position).getImage_url()).apply(options).into(holder.AnimeThumbnail);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvname,tv_rate,tvstudio,tvcat, tv_nb_episode;
        ImageView AnimeThumbnail;
        LinearLayout view_container;


        public MyViewHolder(View itemView) {
            super(itemView);
            view_container = itemView.findViewById(R.id.container);
            tvname = itemView.findViewById(R.id.anime_name);
            tvstudio = itemView.findViewById(R.id.studio);
            tv_rate = itemView.findViewById(R.id.rating);
            tvcat = itemView.findViewById(R.id.categorie);
            AnimeThumbnail = itemView.findViewById(R.id.thumbnail);
            tv_nb_episode = itemView.findViewById(R.id.nb_episode);
        }
    }

    @Override
    public Filter getFilter(){
        return animeFilter;
    }

    public Filter animeFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Anime> filteredList = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(mDataFULL);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for (Anime item : mDataFULL) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            mData.clear();
            mData.addAll((List) filterResults.values);
            notifyDataSetChanged();

        }
    };

}
