package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialService {

    private CredentialMapper credentialMapper;

    public CredentialService(CredentialMapper credentialMapper) {
        this.credentialMapper = credentialMapper;
    }

    public List<Credential> getAllCredentials(){
        return credentialMapper.getAllCredentials();
    }

    public List<Credential> getCredentialsByUserId(Integer userId){
        return credentialMapper.getCredentialsByUserId(userId);
    }

    public Integer createCredential(Credential credential){
        return credentialMapper.createCredential(credential);
    }

    public Integer updateCredential(Credential credential){
        return credentialMapper.updateCredential(credential);
    }

    public Integer deleteCredential(Credential credential){
        return credentialMapper.deleteCredential(credential);
    }
}
