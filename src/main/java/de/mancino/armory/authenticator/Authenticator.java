package de.mancino.armory.authenticator;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.bouncycastle.crypto.digests.SHA1Digest;
import org.bouncycastle.crypto.engines.RSAEngine;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.RSAKeyParameters;

public class Authenticator {
    /*--- Constants ---*/
    /// Example: str_consts.get("EU").get("auth-url-enroll")
    public static Map<String, Map<String,String>> str_consts;

    /*--- Variables that define the Authenticator instance (serial, ...) ---*/
    /// Difference between server & local time [ms]
    public long time_diff;

    /// Serial number, {EU|US}-NNNN-NNNN-NNNN
    public String str_serial;

    /// Token, 40 hexadecimal characters
    public String str_token;

    /*--- Internal state variables ---*/
    /// 37-byte XOR key, used to encrypt Token+Serial
    byte[] xor_key_random37;

    /// Token in binary form (20 bytes)
    byte[] token;

    /// last generated key
    String last_key_string;

    /// time of last generated key
    long last_key_time;

    /// next (future) key
    String future_key;

    public Authenticator() {
        str_consts = new HashMap<String, Map<String, String>>();
        str_consts.put("US", new HashMap<String,String>());
        str_consts.put("EU", new HashMap<String,String>());
        // http://mobile-service.blizzard.com ??
        str_consts.get("US").put("auth-url-enroll",   "http://m.us.mobileservice.blizzard.com/enrollment/enroll.htm");
        str_consts.get("US").put("auth-url-enroll2",   "http://m.us.mobileservice.blizzard.com/enrollment/enroll2.htm");
        str_consts.get("US").put("auth-url-time",     "http://m.us.mobileservice.blizzard.com/enrollment/time.htm");
        str_consts.get("US").put("auth-url-init-restore",     "http://m.us.mobileservice.blizzard.com/enrollment/initiatePaperRestore.htm");
        //http://mobile-service.blizzard.com
        str_consts.get("US").put("auth-url-validate-restore",     "http://mobile-service.blizzard.com/enrollment/validatePaperRestore.htm");
        str_consts.get("US").put("auth-url-setup",    "http://www.battle.net/bma");
        str_consts.get("US").put("auth-url-acctmgmt", "http://www.battle.net/account/management");
        str_consts.get("US").put("auth-region",       "US");
        str_consts.get("US").put("auth-phone-model",  "Motorola RAZR v3");

        str_consts.get("EU").put("auth-url-enroll",   "http://m.eu.mobileservice.blizzard.com/enrollment/enroll.htm");
        str_consts.get("EU").put("auth-url-enroll2",   "http://m.eu.mobileservice.blizzard.com/enrollment/enroll2.htm");
        str_consts.get("EU").put("auth-url-time",     "http://m.eu.mobileservice.blizzard.com/enrollment/time.htm");
        str_consts.get("EU").put("auth-url-init-restore",     "http://mobile-service.blizzard.com/enrollment/initiatePaperRestore.htm");
        str_consts.get("EU").put("auth-url-validate-restore",     "http://mobile-service.blizzard.com/enrollment/validatePaperRestore.htm");
        str_consts.get("EU").put("auth-url-setup",    "http://eu.battle.net/bma");
        str_consts.get("EU").put("auth-url-acctmgmt", "http://eu.battle.net/account/management");
        str_consts.get("EU").put("auth-region",       "EU");
        str_consts.get("EU").put("auth-phone-model",  "Motorola RAZR v3");

        last_key_time = 0;
        last_key_string = null;
    }

    String getRegion() {
        return (str_serial == null) ? "" : str_serial.substring(0,2);
    }

    /**
     * @return time [ms] since the last key change
     */
    public long timeSinceLastKeyChange() {
        return (System.currentTimeMillis() + time_diff) % 30000;
    }

    /**
     * @return The current authentication key
     */
    public String getAuthKey() {
        long time_now = System.currentTimeMillis();
        long time_div = (time_now  + time_diff) / 30000;
        long time_last_div = (last_key_time + time_diff) / 30000;
        if( time_div != time_last_div ) {
            last_key_time = time_now;
            last_key_string = getAuthKeyString(time_div);
            future_key = getAuthKeyString(time_div+1);
        }
        return last_key_string;
    }

    /**
     * @return The next authentication key (future).
     */
    public String getAuthKeyFuture() {
        getAuthKey();
        return future_key;
    }

