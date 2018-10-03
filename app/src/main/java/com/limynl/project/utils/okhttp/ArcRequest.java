package com.limynl.project.utils.okhttp;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Handler;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * desc   : dd请求
 */
public class ArcRequest {

    private static final long LOAD_TIME = 1500;
    private String regex = "^[-\\+]?[\\d]*$";
    private String dfPattern = "yyyy-MM-dd HH:mm:ss";

    private String mUrl;
    private Gson mGson;
    private Handler mDelivery;
    private OkHttpClient mOkHttpClient;

    // 请求头
    private LinkedHashMap<String, String> headers = new LinkedHashMap<>();
    // 参数
    private LinkedHashMap<String, String> params = new LinkedHashMap<>();
    // 是否加载延迟
    private boolean isLoadDelay;
    // 时间戳
    private long timeStamp;
    // 加载dialog
    private ProgressDialog mProgressDialog;

    /**
     * 构造函数
     */
    public ArcRequest() {
        OkUtil okUtil = OkUtil.newInstance();
        mDelivery = okUtil.getDelivery();
        mOkHttpClient = okUtil.getOkHttpClient();

        // json date format
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                String timeStr = json.getAsJsonPrimitive().getAsString();
                Pattern pattern = Pattern.compile(regex);
                if (pattern.matcher(timeStr).matches()) {
                    try {
                        Long dateL = Long.valueOf(timeStr);
                        return new Date(dateL * 1000);
                    } catch (NumberFormatException e) {
                        return null;
                    }
                } else {
                    @SuppressLint("SimpleDateFormat")
                    SimpleDateFormat format = new SimpleDateFormat(dfPattern);
                    try {
                        return format.parse(timeStr);
                    } catch (ParseException e) {
                        return null;
                    }
                }
            }
        });
        mGson = gsonBuilder.create();
    }

    /**
     * 设置url
     */
    public ArcRequest url(@NonNull String url) {
        this.mUrl = url;
        return this;
    }

    /**
     * 添加请求头
     */
    public ArcRequest addHeader(@NonNull String key, String value) {
        if (value == null) return this;
        this.headers.put(key, value);
        return this;
    }

    /**
     * 添加参数
     */
    public ArcRequest addParam(@NonNull String key, Integer value) {
        if (value == null) return this;
        this.params.put(key, String.valueOf(value));
        return this;
    }

    /**
     * 添加参数
     */
    public ArcRequest addParam(@NonNull String key, String value) {
        if (value == null) return this;
        this.params.put(key, value);
        return this;
    }

    /**
     * 设置加载延迟
     */
    public ArcRequest setLoadDelay() {
        this.isLoadDelay = true;
        this.timeStamp = System.currentTimeMillis();
        return this;
    }

    /**
     * 设置加载动画
     */
    public ArcRequest setProgressDialog(ProgressDialog progressDialog) {
        if (progressDialog != null) {
            this.mProgressDialog = progressDialog;
        }
        return this;
    }

    /**
     * header
     */
    private Headers getHeaders() {
        Headers.Builder headerBuilder = new Headers.Builder();
        if (headers != null && !headers.isEmpty()) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                headerBuilder.add(entry.getKey(), entry.getValue());
            }
        }
        return headerBuilder.build();
    }

    /**
     * from body
     */
    private RequestBody getFormBody() {
        FormBody.Builder builder = new FormBody.Builder();
        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.add(entry.getKey(), entry.getValue());
            }
        }
        return builder.build();
    }

    /**
     * request
     */
    private Request getRequest() {
        Request.Builder builder = new Request.Builder();
        // header
        builder.headers(getHeaders());
        // url
        builder.url(mUrl);
        // from body
        return builder.post(getFormBody()).build();
    }

    /**
     * 异步请求
     */
    public void execute(@NonNull final ArcCallback callback) {
        loadShow();
        mOkHttpClient.newCall(getRequest()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                setOnError(e, callback);
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) {
                try {
                    ResponseBody body = response.body();
                    if (body != null) {
                        setOnSuccess(body.charStream(), callback);
                    } else {
                        setOnError(new Exception("body is null"), callback);
                    }
                } catch (Exception e) {
                    setOnError( e, callback);
                }

            }
        });
    }

    /**
     * 设置请求成功回调
     */
    private void setOnSuccess(final Reader reader, final ArcCallback callback) {
        mDelivery.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadDismiss();
                try {
                    callback.onSuccess(mGson.fromJson(reader, callback.getType()));
                } catch (Exception e) {
                    callback.onError(e);
                }
            }
        }, getLoadTime());
    }

    /**
     * 设置请求失败回调
     */
    private void setOnError(final Exception e, final ArcCallback callback) {
        mDelivery.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadDismiss();
                callback.onError(e);
            }
        }, getLoadTime());
    }

    /**
     * 获取加载时间
     */
    private long getLoadTime() {
        if (isLoadDelay) {
            timeStamp = System.currentTimeMillis() - timeStamp;
            timeStamp = timeStamp > LOAD_TIME ? 0 : LOAD_TIME - timeStamp;
        } else {
            timeStamp = 0;
        }
        return timeStamp;
    }

    /**
     * 展示加载动画
     */
    private void loadShow() {
        if (mProgressDialog != null && !mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }

    /**
     * 关闭加载动画
     */
    private void loadDismiss() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
}
