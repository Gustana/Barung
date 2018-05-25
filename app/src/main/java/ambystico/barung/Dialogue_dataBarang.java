package ambystico.barung;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Dialogue_dataBarang extends Activity {

    @BindView(R.id.btnKirim)
        Button btnKirim;
    @BindView(R.id.edtNamaBarang)
        MaterialEditText edtNamaBarang;
    @BindView(R.id.edtJumlahBarang)
        MaterialEditText edtJumlahBarang;
    @BindView(R.id.edtHargaBarang)
        MaterialEditText edtHargaBarang;
    @BindView(R.id.imgEditHargaBarang)
        ImageView imgEditHargaBarang;
    @BindView(R.id.imgEditJumlahBarang)
        ImageView imgEditJumlahBarang;
    @BindView(R.id.imgEditNamaBarang)
        ImageView imgEditNamaBarang;

    final String URL = "http://192.168.8.104/custom/Dummy/Barang/Process/update_barang.php";
    String nama_barang, harga_barang, jumlah_barang;
    StringRequest stringRequest;
    RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialogue_data_barang);

        ButterKnife.bind(this);
        getDataBarang();

        edtHargaBarang.setEnabled(false);
        edtJumlahBarang.setEnabled(false);
        edtNamaBarang.setEnabled(false);
        btnKirim.setEnabled(false);

        editImageClick(imgEditHargaBarang, edtHargaBarang);
        editImageClick(imgEditNamaBarang, edtNamaBarang);
        editImageClick(imgEditJumlahBarang, edtJumlahBarang);

        edtHargaBarang.setText(harga_barang);
        edtJumlahBarang.setText(jumlah_barang);
        edtNamaBarang.setText(nama_barang);

        watchText(edtJumlahBarang);
        watchText(edtNamaBarang);
        watchText(edtHargaBarang);

        requestQueue = Volley.newRequestQueue(this);

        btnKirim.setOnClickListener(view -> {

            String nama_barang = edtNamaBarang.getText().toString();
            String harga_barang = edtHargaBarang.getText().toString();
            String jumlah_barang = edtJumlahBarang.getText().toString();

            stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(Dialogue_dataBarang.this, response, Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Dialogue_dataBarang.this, error.toString(), Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String, String> getParams(){
                    Map<String, String> params = new HashMap<>();

                    params.put("nama_barang", nama_barang);
                    params.put("harga_barang", harga_barang);
                    params.put("jumlah_barang", jumlah_barang);

                    return params;
                }
            };

            requestQueue.add(stringRequest);

        });

    }

    private void editImageClick(ImageView imageView, MaterialEditText materialEditText){
        imageView.setOnClickListener(view -> {
            materialEditText.setEnabled(true);
        });
    }

    private void getDataBarang(){
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            nama_barang = bundle.getString("nama_barang");
            harga_barang = bundle.getString("harga_barang");
            jumlah_barang = bundle.getString("jumlah_barang");
        }
    }

    private void watchText(MaterialEditText materialEditText){
        materialEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                btnKirim.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                btnKirim.setEnabled(true);
            }
        });
    }

}
