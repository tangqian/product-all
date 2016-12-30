package com.thinkgem.jeesite.modules.sys.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.shiro.crypto.UnknownAlgorithmException;

public class Hasher {
	
	private final String algorithmName;

	public Hasher(String algorithmName) {
		super();
		this.algorithmName = algorithmName;
	}

	/**
     * Returns the JDK MessageDigest instance to use for executing the hash.
     *
     * @param algorithmName the algorithm to use for the hash, provided by subclasses.
     * @return the MessageDigest object for the specified {@code algorithm}.
     * @throws UnknownAlgorithmException if the specified algorithm name is not available.
     */
    protected MessageDigest getDigest(String algorithmName) throws UnknownAlgorithmException {
        try {
            return MessageDigest.getInstance(algorithmName);
        } catch (NoSuchAlgorithmException e) {
            String msg = "No native '" + algorithmName + "' MessageDigest instance available on the current JVM.";
            throw new UnknownAlgorithmException(msg, e);
        }
    }
    
    public String getAlgorithmName() {
		return algorithmName;
	}

    /**
     * Hashes the specified byte array without a salt for a single iteration.
     *
     * @param bytes the bytes to hash.
     * @return the hashed bytes.
     */
    public byte[] hash(byte[] bytes) throws UnknownAlgorithmException {
        return hash(bytes, null, 1);
    }

    /**
     * Hashes the specified byte array using the given {@code salt} for a single iteration.
     *
     * @param bytes the bytes to hash
     * @param salt  the salt to use for the initial hash
     * @return the hashed bytes
     */
    public byte[] hash(byte[] bytes, byte[] salt) throws UnknownAlgorithmException  {
        return hash(bytes, salt, 1);
    }

    /**
     * Hashes the specified byte array using the given {@code salt} for the specified number of iterations.
     *
     * @param bytes          the bytes to hash
     * @param salt           the salt to use for the initial hash
     * @param hashIterations the number of times the the {@code bytes} will be hashed (for attack resiliency).
     * @return the hashed bytes.
     * @throws UnknownAlgorithmException if the {@link #getAlgorithmName() algorithmName} is not available.
     */
    public byte[] hash(byte[] bytes, byte[] salt, int hashIterations) throws UnknownAlgorithmException {
        MessageDigest digest = getDigest(getAlgorithmName());
        if (salt != null) {
            digest.reset();
            digest.update(salt);
        }
        byte[] hashed = digest.digest(bytes);
        int iterations = hashIterations - 1; //already hashed once above
        //iterate remaining number:
        for (int i = 0; i < iterations; i++) {
            digest.reset();
            hashed = digest.digest(hashed);
        }
        return hashed;
    }
	
}