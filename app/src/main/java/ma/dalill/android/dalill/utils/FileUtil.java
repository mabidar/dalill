package ma.dalill.android.dalill.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by HP on 16/02/2017.
 */
public class FileUtil {


    public static String readFile(String file,int lineNumber, AssetManager assetManager )
             {
        // To load text file
        InputStream input;
        try {
            input = assetManager.open(file + ".dll");
            BufferedReader br = new BufferedReader(new InputStreamReader(input));
            int counter = 0;

            // This will reference one line at a time
            String line ;
            String buffer = null;
            String strLine;
            while ((strLine = br.readLine()) != null){
                if(counter == lineNumber )
                {
                    buffer = strLine;
                }
                counter++;

            }

            if(buffer!=null) {
                // byte buffer into a string
                return new String(buffer);
            }
        } catch (IOException e) {
            Log.e("Exception", "File read failed: " + e.toString());

        }
        return "";
    }

    public static void writeToFile(String data , Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("savep.dll", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    public static String readFromFile(Context context) {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput("savep.dll");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                    Log.e("file read ", " activity : "+receiveString);

                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }
}
