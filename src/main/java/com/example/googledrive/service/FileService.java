package com.example.googledrive.service;

import com.example.googledrive.entity.File;
import com.example.googledrive.exception.FileNotFoundException;
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

    @Autowired
    public FileService(UserRepository userRepository, FolderRepository folderRepository, FileRepository fileRepository) {
        this.userRepository = userRepository;
        this.folderRepository = folderRepository;
        this.fileRepository = fileRepository;
    }

    /*    - - - - - - - - - - - - - - - - - - -   */

    public void store(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        File FileDB = new File(fileName, file.getContentType(), file.getBytes());
        fileRepository.save(FileDB);
    }

    public List<File> getAllFiles() {
        return fileRepository.findAll();
    }
    public File getFile(String id) {
        UUID uuid = UUID.fromString(id);
        return fileRepository.findById(uuid).orElseThrow(() -> new FileNotFoundException(id));
    }


}
