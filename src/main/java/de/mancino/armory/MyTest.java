package de.mancino.armory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import org.apache.commons.io.IOUtils;

public class MyTest {
    private static final String login = 
        "0000000e2f61757468656e74696361746531000001f6050000000973637265656e5265730000000a50484f4e455f48494748ff050000000c656d61696c41646472657373000000146d6172696f406d616e63696e6f2d6e65742e6465ff05000000066c6f63616c650000000564655f4445ff0500000006646576696365000000066950686f6e65ff050000001064657669636554696d655a6f6e6549640000000d4575726f70652f4265726c696eff050000000e64657669636554696d655a6f6e650000000737323030303030ff050000000a64657669636554696d650000000d31333036353034353032313333ff05000000046170705600000005332e302e30ff0400000007636c69656e7441000000850400000080e9c8c6bdd1a810864baf14620a350d6e2be3f4051b794eaafccf74cfa6a791b932780b4e4aed227f72d0b84ce6bb40c47e3d41aa7ed73ea1df32ed0416007509843e95bf2b86f9ff4d33a485995f1b2298e41a925af00020ab88f80116e6233a9fd20d6444af24e651cb9790fe8c413292c91a438bf32d28a721ad9275c3da14ff050000000864657669636549640000002830626435663633313238623432316662306261366138303934313335366131643435396262366331ff050000000561707049640000000641726d6f7279ff050000000b6465766963654d6f64656c000000096950686f6e65332c31ff050000001364657669636553797374656d56657273696f6e00000003342e33ffff";;
      //"0000000e2f61757468656e74696361746531000001e6050000000973637265656e5265730000000a50484f4e455f48494748ff050000000c656d61696c41646472657373000000146d6172696f406d616e63696e6f2d6e65742e6465ff05000000066c6f63616c650000000564655f4445ff0500000006646576696365000000066950686f6e65ff050000001064657669636554696d655a6f6e6549640000000d4575726f70652f4265726c696eff050000000e64657669636554696d655a6f6e650000000737323030303030ff050000000a64657669636554696d650000000d31333036343831343430363138ff05000000046170705600000005332e302e30ff0400000007636c69656e7441000000850400000080a7b176b960f9fb1ccdf44d7a99f912c2455d3e449823a27a5808eed634386909d1bdd52a4424cd93a7d00d07d6632b62bd8b32205fe51cc1d7877a0fdd741580d8911b20ee06fa7879323e6997d399f3c644f7f3a8cfccb3fd3e793c042de1efbdc63126e606d65a96c2b8d2b1e42b170e9a3d0b7696cbce17411a44678e9c85ff050000000864657669636549640000002830626435663633313238623432316662306261366138303934313335366131643435396262366331ff050000000561707049640000000641726d6f7279ff050000000b6465766963654d6f64656c000000096950686f6e65332c31ff050000001364657669636553797374656d56657273696f6e00000003342e33ffff";
// FILTER: ip.src==80.239.186.38 or ip.addr==80.239.186.38 and tcp.flags.push==1
    private static final byte[] loginBytes = hexStringToByteArray(login);
    private static final String login2 = 
        "0000000e2f61757468656e74696361746532000001f7040000000b636c69656e7450726f6f660000002504000000202fd3245188221ee9b00a5b9ccdc6432c8367941b136326635d376aac85d7b7d5ffff";
      //"00000c9ff000001ff3457b91080045000079c22b40003f06b4cfac1c0d5250efba26cc2e224cf3f87ccf88cadd065018ffffcaca00000000000e2f61757468656e7469636174653200000202040000000b636c69656e7450726f6f6600000025040000002057f19cd954e325d10d950e1afdea1326d2f018a0497761a030e6ecafe6e290f0ffff";
    private static final byte[] loginBytes2 = hexStringToByteArray(login2);
    private static final byte[] key = hexStringToByteArray("000001f605");
    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }
    /**
     * @param args
     */
    public static void main(String[] args) {
        new MyTest().doit();
    }
    
    private MyTest doit() {
        System.err.println("doit!");
        DataInputStream inRaw = null;
            DataOutputStream outRaw = null;
        try {
            Socket skt = new Socket(MobileChat.HOSTNAME, MobileChat.PORT);
            skt.setKeepAlive(true);
            inRaw = new DataInputStream(skt.getInputStream());
            outRaw = new DataOutputStream(skt.getOutputStream());
            /*
            for(byte b : loginBytes) {
                outRaw.write(b);
            }
            outRaw.flush();*/
            ArmoryRequest auth1 = new ArmoryRequest("/authenticate1", key);
            auth1.addParameter("appId", "Armory");
            auth1.addParameter("screenRes", "PHONE_HIGH");
            auth1.addParameter("emailAddress", "mario@mancino-net.de");
            auth1.addParameter("locale", "de_DE");
            auth1.addParameter("device", "iPhone");
            auth1.send(outRaw);
            new ArmoryResponse(inRaw);
            
            for(byte b : loginBytes2) {
                outRaw.write(b);
            }
            outRaw.flush();
            new ArmoryResponse(inRaw);
            inRaw.close();
        }
        catch(Exception e) {
            System.out.print("Whoops! It didn't work!\n");
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(inRaw);
            IOUtils.closeQuietly(outRaw);
        }
        return this;
    }

    private static void readResponse(final DataInputStream dis) throws IOException {
        readByte(dis, "Prefix", 6);
        readString(dis);
        readByte(dis, "Command Suffix", 5);
        readInt(dis);
        byte lastCall = 0;
        while(lastCall != 255) {
            readString(dis);
            readByte(dis, "Param Suffix", 5);
            readString(dis);
            lastCall = readByte(dis, "Param Trenner", 2);
        }
    }

    private static byte readByte(final DataInputStream dis, final String description, final int expectedLength) throws IOException {
        byte data[] = new byte[expectedLength];
        final int prefixLength = dis.read(data, 0, expectedLength);
        System.out.print("Read '" + description + "' of length " + prefixLength + ": ");
        for(int i = 0 ; i < prefixLength ; i++) {
            System.out.print(((int)data[i]) + " ");
        }
        System.out.println("");
        return data[expectedLength-1];
    }

    private static void readString(final DataInputStream dis) throws IOException {
        final int stringLength = dis.readInt();
        System.out.println("String with length of: " + stringLength);
        byte stringBytes[] = new byte[stringLength];
        final int readBytes = dis.read(stringBytes, 0, stringLength);
        final String data = new String(stringBytes);
        System.out.println("Read String '" + data + "'");
    }

    private static void readInt(final DataInputStream dis) throws IOException {
        final int value = dis.readInt();
        System.out.println("Read Int '" + value + "'");
    }
}
/*

0000   00 00 0c 9f f0 00 00 1f f3 45 7b 91 08 00 45 00  .........E{...E.
0010   02 4e 60 3e 40 00 3f 06 14 e8 ac 1c 0d 52 50 ef  .N`>@.?......RP.
0020   ba 26 cc 2d 22 4c 8e bc b9 dc 18 f3 f3 f9 50 18  .&.-"L........P.
0030   ff ff 29 d3 00 00 00 00 00 0e 2f 61 75 74 68 65  ..)......./authe
0040   6e 74 69 63 61 74 65 31 00 00 01 f6 05 00 00 00  nticate1........
0050   09 73 63 72 65 65 6e 52 65 73 00 00 00 0a 50 48  .screenRes....PH
0060   4f 4e 45 5f 48 49 47 48 ff 05 00 00 00 0c 65 6d  ONE_HIGH......em
0070   61 69 6c 41 64 64 72 65 73 73 00 00 00 14 6d 61  ailAddress....ma
0080   72 69 6f 40 6d 61 6e 63 69 6e 6f 2d 6e 65 74 2e  rio@mancino-net.
0090   64 65 ff 05 00 00 00 06 6c 6f 63 61 6c 65 00 00  de......locale..
00a0   00 05 64 65 5f 44 45 ff 05 00 00 00 06 64 65 76  ..de_DE......dev
00b0   69 63 65 00 00 00 06 69 50 68 6f 6e 65 ff 05 00  ice....iPhone...
00c0   00 00 10 64 65 76 69 63 65 54 69 6d 65 5a 6f 6e  ...deviceTimeZon
00d0   65 49 64 00 00 00 0d 45 75 72 6f 70 65 2f 42 65  eId....Europe/Be
00e0   72 6c 69 6e ff 05 00 00 00 0e 64 65 76 69 63 65  rlin......device
00f0   54 69 6d 65 5a 6f 6e 65 00 00 00 07 37 32 30 30  TimeZone....7200
0100   30 30 30 ff 05 00 00 00 0a 64 65 76 69 63 65 54  000......deviceT
0110   69 6d 65 00 00 00 0d 31 33 30 36 35 30 34 35 30  ime....130650450
0120   32 31 33 33 ff 05 00 00 00 04 61 70 70 56 00 00  2133......appV..
0130   00 05 33 2e 30 2e 30 ff 04 00 00 00 07 63 6c 69  ..3.0.0......cli
0140   65 6e 74 41 00 00 00 85 04 00 00 00 80 e9 c8 c6  entA............
0150   bd d1 a8 10 86 4b af 14 62 0a 35 0d 6e 2b e3 f4  .....K..b.5.n+..
0160   05 1b 79 4e aa fc cf 74 cf a6 a7 91 b9 32 78 0b  ..yN...t.....2x.
0170   4e 4a ed 22 7f 72 d0 b8 4c e6 bb 40 c4 7e 3d 41  NJ.".r..L..@.~=A
0180   aa 7e d7 3e a1 df 32 ed 04 16 00 75 09 84 3e 95  .~.>..2....u..>.
0190   bf 2b 86 f9 ff 4d 33 a4 85 99 5f 1b 22 98 e4 1a  .+...M3..._."...
01a0   92 5a f0 00 20 ab 88 f8 01 16 e6 23 3a 9f d2 0d  .Z.. ......#:...
01b0   64 44 af 24 e6 51 cb 97 90 fe 8c 41 32 92 c9 1a  dD.$.Q.....A2...
01c0   43 8b f3 2d 28 a7 21 ad 92 75 c3 da 14 ff 05 00  C..-(.!..u......
01d0   00 00 08 64 65 76 69 63 65 49 64 00 00 00 28 30  ...deviceId...(0
01e0   62 64 35 66 36 33 31 32 38 62 34 32 31 66 62 30  bd5f63128b421fb0
01f0   62 61 36 61 38 30 39 34 31 33 35 36 61 31 64 34  ba6a80941356a1d4
0200   35 39 62 62 36 63 31 ff 05 00 00 00 05 61 70 70  59bb6c1......app
0210   49 64 00 00 00 06 41 72 6d 6f 72 79 ff 05 00 00  Id....Armory....
0220   00 0b 64 65 76 69 63 65 4d 6f 64 65 6c 00 00 00  ..deviceModel...
0230   09 69 50 68 6f 6e 65 33 2c 31 ff 05 00 00 00 13  .iPhone3,1......
0240   64 65 76 69 63 65 53 79 73 74 65 6d 56 65 72 73  deviceSystemVers
0250   69 6f 6e 00 00 00 03 34 2e 33 ff ff              ion....4.3..
*/