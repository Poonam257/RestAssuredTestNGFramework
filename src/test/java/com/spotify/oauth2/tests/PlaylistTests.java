package com.spotify.oauth2.tests;

import com.spotify.oauth2.api.StatusCode;
import com.spotify.oauth2.api.applicationApi.PlaylistApi;
import com.spotify.oauth2.pojo.Error;
import com.spotify.oauth2.pojo.Playlist;
import com.spotify.oauth2.utils.DataLoader;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static com.spotify.oauth2.utils.FakerUtils.generateDescription;
import static com.spotify.oauth2.utils.FakerUtils.generateName;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@Epic("Spotify OAuth 2.0")
@Feature("Playlist API")
public class PlaylistTests extends BaseTest{

    @Step
    public Playlist playlistBuilder(String name, String description, boolean _public) {
        return Playlist.builder().
                name(name).description(description)
                ._public(_public).build();
    }

    @Step
    public void assertPlaylistEqual(Playlist requestPlaylist, Playlist responsePlaylist) {
        assertThat(responsePlaylist.getName(), equalTo(requestPlaylist.getName()));
        assertThat(requestPlaylist.getDescription(), equalTo(requestPlaylist.getDescription()));
        assertThat(responsePlaylist.get_public(), equalTo(requestPlaylist.get_public()));
    }

    @Step
    public void assertStatusCode(int actualStatusCode, StatusCode statusCode) {
        assertThat(actualStatusCode, equalTo(statusCode.code));
    }

    @Step
    public void assertError(Error responseErr, StatusCode statusCode) {
        assertThat(responseErr.getError().getStatus(), equalTo(statusCode.code));
        assertThat(responseErr.getError().getMessage(), equalTo(statusCode.msg));
    }

    @Story("Create A Playlist Story")
    @Link("https://example.org/")
    @Link(name = "allure", type = "mylink")
    @TmsLink("12345")
    @Issue("1234567")
    @Description("Create a Playlist")
    @Test(description = "should be able to create a playlist")
    public void ShouldBeAbleToCreateAPlaylist() {
        Playlist requestPlaylist = playlistBuilder(generateName(), generateDescription(), false);
        Response response = PlaylistApi.post(requestPlaylist);
        assertStatusCode(response.statusCode(), StatusCode.CODE_201);
        assertPlaylistEqual(response.as(Playlist.class), requestPlaylist);

    }

    @Test(description = "should be able to get a playlist")
    public void ShouldBeAbleToGetAPlaylist() {
//        Playlist requestPlaylist = playlistBuilder(generateName(), generateDescription(), false);
        Response response = PlaylistApi.get(DataLoader.getInstance().getGetPlaylistId());
        assertStatusCode(response.statusCode(), StatusCode.CODE_200);
//        assertPlaylistEqual(response.as(Playlist.class),requestPlaylist);

    }

    @Test(description = "should be able to update a playlist")
    public void ShouldBeAbleToUpdateAPlaylist() {
        Playlist requestPlaylist = playlistBuilder(generateName(), generateDescription(), false);
        Response response = PlaylistApi.update(DataLoader.getInstance().getUpdatePlaylistId(), requestPlaylist);
        assertStatusCode(response.statusCode(), StatusCode.CODE_200);
    }

    @Story("Create A Playlist Story")
    @Test(description = "should not be able to create a playlist with name")
    public void ShouldNotBeAbleToCreateAPlaylistWithName() {
        Playlist requestPlaylist = playlistBuilder("", generateDescription(), false);
        Response response = PlaylistApi.post(requestPlaylist);
        assertStatusCode(response.statusCode(), StatusCode.CODE_400);
        assertError(response.as(Error.class), StatusCode.CODE_400);
    }

    @Test(description = "should not be able to create a playlist with invalid token")
    public void ShouldNotBeAbleToCreateAPlaylistWithInvalidToken() {
        String invalid_token = "123456";
        Playlist requestPlaylist = playlistBuilder(generateName(), generateDescription(), false);
        Response response = PlaylistApi.post(invalid_token, requestPlaylist);
        assertStatusCode(response.statusCode(), StatusCode.CODE_401);
        assertError(response.as(Error.class), StatusCode.CODE_401);
    }

//    @Story("Create A Playlist Story")
//    @Test(description = "should not be able to create a playlist with expired token")
//    public void ShouldNotBeAbleToCreateAPlaylistWithExpiredToken() {
//        String expired_token = "BQBOG-LxcNfoXHRHq737qzR9NWNWyOpHrSQbpIoHQ7ANZ23riyi2hGHKGMtOxXCi8BoKWQdiBA9dTiWxVBweCIsCRODPl3NcdDfhAguwvvOTOUOyRso0VhD3LMOb_9v0zE89LHjKl-5E8-QVu4pz4vreQKC5W3XQGz_XIn8zOW9em_F9i74VMWcCzcDgsnop1qJQbnGPv64--oa1C-e2iGrXqDBY1Je_bQoEZG66Hdxb34zOpPhvfVNP4XX8E0mrYUrQM3mARKrUBArUY-1swpIs0ydYp63n-Adomx1KhnQZ";
//        Playlist requestPlaylist = playlistBuilder(generateName(), generateDescription(), false);
//        Response response = PlaylistApi.post(expired_token, requestPlaylist);
//        assertStatusCode(response.statusCode(), StatusCode.CODE_401);
//        assertError(response.as(Error.class), StatusCode.CODE_401);
//
//    }

}

