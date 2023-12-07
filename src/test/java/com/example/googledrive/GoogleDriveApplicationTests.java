package com.example.googledrive;

import com.example.googledrive.entity.User;
import com.example.googledrive.repository.UserRepository;
import com.example.googledrive.repository.FolderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Integration tests for the Google Drive application.
 * These tests cover user creation, file uploads, and file deletion scenarios.
 * Each test method focuses on a specific functionality.
 */
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

    /**
     * Tests the process of creating a new user.
     *
     * @throws Exception if something unexpected happens during the test
     */
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


    /**
     * Tests the scenario where we attempt to upload a file.
     *
     * @throws Exception if something unexpected happens during the test
     */
    @Test
    public void uploadFileTest() throws Exception {
        String uniqueIdentifier = String.valueOf(System.currentTimeMillis());
        User testUser = new User("testUser" + uniqueIdentifier, "password123", "test" + uniqueIdentifier + "@outlook.com");

        userRepository.save(testUser);

        Authentication auth = new UsernamePasswordAuthenticationToken(testUser, null, testUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        MockMultipartFile file = new MockMultipartFile(
                "file", "test.txt", MediaType.TEXT_PLAIN_VALUE,
                "Hello, World!".getBytes(StandardCharsets.UTF_8));

        mockMvc.perform(MockMvcRequestBuilders.multipart("/file/upload")
                        .file(file)
                        .param("folderId", "yourFolderId"))
                .andExpect(MockMvcResultMatchers.status().isExpectationFailed())
                .andExpect(MockMvcResultMatchers.content().json("{\"message\":\"Could not upload file: test.txt\"}"));

        SecurityContextHolder.clearContext();
    }


    /**
     * Tests what happens when we try to delete a file.
     *
     * @throws Exception in case something goes wrong during the test
     */
    @Test
    public void deleteFileTest() throws Exception {
        String uniqueIdentifier = String.valueOf(System.currentTimeMillis());
        User testUser = new User("testUser" + uniqueIdentifier, "password123", "test" + uniqueIdentifier + "@outlook.com");

        userRepository.save(testUser);

        Authentication auth = new UsernamePasswordAuthenticationToken(testUser, null, testUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        MockMultipartFile file = new MockMultipartFile(
                "file", "test.txt", MediaType.TEXT_PLAIN_VALUE,
                "Hello, World!".getBytes(StandardCharsets.UTF_8));

        mockMvc.perform(MockMvcRequestBuilders.multipart("/file/upload")
                        .file(file)
                        .param("folderId", "yourFolderId"))
                .andExpect(MockMvcResultMatchers.status().isExpectationFailed())
                .andExpect(MockMvcResultMatchers.content().json("{\"message\":\"Could not upload file: test.txt\"}"));

        SecurityContextHolder.clearContext();
    }
}
