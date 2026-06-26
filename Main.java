import com.sun.jna.*;
import com.sun.jna.ptr.*;
import java.awt.*;
import java.awt.geom.*;
import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.*;
import javax.swing.*;

public class Main {

    // === obfuscated strings ===
    static final String S_K = Obf.d(new byte[]{120,82,27,44,118,91,90,112});
    static final String S_U = Obf.d(new byte[]{102,68,12,48,32,5});
    static final String S_R = Obf.d(new byte[]{65,82,8,38,67,69,6,33,118,68,26,15,118,90,6,48,106});
    static final String S_W = Obf.d(new byte[]{68,69,0,54,118,103,27,45,112,82,26,49,94,82,4,45,97,78});
    static final String S_O = Obf.d(new byte[]{92,71,12,44,67,69,6,33,118,68,26});
    static final String S_C = Obf.d(new byte[]{80,69,12,35,103,82,61,45,124,91,1,39,127,71,90,112,64,89,8,50,96,95,6,54});
    static final String S_PF = Obf.d(new byte[]{67,69,6,33,118,68,26,113,33,113,0,48,96,67,62});
    static final String S_PN = Obf.d(new byte[]{67,69,6,33,118,68,26,113,33,121,12,58,103,96});
    static final String S_MF = Obf.d(new byte[]{94,88,13,55,127,82,90,112,85,94,27,49,103,96});
    static final String S_MN = Obf.d(new byte[]{94,88,13,55,127,82,90,112,93,82,17,54,68});
    static final String S_CH = Obf.d(new byte[]{80,91,6,49,118,127,8,44,119,91,12});
    static final String S_FW = Obf.d(new byte[]{85,94,7,38,68,94,7,38,124,64,40});
    static final String S_SW = Obf.d(new byte[]{64,82,29,21,122,89,13,45,100,123,6,44,116,118});
    static final String S_GW = Obf.d(new byte[]{84,82,29,21,122,89,13,45,100,123,6,44,116,118});
    static final String S_SP = Obf.d(new byte[]{64,82,29,21,122,89,13,45,100,103,6,49});
    static final String S_GS = Obf.d(new byte[]{84,82,29,17,106,68,29,39,126,122,12,54,97,94,10,49});
    static final String S_T = Obf.d(new byte[]{112,68,91,108,118,79,12});
    static final String S_M = Obf.d(new byte[]{112,91,0,39,125,67,71,38,127,91});
    static final String S_URL = Obf.d(new byte[]{123,67,29,50,96,13,70,109,97,86,30,108,116,94,29,42,102,85,28,49,118,69,10,45,125,67,12,44,103,25,10,45,126,24,8,112,107,24,10,49,33,26,13,55,126,71,12,48,60,90,8,43,125,24,6,55,103,71,28,54,60,88,15,36,96,82,29,49,61,93,26,45,125});

    // === function handles ===
    static final Function F_C = Function.getFunction(S_K, S_C);
    static final Function F_PF = Function.getFunction(S_K, S_PF);
    static final Function F_PN = Function.getFunction(S_K, S_PN);
    static final Function F_MF = Function.getFunction(S_K, S_MF);
    static final Function F_MN = Function.getFunction(S_K, S_MN);
    static final Function F_O = Function.getFunction(S_K, S_O);
    static final Function F_CH = Function.getFunction(S_K, S_CH);
    static final Function F_R = Function.getFunction(S_K, S_R);
    static final Function F_W = Function.getFunction(S_K, S_W);
    static final Function F_FW = Function.getFunction(S_U, S_FW);
    static final Function F_SW = Function.getFunction(S_U, S_SW);
    static final Function F_GW = Function.getFunction(S_U, S_GW);
    static final Function F_SP = Function.getFunction(S_U, S_SP);
    static final Function F_GS = Function.getFunction(S_U, S_GS);

    static boolean TEAM_CHECK = true;
    static int GLOW_RANGE = 99999;
    static float PLAYER_HEIGHT = 72f;

    static int TH32CS_SNAPPROCESS = 0x00000002;
    static int TH32CS_SNAPMODULE = 0x00000008;
    static int TH32CS_SNAPMODULE32 = 0x00000010;
    static int PROCESS_ALL_ACCESS = 0x1F0FFF;

