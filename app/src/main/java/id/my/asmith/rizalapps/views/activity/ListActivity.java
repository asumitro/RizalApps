package id.my.asmith.rizalapps.views.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.my.asmith.rizalapps.R;
import id.my.asmith.rizalapps.model.PostProduct;
import id.my.asmith.rizalapps.model.Value;
import id.my.asmith.rizalapps.network.ApiClient;
import id.my.asmith.rizalapps.network.ApiService;
import id.my.asmith.rizalapps.views.adapter.ListProductAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static id.my.asmith.rizalapps.network.Const.BASE_URL;

public class ListActivity extends AppCompatActivity {
    ApiClient mApiClient = new ApiClient();
    private List<PostProduct> results = new ArrayList<>();
    private ListProductAdapter viewAdapter;
    private ProgressDialog mProgressdlg;
    @BindView(R.id.recycler_product)
    RecyclerView recyclerView;
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        getSupportActionBar().setTitle("My Store");
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        mProgressdlg = new ProgressDialog(this);
        ButterKnife.bind(this);
        viewAdapter = new ListProductAdapter(this, results);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(viewAdapter);
        loadData();

    }

    private void loadData() {
        //Task

        mProgressdlg.setMessage("Load data...");
        mProgressdlg.setCancelable(false);
        mProgressdlg.show();

        Intent intent = getIntent();
        String kategori = intent.getStringExtra("kategori");
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .client(provideOkHttpClient())
//                .build();
//        ApiService api = retrofit.create(ApiService.class);
//        Call<Value> call = api.getData(kategori, null, null);
//        call.enqueue(new Callback<Value>() {
//            @Override
//            public void onResponse(Call<Value> call, Response<Value> response) {
//                String value = response.body().getValue();
//                mProgressdlg.dismiss();
//                if (value.equals("1")) {
//                    results = response.body().getResult();
//                    viewAdapter = new ListProductAdapter(ListActivity.this, results);
//                    recyclerView.setAdapter(viewAdapter);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Value> call, Throwable t) {
//                mProgressdlg.dismiss();
//            }
//        });

        mApiClient
                .ApiServices()
                .getData(kategori, null, null)
                .enqueue(new Callback<Value>() {
                    @Override
                    public void onResponse(Call<Value> call, Response<Value> response) {
                        String value = response.body().getValue();
                        mProgressdlg.dismiss();
                        if (value.equals("1")) {
                            results = response.body().getResult();
                            viewAdapter = new ListProductAdapter(ListActivity.this, results);
                            recyclerView.setAdapter(viewAdapter);
                        }
                    }

                    @Override
                    public void onFailure(Call<Value> call, Throwable t) {
                        mProgressdlg.dismiss();
                    }
                });
    }

}
