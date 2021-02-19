package com.example.findbrew;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BrewAdapter extends RecyclerView.Adapter<BrewAdapter.ViewHolder>{
    private List<Brew> brews;

    //pass this list into the constructor of the adapter
    public BrewAdapter(List<Brew> brews){
        this.brews = brews;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // inflate the customer layout
        View brewView = inflater.inflate(R.layout.item_brew, parent, false);
        return new ViewHolder(brewView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // populate data into the item through holder
        Brew brew = brews.get(position);

        holder.textView_name.setText(brew.getName());
        holder.textView_description.setText(brew.getDescription());

        Picasso.get().load(brew.getImageUrl()).into(holder.imageView_beer);

        if (brew.isFav()){
            Picasso.get().load("file:///android_asset/star_on.png").into(holder.imageView_fav_icon);
        } else {
            Picasso.get().load("file:///android_asset/star_off.png").into(holder.imageView_fav_icon);
        }
        // fav_icon change on click
        holder.imageView_fav_icon.setOnClickListener(v -> {
            brew.setFav(!brew.isFav());
            this.notifyItemChanged(position);
        });

        // click beer image to go to the detail page
        holder.imageView_beer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchDetailProductActivity(view, brew);
            }
        });
    }

    @Override
    public int getItemCount() {
        // return the total number of items in the list
        return brews.size();
    }

    // get info from API
    public void launchDetailProductActivity(View view, Brew brew) {
        Intent intent = new Intent(view.getContext(), ProductActivity.class);
        intent.putExtra("brew", brew);
        view.getContext().startActivity(intent);
    }

    // update when text input change
    public void updateBrews(List<Brew> brews){
        this.brews = brews;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView_name;
        ImageView imageView_beer;
        TextView textView_description;
        ImageView imageView_fav_icon;

        public ViewHolder(View itemView){
            super(itemView);
            textView_name = itemView.findViewById(R.id.textView_brew_name);
            imageView_beer = itemView.findViewById(R.id.imageView_brew);
            textView_description = itemView.findViewById(R.id.textView_description);
            imageView_fav_icon = itemView.findViewById(R.id.imageView_fav_icon);
        }

    }

}
