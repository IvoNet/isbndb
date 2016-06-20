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

package nl.ivonet.isbndb;

import org.junit.Before;


/**
 * @author Ivo Woltring
 */
public class IsbndbTest {
    private Isbndb isbndb;

    @Before
    public void setUp() throws Exception {
        this.isbndb = new Isbndb();
    }

//    @Test
//    public void broadSearch() throws Exception {
//        final String json = readTestResourceAsString("isbndb_author_search.json");
//        final Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
// .create();
//        final IsbndbSearch isbndbSearch = gson.fromJson(json, IsbndbSearch.class);
//        System.out.println("isbndbSearch = " + isbndbSearch);
//        final String s = gson.toJson(isbndbSearch);
//        System.out.println("s = " + s);
//    }


}