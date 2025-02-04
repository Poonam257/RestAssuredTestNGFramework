package com.spotify.oauth2.api.applicationApi;

import com.spotify.oauth2.api.RestResource;
import com.spotify.oauth2.pojo.Playlist;
import com.spotify.oauth2.utils.ConfigLoader;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static com.spotify.oauth2.api.Route.PLAYLISTS;
import static com.spotify.oauth2.api.Route.USERS;
import static com.spotify.oauth2.api.TokenManager.getToken;
import static io.restassured.RestAssured.given;

public class PlaylistApi {
//    static String access_token = "BQCBjDOX-lqT5P_dHtYUNMCHw5xl0jlROdNUXT-fonq_uED3980tiumSjup_-B8Aqfbb1t8kL73MotppsQWnti3wmvby7mEUzUd8cGqy0kGjQaMHj5jPiIZCaTe63rv7p2CFnKrDsjjwodxkxh7iMBwLLqJ96OmfgKBEFgq7m3zwb-JxBw-ooaMcRmNxJ7rfLiBI1zCNbhHX5m-ojgZgar8X9df5hqP6dSm8xMD1N0KFyGDOU-OyC7xNKvOxaqJ9M7kkMp9lvBycXrqZv1LDv_IVxPgFpLwbha-4cMGvjHPZ";

    @Step
    public static Response post(Playlist requestPlaylist) {
        return RestResource.post(USERS + "/" + ConfigLoader.getInstance().getUser() + PLAYLISTS, getToken(), requestPlaylist);

    }

    public static Response post(String token, Playlist requestPlaylist) {
        return RestResource.post(USERS + "/" + ConfigLoader.getInstance().getUser() + PLAYLISTS, token, requestPlaylist);

    }

    public static Response get(String playlistId) {
        return RestResource.get(PLAYLISTS + "/" + playlistId, getToken());
    }

    public static Response update(String playlistId, Playlist requestPlaylist) {
        return RestResource.update(PLAYLISTS + "/" +playlistId, getToken(), requestPlaylist);
    }
}