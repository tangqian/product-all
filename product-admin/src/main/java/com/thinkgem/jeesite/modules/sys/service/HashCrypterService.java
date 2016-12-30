package com.thinkgem.jeesite.modules.sys.service;

import com.thinkgem.jeesite.modules.sys.utils.Hasher;

public class HashCrypterService{

    private String algorithmName;
    private Hasher hasher;
    private byte[] salt;
    private int hashIterations = 1;

    public byte[] encrypt(byte[] source, String charset) {
        return hasher.hash(source, salt, hashIterations);
    }

    public void setAlgorithmName(String algorithmName) {
        this.algorithmName = algorithmName;
        this.hasher = new Hasher(this.algorithmName);
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }
    
    public void setSaltString(String saltString) {
        this.salt = saltString.getBytes();
    }

    public void setHashIterations(int hashIterations) {
        this.hashIterations = hashIterations;
    }

}