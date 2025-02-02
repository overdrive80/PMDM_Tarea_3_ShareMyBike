package edu.pmdm.sharemybike;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.pmdm.sharemybike.bikes.BikesContent;
import edu.pmdm.sharemybike.placeholder.PlaceholderContent.PlaceholderItem;
import edu.pmdm.sharemybike.databinding.FragmentBikeItemBinding;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

//    PARA EL CODIGO DE EJEMPLO USANDO EL CONTENIDO DE LA CLASE PLACEHOLDER
//    private final List<PlaceholderItem> mValues;
//    public BikeAdapter_RecyclerView(List<PlaceholderItem> items) {
//        mValues = items;
//    }

    private final List<BikesContent.Bike> mValues;
    private String fechaSeleccion;

    public MyItemRecyclerViewAdapter(List<BikesContent.Bike> items, String fechaSeleccion) {
        mValues = items;
        this.fechaSeleccion = fechaSeleccion;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentBikeItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    // DELEGAMOS EN EL HOLDER LA VINCULACIÓN DE LOS DATOS
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.bind(mValues.get(position));
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    /**
     * holder
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        public PlaceholderItem mItem;
        private FragmentBikeItemBinding binding;

        public ViewHolder(FragmentBikeItemBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }

        //CREAMOS UN METODO PARA VINCULAR LOS DATOS
        public void bind(BikesContent.Bike bici) {
            binding.ivBici.setImageBitmap(bici.getPhoto());
            binding.tvCiudad.setText(bici.getCity());
            binding.tvPropietario.setText(bici.getOwner());
            binding.tvDireccion.setText(bici.getLocation());
            binding.tvDescripcion.setText(bici.getDescription());

            binding.ibEmail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    enviarEmail(v, bici.getEmail());
                }
            });
        }

        private void enviarEmail(View v, String email) {

            Intent intent = new Intent();
            Intent chooser = null;

            intent.setAction(Intent.ACTION_SEND);
            String uriEmail = "mailto:";
            intent.setData(Uri.parse(uriEmail));

            //Código para enviar el email
            String textoEmail = v.getContext().getString(
                    R.string.email_content,
                    binding.tvPropietario.getText(),
                    binding.tvDireccion.getText(),
                    binding.tvCiudad.getText(),
                    fechaSeleccion);

            String[] destinos = {email};

            //3. Extras
            intent.putExtra(Intent.EXTRA_EMAIL, destinos);
            intent.putExtra(Intent.EXTRA_SUBJECT, "ShareMyBike");
            intent.putExtra(Intent.EXTRA_TEXT, textoEmail);

            intent.setType("message/rfc822");
            chooser = intent.createChooser(intent, "Enviar Email");

            v.getContext().startActivity(chooser);
        }

    }


}