package com.example.expencetrackerapp.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.expencetrackerapp.R;
import com.example.expencetrackerapp.adapters.BillingsAdapter;
import com.example.expencetrackerapp.databinding.ActivityBillingBinding;
import com.example.expencetrackerapp.interfaces.FragmentBottomNavigation;
import com.example.expencetrackerapp.models.Billing;
import com.example.expencetrackerapp.viewmodel.BillingViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class BilingFragment extends Fragment implements  BillingsAdapter.OnItemClikedListener {

    FragmentBottomNavigation communicator;

    ActivityBillingBinding binding;

    ArrayList<Billing> billings = new ArrayList<>();

    BillingsAdapter billingAdapter;

    BillingViewModel billingViewModel;

    EditText editTextTitle,editTextContent,editTextDate;

    Integer noteId ;

    TextView editTextName ,editTextAmount;

    Button editTextCancle ,editTextOk;

    boolean isMarkAsRead  = false;

    Billing note;

    String dateTime;

    Calendar calendar;

    SimpleDateFormat simpleDateFormat;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            communicator = (FragmentBottomNavigation) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context + " must implement FragmentToActivityCommunicator");
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = ActivityBillingBinding.inflate(inflater, container, false);

        communicator.navigateBottomFrag(3, true);

        ViewModelProvider provider = new ViewModelProvider(getActivity());

        billingViewModel = provider.get(BillingViewModel.class);

        billingViewModel.getAllNotes().observe(getActivity(), notes -> {
            if (notes != null) {

                this.billings = (ArrayList<Billing>) notes;
                loadDate(billings);

            }
        });

        binding.addTaskFABtn.setOnClickListener(v -> {
            newCreateTodo();
        });

        return binding.getRoot();

    }

    private void loadDate(ArrayList<Billing> billings) {

            Log.d("TAG" ," loadDate >>> 1 " + binding.recyclerViewExpenses2);
            billingAdapter = new BillingsAdapter(getContext(), billings ,this);
            binding.recyclerViewExpenses2.setLayoutManager(new LinearLayoutManager(getContext()));
            binding.recyclerViewExpenses2.setAdapter(billingAdapter);
            billingAdapter.notifyDataSetChanged();



    }

    @SuppressLint("MissingInflatedId")
    private void newCreateTodo() {
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.activity_add_edit_note, null);

        AlertDialog dialogBuilder = new AlertDialog.Builder(getContext())
                .setView(dialogView)
                .create();

        editTextTitle = dialogView.findViewById(R.id.edit_text_title);
        editTextContent = dialogView.findViewById(R.id.edit_text_content);
        editTextDate = dialogView.findViewById(R.id.date_text_content);
        Button dialogButton = dialogView.findViewById(R.id.button_save);

        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        dateTime = simpleDateFormat.format(calendar.getTime()).toString();
        editTextDate.setText(dateTime);

        String date = editTextDate.getText().toString();

        dialogButton.setOnClickListener(v -> saveNewNotes(editTextTitle, editTextContent, editTextDate,dialogBuilder,date));

        dialogBuilder.show();
    }

    private void saveNewNotes(EditText editTextTitle, EditText editTextContent, EditText editTextDate, AlertDialog dialogBuilder ,String date) {


        String title = editTextTitle.getText().toString();
        String amount = editTextContent.getText().toString();



        if (title.isEmpty() || amount.isEmpty() || date.isEmpty()) {
            Toast.makeText(getContext(), "Please fill out all fields", Toast.LENGTH_SHORT).show();
            return;
        }


        Billing note = new Billing(
                noteId != null ? noteId : 0,
                title,
                amount,
                date,
                false
        );


        new Thread(() -> billingViewModel.insert(note)).start();


        dialogBuilder.dismiss();
    }

    @Override
    public void onItemClick(Billing billing) {
        newCreateTodo(billing);
    }

    @Override
    public void onItemMarkRead(Billing billing) {
         selectMarkasRead(billing);
    }

    private void newCreateTodo(Billing billing) {
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.activity_add_edit_note, null);

        AlertDialog dialogBuilder = new AlertDialog.Builder(getContext()).setView(dialogView).create();

        editTextTitle = dialogView.findViewById(R.id.edit_text_title);
        editTextContent = dialogView.findViewById(R.id.edit_text_content);
        editTextDate = dialogView.findViewById(R.id.date_text_content);
        Button dialogButton = dialogView.findViewById(R.id.button_save);

        editTextTitle.setText(billing.getTitle());
        editTextContent.setText(billing.getAmount());
        editTextDate.setText(billing.getDate());


        dialogButton.setOnClickListener(v -> saveNewNotes(editTextTitle, editTextContent, editTextDate,dialogBuilder,billing));

        dialogBuilder.show();
    }

    private void saveNewNotes(EditText editTextTitle, EditText editTextContent, EditText editTextDate, AlertDialog dialogBuilder, Billing billing) {

        String title = editTextTitle.getText().toString();
        String content = editTextContent.getText().toString();
        String date = editTextDate.getText().toString();


        if (title.isEmpty() || content.isEmpty() || date.isEmpty()) {
            Toast.makeText(getContext(), "Please fill out all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        isMarkAsRead = billing.isMarkAsRead();


        Billing note = new Billing(
                billing.getId(),
                title,
                content,
                date,
                isMarkAsRead
        );

        new Thread(() -> billingViewModel.update(note)).start();

        dialogBuilder.dismiss();
    }

    private void selectMarkasRead(Billing billing) {

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.mark_as_read, null);

        AlertDialog dialogBuilder = new AlertDialog.Builder(getContext()).setView(dialogView).create();

        editTextName = dialogView.findViewById(R.id.name);
        editTextAmount = dialogView.findViewById(R.id.amount);
        editTextCancle = dialogView.findViewById(R.id.button_cancel);
        editTextOk = dialogView.findViewById(R.id.button_ok);

        editTextName.setText(billing.getTitle());
        editTextAmount.setText(billing.getAmount());

        if (billing.isMarkAsRead()){
            editTextOk.setText("MARK AS PAID");
            editTextOk.setBackground(getActivity().getResources().getDrawable(R.color.blue));

        }else {
            editTextOk.setText("MARK AS UNPAID");
            editTextOk.setBackground(getActivity().getResources().getDrawable(R.color.light_red));

        }


        editTextCancle.setOnClickListener(v -> dialogBuilder.dismiss());


        editTextOk.setOnClickListener(v -> saveMarkasPaid(dialogBuilder,billing));

        dialogBuilder.show();

    }

    private void saveMarkasPaid(AlertDialog dialogBuilder, Billing billing) {


         if (billing.isMarkAsRead()){
              note = new Billing(
                     billing.getId(),
                     billing.getTitle(),
                     billing.getAmount(),
                     billing.getDate(),
                     false
             );
         }else {
              note = new Billing(
                     billing.getId(),
                     billing.getTitle(),
                     billing.getAmount(),
                     billing.getDate(),
                     true
             );
         }




        new Thread(() ->  billingViewModel.updateRead(note)).start();




         dialogBuilder.dismiss();



    }
}
