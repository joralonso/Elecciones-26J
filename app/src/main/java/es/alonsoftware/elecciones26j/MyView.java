package es.alonsoftware.elecciones26j;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.List;

import es.alonsoftware.elecciones26j.dao.Partido;

/**
 * Created by Jorge on 23/5/16.
 */
public class MyView extends View {

    int[] data = { 123, 90, 69, 40, 20,10, 5 };

    int[] color = {Color.RED, Color.BLUE, Color.GREEN, Color.BLACK, Color.YELLOW, Color.GREEN, Color.CYAN};

    List<Partido> partidos;
    String titulo = "Congreso 2016";
    boolean porVotos = false;

    public MyView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        int radius;
        radius = 100;
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        canvas.drawPaint(paint);
        // Use Color.parseColor to define HTML colors
        paint.setColor(Color.parseColor("#CD5C5C"));
        //canvas.drawCircle(x / 2, y / 2, radius, paint);

        paint.setColor(Color.BLACK);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(40f);
        canvas.drawText("ELECCIONES 26-J", width / 2, 40, paint);
        paint.setTextSize(30f);
        canvas.drawText(titulo, width / 2, 80, paint);


        paint.setTextSize(20f);
        canvas.drawText("alonsoftware.es", width / 2, height-100, paint);

        if (partidos == null){
            Log.d("MyView", "Partidos NULL");
            return;
        }


        Log.d("MyView", "PorVotos: "+porVotos);

