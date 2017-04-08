package ma.dalill.android.dalill;




import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
/**
 * Created by HP on 13/03/2017.
 */
public class AutoStart extends BroadcastReceiver
{
    Alarm alarm = new Alarm();
    @Override
    public void onReceive(Context context, Intent intent)
    {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED))
        {
            alarm.setAlarm(context);
        }
    }
}