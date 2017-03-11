package es.alonsoftware.elecciones26j;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;

import es.alonsoftware.elecciones26j.dao.Partido;

/**
 * Created by Jorge on 23/5/16.
 */
public class GraficoView extends View {

    int[] datos = { 123, 90, 69, 40, 20,10, 5 };

    int[] color = {Color.RED, Color.BLUE, Color.GREEN, Color.BLACK, Color.YELLOW, Color.GREEN, Color.CYAN};

    List<Partido> partidos;
    String titulo = "Congreso 2016";
    boolean porVotos = false;

    public GraficoView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public GraficoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GraficoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        int WIDTH = getWidth();
        int HEIGHT = getHeight();
        int x = 0;
        int y = 0;


        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.TRANSPARENT);
        canvas.drawPaint(paint);

        int sum = 0;

        if (porVotos){
            for (Partido p: partidos)
                sum += p.getVotos_numero();
        }else{
            for (Partido p: partidos)
                sum += p.getElectos();
        }



        System.out.println("Sum "+sum);
        float deltaAngle = 180f / sum;
        float startAngle = -180;

        System.out.println("Start "+startAngle+" and Delta "+deltaAngle);

        int diameter;
        if (WIDTH - x > (HEIGHT - y)*2) {
            diameter = HEIGHT - y * 2;
            diameter *= 2;
        }else
            diameter = WIDTH - x * 2;


        System.out.println("WIDTH "+WIDTH+" HEIGHT "+HEIGHT);
        System.out.println("Diametro: "+diameter);

        canvas.drawRect(x, y, WIDTH - x * 2, (HEIGHT - y * 2), paint);

        x = (WIDTH - diameter) / 2;

        for (Partido p : partidos) {
            paint.setColor(p.getColor());
            float deltaTemp = 0;

            if (porVotos) {
                deltaTemp = deltaAngle * p.getVotos_numero();
            }else {
                deltaTemp = deltaAngle * p.getElectos();
                if (p.getElectos() == 0)
                    break;
            }

            canvas.drawArc(new RectF(x, y, x + diameter, y + diameter), startAngle, deltaTemp, true, paint);
            startAngle += deltaTemp;
        }

        paint.setColor(Color.WHITE);

        int j = WIDTH / 4;
        canvas.drawArc(new RectF(x+j, y+j, x + diameter -j, y + diameter -j), -180, 180, true, paint);


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