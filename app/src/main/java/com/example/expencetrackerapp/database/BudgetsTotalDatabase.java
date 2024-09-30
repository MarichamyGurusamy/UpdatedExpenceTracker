package com.example.expencetrackerapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.expencetrackerapp.dao.BudgetDao;
import com.example.expencetrackerapp.dao.BudgetTotalDao;
import com.example.expencetrackerapp.models.Budget;
import com.example.expencetrackerapp.models.BudgetTotal;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {BudgetTotal.class}, version = 1, exportSchema = false)
public abstract class BudgetsTotalDatabase extends RoomDatabase {

    public abstract BudgetTotalDao budgetTotalDao(); // Add this line


    private static volatile BudgetsTotalDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static BudgetsTotalDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (BudgetsTotalDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    BudgetsTotalDatabase.class, "budget_totals")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}