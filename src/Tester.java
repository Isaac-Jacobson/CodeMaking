
public class Tester {

	public static void main(String[] args) {
		
		String plaintext = "abcd";
		String correctCipherText = "cpfh";
		String testCipherText = Cipher.vigenereCipherEncrypt(plaintext, "code", Cipher.ALPHABET);
		
		System.out.println(plaintext);
		System.out.println(correctCipherText);
		System.out.println(testCipherText);

		
	}

}