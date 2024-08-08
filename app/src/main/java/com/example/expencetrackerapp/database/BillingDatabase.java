package com.example.expencetrackerapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.expencetrackerapp.dao.BillingDao;
import com.example.expencetrackerapp.dao.ExpenseDao;
import com.example.expencetrackerapp.models.Billing;
import com.example.expencetrackerapp.models.Expense;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Billing.class}, version = 6, exportSchema = false)
public abstract class BillingDatabase extends RoomDatabase {

    public abstract BillingDao billingDao();

    private static volatile BillingDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static BillingDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (BillingDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    BillingDatabase.class, "billing_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}