package bluevisionit.android.resusrunner;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by tzimonjic on 4/24/16.
 */
public class MainActivity extends FragmentActivity {
    Button end_code_bt;
    private Typeface font_bold;
    private Typeface font_super_bold;
    private Typeface font_regular;
    private Typeface font_thin;
    private TextView label_temperature;
    private EditText edit_temperature;
    private TextView label_pulse;
    private EditText edit_pulse;
    private TextView label_respiration;
    private TextView label_gcs;
    private EditText edit_gcs;
    private TextView label_saturations;
    private EditText edit_saturations;
    private TextView label_blood;
    private EditText edit_blood;
    private TextView label_rhytmn;
    private EditText edit_rhytmn;
    private TextView label_drug;
    private EditText edit_drug;
    private EditText edit_respiration;
    private TextView label_start_code;
    private TextView start_timer;
    //    Timer t;
    private long startTime = 0L;
    private long cprStartTime = 0L;
    public String[] letters = {"A", "B", "C", "D", "E", "F", "CODE END"};
    private Handler customHandler = new Handler();
    private Handler cprCustomHandler = new Handler();
    private Handler cprGive2BreathsHandler = new Handler();
    private Handler countDownHandler = new Handler();
    int give2breathsCounter = 0;

    EditText middleSearch;
    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;
    long cprTimeInMilliseconds = 0L;
    long cprTimeSwapBuff = 0L;
    long cprUpdatedTime = 0L;
    long countDownTimeInMilliseconds = 10000;

    private TextView start_cpr_timer;
    private TextView tv_start_cpr;
    private TextView tv_pulse_no;
    private TextView tv_shock_no;
    ListView middleList;
    TextView listTitle;
    SubheadingListAdapter middleListAdapter;
    String[] middleListItems;
    private MediaPlayer metronome;
    private MediaPlayer start_cpr;
    private MediaPlayer give2breaths;
    private MediaPlayer cprStop;
    ReportCycle cycle = new ReportCycle();
    private int checkPulseCounter = 0;
    private AlphaAnimation mAnimation;
    private InputMethodManager imm;
    ImageView checkPulseIcon;
    private long breathsDuration;
    private ImageView shockIcon;
    private TextView bottombarMessage;
    ListView sideMenu;
    DrawerLayout mDrawerLayout;
    private EditText edit_joules;
    private EditText edit_rate;
    private EditText edit_mamp;
    private PopupWindow popup;
    private ImageView drug_icon;
    private boolean isShockButtonActive = false;
    private LinearLayout bradycardia_layout;
    private LinearLayout tachycardia_layout;
    private TextView rhytmn_vt;
    private TextView rhytmn_pea;
    private TextView rhytmn_bradycardia;
    private TextView rhytmn_tachycardia;
    private TextView rhytmn_stemi;
    private ListView log_list;
    private LogListAdapter logListAdapter;
    private EditText logSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        font_bold = Typeface.createFromAsset(getAssets(),"font/brandon_bld.otf");
        font_super_bold = Typeface.createFromAsset(getAssets(),"font/brandon_blk.otf");
        font_regular = Typeface.createFromAsset(getAssets(),"font/brandon_med.otf");
        font_thin = Typeface.createFromAsset(getAssets(),"font/brandon_light.otf");

        end_code_bt = (Button)findViewById(R.id.end_code_bt);
        label_temperature = (TextView)findViewById(R.id.label_temperature);
        edit_temperature = (EditText)findViewById(R.id.edit_temperature);
        label_pulse = (TextView)findViewById(R.id.label_pulse);
        edit_pulse = (EditText)findViewById(R.id.edit_pulse);
        label_respiration = (TextView)findViewById(R.id.label_respiration);
        edit_respiration = (EditText)findViewById(R.id.edit_respiration);
        label_gcs = (TextView)findViewById(R.id.label_gcs);
        edit_gcs = (EditText)findViewById(R.id.edit_gcs);
        label_saturations = (TextView)findViewById(R.id.label_saturations);
        edit_saturations = (EditText)findViewById(R.id.edit_saturations);
        label_blood = (TextView)findViewById(R.id.label_blood);
        edit_blood = (EditText)findViewById(R.id.edit_blood);
        label_rhytmn = (TextView)findViewById(R.id.label_rhytmn);
//        label_drug = (TextView)findViewById(R.id.label_drug);
//        edit_drug = (EditText)findViewById(R.id.edit_drug);
        label_start_code = (TextView)findViewById(R.id.tv_start_code);
        start_timer = (TextView)findViewById(R.id.start_timer);
        start_cpr_timer = (TextView)findViewById(R.id.start_cpr_timer);
        tv_start_cpr = (TextView)findViewById(R.id.tv_start_cpr);
        tv_pulse_no = (TextView)findViewById(R.id.tv_pulse_no);
        tv_shock_no = (TextView)findViewById(R.id.tv_shock_no);
        sideMenu = (ListView) findViewById(R.id.side_menu);
        checkPulseIcon = (ImageView)findViewById(R.id.check_pulse_icon);
        shockIcon = (ImageView) findViewById(R.id.shock_icon);
        bottombarMessage = (TextView)findViewById(R.id.bottombar_message);
        drug_icon = (ImageView)findViewById(R.id.drug_icon);

        end_code_bt.setTypeface(font_super_bold);
        label_temperature.setTypeface(font_regular);
        edit_temperature.setTypeface(font_super_bold);
        label_pulse.setTypeface(font_regular);
        edit_pulse.setTypeface(font_super_bold);
        label_respiration.setTypeface(font_regular);
        edit_respiration.setTypeface(font_super_bold);
        label_gcs.setTypeface(font_regular);
        edit_gcs.setTypeface(font_super_bold);
        label_saturations.setTypeface(font_regular);
        edit_saturations.setTypeface(font_super_bold);
        label_blood.setTypeface(font_regular);
        edit_blood.setTypeface(font_super_bold);
        label_rhytmn.setTypeface(font_regular);
        bottombarMessage.setTypeface(font_regular);
//        label_drug.setTypeface(font_regular);
//        edit_drug.setTypeface(font_super_bold);
        label_start_code.setTypeface(font_bold);
        start_timer.setTypeface(font_super_bold);
        tv_start_cpr.setTypeface(font_bold);
        start_cpr_timer.setTypeface(font_super_bold);
        tv_pulse_no.setTypeface(font_super_bold);
        tv_shock_no.setTypeface(font_super_bold);

        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        cprStop = MediaPlayer.create(MainActivity.this, R.raw.cprstop);
        give2breaths = MediaPlayer.create(MainActivity.this, R.raw.give_two_breaths);
        start_cpr = MediaPlayer.create(MainActivity.this, R.raw.cprbegin);
        metronome = MediaPlayer.create(MainActivity.this, R.raw.metronome);
        breathsDuration = (long)give2breaths.getDuration();
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

