package com.example.findbrew;

import android.os.Parcel;
import android.os.Parcelable;

// parcelable tutorial
// https://medium.com/@gaandlaneeraja/how-to-pass-objects-between-android-activities-86f2cfb61bd4
public class Brew implements Parcelable {
    private String name;
    private String image_url;
    private String description;
    private String abv;
    private String first_brewed;
    private String food_pairings;
    private String brewster_tips;


    private boolean fav;

    public Brew(String name, String image_url, String description,
                String abv, String first_brewed, String food_pairings, String brewster_tips){
        this.name = name;
        this.image_url = image_url;
        this.description = description;
        this.abv = abv;
        this.first_brewed = first_brewed;
        this.food_pairings = food_pairings;
        this.brewster_tips = brewster_tips;

        fav = false;
    }


    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return image_url;
    }

    public void setImageUrl(String image_url) {
        this.image_url = image_url;
    }


    public String getDescription() {
        return description;
    }

    public boolean isFav() {
        return fav;
    }

    public void setFav(boolean fav) {
        this.fav = fav;
    }

    public String getAbv() {
        return abv;
    }

    public void setAbv(String abv) {
        this.abv = abv;
    }

    public String getFirst_brewed() {
        return first_brewed;
    }

    public void setFirst_brewed(String first_brewed) {
        this.first_brewed = first_brewed;
    }

    public String getFood_pairings() {
        return food_pairings;
    }

    public void setFood_pairings(String food_pairings) {
        this.food_pairings = food_pairings;
    }

    public String getBrewster_tips() {
        return brewster_tips;
    }

    public void setBrewster_tips(String brewster_tips) {
        this.brewster_tips = brewster_tips;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.image_url);
        dest.writeString(this.description);
        dest.writeString(this.abv);
        dest.writeString(this.first_brewed);
        dest.writeString(this.food_pairings);
        dest.writeString(this.brewster_tips);
    }

    protected Brew(Parcel in) {
        this.name = in.readString();
        this.image_url = in.readString();
        this.description = in.readString();
        this.abv = in.readString();
        this.first_brewed = in.readString();
        this.food_pairings = in.readString();
        this.brewster_tips = in.readString();
    }

    public static final Parcelable.Creator<Brew> CREATOR = new Parcelable.Creator<Brew>() {
        @Override
        public Brew createFromParcel(Parcel source) {
            return new Brew(source);
        }

        @Override
        public Brew[] newArray(int size) {
            return new Brew[size];
        }
    };
}

