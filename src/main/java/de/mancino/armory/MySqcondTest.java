package de.mancino.armory;
import java.io.UnsupportedEncodingException; 
import java.security.MessageDigest; 
import java.security.NoSuchAlgorithmException; 

public class MySqcondTest {
    private static final String pw = "nkgseqpgy7a";


    private static String convertToHex(byte[] data) { 
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < data.length; i++) { 
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;
            do { 
                if ((0 <= halfbyte) && (halfbyte <= 9)) 
                    buf.append((char) ('0' + halfbyte));
                else 
                    buf.append((char) ('a' + (halfbyte - 10)));
                halfbyte = data[i] & 0x0F;
            } while(two_halfs++ < 1);
        } 
        return buf.toString();
    } 

    public static String SHA1(String pw) 
    throws NoSuchAlgorithmException, UnsupportedEncodingException  { 
        MessageDigest md;
        final String alg = "SHA-256";
        md = MessageDigest.getInstance(alg);
        byte[] sha1hash = new byte[80];
        String str = "d82997b46fcdffbdd2f356b89172c79b5a62afa7b3d93fa858a240b1600351f6";
        byte[] salt = hexStringToByteArray(str);
        md.update(salt);
        md.update(pw.getBytes("UTF8"));
        sha1hash = md.digest();
        return convertToHex(sha1hash);
    } 

    
    
    
    
    /**
     * @param args
     * @throws UnsupportedEncodingException 
     * @throws NoSuchAlgorithmException 
     */
    public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        System.err.println(SHA1(pw));
        System.err.println(SHA1(pw).length()/2);
    }
    
    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }
}