    public void setSerial(String s_token, String s_serial, long t_diff) {
        this.time_diff = t_diff;
        checkAndStoreTokenSerial(s_token, s_serial);
        if( str_token == null || str_serial == null )
            throw new RuntimeException("invalid token or serial number");
        parseTokenString();
    }

    /**
     * @param time_divided (currentTimeMillis() + time_offset) / 30000
     * @return The current authentication key
     */
    private String getAuthKeyString(long time_divided) {
        int i = AuthKeyGen.calcAuthKey(token, time_divided);
        String authkey = "";
        for (int j = 0; j < 8; j++) {
            authkey = i % 10 + authkey;
            i /= 10;
        }

        return authkey;
    }

    private void checkAndStoreTokenSerial(String token, String serial) {
        boolean valid_token = false;
        boolean valid_serial = false;
        if (token.length() == 40) {
            valid_token = true;
            int i = 0;
            do {
                if (i >= 40) {
                    break;
                }
                char c = token.charAt(i);
                if (!(valid_token &= c >= '0' && c <= '9' || c >= 'a' && c <= 'f'
                        || c >= 'A' && c <= 'F')) {
                    break;
                }
                i++;
            } while (true);
        }
        if (serial.length() == 17) {
            valid_serial = true;
            int i = 0;
            do {
                if (i >= 17) {
                    break;
                }
                char c = serial.charAt(i);
                if (i < 2) {
                    valid_serial &= c == 'E' || c == 'U' || c == 'S';
                } else {
                    valid_serial &= c >= '0' && c <= '9' || c == '-';
                }
                if (!valid_serial) {
                    break;
                }
                i++;
            } while (true);
        }
        if (valid_token && valid_serial) {
            str_token = token;
            str_serial = serial;
            return;
        } else {
            str_token = null;
            str_serial = null;
            return;
        }
    }
    public void restore(final String serial, final String restoreCode) throws Exception {
        // send the request to the server to get our challenge
        final String submitSerial = serial.replace("-", "");
        final byte[] serialBytes = submitSerial.getBytes();
        URL url_sync = new URL(str_consts.get(getRegion()).get("auth-url-init-restore"));
        HttpURLConnection conn = (HttpURLConnection) url_sync.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-type", "application/octet-stream");
        conn.setRequestProperty("Accept", "text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2");
        conn.setRequestProperty("Content-length", Integer.toString(submitSerial.length()));
        conn.setReadTimeout(15000);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.connect();
        conn.getOutputStream().write(submitSerial.getBytes());

        // load back the buffer - should only be a byte[32]
        byte[] challenge = new byte[32];
        InputStream inp_stream = conn.getInputStream();
        inp_stream.read(challenge, 0, 32);
        inp_stream.close();
        conn.disconnect();

        // only take the first 10 bytes of the restore code and encode to byte taking count of the missing chars
        byte[] restoreCodeBytes = new byte[10];
        char[] arrayOfChar =  restoreCode.toUpperCase().toCharArray();
        for (int i = 0; i < 10; i++) {
            restoreCodeBytes[i] = convertRestoreCodeCharToByte(arrayOfChar[i]);
        }

       // build the response to the challenge
        HMac hmac = new HMac(new SHA1Digest());
        hmac.init(new KeyParameter(restoreCodeBytes));
        byte hashdata[] = new byte[serialBytes.length + challenge.length];
        System.arraycopy(serialBytes, 0, hashdata, 0, serialBytes.length);
        System.arraycopy(challenge, 0, hashdata, serialBytes.length, challenge.length);
        hmac.update(hashdata, 0, hashdata.length);
        byte hash[] = new byte[hmac.getMacSize()];
        hmac.doFinal(hash, 0);

        // create a random key
        byte[] oneTimePad = BlizzCrypt.genRandomBytes(20); //??? Should work the same...

        // concatanate the hash and key
        byte[] hashkey = new byte[hash.length + oneTimePad.length];
        System.arraycopy(hash, 0, hashkey, 0, hash.length);
        System.arraycopy(oneTimePad, 0, hashkey, hash.length, oneTimePad.length);


        // encrypt the data with BMA public key
        final String ENROLL_MODULUS =
                "955e4bd989f3917d2f15544a7e0504eb9d7bb66b6f8a2fe470e453c779200e5e" +
                "3ad2e43a02d06c4adbd8d328f1a426b83658e88bfd949b2af4eaf30054673a14" +
                "19a250fa4cc1278d12855b5b25818d162c6e6ee2ab4a350d401d78f6ddb99711" +
                "e72626b48bd8b5b0b7f3acf9ea3c9e0005fee59e19136cdb7c83f2ab8b0a2a99";
        final String ENROLL_EXPONENT =
                "0101";
        RSAEngine rsa = new RSAEngine();
        RSAKeyParameters keyParameters = new RSAKeyParameters(false, new BigInteger(ENROLL_MODULUS, 16), new BigInteger(ENROLL_EXPONENT, 16));
        rsa.init(true, keyParameters);
        byte[] encrypted = rsa.processBlock(hashkey, 0, hashkey.length);

        // prepend the serial to the encrypted data
        byte[] postbytes = new byte[serialBytes.length + encrypted.length];
        System.arraycopy(serialBytes, 0, postbytes, 0, serialBytes.length);
        System.arraycopy(encrypted, 0, postbytes, serialBytes.length, encrypted.length);

        // send the challenge response back to the server
        URL url_enroll = new URL(str_consts.get(getRegion()).get("auth-url-validate-restore"));
        conn = (HttpURLConnection) url_enroll.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-type", "application/octet-stream");
        //conn.setRequestProperty("Accept", "text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2");
        conn.setRequestProperty("Content-length", String.valueOf(postbytes.length));
        conn.setReadTimeout(15000);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.connect();
        conn.getOutputStream().write(postbytes);

        // load back the buffer - should only be a byte[32]
        byte[] secretKey = new byte[32];
        /*
  int code = (int)((HttpWebResponse)we.Response).StatusCode;
                                if (code >= 500 && code < 600)
                                {
                                        throw new InvalidRestoreResponseException(string.Format("No response from server ({0}). Perhaps maintainence?", code));
                                }
                                else if (code >= 600 && code < 700)
                                {
                                        throw new InvalidRestoreCodeException("Invalid serial number or restore code.");
                                }
                                else
                                {
                                        throw new InvalidRestoreResponseException(string.Format("Error communicating with server: {0} - {1}", code, ((HttpWebResponse)we.Response).StatusDescription));
                                }
         *
         */
        inp_stream = conn.getInputStream();
        inp_stream.read(secretKey, 0, 32);
        inp_stream.close();
        conn.disconnect();

        // xor the returned data key with our pad to get the actual secret key
        for (int i = oneTimePad.length - 1; i >= 0; i--)
        {
                secretKey[i] ^= oneTimePad[i];
        }

        token = secretKey;
        /*

                        // xor the returned data key with our pad to get the actual secret key
                        for (int i = oneTimePad.Length - 1; i >= 0; i--)
                        {
                                secretKey[i] ^= oneTimePad[i];
                        }

                        // set the authenticator data
                        SecretKey = secretKey;
                        if (serial.Length == 14)
                        {
                                Serial = serial.Substring(0, 2).ToUpper() + "-" + serial.Substring(2, 4) + "-" + serial.Substring(6, 4) + "-" + serial.Substring(10, 4);
                        }
                        else
                        {
                                Serial = serial.ToUpper();
                        }
                        // restore code is ok
                        RestoreCodeVerified = true;
                        // sync the time
                        ServerTimeDiff = 0L;
                        Sync();
         */
    }
    /*
 /// <summary>
                /// Restore an authenticator from the serial number and restore code.
                /// </summary>
                /// <param name="serial">serial code, e.g. US-1234-5678-1234</param>
                /// <param name="restoreCode">restore code given on enroll, 10 chars.</param>
                public override void Restore(string serial, string restoreCode)
                {
                        // get the serial data
                        byte[] serialBytes = Encoding.UTF8.GetBytes(serial.ToUpper().Replace("-", string.Empty));

                        // send the request to the server to get our challenge
                        HttpWebRequest request = (HttpWebRequest)WebRequest.Create(GetMobileUrl(serial) + RESTORE_PATH);
                        request.Method = "POST";
                        request.ContentType = "application/octet-stream";
                        request.ContentLength = serialBytes.Length;
                        Stream requestStream = request.GetRequestStream();
                        requestStream.Write(serialBytes, 0, serialBytes.Length);
                        requestStream.Close();
                        byte[] challenge = null;
                        try
                        {
                                using (HttpWebResponse response = (HttpWebResponse)request.GetResponse())
                                {
                                        // OK?
                                        if (response.StatusCode != HttpStatusCode.OK)
                                        {
                                                throw new InvalidRestoreResponseException(string.Format("{0}: {1}", (int)response.StatusCode, response.StatusDescription));
                                        }

                                        // load back the buffer - should only be a byte[32]
                                        using (MemoryStream ms = new MemoryStream())
                                        {
                                                using (Stream bs = response.GetResponseStream())
                                                {
                                                        byte[] temp = new byte[RESPONSE_BUFFER_SIZE];
                                                        int read;
                                                        while ((read = bs.Read(temp, 0, RESPONSE_BUFFER_SIZE)) != 0)
                                                        {
                                                                ms.Write(temp, 0, read);
                                                        }
                                                        challenge = ms.ToArray();

                                                        // check it is correct size
                                                        if (challenge.Length != RESTOREINIT_BUFFER_SIZE)
                                                        {
                                                                throw new InvalidRestoreResponseException(string.Format("Invalid response data size (expected 32 got {0})", challenge.Length));
                                                        }
                                                }
                                        }
                                }
                        }
                        catch (WebException we)
                        {
                                int code = (int)((HttpWebResponse)we.Response).StatusCode;
                                if (code >= 500 && code < 600)
                                {
                                        throw new InvalidRestoreResponseException(string.Format("No response from server ({0}). Perhaps maintainence?", code));
                                }
                                else
                                {
                                        throw new InvalidRestoreResponseException(string.Format("Error communicating with server: {0} - {1}", code, ((HttpWebResponse)we.Response).StatusDescription));
                                }
                        }

                        // only take the first 10 bytes of the restore code and encode to byte taking count of the missing chars
                        byte[] restoreCodeBytes = new byte[10];
                        char[] arrayOfChar = restoreCode.ToUpper().ToCharArray();
                        for (int i = 0; i < 10; i++)
                        {
                                restoreCodeBytes[i] = ConvertRestoreCodeCharToByte(arrayOfChar[i]);
                        }

                        // build the response to the challenge
                        HMac hmac = new HMac(new Sha1Digest());
                        hmac.Init(new KeyParameter(restoreCodeBytes));
                        byte[] hashdata = new byte[serialBytes.Length + challenge.Length];
                        Array.Copy(serialBytes, 0, hashdata, 0, serialBytes.Length);
                        Array.Copy(challenge, 0, hashdata, serialBytes.Length, challenge.Length);
                        hmac.BlockUpdate(hashdata, 0, hashdata.Length);
                        byte[] hash = new byte[hmac.GetMacSize()];
                        hmac.DoFinal(hash, 0);

                        // create a random key
                        byte[] oneTimePad = CreateOneTimePad(20);

                        // concatanate the hash and key
                        byte[] hashkey = new byte[hash.Length + oneTimePad.Length];
                        Array.Copy(hash, 0, hashkey, 0, hash.Length);
                        Array.Copy(oneTimePad, 0, hashkey, hash.Length, oneTimePad.Length);

                        // encrypt the data with BMA public key
                        RsaEngine rsa = new RsaEngine();
                        rsa.Init(true, new RsaKeyParameters(false, new Org.BouncyCastle.Math.BigInteger(ENROLL_MODULUS, 16), new Org.BouncyCastle.Math.BigInteger(ENROLL_EXPONENT, 16)));
                        byte[] encrypted = rsa.ProcessBlock(hashkey, 0, hashkey.Length);

                        // prepend the serial to the encrypted data
                        byte[] postbytes = new byte[serialBytes.Length + encrypted.Length];
                        Array.Copy(serialBytes, 0, postbytes, 0, serialBytes.Length);
                        Array.Copy(encrypted, 0, postbytes, serialBytes.Length, encrypted.Length);

                        // send the challenge response back to the server
                        request = (HttpWebRequest)WebRequest.Create(GetMobileUrl(serial) + RESTOREVALIDATE_PATH);
                        request.Method = "POST";
                        request.ContentType = "application/octet-stream";
                        request.ContentLength = postbytes.Length;
                        requestStream = request.GetRequestStream();
                        requestStream.Write(postbytes, 0, postbytes.Length);
                        requestStream.Close();
                        byte[] secretKey = null;
                        try {
                                using (HttpWebResponse response = (HttpWebResponse)request.GetResponse())
                                {
                                        // OK?
                                        if (response.StatusCode != HttpStatusCode.OK)
                                        {
                                                throw new InvalidRestoreResponseException(string.Format("{0}: {1}", (int)response.StatusCode, response.StatusDescription));
                                        }

                                        // load back the buffer - should only be a byte[32]
                                        using (MemoryStream ms = new MemoryStream())
                                        {
                                                using (Stream bs = response.GetResponseStream())
                                                {
                                                        byte[] temp = new byte[RESPONSE_BUFFER_SIZE];
                                                        int read;
                                                        while ((read = bs.Read(temp, 0, RESPONSE_BUFFER_SIZE)) != 0)
                                                        {
                                                                ms.Write(temp, 0, read);
                                                        }
                                                        secretKey = ms.ToArray();

                                                        // check it is correct size
                                                        if (secretKey.Length != RESTOREVALIDATE_BUFFER_SIZE)
                                                        {
                                                                throw new InvalidRestoreResponseException(string.Format("Invalid response data size (expected " + RESTOREVALIDATE_BUFFER_SIZE + " got {0})", secretKey.Length));
                                                        }
                                                }
                                        }
                                }
                        }
                        catch (WebException we)
                        {
                                int code = (int)((HttpWebResponse)we.Response).StatusCode;
                                if (code >= 500 && code < 600)
                                {
                                        throw new InvalidRestoreResponseException(string.Format("No response from server ({0}). Perhaps maintainence?", code));
                                }
                                else if (code >= 600 && code < 700)
                                {
                                        throw new InvalidRestoreCodeException("Invalid serial number or restore code.");
                                }
                                else
                                {
                                        throw new InvalidRestoreResponseException(string.Format("Error communicating with server: {0} - {1}", code, ((HttpWebResponse)we.Response).StatusDescription));
                                }
                        }

                        // xor the returned data key with our pad to get the actual secret key
                        for (int i = oneTimePad.Length - 1; i >= 0; i--)
                        {
                                secretKey[i] ^= oneTimePad[i];
                        }

                        // set the authenticator data
                        SecretKey = secretKey;
                        if (serial.Length == 14)
                        {
                                Serial = serial.Substring(0, 2).ToUpper() + "-" + serial.Substring(2, 4) + "-" + serial.Substring(6, 4) + "-" + serial.Substring(10, 4);
                        }
                        else
                        {
                                Serial = serial.ToUpper();
                        }
                        // restore code is ok
                        RestoreCodeVerified = true;
                        // sync the time
                        ServerTimeDiff = 0L;
                        Sync();
                }
     */

