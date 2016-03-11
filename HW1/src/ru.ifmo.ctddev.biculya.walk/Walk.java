package ru.ifmo.ctddev.biculya.walk;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.*;

/**
 * Created by gaudima on 2/10/16.
 */
public class Walk {
    private static BufferedReader br;
    private static BufferedWriter bw;
    private static Md5Hasher hasher = new Md5Hasher();

    private static void walkRecursive(Path path) throws IOException {
        if (Files.isDirectory(path)) {
            try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(path)) {
                for (Path p : directoryStream) {
                    walkRecursive(p);
                }
            }
        } else {
            String hash;
            try {
                hash = hasher.hashFile(path);
            } catch (IOException e) {
                hash = "00000000000000000000000000000000";
            }
            bw.write(hash);
            bw.write(" ");
            bw.write(path.toString());
            bw.write("\n");
        }
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Wrong number of arguments.");
            return;
        }
        try {
            br = Files.newBufferedReader(Paths.get(args[0]), Charset.forName("UTF-8"));
            bw = Files.newBufferedWriter(Paths.get(args[1]), Charset.forName("UTF-8"));

            String path = br.readLine();

            while (path != null) {
                walkRecursive(Paths.get(path));
                path = br.readLine();
            }
        } catch (NoSuchFileException e) {
            System.out.println("No such file: " + e.getFile());
        } catch (IOException e) {
            System.out.println("Error reading input or writing output file.");
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (bw != null) {
                    bw.close();
                }
            } catch (IOException ex) { }
        }
    }
}
