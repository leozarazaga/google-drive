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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class FileService {

    private final UserRepository userRepository;
    private final FolderRepository folderRepository;
    private final FileRepository fileRepository;

    /**
     * This code does this
     *
     * @param userRepository
     * @param folderRepository
     * @param fileRepository
     */

    @Autowired
    public FileService(UserRepository userRepository, FolderRepository folderRepository, FileRepository fileRepository) {
        this.userRepository = userRepository;
        this.folderRepository = folderRepository;
        this.fileRepository = fileRepository;
    }

    /*    - - - - - - - - - - - - - - - - - - -   */

    public void store(MultipartFile file, String folderId, String userId) throws IOException {
        UUID folderUUID = UUID.fromString(folderId);
        Folder folder = folderRepository.findById(folderUUID).orElseThrow(() -> new FolderNotFoundException(folderId));

        UUID userUUID = UUID.fromString(userId);
        User user = userRepository.findById(userUUID).orElseThrow(() -> new UserNotFoundException(userId));

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        File fileDB = new File(fileName, file.getContentType(), file.getBytes());
        fileDB.setFolder(folder);
        fileDB.setUser(user);
        fileRepository.save(fileDB);
    }

    public List<File> getAllFilesByUser(User user) {
        return fileRepository.findByUser(user);
    }

    public File getFile(String id) {
        UUID uuid = UUID.fromString(id);
        return fileRepository.findById(uuid).orElseThrow(() -> new FileNotFoundException(id));
    }

    public void deleteFileById(String id) {
        UUID uuid = UUID.fromString(id);
        File file = fileRepository.findById(uuid).orElseThrow(() -> new FileNotFoundException(id));
        fileRepository.delete(file);
    }

}
