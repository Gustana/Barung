package ambystico.barung;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerHolder> {

    private List<DataBarang> dataBarangList;
    Context context;
    Intent i;

    @Override
    public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_list_item, parent, false);
        return new RecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerHolder holder, int position) {
        DataBarang dataBarang = dataBarangList.get(position);
        holder.txtHarga.setText(dataBarang.harga_barang);
        holder.txtJumlah.setText(dataBarang.jumlah_barang);
        holder.txtNama.setText(dataBarang.nama_barang);

        holder.itemView.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putString("nama_barang", dataBarang.nama_barang);
            bundle.putString("harga_barang", dataBarang.harga_barang);
            bundle.putString("jumlah_barang", dataBarang.jumlah_barang);
            i.putExtras(bundle);
            context.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return dataBarangList.size();
    }

    public class RecyclerHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.txtJumlah)
            TextView txtJumlah;
        @BindView(R.id.txtHarga)
            TextView txtHarga;
        @BindView(R.id.txtNama)
            TextView txtNama;

        public RecyclerHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    public RecyclerViewAdapter(Context context, List<DataBarang> dataBarangList){
        this.dataBarangList = dataBarangList;
        this.context = context;
        i = new Intent(context, Dialogue_dataBarang.class);
    }


}
