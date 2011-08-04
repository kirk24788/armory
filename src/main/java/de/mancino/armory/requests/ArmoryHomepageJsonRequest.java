package de.mancino.armory.requests;

import java.io.IOException;

import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import de.mancino.armory.ArmoryBaseUri;
import de.mancino.armory.exceptions.ResponseParsingException;
import de.mancino.armory.json.vault.character.auction.money.Money;

public class ArmoryHomepageJsonRequest<T> extends ArmoryHomepageRequest {

    private T jsonObject;
    private Class<T> valueType;
    
    public ArmoryHomepageJsonRequest(final String requestPath, final Class<T> valueType) {
        this(new ArmoryBaseUri(), requestPath, valueType);
    }
    public ArmoryHomepageJsonRequest(final ArmoryBaseUri armoryBaseUri, final String requestPath, final Class<T> valueType) {
        super(armoryBaseUri, requestPath);
        this.valueType = valueType;
    }
    
    @Override
    protected void parseResponse(byte[] responseAsBytes) throws ResponseParsingException {
        final ObjectMapper mapper = new ObjectMapper();
        try {
            jsonObject = mapper.readValue(new String(responseAsBytes), valueType);
        } catch (JsonParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public T getObject() {
        return jsonObject;
    }
    
/**
 *         ObjectMapper mapper = new ObjectMapper();
        try {
            Money m = mapper.readValue(CT, Money.class);
            System.err.println(m.character.classId);
        } catch (JsonParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
 */
}
