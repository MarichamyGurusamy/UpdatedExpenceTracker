package com.example.expencetrackerapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.expencetrackerapp.dao.BudgetCategoryDao;
import com.example.expencetrackerapp.dao.BudgetDao;
import com.example.expencetrackerapp.models.Budget;
import com.example.expencetrackerapp.models.BudgetCategory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {BudgetCategory.class}, version = 3, exportSchema = false)
public abstract class BudgetCategoryDatabase extends RoomDatabase {

    public abstract BudgetCategoryDao budgetCategoryDao(); // Add this line


    private static volatile BudgetCategoryDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static BudgetCategoryDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (BudgetCategoryDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    BudgetCategoryDatabase.class, "budgetcategory_table")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}