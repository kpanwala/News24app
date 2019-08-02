package com.example.abc;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static String PRODUCT_URL1[]={"https://newsapi.org/v2/top-headlines?country=in&apiKey=083341c007b3446f87972661995a65dd","https://newsapi.org/v2/top-headlines?country=in&category=entertainment&apiKey=083341c007b3446f87972661995a65dd","https://newsapi.org/v2/top-headlines?sources=bbc-news&apiKey=083341c007b3446f87972661995a65dd","https://newsapi.org/v2/top-headlines?country=in&category=technology&apiKey=083341c007b3446f87972661995a65dd","https://newsapi.org/v2/top-headlines?country=in&category=business&apiKey=083341c007b3446f87972661995a65dd","https://newsapi.org/v2/top-headlines?country=in&category=sports&apiKey=083341c007b3446f87972661995a65dd","https://newsapi.org/v2/top-headlines?country=in&category=business&apiKey=083341c007b3446f87972661995a65dd"};

    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    int count=0;
    List<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.simpleSwipeRefreshLayout);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        productList = new ArrayList<>();
        loadProducts();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // cancel the Visual indication of a refresh
                swipeRefreshLayout.setRefreshing(false);
                loadProducts();
            }
        });


    }
    int j;
    public void loadProducts(){
        int size;
        size=PRODUCT_URL1.length;
        count=(int)Math.floor(Math.random()*size);
        String PRODUCT_URL=PRODUCT_URL1[count];

        Log.d("Count value"," "+count);

        size = productList.size();
        if(size!=0){
            productList.clear();
        }

        StringRequest stringRequest=new StringRequest(Request.Method.GET, PRODUCT_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            j=0;
                            JSONObject jsonResponse = new JSONObject(response);
                            JSONArray jsonArray = jsonResponse.optJSONArray("articles");

                            while(j<jsonArray.length()){

                                JSONObject productObject = jsonArray.getJSONObject(j);
                                String title=productObject.getString("title");
                                String description=productObject.getString("description");
                                String image=productObject.getString("urlToImage");
                                String url=productObject.getString("url");

                                if(image!=null && description!=null && title!=null){
                                    Product product=new Product(title,description,image,url);
                                    productList.add(product);
                                }

                                j++;
                            }


                            ProductAdapter adapter = new ProductAdapter(MainActivity.this, productList);
                            recyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }
                ,new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        Volley.newRequestQueue(this).add(stringRequest);
    }

}
