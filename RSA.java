/*
 * File: RSA.java
 * Name: Giacalone/McClellan
 * Date: 05/21/2016
 * -------------------------
 * The main class for the RSA cryptography program. Controls the keys and backend
 * encryption and decryption.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;

public class RSA {
	
	//makes the RSA key
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
	
	//takes the message and converts it to a number
	public static BigInteger convertFromString(String message) {
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
	
	//converts the number into the character message
	public static String convertToString(BigInteger num) {
		StringBuffer str = new StringBuffer("");
		String n = num.toString();
		if (n.length() % 3 == 1) n = "00" + n;
		else if (n.length() % 3 == 2) n = "0" + n;

		for (int index = 0; index < n.length(); index += 3)
			str.append((char) Integer.parseInt(n.substring(index, index + 3)));
		return str.toString();
	}

	// returns a large prime number
	public static BigInteger getPrime() {
		return BigInteger.probablePrime(500, new Random());
	}
	
	//gets a prime that is not equal to the provided integer
	public static BigInteger getPrime(BigInteger not) {
		BigInteger num = getPrime();
		while (num.equals(not))
			num = getPrime();
		return num;
	}

	// runs the program
	public static void main(String[] args) throws IOException {
		RSA_Frame a = new RSA_Frame("Ashok", true, null);
		RSA_Frame b = new RSA_Frame("Barbara", false, a);
	}

}
