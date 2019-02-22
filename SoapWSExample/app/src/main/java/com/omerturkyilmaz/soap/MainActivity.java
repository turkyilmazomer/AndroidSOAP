package com.omerturkyilmaz.soap;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.omerturkyilmaz.soap.models.Employee;
import com.omerturkyilmaz.soap.ws.WebServiceCallerMethods;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);






    }

    public  void clickAllEmployees(View v){

        new AsyncTask<Object, Void, String>() {
            protected String doInBackground(Object... params) {
                WebServiceCallerMethods webServiceCallerMethods = new WebServiceCallerMethods();
                return webServiceCallerMethods.GetEmployee();
            }

            protected void onPostExecute(String result) {

                String employeeJSON= result;
                TextView textViewResult = (TextView) findViewById(R.id.textViewResult);
                textViewResult.setText(employeeJSON);

                Gson gson = new Gson();
                List<Employee> employees= gson.fromJson(employeeJSON, new TypeToken<List<Employee>>() {
                }.getType());
            }
        }.execute();



    }


    public  void clickSingleEmployee(View v){

        new AsyncTask<Object, Void, String>() {
            protected String doInBackground(Object... params) {
                WebServiceCallerMethods webServiceCallerMethods = new WebServiceCallerMethods();
                return webServiceCallerMethods.GetEmployeeById(1);
            }

            protected void onPostExecute(String result) {

                String employeeJSON= result;
                TextView textViewResult = (TextView) findViewById(R.id.textViewResult);
                textViewResult.setText(employeeJSON);

                Gson gson = new Gson();
                Employee employee= gson.fromJson(employeeJSON, new TypeToken<Employee>() {
                }.getType());




            }
        }.execute();

    }
}
