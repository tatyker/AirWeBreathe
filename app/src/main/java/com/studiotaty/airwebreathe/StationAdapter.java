package com.studiotaty.airwebreathe;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.studiotaty.airwebreathe.model.Station;

import java.util.ArrayList;

public class StationAdapter extends RecyclerView.Adapter<StationAdapter.ViewHolder> {

    private ArrayList<Station> stationData;
    private Context context;


    public StationAdapter(ArrayList<Station> stationData, Context context) {
        this.stationData = stationData;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Station currentStation = stationData.get(position);
        holder.bindTo(currentStation);

    }

    @Override
    public int getItemCount() {
        return stationData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView stationNameView;
        private TextView stationTimeView;
        private TextView stationAqiView;
        private CardView cardView;
        private Integer bkgColorNumber;
        private Integer txtColorNumber;
        private Integer infoMessageNumber;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            stationNameView = itemView.findViewById(R.id.stationName);
            stationTimeView = itemView.findViewById(R.id.stationTime);
            stationAqiView = itemView.findViewById(R.id.stationAqi);
            cardView = itemView.findViewById(R.id.cardView);


            itemView.setOnClickListener(this);
        }


        public void bindTo(Station currentStation){
            stationNameView.setText(currentStation.getStationName());
            stationTimeView.setText(currentStation.getStationTime());

            String aqiString = currentStation.getAqi();
            stationAqiView.setText(aqiString);


            if(aqiString.matches("\\d+")){
                Integer aqi = Integer.parseInt(aqiString);
                if(aqi <= 50){
                    bkgColorNumber = Color.GREEN;
                    txtColorNumber = Color.BLACK;
                    infoMessageNumber = R.string.green_message;
                    cardView.setClickable(true);
                } else if (aqi > 50 && aqi <=100 ){
                    bkgColorNumber = Color.YELLOW;
                    txtColorNumber = Color.BLACK;
                    infoMessageNumber = R.string.yellow_message;
                    cardView.setClickable(true);
                } else if (aqi > 100 && aqi <= 150){
                    bkgColorNumber = 0xFFFF8000;
                    txtColorNumber = Color.BLACK;
                    infoMessageNumber = R.string.orange_message;
                    cardView.setClickable(true);
                } else if (aqi > 150 && aqi <=200){
                    bkgColorNumber = Color.RED;
                    txtColorNumber = 0xFFFFFFFF;
                    infoMessageNumber = R.string.red_message;
                    cardView.setClickable(true);
                }else if (aqi > 200 && aqi <= 300) {
                    bkgColorNumber = 0xFFC000FF;
                    txtColorNumber = 0xFFFFFFFF;
                    infoMessageNumber = R.string.violet_message;
                    cardView.setClickable(true);
                }
                else if (aqi >300){
                    bkgColorNumber = 0xFFCC0066;
                    txtColorNumber = 0xFFFFFFFF;
                    infoMessageNumber = R.string.bordeaux_message;
                    cardView.setClickable(true);
                }
            } else {

                bkgColorNumber = 0xFFE0E0E0;
                txtColorNumber = Color.BLACK;
                cardView.setClickable(false);

            }

            cardView.setBackgroundColor(bkgColorNumber);
            setColorOfText(txtColorNumber);



        }

        public void setColorOfText(int myColor){
            stationNameView.setTextColor(myColor);
            stationTimeView.setTextColor(myColor);
            stationAqiView.setTextColor(myColor);
        }


        @Override
        public void onClick(View v) {

            Station currentStation = stationData.get(getAdapterPosition());

            String stationLocation = "geo:" + currentStation.getLon() + ";" + currentStation.getLat();

            Intent intent = new Intent(context, DetailActivity.class);

            intent.putExtra("location", stationLocation);

            intent.putExtra("backgroundColor", bkgColorNumber);
            intent.putExtra("textColor", txtColorNumber);
            intent.putExtra("infoMessage", infoMessageNumber);

            context.startActivity(intent);

        }


    }
}
