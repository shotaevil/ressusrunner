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
public class SubheadingListAdapter extends BaseAdapter {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_SEPARATOR = 1;

    private ArrayList<String> mData = new ArrayList<String>();
    private TreeSet<Integer> sectionHeader = new TreeSet<Integer>();
    Typeface font_bold;
    Typeface font_regular;
    private LayoutInflater mInflater;
    Context mContext;
    String[] mEntries;
    List<String> mHeadings;
    public SubheadingListAdapter(Context context, String[] items) {
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mContext = context;
        for(int i=0; i<items.length; i++) {
            if (items[i].equals("AIRWAY DEVICES") ||
                    items[i].equals("LAB TESTS") ||
                    items[i].equals("VENTILATOR SETTINGS") ||
                    items[i].equals("TESTS") ||
                    items[i].equals("LINES") ||
                    items[i].equals("MEDICATIONS") ||
                    items[i].equals("GCS") ||
                    items[i].equals("AVPU") ||
                    items[i].equals("EYES") ||
                    items[i].equals("REFLEXES") ||
                    items[i].equals("COMA & SEIZURE"))
                addSectionHeaderItem(items[i]);
            else
                addItem(items[i]);
        }
        font_bold = Typeface.createFromAsset(context.getAssets(),"font/brandon_bld.otf");
        font_regular = Typeface.createFromAsset(context.getAssets(),"font/brandon_med.otf");
    }

    public void addItem(final String item) {
        mData.add(item);
        notifyDataSetChanged();
    }

    public void addSectionHeaderItem(final String item) {
        mData.add(item);
        sectionHeader.add(mData.size() - 1);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return sectionHeader.contains(position) ? TYPE_SEPARATOR : TYPE_ITEM;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public String getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        int rowType = getItemViewType(position);

        if (convertView == null) {
            holder = new ViewHolder();
            switch (rowType) {
                case TYPE_ITEM:
                    convertView = mInflater.inflate(R.layout.list_item, null);
                    holder.textView = (TextView) convertView.findViewById(R.id.text);
                    holder.textView.setTypeface(font_regular);
                    holder.check = (ImageView)convertView.findViewById(R.id.check);
                    break;
                case TYPE_SEPARATOR:
                    convertView = mInflater.inflate(R.layout.heading_list_line, null);
                    holder.textView = (TextView) convertView.findViewById(R.id.list_heading);
                    holder.textView.setTypeface(font_bold);
                    break;
            }
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textView.setText(mData.get(position));
//        if(mData.get(position).equals("Other"))
//            holder.textView.setTextColor(mContext.getResources().getColor(R.color.blue));

        return convertView;
    }



    public static class ViewHolder {
        public TextView textView;
        public ImageView check;
    }

}
