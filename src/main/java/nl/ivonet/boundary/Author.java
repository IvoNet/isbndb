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

import java.util.List;

/**
 * @author Ivo Woltring
 */
public class Author {
    private String authorId;
    private String name;
    private String latinName;
    private String lastName;
    private String firstName;
    private int bookCount;
    private List<String> categoryIds;
    private List<String> subjectIds;
    private List<String> bookIds;
    private String dates;

    public String getAuthorId() {
        return this.authorId;
    }

    public String getName() {
        return this.name;
    }

    public String getLatinName() {
        return this.latinName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public int getBookCount() {
        return this.bookCount;
    }

    public List<String> getCategoryIds() {
        return this.categoryIds;
    }

    public List<String> getSubjectIds() {
        return this.subjectIds;
    }

    public List<String> getBookIds() {
        return this.bookIds;
    }

    public String getDates() {
        return this.dates;
    }

}
