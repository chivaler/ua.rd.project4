package ua.rd.project4.model.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.rd.project4.domain.User;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;

public interface UserService extends EntityService<User> {

    List<User> findUsersByClientId(int clientId);

    User authentication(String login, String password);

    default String getHashPassword(String nakedPassword) {
        final String CRYPTO_ALGORITHM = "MD5";
        MessageDigest messageDigest;
        byte[] digest = new byte[0];
        try {
            messageDigest = MessageDigest.getInstance(CRYPTO_ALGORITHM);
            messageDigest.reset();
            messageDigest.update(nakedPassword.getBytes());
            digest = messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            Logger logger = LogManager.getLogger(UserService.class);
            logger.error(e);
        }

        BigInteger bigInt = new BigInteger(1, digest);
        String md5Hex = bigInt.toString(16);
        while (md5Hex.length() < 32) {
            md5Hex = "0" + md5Hex;
        }
        return md5Hex;
    }
}
