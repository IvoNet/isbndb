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
import nl.ivonet.dependency.GsonFactory;
import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;

import static nl.ivonet.utility.Utils.readTestResourceAsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Ivo Woltring
 */
public class PublisherResponseTest {
    private Gson gson;

    @Before
    public void setUp() throws Exception {
        this.gson = GsonFactory.getInstance()
                               .gson();
    }

    @Test
    public void publishersResponse() throws Exception {
        final String json = readTestResourceAsString("PublishersResponse.json");
        final PublisherResponse response = this.gson.fromJson(json, PublisherResponse.class);
        assertTrue(response.getData() != null);
        assertTrue(response.getError() == null);
        final Publisher publisher = response.getData()
                                            .get(0);
        assertThat(publisher.getName(), Is.is("Chapman"));
        assertNotNull(publisher.getBookIds());
        assertFalse(publisher.getBookIds()
                             .isEmpty());
        assertThat(publisher.getBookIds()
                            .get(0)
                            .length(), Is.is(47));
    }

    @Test
    public void publisherResponse() throws Exception {
        final String json = readTestResourceAsString("PublisherResponse.json");
        final PublisherResponse response = this.gson.fromJson(json, PublisherResponse.class);
        assertTrue(response.getData() != null);
        assertTrue(response.getError() == null);
        assertThat(response.getData()
                           .size(), Is.is(1));
        assertThat(response.getData()
                           .get(0)
                           .getBookIds()
                           .size(), Is.is(response.getData()
                                                  .get(0)
                                                  .getBookCount()));

    }


}