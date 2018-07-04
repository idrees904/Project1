package com.androidjson.recyclerviewimageviewtextview_androidjsoncom;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONArray;

import java.util.ArrayList;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import java.util.List;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;

public class CompanyActivity extends AppCompatActivity {

    List<DataAdapter> ListOfdataAdapter;

    RecyclerView recyclerView;

    String id_c;

    String HTTP_JSON_URL = "http://barbersakh.ru/company.php";
    String Name_Company_JSON = "name_company";
    String Image_company_JSON = "image_company";
    String Addres_company_JSON = "address_company";
    String id_category_JSON = "id_category";
    String id_company_JSON = "id_company";

    JsonArrayRequest RequestOfJSonArray;

    RequestQueue requestQueue;

    View view;

    int RecyclerViewItemPosition;

    RecyclerView.LayoutManager layoutManagerOfrecyclerView;

    RecyclerView.Adapter recyclerViewadapter;

    ArrayList<String> ImageCompanyArrListForClick;

    ArrayList<String> IdCategoryArrListForClick;
    ArrayList<String> IdCompanyArrListForClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        String id_category = intent.getStringExtra("id_category");
        id_c = id_category;


        ImageCompanyArrListForClick = new ArrayList<>();

        IdCategoryArrListForClick = new ArrayList<>();
        IdCompanyArrListForClick = new ArrayList<>();

        ListOfdataAdapter = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview1);

        recyclerView.setHasFixedSize(true);

        layoutManagerOfrecyclerView = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManagerOfrecyclerView);

        JSON_HTTP_CALL();

        // Реализация прослушивателя щелчков в RecyclerView.
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

            GestureDetector gestureDetector = new GestureDetector(CompanyActivity.this, new GestureDetector.SimpleOnGestureListener() {

                @Override
                public boolean onSingleTapUp(MotionEvent motionEvent) {

                    return true;
                }

            });

            @Override
            public boolean onInterceptTouchEvent(RecyclerView Recyclerview, MotionEvent motionEvent) {

                view = Recyclerview.findChildViewUnder(motionEvent.getX(), motionEvent.getY());

                if (view != null && gestureDetector.onTouchEvent(motionEvent)) {

                    // Получение значения свойства RecyclerView.
                    RecyclerViewItemPosition = Recyclerview.getChildAdapterPosition(view);

                    // Отображение RecyclerView Щелкнув значение элемента с помощью Toast.
                    //Toast.makeText(CompanyActivity.this, ImageTitleNameArrayListForClick.get(RecyclerViewItemPosition), Toast.LENGTH_SHORT).show();
/*
                    Intent intent = new Intent(CompanyActivity.this, CompanyInfoActivity.class);
                    intent.putExtra("id_comp", IdArrayListForClick.get(RecyclerViewItemPosition));
                    startActivity(intent);
*/
                    Intent intent = new Intent(CompanyActivity.this, SpecialistActivity.class);
                    intent.putExtra("id_category", IdCategoryArrListForClick.get(RecyclerViewItemPosition));
                    intent.putExtra("id_company", IdCompanyArrListForClick.get(RecyclerViewItemPosition));
                    startActivity(intent);
                }

                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView Recyclerview, MotionEvent motionEvent) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });


    }

    public void JSON_HTTP_CALL() {

//id_c - id категории

        RequestOfJSonArray = new JsonArrayRequest(HTTP_JSON_URL + "?" + "id=" + id_c,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        ParseJSonResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        requestQueue = Volley.newRequestQueue(CompanyActivity.this);

        requestQueue.add(RequestOfJSonArray);
    }

    public void ParseJSonResponse(JSONArray array) {

        for (int i = 0; i < array.length(); i++) {

            DataAdapter GetDataAdapter2 = new DataAdapter();

            JSONObject json = null;
            try {

                json = array.getJSONObject(i);

                GetDataAdapter2.setImageTitle(json.getString(Name_Company_JSON));
                GetDataAdapter2.setAddressCompany(json.getString(Addres_company_JSON));
                GetDataAdapter2.setId(json.getString(id_company_JSON));

                // Добавление имени заголовка изображения в массив для отображения в событии click RecyclerView.
                ImageCompanyArrListForClick.add(json.getString(Name_Company_JSON));
                //ImageTitleNameArrayListForClick.add(json.getString(Addres_company_JSON));

                //Добавление id в массив для отображения в событии click
                IdCategoryArrListForClick.add(json.getString(id_category_JSON));
                IdCompanyArrListForClick.add(json.getString(id_company_JSON));

                GetDataAdapter2.setImageUrl(json.getString(Image_company_JSON));

            } catch (JSONException e) {

                e.printStackTrace();
            }
            ListOfdataAdapter.add(GetDataAdapter2);
        }

        recyclerViewadapter = new RecyclerViewAdapterCompany(ListOfdataAdapter, this);

        recyclerView.setAdapter(recyclerViewadapter);
    }
}