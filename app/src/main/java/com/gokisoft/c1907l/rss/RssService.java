package com.gokisoft.c1907l.rss;

import com.gokisoft.c1907l.Config;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Diep.Tran on 8/4/21.
 */

public interface RssService {
    @GET(Config.RSS_VIETNAMNET_BUSINESS)
    Call<RssFeed> getBussinessFeed();
}
