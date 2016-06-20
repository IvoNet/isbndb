/*
 * Copyright 2016 Ivo Woltring <WebMaster@ivonet.nl>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package nl.ivonet.io;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * @author Ivo Woltring
 */
public class WebResource {

    private WebResource() {
    }

    private String getJson(final String url) {
        final HttpClient client = HttpClientBuilder.create()
                                                   .build();
        final HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader("Accept", "application/json");

        final HttpResponse response;
        try {
            response = client.execute(httpGet);
            if (response.getStatusLine()
                        .getStatusCode() == HttpStatus.SC_OK) {
                return EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
        return "";
    }

    @SuppressWarnings("UtilityClass")
    private static final class Instance {
        static final WebResource SINGLETON = new WebResource();
    }

    public static WebResource getInstance() {
        return Instance.SINGLETON;
    }
}
