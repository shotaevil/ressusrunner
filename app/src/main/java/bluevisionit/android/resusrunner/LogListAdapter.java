package bluevisionit.android.resusrunner;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

/**
 * Created by tzimonjic on 4/28/16.
 */
public class LogListAdapter extends BaseAdapter {
    Context context;
    ArrayList<Log> logs = new ArrayList<Log>();
    public LogListAdapter(Context context, ArrayList<Log> logs){
        this.context = context;
        this.logs = logs;
    }

    @Override
    public int getCount() {
        return logs.size();
    }

    @Override
    public Log getItem(int i) {
        return logs.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View rowView = LayoutInflater.from(context).
                inflate(R.layout.log_list_item, viewGroup, false);

        TextView tv_event = (TextView) rowView.findViewById(R.id.tv_event);
        TextView tv_real_time = (TextView) rowView.findViewById(R.id.tv_real_time);
        TextView tv_codetime = (TextView) rowView.findViewById(R.id.tv_code_time);

        Typeface font_bold = Typeface.createFromAsset(context.getAssets(),"font/brandon_bld.otf");

        tv_event.setTypeface(font_bold);
        tv_codetime.setTypeface(font_bold);
        tv_real_time.setTypeface(font_bold);

        tv_event.setText(logs.get(i).event);
        tv_codetime.setText(logs.get(i).code_time);
        tv_real_time.setText(logs.get(i).real_time);
        return rowView;
    }
}
