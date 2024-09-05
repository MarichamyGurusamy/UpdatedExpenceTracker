package com.example.expencetrackerapp.repository;

import androidx.lifecycle.LiveData;

import com.example.expencetrackerapp.dao.BillingDao;
import com.example.expencetrackerapp.models.Billing;

import java.util.List;

public class BillingRepository {

    private final BillingDao billingDao;

    public BillingRepository(BillingDao billingDao) {
        this.billingDao = billingDao;
    }

    public LiveData<List<Billing>> getAllNotes() {
        return billingDao.getAllNotes();
    }

    public void insert(Billing note) {
        billingDao.insert(note);
    }

    public void update(Billing note) {
        billingDao.update(note);
    }

    public void updateRead(Billing note) {
        billingDao.updateRead(note);
    }

}
