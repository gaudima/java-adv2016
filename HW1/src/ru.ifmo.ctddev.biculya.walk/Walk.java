package ru.ifmo.ctddev.biculya.walk;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by gaudima on 2/10/16.
 */
public class Walk {
    private static BufferedReader br;
    private static BufferedWriter bw;
    private static Md5Hasher hasher = new Md5Hasher();
    private static void walkRecursive(Path path) throws IOException {
        if(Files.isDirectory(path)) {
            try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(path)) {
                for(Path p : directoryStream) {
                    walkRecursive(p);
                }
            }
        } else {
            String hash;
            try {
                hash = hasher.hashFile(path);
            } catch (Exception e) {
                hash = "00000000000000000000000000000000";
            }
            bw.write(hash);
            bw.write(" ");
            bw.write(path.toString());
            bw.write("\n");
        }
    }
    public static void main(String[] args) {
        try {
            br = Files.newBufferedReader(Paths.get(args[0]), Charset.forName("UTF-8"));
            bw = Files.newBufferedWriter(Paths.get(args[1]), Charset.forName("UTF-8"));
            String path = br.readLine();

            while(path != null) {
                walkRecursive(Paths.get(path));
                path = br.readLine();
            }
            br.close();
            bw.close();
        } catch(IOException e) {
            System.out.println(e.getMessage());
            try {
                br.close();
                bw.close();
            } catch (IOException ex) {}
        }
    }
}
