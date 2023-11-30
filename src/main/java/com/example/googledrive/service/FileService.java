package com.example.googledrive.service;

import com.example.googledrive.dto.CreateFileDto;
import com.example.googledrive.entity.File;
import com.example.googledrive.entity.Folder;
import com.example.googledrive.entity.User;
import com.example.googledrive.exception.FileAlreadyExistsException;
import com.example.googledrive.exception.FileNotFoundException;
import com.example.googledrive.exception.FolderNotFoundException;
import com.example.googledrive.exception.UserNotFoundException;
import com.example.googledrive.repository.FileRepository;
import com.example.googledrive.repository.FolderRepository;
import com.example.googledrive.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

   public File store(MultipartFile file) throws IOException{
       String fileName = StringUtils.cleanPath(file.getOriginalFilename());
       File fileDB = new File(fileName, file.getContentType(), file.getBytes());
       return fileRepository.save(fileDB);
   }

   public List<File> getAllFiles(){
       return fileRepository.findAll();
    }

   public File getFile(String id){
       UUID uuid = UUID.fromString(id);
       return fileRepository.findById(uuid).orElseThrow(() -> new FileNotFoundException(id));
   }

}
