package bluevisionit.android.resusrunner;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SignUpActivity extends Activity {
    TextView tv_signUp;
    TextView tv_login;
    TextView tv_create;
    TextView account_message;
    TextView create;
    TextView login;
    Resources res;
    String loginMessage;
    String registerMessage;
    private EditText edit_username;
    private EditText edit_password;
    private EditText edit_confirm_password;
    private TextView title_signin;
    private String create_account;
    private String signin;
    private LinearLayout basic_life_support;
    private TextView yes_pulse;
    private LinearLayout no_pulse_layout;
    private TextView yes_aed;
//    private LinearLayout no_aed_layout;
    private TextView title_sign_in;
    Typeface font_bold;
    Typeface font_regular;
    Typeface font_thin;
    private Typeface font_super_bold;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        tv_signUp = (TextView)findViewById(R.id.tv_signup);
        tv_login = (TextView)findViewById(R.id.tv_login);
        tv_create = (TextView)findViewById(R.id.tv_create);
        account_message = (TextView)findViewById(R.id.account_message);
        create = (TextView)findViewById(R.id.create);
        login = (TextView)findViewById(R.id.login);
        edit_username = (EditText)findViewById(R.id.edit_username);
        edit_password = (EditText)findViewById(R.id.edit_password);
        edit_confirm_password = (EditText)findViewById(R.id.edit_confirm_pass);
        title_signin = (TextView)findViewById(R.id.title_sign_in);
        basic_life_support = (LinearLayout)findViewById(R.id.basic_life_layout);
        yes_pulse = (TextView)findViewById(R.id.yes_pulse);
        no_pulse_layout = (LinearLayout)findViewById(R.id.no_pulse_layout);
        yes_aed = (TextView)findViewById(R.id.yes_aed);
        title_sign_in = (TextView)findViewById(R.id.title_sign_in);
//        no_aed_layout = (LinearLayout)findViewById(R.id.no_aed_layout);

        font_bold = Typeface.createFromAsset(getAssets(),"font/brandon_bld.otf");
        font_super_bold = Typeface.createFromAsset(getAssets(),"font/brandon_blk.otf");
        font_regular = Typeface.createFromAsset(getAssets(),"font/brandon_med.otf");
        font_thin = Typeface.createFromAsset(getAssets(),"font/brandon_light.otf");

        res = getResources();
        registerMessage = res.getString(R.string.no_account);
        loginMessage = res.getString(R.string.already_have);
        signin = res.getString(R.string.signin);
        create_account = res.getString(R.string.create_account);

        account_message.setText(registerMessage);
        create.setVisibility(View.VISIBLE);
        login.setVisibility(View.GONE);
        edit_confirm_password.setVisibility(View.GONE);
        title_signin.setText(signin);

        setUpTypeface();
        setUpBasicLifeLayoutSize();
        setUpSignInButtonListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        LinearLayout loginForm = (LinearLayout)findViewById(R.id.login_form);
        loginForm.requestFocus();
        loginForm.clearFocus();
    }

    private void setUpTypeface() {
        //login-register deo
        TextView title_start_code = (TextView) findViewById(R.id.title_start_code);
        TextView title_code_history = (TextView) findViewById(R.id.title_code_history);
        title_start_code.setTypeface(font_super_bold);
        title_code_history.setTypeface(font_super_bold);
        tv_signUp.setTypeface(font_bold);
        tv_login.setTypeface(font_bold);
        tv_create.setTypeface(font_regular);
        create.setTypeface(font_bold);
        title_signin.setTypeface(font_bold);
        edit_username.setTypeface(font_regular);
        edit_password.setTypeface(font_regular);
        edit_confirm_password.setTypeface(font_regular);
        account_message.setTypeface(font_regular);

        //basic life support deo
        TextView title_life_support = (TextView) findViewById(R.id.title_life_support);
        TextView tv_attention = (TextView) findViewById(R.id.tv_attention);
        TextView tv_assess = (TextView) findViewById(R.id.tv_assess);
        TextView asses_desc = (TextView) findViewById(R.id.asses_desc);
        TextView tv_check_life = (TextView) findViewById(R.id.tv_check_life);
        TextView tv_check_life_desc = (TextView) findViewById(R.id.tv_check_life_desc);
        TextView tv_help = (TextView) findViewById(R.id.tv_help);
        TextView tv_help_desc = (TextView) findViewById(R.id.tv_help_desc);

        TextView tv_begin_title = (TextView) findViewById(R.id.tv_begin_title);
        TextView tv_airway = (TextView) findViewById(R.id.tv_airway);
        TextView tv_airway_desc = (TextView) findViewById(R.id.tv_airway_desc);
        TextView tv_breaths = (TextView) findViewById(R.id.tv_breaths);
        TextView tv_breaths_desc = (TextView) findViewById(R.id.tv_breaths_desc);

        TextView tv_pulse = (TextView) findViewById(R.id.tv_pulse);
        TextView tv_pulse_question = (TextView) findViewById(R.id.tv_pulse_question);
//        TextView pulse_yes = (TextView) findViewById(R.id.pulse_yes);
//        TextView pulse_no = (TextView) findViewById(R.id.pulse_no);
        TextView tv_no_pulse = (TextView) findViewById(R.id.tv_no_pulse);
        TextView tv_no_pulse_desc = (TextView) findViewById(R.id.tv_no_pulse_desc);

        TextView aed = (TextView) findViewById(R.id.aed);
        TextView aed_question = (TextView) findViewById(R.id.aed_question);
      /*  Button shockable = (Button) findViewById(R.id.shockable);
        Button non_shockable = (Button) findViewById(R.id.non_shockable);*/
        TextView no_aed = (TextView) findViewById(R.id.no_aed);

        title_life_support.setTypeface(font_super_bold);
        tv_attention.setTypeface(font_bold);
        tv_assess.setTypeface(font_thin);
        asses_desc.setTypeface(font_bold);
        tv_check_life.setTypeface(font_thin);
        tv_check_life_desc.setTypeface(font_bold);
        tv_help.setTypeface(font_thin);
        tv_help_desc.setTypeface(font_bold);

        tv_begin_title.setTypeface(font_bold);
        tv_airway.setTypeface(font_thin);
        tv_airway_desc.setTypeface(font_bold);
        tv_breaths.setTypeface(font_thin);
        tv_breaths_desc.setTypeface(font_bold);

        tv_pulse.setTypeface(font_super_bold);
        tv_pulse_question.setTypeface(font_thin);
//        pulse_yes.setTypeface(font_bold);
//        pulse_no.setTypeface(font_bold);
        yes_pulse.setTypeface(font_bold);
        tv_no_pulse.setTypeface(font_thin);
        tv_no_pulse_desc.setTypeface(font_bold);

        aed.setTypeface(font_super_bold);
        aed_question.setTypeface(font_thin);
//        shockable.setTypeface(font_bold);
//        non_shockable.setTypeface(font_bold);
        yes_aed.setTypeface(font_bold);
        no_aed.setTypeface(font_bold);
    }

    private void setUpSignInButtonListener() {
        title_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(title_signin.getText().equals("CREATE ACCOUNT"))
                    Toast.makeText(getApplicationContext(), "Create account", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(), "Sign in", Toast.LENGTH_SHORT).show();
                SignUpActivity.this.finish();
                Intent i = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }

    private void setUpBasicLifeLayoutSize() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int newWidth = (int)((2*width)/3)-64;
        Log.v("Width", ""+width+" new: "+newWidth);
        basic_life_support.setLayoutParams(new LinearLayout.LayoutParams(newWidth, ViewGroup.LayoutParams.FILL_PARENT));
    }

    public void ShowSignUp(View v){
        tv_login.setVisibility(View.GONE);
        tv_signUp.setVisibility(View.VISIBLE);
        tv_create.setVisibility(View.VISIBLE);
        account_message.setText(loginMessage);
        create.setVisibility(View.GONE);
        login.setVisibility(View.VISIBLE);
        edit_confirm_password.setVisibility(View.VISIBLE);
        title_signin.setText(create_account);
    }

    public void ShowLogin(View v){
        tv_login.setVisibility(View.VISIBLE);
        tv_signUp.setVisibility(View.GONE);
        tv_create.setVisibility(View.GONE);
        account_message.setText(registerMessage);
        create.setVisibility(View.VISIBLE);
        login.setVisibility(View.GONE);
        edit_confirm_password.setVisibility(View.GONE);
        title_signin.setText(signin);
    }

    public void ShowYESPulseAnswer(View v){
        yes_pulse.setVisibility(View.VISIBLE);
        no_pulse_layout.setVisibility(View.GONE);
    }

    public void ShowNOPulseAnswer(View v){
        yes_pulse.setVisibility(View.GONE);
        no_pulse_layout.setVisibility(View.VISIBLE);
    }

    /*public void ShowYESAedAnswer(View v){
        yes_aed.setVisibility(View.VISIBLE);
        no_aed_layout.setVisibility(View.GONE);

    }
    public void ShowNOAedAnswer(View v){
        yes_aed.setVisibility(View.GONE);
        no_aed_layout.setVisibility(View.VISIBLE);
    }*/


    public void StartCode(View v){
        Toast.makeText(getApplicationContext(), "Start Code", Toast.LENGTH_SHORT).show();
        SignUpActivity.this.finish();
        Intent i = new Intent(SignUpActivity.this, MainActivity.class);
        startActivity(i);
    }

    public void StartCodeHistory(View v){
        Toast.makeText(getApplicationContext(), "Start Code History", Toast.LENGTH_SHORT).show();
    }
}
