package ambystico.barung.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ambystico.barung.DataBarang;
import ambystico.barung.R;
import ambystico.barung.RecyclerViewAdapter;
import ambystico.barung.TambahBarang;
import butterknife.BindView;
import butterknife.ButterKnife;

public class Fragment_Home extends Fragment {

    @BindView(R.id.fabAdd)
    FloatingActionButton fabAdd;
    @BindView(R.id.rvBarang)
    RecyclerView rvBarang;
    RecyclerView.LayoutManager layoutManager;
    List<DataBarang> list = new ArrayList<>();
    RecyclerViewAdapter adapter;

    RequestQueue requestQueue;
    StringRequest request;
    Handler handler;
    Runnable runnable;
    int UPDATE_TIME = 1000;

    String URL = "http://192.168.8.104/custom/Dummy/Barang/Process/tampil_barang.php";
    String TAG = Fragment_Home.class.getSimpleName();
    Context context;


    public Fragment_Home() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);

        rvBarang.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        rvBarang.setLayoutManager(layoutManager);
        rvBarang.setItemAnimator(new DefaultItemAnimator());
        requestQueue = Volley.newRequestQueue(getContext());

        request = new StringRequest(Request.Method.POST, URL, response -> {
            Log.d(TAG, "onResponse: " + response);
            try{
                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray = jsonObject.getJSONArray("dataBarang");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject json = jsonArray.getJSONObject(i);
                    DataBarang dataBarang = new DataBarang();
                    dataBarang.harga_barang = json.getString("harga_barang");
                    dataBarang.jumlah_barang = json.getString("jumlah_barang");
                    dataBarang.nama_barang = json.getString("nama_barang");
                    dataBarang.img_barang = json.getString("image_data");

                    list.add(dataBarang);
                    adapter = new RecyclerViewAdapter(context, list);
                    Log.i(TAG, "onResponse: Data Array : " + dataBarang);
                    Log.i(TAG, "onResponse: Array Barang ; " + list);
                }

                rvBarang.setAdapter(adapter);
                adapter.notifyDataSetChanged();


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(request);

        fabAdd.setOnClickListener(view1 -> {
            Intent i = new Intent(context, TambahBarang.class);
            startActivity(i);
        });

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                requestQueue.add(request);
                list.clear();
                handler.postDelayed(runnable, UPDATE_TIME);
            }
        };
        handler.postDelayed(runnable, UPDATE_TIME);

        return view;
    }
}
