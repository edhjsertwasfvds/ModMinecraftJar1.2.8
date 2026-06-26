import com.sun.jna.*;
import com.sun.jna.ptr.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

public class CS2Glow {

    // === obfuscated strings ===
    static final String A_0 = Obf.d(new byte[]{120,82,27,44,118,91,90,112});
    static final String A_1 = Obf.d(new byte[]{65,82,8,38,67,69,6,33,118,68,26,15,118,90,6,48,106});
    static final String A_2 = Obf.d(new byte[]{68,69,0,54,118,103,27,45,112,82,26,49,94,82,4,45,97,78});
    static final String A_3 = Obf.d(new byte[]{92,71,12,44,67,69,6,33,118,68,26});
    static final String A_4 = Obf.d(new byte[]{80,69,12,35,103,82,61,45,124,91,1,39,127,71,90,112,64,89,8,50,96,95,6,54});
    static final String A_5 = Obf.d(new byte[]{67,69,6,33,118,68,26,113,33,113,0,48,96,67,62});
    static final String A_6 = Obf.d(new byte[]{67,69,6,33,118,68,26,113,33,121,12,58,103,96});
    static final String A_7 = Obf.d(new byte[]{94,88,13,55,127,82,90,112,85,94,27,49,103,96});
    static final String A_8 = Obf.d(new byte[]{94,88,13,55,127,82,90,112,93,82,17,54,68});
    static final String A_9 = Obf.d(new byte[]{80,91,6,49,118,127,8,44,119,91,12});
    static final String A_A = Obf.d(new byte[]{112,68,91,108,118,79,12});
    static final String A_B = Obf.d(new byte[]{112,91,0,39,125,67,71,38,127,91});

    // === function handles ===
    static final Function F_0 = Function.getFunction(A_0, A_4);
    static final Function F_1 = Function.getFunction(A_0, A_5);
    static final Function F_2 = Function.getFunction(A_0, A_6);
    static final Function F_3 = Function.getFunction(A_0, A_7);
    static final Function F_4 = Function.getFunction(A_0, A_8);
    static final Function F_5 = Function.getFunction(A_0, A_3);
    static final Function F_6 = Function.getFunction(A_0, A_9);
    static final Function F_7 = Function.getFunction(A_0, A_1);
    static final Function F_8 = Function.getFunction(A_0, A_2);

    // === config ===
    static final float[] GLOW_COLOR = {1.0f, 0.0f, 0.0f};
    static final boolean TEAM_CHECK = true;

    // === offsets ===
    static final int O_0 = 0x24B0258;
    static final int O_1 = 0x206A9E0;
    static final int O_2 = 0x354;
    static final int O_3 = 0x35C;
    static final int O_4 = 0x3F3;
    static final int O_5 = 0x6C4;
    static final int O_6 = 0xCC0;

    // === constants ===
    static final int C_0 = 0x1F0FFF;
    static final int C_1 = 0x00000002;
    static final int C_2 = 0x00000008;
    static final int C_3 = 0x00000010;

    // === native structures ===
    public static class P32 extends Structure {
        public int a, b, c;
        public Pointer d;
        public int e, f, g, h, i, j;
        public byte[] k = new byte[260];
        @Override protected java.util.List<String> getFieldOrder() { return Arrays.asList("a","b","c","d","e","f","g","h","i","j","k"); }
    }

    public static class M32 extends Structure {
        public int a, b, c, d, e;
        public Pointer f;
        public int g;
        public Pointer h;
        public byte[] i = new byte[256];
        public byte[] j = new byte[260];
        @Override protected java.util.List<String> getFieldOrder() { return Arrays.asList("a","b","c","d","e","f","g","h","i","j"); }
    }

    // === memory class ===
    static class M {
        Pointer h = null;
        int p = 0;
        long c = 0;

        boolean o(String n) {
            Pointer s = (Pointer) F_0.invoke(Pointer.class, new Object[]{C_1, 0});
            P32 e = new P32(); e.a = e.size();
            if ((Boolean) F_1.invoke(Boolean.class, new Object[]{s, e})) {
                while (true) {
                    String name = new String(e.k).trim().replaceAll("\0", "").toLowerCase();
                    if (name.equals(n)) { p = e.c; break; }
                    if (!(Boolean) F_2.invoke(Boolean.class, new Object[]{s, e})) break;
                }
            }
            F_6.invoke(new Object[]{s});
            if (p == 0) return false;
            h = (Pointer) F_5.invoke(Pointer.class, new Object[]{C_0, false, p});
            return h != null;
        }

