package com.example.user.app1;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ZazitkyListAdapter extends RecyclerView.Adapter<ZazitkyListAdapter.ZazitkyViewHolder> {

    class ZazitkyViewHolder extends RecyclerView.ViewHolder {
        private final TextView nazov;
        private final TextView miesto;
        private final TextView popis;
        private final ImageView url;

        private ZazitkyViewHolder(View itemView) {
            super(itemView);
            nazov = itemView.findViewById(R.id.Nazov);
            miesto = itemView.findViewById(R.id.Miesto);
            popis = itemView.findViewById(R.id.Popis3);
            url = itemView.findViewById(R.id.Url);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(mZazitky.get(position));
                    }
                }
            });
        }
    }

    public Zazitky getZazitkyAt(int position) {
        return mZazitky.get(position);
    }

    public ZazitkyListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    private final LayoutInflater mInflater;
    private List<Zazitky> mZazitky;
    private OnItemClickListener listener;

    @Override
    public ZazitkyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = mInflater.inflate(R.layout.model, viewGroup, false);
        return new ZazitkyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ZazitkyViewHolder zazitkyViewHolder, int i) {
        if (mZazitky != null) {
            Zazitky current = mZazitky.get(i);
            zazitkyViewHolder.nazov.setText(current.getNazov());
            zazitkyViewHolder.miesto.setText(current.getMiesto());
            zazitkyViewHolder.popis.setText(current.getPopis());
            //zazitkyViewHolder.url.setText(current.getUrl());

            Picasso.get().load(current.getUrl()).into(zazitkyViewHolder.url);
        }
    }

    void setmZazitky(List<Zazitky> zazitky) {
        mZazitky = zazitky;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mZazitky != null) {
            return mZazitky.size();
        } else
            return 0;
    }

    public interface OnItemClickListener {
        void onItemClick(Zazitky zazitky);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}
