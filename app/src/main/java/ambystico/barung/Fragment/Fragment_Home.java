package ambystico.barung.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
import java.util.HashMap;

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
    RecyclerViewAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<HashMap<String, String>> arrayBarang;
    DataBarang dataBarang;


    RequestQueue requestQueue;

    String URL = "http://10.164.116.214/custom/Dummy/Barang/Process/tampil_barang.php";
    String TAG = Fragment_Home.class.getSimpleName();

    public Fragment_Home() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);

        arrayBarang = new ArrayList<>();
        rvBarang.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        rvBarang.setLayoutManager(layoutManager);

        rvBarang.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {
                Toast.makeText(getContext(), "clicked", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

        requestQueue = Volley.newRequestQueue(getContext());
        dataBarang = new DataBarang();

        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: " + response);
                try{
                    DataBarang dataBarang = new DataBarang();
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("dataBarang");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject json = jsonArray.getJSONObject(i);
                        HashMap<String, String> data = new HashMap<>();
                        data.put(dataBarang.nama_barang, json.getString("nama_barang"));
                        data.put(dataBarang.harga_barang, json.getString("harga_barang"));
                        data.put(dataBarang.jumlah_barang, json.getString("jumlah_barang"));


                        arrayBarang.add(data);

                        Log.i(TAG, "onResponse: Data Array : " + data);
                        Log.i(TAG, "onResponse: Array Barang ; " + arrayBarang);

                        RecyclerViewAdapter adapter = new RecyclerViewAdapter(arrayBarang);
                        rvBarang.setAdapter(adapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(request);

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), TambahBarang.class);
                startActivity(i);
            }
        });

        return view;
    }



}
