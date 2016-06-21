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
import nl.ivonet.io.GsonFactory;
import org.junit.Before;
import org.junit.Test;

import static nl.ivonet.utility.Utils.readTestResourceAsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Ivo Woltring
 */
public class BookResponseTest {

    private Gson gson;

    @Before
    public void setUp() throws Exception {
        this.gson = GsonFactory.getInstance()
                               .gson();
    }


    @Test
    public void booksResponse() throws Exception {
        final String json = readTestResourceAsString("BooksResponse.json");
        final BookResponse response = this.gson.fromJson(json, BookResponse.class);
        assertTrue(response.getData() != null);
        assertTrue(response.getError() == null);
        final Book book = response.getData()
                                  .get(0);
        assertNotNull(book.getAuthorData());
        assertThat(book.getAuthorData()
                       .get(0)
                       .getId(), is("ilona_andrews"));
        assertThat(response.getData()
                           .get(1)
                           .getAuthorData()
                           .size(), is(2));
    }

    @Test
    public void bookResponse() throws Exception {
        final String json = readTestResourceAsString("BookResponse.json");
        final BookResponse response = this.gson.fromJson(json, BookResponse.class);
        assertTrue(response.getData() != null);
        final Book book = response.getData()
                                  .get(0);
        assertNotNull(book.getAuthorData());
        assertThat(book.getAuthorData()
                       .get(0)
                       .getId(), is("ilona_andrews"));
    }


}