package es.alonsoftware.elecciones26j.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.text.NumberFormat;
import java.util.List;

import es.alonsoftware.elecciones26j.GraficoView;
import es.alonsoftware.elecciones26j.R;
import es.alonsoftware.elecciones26j.dao.Lugar;
import es.alonsoftware.elecciones26j.dao.Partido;
import es.alonsoftware.elecciones26j.dao.Porcentaje;

/**
 * Created by Jorge on 19/5/16.
 */
public class PartidoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Partido> mItems;
    private Lugar lugar;
    private boolean porVotos;
    private Porcentaje porcentaje;

    public PartidoAdapter(List<Partido> mItems, Lugar lugar, boolean porVotos, Porcentaje porcentaje) {
        this.mItems = mItems;
        this.lugar = lugar;
        this.porVotos = porVotos;
        this.porcentaje = porcentaje;

        Log.d("PartidoAdapter", "PorVoto: "+porVotos);
    }


    @Override
    public int getItemCount(){
        return mItems.size()+1;
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
                return new GraficoHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_main, parent, false));
            default:
                return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_partido, parent, false));
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position != 0)
            ((ViewHolder) holder).setData(mItems.get(position-1));
        else {
            ((GraficoHolder) holder).setPartidos(mItems, lugar, porVotos);
            ((GraficoHolder) holder).setPorcentaje(porcentaje);

        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        public TextView nombre, votos, porcentaje, electos;
        public Partido item;
        public Context context;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            nombre = (TextView) itemView.findViewById(R.id.nombre);
            votos = (TextView) itemView.findViewById(R.id.votos);
            porcentaje = (TextView) itemView.findViewById(R.id.porcentaje);
            electos = (TextView) itemView.findViewById(R.id.electos);
            context = itemView.getContext();
        }

        public void setData(Partido item) {
            this.item = item;
            nombre.setText(item.getNombre());
            votos.setText("" + NumberFormat.getInstance().format(item.getVotos_numero()) + " votos");
            porcentaje.setText("" + item.getVotos_porciento() + "%");

            electos.setText("" + item.getElectos());
            electos.setBackgroundResource(R.drawable.rounded_background);

            GradientDrawable drawable = (GradientDrawable) electos.getBackground();
            drawable.setColor(item.getColor());
        }

        @Override
        public void onClick(View v) {
            Log.d("ON CCLIE", "ON CLICKE 1 1 11 1");

            View screenView = v.getRootView();
            screenView.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(screenView.getDrawingCache());
            screenView.setDrawingCacheEnabled(false);


            try{
                String pathofBmp = "";
                pathofBmp = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap,"title", null);
                Log.d("URI", pathofBmp);
                Uri bmpUri = Uri.parse(pathofBmp);
                final Intent emailIntent1 = new Intent(     android.content.Intent.ACTION_SEND);
                emailIntent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                emailIntent1.putExtra(Intent.EXTRA_STREAM, bmpUri);
                emailIntent1.setType("image/png");

            } catch (Exception ex){
                ex.printStackTrace();
            }



        }
    }


    public class GraficoHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        List<Partido> partidos;
        Lugar lugar;
        GraficoView grafico;
        Context context;
        private TextView titulo;
        private TextView porcentaje_text;
        private ProgressBar progressBar;
        private View v1;

        public GraficoHolder(View itemView) {
            super(itemView);
            v1 = itemView;
            context = itemView.getContext();
            grafico = (GraficoView) itemView.findViewById(R.id.grafico);
            titulo = (TextView) itemView.findViewById(R.id.titulo);
            porcentaje_text = (TextView) itemView.findViewById(R.id.porcentaje_text);
            progressBar = (ProgressBar) itemView.findViewById(R.id.porcentaje_barra);
            progressBar.setMax(100);
            progressBar.setProgress(0);
            itemView.setOnLongClickListener(this);
            grafico.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {

            Log.d("CLICK", "CLICK  2 2 2 ");
        }



        public void setPartidos(List<Partido> partidos, Lugar lugar, boolean porVotos) {
            this.partidos = partidos;
            this.lugar = lugar;
            grafico.setData(partidos);
            grafico.setTitulo(lugar.getTitulo());
            grafico.setPorVotos(porVotos);
            grafico.invalidate();
            titulo.setText(lugar.getTitulo());

            Log.d("setPartidos", "PorVoto: "+porVotos);

        }

        public void setPorcentaje(Porcentaje p){
            progressBar.setProgress(p.getPorcentaje());
            porcentaje_text.setText(""+p.getPorcentaje()+" %");
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