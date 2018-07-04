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

public class MainActivity extends AppCompatActivity {

    List<DataAdapter> ListOfdataAdapter;

    RecyclerView recyclerView;

    String HTTP_JSON_URL = "http://barbersakh.ru/services.php";

    String Name_Category_JSON = "name_category";

    String Img_Category_JSON = "img_category";

    String Id_Category_JSON ="id_category";  //--------------------

    JsonArrayRequest RequestOfJSonArray ;

    RequestQueue requestQueue ;

    View view ;

    int RecyclerViewItemPosition ;

    RecyclerView.LayoutManager layoutManagerOfrecyclerView;

    RecyclerView.Adapter recyclerViewadapter;

    ArrayList<String> NameCategoryArrListForClick;

    ArrayList<String> IdCategoryArrListForClick;  //--------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NameCategoryArrListForClick = new ArrayList<>();

        IdCategoryArrListForClick = new ArrayList<>();  //----------------------

        ListOfdataAdapter = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview1);

        recyclerView.setHasFixedSize(true);

        layoutManagerOfrecyclerView = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManagerOfrecyclerView);

        JSON_HTTP_CALL();

        // Реализация прослушивателя щелчков в RecyclerView.
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

            GestureDetector gestureDetector = new GestureDetector(MainActivity.this, new GestureDetector.SimpleOnGestureListener() {

                @Override public boolean onSingleTapUp(MotionEvent motionEvent) {

                    return true;
                }

            });
            @Override
            public boolean onInterceptTouchEvent(RecyclerView Recyclerview, MotionEvent motionEvent) {

                view = Recyclerview.findChildViewUnder(motionEvent.getX(), motionEvent.getY());

                if(view != null && gestureDetector.onTouchEvent(motionEvent)) {

                    // Получение значения свойства RecyclerView.
                    RecyclerViewItemPosition = Recyclerview.getChildAdapterPosition(view);

                    // Отображение id выбранного элемента из списка
                    //Toast.makeText(MainActivity.this, IdArrayListForClick.get(RecyclerViewItemPosition), Toast.LENGTH_SHORT).show();

                    //Передача id на CompanyActivity и переход на него
                    //String data = IdArrayListForClick.get(RecyclerViewItemPosition);

                    Intent intent = new Intent(MainActivity.this, CompanyActivity.class);
                    intent.putExtra("id_category", IdCategoryArrListForClick.get(RecyclerViewItemPosition));
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

    public void JSON_HTTP_CALL(){

        RequestOfJSonArray = new JsonArrayRequest(HTTP_JSON_URL,

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

        requestQueue = Volley.newRequestQueue(MainActivity.this);

        requestQueue.add(RequestOfJSonArray);
    }

    public void ParseJSonResponse(JSONArray array){

        for(int i = 0; i<array.length(); i++) {

            DataAdapter GetDataAdapter2 = new DataAdapter();

            JSONObject json = null;
            try {

                json = array.getJSONObject(i);

                GetDataAdapter2.setImageTitle(json.getString(Name_Category_JSON));

                GetDataAdapter2.setId(json.getString(Id_Category_JSON));

                // Добавление имени заголовка изображения в массив для отображения в событии click RecyclerView.
                NameCategoryArrListForClick.add(json.getString(Name_Category_JSON));

                //Добавление id в массив для отображения в событии click
                IdCategoryArrListForClick.add(json.getString(Id_Category_JSON));

                GetDataAdapter2.setImageUrl(json.getString(Img_Category_JSON));


            } catch (JSONException e) {

                e.printStackTrace();
            }
            ListOfdataAdapter.add(GetDataAdapter2);
        }

        recyclerViewadapter = new RecyclerViewAdapter(ListOfdataAdapter, this);

        recyclerView.setAdapter(recyclerViewadapter);
    }
}