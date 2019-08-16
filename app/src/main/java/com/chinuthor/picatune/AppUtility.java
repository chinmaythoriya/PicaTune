package com.chinuthor.picatune;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatEditText;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AppUtility {

    private static final String TAG = "APPSETTINGS";

    /**
     * Set App Property
     *
     * @param key
     * @param value
     * @param context
     */
    public static void setAppProperty(String key, Object value, Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        if (value == null) {
            editor.putString(key, null);
        } else {
            editor.putString(key, value.toString());
        }
        editor.apply();
    }


    /**
     * Get App Property
     *
     * @param key
     * @param context
     * @return
     */
    public static String getAppProperty(String key, String defaultValue, Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(key, defaultValue);
    }

    /**
     * Remove app property
     *
     * @param key
     * @param context
     */
    public static void removeAppProperty(String key, Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(key);
        editor.commit();
    }

    /**
     * Show Toast Message
     *
     * @param context
     * @param message
     * @param duration
     */
    public static void showToast(Context context, String message, int duration) {
        Toast toast = Toast.makeText(context, message, duration);
        LinearLayout toastLayout = (LinearLayout) toast.getView();
        TextView toastMessage = (TextView) toastLayout.getChildAt(0);
//        toastMessage.setTypeface(getFont(context, R.integer.font_style_normal));
        toastMessage.setTextSize(18);
        toast.show();
    }

    public static ProgressDialog showProgressDialog(Context context, String title, String message) {
        ProgressDialog pd = new ProgressDialog(context);
        pd.setTitle(title);
        pd.setMessage(message);
        pd.show();
        pd.setCancelable(false);
        pd.setCanceledOnTouchOutside(false);

//        int titleId = context.getResources().getIdentifier("alertTitle", "id", "android");
//        if (titleId > 0) {
//            TextView dialogTitle = (TextView) pd.findViewById(titleId);
//            dialogTitle.setTypeface(getFont(context, R.integer.font_style_bold));
//        }
//
//        TextView textView = (TextView) pd.findViewById(android.R.id.message);
//        textView.setTypeface(getFont(context, R.integer.font_style_normal));
        return pd;
    }

    public static void showAlertDialog(Context context, String title, String message, String positiveButtonText, String negativeButtonText, int iconImageRes, final AlertDialogCallback alertDialogCallback) {
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(positiveButtonText, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (alertDialogCallback != null) {
                            alertDialogCallback.onPositiveButtonClick(dialog);
                        }
                    }
                })
                .setNegativeButton(negativeButtonText, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (alertDialogCallback != null) {
                            alertDialogCallback.onNegativeButtonClick(dialog);
                        }
                    }
                })
                .setIcon(iconImageRes)
                .show();

//        int titleId = context.getResources().getIdentifier("alertTitle", "id", "android");
//        if (titleId > 0) {
//            TextView dialogTitle = (TextView) dialog.findViewById(titleId);
//            dialogTitle.setTypeface(getFont(context));
//        }
//
//        TextView messageTextView = (TextView) dialog.findViewById(android.R.id.message);
//        messageTextView.setTypeface(getFont(context, R.integer.font_style_normal));
//
//        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTypeface(getFont(context, R.integer.font_style_normal));
//        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTypeface(getFont(context, R.integer.font_style_normal));
    }

    public static void showAlertDialog(Context context, String title, String message) {
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setIcon(android.R.drawable.ic_dialog_info)
                .setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Do Nothing
                        dialog.dismiss();
                    }
                })
                .show();

//        int titleId = context.getResources().getIdentifier("alertTitle", "id", "android");
//        if (titleId > 0) {
//            TextView dialogTitle = (TextView) dialog.findViewById(titleId);
//            dialogTitle.setTypeface(getFont(context, R.integer.font_style_bold));
//        }
//
//        TextView dialogMessage = (TextView) dialog.findViewById(android.R.id.message);
//        dialogMessage.setTypeface(getFont(context, R.integer.font_style_normal));
//
//        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTypeface(getFont(context, R.integer.font_style_normal));
    }

//    /**
//     * Convert Image into String
//     *
//     * @param bmp bitmap to be changed in string
//     * @return StringFormateofImage
//     */
//    public static String getStringImage(Bitmap bmp) {
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//        byte[] imageBytes = baos.toByteArray();
//        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
//    }

