package com.knewin.crawler.model;

public class News {

  private final String title;
  private final String subtitulo;
  private final String date;
  private final String author;
  private final String article;
  private final String url;
  private final String pageUrl;

  public News(String title, String subtitulo, String date, String author, String article, String url, String pageUrl) {
    this.title = title;
    this.subtitulo = subtitulo;
    this.date = date;
    this.author = author;
    this.article = article;
    this.url = url;
    this.pageUrl = pageUrl;
  }

  public String getDate() { return date; }
  public String getAuthor() { return author; }
  public String getArticle() { return article; }
  public String getTitle() { return title; }
  public String getSubtitulo() { return subtitulo; }
  public String getUrl() { return url; }
  public String getPageUrl() { return pageUrl; }

  @Override
  public String toString() {
    return
        "News = {                                     \n" +
        "\t\t\t\t\t\t - url:      "+getUrl()       + "\n" +
        "\t\t\t\t\t\t - title:    "+getTitle()     + "\n" +
        "\t\t\t\t\t\t - subtitle: "+getSubtitulo() + "\n" +
        "\t\t\t\t\t\t - author:   "+getAuthor()    + "\n" +
        "\t\t\t\t\t\t - date:     "+getDate()      + "\n" +
        "\t\t\t\t\t\t - content:  "+getArticle()   + "\n" +
        "\t\t\t\t\t},"                              + "\n";
  }
}