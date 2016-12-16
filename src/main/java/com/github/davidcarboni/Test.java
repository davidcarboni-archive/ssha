package com.github.davidcarboni;

import com.github.davidcarboni.cryptolite.ByteArray;
import com.github.davidcarboni.cryptolite.Random;
import com.liferay.portal.security.pwd.PwdEncryptor;
import com.liferay.portal.security.pwd.PwdEncryptorException;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;

/**
 * Not really a test, more of a quick visual inspection to see that this API works.
 */
public class Test {


    public static void main(String[] args) throws PwdEncryptorException, IOException {

        // Generate a Liferay hash
        String password = Random.password(8);
        System.out.println("password = " + password);
        String hash = PwdEncryptor.encrypt(password);
        System.out.println("hash = " + hash);
        String hashBytes = ByteArray.toHexString(ByteArray.fromBase64String(hash));
        System.out.println("hashBytes = " + hashBytes);

        // Extract the salt
        Salt saltApi = new Salt();
        Data data = new Data();
        data.hash = hash;
        Data result = saltApi.getSalt(null, data);
        String salt = result.salt;
        System.out.println("salt = " + salt);
        String saltBytes = ByteArray.toHexString(ByteArray.fromBase64String(salt));
        System.out.println("saltBytes = " + saltBytes);

        // Re-hash using the plaintext password and extracted salt
        Hash hashApi = new Hash();
        data = new Data();
        data.password = password;
        data.salt = salt;
        result = hashApi.hash(null, data);
        String rehash = result.hash;
        System.out.println("rehash = " + rehash);
        String rehashBytes = ByteArray.toHexString(ByteArray.fromBase64String(hash));
        System.out.println("rehashBytes = " + rehashBytes);

        // For completeness
        boolean matches = StringUtils.equals(hash, rehash);
        System.out.println("matches = " + matches);
    }
}
