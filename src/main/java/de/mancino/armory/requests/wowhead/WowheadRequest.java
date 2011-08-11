package de.mancino.armory.requests.wowhead;

import java.io.ByteArrayInputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;


import de.mancino.armory.exceptions.ResponseParsingException;
import de.mancino.armory.requests.GetRequest;
import de.mancino.armory.xml.wowhead.Wowhead;

public class WowheadRequest extends GetRequest {

    private Wowhead data;

    public WowheadRequest(final String requestPath) {
        super(requestPath);
    }

    @Override
    protected void parseResponse(byte[] responseAsBytes) throws ResponseParsingException {
        try {
            JAXBContext context = JAXBContext.newInstance(Wowhead.class);
            Unmarshaller um = context.createUnmarshaller();
            data =  (Wowhead) um.unmarshal( new ByteArrayInputStream(responseAsBytes));
            if(data.error != null) {
                throw new ResponseParsingException("Error: " + data.error);
            }
        } catch (JAXBException e) {
            throw new ResponseParsingException("Error parsing XML Response!", e);
        }
    }

    public Wowhead getData() {
        return data;
    }
}
