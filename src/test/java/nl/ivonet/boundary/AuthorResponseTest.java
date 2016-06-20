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

package nl.ivonet.boundary;

import com.google.gson.Gson;
import nl.ivonet.parser.GsonFactory;
import org.junit.Before;
import org.junit.Test;

import static nl.ivonet.utility.Utils.readTestResourceAsString;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;


/**
 * @author Ivo Woltring
 */
public class AuthorResponseTest {

    private Gson gson;

    @Before
    public void setUp() throws Exception {
        this.gson = GsonFactory.getInstance()
                               .gson();
    }

    @Test
    public void authorsResponse() throws Exception {
        final String json = readTestResourceAsString("AuthorsResponse.json");
        final AuthorResponse response = this.gson.fromJson(json, AuthorResponse.class);
        assertFalse(response.getData()
                            .isEmpty());
        assertThat(response.getData()
                           .get(0)
                           .getFirstName(), is("Ilona"));
        assertThat(response.getCurrentPage(), is(1));
        assertThat(response.getPageCount(), is(7977));
        assertThat(response.getIndexSearched(), is("author_name"));
    }

    @Test
    public void error() throws Exception {
        final String json = readTestResourceAsString("ErrorInvalidApiKey.json");
        final AuthorResponse response = this.gson.fromJson(json, AuthorResponse.class);
        assertTrue(response.getData() == null);
        assertTrue(response.getError()
                           .contains("Invalid api key"));
    }

    @Test
    public void keystats() throws Exception {
        final String json = readTestResourceAsString("AuthorResponseWithStatistics.json");
        final AuthorResponse response = this.gson.fromJson(json, AuthorResponse.class);
        assertTrue(response.getData() != null);
        assertTrue(response.getError() == null);
        assertThat(response.getKeystats()
                           .getKeyId(), is("FOOBAR"));
    }
}