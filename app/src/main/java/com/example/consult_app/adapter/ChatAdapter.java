package com.example.consult_app.adapter;

import android.content.Context;
import android.print.PrintAttributes;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.consult_app.R;
import com.example.consult_app.model.ChatModel;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.HolderData> {
    private Context context;
    private List<ChatModel> model ;

    public ChatAdapter(Context context, List<ChatModel> model) {
        this.context = context;
        this.model = model;
    }

    @NonNull
    @Override
    public ChatAdapter.HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_chat, parent, false);
        HolderData holderData = new HolderData(view);
        return holderData;
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.HolderData holder, int position) {
        ChatModel chatModel = model.get(position);
        holder.pesan.setText(chatModel.getPesan());
        holder.waktu.setText(chatModel.getWaktu());
//        if (chatModel.getTipe().equals("file")){
//
//        }
        if (chatModel.getPengirim().equals("faskes")){
            holder.cv.setBackgroundResource(R.drawable.bg_chat_dokter);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.RIGHT;
            params.setMargins(0, 25, 75, 0);
            holder.cv.setLayoutParams(params);
        }else {
            holder.cv.setBackgroundResource(R.drawable.bg_chat_pasien);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.RIGHT;
            params.setMargins(75, 15, 0, 0);
            holder.cv.setLayoutParams(params);
        }
    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    public class HolderData extends RecyclerView.ViewHolder {

        TextView pesan, waktu;
        RelativeLayout rl;
        CardView cv;
        public HolderData(@NonNull View itemView) {
            super(itemView);
            pesan = itemView.findViewById(R.id.pesan);
            waktu = itemView.findViewById(R.id.waktu);
            rl = itemView.findViewById(R.id.rl);
            cv = itemView.findViewById(R.id.card);
        }
    }
}
