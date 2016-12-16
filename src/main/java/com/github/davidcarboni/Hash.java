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
public class Hash {

    @POST
    public Data hash(HttpServletResponse res, Data data) throws IOException, PwdEncryptorException {

        // Validate
        if (data == null || StringUtils.isBlank(data.password)) {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            Data result = new Data();
            result.message = "Please provide a Json message with 'password' and 'salt' key/values.";
            return result;
        }

        // Hash password with a random salt
        if (StringUtils.isBlank(data.salt)) {
            Data result = new Data();
            result.hash = PwdEncryptor.encrypt(data.password);
            return result;
        }

        // Hash password with a knows salt
        Data result = new Data();
        byte[] salt = ByteArray.fromBase64String(data.salt);
        result.hash = PwdEncryptor.encodePassword(PwdEncryptor.TYPE_SSHA, data.password, salt);
        return result;
    }

    @GET
    public String info() {
        return "Please POST a password and salt to /hash (see also /salt).";
    }


}
