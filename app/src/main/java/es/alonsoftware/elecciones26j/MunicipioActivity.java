package es.alonsoftware.elecciones26j;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

import es.alonsoftware.elecciones26j.dao.Lugar;
import es.alonsoftware.elecciones26j.fragment.Default2Fragment;
import es.alonsoftware.elecciones26j.fragment.DefaultFragment;

public class MunicipioActivity extends AppCompatActivity {


    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    private String nombre = "", id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        nombre = this.getIntent().getExtras().getString("nombre");
        id = this.getIntent().getExtras().getString("id");
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());


        setTitle(nombre);
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        Date d = new Date();
        if (d.getTime() < 1466971200000L) {
            mViewPager.setCurrentItem(3);
            Toast.makeText(this, "Datos de 2016 no disponibles hasta el 26-J a las 20:00 horas", Toast.LENGTH_LONG).show();
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            startActivity(new Intent(getApplicationContext(), AboutActivity.class));
            return true;
        }else if (id == R.id.action_buscar)
            mViewPager.setCurrentItem(5);
        else if (id == R.id.action_memes)
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="+"es.alonsoftware.memespoliticos")));
        else if (id == R.id.action_pactometro)
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="+"es.alonsoftware.memespoliticos")));
        else if (id == R.id.action_share){
            ProgressDialog dialog = ProgressDialog.show(MunicipioActivity.this, "","Loading. Please wait...", true);

            try {

                File imageFile = new File(getCacheDir(), "documents/image.jpg");
                final File parentDir = imageFile.getParentFile();

                if ((parentDir != null) && !parentDir.exists())
                    parentDir.mkdirs();

                final Uri uri = FileProvider.getUriForFile(MunicipioActivity.this, getPackageName(), imageFile);

                View v1 = getWindow().getDecorView();
                v1.setDrawingCacheEnabled(true);
                Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
                v1.setDrawingCacheEnabled(false);
                FileOutputStream outputStream = new FileOutputStream(imageFile);
                int quality = 100;
                bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
                outputStream.flush();
                outputStream.close();

                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("image/*");
                sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);
                sharingIntent.putExtra(Intent.EXTRA_TEXT, "Elecciones 26-J en tu móvil http://bit.ly/ele26j");
                sharingIntent
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET)
                        .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                dialog.dismiss();
                startActivity(Intent.createChooser(sharingIntent, "Compartir"));

            } catch (Throwable e) {
                // Several error may come out with file handling or OOM
                e.printStackTrace();
            }
        }


        return super.onOptionsItemSelected(item);
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position){
                case 0:
                    return DefaultFragment.newInstance(new Lugar(nombre, id, Lugar.CONGRESO, 2016), true);
                case 1:
                    return DefaultFragment.newInstance(new Lugar(nombre, id, Lugar.SENADO, 2016), true);
                case 2:
                    return Default2Fragment.newInstance(new Lugar(nombre, id, Lugar.CONGRESO, 2016),new Lugar(nombre, id, Lugar.CONGRESO, 2015), true);
                case 3:
                    return DefaultFragment.newInstance(new Lugar(nombre, id, Lugar.CONGRESO, 2015), true);
                case 4:
                    return DefaultFragment.newInstance(new Lugar(nombre, id, Lugar.SENADO, 2015), true);
                case 5:
                    return Default2Fragment.newInstance(new Lugar(nombre, id, Lugar.CONGRESO, 2015),new Lugar(nombre, id, Lugar.CONGRESO, 2011), true);
                case 6:
                    return Default2Fragment.newInstance(new Lugar(nombre, id, Lugar.CONGRESO, 2016),new Lugar(nombre, id, Lugar.CONGRESO, 2011), true);
                default:
                    return DefaultFragment.newInstance(new Lugar(nombre, id, Lugar.SENADO, 2011), true);

            }

        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return getResources().getStringArray(R.array.menu2).length;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            return getResources().getStringArray(R.array.menu2)[position];
        }
    }
}
