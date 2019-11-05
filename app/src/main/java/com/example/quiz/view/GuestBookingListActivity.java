package com.example.quiz.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;


import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quiz.R;
import com.example.quiz.adapter.GuesthouseAdapter;
import com.example.quiz.databinding.ActivityGuestBookingListBinding;
import com.example.quiz.viewmodel.GueshouseViewModel;

import java.util.Observable;
import java.util.Observer;

@SuppressLint("Registered")
public class GuestBookingListActivity extends AppCompatActivity implements Observer {

    ActivityGuestBookingListBinding binding;
    private GuesthouseAdapter adapter;
    private GueshouseViewModel gueshouseViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_guest_booking_list);

        setToolbar();
        initDataBinding();
        setupListPeopleView(binding.bookingRecyclerView);
        setupObserver(gueshouseViewModel);
    }

    private void setToolbar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getResources().getString(R.string.guesthouse_bookings));
            actionBar.setBackgroundDrawable(new ColorDrawable());
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }

    private void initDataBinding() {
       // UserPref userPref = new UserPref(this);
        gueshouseViewModel = new GueshouseViewModel(this, "38");
        binding.setMainViewModel(gueshouseViewModel);
    }

    private void setupListPeopleView(RecyclerView listPeople) {
        adapter = new GuesthouseAdapter();
        listPeople.setAdapter(adapter);
        listPeople.setLayoutManager(new LinearLayoutManager(this));
        listPeople.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

    }

    public void setupObserver(Observable observable) {
        observable.addObserver(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        gueshouseViewModel.reset();
    }



    @Override
    public void update(Observable observable, Object data) {
        if (observable instanceof GueshouseViewModel) {
            GuesthouseAdapter guesthouseAdapter = (GuesthouseAdapter) binding.bookingRecyclerView.getAdapter();
            GueshouseViewModel gueshouseViewModel = (GueshouseViewModel) observable;
            guesthouseAdapter.setGuesthouseBookingList(gueshouseViewModel.getGuesthouseList());

        }
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.request_leave, menu);
        MenuItem menuItem = menu.findItem(R.id.menu_request);
        menuItem.setTitle(getResources().getString(R.string.booking));
        menuItem.setIcon(getResources().getDrawable(R.drawable.study1));
        return super.onCreateOptionsMenu(menu);
    }*/

   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_request) {
            startActivityForResult(new Intent(this, GuesthouseBookingRequestActivity.class),1);
            return true;
        } else
            return super.onOptionsItemSelected(item);
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK){
            adapter.clearList();
            gueshouseViewModel.refreshList();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
