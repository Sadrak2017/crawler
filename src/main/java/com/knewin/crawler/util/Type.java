package com.knewin.crawler.util;

public enum Type {
  SPAN_TITLE("span[class=hl-title hl-title-2]"),
  NAME_FILE("Últimas notícias de mercado.txt"),
  DIV_NEWS("div[class=row py-3 item]"),
  ARTICLE_BODY("article"),
  ARTICLE_TITLE("h1[class=page-title-1"),
  ARTICLE_SUBTITLE("p[class=article-lead]"),
  ARTICLE_AUTHOR("span[class=author-name]"),
  ARTICLE_DATE("span[class=article-date]"),
  ARTICLE("div[class=col-md-9 col-lg-8 col-xl-6 m-sm-auto m-lg-0 article-content]"),
  PAGE("page/");
  private String html;

  Type(String html) {
    this.html = html;
  }

  public String get() {
    return html;
  }
}
