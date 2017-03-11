package es.alonsoftware.elecciones26j.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.text.NumberFormat;
import java.util.List;

import es.alonsoftware.elecciones26j.Grafico2View;
import es.alonsoftware.elecciones26j.R;
import es.alonsoftware.elecciones26j.dao.Lugar;
import es.alonsoftware.elecciones26j.dao.Partido;

/**
 * Created by Jorge on 19/5/16.
 */
public class Partido2Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Partido> mItems;
    private List<Partido> mItems2;
    private Lugar lugar;
    private Lugar lugar2;
    private boolean porVotos;
    private List<Partido> temp;

    public Partido2Adapter(List<Partido> mItems, List<Partido> mItems2,  Lugar lugar, Lugar lugar2, boolean porVotos) {
        this.mItems = mItems;
        this.mItems2 = mItems2;
        this.lugar = lugar;
        this.lugar2 = lugar2;
        this.porVotos = porVotos;

        Log.d("PartidoAdapter", "PorVoto: "+porVotos);
    }


    @Override
    public int getItemCount(){
        return mItems.size()+2;
    }


    @Override
    public int getItemViewType(int position) {
        // Just as an example, return 0 or 2 depending on position
        // Note that unlike in ListView adapters, types don't have to be contiguous
        return position;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType){
            case 0:
                return new GraficoHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_2_partidos, parent, false));
            case 1:
                return new ViewHolderTitulo(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_partido_comparator_titulos, parent, false));
            default:
                return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_partido_comparator, parent, false));
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position == 1){
            ((ViewHolderTitulo) holder).setData(lugar);
            ((ViewHolderTitulo) holder).setData2(lugar2);
        }else if (position != 0) {
            if (mItems.size() > position-2)
                ((ViewHolder) holder).setData(mItems.get(position - 2));
            if (mItems2.size() > position-2)
                ((ViewHolder) holder).setData2(mItems2.get(position - 2));

        }else
            ((GraficoHolder) holder).setPartidos(mItems, mItems2, lugar, lugar2, porVotos);
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        public TextView nombre, nombre2, votos, electos, electos2, votos2;
        public Partido item;
        public Context context;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            nombre = (TextView) itemView.findViewById(R.id.nombre);
            nombre2 = (TextView) itemView.findViewById(R.id.nombre2);
            votos = (TextView) itemView.findViewById(R.id.votos);
            electos = (TextView) itemView.findViewById(R.id.electos);
            votos2 = (TextView) itemView.findViewById(R.id.votos2);
            electos2 = (TextView) itemView.findViewById(R.id.electos2);
            context = itemView.getContext();
        }

        public void setData(Partido item) {
            this.item = item;
            nombre.setText(item.getNombre());
            votos.setText("" + NumberFormat.getInstance().format(item.getVotos_numero()));

            electos.setText("" + item.getElectos());
            electos.setBackgroundResource(R.drawable.rounded_background);

            GradientDrawable drawable = (GradientDrawable) electos.getBackground();
            drawable.setColor(item.getColor());
        }

        public void setData2(Partido item) {
            this.item = item;
            nombre2.setText(item.getNombre());
            votos2.setText("" + NumberFormat.getInstance().format(item.getVotos_numero()));

            electos2.setText("" + item.getElectos());
            electos2.setBackgroundResource(R.drawable.rounded_background);

            GradientDrawable drawable = (GradientDrawable) electos2.getBackground();
            drawable.setColor(item.getColor());
        }


        @Override
        public void onClick(View v) {
            Log.d("ON CCLIE", "ON CLICKE");

        }
    }

    public class ViewHolderTitulo extends RecyclerView.ViewHolder {


        public TextView titulo, titulo2;

        public ViewHolderTitulo(View itemView) {
            super(itemView);
            titulo = (TextView) itemView.findViewById(R.id.titulo);
            titulo2 = (TextView) itemView.findViewById(R.id.titulo2);
        }

        public void setData(Lugar lugar) {
            titulo.setText(""+lugar.getYear());
        }public void setData2(Lugar lugar) {
            titulo2.setText(""+lugar.getYear());
        }

    }


    public class GraficoHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener  {

        List<Partido> partidos;
        List<Partido> partidos2;
        Lugar lugar;
        Grafico2View grafico;
        Context context;
        private TextView titulo;
        private View v1;

        public GraficoHolder(View itemView) {
            super(itemView);
            v1 = itemView;
            context = itemView.getContext();
            grafico = (Grafico2View) itemView.findViewById(R.id.grafico);
            titulo = (TextView) itemView.findViewById(R.id.titulo);
            itemView.setOnLongClickListener(this);
            grafico.setOnLongClickListener(this);

        }

        public void setPartidos(List<Partido> partidos,List<Partido> partidos2, Lugar lugar, Lugar lugar2, boolean porVotos) {
            this.partidos = partidos;
            this.partidos2 = partidos2;
            this.lugar = lugar;
            grafico.setData(partidos, partidos2);
            grafico.setTitulo(lugar.getTitulo());
            grafico.setPorVotos(porVotos);
            grafico.invalidate();
            titulo.setText(lugar.getTitulo(lugar2));

            Log.d("setPartidos", "PorVoto: "+porVotos);

        }


        @Override
        public boolean onLongClick(View v) {

            try {

                File imageFile = new File(context.getCacheDir(), "documents/image.jpg");
                final File parentDir = imageFile.getParentFile();

                if ((parentDir != null) && !parentDir.exists())
                    parentDir.mkdirs();

                final Uri uri = FileProvider.getUriForFile(context, context.getPackageName(), imageFile);

                v1.setBackgroundColor(Color.WHITE);
                v1.setDrawingCacheEnabled(true);
                Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
                v1.setDrawingCacheEnabled(false);
                FileOutputStream outputStream = new FileOutputStream(imageFile);
                int quality = 100;
                bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
                outputStream.flush();
                outputStream.close();
                v1.setBackgroundColor(Color.TRANSPARENT);

                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("image/*");
                sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);
                sharingIntent.putExtra(Intent.EXTRA_TEXT, "Elecciones 26-J en tu móvil http://bit.ly/ele26j");
                sharingIntent
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET)
                        .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                context.startActivity(Intent.createChooser(sharingIntent, "Compartir"));

            } catch (Throwable e) {
                e.printStackTrace();
            }
            return false;
        }
    }



}