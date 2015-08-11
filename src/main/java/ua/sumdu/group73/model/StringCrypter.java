package ua.sumdu.group73.model;

import java.io.IOException;
import org.apache.commons.codec.binary.Base64;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
 
/**
 * Класс для шифрования и дешифрования строк
 * Использует библиотеку Apache Codec http://commons.apache.org/codec/
 * @author Рудницкий Валентин
 */
public class StringCrypter {
 
    private Cipher ecipher;
    private Cipher dcipher;
    
    private static StringCrypter instance;
    
    private byte[] key = new byte[]{16, 44, 55, 113, 8, 64, 8, 5};

    public static StringCrypter getInstance() {
    	if (instance == null) 
    		instance = new StringCrypter();
    	return instance;
    }
    
    private StringCrypter() {
        try {
        	DESSecretKey secKey = new DESSecretKey(key);
            ecipher = Cipher.getInstance(secKey.getAlgorithm());
            dcipher = Cipher.getInstance(secKey.getAlgorithm());
            ecipher.init(Cipher.ENCRYPT_MODE, secKey);
            dcipher.init(Cipher.DECRYPT_MODE, secKey);
        } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }
    }
  
    /**
     * Функция шифрования
     *
     * @param str строка открытого текста
     * @return зашифрованная строка в формате Base64
     */
    public String encrypt(String str) {
        try {
            byte[] utf8 = str.getBytes("UTF8");
            byte[] enc = ecipher.doFinal(utf8);
            return Base64.encodeBase64String(enc);
        } catch (IllegalBlockSizeException | BadPaddingException | UnsupportedEncodingException ex) {
            Logger.getLogger(StringCrypter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
 
    /**
     * Функция дешифрования
     *
     * @param str зашифрованная строка в формате Base64
     * @return расшифрованная строка
     */
    public String decrypt(String str) {
        try {
            byte[] dec = Base64.decodeBase64(str);
            byte[] utf8 = dcipher.doFinal(dec);
            return new String(utf8, "UTF8");
        } catch (IllegalBlockSizeException | BadPaddingException | IOException ex) {
            Logger.getLogger(StringCrypter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static class DESSecretKey implements SecretKey {
    	 
		private static final long serialVersionUID = 1L;
		private final byte[] key;
 
        /**
         * ключ должен иметь длину 8 байт
         */
        public DESSecretKey(byte[] key) {
            this.key = key;
        }
 
        @Override
        public String getAlgorithm() {
            return "DES";
        }
 
        @Override
        public String getFormat() {
            return "RAW";
        }
 
        @Override
        public byte[] getEncoded() {
            return key;
        }
    }
}