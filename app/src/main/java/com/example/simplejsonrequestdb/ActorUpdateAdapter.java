package com.example.simplejsonrequestdb;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wingnity.jsonparsingtutorial.R;

import java.io.InputStream;
import java.util.ArrayList;


public class ActorUpdateAdapter extends RecyclerView.Adapter<ActorUpdateAdapter.MyViewHolder> {
    private ArrayList<Actors> actorList;
    private LayoutInflater layoutInflater;

    public ActorUpdateAdapter(Context context, ArrayList<Actors> mActorList) {
        layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        actorList = mActorList;
    }

    @Override
    public ActorUpdateAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ActorUpdateAdapter.MyViewHolder holder, int position) {
        holder.imageview.setImageResource(R.drawable.ic_launcher_background);
        new ActorUpdateAdapter.DownloadImageTask(holder.imageview).execute(actorList.get(position).getImage());
        holder.tvName.setText(actorList.get(position).getName());
        holder.tvDescription.setText(actorList.get(position).getDescription());
        holder.tvDOB.setText("B'day: " + actorList.get(position).getDob());
        holder.tvCountry.setText(actorList.get(position).getCountry());
        holder.tvHeight.setText("Height: " + actorList.get(position).getHeight());
        holder.tvSpouse.setText("Spouse: " + actorList.get(position).getSpouse());
        holder.tvChildren.setText("Children: " + actorList.get(position).getChildren());

    }

    @Override
    public int getItemCount() {
        return actorList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageview;
        public TextView tvName;
        public TextView tvDescription;
        public TextView tvDOB;
        public TextView tvCountry;
        public TextView tvHeight;
        public TextView tvSpouse;
        public TextView tvChildren;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageview = (ImageView) itemView.findViewById(R.id.ivImage);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvDescription = (TextView) itemView.findViewById(R.id.tvDescriptionn);
            tvDOB = (TextView) itemView.findViewById(R.id.tvDateOfBirth);
            tvCountry = (TextView) itemView.findViewById(R.id.tvCountry);
            tvHeight = (TextView) itemView.findViewById(R.id.tvHeight);
            tvSpouse = (TextView) itemView.findViewById(R.id.tvSpouse);
            tvChildren = (TextView) itemView.findViewById(R.id.tvChildren);

        }
    }
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
