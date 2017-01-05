package com.hearglobal.msp.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by lvzhouyang on 17/1/5.
 */
public class HttpUtil {
    private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);


    public static boolean post(String serverUrl, String data, long timeout) {
        StringBuilder responseBuilder = null;
        BufferedReader reader = null;
        OutputStreamWriter wr = null;

        try {
            URL url = new URL(serverUrl);
            URLConnection e = url.openConnection();
            e.setDoOutput(true);
            e.setConnectTimeout(5000);
            wr = new OutputStreamWriter(e.getOutputStream());
            wr.write(data);
            wr.flush();
            if(logger.isDebugEnabled()) {
                reader = new BufferedReader(new InputStreamReader(e.getInputStream()));
                responseBuilder = new StringBuilder();
                String line = null;

                while((line = reader.readLine()) != null) {
                    responseBuilder.append(line).append("\n");
                }

                logger.debug(responseBuilder.toString());
            }
        } catch (IOException var22) {
            logger.error("", var22);
        } finally {
            if(wr != null) {
                try {
                    wr.close();
                } catch (IOException var21) {
                    logger.error("close error", var21);
                }
            }

            if(reader != null) {
                try {
                    reader.close();
                } catch (IOException var20) {
                    logger.error("close error", var20);
                }
            }

        }

        return false;
    }
}
