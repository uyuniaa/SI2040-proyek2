package com.thenotes.Notes.paketku;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thenotes.Notes.R;

import java.util.List;

public class AdapterDataku extends RecyclerView.Adapter<AdapterDataku.ViewHolder> {
    Context context;
    List<Dataku> list;
    OnCallBack onCallBack;

    public void setOnCallBack(OnCallBack onCallBack) {
        this.onCallBack = onCallBack;
    }

    public AdapterDataku(Context context, List<Dataku> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(context).inflate(R.layout.list_data,parent,false);
       return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textviewData.setText(list.get(position).getIsi());

        holder.tblHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCallBack.onTblHapus(list.get(position));
            }
        });

        holder.tblEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCallBack.onTblEdit(list.get(position));
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textviewData;
        ImageButton tblHapus,tblEdit;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textviewData = itemView.findViewById(R.id.text_view_data);
            tblHapus = itemView.findViewById(R.id.tombl_hapus);
            tblEdit = itemView.findViewById(R.id.tombl_edit);
        }
    }
    public interface OnCallBack{
        void onTblHapus(Dataku dataku);
        void onTblEdit (Dataku dataku);
    }

    public AdapterDataku() {
    }
}
