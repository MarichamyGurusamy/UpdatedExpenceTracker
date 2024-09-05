package com.example.expencetrackerapp.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.expencetrackerapp.dao.BillingDao;
import com.example.expencetrackerapp.database.BillingDatabase;
import com.example.expencetrackerapp.models.Billing;
import com.example.expencetrackerapp.repository.BillingRepository;
import java.util.List;

public class BillingViewModel extends AndroidViewModel {

    final BillingRepository repository;

    public BillingViewModel(@NonNull Application application) {
        super(application);
        BillingDao billingDao = BillingDatabase.getDatabase(application).billingDao();
        repository = new BillingRepository(billingDao);
    }

    public LiveData<List<Billing>> getAllNotes() {
       return  repository.getAllNotes();
    }

    public void insert(Billing note) {
            repository.insert(note);
    }

    public void update(Billing note) {
            repository.update(note);
    }

    public void updateRead(Billing billing) {
        repository.updateRead(billing);
    }
}