    /// <summary>
    /// Convert a char to a byte but with appropriate mapping to exclude I,L,O and S. E.g. A=10 but J=18 not 19 (as I is missing)
    /// </summary>
    /// <param name="c">char to convert.</param>
    /// <returns>byte value of restore code char</returns>
    private  byte convertRestoreCodeCharToByte(char c) {
            if (c >= '0' && c <= '9')
            {
                    return (byte)(c - '0');
            }
            else
            {
                    byte index = (byte)(c + 10 - 65);
                    if (c >= 'I')
                    {
                            index--;
                    }
                    if (c >= 'L')
                    {
                            index--;
                    }
                    if (c >= 'O')
                    {
                            index--;
                    }
                    if (c >= 'S')
                    {
                            index--;
                    }

                    return index;
            }
    }


    /**
     * Converts str_token (40 hex chars) to token (20 bytes)
     */
    private void parseTokenString() {
        if (str_serial != null && str_token != null) {
            byte btoken[] = new byte[20];
            for (int i = 0; i < str_token.length(); i += 2) {
                byte b = (byte) ((hexcharToNibble(str_token.charAt(i)) << 4) + hexcharToNibble(str_token
                        .charAt(i + 1)));
                btoken[i >> 1] = b;
            }

            token = btoken;
            return;
        }
    }

