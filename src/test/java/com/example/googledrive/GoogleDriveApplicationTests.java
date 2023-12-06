package com.example.googledrive;

import com.example.googledrive.entity.File;
import com.example.googledrive.entity.Folder;
import com.example.googledrive.entity.User;
import com.example.googledrive.repository.FileRepository;
import com.example.googledrive.repository.UserRepository;
import com.example.googledrive.repository.FolderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class GoogleDriveApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FolderRepository folderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FileRepository fileRepository;


    @BeforeEach
    void clearDatabase() {
        userRepository.deleteAll();
        folderRepository.deleteAll();
    }


    @Test
    public void createUserTest() throws Exception {
        String uniqueIdentifier = String.valueOf(System.currentTimeMillis());
        User user = new User("testUser" + uniqueIdentifier, "password123", "test" + uniqueIdentifier + "@outlook.com");


        String userJson = objectMapper.writeValueAsString(user);

        RequestBuilder request = MockMvcRequestBuilders.post("/user/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson);

        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value(user.getUsername()))
                .andExpect(jsonPath("$.email").value(user.getEmail()));
    }

    @Test
    public void uploadFileTest() throws Exception {
        // Mock user
        String uniqueIdentifier = String.valueOf(System.currentTimeMillis());
        User user = new User("testUser" + uniqueIdentifier, "password123", "test" + uniqueIdentifier + "@outlook.com");
        userRepository.save(user);

        // Mock folder
        Folder folder = new Folder("TestFolder");
        folder.setUser(user);
        folderRepository.save(folder);

        // Create a MockMultipartFile for file upload
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "Hello, World!".getBytes());

        mockMvc.perform(MockMvcRequestBuilders.multipart("/folder/" + folder.getId() + "/upload")
                        .file(file)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk());
    }



}
