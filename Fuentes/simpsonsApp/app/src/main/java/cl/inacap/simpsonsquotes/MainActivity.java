package cl.inacap.simpsonsquotes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import cl.inacap.simpsonsquotes.adapters.QuotesAdapter;
import cl.inacap.simpsonsquotes.dto.Personaje;

public class MainActivity extends AppCompatActivity {

    private List<Personaje> personajes = new ArrayList<>();
    private ListView citasLv;
    private Spinner spinner;
    private Button button;
    private RequestQueue queue;
    private QuotesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setSupportActionBar(findViewById(R.id.toolbar));
        this.spinner = findViewById(R.id.cantidad_spin);
        this.button = findViewById(R.id.solicitar_btn);
        this.citasLv = findViewById(R.id.quotes_list);
        queue = Volley.newRequestQueue(this);


        Integer[] cantidad = new Integer[10];
        for (int k = 0; k < cantidad.length; k++) {
            cantidad[k] = k + 1;
        }
        ArrayAdapter<Integer> adapter_list = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, cantidad);
        adapter_list.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter_list);
        this.adapter = new QuotesAdapter(this, R.layout.citas_list, this.personajes);
        this.citasLv.setAdapter(this.adapter);
        this.button.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                int cantidad = (int) spinner.getSelectedItem();
                String URL = "https://thesimpsonsquoteapi.glitch.me/quotes?count=" + cantidad;
                StringRequest jsonReq = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            personajes.clear();
                            Personaje[] arreglo = new Gson().fromJson(response,Personaje[].class);
                            personajes.addAll(Arrays.asList(arreglo));
                        } catch (Exception ex) {
                            personajes.clear();
                        } finally {
                            adapter.notifyDataSetChanged();
                        }

                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                personajes.clear();
                                Log.e("Personajes", "Error de peticion");
                                adapter.notifyDataSetChanged();
                            }
                        });
                queue.add(jsonReq);

            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();


    }
}
