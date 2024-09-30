package com.example.expencetrackerapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.expencetrackerapp.dao.BudgetDao;
import com.example.expencetrackerapp.dao.ExpenseDao;
import com.example.expencetrackerapp.models.Budget;
import com.example.expencetrackerapp.models.Expense;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Budget.class}, version = 3, exportSchema = false)
public abstract class BudgetsDatabase extends RoomDatabase {

    public abstract BudgetDao budgetDao(); // Add this line


    private static volatile BudgetsDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static BudgetsDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (BudgetsDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    BudgetsDatabase.class, "budget_table")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}