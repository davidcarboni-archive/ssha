package com.github.davidcarboni;

import com.github.davidcarboni.cryptolite.ByteArray;
import com.github.davidcarboni.restolino.framework.Api;
import com.liferay.portal.security.pwd.PwdEncryptor;
import com.liferay.portal.security.pwd.PwdEncryptorException;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import java.io.IOException;

/**
 * Computes an SSHA password hash.
 */
@Api
public class Salt {

    /**
     * Extracts the salt value from a Liferay SSHA hash.
     * @param res The response.
     * @param data A {@link Data} instance with the {@link Data#hash} field set.
     * @return A {@link Data} instance with the {@link Data#salt} field set.
     * @throws IOException If an error occurs.
     * @throws PwdEncryptorException If an error occurs.
     */
    @POST
    public Data getSalt(HttpServletResponse res, Data data) throws IOException, PwdEncryptorException {

        // Validate
        if (data == null || StringUtils.isBlank(data.hash)) {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            Data result = new Data();
            result.message = "Please provide a Json message with a 'hash' key/value.";
            return result;
        }

        // Get the salt
        Data result = new Data();
        result.salt = ByteArray.toBase64String(PwdEncryptor._getSaltFromSSHA(data.hash));
        return result;
    }

    @GET
    public String info() {
        return "Please POST a Liferay SSHA hash to /salt.";
    }


}
