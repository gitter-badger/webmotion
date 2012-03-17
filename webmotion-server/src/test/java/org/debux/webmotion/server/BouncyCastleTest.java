/*
 * #%L
 * Webmotion server
 * 
 * $Id$
 * $HeadURL$
 * %%
 * Copyright (C) 2011 - 2012 Debux
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as 
 * published by the Free Software Foundation, either version 3 of the 
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public 
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */
package org.debux.webmotion.server;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.engines.RijndaelEngine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.util.encoders.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

/**
 * Test on BouncyCastle.
 * 
 * @author julien
 */
public class BouncyCastleTest {
   
    private static final Logger log = LoggerFactory.getLogger(WebMotionUtilsTest.class);

    @Test
    public void testRijndael() throws DataLengthException, IllegalStateException, InvalidCipherTextException {
        BlockCipher engine = new RijndaelEngine(256);
        BufferedBlockCipher cipher = new PaddedBufferedBlockCipher(new CBCBlockCipher(engine));
        
        byte[] key = "0123456789abcdef0123456789abcdef".getBytes();
        cipher.init(true, new KeyParameter(key));
        
        byte[] input = "value".getBytes();
        byte[] cipherText = new byte[cipher.getOutputSize(input.length)];
        
        int cipherLength = cipher.processBytes(input, 0, input.length, cipherText, 0);
        cipher.doFinal(cipherText, cipherLength);
        
        String result = new String(Base64.encode(cipherText));
        log.info("result : " + result);
        AssertJUnit.assertNotNull(result);
    }
    
    @Test
    public void testSha1() throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

        byte[]   keyBytes = "0123456789abcdef0123456789abcdef".getBytes();
        SecretKey key = new SecretKeySpec(keyBytes, "HMac-SHA1");
        
        Mac mac = Mac.getInstance("HMac-SHA1", "BC");
        mac.init(key);
        mac.reset();
        
        byte[] input = "value".getBytes();
        mac.update(input, 0, input.length);
        byte[] out = mac.doFinal();
        
        String result = new String(Base64.encode(out));
        log.info("result : " + result);
        AssertJUnit.assertNotNull(result);
    }
}
