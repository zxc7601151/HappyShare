//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.happy.share.network.logger;

import android.text.TextUtils;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.Interceptor.Chain;

public class LoggingInterceptor implements Interceptor {
    private boolean isDebug;
    private LoggingInterceptor.Builder builder;

    private LoggingInterceptor(LoggingInterceptor.Builder builder) {
        this.builder = builder;
        this.isDebug = builder.isDebug;
    }

    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if(this.builder.getHeaders().size() > 0) {
            Headers headers = request.headers();
            Set<String> names = headers.names();
            Iterator<String> iterator = names.iterator();
            okhttp3.Request.Builder requestBuilder = request.newBuilder();
            requestBuilder.headers(this.builder.getHeaders());

            while(iterator.hasNext()) {
                String name = (String)iterator.next();
                requestBuilder.addHeader(name, headers.get(name));
            }

            request = requestBuilder.build();
        }

        if(this.isDebug && this.builder.getLevel() != Level.NONE) {
            RequestBody requestBody = request.body();
            String rSubtype = null;
            if(requestBody != null && requestBody.contentType() != null) {
                rSubtype = requestBody.contentType().subtype();
            }

            if(this.isNotFileRequest(rSubtype)) {
                Printer.printJsonRequest(this.builder, request);
            } else {
                Printer.printFileRequest(this.builder, request);
            }

            long st = System.nanoTime();
            Response response = chain.proceed(request);
            long chainMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - st);
            List<String> segmentList = request.url().encodedPathSegments();
            String header = response.headers().toString();
            int code = response.code();
            boolean isSuccessful = response.isSuccessful();
            String message = response.message();
            ResponseBody responseBody = response.body();
            MediaType contentType = responseBody.contentType();
            String subtype = null;
            if(contentType != null) {
                subtype = contentType.subtype();
            }

            if(this.isNotFileRequest(subtype)) {
                String bodyString = Printer.getJsonString(responseBody.string());
                String url = response.request().url().toString();
                Printer.printJsonResponse(this.builder, chainMs, isSuccessful, code, header, bodyString, segmentList, message, url);
                ResponseBody body = ResponseBody.create(contentType, bodyString);
                return response.newBuilder().body(body).build();
            } else {
                Printer.printFileResponse(this.builder, chainMs, isSuccessful, code, header, segmentList, message);
                return response;
            }
        } else {
            return chain.proceed(request);
        }
    }

    private boolean isNotFileRequest(String subtype) {
        return subtype != null && (subtype.contains("json") || subtype.contains("xml") || subtype.contains("plain") || subtype.contains("html"));
    }

    public static class Builder {
        private static String TAG = "LoggingI";
        private boolean isDebug;
        private int type = 4;
        private String requestTag;
        private String responseTag;
        private Level level;
        private okhttp3.Headers.Builder builder;
        private Logger logger;

        public Builder() {
            this.level = Level.BASIC;
            this.builder = new okhttp3.Headers.Builder();
        }

        int getType() {
            return this.type;
        }

        Level getLevel() {
            return this.level;
        }

        Headers getHeaders() {
            return this.builder.build();
        }

        String getTag(boolean isRequest) {
            return isRequest?(TextUtils.isEmpty(this.requestTag)?TAG:this.requestTag):(TextUtils.isEmpty(this.responseTag)?TAG:this.responseTag);
        }

        Logger getLogger() {
            return this.logger;
        }

        public LoggingInterceptor.Builder addHeader(String name, String value) {
            this.builder.set(name, value);
            return this;
        }

        public LoggingInterceptor.Builder setLevel(Level level) {
            this.level = level;
            return this;
        }

        public LoggingInterceptor.Builder tag(String tag) {
            TAG = tag;
            return this;
        }

        public LoggingInterceptor.Builder request(String tag) {
            this.requestTag = tag;
            return this;
        }

        public LoggingInterceptor.Builder response(String tag) {
            this.responseTag = tag;
            return this;
        }

        public LoggingInterceptor.Builder loggable(boolean isDebug) {
            this.isDebug = isDebug;
            return this;
        }

        public LoggingInterceptor.Builder log(int type) {
            this.type = type;
            return this;
        }

        public LoggingInterceptor.Builder logger(Logger logger) {
            this.logger = logger;
            return this;
        }

        public LoggingInterceptor build() {
            return new LoggingInterceptor(this);
        }
    }
}
