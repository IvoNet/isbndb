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

package nl.ivonet.service;

import nl.ivonet.boundary.AuthorResponse;
import nl.ivonet.error.IsbnInvalidApiKeyException;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;


/**
 * A couple of Tests will fail if you have not created an isbndb.properties file in
 * src/test/resources with a 'api.key' property with valid api key.
 * As these keys are personal you won't get one from me :-)
 *
 * @author Ivo Woltring
 */
public class IsbndbTest {
    private Isbndb isbndb;

    @Before
    public void setUp() throws Exception {
        this.isbndb = new Isbndb();
    }

    @Test(expected = IsbnInvalidApiKeyException.class)
    public void exceptionInvalidKeyNull() throws Exception {
        isbndb.setApiKey(null);
        isbndb.authorById("foo");
    }

    @Test(expected = IsbnInvalidApiKeyException.class)
    public void exceptionInvalidKeyEmpty() throws Exception {
        isbndb.setApiKey("");
        isbndb.authorById("foo");
    }

    /**
     * READ CLASS JAVA_DOC WHEN TEST FAILS!
     */
    @Test
    public void authorById() throws Exception {
        final AuthorResponse response = isbndb.authorById("Ilona Andrews");
        assertNotNull(response);
        assertThat(response.getData()
                           .get(0)
                           .getFirstName(), is("Ilona"));
        assertThat(response.getData()
                           .get(0)
                           .getLastName(), is("Andrews"));
        assertThat(response.getIndexSearched(), is("author_id"));
        assertThat(response.getData()
                           .size(), is(1));
    }
}