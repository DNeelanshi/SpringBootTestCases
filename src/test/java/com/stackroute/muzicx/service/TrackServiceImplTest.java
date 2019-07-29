package com.stackroute.muzicx.service;

import com.stackroute.muzicx.domain.Track;
import com.stackroute.muzicx.exception.TrackAlreadyExistsException;
import com.stackroute.muzicx.exception.TrackNotFoundException;
import com.stackroute.muzicx.repository.TrackRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.task.TaskRejectedException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TrackServiceImplTest {

    Track track;

    //Create a mock for UserRepository
    @Mock
    TrackRepository trackRepository;

    //Inject the mocks as dependencies into UserServiceImpl
    @InjectMocks
    TrackServiceImpl trackService;

    List<Track> list= null;

    @Before
    public void setUp(){
        //Initialising the mock object
        MockitoAnnotations.initMocks(this);
        track = new Track();
        track.setName("welcome");
        track.setId(202);
        track.setComment("Jenny");
        list = new ArrayList<>();
        list.add(track);
    }

    @Test
    public void saveTrackTestSuccess() throws TrackAlreadyExistsException {

        when(trackRepository.save((Track)any())).thenReturn(track);
        Track savedtrack = trackService.saveTrack(track);
        Assert.assertEquals(track,savedtrack);

        //verify here verifies that userRepository save method is called.
        verify(trackRepository,times(1)).save(track);

    }

    @Test
    public void getAllTracks() throws TrackNotFoundException {

        trackRepository.save(track);
        //stubbing the mock to return specific data
        when(trackRepository.findAll()).thenReturn(list);
        List<Track> tracklist = trackService.getAllTracks();
        Assert.assertEquals(list,tracklist);
    }


//    	Test for deleteTrack service method
    @Test
    public void deleteTrack() throws TrackAlreadyExistsException {
        Track track1 = new Track(304,"dbb","hhd");
        trackService.saveTrack(track);
        trackService.saveTrack(track1);
        trackService.deleteTrack(304);
        Assert.assertEquals(1,trackService.deleteTrack(304));

        //verify here verifies that trackRepository deleteById method is only called once
        verify(trackRepository,times(1)).deleteById(track1.getId());
    }

    //	Test for updateTrack service method
    @Test
    public void updateTrack() throws TrackNotFoundException {
        when(trackRepository.save(track)).thenReturn(track);
        when(trackRepository.existsById(track.getId())).thenReturn(true);
        track.setComment("worst");
        Track updatetrack = trackService.UpdateTrack(101,track);
        Assert.assertEquals(track,updatetrack);
        verify(trackRepository, times(1)).save(track);

    }

    }