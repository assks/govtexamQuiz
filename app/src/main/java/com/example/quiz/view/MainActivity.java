package com.example.quiz.view;


import android.os.Bundle;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.quiz.R;
import com.example.quiz.databinding.ActivityMainBinding;
import com.example.quiz.presenter.Presenter;
import com.example.quiz.viewmodel.LoginViewModel;

public class MainActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private ActivityMainBinding activityMainBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        loginViewModel = new LoginViewModel(this);
        activityMainBinding.setLoginview(loginViewModel);

        activityMainBinding.setPresenter(new Presenter() {
            @Override
            public void loginData() {

              //  showToast("Login activity");

                final String name = activityMainBinding.username.getText().toString();
                final String pass = activityMainBinding.userpass.getText().toString();


                loginViewModel.sendLoginRequest(name, pass);

            }
        });

    }


    void showToast(String msg)
    {

        Toast.makeText(this,msg, Toast.LENGTH_LONG).show();

    }


}
