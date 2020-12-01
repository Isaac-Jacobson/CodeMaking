import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.*;

public class Cipher {
	public static final String ALPHABET = "abcdefghijklmnopqrsnippletuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789,.() '\"![]/%-_;?=:\n";
	public static final String SIMPLE_ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789 ";

	// Set this variable to the default alphabet you wish to use
	private static final String DEFAULT_ALPHABET = SIMPLE_ALPHABET;

	/**
	 * Returns plaintext encrypted by the rotation cipher with a shift of
	 * movement.
	 * 
	 * @param alphabet
	 *            the alphabet to be used for the encryption
	 * @param plainText
	 *            the plain text to be encrypted.
	 * @param shiftAmount
	 *            the number of characters in ALPHABET to shift by.
	 * @return returns the encrypted plainText.
	 */
	public static String rotationCipherEncrypt(String plainText, int shiftNumber, String alphabet) {
		String output = "";

		for (int i = 0; i < plainText.length(); i++) {

			int index = alphabet.indexOf(plainText.substring(i, i + 1));

			for (int j = 0; j < Math.abs(shiftNumber); j++) {
				if (index == alphabet.length() - 1 && (shiftNumber / Math.abs(shiftNumber)) == 1) {
					index = 0;
				} else if (index == 0 && (shiftNumber / Math.abs(shiftNumber)) == -1) {
					index = alphabet.length() - 1;
				} else {
					index += (shiftNumber / Math.abs(shiftNumber));
				}
			}

			output += alphabet.substring(index, index + 1);

		}
		return output;
	}

	public static String rotationCipherEncrypt(String plainText, int shiftAmount) {
		return rotationCipherEncrypt(plainText, shiftAmount, DEFAULT_ALPHABET);
	}

	/**
	 * Returns a the result of decrypting cipherText by shiftAmount using the
	 * rotation cipher.
	 * 
	 * @param alphabet
	 *            the alphabet to be used for the encryption
	 * @param cipherText
	 *            the encrypted text.
	 * @param shiftAmount
	 *            the key to decrypt the cipher.
	 * @return returns the decrypted cipherText.
	 */
	public static String rotationCipherDecrypt(String cipherText, int shiftNumber, String alphabet) {
		return rotationCipherEncrypt(cipherText, -shiftNumber, alphabet);
	}

	public static String rotationCipherDecrypt(String cipherText, int shiftAmount) {
		return rotationCipherDecrypt(cipherText, -shiftAmount, DEFAULT_ALPHABET);
	}

	/**
	 * Returns plaintext encrypted by the permutation cipher encoded with the
	 * input permutation. A permutation is given by a String the same length as
	 * alphabet that contains the exact same characters as alphabet but possibly
	 * in another ordering.
	 * 
	 * @param alphabet
	 *            the alphabet to be used for the encryption
	 * @param plainText
	 *            the plain text to be encrypted.
	 * @param permutation
	 *            a permutation of alphabet, to be used in encoding
	 * @return returns the encrypted plainText.
	 */
	public static String permutationCipherEncrypt(String plainText, String permutation, String alphabet) {
		String output = "";

		for (int i = 0; i < plainText.length(); i++) {

			int index = alphabet.indexOf(plainText.substring(i, i + 1));

			output += permutation.substring(index, index + 1);

		}
		return output;
	}

	/**
	 * Returns the result of decrypting cipherText with the key permutation.
	 * 
	 * @param alphabet
	 *            the alphabet to be used for the encryption
	 * @param cipherText
	 *            the encrypted text.
	 * @param code
	 *            the decryption key
	 * @return returns the decrypted cipherText.
	 */
	public static String permutationCipherDecrypt(String cipherText, String code, String alphabet) {
		return permutationCipherEncrypt(alphabet, code, cipherText);
	}

