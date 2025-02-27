package com.my.newproject5;

import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.cert.Certificate;
import java.util.Base64;

public class GetDeviceUniqueID {
	
	/* Author @developer-krushna */
	
	/* this might not work in lower android */
	/* This is a small effort to make truely unique without using
	any run time permissions */
	
	private static final String KEY_ALIAS = "device_key"; // place holder 
	
	public String getDeviceID() {
		try {
			KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
			keyStore.load(null);
			
			// check if the the alias exists in keystore if not generate it 
			if (!keyStore.containsAlias(KEY_ALIAS)) {
				generateKeyPair();
			}
			
			// Getting certofdetails 
			Certificate certificate = keyStore.getCertificate(KEY_ALIAS);
			if (certificate != null) {
				byte[] certBytes = certificate.getEncoded();
				
				// Certifivate in Base64-encoded
				String certText = Base64.getEncoder().encodeToString(certBytes);
				System.out.println("Certificate (Base64):\n" + certText);
				
				// Create MD5 hash   you can use other cryptography encryption such as SHA
				MessageDigest md = MessageDigest.getInstance("MD5");
				byte[] digest = md.digest(certBytes);
				
				// Convert to hex string
				StringBuilder hexString = new StringBuilder();
				for (byte b : digest) {
					hexString.append(String.format("%02x", b));
				}
				return hexString.toString();
			} else {
				return "key generate failed !!" ;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// generating key pair
	private void generateKeyPair() {
		try {
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(
			KeyProperties.KEY_ALGORITHM_RSA, "AndroidKeyStore");
			
			KeyGenParameterSpec keyGenParameterSpec = new KeyGenParameterSpec.Builder(
			KEY_ALIAS,
			KeyProperties.PURPOSE_SIGN | KeyProperties.PURPOSE_VERIFY)
			.setDigests(KeyProperties.DIGEST_SHA256)
			.setCertificateSerialNumber(java.math.BigInteger.ONE)
			.setCertificateSubject(new javax.security.auth.x500.X500Principal("CN=Device ID"))
			.build();
			
			keyPairGenerator.initialize(keyGenParameterSpec);
			KeyPair keyPair = keyPairGenerator.generateKeyPair();
			System.out.println("Key pair generated successfully!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
