package ma.dalill.android.dalill;

    import android.app.ActionBar;
    import android.app.AlertDialog;
    import android.app.AlertDialog.Builder;
    import android.app.Dialog;
    import android.app.NotificationManager;
    import android.content.Context;
    import android.content.DialogInterface;
    import android.content.SharedPreferences;
    import android.content.res.Configuration;
    import android.graphics.Canvas;
    import android.graphics.Color;
    import android.graphics.Matrix;
    import android.graphics.Paint;
    import android.graphics.PointF;
    import android.graphics.Typeface;
    import android.os.Message;
    import android.preference.PreferenceManager;
    import android.support.v4.app.NotificationCompat;
    import android.support.v7.app.ActionBarActivity;
    import android.os.Bundle;
    import android.text.method.ScrollingMovementMethod;
    import android.util.AttributeSet;
    import android.util.FloatMath;
    import android.util.Log;
    import android.view.Gravity;
    import android.view.Menu;
    import android.view.MenuItem;
    import android.view.MotionEvent;
    import android.view.ScaleGestureDetector;
    import android.view.View;
    import android.view.ViewGroup.LayoutParams;
    import android.view.WindowManager;
    import android.widget.ImageButton;
    import android.widget.ImageView;
    import android.widget.LinearLayout;
    import android.widget.PopupMenu;
    import android.widget.RelativeLayout;
    import android.widget.ScrollView;
    import android.widget.TextView;
    import android.widget.Toast;

    import com.google.android.gms.ads.AdRequest;
    import com.google.android.gms.ads.AdView;

    import ma.dalill.android.dalill.utils.FileUtil;


public class MainActivity extends ActionBarActivity {
    String day="Monday";
    int lineNumber=-1;
    String backgroundResource="bg";
    String font="kufi";
    boolean isHome=true;
    private AdView mAdView;
    //Called when the activity is first created.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mAdView.loadAd(adRequest);
        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

       ImageButton buttonNext = (ImageButton) findViewById(R.id.imageButtonNext);
       buttonNext.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                 displayNextTextView(day);
            }

        });

        ImageButton buttonPrevious = (ImageButton) findViewById(R.id.imageButtonPrevious);
        buttonPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayPreviousTextView(day);
            }

        });

        ImageButton buttonMark = (ImageButton) findViewById(R.id.buttonMark);
        buttonMark.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(lineNumber==  -1 && "Monday".equals(day)){
                    displayAlert("لا نحتفظ بالصفحة الأولى ");
                }else{
                    writeToFile();
                    displayAlert(" لقد إحتفظنا لكم بالصفحة يمكنكم الرجوع لها ");
                }
            }

        });

     /*   final   ImageButton buttonFont = (ImageButton) findViewById(R.id.fonts);
        buttonFont.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(MainActivity.this, buttonFont);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        if("kufi".equals(item.getItemId())){
                            font="kufi";
                        }else if("maroc".equals(item.getItemId())){
                            font="maroc";
                        }
                        return true;
                    }
                });

                popup.show();//showing popup menu
            }

        });*/



        return true;
    }
    private void writeToFile(){
        FileUtil.writeToFile( day + ";" + (lineNumber-1),this);

    }

    private void setBG(){
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
       if(backgroundResource.equals("bg")) {
           relativeLayout.setBackgroundResource(R.drawable.bggreen);
           backgroundResource="bggreen";
       }else{
           relativeLayout.setBackgroundResource(R.drawable.bg);
           backgroundResource="bg";

       }
    }
    private void displayNextTextView(String day) {

        TextView textView =(TextView)findViewById(R.id.textView);
        lineNumber=lineNumber+1;

        String text= FileUtil.readFile(day, lineNumber, getAssets());
        if(text == null || text=="") {
            lineNumber = lineNumber - 1;
            displayAlert("للإنتقال إلى الحزب الموالي إستعملوا القائمة");
        }else{
            textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            textView.setText(text);
            setFontToText(textView);
            setBG();
        }
        displayIndex();

    }

    private void setFontToText(TextView textView){
        // Font path
        String fontPath  =  "fonts/DroidKufi-Regular.ttf";
         if("maroc".equals(font)){
            fontPath= "fonts/Maroc_font.ttf";
        }



        // Loading Font Face
        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);

        // Applying font
        textView.setTypeface(tf);
        textView.setTextColor((Color.parseColor("#1d2129")));
     }

   private void displayPreviousTextView(String day) {

       TextView textView = (TextView) findViewById(R.id.textView);
       this.lineNumber--;
       String text = FileUtil.readFile(day, this.lineNumber, getAssets());
       if (text == null ||  text=="") {
           this.lineNumber++;
           displayAlert("للإنتقال إلى الحزب الموالي إستعملوا القائمة");
       } else {
           textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
           textView.setText(text);
           setFontToText(textView);
           setBG();
           displayIndex();

       }
    }

    private void displayAlert(String msg) {

        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);

        //Set the background for the toast using android's default toast_frame.
        //Optionally you can set the background color to #646464 which is the
        //color of the frame
        View view = toast.getView();
        view.setBackgroundResource(android.R.drawable.toast_frame);

        //Get the TextView for the toast message so you can customize
        TextView toastMessage = (TextView) view.findViewById(android.R.id.message);
        //Set background color for the text.
        toastMessage.setTextColor((Color.parseColor("#acf709")));

        toast.show();

    }

