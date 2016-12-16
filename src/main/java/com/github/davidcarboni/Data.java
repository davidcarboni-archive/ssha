package com.github.davidcarboni;

import com.github.davidcarboni.cryptolite.ByteArray;
import com.github.davidcarboni.cryptolite.Random;
import com.github.davidcarboni.restolino.json.Serialiser;
import com.liferay.portal.security.pwd.PwdEncryptor;
import com.liferay.portal.security.pwd.PwdEncryptorException;

import java.io.IOException;


/**
 * Json message.
 */
public class Data {
    public String message;
    public String password;
    public String hash;
    public String salt;
}