	/***
	 * Returns a random permutation of the alphabet string
	 * 
	 * @param alphabet
	 *            represents the alphabet
	 * @return a random permutation of alphabet
	 */
	public static String generateRandomPermutation(String alphabet) {

		int[] arr;

		if (alphabet.length() > 3) {
			arr = new int[alphabet.length() - 2];
		} else {
			return alphabet;
		}
		String outputWord = alphabet.substring(0, 1);

		for (int i = 0; i < alphabet.length() - 2; i++) {
			int a;

			do {
				a = (int) (Math.random() * (alphabet.length() - 2) + 1);
			} while (!match(a, arr));

			arr[i] = a;

			outputWord += alphabet.substring(a, a + 1);
		}
		return outputWord + alphabet.substring(alphabet.length() - 1, alphabet.length());

	}

	public static boolean match(int a, int[] arr) {
		for (int i = 0; i < arr.length; i++) {
			if (a == arr[i]) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Returns plaintext encrypted by the vigenere cipher encoded with the
	 * String code
	 * 
	 * @param alphabet
	 *            the alphabet to be used for the encryption
	 * @param plainText
	 *            the plain text to be encrypted.
	 * @param code
	 *            the code to use as the encryption key.
	 * @return returns the encrypted plainText.
	 */
	public static String vigenereCipherEncrypt(String plainText, String code, String alphabet) {
		String output = "";

		for (int i = 0; i < plainText.length(); i++) {

			int index = alphabet.indexOf(plainText.substring(i, i + 1));

			output += shiftLetter(index, alphabet.indexOf(code.substring(i % code.length(), i % code.length() + 1)),
					alphabet);
		}

		return output;
	}

	public static String shiftLetter(int index, int shiftAmount, String alphabet) {
		for (int j = 0; j < shiftAmount; j++) {
			if (index == alphabet.length() - 1) {
				index = 0;
			} else {
				index++;
			}
		}
		return alphabet.substring(index, index + 1);
	}

	public static String vigenereCipherEncrypt(String plainText, String code) {
		return vigenereCipherEncrypt(plainText, code, DEFAULT_ALPHABET);
	}

	/**
	 * Returns the result of decrypting cipherText with the key code.
	 * 
	 * @param alphabet
	 *            the alphabet to be used for the encryption
	 * @param cipherText
	 *            the encrypted text.
	 * @param code
	 *            the decryption key
	 * @return returns the decrypted cipherText.
	 */
	public static String vigenereCipherDecrypt(String cipherText, String code, String alphabet) {

		String inverse = "";
		for (int i = 0; i < code.length(); i++) {
			inverse += rotationCipherEncrypt(code.substring(i, i + 1),
					alphabet.length() - alphabet.indexOf(code.substring(i, i + 1)), alphabet);
		}

		return vigenereCipherEncrypt(cipherText, inverse, alphabet);
	}

	public static String vigenereCipherDecrypt(String cipherText, String code) {
		return vigenereCipherDecrypt(cipherText, code, DEFAULT_ALPHABET);
	}

	/**
	 * returns a copy of the input plaintext String with invalid characters
	 * stripped out.
	 * 
	 * @param plaintext
	 *            The plaintext string you wish to remove illegal characters
	 *            from
	 * @param alphabet
	 *            A string of all legal characters.
	 * @return String A copy of plain with all characters not in alphabet
	 *         removed.
	 */
	private static String stripInvalidChars(String plaintext, String alphabet) {
		StringBuilder b = new StringBuilder();
		for (int i = 0; i < plaintext.length(); i++) { // loop through plaintext
			if (alphabet.indexOf(plaintext.charAt(i)) >= 0) // get index of char
				b.append(plaintext.charAt(i)); // if it exists, keep it
			else
				// otherwise skip it &
				System.out.println("Stripping letter: \"" + plaintext.charAt(i) // display
																				// a
																				// message
						+ "\"");
		}
		return b.toString();
	}

	public static String loadFileAsString(String filepath) {
		return "";
	}

	public static String saveStringToFile(String contents, String filepath) {
		return "";
	}

}