        setUpLetterMenuWithList();
        setUpLists();
        setEditTextListeners();


    }

    private void setEditTextListeners() {
        logSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (keyEvent != null&& (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                }
                return false;
            }
        });
        middleSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (keyEvent != null&& (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                }
                return false;
            }
        });
        edit_temperature.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (keyEvent != null&& (keyEvent.getKeyCode() == KeyEvent.ACTION_UP)) {
                    edit_pulse.requestFocus();
                }
                return false;
            }
        });
        edit_pulse.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (keyEvent != null&& (keyEvent.getKeyCode() == KeyEvent.ACTION_UP)) {
                    edit_respiration.requestFocus();
                }
                return false;
            }
        });
        edit_respiration.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (keyEvent != null&& (keyEvent.getKeyCode() == KeyEvent.ACTION_UP)) {
                    edit_gcs.requestFocus();
                }
                return false;
            }
        });
        edit_gcs.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (keyEvent != null&& (keyEvent.getKeyCode() == KeyEvent.ACTION_UP)) {
                    edit_saturations.requestFocus();
                }
                return false;
            }
        });
        edit_saturations.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (keyEvent != null&& (keyEvent.getKeyCode() == KeyEvent.ACTION_UP)) {
                    edit_blood.requestFocus();
                }
                return false;
            }
        });
        edit_blood.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (keyEvent != null&& (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    InputMethodManager imm = (InputMethodManager)edit_blood.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(edit_blood.getWindowToken(), 0);
                }
                return false;
            }
        });
    }

    public void SetFocusOnTemperature(View v){
        edit_temperature.requestFocus();
        imm.showSoftInput(edit_temperature, InputMethodManager.SHOW_IMPLICIT);
      /*  edit_temperature.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (keyEvent != null&& (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    edit_pulse.requestFocus();
//                    imm.hideSoftInputFromWindow(edit_temperature.getApplicationWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
                    return true;
                }
                return false;
            }
        });*/
    }

    public void SetFocusOnPulse(View v){
        edit_pulse.requestFocus();
        imm.showSoftInput(edit_pulse, InputMethodManager.SHOW_IMPLICIT);
       /* edit_pulse.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (keyEvent != null&& (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
//                    imm.hideSoftInputFromWindow(edit_pulse.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    edit_respiration.requestFocus();
                    return true;
                }
                return false;
            }
        });*/
    }

    public void SetFocusOnRespiration(View v){
        edit_respiration.requestFocus();
        imm.showSoftInput(edit_respiration, InputMethodManager.SHOW_IMPLICIT);
       /* edit_respiration.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (keyEvent != null&& (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
//                    imm.hideSoftInputFromWindow(edit_respiration.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    edit_gcs.requestFocus();
                    return true;
                }
                return false;
            }
        });*/
    }

    public void SetFocusOnGCS(View v){
        edit_gcs.requestFocus();
        imm.showSoftInput(edit_gcs, InputMethodManager.SHOW_IMPLICIT);
       /* edit_gcs.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (keyEvent != null&& (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    //imm.hideSoftInputFromWindow(edit_gcs.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    edit_saturations.requestFocus();
                    return true;
                }
                return false;
            }
        });*/
    }

    public void SetFocusOnSaturations(View v){
        edit_saturations.requestFocus();
        imm.showSoftInput(edit_saturations, InputMethodManager.SHOW_IMPLICIT);
       /* edit_saturations.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (keyEvent != null&& (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    edit_blood.requestFocus();
//                    imm.hideSoftInputFromWindow(edit_saturations.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    return true;
                }
                return false;
            }
        });*/
    }

    public void SetFocusOnBloodPressure(View v){
        edit_blood.requestFocus();
        imm.showSoftInput(edit_blood, InputMethodManager.SHOW_IMPLICIT);
        /*edit_blood.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (keyEvent != null&& (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    imm.hideSoftInputFromWindow(edit_blood.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    return true;
                }
                return false;
            }
        });*/
    }

    private void setUpLists() {
        sideMenu.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.side_menu_list_items)){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getView(position, convertView, parent);

                textView.setTextColor(Color.WHITE);
                textView.setTypeface(font_bold);

                return textView;
            }
        });
        sideMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(sideMenu.getItemAtPosition(i).toString().equalsIgnoreCase("exit")){
                    MainActivity.this.finish();
                }


            }
        });
        setUpResourcesList();
        setUpActiveLetter("A");
        setUpMiddleList("A");
        setUpLogList();
    }

    private void setUpLogList() {
        LinearLayout lists = (LinearLayout)findViewById(R.id.lists);
        Display display = getWindowManager().getDefaultDisplay();
        final int screenWidth = display.getWidth();
        LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = vi.inflate(R.layout.log_list, null);
        LinearLayout lv = (LinearLayout) view.findViewById(R.id.layout_log_list);
        TextView listTitle = (TextView)view.findViewById(R.id.list_title);
        TextView event_heading = (TextView)view.findViewById(R.id.event_heading);
        TextView code_heading = (TextView)view.findViewById(R.id.code_time_heading);
        TextView real_heading = (TextView)view.findViewById(R.id.real_time_heading);
        logSearch = (EditText)view.findViewById(R.id.log_search);

        log_list = (ListView)view.findViewById(R.id.log_listview);
        logListAdapter = new LogListAdapter(MainActivity.this, Variables.log);
        log_list.setAdapter(logListAdapter);

        listTitle.setTypeface(font_regular);
        event_heading.setTypeface(font_regular);
        code_heading.setTypeface(font_regular);
        real_heading.setTypeface(font_regular);

        listTitle.setText("LOG");
        int newWidth = (int)(screenWidth/3)-20;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(newWidth, LinearLayout.LayoutParams.FILL_PARENT);
        lv.setLayoutParams(params);
        lists.addView(lv);

        searchLogList();
    }

    private void setUpResourcesList() {
        LinearLayout lists = (LinearLayout)findViewById(R.id.lists);
        Display display = getWindowManager().getDefaultDisplay();
        final int screenWidth = display.getWidth();
        LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = vi.inflate(R.layout.resources_list, null);
        LinearLayout lv = (LinearLayout) view.findViewById(R.id.layout_resources_list);
        TextView listTitle = (TextView)view.findViewById(R.id.list_title);
        listTitle.setTypeface(font_regular);
        int newWidth = (int)(screenWidth/3)-20;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(newWidth, LinearLayout.LayoutParams.FILL_PARENT);
        lv.setLayoutParams(params);
        lists.addView(lv);
    }

    private void setUpMiddleList(String letterChoose) {
        LinearLayout lists = (LinearLayout)findViewById(R.id.lists);
        Display display = getWindowManager().getDefaultDisplay();
        final int screenWidth = display.getWidth();
        int newWidth = (int)(screenWidth/3)-20;
        LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = vi.inflate(R.layout.letter_list, null);
        LinearLayout lv = (LinearLayout) view.findViewById(R.id.layout_middle_list);
        LinearLayout lv2 = (LinearLayout) view.findViewById(R.id.layout_further_interventions_list);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(newWidth, LinearLayout.LayoutParams.FILL_PARENT);

        middleSearch = (EditText)view.findViewById(R.id.middle_search);
        listTitle = (TextView)view.findViewById(R.id.list_title);
        middleList = (ListView)view.findViewById(R.id.middle_list);
        listTitle.setTypeface(font_regular);
        updateMiddleList(letterChoose);
        params.setMargins(5,0,5,0);
        lv.setLayoutParams(params);
        lists.addView(lv);
//        if(letterChoose.equals("F")){
//            lv2.setVisibility(View.VISIBLE);
//        }
//        else {
//            lv2.setVisibility(View.GONE);
//        }
        searchMiddleList();
    }

    private void searchMiddleList(){
        middleSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                List<String> filterList = new ArrayList<String>();
                for(int j=0; j<middleListItems.length; j++){
                    if(middleListItems[j].toString().toLowerCase().contains(charSequence.toString().toLowerCase()))
                        filterList.add(middleListItems[j]);
                }
                String[] newArr = new String[filterList.size()];
                newArr = filterList.toArray(newArr);


                middleListAdapter = new SubheadingListAdapter(MainActivity.this, newArr);
                middleList.setAdapter(middleListAdapter);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    private void searchLogList(){
        logSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ArrayList<bluevisionit.android.resusrunner.Log> filterList = new ArrayList<bluevisionit.android.resusrunner.Log>();
                for(int j=0; j<Variables.log.size(); j++){
                    if(Variables.log.get(j).event.toLowerCase().contains(charSequence.toString().toLowerCase()))
                        filterList.add(Variables.log.get(j));
                }
                if(filterList.size()==Variables.log.size())
                    logListAdapter = new LogListAdapter(MainActivity.this, Variables.log);
                else
                    logListAdapter = new LogListAdapter(MainActivity.this, filterList);
                log_list.setAdapter(logListAdapter);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    private void updateMiddleList(String letterChoose) {
        if(letterChoose.equals("A"))
            listTitle.setText(getResources().getString(R.string.airway));
        if(letterChoose.equals("B"))
            listTitle.setText(getResources().getString(R.string.breathing));
        if(letterChoose.equals("C"))
            listTitle.setText(getResources().getString(R.string.circulation));
        if(letterChoose.equals("D"))
            listTitle.setText(getResources().getString(R.string.disability));
        if(letterChoose.equals("E"))
            listTitle.setText(getResources().getString(R.string.exposure));
        if(letterChoose.equals("F"))
            listTitle.setText(getResources().getString(R.string.further_interventions));

        middleListAdapter = getAdapter(letterChoose);
        middleList.setAdapter(middleListAdapter);
        middleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = middleList.getItemAtPosition(i).toString();
                if(item.equals("AIRWAY DEVICES") ||
                        item.equals("LAB TESTS") ||
                        item.equals("VENTILATOR SETTINGS") ||
                        item.equals("TESTS") ||
                        item.equals("LINES") ||
                        item.equals("MEDICATIONS") ||
                        item.equals("GCS") ||
                        item.equals("AVPU") ||
                        item.equals("EYES") ||
                        item.equals("REFLEXES") ||
                        item.equals("COMA & SEIZURE") )
                    Log.v("Item clicked", "heading");
                else
                    Log.v("Item clicked", middleList.getItemAtPosition(i).toString());

            }
        });
    }

    private SubheadingListAdapter getAdapter(String letter) {
        middleListItems = new String[0];
        if(letter.equals("A"))
            middleListItems = getResources().getStringArray(R.array.airway_entries);
        if(letter.equals("B"))
            middleListItems = getResources().getStringArray(R.array.breathing_entries);
        if(letter.equals("C"))
            middleListItems = getResources().getStringArray(R.array.circulation_entries);
        if(letter.equals("D"))
            middleListItems = getResources().getStringArray(R.array.disability_entries);
        if(letter.equals("E"))
            middleListItems = getResources().getStringArray(R.array.exposure_entries);
        if(letter.equals("F"))
            middleListItems = getResources().getStringArray(R.array.further_intervensions_entries);
//        for(int i=0; i<items.length; i++){
//            if(items[i].equals("AIRWAY DEVICES") ||
//                    items[i].equals("LAB TESTS") ||
//                    items[i].equals("VENTILATOR SETTINGS") ||
//                    items[i].equals("TESTS") ||
//                    items[i].equals("LINES") ||
//                    items[i].equals("MEDICATIONS") ||
//                    items[i].equals("GCS") ||
//                    items[i].equals("AVPU") ||
//                    items[i].equals("EYES") ||
//                    items[i].equals("REFLEXES") ||
//                    items[i].equals("COMA & SEIZURE") )
//                adapter.addSectionHeaderItem(items[i]);
//            else
//                adapter.addItem(items[i]);
//        }
        SubheadingListAdapter adapter = new SubheadingListAdapter(MainActivity.this, middleListItems);
        return adapter;
    }

    private void setUpLetterMenuWithList() {
        LinearLayout letter_layout = (LinearLayout)findViewById(R.id.letters);
        Display display = getWindowManager().getDefaultDisplay();
        final int screenWidth = display.getWidth();
        for(int i=0; i<letters.length; i++){
            LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = vi.inflate(R.layout.letter, null);
            LinearLayout lv = (LinearLayout) view.findViewById(R.id.layout_letter);
            int newWidth = (int)(screenWidth/7)-20;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(newWidth, LinearLayout.LayoutParams.WRAP_CONTENT);
            View activity_line = (View)view.findViewById(R.id.active_line);
            TextView tv = (TextView)view.findViewById(R.id.tv_letter);
            lv.setLayoutParams(params);
            int tvID = i+1;
            int lineID = i+8;
            if(i==6){
                int newWidth2 = (int)(screenWidth/5);
                LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(newWidth2, LinearLayout.LayoutParams.WRAP_CONTENT);
                lv.setLayoutParams(params2);
            }
            tv.setText(letters[i]);
            tv.setId(tvID);
            activity_line.setId(lineID);
            tv.setTypeface(font_thin);
            tv.setTextColor(getResources().getColor(R.color.dark_blue_main));
            activity_line.setVisibility(View.INVISIBLE);
            setOnLetterClickListener(lv, tv, activity_line, i);
            letter_layout.addView(view);
        }

    }

    private void setUpActiveLetter(String letter){
        int[] ids = {1,2,3,4,5,6,7};
        int[] imageIds = {8,9,10,11,12,13,14};
        TextView tv;
        View line;
        for(int i=0; i<ids.length; i++){
            line = (View)findViewById(imageIds[i]);
            tv = (TextView)findViewById(ids[i]);
            if(!letter.equals(tv.getText().toString())){
                line.setVisibility(View.INVISIBLE);
                tv.setTextColor(getResources().getColor(R.color.dark_blue_main));
            }else{
                line.setVisibility(View.VISIBLE);
                tv.setTextColor(Color.WHITE);
            }
        }
    }

    private void setOnLetterClickListener(LinearLayout lv, final TextView tv, final View activity_line, final int position) {
        lv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (activity_line.getVisibility() == View.INVISIBLE) {
                    if(letters[position].equals("CODE END")){
                        Log.v("CODE END", "true");
                        tv.setTextColor(Color.WHITE);
                        activity_line.setVisibility(View.INVISIBLE);
                    }else {
                        updateMiddleList(tv.getText().toString());
                        tv.setTextColor(Color.WHITE);
                        activity_line.setVisibility(View.VISIBLE);
                    }
                }
                setOtherLettersInactive(position);
            }
        });
    }

    private void setOtherLettersInactive(int position) {
        int[] ids = {1,2,3,4,5,6,7};
        int[] imageIds = {8,9,10,11,12,13,14};
        TextView tv;
        View line;
        int pos = position+1;
        for(int i=0; i<ids.length; i++){
            line = (View)findViewById(imageIds[i]);
            tv = (TextView)findViewById(ids[i]);
            if(pos!=ids[i]){
                line.setVisibility(View.INVISIBLE);
                tv.setTextColor(getResources().getColor(R.color.dark_blue_main));
            }else{
                line.setVisibility(View.VISIBLE);
                tv.setTextColor(Color.WHITE);
            }
        }
    }

    public static String intToString(int num, int digits) {
        String output = Integer.toString(num);
        while (output.length() < digits) output = "0" + output;
        return output;
    }

    private Runnable updateTimerThread = new Runnable() {

        public void run() {

            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;

            updatedTime = timeSwapBuff + timeInMilliseconds;

            int secs = (int) (updatedTime / 1000);
            int mins = secs / 60;
            int hours = (int)(mins/60);
            secs = secs % 60;
            //int milliseconds = (int) (updatedTime % 1000);
            start_timer.setText(""+String.format("%02d", hours)+":" + String.format("%02d", mins) + ":"
                    + String.format("%02d", secs));
            customHandler.postDelayed(this, 0);
        }

    };

    private Runnable give2breathsThread = new Runnable() {
        @Override
        public void run() {
            give2breathsCounter++;
            metronome.pause();
            give2breaths.start();
            cprGive2BreathsHandler.removeCallbacks(this);
            give2breaths.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    metronome.start();
                    if(give2breathsCounter<5) {
                        cprGive2BreathsHandler.postDelayed(give2breathsThread, 18000);
                    }else{
                        CheckPulse();
                    }
                }
            });
        }
    };

    private void CheckPulse() {
        checkPulseCounter++;
        String pauseTime = start_cpr_timer.getText().toString();
        cycle.cprPauseTimes.add(pauseTime);
        addLogToList(setLog("Check Pulse "+checkPulseCounter));
//        Log.v("Cycle - Pause", cycle.cprPauseTimes.get(0));
        metronome.pause();
        cprStop.start();
        cprCustomHandler.removeCallbacks(updateCPRTimerThread);
        checkPulseIcon.setImageDrawable(getResources().getDrawable(R.drawable.check_pulse_active));
        checkPulseIcon.setAnimation(mAnimation);
        tv_pulse_no.setText(""+checkPulseCounter);
        Toast.makeText(MainActivity.this,"Check Pulse", Toast.LENGTH_LONG).show();
        countDownTimeInMilliseconds = 10000;
        countDownHandler.postDelayed(countDownCPRTimerThread,1000);
    }

    private Runnable updateCPRTimerThread = new Runnable() {

        public void run() {

            cprUpdatedTime = cprTimeSwapBuff + cprTimeInMilliseconds;
            cprTimeInMilliseconds = SystemClock.uptimeMillis() - cprStartTime;

            int secs = (int) TimeUnit.MILLISECONDS.toSeconds(cprTimeInMilliseconds);
            int mins = (int) TimeUnit.MILLISECONDS.toMinutes(cprTimeInMilliseconds);
            int hours = (int) TimeUnit.MILLISECONDS.toHours(cprTimeInMilliseconds);
            secs = secs % 60;
            start_cpr_timer.setText("" + String.format("%02d", hours) + ":" + String.format("%02d", mins) + ":"
                    + String.format("%02d", secs));
            cprCustomHandler.postDelayed(this, 1000);
        }
    };

    private Runnable countDownCPRTimerThread = new Runnable() {

        public void run() {
            if(countDownTimeInMilliseconds>0L) {
                countDownTimeInMilliseconds = countDownTimeInMilliseconds - 1000;

                int secs = (int) TimeUnit.MILLISECONDS.toSeconds(countDownTimeInMilliseconds);
                int mins = (int) TimeUnit.MILLISECONDS.toMinutes(countDownTimeInMilliseconds);
                int hours = (int) TimeUnit.MILLISECONDS.toHours(countDownTimeInMilliseconds);
                secs = secs % 60;
                start_cpr_timer.setText("" + String.format("%02d", hours) + ":" + String.format("%02d", mins) + ":"
                        + String.format("%02d", secs));
                countDownHandler.postDelayed(this, 1000);
            }else {
                countDownHandler.removeCallbacks(this);
                tv_start_cpr.startAnimation(mAnimation);
                tv_start_cpr.setClickable(true);
            }
        }
    };

    public String parseCalendarDate(long time){
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return formatter.format(calendar.getTime());
    }

    public void StartCodeTimer(View v){
        Calendar startCycle = Calendar.getInstance();
        cycle.startSystemDateTime = parseCalendarDate(startCycle.getTimeInMillis());
        if(start_timer.getText().equals("00:00:00")){
            startTime = SystemClock.uptimeMillis();
            customHandler.postDelayed(updateTimerThread, 0);
        }
        setUpAnimation();
        tv_start_cpr.startAnimation(mAnimation);
        tv_start_cpr.setClickable(true);
    }

    private void setUpAnimation() {
        mAnimation = new AlphaAnimation(1, 0);
        mAnimation.setDuration(800);
        mAnimation.setInterpolator(new LinearInterpolator());
        mAnimation.setRepeatCount(Animation.INFINITE);
        mAnimation.setRepeatMode(Animation.REVERSE);
    }

    public void StartCPRCodeTimer(View v){
        v.clearAnimation();
        tv_start_cpr.setClickable(false);
        give2breathsCounter = 0;
        checkPulseIcon.setImageDrawable(getResources().getDrawable(R.drawable.pulse));
        checkPulseIcon.clearAnimation();
        cycle.startCPR = start_timer.getText().toString();
        Log.v("Cycle - startCPR", cycle.startCPR);
        if(start_timer.getText().equals("00:00:00")){
            Toast.makeText(MainActivity.this, "Start Code first", Toast.LENGTH_SHORT).show();
        }else{
            addLogToList(setLog("Start CPR"));
            start_cpr.start();
            start_cpr.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    metronome.start();
                    if(start_cpr_timer.getText().equals("00:00:00")){
                        cprStartTime = SystemClock.uptimeMillis();
                        cprCustomHandler.postDelayed(updateCPRTimerThread, 1000);
                        cprGive2BreathsHandler.postDelayed(give2breathsThread,18000);
                    }
                }
            });
        }
    }

    private bluevisionit.android.resusrunner.Log setLog(String event) {
        bluevisionit.android.resusrunner.Log log = new bluevisionit.android.resusrunner.Log();
        log.code_time = start_timer.getText().toString();
        log.real_time = getCurrentTime();
        log.event = event;
        Log.v("Log", log.code_time+","+log.real_time+","+log.event);
        return log;
    }

    public void RestartCPRCodeTimer (View v){
        addLogToList(setLog("Restart CPR"));
        if(start_cpr.isPlaying()){
            start_cpr.pause();
        }
        if(metronome.isPlaying()){
            metronome.pause();
        }
        start_cpr_timer.setText("00:00:00");
        cprCustomHandler.removeCallbacks(updateCPRTimerThread);
        start_cpr.start();
        start_cpr.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                cprStartTime = SystemClock.uptimeMillis();;
                cprCustomHandler.postDelayed(updateCPRTimerThread, 0);

                //metronome = MediaPlayer.create(MainActivity.this, R.raw.metronome);
                metronome.start();
            }
        });
    }

    public void PauseCprCodeTimer(View v){
        if(metronome.isPlaying()) {
            metronome.pause();
        }
        addLogToList(setLog("Pause CPR"));
        cprCustomHandler.removeCallbacks(updateCPRTimerThread);
        cprGive2BreathsHandler.removeCallbacks(give2breathsThread);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LinearLayout submenu = (LinearLayout)findViewById(R.id.submenu_layout);
        submenu.requestFocus();
        submenu.clearFocus();
    }
    public void ConfirmShock(View v){
        Variables.shock.joules = edit_joules.getText().toString();
        Variables.shock.rate = edit_rate.getText().toString();
        Variables.shock.mAmp = edit_mamp.getText().toString();
        popup.dismiss();
        String msg = "";
        if(!Variables.shock.joules.equals("")){
            msg = "Joules: "+Variables.shock.joules+" ";
        }
        if(!Variables.shock.rate.equals("")){
            msg = msg+"Rate: "+Variables.shock.rate+" ";
        }
        if(!Variables.shock.mAmp.equals("")){
            msg = msg+"mAmp: "+Variables.shock.mAmp+" ";
        }
        if(!msg.equals(""))
            addLogToList(setLog("Shock "+tv_shock_no.getText().toString()+": "+msg));
        shockIcon.setImageDrawable(getResources().getDrawable(R.drawable.shock));
    }

    private void setShockPopOutMenuTypeface(View layout){
        TextView tv_joules = (TextView) layout.findViewById(R.id.tv_joules);
        TextView tv_rate = (TextView) layout.findViewById(R.id.tv_rate);
        TextView tv_mamp = (TextView) layout.findViewById(R.id.tv_mamp);
        tv_joules.setTypeface(font_bold);
        tv_rate.setTypeface(font_bold);
        tv_mamp.setTypeface(font_bold);
    }

    private void setRhytmnPopOutMenuTypeface(View layout) {
        rhytmn_vt.setTypeface(font_bold);
        rhytmn_pea.setTypeface(font_bold);
        rhytmn_bradycardia.setTypeface(font_bold);
        rhytmn_tachycardia.setTypeface(font_bold);
        rhytmn_stemi.setTypeface(font_bold);
    }
    private void setPopOutMenuTypeface(View layout) {
        TextView textView1 = (TextView) layout.findViewById(R.id.textView1);
        TextView textView2 = (TextView) layout.findViewById(R.id.textView2);
        TextView textView3 = (TextView) layout.findViewById(R.id.textView3);
        TextView textView4 = (TextView) layout.findViewById(R.id.textView4);
        TextView textView5 = (TextView) layout.findViewById(R.id.textView5);
        TextView textView6 = (TextView) layout.findViewById(R.id.textView6);
        TextView textView7 = (TextView) layout.findViewById(R.id.textView7);
//        TextView textView8 = (TextView) layout.findViewById(R.id.textView8);

        textView1.setTypeface(font_bold);
        textView2.setTypeface(font_bold);
        textView3.setTypeface(font_bold);
        textView4.setTypeface(font_bold);
        textView5.setTypeface(font_bold);
        textView6.setTypeface(font_bold);
        textView7.setTypeface(font_bold);

//        textView8.setTypeface(font_bold);
    }

    public void OpenSideMenu(View v){
        // mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close)
        Log.v("SideMenu", "Open");
        mDrawerLayout.openDrawer(sideMenu);
    }

    @Override
    public void onBackPressed() {
        Variables.log = new ArrayList<bluevisionit.android.resusrunner.Log>();
        super.onBackPressed();
        if(metronome.isPlaying()){
            metronome.pause();
        }
        if(start_cpr.isPlaying()){
            start_cpr.pause();
        }
        if(give2breaths.isPlaying()){
            give2breaths.pause();
        }
        countDownHandler.removeCallbacks(countDownCPRTimerThread);
        cprCustomHandler.removeCallbacks(updateCPRTimerThread);
        customHandler.removeCallbacks(updateTimerThread);
        Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        metronome.release();
        start_cpr.release();
        give2breaths.release();
    }

    public void EndCode(View v){
        Toast.makeText(getApplicationContext(), "End Code", Toast.LENGTH_SHORT).show();
    }

    public void ShowPopUpMenu(View v){

        final Display display = getWindowManager().getDefaultDisplay();
        final Point size = new Point();
        display.getSize(size);
        final int newHeight = size.y - 180;

        final int[] location = new int[2];
        final ImageView stetoskope = (ImageView) findViewById(R.id.bt_stetoskop);
        stetoskope.getLocationOnScreen(location);

        //Initialize the Point with x, and y positions
        Point p = new Point();
        p.x = location[0];
        p.y = location[1];

        // Inflate the popup_layout.xml
        LinearLayout viewGroup = (LinearLayout)findViewById(R.id.popup);
        LayoutInflater layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View layout = layoutInflater.inflate(R.layout.popup_layout, viewGroup);

        final EditText edit_celsius = (EditText) layout.findViewById(R.id.edit_celsius);
        final EditText edit_farenheit = (EditText) layout.findViewById(R.id.edit_farenheit);
        final EditText ed_pulse = (EditText) layout.findViewById(R.id.edit_pulse);
        final EditText ed_respiration = (EditText) layout.findViewById(R.id.edit_respiration);
        final EditText ed_gcs = (EditText) layout.findViewById(R.id.edit_gcs);
        final EditText ed_saturation = (EditText) layout.findViewById(R.id.edit_saturations);
        final EditText ed_blood = (EditText) layout.findViewById(R.id.edit_blood_pressure);
        final EditText ed_rhythm = (EditText) layout.findViewById(R.id.edit_rhytmn);

        ed_pulse.setText(edit_pulse.getText().toString());
        ed_respiration.setText(edit_respiration.getText().toString());
        ed_gcs.setText(edit_gcs.getText().toString());
        ed_saturation.setText(edit_gcs.getText().toString());
        ed_blood.setText(edit_blood.getText().toString());
      //  ed_rhythm.setText(edit_rhytmn.getText().toString());

        // Creating the PopupWindow
        final PopupWindow popup = new PopupWindow();
        popup.setContentView(layout);
        popup.setWidth(200);
        popup.setHeight(newHeight);
        popup.setFocusable(true);

        // Some offset to align the popup a bit to the right, and a bit down, relative to button's position.
        int OFFSET_X = 10;
        int OFFSET_Y = 20;

        // Clear the default translucent background
        popup.setBackgroundDrawable(new BitmapDrawable());

        // Displaying the popup at the specified location, + offsets.
        popup.showAtLocation(layout, Gravity.NO_GRAVITY, p.x + OFFSET_X, p.y + OFFSET_Y);

        setPopOutMenuTypeface(layout);

        // Getting a reference to Close button, and close the popup when clicked.
        Button close = (Button) layout.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                edit_temperature.setText(edit_celsius.getText().toString()+
                        getResources().getString(R.string.celsius)+"/"+
                        edit_farenheit.getText().toString()+getResources().getString(R.string.farenheit));
                edit_pulse.setText(ed_pulse.getText().toString());
                edit_respiration.setText(ed_respiration.getText().toString());
                edit_gcs.setText(ed_gcs.getText().toString());
                edit_saturations.setText(ed_saturation.getText().toString());
                edit_blood.setText(ed_blood.getText().toString());
                edit_rhytmn.setText(ed_rhythm.getText().toString());

                popup.dismiss();
            }
        });
    }

    public void ShowShockPopUpMenu(View v){
//        if(isShockButtonActive){
//            shockIcon.setImageDrawable(getResources().getDrawable(R.drawable.shock));
//            isShockButtonActive=false;
//        }
//        else {
        shockIcon.setImageDrawable(getResources().getDrawable(R.drawable.shock_active));
//            isShockButtonActive=true;
//        }
        drug_icon.setImageDrawable(getResources().getDrawable(R.drawable.drugs));

        final Display display = getWindowManager().getDefaultDisplay();
        final Point size = new Point();
        display.getSize(size);

        final int[] location = new int[2];

        shockIcon.getLocationOnScreen(location);

        //Initialize the Point with x, and y positions
        Point p = new Point();
        p.x = location[0];
        p.y = location[1];

        // Inflate the popup_layout.xml
        LinearLayout viewGroup = (LinearLayout)findViewById(R.id.shock_popup);
        LayoutInflater layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View layout = layoutInflater.inflate(R.layout.shock_popout_layout, viewGroup);

        edit_joules = (EditText) layout.findViewById(R.id.edit_joules);
        edit_rate = (EditText) layout.findViewById(R.id.edit_rate);
        edit_mamp = (EditText) layout.findViewById(R.id.edit_mamp);

        if(Variables.shock!=null){
            edit_joules.setText(Variables.shock.joules);
            edit_rate.setText(Variables.shock.rate);
            edit_mamp.setText(Variables.shock.mAmp);
        }

        // Creating the PopupWindow
        popup = new PopupWindow();
        popup.setContentView(layout);
        popup.setWidth(200);
        popup.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        popup.setFocusable(true);

        // Some offset to align the popup a bit to the right, and a bit down, relative to button's position.
        int OFFSET_X = 50;
        int OFFSET_Y = 60;

        // Clear the default translucent background
        popup.setBackgroundDrawable(new BitmapDrawable());

        // Displaying the popup at the specified location, + offsets.
        popup.showAtLocation(layout, Gravity.NO_GRAVITY, p.x + OFFSET_X, p.y + OFFSET_Y);

        setShockPopOutMenuTypeface(layout);
        // Getting a reference to Close button, and close the popup when clicked.
    }
    public void ShowDrugPopUpMenu(View v){
        shockIcon.setImageDrawable(getResources().getDrawable(R.drawable.shock));
        drug_icon.setImageDrawable(getResources().getDrawable(R.drawable.drugs_active));


        final Display display = getWindowManager().getDefaultDisplay();
        final Point size = new Point();
        display.getSize(size);

        final int[] location = new int[2];

        drug_icon.getLocationOnScreen(location);

        //Initialize the Point with x, and y positions
        Point p = new Point();
        p.x = location[0];
        p.y = location[1];

        // Inflate the popup_layout.xml
        LinearLayout viewGroup = (LinearLayout)findViewById(R.id.drug_popup);
        LayoutInflater layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View layout = layoutInflater.inflate(R.layout.drugs_popout_layout, viewGroup);
        final ListView drugList = (ListView) layout.findViewById(R.id.drug_list);
        drugList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.drug_names)){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getView(position, convertView, parent);

                textView.setTextColor(Color.WHITE);
                textView.setTypeface(font_bold);
                textView.setTextSize(16);

                return textView;
            }
        });
        drugList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                popup.dismiss();
                addLogToList(setLog("Drugs: "+drugList.getItemAtPosition(i).toString()));
                drug_icon.setImageDrawable(getResources().getDrawable(R.drawable.drugs));
            }
        });

        // Creating the PopupWindow
        popup = new PopupWindow();
        popup.setContentView(layout);
        popup.setWidth(200);
        popup.setHeight(300);
        popup.setFocusable(true);

        // Some offset to align the popup a bit to the right, and a bit down, relative to button's position.
        int OFFSET_X = 80;
        int OFFSET_Y = 60;

        // Clear the default translucent background
        popup.setBackgroundDrawable(new BitmapDrawable());

        // Displaying the popup at the specified location, + offsets.
        popup.showAtLocation(layout, Gravity.NO_GRAVITY, p.x - OFFSET_X, p.y + OFFSET_Y);

        // Getting a reference to Close button, and close the popup when clicked.
    }
    public void ShowRhytmnPopUpMenu(View v){

        final Display display = getWindowManager().getDefaultDisplay();
        final Point size = new Point();
        display.getSize(size);

        final int[] location = new int[2];

        label_rhytmn.getLocationOnScreen(location);

        //Initialize the Point with x, and y positions
        Point p = new Point();
        p.x = location[0];
        p.y = location[1];

        // Inflate the popup_layout.xml
        LinearLayout viewGroup = (LinearLayout)findViewById(R.id.rhytmn_popup);
        LayoutInflater layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View layout = layoutInflater.inflate(R.layout.rhytmn_popout_layout, viewGroup);

        rhytmn_vt = (TextView)layout.findViewById(R.id.rhytmn_vt);
        rhytmn_pea = (TextView)layout.findViewById(R.id.rhytmn_pea);
        rhytmn_bradycardia = (TextView)layout.findViewById(R.id.rhytmn_bradycardia);
        rhytmn_tachycardia = (TextView)layout.findViewById(R.id.rhytmn_tachycardia);
        rhytmn_stemi = (TextView)layout.findViewById(R.id.rhytmn_stemi);
        bradycardia_layout = (LinearLayout)layout.findViewById(R.id.bradycardia_layout);
        tachycardia_layout = (LinearLayout)layout.findViewById(R.id.tachycardia_layout);
        TextView rhytmn_idiovenricular = (TextView) layout.findViewById(R.id.rhytmn_idiovenricular);
        TextView rhytmn_heart_block = (TextView) layout.findViewById(R.id.rhytmn_heart_block);
        TextView rhytmn_first_hb = (TextView) layout.findViewById(R.id.rhytmn_first_hb);
        TextView rhytmn_second_block_type = (TextView) layout.findViewById(R.id.rhytmn_second_block_type);
        TextView rhytmn_sinus = (TextView) layout.findViewById(R.id.rhytmn_sinus);
        TextView rhytmn_svt = (TextView) layout.findViewById(R.id.rhytmn_svt);
        TextView rhytmn_svt_aberancy = (TextView) layout.findViewById(R.id.rhytmn_svt_aberancy);
        TextView rhytmn_ventricular = (TextView) layout.findViewById(R.id.rhytmn_ventricular);
        TextView rhytmn_flutter = (TextView) layout.findViewById(R.id.rhytmn_flutter);

        rhytmn_idiovenricular.setTypeface(font_bold);
        rhytmn_heart_block.setTypeface(font_bold);
        rhytmn_first_hb.setTypeface(font_bold);
        rhytmn_second_block_type.setTypeface(font_bold);
        rhytmn_sinus.setTypeface(font_bold);
        rhytmn_svt.setTypeface(font_bold);
        rhytmn_svt_aberancy.setTypeface(font_bold);
        rhytmn_ventricular.setTypeface(font_bold);
        rhytmn_flutter.setTypeface(font_bold);

        rhytmn_vt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bluevisionit.android.resusrunner.Log log = new bluevisionit.android.resusrunner.Log();
                log.code_time = start_timer.getText().toString();
                log.real_time = getCurrentTime();
                log.event = "Rhytmn: VT/VF";
                popup.dismiss();
                addLogToList(log);
                Log.v("Log", log.code_time+","+log.real_time+","+log.event);
            }
        });
        rhytmn_pea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*bluevisionit.android.resusrunner.Log log = new bluevisionit.android.resusrunner.Log();
                log.code_time = start_timer.getText().toString();
                log.real_time = getCurrentTime();
                log.event = "Rhytmn - PEA/Asystole";
                popup.dismiss();*/
                addLogToList(setLog("Rhytmn: PEA/Asystole"));
            }
        });
        rhytmn_stemi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bluevisionit.android.resusrunner.Log log = new bluevisionit.android.resusrunner.Log();
                log.code_time = start_timer.getText().toString();
                log.real_time = getCurrentTime();
                log.event = "Rhytmn: STEMI/NSTEMI";
                popup.dismiss();
                addLogToList(log);
                Log.v("Log", log.code_time+","+log.real_time+","+log.event);
            }
        });

        rhytmn_idiovenricular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bluevisionit.android.resusrunner.Log log = new bluevisionit.android.resusrunner.Log();
                log.code_time = start_timer.getText().toString();
                log.real_time = getCurrentTime();
                log.event = "Rhytmn: Bradycardia: Idioventricular Junctional rhythm";
                popup.dismiss();
                addLogToList(log);
                Log.v("Log", log.code_time+","+log.real_time+","+log.event);
            }
        });
        rhytmn_heart_block.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bluevisionit.android.resusrunner.Log log = new bluevisionit.android.resusrunner.Log();
                log.code_time = start_timer.getText().toString();
                log.real_time = getCurrentTime();
                log.event = "Rhytmn: Bradycardia: Complete Heart Block";
                popup.dismiss();
                addLogToList(log);
                Log.v("Log", log.code_time+","+log.real_time+","+log.event);
            }
        });
        rhytmn_first_hb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bluevisionit.android.resusrunner.Log log = new bluevisionit.android.resusrunner.Log();
                log.code_time = start_timer.getText().toString();
                log.real_time = getCurrentTime();
                log.event = "Rhytmn: Bradycardia: 1st deg HB ";
                popup.dismiss();
                addLogToList(log);
                Log.v("Log", log.code_time+","+log.real_time+","+log.event);
            }
        });

        rhytmn_second_block_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bluevisionit.android.resusrunner.Log log = new bluevisionit.android.resusrunner.Log();
                log.code_time = start_timer.getText().toString();
                log.real_time = getCurrentTime();
                log.event = "Rhytmn: Bradycardia: 2nd degree block Type1/Type2";
                popup.dismiss();
                addLogToList(log);
                Log.v("Log", log.code_time+","+log.real_time+","+log.event);
            }
        });

        rhytmn_sinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bluevisionit.android.resusrunner.Log log = new bluevisionit.android.resusrunner.Log();
                log.code_time = start_timer.getText().toString();
                log.real_time = getCurrentTime();
                log.event = "Rhytmn: Tachycardia: Sinus tachycardia";
                popup.dismiss();
                addLogToList(log);
                Log.v("Log", log.code_time+","+log.real_time+","+log.event);
            }
        });
        rhytmn_svt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bluevisionit.android.resusrunner.Log log = new bluevisionit.android.resusrunner.Log();
                log.code_time = start_timer.getText().toString();
                log.real_time = getCurrentTime();
                log.event = "Rhytmn: Tachycardia: SVT";
                popup.dismiss();
                addLogToList(log);
                Log.v("Log", log.code_time+","+log.real_time+","+log.event);
            }
        });
        rhytmn_svt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bluevisionit.android.resusrunner.Log log = new bluevisionit.android.resusrunner.Log();
                log.code_time = start_timer.getText().toString();
                log.real_time = getCurrentTime();
                log.event = "Rhytmn: Tachycardia: SVT";
                popup.dismiss();
                addLogToList(log);
                Log.v("Log", log.code_time+","+log.real_time+","+log.event);
            }
        });
        rhytmn_svt_aberancy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bluevisionit.android.resusrunner.Log log = new bluevisionit.android.resusrunner.Log();
                log.code_time = start_timer.getText().toString();
                log.real_time = getCurrentTime();
                log.event = "Rhytmn: Tachycardia: SVT /w aberrancy ";
                popup.dismiss();
                addLogToList(log);
                Log.v("Log", log.code_time+","+log.real_time+","+log.event);
            }
        });
        rhytmn_ventricular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bluevisionit.android.resusrunner.Log log = new bluevisionit.android.resusrunner.Log();
                log.code_time = start_timer.getText().toString();
                log.real_time = getCurrentTime();
                log.event = "Rhytmn: Tachycardia: Ventricular tachycardia";
                popup.dismiss();
                addLogToList(log);
                Log.v("Log", log.code_time+","+log.real_time+","+log.event);
            }
        });
        rhytmn_flutter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bluevisionit.android.resusrunner.Log log = new bluevisionit.android.resusrunner.Log();
                log.code_time = start_timer.getText().toString();
                log.real_time = getCurrentTime();
                log.event = "Rhytmn: Tachycardia: A fib./A. Flutter";
                popup.dismiss();
                addLogToList(log);
                Log.v("Log", log.code_time+","+log.real_time+","+log.event);
            }
        });

        // Creating the PopupWindow
        popup = new PopupWindow();
        popup.setContentView(layout);
        popup.setWidth(200);
        popup.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        popup.setFocusable(true);

        // Some offset to align the popup a bit to the right, and a bit down, relative to button's position.
        int OFFSET_X = 50;
        int OFFSET_Y = 20;

        // Clear the default translucent background
        popup.setBackgroundDrawable(new BitmapDrawable());

        // Displaying the popup at the specified location, + offsets.
        popup.showAtLocation(layout, Gravity.NO_GRAVITY, p.x + OFFSET_X, p.y + OFFSET_Y);

        setRhytmnPopOutMenuTypeface(layout);
        // Getting a reference to Close button, and close the popup when clicked.
    }

    private void addLogToList(bluevisionit.android.resusrunner.Log log) {
        Variables.log.add(log);
        logListAdapter.notifyDataSetChanged();
    }

    private String getCurrentTime() {
        Calendar currentTime = Calendar.getInstance();
        return String.format("%02d",currentTime.get(Calendar.HOUR_OF_DAY))+":"+String.format("%02d",currentTime.get(Calendar.MINUTE))+":"+String.format("%02d",currentTime.get(Calendar.SECOND));
    }


    public void OpenBradycardiaLayout(View v){
        if(bradycardia_layout.getVisibility()==View.VISIBLE)
            bradycardia_layout.setVisibility(View.GONE);
        else
            bradycardia_layout.setVisibility(View.VISIBLE);
    }

    public void OpenTachycardiaLayout(View v){
        if(tachycardia_layout.getVisibility()==View.VISIBLE)
            tachycardia_layout.setVisibility(View.GONE);
        else
            tachycardia_layout.setVisibility(View.VISIBLE);
    }
}