        long m(String n) {
            Pointer s = (Pointer) F_0.invoke(Pointer.class, new Object[]{C_2 | C_3, p});
            M32 e = new M32(); e.a = e.size();
            long r = 0;
            if ((Boolean) F_3.invoke(Boolean.class, new Object[]{s, e})) {
                while (true) {
                    String name = new String(e.i).trim().replaceAll("\0", "").toLowerCase();
                    if (name.equals(n)) { r = Pointer.nativeValue(e.f); break; }
                    if (!(Boolean) F_4.invoke(Boolean.class, new Object[]{s, e})) break;
                }
            }
            F_6.invoke(new Object[]{s});
            return r;
        }

        byte[] r(long a, int s) {
            byte[] b = new byte[s];
            com.sun.jna.Memory m = new com.sun.jna.Memory(s);
            LongByReference br = new LongByReference(0);
            F_7.invoke(new Object[]{h, new Pointer(a), m, s, br});
            m.read(0, b, 0, s);
            return b;
        }

        long u64(long a) {
            byte[] b = r(a, 8);
            return b.length < 8 ? 0 : ByteBuffer.wrap(b).order(ByteOrder.LITTLE_ENDIAN).getLong();
        }

        int u32(long a) {
            byte[] b = r(a, 4);
            return b.length < 4 ? 0 : ByteBuffer.wrap(b).order(ByteOrder.LITTLE_ENDIAN).getInt();
        }

        void w(long a, byte[] d) {
            com.sun.jna.Memory m = new com.sun.jna.Memory(d.length);
            m.write(0, d, 0, d.length);
            LongByReference bw = new LongByReference(0);
            F_8.invoke(new Object[]{h, new Pointer(a), m, d.length, bw});
        }
    }

    // === glow ===
    static void a(M m, long p, float r, float g, float b) {
        long gp = p + O_6;
        m.w(gp + 0x30, ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(3).array());
        byte[] rgba = new byte[]{(byte)(r*255), (byte)(g*255), (byte)(b*255), (byte)255};
        m.w(gp + 0x40, rgba);
        m.w(gp + 0x50, new byte[]{0x01, 0x01});
    }

    static void g(M m) {
        float r = GLOW_COLOR[0], g = GLOW_COLOR[1], b = GLOW_COLOR[2];
        System.out.println("[*] running");
        while (true) {
            try {
                long el = m.u64(m.c + O_0);
                if (el == 0) { Thread.sleep(50); continue; }

                long lp = m.u64(m.c + O_1);
                int lt = 0;
                if (lp != 0) {
                    byte[] raw = m.r(lp + O_4, 1);
                    lt = raw[0] & 0xFF;
                }

                for (int i = 1; i < 65; i++) {
                    try {
                        long le = m.u64(el + 0x10 + 8L * (i >> 9));
                        if (le == 0) continue;
                        long ctrl = m.u64(le + 0x70L * (i & 0x1FF));
                        if (ctrl == 0) continue;
                        int ph = m.u32(ctrl + O_5);
                        if (ph == 0) continue;
                        int idx = ph & 0x7FFF;
                        long pe = m.u64(el + 0x10 + 8L * (idx >> 9));
                        if (pe == 0) continue;
                        long pawn = m.u64(pe + 0x70L * (idx & 0x1FF));
                        if (pawn == 0 || pawn == lp) continue;

                        byte[] raw = m.r(pawn + O_2, 0xA0);
                        if (raw.length < 0xA0) continue;
                        int hp = ByteBuffer.wrap(raw).order(ByteOrder.LITTLE_ENDIAN).getInt();
                        int ls = raw[8] & 0xFF;
                        int team = raw[0x9F] & 0xFF;

                        if (hp <= 0 || ls != 0) continue;
                        if (TEAM_CHECK && team == lt) continue;

                        a(m, pawn, r, g, b);
                    } catch (Exception ignored) {}
                }
            } catch (Exception ignored) {}
            try { Thread.sleep(2); } catch (InterruptedException ignored) {}
        }
    }

    // === main ===
    public static void main(String[] args) {
        System.out.println("External\n");
        M m = new M();
        if (!m.o(A_A)) { System.out.println("[-] target not found"); return; }
        System.out.println("[+] target found         (pid: " + m.p + ")");
        m.c = m.m(A_B);
        if (m.c == 0) { System.out.println("[-] module not found"); return; }
        System.out.printf("[+] module found         (base: 0x%X)%n", m.c);
        System.out.printf("[+] color                (r=%.2f g=%.2f b=%.2f)%n", GLOW_COLOR[0], GLOW_COLOR[1], GLOW_COLOR[2]);
        System.out.println("[+] team check           " + (TEAM_CHECK ? "on" : "off"));
        g(m);
    }
}
