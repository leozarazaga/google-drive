package com.example.googledrive.service;

import com.example.googledrive.dto.CreateFileDto;
import com.example.googledrive.entity.File;
import com.example.googledrive.entity.Folder;
import com.example.googledrive.entity.User;
import com.example.googledrive.exception.FileAlreadyExistsException;
import com.example.googledrive.exception.FolderNotFoundException;
import com.example.googledrive.exception.UserNotFoundException;
import com.example.googledrive.repository.FileRepository;
import com.example.googledrive.repository.FolderRepository;
import com.example.googledrive.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.util.List;
import java.util.Optional;
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

    public File createFileInFolder(String userId, String folderId, CreateFileDto fileDto) {
        UUID userUUID = UUID.fromString(userId);
        UUID folderUUID = UUID.fromString(folderId);

        User user = userRepository.findById(userUUID).orElseThrow(() -> new UserNotFoundException(userId));
        Folder folder = folderRepository.findById(folderUUID).orElseThrow(() -> new FolderNotFoundException(folderId));

        Optional<File> existingFile = fileRepository.findByUserAndFolderAndFileName(user, folder, fileDto.getFileName());
        if (existingFile.isPresent()) {
            throw new FileAlreadyExistsException(userId, folderId);
        }

        File file = new File();
        file.setFileName(fileDto.getFileName());
        file.setContent(fileDto.getContent());
        file.setFolder(folder);
        file.setUser(user);

        return fileRepository.save(file);
    }


    public List<File> getAllFiles() {
        return fileRepository.findAll();
    }


}
