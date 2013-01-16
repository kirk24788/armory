package de.mancino.armory.requests.vault;

import java.io.IOException;

import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mancino.armory.datatypes.ArmoryBaseUri;
import de.mancino.armory.exceptions.ResponseParsingException;
import de.mancino.armory.json.JsonResponse;

public class ArmoryVaultJsonRequest<T extends JsonResponse> extends ArmoryVaultRequest {
    /**
     * Logger instance of this class.
     */
    private static final Logger LOG = LoggerFactory.getLogger(ArmoryVaultJsonRequest.class);

    private T jsonObject;
    private Class<T> valueType;

    public ArmoryVaultJsonRequest(final ArmoryBaseUri armoryBaseUri, final String requestPath, final Class<T> valueType) {
        super(armoryBaseUri, requestPath);
        this.valueType = valueType;
    }
    public ArmoryVaultJsonRequest(final ArmoryBaseUri armoryBaseUri, final String requestPath, final Class<T> valueType,
            final BasicNameValuePair ... parameters) {
        super(armoryBaseUri, requestPath, parameters);
        this.valueType = valueType;
    }
    
    @Override
    protected void parseResponse(byte[] responseAsBytes) throws ResponseParsingException {
        final ObjectMapper mapper = createJsonObjectMapper();
        final String responseAsString = new String(responseAsBytes);
        try {
            LOG.trace("Parsing JSON Response:\n{}", responseAsString);
            jsonObject = mapper.readValue(new String(responseAsBytes), valueType);
            if(jsonObject.error != null) {
                throw new ResponseParsingException("Error " + jsonObject.error.code + ": " + jsonObject.error.message);
            }
        } catch (JsonParseException e) {
            LOG.debug("Error while Parsing JSON Response:\n{}", responseAsString);
            throw new ResponseParsingException("Parsing exception while processing Response!", e);
        } catch (JsonMappingException e) {
            throw new ResponseParsingException("Mapping exception while mapping Response!", e);
        } catch (IOException e) {
            throw new ResponseParsingException("IO Exception while processing Response!", e);
        }
    }
    
    public T getObject() {
        return jsonObject;
    }
}
