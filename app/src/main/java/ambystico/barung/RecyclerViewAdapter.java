package ambystico.barung;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private ArrayList<HashMap<String, String>> rvData;
    private final String TAG = RecyclerViewAdapter.class.getSimpleName();

    public RecyclerViewAdapter(ArrayList<HashMap<String, String>> inputData){
        rvData = inputData;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.txtNama)
        TextView txtNama;
        @BindView(R.id.txtHarga)
        TextView txtHarga;
        @BindView(R.id.txtJumlah)
        TextView txtJumlah;

        public ViewHolder(View v){
            super(v);
            ButterKnife.bind(this, v);
        }

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_list_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DataBarang dataBarang = new DataBarang();
        holder.txtHarga.setText(rvData.get(position).get(dataBarang.harga_barang));
        holder.txtJumlah.setText(rvData.get(position).get(dataBarang.jumlah_barang));
        holder.txtNama.setText(rvData.get(position).get(dataBarang.nama_barang));
    }


    @Override
    public int getItemCount() {
        return rvData.size();
    }
}