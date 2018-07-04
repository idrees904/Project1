package com.androidjson.recyclerviewimageviewtextview_androidjsoncom;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

/**
 * Created by Juned on 2/8/2017.
 */

public class RecyclerViewAdapterSpecialist extends RecyclerView.Adapter<RecyclerViewAdapterSpecialist.ViewHolder> {

    Context context;

    List<DataAdapter> dataAdapters;

    ImageLoader imageLoader;

    public RecyclerViewAdapterSpecialist(List<DataAdapter> getDataAdapter, Context context){

        super();
        this.dataAdapters = getDataAdapter;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.specialist_cardview, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder Viewholder, int position) {

        DataAdapter dataAdapterOBJ =  dataAdapters.get(position);

        imageLoader = ImageAdapter.getInstance(context).getImageLoader();

        imageLoader.get(dataAdapterOBJ.getPhotoSpecialist(),
                ImageLoader.getImageListener(
                        Viewholder.VollyImageView, // Изображение сервера
                        R.mipmap.ic_launcher, // Перед загрузкой с сервера по умолчанию отображается изображение.
                        android.R.drawable.ic_dialog_alert // Изображение ошибки, если запрошенное изображение не найдено на сервере.
                )
        );

        Viewholder.VollyImageView.setImageUrl(dataAdapterOBJ.getPhotoSpecialist(), imageLoader);

        Viewholder.ImageTitleTextView.setText(dataAdapterOBJ.getFIOSpecialist());

    }

    @Override
    public int getItemCount() {

        return dataAdapters.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public TextView ImageTitleTextView;
        public NetworkImageView VollyImageView ;

        public ViewHolder(View itemView) {

            super(itemView);

            ImageTitleTextView = (TextView) itemView.findViewById(R.id.tvFIOspecialist) ;

            VollyImageView = (NetworkImageView) itemView.findViewById(R.id.VolleyImageView) ;

        }
    }
}