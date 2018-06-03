package ambystico.barung;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
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

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TambahBarang extends AppCompatActivity {

    private static final String TAG = TambahBarang.class.getSimpleName();
    private static int GALLERY_REQ = 1;
    private static final int CAMERA_REQ = 1888;

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


    Uri uri;
    Bitmap bitmap;

    StringRequest request;
    AlertDialog.Builder alertDialog;
    private String URL = "http://192.168.8.104/custom/Dummy/Barang/Process/insert_barang.php";

    RequestQueue requestQueue;
    String namaBarang, jumlahBarang, hargaBarang;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_barang);

        ButterKnife.bind(this);

        requestQueue = Volley.newRequestQueue(this);
        alertDialog = new AlertDialog.Builder(this);

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

                   ByteArrayOutputStream baos = new ByteArrayOutputStream();
                   bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos);
                   byte[] imageBytes = baos.toByteArray();
                   String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                   params.put("nama_barang", namaBarang);
                   params.put("harga_barang", hargaBarang);
                   params.put("jumlah_barang", jumlahBarang);
                   params.put("image_data", encodedImage);

                   return params;
               }
           };
            requestQueue.add(request);
        });

        imgBarang.setOnClickListener(view -> {
            Toast.makeText(this, "click", Toast.LENGTH_SHORT).show();
            alertDialog.setTitle("Gambar");
            alertDialog.setMessage("Pilih gambar dari :");
            alertDialog.setPositiveButton("Gallery", (dialogInterface, i) -> {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY_REQ);
            });

            alertDialog.setNegativeButton("Camera", (dialogInterface, i) -> {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA_REQ);
            });
            alertDialog.show();
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQ && resultCode == RESULT_OK && data != null && data.getData() != null){
            Uri uri = data.getData();
            try{
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                imgBarang.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if (requestCode == CAMERA_REQ && resultCode == RESULT_OK && data != null && data.getData() != null){
            Uri uri = data.getData();
            try{
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                imgBarang.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void getValue() {
        namaBarang = edtNamaBarang.getText().toString();
        jumlahBarang = edtJumlahBarang.getText().toString();
        hargaBarang = edtHargaBarang.getText().toString();
    }

}
