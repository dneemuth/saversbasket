package com.sb.web.utils;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
class EncryptionUtilsTest {

	@Test
	public void decryptString() {
		
		String encryptedText = EncryptionUtil.encrypt("12");
		System.out.println("encrypted:"+encryptedText);
		
		String decryptedText = EncryptionUtil.decrypt(encryptedText);		
		Assert.assertTrue(decryptedText.length()>0);
	}

}