    /// only related to the first contact, generates part of the POST data
    byte[] generateEnrollmentMash(String region) {
        xor_key_random37 = BlizzCrypt.genRandomBytes(37);

        byte mash[] = new byte[55];
        System.arraycopy(xor_key_random37, 0, mash, 0, 37);
        byte tmp[];
        System.arraycopy(tmp = region.getBytes(), 0, mash, 37, Math.min(tmp.length, 2));
        System.arraycopy(tmp = str_consts.get(region).get("auth-phone-model").getBytes(), 0, mash, 39, Math
                .min(tmp.length, 16));
        return mash;
    }

    /**
     * @param abyte array of bytes
     * @param offset offset
     * @return long integer (QWORD), read from abyte[offset]
     */
    static long bytesToLong(byte abyte[], int offset)
    {
        long result = 0L;
        for(int k = offset; k < offset + 8; k++)
        {
            result <<= 8;
            long l1 = abyte[k] & 0xff;
            result += l1;
        }

        return result;
    }

    /**
     * @param nibble [0..15]
     * @return hexadecimal character
     */
    private static char nibbleToHexChar(int nibble) {
        if (nibble < 10)
            return (char) (48 + nibble);
        else
            return (char) (97 + (nibble - 10));
    }

    /**
     * @param c hexadecimal character
     * @return nibble [0..15]
     */
    private static int hexcharToNibble(char c) {
        if (c >= '0' && c <= '9')
            return c - 48;
        if (c >= 'a' && c <= 'f')
            return 10 + (c - 97);
        if (c >= 'A' && c <= 'F')
            return 10 + (c - 65);
        else
            return 0;
    }

