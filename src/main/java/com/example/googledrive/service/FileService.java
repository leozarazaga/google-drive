package com.example.googledrive.service;

import com.example.googledrive.entity.File;
import com.example.googledrive.entity.Folder;
import com.example.googledrive.entity.User;
import com.example.googledrive.exception.FileNotFoundException;
import com.example.googledrive.exception.FolderNotFoundException;
import com.example.googledrive.exception.UserNotFoundException;
import com.example.googledrive.repository.FileRepository;
import com.example.googledrive.repository.FolderRepository;
import com.example.googledrive.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * Service responsible for managing file-related operations.
 * Handles file storage, retrieval, and deletion, as well as other file-related functionalities.
 */
@Service
public class FileService {
    private final FolderRepository folderRepository;
    private final FileRepository fileRepository;
    @Autowired
    public FileService(FolderRepository folderRepository, FileRepository fileRepository) {
        this.folderRepository = folderRepository;
        this.fileRepository = fileRepository;
    }

    /**
     * Stores a file in the specified folder for the given user.
     *
     * @param file     The file to be stored.
     * @param folderId The unique identifier of the folder where the file should be stored.
     * @param user     The user uploading the file.
     * @throws IOException             if an error occurs while processing the file content.
     * @throws FolderNotFoundException if the specified folder is not found.
     * @throws AccessDeniedException   if the user does not have permission to upload files to the folder.
     */
    @Transactional
    public void store(MultipartFile file, String folderId, User user) throws IOException {
        UUID folderUUID = UUID.fromString(folderId);
        Folder folder = folderRepository.findById(folderUUID)
                .orElseThrow(() -> new FolderNotFoundException(folderId));

        if (!folder.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("You do not have permission to upload files to this folder");
        }

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        File fileDB = new File(fileName, file.getContentType(), file.getBytes());
        fileDB.setFolder(folder);
        fileDB.setUser(user);
        fileRepository.save(fileDB);
    }

    /**
     * Retrieves a list of all files associated with a specific user.
     *
     * @param user The user for whom to retrieve files.
     * @return A list of files belonging to the user.
     */
    @Transactional
    public List<File> getAllFilesByUser(User user) {
        return fileRepository.findByUser(user);
    }

    /**
     * Retrieves a specific file by its unique identifier and associated user.
     *
     * @param id   The unique identifier of the file.
     * @param user The user associated with the file.
     * @return The file with the specified identifier.
     * @throws FileNotFoundException if the file is not found.
     */
    @Transactional
    public File getFileByUser(String id, User user) {
        UUID uuid = UUID.fromString(id);
        return fileRepository.findByIdAndUser(uuid, user).orElseThrow(() -> new FileNotFoundException(id));
    }

    /**
     * Deletes a file by its unique identifier and associated user.
     *
     * @param id   The unique identifier of the file to be deleted.
     * @param user The user associated with the file.
     * @throws FileNotFoundException if the file is not found.
     */
    @Transactional
    public void deleteFileByUser(String id, User user) {
        UUID uuid = UUID.fromString(id);
        File file = fileRepository.findByIdAndUser(uuid, user).orElseThrow(() -> new FileNotFoundException(id));
        fileRepository.delete(file);
    }
}
