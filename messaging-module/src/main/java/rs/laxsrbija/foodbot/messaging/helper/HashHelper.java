package rs.laxsrbija.foodbot.messaging.helper;

import java.security.InvalidParameterException;
import java.util.*;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import rs.laxsrbija.foodbot.messaging.exception.FoodBotMessagingException;

public class HashHelper
{
	private static final String SKYPEWEB_APPID = "msmsgs@msnmsgr.com";
	private static final String SKYPEWEB_SECRET = "Q1P7W2E4J9R8U3S5";
	private static final String HEX_CHARS = "0123456789abcdef";
	private static final long MODULUS = 0x7fffffff;
	private static final long MAGIC_CONSTANT = 0x0e79a9c1;

	// Here be dragons. This code was mainly ported as-is from the SkPy library,
	// which itself uses the reverse-engineered code from the Skype JS file.
	// For further reading refer to: https://github.com/Terrance/SkPy/blob/master/skpy/conn.py#L748-L830
	public static String getMac256Hash(final String challenge)
	{
		String message = challenge + SKYPEWEB_APPID;

		// If the message length isn't a multiple of 8, add a right-padded character '0' until it is
		message += StringUtils.repeat("0", 8 - (message.length() % 8));

		final List<Long> challengeParts = toUint32Array(message);
		final String sha256HexString = DigestUtils.sha256Hex(challenge + SKYPEWEB_SECRET).toUpperCase();
		final String decodedHash;
		try
		{
			 decodedHash = Hex.decodeHex(sha256HexString).toString();
		}
		catch (DecoderException e)
		{
			throw new FoodBotMessagingException(e);
		}

		final List<Long> sha256Parts = toUint32Array(decodedHash.substring(0, 16));
		final List<Long> checkSumParts = checkSum64(challengeParts, sha256Parts);

		String result = "";
		for (int i = 0; i < sha256Parts.size(); i++)
		{
			final long comparisonResult = sha256Parts.get(i) ^ checkSumParts.get(i % 2);
			result += toLittleEndianHexString(comparisonResult);
		}

		return result;
	}

	private static List<Long> checkSum64(final List<Long> challengeParts, final List<Long> hashParts)
	{
		final int challengeLength = challengeParts.size();
		if (challengeLength < 2 || challengeLength % 2 != 0)
		{
			throw new IllegalArgumentException("The challenge must contain at least 2 even parts");
		}

		final long hash0 = hashParts.get(0) & MODULUS;
		final long hash1 = hashParts.get(1) & MODULUS;
		final long hash2 = hashParts.get(2) & MODULUS;
		final long hash3 = hashParts.get(3) & MODULUS;

		long low = 0;
		long high = 0;

		long temp;
		for (int i = 0; i < challengeLength; i += 2)
		{
			temp = (challengeParts.get(i) * MAGIC_CONSTANT) % MODULUS;
			low = (((low + temp) * hash0) + hash1) % MODULUS;
			high += low;

			temp = challengeParts.get(i + 1);
			low = (((low + temp) * hash2) + hash3) % MODULUS; // TODO Check braces
			high += low;
		}

		low = (low + hash1) % MODULUS;
		high = (high + hash3) % MODULUS;

		return Arrays.asList(low, high);
	}

	// Returns a zero-padded (8 chars long) hex-string of the little-endian representation of the argument.
	private static String toLittleEndianHexString(final long n)
	{
		String result = "";
		for (long i = 0L; i < 4; i++)
		{
			result += HEX_CHARS.charAt((int)((n >> (i * 8 + 4)) & 15L));
			result += HEX_CHARS.charAt((int)((n >> (i * 8)) & 15L));
		}

		return result;
	}

	private static List<Long> toUint32Array(final String message)
	{
		final int length = message.length();

		if (length % 4 != 0)
		{
			throw new InvalidParameterException("The message length must be a multiple of 4");
		}

		final List<Long> uint32Array = new ArrayList<>(); // Todo fix the size to len / 4

		for (int i = 0, j = 0; i < length; i += 4, j++)
		{
			long value = 0L;
			value += (int)message.charAt(i); // TODO remove casting
			value += (int)message.charAt(i + 1) * (1 << 8);
			value += (int)message.charAt(i + 2) * (1 << 16);
			value += (int)message.charAt(i + 3) * (1 << 24);
			uint32Array.add(value);
		}

		return uint32Array;
	}
}