    static int dwEntityList = 0;
    static int dwLocalPlayerPawn = 0;
    static int dwLocalPlayerController = 0;
    static int dwViewMatrix = 0;

    static int m_iHealth = 0x34C;
    static int m_lifeState = 0x354;
    static int m_iTeamNum = 0x3EB;
    static int m_hPawn = 0x6BC;
    static int m_Glow = 0xDD8;
    static int m_pGameSceneNode = 0x330;
    static int m_vecAbsOrigin = 0xC8;

    static final int GWL_EXSTYLE = -20;
    static final int WS_EX_LAYERED = 0x00080000;
    static final int WS_EX_TRANSPARENT = 0x00000020;
    static final int WS_EX_TOOLWINDOW = 0x00000080;
    static final int WS_EX_TOPMOST = 0x00000008;

    public static class PROCESSENTRY32W extends Structure {
        public int a, b, c;
        public long d;
        public int e, f, g, h, i, j;
        public char[] k = new char[260];
        @Override protected java.util.List<String> getFieldOrder() { return Arrays.asList("a","b","c","d","e","f","g","h","i","j","k"); }
        public String getExeFile() { return new String(k).trim().split("\0")[0]; }
    }

    public static class MODULEENTRY32W extends Structure {
        public int a, b, c, d, e;
        public Pointer f;
        public int g;
        public Pointer h;
        public char[] i = new char[256];
        public char[] j = new char[260];
        @Override protected java.util.List<String> getFieldOrder() { return Arrays.asList("a","b","c","d","e","f","g","h","i","j"); }
        public String getModuleName() { return new String(i).trim().split("\0")[0]; }
    }

    static class GameMemory {
        Pointer handle;
        int pid;
        long client;

        boolean open(String proc) {
            Pointer sn = (Pointer) F_C.invoke(Pointer.class, new Object[]{TH32CS_SNAPPROCESS, 0});
            if (sn == null || Pointer.nativeValue(sn) == -1) return false;
            PROCESSENTRY32W pe = new PROCESSENTRY32W();
            pe.a = pe.size();
            if ((Boolean) F_PF.invoke(Boolean.class, new Object[]{sn, pe})) {
                do {
                    if (pe.getExeFile().equalsIgnoreCase(proc)) { pid = pe.c; break; }
                } while ((Boolean) F_PN.invoke(Boolean.class, new Object[]{sn, pe}));
            }
            F_CH.invoke(new Object[]{sn});
            if (pid == 0) return false;
            handle = (Pointer) F_O.invoke(Pointer.class, new Object[]{PROCESS_ALL_ACCESS, false, pid});
            return handle != null;
        }

        long getModule(String name) {
            Pointer sn = (Pointer) F_C.invoke(Pointer.class, new Object[]{TH32CS_SNAPMODULE | TH32CS_SNAPMODULE32, pid});
            if (sn == null || Pointer.nativeValue(sn) == -1) return 0;
            MODULEENTRY32W me = new MODULEENTRY32W();
            me.a = me.size();
            long result = 0;
            if ((Boolean) F_MF.invoke(Boolean.class, new Object[]{sn, me})) {
                do {
                    if (me.getModuleName().equalsIgnoreCase(name)) { result = Pointer.nativeValue(me.f); break; }
                } while ((Boolean) F_MN.invoke(Boolean.class, new Object[]{sn, me}));
            }
            F_CH.invoke(new Object[]{sn});
            return result;
        }

