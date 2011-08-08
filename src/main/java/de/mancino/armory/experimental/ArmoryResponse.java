package de.mancino.armory.experimental;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArmoryResponse {
    /**
     * Logger instance of this class.
     */
    private static final Logger LOG = LoggerFactory.getLogger(ArmoryResponse.class);

    public final int responseLength;
    private final byte[] rawResponse;
    public final short UNKNOWN_protocolVersion;
    public final String command;
    public final byte[] UNKNOWN_commandSuffix;
    public Map<String,String> parameterMap; // XXX: So nicht! Parameter sind typisiert!!!

    private int streamPos;

    public ArmoryResponse(DataInputStream dis) throws IOException {
        LOG.debug("Begin Reading stream...");
        responseLength = dis.readInt();
        rawResponse=new byte[responseLength];
        final int bytesRead = dis.read(rawResponse, 0, responseLength);
        LOG.debug(bytesRead + "/" + responseLength + " Bytes read!");
        
        LOG.debug("Parsing Response...");
        UNKNOWN_protocolVersion = readShort();
        LOG.debug("Protocol Version (?): " + UNKNOWN_protocolVersion);
        final int commandLength = readInteger();
        LOG.debug("Command Length: " + commandLength);
        command=readString(commandLength);
        LOG.debug("Command: " + command);
        UNKNOWN_commandSuffix = readBytes(5);
        LOG.debug("Command Suffix: " + byteToString(UNKNOWN_commandSuffix));
        
        Map<String,String> tempParameterMap = new HashMap<String, String>();
        final int numberOfParameters = readInteger();
        LOG.debug("Number of Parameters: " + numberOfParameters);
        for(int currentParameter = 0 ; currentParameter < numberOfParameters ; currentParameter ++) {
            LOG.debug("Parsing Parameter " + currentParameter + "...");
            final int keyNameLength = readInteger();
            LOG.debug("Parameter-Key Length: " + keyNameLength);
            final String keyName = readString(keyNameLength);
            LOG.debug("Parameter-Key: " + keyName);
            final byte parameterType = readByte();
            LOG.debug("Parameter-Type: " + parameterType);
            final String parameterValue;
            final int paramLength;
            switch(parameterType) {
            case 2:
                parameterValue = String.valueOf(readInteger());
                break;
            case 3:
                parameterValue = String.valueOf(readInteger());
                break;
            case 4:
                int oldPos = streamPos;
                paramLength = readInteger();
                LOG.debug("Parameter-Length: " + paramLength);
                final byte[] data = readBytes(paramLength);
                parameterValue = byteToString(data);
                break;
            case 5:
                paramLength = readInteger();
                LOG.debug("Parameter-Length: " + paramLength);
                parameterValue = readString(paramLength);
                break;
            case 6: // bool?
                parameterValue = String.valueOf(readByte());
                break;
            default:
                LOG.error("Unbekannter Parameter-Type: " + parameterType );
                throw new RuntimeException();
            }
            tempParameterMap.put(keyName, parameterValue);
        }
        parameterMap = Collections.unmodifiableMap(tempParameterMap);
        LOG.debug("Parsing Done! " + toString());
    }

    private void resetStream() {
        streamPos=0;
    }

    private byte readByte() {
        byte data = readByte(streamPos);
        streamPos++;
        return data;
    }
    private byte readByte(final int pos) {
        return rawResponse[pos];
    }
    

    private short readShort() {
        short data = readShort(streamPos);
        streamPos+=2;
        return data;
    }
    private short readShort(final int pos) {
        short value = 0;
        for (int i = 0; i < 2; i++) {
            int shift = (2 - 1 - i) * 8;
            value += (rawResponse[i + pos] & 0x000000FF) << shift;
        }
        return value;
    }

    private int readInteger() {
        int data = readInteger(streamPos);
        streamPos+=4;
        return data;
    }
    private int readInteger(final int pos) {
        int value = 0;
        for (int i = 0; i < 4; i++) {
            int shift = (4 - 1 - i) * 8;
            value += (rawResponse[i + pos] & 0x000000FF) << shift;
        }
        return value;
    }

    private byte[] readBytes(final int size) {
        final byte data[] = readBytes(streamPos, size);
        streamPos+=size;
        return data;
    }
    private byte[] readBytes(final int pos, final int size) {
        final byte data[] = new byte[size];
        for(int i=0 ; i < size; i++) {
            data[i] = rawResponse[i+pos];
        }
        return data;
    }


    private String readString(final int size) {
        return new String(readBytes(size));
    }
    private String readString(final int pos, final int size) {
        return new String(readBytes(pos, size));
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("Armory Response (").append(responseLength).append(" Bytes):\n")
        .append("  Protocoll Version(?): ").append(UNKNOWN_protocolVersion).append("\n")
        .append("  Command: ").append(command).append("\n")
        .append("  Command-Suffix: ").append(byteToString(UNKNOWN_commandSuffix)).append("\n")
        .append("  Parameters: ").append("\n");
        for(final String key : parameterMap.keySet()) {
            sb.append("   * ").append(key).append(": '").append(parameterMap.get(key)).append("'\n");
        }
        return sb.toString();
    }

    private String byteToString(byte data[]) {
        final StringBuffer sb = new StringBuffer();
        for(byte d : data) {
            sb.append(" ").append(Integer.toString( ( d & 0xff ) + 0x100, 16).substring( 1 ));
        }
        
        return sb.substring(1);
    }
}
