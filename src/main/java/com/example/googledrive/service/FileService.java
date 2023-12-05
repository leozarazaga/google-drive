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
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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


    @Autowired
    public FileService(UserRepository userRepository, FolderRepository folderRepository, FileRepository fileRepository) {
        this.userRepository = userRepository;
        this.folderRepository = folderRepository;
        this.fileRepository = fileRepository;
    }

    /*    - - - - - - - - - - - - - - - - - - -   */


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


    @Transactional
    public List<File> getAllFilesByUser(User user) {
        return fileRepository.findByUser(user);
    }


    @Transactional
    public File getFileByUser(String id, User user) {
        UUID uuid = UUID.fromString(id);
        return fileRepository.findByIdAndUser(uuid, user).orElseThrow(() -> new FileNotFoundException(id));
    }

    @Transactional
    public void deleteFileByUser(String id, User user) {
        UUID uuid = UUID.fromString(id);
        File file = fileRepository.findByIdAndUser(uuid, user).orElseThrow(() -> new FileNotFoundException(id));
        fileRepository.delete(file);
    }

}
