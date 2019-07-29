package com.stackroute.muzicx.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.muzicx.MuzicxApplication;
import com.stackroute.muzicx.domain.Track;
import com.stackroute.muzicx.exception.TrackAlreadyExistsException;
import com.stackroute.muzicx.service.TrackService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = MuzicxApplication.class)
@WebMvcTest
public class TrackControllerTest {

    @MockBean
    private TrackService trackService;

     private Track track;

    @InjectMocks
    TrackController trackController;

    @Autowired
    MockMvc mockMvc;


    @Value("${Savesuccessmsg}")
    String success;

    private List<Track> trackList;
    @Before
    public void setUp(){

        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(trackController).build();

        Track track = new Track(201,"testcase","testcpmment");
        trackList = new ArrayList();

        trackList.add(track);
    }

    //	method to check save() method of controller
    @Test
    public void saveTrack() throws Exception {

        when(trackService.saveTrack(track)).thenReturn(track);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/track")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(track)))
                .andExpect(status().isCreated());

        verify(trackService,times(1)).saveTrack(Mockito.any(Track.class));

    }

    //	method to check get all tracks() method of controller

    @Test
    public void getAllTracks() throws Exception{
        when(trackService.getAllTracks()).thenReturn(trackList);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/track")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(track)))
                .andExpect(status().isOk());

       verify(trackService,times(1)).getAllTracks();

    }

    //	method to check update() method of controller

    @Test
    public void updateTrack() throws Exception {
        Track track1 = new Track(1,"testcase","testcomment");
        when(trackService.UpdateTrack(anyInt(), any())).thenReturn(track1);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/track/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(track)))
                .andExpect(status().isCreated());

//        verify(trackService,times(1)).UpdateTrack(1,Mockito.any(Track.class));

    }

    //	method to check delete() method of controller


    @Test
    public void delete() throws Exception {
        when(trackService.deleteTrack(anyLong())).thenReturn(1);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/track/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(track)))
                .andExpect(status().isCreated());

//        verify(trackService,times(1)).UpdateTrack(id,Mockito.any(Track.class));

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