        if (!porVotos) {

            int total = 0;
            float sum = 0;
            for (Partido p : partidos) {
                sum += p.getElectos();
                total += p.getElectos();
            }

            float deltaAngle = 180f / sum;

            Log.d("DeltaAngle", "180 / " + sum + " =  " + deltaAngle + " (" + (180 / sum) + ")");
            Log.d("With", "" + width);
            int x = 16;
            int y = 100;
            int diameter;


            Log.d("MyView", "Calculo diameter : "+(width - x*2)+"  -  "+(height - 180) * 2);

            if ((width - x*2) < (height - 180) * 2){
                diameter = width - x * 2;
                Log.d("MyView", "diameter 1 : "+diameter);
            }else{
               diameter =  (height - 180) * 2;
                Log.d("MyView", "diameter 2 : "+diameter);
            }
            x = (width - diameter) / 2;


            int startAngle = 180;


            int textPosition = 0;

            for (Partido p : partidos) {

                if (p.getElectos() == 0)
                    break;

                paint.setColor(p.getColor());

                RectF rec = new RectF(x, y, diameter, (height - 180) * 2);

                double deltaAngleTemp = deltaAngle * p.getElectos();

                if (deltaAngleTemp < 1)
                    deltaAngleTemp = 1;

                if (startAngle > 360)
                    break;


                canvas.drawArc(rec, startAngle, (float) deltaAngleTemp, true, paint);
                startAngle += deltaAngleTemp;
                Log.d("Dato " + p.getNombre() + " " + p.getElectos(), "" + x + " " + y + " " + diameter + " " + startAngle + " " + deltaAngleTemp);

            }


            paint.setColor(Color.WHITE);

            Log.d("MyView", "HEIGHT: "+x+", "+y+", "+diameter+", "+ ((height - 180) * 2));
            Log.d("MyView", "HEIGHT: "+(x+(x/2))+", "+(y +(width/4))+", "+( diameter - (width/4))+", "+ ((height - 180) * 2 +(width/4)));

            RectF rec = new RectF(x + (width/4), y +(height/5), diameter - (width/4) , (height - 180) * 2 -(height/5));

            int deltaAngleTemp = 180;
            canvas.drawArc(rec, 180, deltaAngleTemp, true, paint);


            paint.setTextSize(25f);
            paint.setTextAlign(Paint.Align.CENTER);
            for (int i = 0; i < 4; i++) {
                paint.setColor(partidos.get(i).getColor());

                if (partidos.get(i).getNombre().length() > 6)
                    canvas.drawText(partidos.get(i).getNombre().substring(0, 6) + " " + partidos.get(i).getElectos(), (width / 4) * i + (width / 8), height - 50, paint);
                else
                    canvas.drawText(partidos.get(i).getNombre() + " " + partidos.get(i).getElectos(), (width / 4) * i + (width / 8), height - 50, paint);
            }

            for (int i = 0; i < 4; i++) {
                paint.setColor(partidos.get(i + 4).getColor());
                if (partidos.get(i + 4).getNombre().length() > 6)
                    canvas.drawText(partidos.get(i + 4).getNombre().substring(0, 6) + " " + partidos.get(i + 4).getElectos(), (width / 4) * i + (width / 8), height - 20, paint);
                else
                    canvas.drawText(partidos.get(i + 4).getNombre() + " " + partidos.get(i + 4).getElectos(), (width / 4) * i + (width / 8), height - 20, paint);
            }

        } else {


            int total = 0;
            float sum = 0;
            for (Partido p : partidos) {
                sum += p.getVotos_numero();
                total += p.getVotos_numero();
            }

            float deltaAngle = 180f / sum;

            Log.d("DeltaAngle", "180 / " + sum + " =  " + deltaAngle + " (" + (180 / sum) + ")");
            Log.d("With", "" + width);
            int x = 16;
            int y = 100;
            int diameter;

            diameter = width - x * 2;

            int startAngle = 180;


            int textPosition = 0;

            for (Partido p : partidos) {

                if (p.getVotos_numero() == 0)
                    break;

                paint.setColor(p.getColor());

                RectF rec = new RectF(x, y, diameter, (height - 180) * 2);

                double deltaAngleTemp = deltaAngle * p.getVotos_numero();

                if (deltaAngleTemp < 1)
                    deltaAngleTemp = 1;

                if (startAngle > 360)
                    break;


                canvas.drawArc(rec, startAngle, (float) deltaAngleTemp, true, paint);
                startAngle += deltaAngleTemp;
                Log.d("Dato " + p.getNombre() + " " + p.getVotos_numero(), "" + x + " " + y + " " + diameter + " " + startAngle + " " + deltaAngleTemp);

            }


            paint.setColor(Color.WHITE);

            RectF rec = new RectF(x + 200, y + 202, diameter - 200, (height - 180) * 2 - 200);

            int deltaAngleTemp = 180;
            canvas.drawArc(rec, 180, deltaAngleTemp, true, paint);


            paint.setTextSize(25f);
            paint.setTextAlign(Paint.Align.CENTER);
            for (int i = 0; i < 2; i++) {
                paint.setColor(partidos.get(i).getColor());

                if (partidos.get(i).getNombre().length() > 6)
                    canvas.drawText(partidos.get(i).getNombre().substring(0, 6) + " " + partidos.get(i).getVotos_numero(), (width / 2) * i + (width / 4), height - 50, paint);
                else
                    canvas.drawText(partidos.get(i).getNombre() + " " + partidos.get(i).getVotos_numero(), (width / 2) * i + (width /4), height - 50, paint);
            }

            for (int i = 0; i < 2; i++) {
                paint.setColor(partidos.get(i + 2).getColor());
                if (partidos.get(i + 2).getNombre().length() > 6)
                    canvas.drawText(partidos.get(i + 2).getNombre().substring(0, 6) + " " + partidos.get(i + 4).getVotos_numero(), (width / 2) * i + (width / 4), height - 20, paint);
                else
                    canvas.drawText(partidos.get(i + 2).getNombre() + " " + partidos.get(i + 4).getVotos_numero(), (width / 2) * i + (width / 4), height - 20, paint);
            }

        }
        //RectF rec = new RectF(0, 0, 100, 200);

        //canvas.drawRect(rec,  paint);
        //canvas.drawArc(rec, 90, 200, true, paint);

       // RectF rectF = new RectF(50, 20, 100, 80);
        //canvas.drawOval(rectF, paint);
        //canvas.drawArc (rectF, 90, 45, true, paint);


    }

    public void setData(List<Partido> partidos){
        this.partidos = partidos;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setPorVotos(boolean porVotos) {
        this.porVotos = porVotos;
    }
}