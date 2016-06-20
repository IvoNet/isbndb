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
public class Book {
    private String bookId;
    private String summary;
    private String title;
    private String titleLong;
    private String titleLatin;
    private String isbn13;
    private String isbn10;
    private String editionInfo;
    private String marcEncLevel;
    private String urlsText;
    private String publisherId;
    private String publisherName;
    private String publisherText;
    private String language;
    private String notes;
    private String lccNumber;
    private String physicalDescriptionText;
    private String deweyDecimal;
    private String deweyNormal;
    private List<String> subject_ids;
    private List<BookAuthor> authorData;

    public String getBookId() {
        return this.bookId;
    }

    public String getSummary() {
        return this.summary;
    }

    public String getTitle() {
        return this.title;
    }

    public String getTitleLong() {
        return this.titleLong;
    }

    public String getTitleLatin() {
        return this.titleLatin;
    }

    public String getIsbn13() {
        return this.isbn13;
    }

    public String getIsbn10() {
        return this.isbn10;
    }

    public String getEditionInfo() {
        return this.editionInfo;
    }

    public String getMarcEncLevel() {
        return this.marcEncLevel;
    }

    public String getUrlsText() {
        return this.urlsText;
    }

    public String getPublisherId() {
        return this.publisherId;
    }

    public String getPublisherName() {
        return this.publisherName;
    }

    public String getPublisherText() {
        return this.publisherText;
    }

    public String getLanguage() {
        return this.language;
    }

    public String getNotes() {
        return this.notes;
    }

    public String getLccNumber() {
        return this.lccNumber;
    }

    public String getPhysicalDescriptionText() {
        return this.physicalDescriptionText;
    }

    public String getDeweyDecimal() {
        return this.deweyDecimal;
    }

    public String getDeweyNormal() {
        return this.deweyNormal;
    }

    public List<String> getSubject_ids() {
        return this.subject_ids;
    }

    public List<BookAuthor> getAuthorData() {
        return this.authorData;
    }

}
