package com.example.googledrive.service;

import com.example.googledrive.repository.FileRepository;
import com.example.googledrive.repository.FolderRepository;
import com.example.googledrive.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


}
