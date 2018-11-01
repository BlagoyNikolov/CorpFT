package com.financetracker.services.Impl;

import com.financetracker.entities.User;
import com.financetracker.repositories.UserRepository;
import com.financetracker.services.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    public void insertUser(User user) {
        userRepository.save(user);
    }

    public void updateUser(User user) {
        userRepository.save(user);
    }

    public void updateUserPassword(User user, String password) {
        String pass = DigestUtils.sha512Hex(DigestUtils.sha512(password));
        userRepository.updateUserPassword(user, pass);
    }

    public User getUser(String username) {
        return userRepository.findByUsername(username);
    }

    public boolean existsUser(String username) {
        return userRepository.existsUserByUsername(username);
    }

    public boolean isValidLogin(String username, byte[] password) {
        byte[] hashedPassword = DigestUtils.sha512(DigestUtils.sha512Hex(password));

        if (existsUser(username)) {
            User user = getUser(username);
            return MessageDigest.isEqual(hashedPassword, user.getPassword());
        }
        return false;
    }

    public boolean existEmail(String email) {
        return userRepository.existsUserByEmail(email);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean existUserByPasswordToken(String token) {
        return userRepository.existsUserByPasswordToken(token);
    }

    public User getUserByPasswordToken(String token) {
        return userRepository.findByPasswordToken(token);
    }

    public User getUserByUserId(long userId) {
        return userRepository.findOne(userId);
    }
}
