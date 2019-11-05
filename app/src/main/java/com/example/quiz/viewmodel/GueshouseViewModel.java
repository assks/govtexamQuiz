package com.example.quiz.viewmodel;

import android.content.Context;

import android.view.View;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

import com.example.quiz.modal.GuesthouseBooking;
import com.example.quiz.remote.APIService;
import com.example.quiz.remote.RetroClass;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GueshouseViewModel extends Observable {
    public ObservableInt guesthouseProgress;
    public ObservableInt guesthouseRecycler;
    public ObservableInt guesthouseLabel;
    public ObservableField<String> messageLabel;

    private List<GuesthouseBooking> guesthouseList;
    private Context context;

    public GueshouseViewModel(@Nullable Context context, String userId) {
        this.context = context;
       // userPref = new UserPref(context);
        this.guesthouseList = new ArrayList<>();
        guesthouseProgress = new ObservableInt(View.GONE);
        guesthouseRecycler = new ObservableInt(View.GONE);
        guesthouseLabel = new ObservableInt(View.VISIBLE);
        messageLabel = new ObservableField<>("sds");

        initializeViews();
        fetchGueshouseList();
    }

    private void initializeViews() {
        guesthouseLabel.set(View.GONE);
        guesthouseRecycler.set(View.GONE);
        guesthouseProgress.set(View.VISIBLE);
    }

    private void fetchGueshouseList() {
        APIService api = RetroClass.getRetrofitInstance().create(APIService.class);

        Call<List<GuesthouseBooking>> call = api.fetchBookingList("self_booking","38", /*userId,*/"38","148451");
        call.enqueue(new Callback<List<GuesthouseBooking>>() {
            @Override
            public void onResponse(@NonNull Call<List<GuesthouseBooking>> call, @NonNull Response<List<GuesthouseBooking>> response) {
                if (response.isSuccessful()) {
                    List<GuesthouseBooking> stringResponse = response.body();
                    if (stringResponse != null) {
                        changePeopleDataSet(stringResponse);
                        guesthouseProgress.set(View.GONE);
                        guesthouseLabel.set(View.GONE);
                        guesthouseRecycler.set(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<GuesthouseBooking>> call, @NonNull Throwable t) {

                if (t instanceof SocketTimeoutException) {
                    messageLabel.set("problem_to_connect"/*context.getString(R.string.problem_to_connect)*/);
                }
                guesthouseProgress.set(View.GONE);
                guesthouseLabel.set(View.VISIBLE);
                guesthouseRecycler.set(View.VISIBLE);
            }
        });
    }

    private void changePeopleDataSet(List<GuesthouseBooking> guesthouses) {
        guesthouseList.addAll(guesthouses);
        setChanged();
        notifyObservers();
    }

    public void refreshList(){
        guesthouseList.clear();
        fetchGueshouseList();
    }

    public List<GuesthouseBooking> getGuesthouseList() {
        return guesthouseList;
    }


    public void reset() {
        context = null;
    }

}
