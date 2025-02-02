package edu.pmdm.sharemybike;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import edu.pmdm.sharemybike.bikes.BikesContent;
import edu.pmdm.sharemybike.databinding.ActivityBikeBinding;

public class BikeActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityBikeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityBikeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        //Obtenemos el controlador de navegación
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_bike);
        //Configuramos la appBar
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        //Establecemos la flecha de retroceso
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        //Cargamos la lista de bicicletas que tendremos que manipular en el ViewHolder
        BikesContent.loadBikesFromJSON(this);

    }

    /*
     * Método para volver atrás
     */
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_bike);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}