package com.corelambda.touristapp;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.corelambda.touristapp.datamodel.WikipediaPage;

import java.util.List;

public class TouristRecyclerAdapter extends RecyclerView.Adapter<TouristRecyclerAdapter.TouristHolder> {

    List<WikipediaPage> touristSites;

    public TouristRecyclerAdapter(List<WikipediaPage> touristSites) {
        this.touristSites = touristSites;
    }

    @NonNull
    @Override
    public TouristHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ConstraintLayout v = (ConstraintLayout) inflater.inflate(R.layout.tourist_list_item, parent, false);

        TouristHolder holder = new TouristHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TouristHolder holder, int position) {
        WikipediaPage site = touristSites.get(position);
        holder.bindView(site);
    }

    @Override
    public int getItemCount() {
        return touristSites.size();
    }

    public static class TouristHolder extends RecyclerView.ViewHolder {

        private ConstraintLayout view;
        private TextView textView;
        private ImageView imageView;

        public TouristHolder(View itemView) {
            super(itemView);
            this.view = (ConstraintLayout) itemView;
            this.textView = view.findViewById(R.id.textView);
            this.imageView = view.findViewById(R.id.imageView);
        }

        public void bindView(WikipediaPage touristItem) {
            textView.setText(touristItem.getTitle());

            if (touristItem.getThumbnail() != null
                    && !TextUtils.isEmpty(touristItem.getThumbnail().getSource())) {

                Glide.with(view)
                        .load(touristItem.getThumbnail().getSource())
                        .apply(RequestOptions.circleCropTransform()
                                .error(android.R.drawable.stat_notify_error)
                                .placeholder(android.R.drawable.btn_star))
                        .into(this.imageView);
            } else {
                Glide.with(view)
                        .load(R.drawable.image_error)
                        .apply(RequestOptions.circleCropTransform()
                                .error(R.drawable.image_error)
                                .placeholder(R.drawable.image_error))
                        .into(this.imageView);
            }
        }
    }
}
