package de.mancino.armory.experimental;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ArmoryRequest {
    private final String command; 
    private final byte[] commandSuffix;
    private final Map<String, String> params;
    
    private static final byte[] RESPONSE_PREFIX = {0,0};
    
    public ArmoryRequest(final String command, final byte[] commandSuffix) {
        this(command, commandSuffix, new HashMap<String, String>());
    }
    
    public ArmoryRequest(final String command, final byte[] commandSuffix, final Map<String, String> params) {
        this.command = command;
        this.commandSuffix = commandSuffix;
        this.params = params;
    }
    
    public void send(final DataOutputStream dos) throws IOException {
        sendBytes(dos, RESPONSE_PREFIX);
        sendInteger(dos, command.length());
        sendString(dos, command);
        sendBytes(dos, this.commandSuffix);
        Object[] keySet = params.keySet().toArray();
        for(int keyPos = 0 ; keyPos < keySet.length ; keyPos++) {
            final String key = keySet[keyPos].toString();
            final String value = params.get(key);
            final boolean lastPair = keyPos == keySet.length-1;
            sendInteger(dos, key.length());
            sendString(dos, key);
            sendInteger(dos, value.length());
            sendString(dos, value);
            sendByte(dos, (byte) 255);
            if(lastPair) {
                sendByte(dos, (byte) 255);
            } else {
                sendByte(dos, (byte) 5);
            }
        }
        dos.flush();
    }
    
    private void sendByte(final DataOutputStream dos, final byte data) throws IOException {
        dos.write(data);
    }
    
    private void sendBytes(final DataOutputStream dos, final byte[] data) throws IOException {
        dos.write(data);
    }
    
    private void sendString(final DataOutputStream dos, final String data) throws IOException {
        sendBytes(dos, data.getBytes());
    }
    
    private void sendInteger(final DataOutputStream dos, final int data) throws IOException {
        dos.writeInt(data);
    }
    public void addParameter(final String key, final String value) {
        params.put(key, value);
    }
}
