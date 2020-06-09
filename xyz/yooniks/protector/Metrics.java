/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.configuration.file.YamlConfiguration
 *  org.bukkit.configuration.file.YamlConfigurationOptions
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.PluginDescriptionFile
 *  org.bukkit.plugin.ServicePriority
 *  org.bukkit.plugin.java.JavaPlugin
 *  org.json.simple.JSONArray
 *  org.json.simple.JSONObject
 */
package xyz.yooniks.protector;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.zip.GZIPOutputStream;
import javax.net.ssl.HttpsURLConnection;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.file.YamlConfigurationOptions;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import test.C;
import test.e;
import xyz.yooniks.protector.Metrics;

public class Metrics {
    public static final int B_STATS_VERSION = 1;
    private static final String URL = "https://bStats.org/submitData/bukkit";
    private final JavaPlugin plugin;
    private static boolean logFailedRequests;
    private final List<CustomChart> charts = new ArrayList<CustomChart>();
    private static String serverUUID;

    public JSONObject getPluginData() {
        Metrics a;
        JSONObject jSONObject = new JSONObject();
        String string = a.plugin.getDescription().getName();
        Object object = a.plugin.getDescription().getVersion();
        jSONObject.put((Object)"pluginName", (Object)string);
        jSONObject.put((Object)"pluginVersion", (Object)object);
        string = new JSONArray();
        object = a.charts.iterator();
        do {
            if (!object.hasNext()) {
                jSONObject.put((Object)"customCharts", (Object)string);
                return jSONObject;
            }
            JSONObject jSONObject2 = ((CustomChart)object.next()).getRequestJsonObject();
            if (jSONObject2 == null) continue;
            string.add((Object)jSONObject2);
        } while (true);
    }

    public static /* synthetic */ void access$200(JSONObject a) throws Exception {
        Metrics.sendData((JSONObject)a);
    }

    private static /* synthetic */ byte[] compress(String a) throws IOException {
        if (a == null) {
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        GZIPOutputStream gZIPOutputStream = new GZIPOutputStream((OutputStream)byteArrayOutputStream);
        gZIPOutputStream.write((byte[])a.getBytes((Charset)StandardCharsets.UTF_8));
        gZIPOutputStream.close();
        return byteArrayOutputStream.toByteArray();
    }

    public static /* synthetic */ void access$100(Metrics a) {
        a.submitData();
    }

    public void addCustomChart(CustomChart a) {
        Metrics a2;
        if (a == null) {
            throw new IllegalArgumentException((String)"Chart cannot be null!");
        }
        a2.charts.add((CustomChart)a);
    }

    private /* synthetic */ void submitData() {
        Metrics a;
        JSONObject jSONObject = a.getServerData();
        JSONArray jSONArray = new JSONArray();
        Iterator<E> iterator = Bukkit.getServicesManager().getKnownServices().iterator();
        do {
            if (!iterator.hasNext()) {
                jSONObject.put((Object)"plugins", (Object)jSONArray);
                new Thread((Runnable)new e((Metrics)a, (JSONObject)jSONObject)).start();
                return;
            }
            Class class_ = (Class)iterator.next();
            try {
                class_.getField((String)"B_STATS_VERSION");
            }
            catch (NoSuchFieldException noSuchFieldException) {
                continue;
            }
            try {
                jSONArray.add((Object)class_.getMethod((String)"getPluginData", new Class[0]).invoke((Object)Bukkit.getServicesManager().load((Class)class_), (Object[])new Object[0]));
            }
            catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException reflectiveOperationException) {
            }
        } while (true);
    }

