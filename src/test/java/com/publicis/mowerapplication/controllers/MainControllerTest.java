package com.publicis.mowerapplication.controllers;

import com.publicis.mowerapplication.exceptions.IncorrectContentException;
import com.publicis.mowerapplication.exceptions.IncorrectFileNameException;
import com.publicis.mowerapplication.services.MainService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class MainControllerTest {

    @Mock
    MainService mainService;

    @InjectMocks
    MainController mainController;

    MockMvc mockMvc;

    MockMultipartFile file;

    @BeforeEach
    void setUp() {

        String fileContent = "5 5\n" +
                "1 2 N\n" +
                "GAGAGAGAA\n" +
                "3 3 E\n" +
                "AADAADADDA\n";

        file = new MockMultipartFile(
                "file",
                "test.txt",
                MediaType.TEXT_PLAIN_VALUE,
                fileContent.getBytes()
        );

        mockMvc = MockMvcBuilders.standaloneSetup(mainController).setControllerAdvice(new ControllerExceptionHandler()).build();
    }

    @Test
    void postFile() throws Exception {

        mockMvc.perform(multipart("/").file(file))
                .andExpect(status().isOk());

        verify(mainService).processFile(file);
    }

    @Test
    void postFileWrongExtension() throws Exception {

        when(mainService.processFile(any())).thenThrow(IncorrectFileNameException.class);

        mockMvc.perform(multipart("/").file(file))
                .andExpect(status().isBadRequest());
    }

    @Test
    void postFileWrongContent() throws Exception {
        when(mainService.processFile(any())).thenThrow(IncorrectContentException.class);

        mockMvc.perform(multipart("/").file(file))
                .andExpect(status().isBadRequest());
    }

    @Test
    void postFileNumberFormatException() throws Exception {
        when(mainService.processFile(any())).thenThrow(NumberFormatException.class);

        mockMvc.perform(multipart("/").file(file))
                .andExpect(status().isBadRequest());
    }
}