package com.example.facultyapp.ui.main.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;

import com.example.facultyapp.data.AppDatabase;
import com.example.facultyapp.data.model.User;

public class MainViewModel extends AndroidViewModel {

    private User userLiveData;
    private AppDatabase appDatabase;

    public MainViewModel(Application application) {
        super(application);
        appDatabase = AppDatabase.getDatabase(this.getApplication());
        userLiveData = appDatabase.userDao().getUserById("1");
    }

    public User getUserLiveData() {
        return userLiveData;
    }
}