private void displayIndex(){
    isHome=false;
    TextView index =(TextView)findViewById(R.id.index);
    if(day.equals("Khatem")){
        index.setText("عدد الصفحة في الختم :" + (lineNumber + 1));
    }else {
        index.setText(   " عدد الصفحة في حزب " +getString(day)+" "+ (lineNumber + 1));
    }
}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        switch (id) {
            case (R.id.action_Monday): {
                day = "Monday";
                break;
            }
            case (R.id.action_Tuesday): {
                day = "Tuesday";
                break;
            }
            case (R.id.action_Wednesday): {
                day = "Wednesday";
                break;
            }
            case (R.id.action_Thursday): {
                day = "Thursday";
                break;
            }
            case (R.id.action_Friday): {
                day = "Friday";
                break;
            }
            case (R.id.action_Saturday): {
                day = "Saturday";
                break;
            }
            case (R.id.action_Sunday): {
                day = "Sunday";
                break;
            }
            case (R.id.action_Monday2): {
                day = "Monday2";
                break;
            }
            case (R.id.action_Khatem): {
                day = "Khatem";
                break;
            }
            case(R.id.action_Savepoint) :{
                displayAlertDialogGoToSP();

            return super.onOptionsItemSelected(item);
            }case(R.id.font_kufi) :{
                font="kufi";
                if(isHome) {
                    return true;
                }else{
                    lineNumber=lineNumber-1;
                    displayNextTextView(day);
                    return true;
                }
            }case(R.id.font_maroc) :{
                font="maroc";
                if(isHome) {
                    return true;
                }
                else{
                    lineNumber=lineNumber-1;
                    displayNextTextView(day);
                    return true;
                }
            }  default: {
                return true ;
            }
        }
        lineNumber=-1;
        displayNextTextView(day);
        return super.onOptionsItemSelected(item);
    }

    private void displayAlertDialogGoToSP() {
        String savedData = FileUtil.readFromFile(this);
        if (savedData != null && savedData != "") {

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = preferences.edit();
            String[] data=savedData.split(";");
            editor.putString("savedDay",data[0]);
            editor.putInt("savedLineNumber",Integer.parseInt(data[1]));
            editor.apply();

            AlertDialog alertDialog = new Builder(this).create();
            alertDialog.setTitle(   " صفحة التوقف المحتفظ بها هي "  +(Integer.parseInt(data[1]) + 2)+" في "+getString(data[0]));
            alertDialog.setMessage("إظغط للإنتقال لها");
            alertDialog.setButton(Dialog.BUTTON_POSITIVE, "نعم", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    goToSavedPoint();
                }
            });

            alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "لا", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //
                }
            });
            alertDialog.show();
        } else {
            displayAlert("لا توجد أي صفحة محتفظ بها");
        }


    }

private void goToSavedPoint(){

    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);


       String savedDay = preferences.getString("savedDay",null);
       int savedLineNumber =    preferences.getInt("savedLineNumber",-100);
            if(savedLineNumber==-100 || day==null){
                return;
            }else {
                day=savedDay;
                lineNumber=savedLineNumber;
                displayNextTextView(day);
            }

}



private String getString(String day){
    if ( "Monday".equals(day) ){
       return "الاثنين";
    }
    else if  ( "Tuesday".equals(day)) {
        return "الثلاثاء";
    }
    else if  ("Wednesday".equals(day) ) {

        return "الأربعاء";
    }
    else if  (  "Thursday".equals(day)) {

        return "الخميس";
    }
    else if  ("Friday".equals(day) ) {
        return "الجمعة";
    }
    else if  ( "Saturday".equals(day)) {
               return "السبت";
    }
    else if  ( "Sunday".equals(day) ) {

        return "الأحد";
    }
    else if  ( "Monday2".equals(day)) {
          return "الإثنين الثاني";
    }
    else if ("Khatem".equals(day) ) {

        return "الختم";
    }
return "";



}

    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }

}
