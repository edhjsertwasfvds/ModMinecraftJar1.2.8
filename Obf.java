public class Obf {
    private static final byte[] K = {0x13, 0x37, 0x69, 0x42};

    public static String d(byte[] b) {
        char[] r = new char[b.length];
        for (int i = 0; i < b.length; i++) r[i] = (char) (b[i] ^ K[i % K.length]);
        return new String(r);
    }

    public static byte[] e(String s) {
        byte[] b = new byte[s.length()];
        for (int i = 0; i < s.length(); i++) b[i] = (byte) (s.charAt(i) ^ K[i % K.length]);
        return b;
    }
}
