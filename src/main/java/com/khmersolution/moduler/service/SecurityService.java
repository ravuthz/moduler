package com.khmersolution.moduler.service;

/**
 * Created by Vannaravuth Yo
 * Date : 8/25/17, 12:49 AM
 * Email : ravuthz@gmail.com
 */

public interface SecurityService {
    String findLoggedInUsername();

    void autologin(String username, String password);
}