    public Metrics(JavaPlugin a) {
        Metrics a2;
        if (a == null) {
            throw new IllegalArgumentException((String)"Plugin cannot be null!");
        }
        a2.plugin = a;
        Object object = new File((File)a.getDataFolder().getParentFile(), (String)"bStats");
        Object object22 = YamlConfiguration.loadConfiguration((File)(object = new File((File)object, (String)"config.yml")));
        if (!object22.isSet((String)"serverUuid")) {
            object22.addDefault((String)"enabled", (Object)Boolean.valueOf((boolean)true));
            object22.addDefault((String)"serverUuid", (Object)UUID.randomUUID().toString());
            object22.addDefault((String)"logFailedRequests", (Object)Boolean.valueOf((boolean)false));
            object22.options().header((String)"bStats collects some data for plugin authors like how many servers are using their plugins.\nTo honor their work, you should not disable it.\nThis has nearly no effect on the server performance!\nCheck out https://bStats.org/ to learn more :)").copyDefaults((boolean)true);
            try {
                object22.save((File)object);
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
        serverUUID = object22.getString((String)"serverUuid");
        logFailedRequests = object22.getBoolean((String)"logFailedRequests", (boolean)false);
        if (!object22.getBoolean((String)"enabled", (boolean)true)) return;
        boolean bl = false;
        for (Object object22 : Bukkit.getServicesManager().getKnownServices()) {
            try {
                ((Class)object22).getField((String)"B_STATS_VERSION");
                bl = true;
                break;
            }
            catch (NoSuchFieldException noSuchFieldException) {
            }
        }
        Bukkit.getServicesManager().register(Metrics.class, (Object)a2, (Plugin)a, (ServicePriority)ServicePriority.Normal);
        if (bl) return;
        a2.startSubmitting();
    }

    public static /* synthetic */ boolean access$300() {
        return logFailedRequests;
    }

    private /* synthetic */ JSONObject getServerData() {
        int n = Bukkit.getOnlinePlayers().size();
        int n2 = Bukkit.getOnlineMode() ? 1 : 0;
        String string = Bukkit.getVersion();
        string = string.substring((int)(string.indexOf((String)"MC: ") + 4), (int)(string.length() - 1));
        String string2 = System.getProperty((String)"java.version");
        String string3 = System.getProperty((String)"os.name");
        String string4 = System.getProperty((String)"os.arch");
        String string5 = System.getProperty((String)"os.version");
        int n3 = Runtime.getRuntime().availableProcessors();
        JSONObject jSONObject = new JSONObject();
        jSONObject.put((Object)"serverUUID", (Object)serverUUID);
        jSONObject.put((Object)"playerAmount", (Object)Integer.valueOf((int)n));
        jSONObject.put((Object)"onlineMode", (Object)Integer.valueOf((int)n2));
        jSONObject.put((Object)"bukkitVersion", (Object)string);
        jSONObject.put((Object)"javaVersion", (Object)string2);
        jSONObject.put((Object)"osName", (Object)string3);
        jSONObject.put((Object)"osArch", (Object)string4);
        jSONObject.put((Object)"osVersion", (Object)string5);
        jSONObject.put((Object)"coreCount", (Object)Integer.valueOf((int)n3));
        return jSONObject;
    }

    private /* synthetic */ void startSubmitting() {
        Metrics a;
        Timer timer = new Timer((boolean)true);
        timer.scheduleAtFixedRate((TimerTask)new C((Metrics)a, (Timer)timer), (long)300000L, (long)1800000L);
    }

    private static /* synthetic */ void sendData(JSONObject a) throws Exception {
        if (a == null) {
            throw new IllegalArgumentException((String)"Data cannot be null!");
        }
        if (Bukkit.isPrimaryThread()) {
            throw new IllegalAccessException((String)"This method must not be called from the main thread!");
        }
        HttpsURLConnection httpsURLConnection = (HttpsURLConnection)new URL((String)URL).openConnection();
        byte[] arrby = Metrics.compress((String)a.toString());
        httpsURLConnection.setRequestMethod((String)"POST");
        httpsURLConnection.addRequestProperty((String)"Accept", (String)"application/json");
        httpsURLConnection.addRequestProperty((String)"Connection", (String)"close");
        httpsURLConnection.addRequestProperty((String)"Content-Encoding", (String)"gzip");
        httpsURLConnection.addRequestProperty((String)"Content-Length", (String)String.valueOf((int)arrby.length));
        httpsURLConnection.setRequestProperty((String)"Content-Type", (String)"application/json");
        httpsURLConnection.setRequestProperty((String)"User-Agent", (String)"MC-Server/1");
        httpsURLConnection.setDoOutput((boolean)true);
        DataOutputStream dataOutputStream = new DataOutputStream((OutputStream)httpsURLConnection.getOutputStream());
        dataOutputStream.write((byte[])arrby);
        dataOutputStream.flush();
        dataOutputStream.close();
        httpsURLConnection.getInputStream().close();
    }

    public static /* synthetic */ JavaPlugin access$000(Metrics a) {
        return a.plugin;
    }
}

