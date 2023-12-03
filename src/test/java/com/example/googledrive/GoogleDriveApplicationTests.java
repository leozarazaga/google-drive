package com.example.googledrive;

import com.example.googledrive.entity.Folder;
import com.example.googledrive.entity.User;
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


	@BeforeEach
	void clearDatabase() {
		userRepository.deleteAll();
        folderRepository.deleteAll();
	}


    @Test
    public void createUserTest() throws Exception{
        String uniqueIdentifier = String.valueOf(System.currentTimeMillis());
        User user = new User("testUser" + uniqueIdentifier, "test" + uniqueIdentifier + "@outlook.com");


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
        String uniqueIdentifier = String.valueOf(System.currentTimeMillis());
        User user = new User("testUser" + uniqueIdentifier, "test" + uniqueIdentifier + "@outlook.com");


        String userJson = objectMapper.writeValueAsString(user);
        RequestBuilder userRequest = MockMvcRequestBuilders.post("/user/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson);


        mockMvc.perform(userRequest)
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value(user.getUsername()))
                .andExpect(jsonPath("$.email").value(user.getEmail()));


        // Creating a folder
        Folder folder = new Folder("TestFolder");
        folder.setUser(user);

        // Save both user and folder
        userRepository.save(user);
        folderRepository.save(folder);

        String folderJson = objectMapper.writeValueAsString(folder);
        RequestBuilder folderRequest = MockMvcRequestBuilders.post("/folder/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(folderJson);

        mockMvc.perform(folderRequest)
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.folderName").value(folder.getFolderName()));

        // Creating a file
        MockMultipartFile file = new MockMultipartFile("file", "test-file.txt", MediaType.TEXT_PLAIN_VALUE, "Hello, World!".getBytes());

        // Performing file upload
        RequestBuilder fileUploadRequest = MockMvcRequestBuilders.multipart("/file/upload")
                .file(file)
                .param("folderId", folder.getId().toString())
                .param("userId", user.getId().toString());


        mockMvc.perform(fileUploadRequest)
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Uploaded the file successfully: test-file.txt"));
    }







}