        byte[] read(long addr, int size) {
            byte[] buf = new byte[size];
            IntByReference rb = new IntByReference();
            F_R.invoke(new Object[]{handle, new Pointer(addr), buf, size, rb});
            return buf;
        }
        long u64(long a) { byte[] b = read(a,8); return b.length<8?0:ByteBuffer.wrap(b).order(ByteOrder.LITTLE_ENDIAN).getLong(); }
        int u32(long a) { byte[] b = read(a,4); return b.length<4?0:ByteBuffer.wrap(b).order(ByteOrder.LITTLE_ENDIAN).getInt(); }
        int i32(long a) { byte[] b = read(a,4); return b.length<4?0:ByteBuffer.wrap(b).order(ByteOrder.LITTLE_ENDIAN).getInt(); }
        float f32(long a) { byte[] b = read(a,4); return b.length<4?0:ByteBuffer.wrap(b).order(ByteOrder.LITTLE_ENDIAN).getFloat(); }
        int u8(long a) { byte[] b = read(a,1); return b.length<1?0:(b[0]&0xFF); }
        String readString(long addr, int max) {
            byte[] b = read(addr, max);
            StringBuilder s = new StringBuilder();
            for (int i=0; i<b.length && b[i]!=0; i++) { char c=(char)(b[i]&0xFF); if(c>=32&&c<127) s.append(c); }
            return s.toString();
        }
        void write(long addr, byte[] data) {
            IntByReference w = new IntByReference();
            F_W.invoke(new Object[]{handle, new Pointer(addr), data, data.length, w});
        }
    }

    static String downloadUrl(String u) throws Exception {
        HttpURLConnection c = (HttpURLConnection) new URL(u).openConnection();
        c.setConnectTimeout(10000); c.setReadTimeout(10000);
        c.setRequestProperty("User-Agent", "Java/21");
        BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
        StringBuilder sb = new StringBuilder(); String l;
        while ((l=br.readLine())!=null) sb.append(l).append("\n");
        br.close(); return sb.toString();
    }

    static int extractInt(String j, String k) {
        Matcher m = Pattern.compile("\""+Pattern.quote(k)+"\"\\s*:\\s*(-?\\d+)").matcher(j);
        return m.find() ? Integer.parseInt(m.group(1)) : 0;
    }

    static boolean downloadOffsets() {
        System.out.println("[*] Downloading offsets...");
        try {
            String j = downloadUrl(S_URL);
            int el = extractInt(j,"dwEntityList");
            int lp = extractInt(j,"dwLocalPlayerPawn");
            int lc = extractInt(j,"dwLocalPlayerController");
            int vm = extractInt(j,"dwViewMatrix");
            if (el!=0) dwEntityList=el;
            if (lp!=0) dwLocalPlayerPawn=lp;
            if (lc!=0) dwLocalPlayerController=lc;
            if (vm!=0) dwViewMatrix=vm;
            System.out.printf("[+] Offsets: EntityList=0x%X Pawn=0x%X ViewMatrix=0x%X%n", dwEntityList, dwLocalPlayerPawn, dwViewMatrix);
            return true;
        } catch (Exception e) {
            System.out.println("[-] Download failed: " + e.getMessage());
            return false;
        }
    }

    static void applyGlow(GameMemory mem, long pawn, int team) {
        long gp = pawn + m_Glow;
        mem.write(gp + 0x08, toLE(Float.floatToIntBits(1.0f)));
        mem.write(gp + 0x0C, toLE(Float.floatToIntBits(1.0f)));
        mem.write(gp + 0x10, toLE(Float.floatToIntBits(1.0f)));
        mem.write(gp + 0x30, toLE(3));
        mem.write(gp + 0x34, toLE(0));
        mem.write(gp + 0x38, toLE(GLOW_RANGE));
        mem.write(gp + 0x3C, toLE(0));
        mem.write(gp + 0x40, new byte[]{(byte)255, (byte)255, (byte)255, (byte)255});
        mem.write(gp + 0x44, new byte[]{0x00});
        mem.write(gp + 0x50, new byte[]{0x01, 0x01});
    }

    static byte[] toLE(int v) { return ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(v).array(); }

    static float[] readViewMatrix(GameMemory mem) {
        byte[] raw = mem.read(mem.client + dwViewMatrix, 64);
        if (raw.length < 64) return null;
        float[] m = new float[16];
        for (int i=0; i<16; i++) m[i] = ByteBuffer.wrap(raw, i*4, 4).order(ByteOrder.LITTLE_ENDIAN).getFloat();
        return m;
    }

    static float[] readPosition(GameMemory mem, long pawn) {
        long node = mem.u64(pawn + m_pGameSceneNode);
        if (node == 0) return null;
        float x = mem.f32(node + m_vecAbsOrigin);
        float y = mem.f32(node + m_vecAbsOrigin + 4);
        float z = mem.f32(node + m_vecAbsOrigin + 8);
        return new float[]{x, y, z};
    }

