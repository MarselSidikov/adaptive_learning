package ru.kpfu.ivmiit.learning.tools.dao;

import java.security.SecureRandom;

/**
 * Created by Lebedenko Igor on 06.04.2015.
 */
public class UsersDaoUtil {
    public static String generateUserToken() {
        String AB = "0123456789abcdefghigklmnopqrstuvwxyz";
        SecureRandom rnd = new SecureRandom();

        StringBuilder sb = new StringBuilder(32);
        for (int i = 0; i < 32; i++) {
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        }

        return sb.toString();
    }
}
