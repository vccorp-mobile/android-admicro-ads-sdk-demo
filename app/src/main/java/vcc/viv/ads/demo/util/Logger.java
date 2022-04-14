package vcc.viv.ads.demo.util;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import androidx.core.content.ContextCompat;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import vcc.viv.ads.demo.BuildConfig;


public class Logger {
    /*
     * Area : Singleton
     */
    private static Logger instance = null;

    public static Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    /*
     * Area : Variable
     */
    private boolean isLog;
    private Level level;
    private Context context;
    private String tag;
    private Map<String, Callback> callbacks;

    /*
     * Area : Function
     */
    Logger() {
        this.isLog = true;
        this.level = Level.verbose;
        this.tag = Logger.class.getSimpleName();
        this.callbacks = new HashMap<>();
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setLevel(Level level) {
        if (level != null) {
            this.level = level;
        } else {
            Log.w(tag, "Level need not empty");
        }
    }

    public void setTag(String tag) {
        if (TextUtils.isEmpty(tag)) {
            Log.w(tag, "Tag need not empty");
        } else {
            this.tag = tag;
        }
    }

    public void setLog(boolean isLog) {
        this.isLog = isLog;
    }

    public void verbose(String msg) {
        verbose(tag, msg);
    }

    public void verbose(String tag, String msg) {
        print(Level.verbose, tag, msg);
    }

    public void info(String msg) {
        info(tag, msg);
    }

    public void info(String tag, String msg) {
        print(Level.info, tag, msg);
    }

    public void debug(String msg) {
        debug(tag, msg);
    }

    public void debug(String tag, String msg) {
        print(Level.debug, tag, msg);
    }

    public void warning(String msg) {
        warning(tag, msg);
    }

    public void warning(String tag, String msg) {
        print(Level.warning, tag, msg);
    }

    public void error(String msg) {
        error(tag, msg);
    }

    public void error(String tag, String msg) {
        print(Level.error, tag, msg);
    }

    private void print(Level level, String tag, String msg) {
        int logLevel = this.level.ordinal();
        int currentLogLevel = level.ordinal();
        if (currentLogLevel < logLevel) {
//            Log.d(tag, "log level too low");
        } else if (isLog) {
            // create data
            StackTraceElement element = new Throwable().getStackTrace()[3];
            String file = element.getClassName();
            String filename = file.replace(String.format(Setting.Format.oldString, BuildConfig.APPLICATION_ID), Setting.Constant.newString);
            String method = element.getMethodName();
            int line = element.getLineNumber();
            String dataFile = String.format(Setting.Format.dataFileLog, level.name(), filename, method, line, msg);
            String dataConsole = String.format(Setting.Format.dataConsoleLog, filename, method, line, msg);

            // show log in console
            switch (level) {
                case error:
                    Log.e(tag, dataConsole);
                    break;
                case warning:
                    Log.w(tag, dataConsole);
                    break;
                case debug:
                    Log.d(tag, dataConsole);
                    break;
                case info:
                    Log.i(tag, dataConsole);
                    break;
                default:
                    Log.v(tag, dataConsole);
                    break;
            }

            // send lag callback
            sendCallback(tag, dataFile);

            // write log to file
            writeFile(dataFile);
        } else {
            // Log.d(tag, "configure log fail");
        }
    }

    /**
     * Write file : Write log to file
     *
     * @param data is log content
     */
    public void writeFile(String data) {
        if (context != null) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                FileWriter fileWriter;
                BufferedWriter bufferedWriter;
                try {
                    // Create file name
                    String date = new SimpleDateFormat(Setting.Format.date, Locale.getDefault()).format(Calendar.getInstance().getTime());
                    Calendar rightNow = Calendar.getInstance();
                    int currentHour = rightNow.get(Calendar.HOUR_OF_DAY);
                    DecimalFormat df = new DecimalFormat(Setting.Constant.patternFormat);
                    String lastName = df.format(currentHour / Setting.Config.configTime * Setting.Config.configTime);
                    String fileName = String.format(Setting.Format.fileName, date, lastName);

                    // Write file
                    fileWriter = new FileWriter(Environment.getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_DOCUMENTS) + "/" + fileName, true);
                    bufferedWriter = new BufferedWriter(fileWriter);
                    bufferedWriter.write(data);
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
//                Log.d(tag, "No permission to write log");
            }
        } else {
//            Log.d(tag, "Context need to set for check permission");
        }
    }

    /*
     * Area : Callback
     */
    public void callbackRegister(String tag, Callback callback) {
        if (TextUtils.isEmpty(tag)) {
            warning(String.format(Const.Error.textEmpty, "tag"));
        } else if (callback == null) {
            warning(String.format(Const.Error.dataError, "callback"));
        } else {
            callbacks.put(tag, callback);
        }
    }

    public void callbackUnregister(String tag) {
        if (TextUtils.isEmpty(tag)) {
            warning(String.format(Const.Error.textEmpty, "tag"));
        } else if (!callbacks.containsKey(tag)) {
            warning(String.format(Const.Error.keyNotFound, tag));
        } else {
            callbacks.remove(tag);
        }
    }

    public void sendCallback(String tag, String msg) {
        if (callbacks.size() > 0) {
            Set<String> keys = callbacks.keySet();
            for (String key : keys) {
                Callback callback = callbacks.get(key);
                if (callback == null) {
                    continue;
                }
                callback.log(tag, msg);
            }
        }
    }

    /*
     * Area : Inner Class
     */
    public enum Level {verbose, debug, info, warning, error}

    public interface Callback {
        void log(String tag, String msg);
    }

    private interface Setting {
        interface Format {
            String date = "yyyyMMdd";
            String fileName = "%s%s";
            String dataFileLog = "[%s][%s_%s(%s)] : %s \n";
            String dataConsoleLog = "[%s_%s(%s)] : %s \n";
            String oldString = "%s.";
        }

        interface Config {
            long configTime = 6;
        }

        interface Constant {
            String newString = "";
            String patternFormat = "0.##";
        }
    }
}
