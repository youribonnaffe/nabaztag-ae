package com.appspot.nabaztag;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.ArrayList;
import java.util.List;

// if directly embedded in Groovy, causes SecurityAccessException...
public class MessageEncoder {
    public static List<Byte> encode(String text) {
        int[] INVTABLE = {1, 171, 205, 183, 57, 163, 197, 239, 241, 27, 61, 167, 41, 19, 53, 223, 225, 139,
            173, 151, 25, 131, 165, 207, 209, 251, 29, 135, 9, 243, 21, 191, 193, 107, 141, 119, 249, 99, 133, 175, 177, 219, 253, 103,
            233, 211, 245, 159, 161, 75, 109, 87, 217, 67, 101, 143, 145, 187, 221, 71, 201, 179, 213, 127, 129, 43, 77, 55, 185, 35, 69,
            111, 113, 155, 189, 39, 169, 147, 181, 95, 97, 11, 45, 23, 153, 3, 37, 79, 81, 123, 157, 7, 137, 115, 149, 63, 65, 235, 13,
            247, 121, 227, 5, 47, 49, 91, 125, 231, 105, 83, 117, 31, 33, 203, 237, 215, 89, 195, 229, 15, 17, 59, 93, 199, 73, 51, 85, 255};

        Charset charset = Charset.forName("ISO-8859-1");
        CharsetDecoder decoder = charset.newDecoder();
        CharsetEncoder encoder = charset.newEncoder();

        String newData = null;

        try {
            // Convert a string to ISO-LATIN-1 bytes in a ByteBuffer
            // The new ByteBuffer is ready to be read.
            ByteBuffer bbuf = encoder.encode(CharBuffer.wrap(text));

            // Convert ISO-LATIN-1 bytes in a ByteBuffer to a character
            // ByteBuffer and then to a string.
            // The new ByteBuffer is ready to be read.
            CharBuffer cbuf = decoder.decode(bbuf);
            newData = cbuf.toString();

        } catch (CharacterCodingException e) {
            // Ignoring the exception
        }

        int size = newData.length() + 1;
        List<Byte> data = new ArrayList<Byte>();

        data.add((byte) 1);

        int previousChar = 35;
        char currentChar;
        int code;

        for (int i = 0; i < newData.length(); i++) {
            currentChar = newData.charAt(i);
            code = ((INVTABLE[previousChar % 128] * currentChar + 47) % 256);
            previousChar = currentChar;
            data.add((byte) code);
        }
        return data;
    }
}
