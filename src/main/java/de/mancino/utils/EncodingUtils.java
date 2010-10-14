package de.mancino.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Encoding Utils
 *
 * @author mmancino
 */
public class EncodingUtils {

    /**
     * Translates a string into <code>application/x-www-form-urlencoded</code>
     * format using a specific encoding scheme. This method uses the
     * supplied encoding scheme to obtain the bytes for unsafe
     * characters.
     * {@link URLEncoder#encode(String, String)} is used to achieve this, but
     * the {@link UnsupportedEncodingException} is caught and converted into
     * a {@link RuntimeException}.
     *
     * @param   s   <code>String</code> to be translated.
     * @param   enc   The name of a supported
     *    <a href="../lang/package-summary.html#charenc">character
     *    encoding</a>.
     * @return  the translated <code>String</code>.
     */
    public static String urlEncode(final String s, final String enc) {
        try {
            return URLEncoder.encode(s, enc);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
