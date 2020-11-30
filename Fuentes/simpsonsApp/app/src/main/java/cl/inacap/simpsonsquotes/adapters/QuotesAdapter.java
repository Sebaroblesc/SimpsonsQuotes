package cl.inacap.simpsonsquotes.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.List;

import cl.inacap.simpsonsquotes.R;
import cl.inacap.simpsonsquotes.dto.Personaje;

public class QuotesAdapter extends ArrayAdapter<Personaje> {
    private Activity activity;
    private List<Personaje> personajes;


    public QuotesAdapter(@NonNull Activity context, int resource, @NonNull List<Personaje> objects) {
        super(context, resource, objects);
        this.activity = context;
        this.personajes = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = this.activity.getLayoutInflater();
        View row = inflater.inflate(R.layout.citas_list,null,true);

        TextView citaTxt = row.findViewById(R.id.cita_vw);
        TextView nombreTxt = row.findViewById(R.id.nombre_vw);
        ImageView img = row.findViewById(R.id.img_vw);

        citaTxt.setText(personajes.get(position).getQuote());
        nombreTxt.setText(personajes.get(position).getCharacter());
        Picasso.get().load(this.personajes.get(position).getImage()).resize(400,400).centerInside()
                .into(img);

        return row;


    }
}
