package ambystico.barung;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

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

public class TambahBarang extends AppCompatActivity {

    private static final String TAG = TambahBarang.class.getSimpleName();
    @BindView(R.id.btnDataBarang)
        Button btnDataBarang;
    @BindView(R.id.edtHargaBarang)
        MaterialEditText edtHargaBarang;
    @BindView(R.id.edtJumlahBarang)
        MaterialEditText edtJumlahBarang;
    @BindView(R.id.edtNamaBarang)
        MaterialEditText edtNamaBarang;
    @BindView(R.id.imgBarang)
        ImageView imgBarang;

    StringRequest request;

    private String URL = "http://192.168.8.104/custom/Dummy/Barang/Process/insert_barang.php";

    RequestQueue requestQueue;
    String namaBarang, jumlahBarang, hargaBarang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_barang);

        ButterKnife.bind(this);

        requestQueue = Volley.newRequestQueue(this);

        btnDataBarang.setOnClickListener(view -> {
            getValue();

           request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
               @Override
               public void onResponse(String response) {
                   Toast.makeText(TambahBarang.this, response, Toast.LENGTH_SHORT).show();
               }
           }, new Response.ErrorListener() {
                       @Override
                       public void onErrorResponse(VolleyError error) {
                           Toast.makeText(TambahBarang.this, error.toString(), Toast.LENGTH_SHORT).show();
                       }
                   })
           {
               @Override
               protected Map<String, String> getParams(){
                   Map<String, String> params = new HashMap<>();
                   params.put("nama_barang", namaBarang);
                   params.put("harga_barang", hargaBarang);
                   params.put("jumlah_barang", jumlahBarang);

                   return params;
               }
           };
            RequestQueue requestQueue = Volley.newRequestQueue(TambahBarang.this);
            requestQueue.add(request);
        });

        imgBarang.setOnClickListener(view -> {
            Intent i  = new Intent();
            i.setType("image/*");
            i.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(i, "Select Image"), 1);
        });

    }

    private void getValue() {
        namaBarang = edtNamaBarang.getText().toString();
        jumlahBarang = edtJumlahBarang.getText().toString();
        hargaBarang = edtHargaBarang.getText().toString();
    }

}
