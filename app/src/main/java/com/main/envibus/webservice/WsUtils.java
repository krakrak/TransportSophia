package com.main.envibus.webservice;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by KraKk on 14/11/2015.
 */
public class WsUtils {

    public static String toString (InputStream in) throws IOException
    {
        StringBuffer out = new StringBuffer();
        int n =1;
        while (n>0)
        {
            byte[] b = new byte[4096];
            n= in.read(b);

            if (n>0)
            {
                out.append(new String(b, 0, n));
            }

        }
        return out.toString();
    }
}
