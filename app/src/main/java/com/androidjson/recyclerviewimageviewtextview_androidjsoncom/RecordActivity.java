package com.androidjson.recyclerviewimageviewtextview_androidjsoncom;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;

import android.widget.Button;
import android.content.Context;
import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Intent;


public class RecordActivity extends AppCompatActivity {

    HttpResponse httpResponse;
    Button button;
    TextView textView;
    CalendarView mCalendarView;
    JSONObject jsonObject = null;
    String StringHolder = "";

    // Добавление URL-адреса HTTP-сервера в строковую переменную.
    String HttpURL = "http://barbersakh.ru/date_min_max.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        textView = (TextView) findViewById(R.id.textView);

        mCalendarView = (CalendarView) findViewById(R.id.calendarView);

        // Вызов метода GetDataFromServerIntoTextView для установки данных JSon MySQL в TextView.
        new GetDataFromServerIntoTextView(RecordActivity.this).execute();

    }

    // Объявление метода GetDataFromServerIntoTextView с помощью AsyncTask.
    public class GetDataFromServerIntoTextView extends AsyncTask<Void, Void, Void> {
        // Объявление CONTEXT.
        public Context context;

        public GetDataFromServerIntoTextView(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            HttpClient httpClient = new DefaultHttpClient();

            // Добавление HttpURL к моему объекту HttpPost.
            HttpPost httpPost = new HttpPost(HttpURL);

            try {
                httpResponse = httpClient.execute(httpPost);

                StringHolder = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");

            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                // Передача переменной владельца строки в JSONArray.
                JSONArray jsonArray = new JSONArray(StringHolder);
                jsonObject = jsonArray.getJSONObject(0);

                String minDateString = jsonArray.getJSONObject(0).getString("minDate");
                String maxDateString = jsonArray.getJSONObject(0).getString("maxDate");

                // Парсим дату
                SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

                Date minDate = formatter.parse(minDateString);
                Date maxDate = formatter.parse(maxDateString);

                // Получаем время
                long minDateTime = minDate.getTime();
                long maxDateTime = maxDate.getTime();

                // Как-то инициализируете и работаете с этим
                CalendarView dpd;
                CalendarView dp = mCalendarView;

                // Устанавливаем даты
                dp.setMinDate(minDateTime);
                dp.setMaxDate(maxDateTime);

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Void result) {
            try {

                // Добавление строки JSOn в textview после завершения загрузки.
                textView.setText(jsonObject.getString("minDate"));

            } catch (JSONException e) {
                //TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }

}
