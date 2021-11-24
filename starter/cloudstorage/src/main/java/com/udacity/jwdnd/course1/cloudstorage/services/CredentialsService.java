package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialsMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialsService {
    private final CredentialsMapper credentialsMapper;
    private final EncryptionService encryptionService;

    public CredentialsService(CredentialsMapper credentialsMapper, EncryptionService encryptionService) {
        this.credentialsMapper = credentialsMapper;
        this.encryptionService = encryptionService;
    }

    public void insertOrUpdateCredential(Credentials credentials){
        String password = credentials.getPassword();
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(password, encodedKey);
        // <end> encrypt the password
        credentials.setPassword(encryptedPassword);
        credentials.setKey(encodedKey);

        if(credentials.getCredentialid() == null){
            credentialsMapper.insertCredentials(credentials);
        }
        else{
            credentialsMapper.updateCredentials(credentials);
        }
    }

    public Credentials getCredential(Credentials credentials){
        String encryptedPassword = credentials.getPassword();
        String key = credentials.getKey();

        String decryptedPassword = encryptionService.decryptValue(encryptedPassword, key);
        credentials.setPassword(decryptedPassword);
        return credentials;
    }

    public Integer deleteCredential(Integer credentialID, Integer userID){
        return credentialsMapper.deleteCredentials(credentialID, userID);
    }

    public List <Credentials> getAllCredentials(Integer userID){
        return credentialsMapper.selectCredentials(userID);
    }
}
