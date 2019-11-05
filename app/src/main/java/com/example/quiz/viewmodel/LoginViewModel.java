package com.example.quiz.viewmodel;

import android.content.Context;

import android.util.Log;
import android.view.View;
import android.widget.Toast;


import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

import com.example.quiz.remote.APIService;
import com.example.quiz.remote.RetroClass;

import java.util.Observable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by lenovo on 3/31/2018.
 */

public class LoginViewModel extends Observable {

    private Context context;
    public ObservableInt progressBar;
    public final ObservableField<String> username = new ObservableField<>("");
    public final ObservableField<String> userpass = new ObservableField<>("");

    public LoginViewModel(Context context)
    {

        this.context = context;
        progressBar = new ObservableInt(View.GONE);


    }


    public void sendLoginRequest(String name , String pass)
    {

        //showToast("inside loginviewmodel");

        progressBar.set(View.VISIBLE);

        APIService apiService = RetroClass.getAPIService();
        Call<String> loginresponse = apiService.userlogincall(name,pass);
        loginresponse.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                progressBar.set(View.GONE);
                showToast(""+response.body().toString());
                Log.d("response",response.body());

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                progressBar.set(View.GONE);
                showToast(""+t.getMessage());

            }
        });




    }





    void showToast(String msg)
    {

        Toast.makeText(context,msg, Toast.LENGTH_LONG).show();

    }

}
