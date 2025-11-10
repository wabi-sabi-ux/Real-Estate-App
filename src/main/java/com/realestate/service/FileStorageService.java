package com.realestate.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.Set;
import java.util.UUID;

@Service
public class FileStorageService {

    @Value("${app.upload-dir:uploads}")
    private String uploadDir;

    // Accept common image extensions (more tolerant than MIME-type checks)
    private static final Set<String> ALLOWED_EXTS = Set.of("jpg", "jpeg", "png", "webp", "gif");

    public String storeForProperty(Long propId, MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) throw new IOException("Empty file.");

        String ext = getExtension(file.getOriginalFilename());
        if (!ALLOWED_EXTS.contains(ext)) {
            throw new IOException("Unsupported file extension: ." + ext + " (allowed: " + ALLOWED_EXTS + ")");
        }

        Path root = Paths.get(uploadDir).toAbsolutePath().normalize();
        Files.createDirectories(root);

        String safeName = "prop-" + propId + "-" + UUID.randomUUID() + "." + ext;
        Path target = root.resolve(safeName);

        try (InputStream in = file.getInputStream()) {
            Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
        }

        // Public URL exposed by WebConfig (/files/**)
        return "/files/" + safeName;
    }

    public String storeFile(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) throw new IOException("Empty file.");

        String ext = getExtension(file.getOriginalFilename());
        if (!ALLOWED_EXTS.contains(ext)) {
            throw new IOException("Unsupported file extension: ." + ext + " (allowed: " + ALLOWED_EXTS + ")");
        }

        Path root = Paths.get(uploadDir).toAbsolutePath().normalize();
        Files.createDirectories(root);

        String safeName = UUID.randomUUID().toString() + "." + ext;
        Path target = root.resolve(safeName);
        try (InputStream is = file.getInputStream()) {
            Files.copy(is, target, StandardCopyOption.REPLACE_EXISTING);
        }

        // Public URL exposed by WebConfig (/files/**)
        return "/files/" + safeName;
    }

    public void deleteByPublicUrl(String publicUrl) throws IOException {
        if (publicUrl == null || !publicUrl.startsWith("/files/")) return;
        String filename = publicUrl.substring("/files/".length());
        Path root = Paths.get(uploadDir).toAbsolutePath().normalize();
        Path target = root.resolve(filename);
        if (Files.exists(target)) Files.delete(target);
    }

    private String getExtension(String name) {
        if (name == null) return "";
        int i = name.lastIndexOf('.');
        return i >= 0 ? name.substring(i + 1).toLowerCase() : "";
    }
}
