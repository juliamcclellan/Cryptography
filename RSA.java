import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;

public class RSA {
	
	/*private BigInteger PRIME_1;
	private BigInteger PRIME_2;
	private BigInteger N;
	private BigInteger TOTIENT;
	private BigInteger E;
	private BigInteger D;*/

	/*public RSA() {
		PRIME_1 = getPrime();
		PRIME_2 = getPrime(PRIME_1);
		N = new BigInteger("" + PRIME_1).multiply(new BigInteger("" + PRIME_2));
		TOTIENT = (PRIME_1.subtract(BigInteger.ONE)).multiply((PRIME_2.subtract(BigInteger.ONE)));
		E = getPrime(TOTIENT);
		D = E.modInverse(TOTIENT);
	}*/
	
	public static void generateKey() throws IOException {
		BigInteger PRIME_1 = getPrime();
		BigInteger PRIME_2 = getPrime(PRIME_1);
		BigInteger N = new BigInteger("" + PRIME_1).multiply(new BigInteger("" + PRIME_2));
		BigInteger TOTIENT = (PRIME_1.subtract(BigInteger.ONE)).multiply((PRIME_2.subtract(BigInteger.ONE)));
		BigInteger E = getPrime(TOTIENT);
		BigInteger D = E.modInverse(TOTIENT);
		PrintStream writer = new PrintStream(new File("public.key"));
		writer.println(N.toString());
		writer.println(E.toString());
		writer.close();
		writer = new PrintStream(new File("private.key"));
		writer.println(N.toString());
		writer.println(D.toString());
		writer.close();
	}

	// using the public key (e and n) to encrpyt message
	public static BigInteger encrypt(String message) throws FileNotFoundException {
		Scanner scan = new Scanner(new File("public.key"));
		BigInteger N = new BigInteger(scan.nextLine());
		BigInteger E = new BigInteger(scan.nextLine());
		scan.close();
		BigInteger num = convertFromString(message);
		return num.modPow(E, N);
	}

	// using the private key (d and n) to decrypt message
	public static String decrypt(BigInteger message) throws FileNotFoundException {
		Scanner scan = new Scanner(new File("private.key"));
		BigInteger N = new BigInteger(scan.nextLine());
		BigInteger D = new BigInteger(scan.nextLine());
		scan.close();
		BigInteger num = message.modPow(D, N);
		return convertToString(num);
	}

	private static BigInteger convertFromString(String message) {
		String num = "";
		for (int i = 0; i < message.length(); i++) {
			int temp = (int) message.charAt(i);
			if (temp < 0) num += "000";
			else if (temp < 10) num += "00" + temp;
			else if (temp < 100) num += "0" + temp;
			else num += temp;
		}
		return new BigInteger(num);
	}

	private static String convertToString(BigInteger num) {
		StringBuffer str = new StringBuffer("");
		String n = num.toString();
		if (n.length() % 3 == 1) n = "00" + n;
		else if (n.length() % 3 == 2) n = "0" + n;

		for (int index = 0; index < n.length(); index += 3)
			str.append((char) Integer.parseInt(n.substring(index, index + 3)));
		return str.toString();
	}

	// returns a prime from 100 000 000 to 1 000 000 000
	public static BigInteger getPrime() {
		/*
		 * long n = (long)(900000000 * Math.random() + 100000000);
		 * while(!isPrime(n)) n = (long)(900000000 * Math.random() + 100000000);
		 * return new BigInteger("" + n);
		 */
		return BigInteger.probablePrime(500, new Random());
	}

	private static boolean isPrime(long n) {
		if (n <= 1) return false;
		else if (n <= 3) return true;
		else if (n % 2 == 0 || n % 3 == 0) return false;
		int i = 5;
		while (i * i <= n) {
			if (n % i == 0 || n % (i + 2) == 0) return false;
			i = i + 6;
		}
		return true;
	}

	public static BigInteger getPrime(BigInteger not) {
		BigInteger num = getPrime();
		while (num.equals(not))
			num = getPrime();
		return num;
	}

	// roughly 10 bits of BigInteger per character (well the key's length must be greater than or equal to the
	//encrypted message's length)
	public static void main(String[] args) throws IOException {
		//generateKey();
		String message = "Hi Julia, I am in physics right now!";//String message = "We can fit up to 100 characters in using the 1000 bit key. That means # of chars = 0.1 * # of bits!!";
		System.out.println("This is out message: " + message);
		BigInteger b1 = encrypt(message);
		String s1 = convertToString(b1);
		System.out.println("NEW STRING: " + s1);
		System.out.println("This is the encrypted message: " + b1);
		System.out.println("This is the decrypted message: " + decrypt(convertFromString(s1)));
		
	}

}
