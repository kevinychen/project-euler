
package main;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Security;
import java.util.Map;
import java.util.stream.Collectors;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.google.common.base.Strings;

public class Decrypter {

    public static void main(String[] args) throws Exception {
        boolean encryptMode = args.length >= 1 && args[0].equalsIgnoreCase("encrypt");

        Path solutionsPath = Paths.get("solutions.txt");
        if (!Files.exists(solutionsPath)) {
            System.err.println("Solutions file doesn't exist. Exiting.");
            System.exit(1);
        }

        Map<String, String> fileKeys = Files.lines(solutionsPath)
            .map(line -> line.split("\\.", 2))
            .filter(parts -> parts.length == 2 && parts[0].chars().allMatch(Character::isDigit) && !parts[1].trim().isEmpty())
            .collect(Collectors.toMap(
                parts -> String.format("p%03d.java", Integer.parseInt(parts[0])),
                parts -> parts[1].trim()));

        Security.addProvider(new BouncyCastleProvider());
        for (Path path : (Iterable<Path>) Files.walk(Paths.get("src/test/java"))::iterator)
            if (Files.isRegularFile(path)) {
                Path decryptedFile = Paths.get(path.toString().replaceAll("\\.enc$", ""));
                Path encryptedFile = Paths.get(decryptedFile + ".enc");
                String key = fileKeys.get(decryptedFile.toFile().getName());
                if (key != null && !key.isEmpty()) {
                    Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding", "BC");
                    SecretKeySpec spec = new SecretKeySpec(Strings.padEnd(key, 32, ' ').getBytes(StandardCharsets.UTF_8), "AES");
                    if (encryptMode && path.equals(decryptedFile)) {
                        cipher.init(Cipher.ENCRYPT_MODE, spec, new IvParameterSpec(new byte[16]));
                        byte[] encrypted = cipher.doFinal(Files.readAllBytes(decryptedFile));
                        Files.write(encryptedFile, encrypted);
                    } else if (!encryptMode && path.equals(encryptedFile)) {
                        cipher.init(Cipher.DECRYPT_MODE, spec, new IvParameterSpec(new byte[16]));
                        byte[] decrypted = cipher.doFinal(Files.readAllBytes(encryptedFile));
                        Files.write(decryptedFile, decrypted);
                    }
                }
            }

        System.out.println("Done.");
    }
}
