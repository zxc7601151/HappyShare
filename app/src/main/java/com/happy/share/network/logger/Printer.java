//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.happy.share.network.logger;

import android.text.TextUtils;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import okhttp3.Request;
import okio.Buffer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class Printer {
    private static final int JSON_INDENT = 3;
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");
    private static final String DOUBLE_SEPARATOR;
    private static final String[] OMITTED_RESPONSE;
    private static final String[] OMITTED_REQUEST;
    private static final String N = "\n";
    private static final String T = "\t";
    private static final String REQUEST_UP_LINE = "┌────── Request ────────────────────────────────────────────────────────────────────────";
    private static final String END_LINE = "└───────────────────────────────────────────────────────────────────────────────────────";
    private static final String RESPONSE_UP_LINE = "┌────── Response ───────────────────────────────────────────────────────────────────────";
    private static final String BODY_TAG = "Body:";
    private static final String URL_TAG = "URL: ";
    private static final String METHOD_TAG = "Method: @";
    private static final String HEADERS_TAG = "Headers:";
    private static final String STATUS_CODE_TAG = "Status Code: ";
    private static final String RECEIVED_TAG = "Received in: ";
    private static final String CORNER_UP = "┌ ";
    private static final String CORNER_BOTTOM = "└ ";
    private static final String CENTER_LINE = "├ ";
    private static final String DEFAULT_LINE = "│ ";

    protected Printer() {
        throw new UnsupportedOperationException();
    }

    private static boolean isEmpty(String line) {
        return TextUtils.isEmpty(line) || "\n".equals(line) || "\t".equals(line) || TextUtils.isEmpty(line.trim());
    }

    static void printJsonRequest(LoggingInterceptor.Builder builder, Request request) {
        String requestBody = LINE_SEPARATOR + "Body:" + LINE_SEPARATOR + bodyToString(request);
        String tag = builder.getTag(true);
        if(builder.getLogger() == null) {
            I.log(builder.getType(), tag, "┌────── Request ────────────────────────────────────────────────────────────────────────");
        }

        logLines(builder.getType(), tag, new String[]{"URL: " + request.url()}, builder.getLogger(), false);
        logLines(builder.getType(), tag, getRequest(request, builder.getLevel()), builder.getLogger(), true);
        if(builder.getLevel() == Level.BASIC || builder.getLevel() == Level.BODY) {
            logLines(builder.getType(), tag, requestBody.split(LINE_SEPARATOR), builder.getLogger(), true);
        }

        if(builder.getLogger() == null) {
            I.log(builder.getType(), tag, "└───────────────────────────────────────────────────────────────────────────────────────");
        }

    }

    static void printJsonResponse(LoggingInterceptor.Builder builder, long chainMs, boolean isSuccessful, int code, String headers, String bodyString, List<String> segments, String message, String responseUrl) {
        String responseBody = LINE_SEPARATOR + "Body:" + LINE_SEPARATOR + getJsonString(bodyString);
        String tag = builder.getTag(false);
        String[] urlLine = new String[]{"URL: " + responseUrl, "\n"};
        String[] response = getResponse(headers, chainMs, code, isSuccessful, builder.getLevel(), segments, message);
        if(builder.getLogger() == null) {
            I.log(builder.getType(), tag, "┌────── Response ───────────────────────────────────────────────────────────────────────");
        }

        logLines(builder.getType(), tag, urlLine, builder.getLogger(), true);
        logLines(builder.getType(), tag, response, builder.getLogger(), true);
        if(builder.getLevel() == Level.BASIC || builder.getLevel() == Level.BODY) {
            logLines(builder.getType(), tag, responseBody.split(LINE_SEPARATOR), builder.getLogger(), true);
        }

        if(builder.getLogger() == null) {
            I.log(builder.getType(), tag, "└───────────────────────────────────────────────────────────────────────────────────────");
        }

    }

    static void printFileRequest(LoggingInterceptor.Builder builder, Request request) {
        String tag = builder.getTag(true);
        if(builder.getLogger() == null) {
            I.log(builder.getType(), tag, "┌────── Request ────────────────────────────────────────────────────────────────────────");
        }

        logLines(builder.getType(), tag, new String[]{"URL: " + request.url()}, builder.getLogger(), false);
        logLines(builder.getType(), tag, getRequest(request, builder.getLevel()), builder.getLogger(), true);
        if(builder.getLevel() == Level.BASIC || builder.getLevel() == Level.BODY) {
            logLines(builder.getType(), tag, OMITTED_REQUEST, builder.getLogger(), true);
        }

        if(builder.getLogger() == null) {
            I.log(builder.getType(), tag, "└───────────────────────────────────────────────────────────────────────────────────────");
        }

    }

    static void printFileResponse(LoggingInterceptor.Builder builder, long chainMs, boolean isSuccessful, int code, String headers, List<String> segments, String message) {
        String tag = builder.getTag(false);
        if(builder.getLogger() == null) {
            I.log(builder.getType(), tag, "┌────── Response ───────────────────────────────────────────────────────────────────────");
        }

        logLines(builder.getType(), tag, getResponse(headers, chainMs, code, isSuccessful, builder.getLevel(), segments, message), builder.getLogger(), true);
        logLines(builder.getType(), tag, OMITTED_RESPONSE, builder.getLogger(), true);
        if(builder.getLogger() == null) {
            I.log(builder.getType(), tag, "└───────────────────────────────────────────────────────────────────────────────────────");
        }

    }

    private static String[] getRequest(Request request, Level level) {
        String header = request.headers().toString();
        boolean loggableHeader = level == Level.HEADERS || level == Level.BASIC;
        String log = "Method: @" + request.method() + DOUBLE_SEPARATOR + (isEmpty(header)?"":(loggableHeader?"Headers:" + LINE_SEPARATOR + dotHeaders(header):""));
        return log.split(LINE_SEPARATOR);
    }

    private static String[] getResponse(String header, long tookMs, int code, boolean isSuccessful, Level level, List<String> segments, String message) {
        boolean loggableHeader = level == Level.HEADERS || level == Level.BASIC;
        String segmentString = slashSegments(segments);
        String log = (!TextUtils.isEmpty(segmentString)?segmentString + " - ":"") + "is success : " + isSuccessful + " - " + "Received in: " + tookMs + "ms" + DOUBLE_SEPARATOR + "Status Code: " + code + " / " + message + DOUBLE_SEPARATOR + (isEmpty(header)?"":(loggableHeader?"Headers:" + LINE_SEPARATOR + dotHeaders(header):""));
        return log.split(LINE_SEPARATOR);
    }

    private static String slashSegments(List<String> segments) {
        StringBuilder segmentString = new StringBuilder();
        Iterator var2 = segments.iterator();

        while(var2.hasNext()) {
            String segment = (String)var2.next();
            segmentString.append("/").append(segment);
        }

        return segmentString.toString();
    }

    private static String dotHeaders(String header) {
        String[] headers = header.split(LINE_SEPARATOR);
        StringBuilder builder = new StringBuilder();
        String tag = "─ ";
        if(headers.length > 1) {
            for(int i = 0; i < headers.length; ++i) {
                if(i == 0) {
                    tag = "┌ ";
                } else if(i == headers.length - 1) {
                    tag = "└ ";
                } else {
                    tag = "├ ";
                }

                builder.append(tag).append(headers[i]).append("\n");
            }
        } else {
            String[] var8 = headers;
            int var5 = headers.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                String item = var8[var6];
                builder.append(tag).append(item).append("\n");
            }
        }

        return builder.toString();
    }

    private static void logLines(int type, String tag, String[] lines, Logger logger, boolean withLineSize) {
        String[] var5 = lines;
        int var6 = lines.length;

        for(int var7 = 0; var7 < var6; ++var7) {
            String line = var5[var7];
            int lineLength = line.length();
            int MAX_LONG_SIZE = withLineSize?110:lineLength;

            for(int i = 0; i <= lineLength / MAX_LONG_SIZE; ++i) {
                int start = i * MAX_LONG_SIZE;
                int end = (i + 1) * MAX_LONG_SIZE;
                end = end > line.length()?line.length():end;
                if(logger == null) {
                    I.log(type, tag, "│ " + line.substring(start, end));
                } else {
                    logger.log(type, tag, line.substring(start, end));
                }
            }
        }

    }

    private static String bodyToString(Request request) {
        try {
            Request copy = request.newBuilder().build();
            Buffer buffer = new Buffer();
            if(copy.body() == null) {
                return "";
            } else {
                copy.body().writeTo(buffer);
                return getJsonString(buffer.readUtf8());
            }
        } catch (IOException var3) {
            return "{\"err\": \"" + var3.getMessage() + "\"}";
        }
    }

    static String getJsonString(String msg) {
        String message;
        try {
            if(msg.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(msg);
                message = jsonObject.toString(3);
            } else if(msg.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(msg);
                message = jsonArray.toString(3);
            } else {
                message = msg;
            }
        } catch (JSONException var3) {
            message = msg;
        }

        return message;
    }

    static {
        DOUBLE_SEPARATOR = LINE_SEPARATOR + LINE_SEPARATOR;
        OMITTED_RESPONSE = new String[]{LINE_SEPARATOR, "Omitted response body"};
        OMITTED_REQUEST = new String[]{LINE_SEPARATOR, "Omitted request body"};
    }
}
