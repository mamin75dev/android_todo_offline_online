package com.shariat.mysuperapp.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shariat.mysuperapp.Models.City;
import com.shariat.mysuperapp.R;
import com.shariat.mysuperapp.UI.WeatherDetailsActivity;

import java.util.ArrayList;

public class CityRVAdapter extends RecyclerView.Adapter<CityRVAdapter.ViewHolder> {

  private ArrayList<City> cities;
  private Context context;
  private Activity activity;

  public CityRVAdapter(ArrayList<City> cities, Context context) {
    this.cities = cities;
    this.context = context;
  }

  @NonNull
  @Override
  public CityRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new ViewHolder(LayoutInflater.from(parent.getContext())
        .inflate(R.layout.city_item, parent, false)
    );
  }

  @Override
  public void onBindViewHolder(@NonNull CityRVAdapter.ViewHolder holder, int position) {
    City city = cities.get(position);
    holder.nameTV.setText(city.toString());
    holder.layout.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
//        holder.frameLayout.setVisibility(View.VISIBLE);
//        ((FragmentActivity) context).getSupportFragmentManager()
//            .beginTransaction()
//            .add(R.id.weather_fragment, WeatherFragment.newInstance(city.getName()))
//            .commit();
        Intent intent = new Intent(context, WeatherDetailsActivity.class);
        intent.putExtra("id", city.getId());
        intent.putExtra("name", city.getName());
        context.startActivity(intent);

      }
    });
  }

  @Override
  public int getItemCount() {
    return cities.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    private TextView nameTV;
    private RelativeLayout layout;
//    private FrameLayout frameLayout;

    public ViewHolder(@NonNull View itemView) {
      super(itemView);

      nameTV = itemView.findViewById(R.id.item_city_name);
      layout = itemView.findViewById(R.id.city_item_container);
//      frameLayout = itemView.findViewById(R.id.weather_fragment);
    }

  }
}
