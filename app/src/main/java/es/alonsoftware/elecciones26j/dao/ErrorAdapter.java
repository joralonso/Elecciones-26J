package es.alonsoftware.elecciones26j.dao;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import es.alonsoftware.elecciones26j.R;


/**
 * Created by Jorge on 20/11/15.
 */
public class ErrorAdapter extends BaseAdapter {


    public ErrorAdapter(){
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public Object getItem(int position) {
            return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return View.inflate(parent.getContext(), R.layout.view_error, null);
    }
}