//    /**
//     * @param context context where method is called
//     * @return Typeface with custom fonts
//     */
//    public static Typeface getFont(Context context, @NonNull int fontStyle) {
//        switch (fontStyle) {
//            case R.integer.font_style_normal:
//                return Typeface.createFromAsset(context.getAssets(), "fonts/MavenPro-Medium.ttf");
//
//            case R.integer.font_style_bold:
//                return Typeface.createFromAsset(context.getAssets(), "fonts/MavenPro-Bold.ttf");
//            default:
//                return Typeface.createFromAsset(context.getAssets(), "fonts/MavenPro-Medium.ttf");
//        }
//    }

    /**
     * @param activity activity where method is called.
     */
    public static void closeKeyboard(Activity activity) {
        final InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (activity.getCurrentFocus() != null)
            inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.SHOW_FORCED);
    }

    public static void openKeyboard(Activity activity) {
        final InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (activity.getCurrentFocus() != null) {
            inputMethodManager.showSoftInput(activity.getCurrentFocus(), InputMethodManager.SHOW_FORCED);
        }
    }

    public static void limitInput(AppCompatEditText appCompatEditText, int maxLength) {
        appCompatEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
    }

//    public static void turnLocationOn(final Context context, @NonNull GoogleApiClient googleApiClient, LocationRequest mLocationRequest) {
//        ResultCallback<LocationSettingsResult> mResultCallbackFromSettings = new ResultCallback<LocationSettingsResult>() {
//            @Override
//            public void onResult(@NonNull LocationSettingsResult result) {
//                final Status status = result.getStatus();
//                //final LocationSettingsStates locationSettingsStates = result.getLocationSettingsStates();
//                switch (status.getStatusCode()) {
//                    case LocationSettingsStatusCodes.SUCCESS:
//                        // All location settings are satisfied. The client can initialize location
//                        // requests here.
//                        break;
//                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
//                        // Location settings are not satisfied. But could be fixed by showing the user
//                        // a dialog.
//                        try {
//                            // Show the dialog by calling startResolutionForResult(),
//                            // and check the result in onActivityResult().
//                            status.startResolutionForResult(
//                                    (Activity) context, 0);
//                        } catch (IntentSender.SendIntentException e) {
//                            // Ignore the error.
//                        }
//                        break;
//                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
//                        Log.e(TAG, "Settings change unavailable.");
//                        break;
//                }
//            }
//        };
//
//
//        LocationSettingsRequest.Builder locationSettingsRequestBuilder = new LocationSettingsRequest.Builder()
//                .addLocationRequest(mLocationRequest);
//        PendingResult<LocationSettingsResult> result =
//                LocationServices.SettingsApi.checkLocationSettings(googleApiClient, locationSettingsRequestBuilder.build());
//        result.setResultCallback(mResultCallbackFromSettings);
//    }

//    public static boolean checkPlayServices(AppCompatActivity activity, int requestCode) {
//        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
//        int resultCode = apiAvailability.isGooglePlayServicesAvailable(activity);
//        if (resultCode != ConnectionResult.SUCCESS) {
//            if (apiAvailability.isUserResolvableError(resultCode)) {
//                apiAvailability.getErrorDialog(activity, resultCode, requestCode)
//                        .show();
//            } else {
//                Log.e(TAG, "error" + "This device does not support play service");
//                activity.finish();
//            }
//            return false;
//        }
//        return true;
//    }

//    public static String getDeviceUid(Context context) {
//
//        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//        String deviceId = "" + telephonyManager.getDeviceId();
//        String serialNumber = "" + telephonyManager.getSimSerialNumber();
//        String androidId = "" + android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
//
//        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) deviceId.hashCode() << 32) | serialNumber.hashCode());
//        String deviceUId = deviceUuid.toString();
//        Log.d(TAG, "deviceId: " + deviceUId);
//
//        return deviceUId;
//    }

