package com.stackroute.muzicx.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.muzicx.domain.Track;
import com.stackroute.muzicx.service.TrackService;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class TrackControllerTest {

    @MockBean
    private TrackService trackService;
private Track track;
    @InjectMocks
    TrackController trackController;

    @Autowired
    MockMvc mockMvc;


    @Test
    public void saveUser() throws Exception {
        String string = "Succesfully saved";
        when(trackService.saveTrack(any())).thenReturn(string);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/userservice")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(track)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print());

    }

    private static String asJsonString(final Object obj)
    {
        try{
            return new ObjectMapper().writeValueAsString(obj);

        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

}