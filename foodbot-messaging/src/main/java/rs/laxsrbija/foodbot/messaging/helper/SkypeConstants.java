package rs.laxsrbija.foodbot.messaging.helper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SkypeConstants
{
	public static final String API_LOGIN = "https://login.skype.com/login";
	public static final String API_MSACC = "https://login.live.com/ppsecure/post.srf";
	public static final String API_HOST = "https://client-s.gateway.messenger.live.com/v1/";

	private static final String ENDPOINT_ADDRESS_PART = "users/ME/";
	public static final String ENDPOINT_ADDRESS_PART_ENDPOINTS = ENDPOINT_ADDRESS_PART + "endpoints";
	public static final String ENDPOINT_ADDRESS_PART_CONVERSATIONS = ENDPOINT_ADDRESS_PART + "conversations";

	public static final String FIELD_PPFT = "PPFT";
	public static final String FIELD_TOKEN_NAME = "t";

	public static final String COOKIE_MSPOK = "MSPOK";
	public static final String COOKIE_MSPREQU = "MSPRequ";
	public static final String COOKIE_CKTST = "CkTst";
}
