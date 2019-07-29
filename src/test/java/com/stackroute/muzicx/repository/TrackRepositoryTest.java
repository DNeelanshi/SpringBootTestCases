package com.stackroute.muzicx.repository;

import com.stackroute.muzicx.domain.Track;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TrackRepositoryTest {

    @Autowired
    TrackRepository trackRepository;
    Track track;

    List<Track> trackList= null;


    @Before
    public void setUp() {
        track = new Track(101,"songnew","good");
    }

    @After
    public void tearDown() {
        trackRepository.deleteAll();
    }


    //	method to check save() method of repository
    @Test
    public void testSaveTrack(){
        trackRepository.save(track);
        Assert.assertEquals(101,trackRepository.findById(track.getId()).get());

    }

    //	method to check save() method of repository
    @Test
    public void testSaveTrackFailure(){
        Track testUser = new Track(102,"song3","amazing");
        trackRepository.save(track);
        Track fetchUser = trackRepository.findById(track.getId()).get();
        Assert.assertNotSame(testUser,track);
    }

    //	method to check deleteById() method of repository
    @Test(expected = NoSuchElementException.class)
    public void testDeleteTrack(){
        Track testUser = new Track(102,"song4","wonderful");
        trackRepository.save(testUser);
        trackRepository.deleteById(testUser.getId());
        Assert.assertNotEquals(testUser,trackRepository.findById(testUser.getId()).get());
    }

    //	method to check update by id and existsById() method of repository
    @Test
    public void testUpdateTrack(){
        trackRepository.save(track);
        trackRepository.existsById(track.getId());
        track.setComment("good");
        trackRepository.save(track);
        Track fetchUser = trackRepository.findById(track.getId()).get();
        Assert.assertEquals("good",fetchUser.getComment());

    }

    //	method to check findAll() method of repository
    @Test
    public void testGetAllTrack(){
        Track track1 = new Track(102,"newsong","good song");
        Track track2 = new Track(103,"my song","excellent one");
        trackRepository.save(track1);
        trackRepository.save(track2);

        Track trackList = trackRepository.findById(102).get();
        Assert.assertEquals("newsong",trackList.getName());

    }
}
