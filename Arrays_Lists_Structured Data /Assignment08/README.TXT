------------------------------------------------------------------------
PROJECT TITLE: Assignment08 - Vigenere Cipher Breaker
PURPOSE OF PROJECT: Encrypt and decrypt text with Caesar and Vigenere ciphers, then break Vigenere-encrypted messages by testing key lengths against language dictionaries.
VERSION or DATE: 2026-05-08
HOW TO START THIS PROJECT: Open the folder in BlueJ. Create a VigenereBreaker object and run breakVigenere(), then choose an encrypted message file when prompted. The program loads dictionaries from the dictionaries folder.
AUTHORS: JVM 
USER INSTRUCTIONS: VigenereCipher encrypts and decrypts using an array of Caesar keys. VigenereBreaker slices encrypted text by key position, estimates keys with CaesarCracker, counts dictionary words, and compares multiple languages to select the best decryption. Sample encrypted texts and known-key files include aida, athens, oslusiadas, and titus-small examples; additional secret messages are in the messages folder.
