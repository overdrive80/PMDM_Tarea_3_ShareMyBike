package edu.pmdm.sharemybike;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import edu.pmdm.sharemybike.databinding.FragmentFechaBinding;

public class FirstFragment extends Fragment implements CalendarView.OnDateChangeListener, View.OnClickListener {

    private FragmentFechaBinding binding;
    private CalendarView calendario;
    private TextView seleccion;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        binding = FragmentFechaBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Las declaraciones de elementos cuando la vista esté creada
        calendario = binding.cvCalendario;
        seleccion = binding.tvSeleccion;

        calendario.setOnDateChangeListener(this);
        binding.btnBuscar.setOnClickListener(this);
    }

    @Override
    public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {

        binding.btnBuscar.setEnabled(true);
        String formattedDate = getString(R.string.stringfecha, day, month + 1, year);
        seleccion.setText(formattedDate);
    }

    @Override
    public void onClick(View view) {

        //Pasamos un bundle en la navegación
        Bundle bundle = new Bundle();
        bundle.putString("fecha_seleccion", seleccion.getText().toString());


        NavHostFragment.findNavController(FirstFragment.this)
                .navigate(R.id.action_FechaFragment_to_BikeFragment, bundle);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}