//    public static JSONArray getJSONArray(List<?> list) throws Exception {
//        List<HashMap<String, String>> a = new ArrayList<>();
//
//        for (int i = 0; i < list.size(); i++) {
//            a.add(((MyCotraJSON) list.get(i)).getJSONableData());
//        }
//
//
//        Log.d(TAG, "a: " + a);
//        JSONArray array = new JSONArray(a);
//        Log.d(TAG, "jsonArray: " + array.toString());
//        return array;
//    }

    public static String formatDate(String format, Long date) throws Exception {
        String formattedDate = "";
        try {
            SimpleDateFormat format1 = new SimpleDateFormat(format, Locale.getDefault());
            formattedDate = format1.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return formattedDate;
    }

    public static String getDuration(long agoTimeInMillis) throws Exception {
        Calendar postTime = Calendar.getInstance();
        postTime.setTimeInMillis(agoTimeInMillis);

        Calendar now = Calendar.getInstance();

        final String timeFormatString = "h:mm aa";
        final String dateTimeFormatString = "E, MMM d, h:mm aa";

        if (now.get(Calendar.YEAR) == postTime.get(Calendar.YEAR)) {
            //within this year
            // wed, Sep 14, 3:52 pm
//            return DateFormat.format(dateTimeFormatString, postTime).toString();
            if (now.get(Calendar.MONTH) == postTime.get(Calendar.MONTH)) {
                if (now.get(Calendar.DATE) == postTime.get(Calendar.DATE)) {
                    //same day
                    if (now.get(Calendar.HOUR_OF_DAY) == postTime.get(Calendar.HOUR_OF_DAY)) {
                        //same hour
                        if (now.get(Calendar.MINUTE) == postTime.get(Calendar.MINUTE)) {
                            //same min
                            //just now
                            return "Just now";
                        } else if (now.get(Calendar.MINUTE) - postTime.get(Calendar.MINUTE) <= 60) {
                            //within this hour
                            // ___ min ago
                            return (now.get(Calendar.MINUTE) - postTime.get(Calendar.MINUTE)) + " min ago";
                        }
                    } else if (now.get(Calendar.HOUR_OF_DAY) - postTime.get(Calendar.HOUR_OF_DAY) <= 12) {
                        // within 12 hours
                        // ___ hour ago
                        return (now.get(Calendar.HOUR_OF_DAY) - postTime.get(Calendar.HOUR_OF_DAY)) + " hour ago";
                    } else {
                        //within 24 hours
                        // today h:mm a
                        return "Today " + android.text.format.DateFormat.format(timeFormatString, postTime);
                    }
                } else if (now.get(Calendar.DATE) - postTime.get(Calendar.DATE) == 1) {
                    //yesterday at h:mm a
                    return "Yesterday " + android.text.format.DateFormat.format(timeFormatString, postTime);
                } else {
                    return formatDate(dateTimeFormatString, postTime.getTimeInMillis());
                }
            } else {
                return formatDate(dateTimeFormatString, postTime.getTimeInMillis());
            }
        } else if (now.get(Calendar.YEAR) > postTime.get(Calendar.YEAR)) {
            //otherwise
            // Sep 14 2016, 6:42 pm
            return formatDate("MMM dd yyyy, h:mm aa", postTime.getTimeInMillis());
        }
        throw new Exception("Invalide date exception!");
    }

    public static String formatTime(String format, String time) {
        String formattedTime = "";
        try {
            Time time1 = Time.valueOf(time);
            SimpleDateFormat format1 = new SimpleDateFormat(format, Locale.getDefault());
            formattedTime = format1.format(time1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return formattedTime;
    }

    public static Date stringToDate(String format, String stringDate) throws ParseException {
        DateFormat df = new SimpleDateFormat(format, Locale.getDefault());
        return df.parse(stringDate);
    }

    public static boolean isPasswordValid(String password) {
        return !TextUtils.isEmpty(password) && password.length() >= 6 && password.length() <= 15;
    }

    public static String milliSecondsToTimer(String songDuration) {
        int duration = Integer.parseInt(songDuration);
        int hour = (duration / (1000 * 60 * 60));
        int minute = ((duration % (1000 * 60 * 60)) / (1000 * 60));
        int seconds = (((duration % (1000 * 60 * 60)) % (1000 * 60)) / (1000));
        String finalString = "";
        if (hour < 10)
            finalString += "0";
        finalString += hour + ":";
        if (minute < 10)
            finalString += "0";
        finalString += minute + ":";
        if (seconds < 10)
            finalString += "0";
        finalString += seconds;

        return finalString;
    }

    public static int getSongProgress(int totalDuration, int currentDuration) {
        return (currentDuration * 100) / totalDuration;
    }

}
