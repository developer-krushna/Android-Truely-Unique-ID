# Android-Truely-Unique-ID
Generating unique key using Device Attestation method 
## About 
It uses the Android Keystore system to generate a unique cryptographic key pair and a corresponding certificate. The certificate is then hashed (using MD5 in my code case) to create a unique identifier for the device. Resulting in making of truly unique key that can't be modified by device factory reset.. 
