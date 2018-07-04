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
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;

public class SpecialistActivity extends AppCompatActivity {

    List<DataAdapter> ListOfdataAdapter;

    RecyclerView recyclerView;

    String id_category;
    String id_company;

    String HTTP_JSON_URL = "http://barbersakh.ru/specialist.php";

    String FIO_Specialist_JSON = "fio_specialist"; //ФИО специалиста.

    String Photo_Specialist_JSON = "photo_specialist"; //Фотография специалиста

    String Id_Specialist_JSON = "id_specialist";  //ID специалиста

    JsonArrayRequest RequestOfJSonArray;

    RequestQueue requestQueue;

    View view;

    int RecyclerViewItemPosition;

    RecyclerView.LayoutManager layoutManagerOfrecyclerView;

    RecyclerView.Adapter recyclerViewadapterSpecialist;

    ArrayList<String> FIOSpecialistArrListForClick;
    ArrayList<String> IdSpecialistCategoryArrListForClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        String id_cat = intent.getStringExtra("id_category");
        String id_com = intent.getStringExtra("id_company");
        id_category = id_cat;
        id_company = id_com;

        FIOSpecialistArrListForClick = new ArrayList<>();
        IdSpecialistCategoryArrListForClick = new ArrayList<>();

        ListOfdataAdapter = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview1);

        recyclerView.setHasFixedSize(true);

        layoutManagerOfrecyclerView = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManagerOfrecyclerView);

        JSON_HTTP_CALL();

        // Реализация прослушивателя щелчков в RecyclerView.
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

            GestureDetector gestureDetector = new GestureDetector(SpecialistActivity.this, new GestureDetector.SimpleOnGestureListener() {

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

                    // Отображение id выбранного элемента из списка
                    //Toast.makeText(MainActivity.this, IdArrayListForClick.get(RecyclerViewItemPosition), Toast.LENGTH_SHORT).show();

                    //Передача id на CompanyActivity и переход на него
                    //String data = IdArrayListForClick.get(RecyclerViewItemPosition);

                    Intent intent = new Intent(SpecialistActivity.this, RecordActivity.class);
                    //intent.putExtra("id_category", IdSpecialistCategoryArrListForClick.get(RecyclerViewItemPosition));
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

        RequestOfJSonArray = new JsonArrayRequest(HTTP_JSON_URL + "?" + "id_cat=" + id_category + "&" + "id_com=" + id_company,
                //RequestOfJSonArray = new JsonArrayRequest(HTTP_JSON_URL + "?" + "id_cat=2&id_com=2",

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

        requestQueue = Volley.newRequestQueue(SpecialistActivity.this);

        requestQueue.add(RequestOfJSonArray);
    }

    public void ParseJSonResponse(JSONArray array) {

        for (int i = 0; i < array.length(); i++) {

            DataAdapter GetDataAdapter2 = new DataAdapter();

            JSONObject json = null;
            try {

                json = array.getJSONObject(i);

                GetDataAdapter2.setFIOSpecialist(json.getString(FIO_Specialist_JSON));

                GetDataAdapter2.setIdSpecialist(json.getString(Id_Specialist_JSON));

                // Добавление имени заголовка изображения в массив для отображения в событии click RecyclerView.
                FIOSpecialistArrListForClick.add(json.getString(FIO_Specialist_JSON));

                //Добавление id в массив для отображения в событии click
                IdSpecialistCategoryArrListForClick.add(json.getString(Id_Specialist_JSON));

                GetDataAdapter2.setPhotoSpecialist(json.getString(Photo_Specialist_JSON));


            } catch (JSONException e) {

                e.printStackTrace();
            }
            ListOfdataAdapter.add(GetDataAdapter2);
        }

        recyclerViewadapterSpecialist = new RecyclerViewAdapterSpecialist(ListOfdataAdapter, this);

        recyclerView.setAdapter(recyclerViewadapterSpecialist);
    }
}