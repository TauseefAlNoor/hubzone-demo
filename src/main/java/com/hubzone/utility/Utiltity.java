package com.hubzone.utility;

public class Utiltity {
//public static String getRandomValue(){
//	return "asdaasdf@###sadas@#s45sadfjnsdfsdfklhj8934u23423423esfsfzc%%%";
//}
    private static final String ALPHA_NUM = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String ALPHA_NUM_SPECIAL = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz~!@#$%^&*()_+";

	/**
     * Get an AlphaNumeric Special character String of length 'len'
     * @param len
     * @return
     */
    public static String getRandomValue(final int len) {
        StringBuffer sb = new StringBuffer(len);
        for (int i = 0;  i < len;  i++) {
            int ndx = (int) (Math.random() * ALPHA_NUM_SPECIAL.length());
            sb.append(ALPHA_NUM_SPECIAL.charAt(ndx));
        }
        return sb.toString();
    }
}