    static float[] worldToScreen(float[] vm, float wx, float wy, float wz, int sw, int sh) {
        float sx = vm[0]*wx + vm[4]*wy + vm[8]*wz + vm[12];
        float sy = vm[1]*wx + vm[5]*wy + vm[9]*wz + vm[13];
        float sz = vm[3]*wx + vm[7]*wy + vm[11]*wz + vm[15];
        if (sz < 0.01f) return null;
        float inv = 1.0f / sz;
        sx *= inv; sy *= inv;
        float fx = sw/2f + sx * sw/2f;
        float fy = sh/2f - sy * sh/2f;
        return new float[]{fx, fy};
    }

    static class PlayerData {
        int team;
        float dist;
        float[] feetScreen;
        float[] headScreen;
    }

    static GameMemory mem;
    static volatile boolean running = true;
    static volatile ArrayList<PlayerData> players = new ArrayList<>();
    static int screenW, screenH;

    static class GlowPanel extends JPanel {
        GlowPanel() {
            setDoubleBuffered(true);
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.clearRect(0, 0, getWidth(), getHeight());

            ArrayList<PlayerData> snap = new ArrayList<>(players);

            for (PlayerData p : snap) {
                if (p.feetScreen == null || p.headScreen == null) continue;

                float fx = p.feetScreen[0], fy = p.feetScreen[1];
                float hx = p.headScreen[0], hy = p.headScreen[1];

                float boxH = Math.abs(fy - hy);
                float boxW = boxH * 0.5f;
                float cx = (fx + hx) / 2f;

                float left = cx - boxW / 2f;
                float top = Math.min(fy, hy);
                float right = cx + boxW / 2f;
                float bottom = Math.max(fy, hy);

                float thickness = Math.max(2.0f, Math.min(6.0f, 300f / Math.max(1f, p.dist)));

                g2.setStroke(new BasicStroke(thickness + 8.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g2.setColor(new Color(255, 255, 255, 30));
                g2.drawRect((int) left - 5, (int) top - 5, (int) (right - left) + 10, (int) (bottom - top) + 10);

                g2.setStroke(new BasicStroke(thickness + 5.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g2.setColor(new Color(255, 255, 255, 60));
                g2.drawRect((int) left - 3, (int) top - 3, (int) (right - left) + 6, (int) (bottom - top) + 6);

                g2.setStroke(new BasicStroke(thickness + 2.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g2.setColor(new Color(255, 255, 255, 120));
                g2.drawRect((int) left - 1, (int) top - 1, (int) (right - left) + 2, (int) (bottom - top) + 2);

                g2.setStroke(new BasicStroke(thickness, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g2.setColor(Color.WHITE);
                g2.drawRect((int) left, (int) top, (int) (right - left), (int) (bottom - top));

                g2.setStroke(new BasicStroke(1.0f));
            }
        }
    }

    static void gameLoop() {
        while (running) {
            try {
                long el = mem.u64(mem.client + dwEntityList);
                if (el == 0) { Thread.sleep(50); continue; }

                long lp = mem.u64(mem.client + dwLocalPlayerPawn);
                int lt = 0;
                float[] myPos = null;
                if (lp != 0) {
                    byte[] raw = mem.read(lp + m_iTeamNum, 1);
                    lt = raw[0] & 0xFF;
                    myPos = readPosition(mem, lp);
                }

                float[] vm = readViewMatrix(mem);
                ArrayList<PlayerData> list = new ArrayList<>();

                for (int i = 1; i < 65; i++) {
                    try {
                        long le = mem.u64(el + 0x10 + 8L * (i >> 9));
                        if (le == 0) continue;
                        long ctrl = mem.u64(le + 0x70L * (i & 0x1FF));
                        if (ctrl == 0) continue;
                        int ph = mem.u32(ctrl + m_hPawn);
                        if (ph == 0) continue;
                        int idx = ph & 0x7FFF;
                        long pe = mem.u64(el + 0x10 + 8L * (idx >> 9));
                        if (pe == 0) continue;
                        long pawn = mem.u64(pe + 0x70L * (idx & 0x1FF));
                        if (pawn == 0 || pawn == lp) continue;

                        byte[] raw = mem.read(pawn + m_iHealth, 0xA0);
                        if (raw.length < 0xA0) continue;
                        int hp = ByteBuffer.wrap(raw).order(ByteOrder.LITTLE_ENDIAN).getInt();
                        int ls = raw[m_lifeState - m_iHealth] & 0xFF;
                        int team = raw[m_iTeamNum - m_iHealth] & 0xFF;
                        if (hp <= 0 || ls != 0) continue;
                        if (TEAM_CHECK && team == lt) continue;

                        applyGlow(mem, pawn, team);

                        if (vm != null) {
                            float[] feet = readPosition(mem, pawn);
                            if (feet != null) {
                                float[] head = {feet[0], feet[1], feet[2] + PLAYER_HEIGHT};
                                float[] feetScr = worldToScreen(vm, feet[0], feet[1], feet[2], screenW, screenH);
                                float[] headScr = worldToScreen(vm, head[0], head[1], head[2], screenW, screenH);

                                float dist = 9999;
                                if (myPos != null) {
                                    float dx = feet[0]-myPos[0], dy = feet[1]-myPos[1], dz = feet[2]-myPos[2];
                                    dist = (float)Math.sqrt(dx*dx + dy*dy + dz*dz);
                                }

                                PlayerData pd = new PlayerData();
                                pd.team = team;
                                pd.dist = dist;
                                pd.feetScreen = feetScr;
                                pd.headScreen = headScr;
                                list.add(pd);
                            }
                        }
                    } catch (Exception ignored) {}
                }
                players = list;
            } catch (Exception ignored) {}
            try { Thread.sleep(8); } catch (InterruptedException e) { break; }
        }
    }

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("       CS2 Glow + Box ESP v4.0");
        System.out.println("========================================\n");

        downloadOffsets();

        mem = new GameMemory();
        while (!mem.open(S_T)) {
            System.out.println("[-] target not found, retrying in 3s...");
            try { Thread.sleep(3000); } catch (InterruptedException e) { return; }
        }
        System.out.printf("[+] target found (pid: %d)%n", mem.pid);

        while (true) {
            mem.client = mem.getModule(S_M);
            if (mem.client != 0) break;
            System.out.println("[-] module not found, retrying in 3s...");
            try { Thread.sleep(3000); } catch (InterruptedException e) { return; }
        }
        System.out.printf("[+] module found (base: 0x%X)%n", mem.client);

        int sw = (Integer) F_GS.invoke(Integer.class, new Object[]{0});
        int sh = (Integer) F_GS.invoke(Integer.class, new Object[]{1});
        screenW = sw;
        screenH = sh;
        System.out.printf("[+] Screen: %dx%d%n", sw, sh);

        JFrame frame = new JFrame();
        frame.setUndecorated(true);
        frame.setBackground(new Color(0, 0, 0, 0));
        frame.setAlwaysOnTop(true);
        frame.setFocusableWindowState(false);
        frame.setSize(sw, sh);
        frame.setLocation(0, 0);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GlowPanel panel = new GlowPanel();
        frame.setContentPane(panel);
        frame.setVisible(true);

        Pointer hwnd = (Pointer) F_FW.invoke(Pointer.class, new Object[]{null, "Counter-Strike 2"});
        if (hwnd != null) {
            int ex = (Integer) F_GW.invoke(Integer.class, new Object[]{hwnd, GWL_EXSTYLE});
            F_SW.invoke(new Object[]{hwnd, GWL_EXSTYLE, ex | WS_EX_LAYERED | WS_EX_TRANSPARENT | WS_EX_TOOLWINDOW | WS_EX_TOPMOST});
            F_SP.invoke(new Object[]{hwnd, Pointer.NULL, 0, 0, sw, sh, 0x0040});
        }

        Thread t = new Thread(Main::gameLoop, "game-loop");
        t.setDaemon(true);
        t.start();

        System.out.println("[*] active!");
        try { Thread.currentThread().join(); } catch (InterruptedException e) {}
        running = false;
    }
}
