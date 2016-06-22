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

import nl.ivonet.service.EndpointBuilder.Builder;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Ivo Woltring
 */
public class EndpointBuilderTest {

    @Test
    public void singleAuthor() throws Exception {
        final String endpoint = new Builder("FooKey").searchAuthor("Ilona Andrews")
                                                     .build()
                                                     .endpoint();
        assertEquals("http://isbndb.com/api/v2/json/FooKey/author/ilona_andrews?opt=keystats", endpoint);

    }

    @Test
    public void singleAuthorsWithPage() throws Exception {
        final String endpoint = new Builder("FooKey").searchAuthors("Ilona Andrews")
                                                     .page(888)
                                                     .build()
                                                     .endpoint();
        assertEquals("http://isbndb.com/api/v2/json/FooKey/authors?q=ilona+andrews&opt=keystats&page=888", endpoint);

    }

    @Test
    public void singleBook() throws Exception {
        final String endpoint = new Builder("FooKey").searchBook("Magic Bleeds")
                                                     .page(42)
                                                     .build()
                                                     .endpoint();
        assertEquals("http://isbndb.com/api/v2/json/FooKey/book/magic_bleeds?opt=keystats&page=42", endpoint);

    }
}