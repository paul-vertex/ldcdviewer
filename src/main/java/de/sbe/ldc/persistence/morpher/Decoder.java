/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  org.apache.commons.codec.binary.Base64
 */
package de.sbe.ldc.persistence.morpher;

import de.sbe.utils.StringUtils;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import org.apache.commons.codec.binary.Base64;

public class Decoder {
    public String decode(String _text) {
        return this.decode(_text, Charset.defaultCharset().name());
    }

    public String decode(String _text, String _charset) {
        if (StringUtils.isEmptyString(_text)) {
            return "";
        }
        String decodedText = _text;
        if (decodedText.startsWith("%b:")) {
            decodedText = decodedText.substring("%b:".length());
            try {
                decodedText = new String(Base64.decodeBase64((byte[])decodedText.getBytes()), _charset);
            }
            catch (UnsupportedEncodingException _use) {
                decodedText = new String(Base64.decodeBase64((byte[])decodedText.getBytes()));
            }
        }
        if (decodedText.startsWith("%j:")) {
            decodedText = decodedText.substring("%j:".length());
        }
        return decodedText;
    }

    public byte[] decodeToBytes(String _text) {
        if (StringUtils.isEmptyString(_text)) {
            return null;
        }
        byte[] bytes = null;
        bytes = _text.startsWith("%b:") ? Base64.decodeBase64((byte[])_text.substring("%b:".length()).getBytes()) : Base64.decodeBase64((byte[])_text.getBytes());
        return bytes;
    }
}