    /**
     * Creates a new authenticator.
     * @throws IOException
     */
    public void net_enroll(String region) throws IOException {
        if(!region.equals("EU") && !region.equals("US"))
            // FIXME: what's a suitable Exception class?
            throw new RuntimeException("invalid region code");

        // Enroll request
        URL url_enroll = new URL(str_consts.get(region).get("auth-url-enroll"));
        HttpURLConnection conn = (HttpURLConnection) url_enroll.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-type", "application/octet-stream");
        conn.setRequestProperty("Accept", "text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2");
        conn.setReadTimeout(15000);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.connect();

        // Generate enrollment bytes
        byte[] data_out;
        BlizzCrypt.prepareEnrollmentBytes(generateEnrollmentMash(region));
        while(BlizzCrypt.round() != 12) {}
        data_out = BlizzCrypt.getOTPBytes();

        // Send enrollment bytes
        OutputStream out_stream = conn.getOutputStream();
        out_stream.write(data_out);
        out_stream.close();

        // Read reply
        byte[] b_servertime = new byte[8];
        byte[] b_token_and_serial = new byte[37];

        InputStream inp_stream = conn.getInputStream();
        inp_stream.read(b_servertime, 0, 8);
        inp_stream.read(b_token_and_serial, 0, 37);
        inp_stream.close();
        conn.disconnect();

        // Decrypt reply
        byte[] b_token      = new byte[20];
        byte[] b_serial     = new byte[17];
        time_diff = bytesToLong(b_servertime, 0) - System.currentTimeMillis();
        BlizzCrypt.xorEncryptArray(b_token_and_serial, xor_key_random37);
        System.arraycopy(b_token_and_serial, 0, b_token, 0, 20);
        System.arraycopy(b_token_and_serial, 20, b_serial, 0, 17);
        char c_token[] = new char[40];
        for (int i = 0; i < 20; i++) {
            int nib_up = (b_token[i] & 0xf0) >> 4;
        int nib_lo = b_token[i] & 0xf;
        c_token[i * 2] = nibbleToHexChar(nib_up);
        c_token[i * 2 + 1] = nibbleToHexChar(nib_lo);
        }

        setSerial(new String(c_token), new String(b_serial, 0, 17), time_diff);
    }

    /**
     * Updates the server<->client time difference.
     * @throws IOException
     */
    public void net_sync() throws IOException {
        URL url_sync = new URL(str_consts.get(getRegion()).get("auth-url-time"));
        HttpURLConnection conn = (HttpURLConnection) url_sync.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/octet-stream");
        conn.setRequestProperty("Accept", "text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2");
        conn.setReadTimeout(15000);
        conn.setDoInput(true);
        conn.connect();

        // retrieve time
        byte[] b_servertime = new byte[8];
        InputStream inp_stream = conn.getInputStream();
        inp_stream.read(b_servertime, 0, 8);
        inp_stream.close();
        conn.disconnect();

        time_diff = bytesToLong(b_servertime, 0) - System.currentTimeMillis();
    }

    @Override
    public String toString() {
        return str_serial;
    }
}
