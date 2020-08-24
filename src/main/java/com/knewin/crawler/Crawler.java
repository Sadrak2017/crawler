package com.knewin.crawler;

import com.knewin.crawler.model.News;
import com.knewin.crawler.util.Type;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Document;
import org.slf4j.LoggerFactory;
import org.jsoup.nodes.Element;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.FileWriter;
import org.slf4j.Logger;
import org.jsoup.Jsoup;

public class Crawler {
  private final HashMap<String, HashSet<String>> _uPageNews = new  HashMap<>();
  private final Logger _uLogger = LoggerFactory.getLogger(Crawler.class);
  private final List<News> _uNewsList = new ArrayList<>();
  private final int _uMAX_DEPTH;
  private final String _uURL;
  private Integer _uRequest = 0;

  public Crawler(String URL, int depth) {
    this._uURL = URL;
    this._uMAX_DEPTH = depth;
  }

  /**
   * Get all links from the market section
   * @return HashSet<String>
   * */
  private HashSet<String> fncPageLinks(){
    HashSet<String> _rListPage = new HashSet<>();
    int _rI = 0;
    do {
      _rListPage.add(this._uURL + Type.PAGE.get() + ++_rI +"/");
    } while (_rI < this._uMAX_DEPTH);
    return _rListPage;
  }

  /**
   * Get all news links on each page of the market section
   * @return HashMap<String, HashSet<String>>
   * */
  private HashMap<String, HashSet<String>> fncNewsLinks()  {
    fncPageLinks().forEach(page -> {
      HashSet<String> _rListURL = new HashSet<>();
      Elements _rUrlNews = fncVisit(page, Type.DIV_NEWS);
      _rUrlNews.forEach(span -> {
        String _rUrl = span.select(Type.SPAN_TITLE.get()).get(0).children().attr("href");
        if(!_rUrl.isEmpty())
          _rListURL.add(_rUrl);
      });
      if(!_uPageNews.containsKey(page) && !_rListURL.isEmpty())
        _uPageNews.put(page, _rListURL); // get all links news
    });
    return _uPageNews;
  }

  /**
   * Get the news
   * @return void
   * */
  public void proNewsWebSite() {
    fncNewsLinks().forEach( (_rUrlPage, value)
      -> value.forEach(_rUlNews -> {
        Element _rArticle = fncVisit(_rUlNews, Type.ARTICLE_BODY).get(0);
      _uNewsList.add(
              new News(
                _rArticle.select(Type.ARTICLE_TITLE.get()).text()      ,
                _rArticle.select(Type.ARTICLE_SUBTITLE.get()).text()   ,
                fncMaskDate(_rArticle.select(Type.ARTICLE_DATE.get())) ,
                _rArticle.select(Type.ARTICLE_AUTHOR.get()).get(0)
                    .getAllElements().get(1).text()                    ,
                _rArticle.select(Type.ARTICLE.get()).text()            ,
                _rUlNews                                               ,
                _rUrlPage
              )
            );
        })
    );
    proPrintConsole(); // consist
  }

  /**
   * Go to the page and get your source code
   * @return Elements
   * */
  private Elements fncVisit(String page, Type atributte) {
    _uLogger.debug("Visiting page {}", page);
    Elements _rArticle = null;
    try {
      Document _rDocument = Jsoup.connect(page).get();
      _rArticle = _rDocument.select(atributte.get());

    } catch (Exception e) {
      System.err.println(e.getMessage());
      _uLogger.error("Page not indexed", e);
    } finally {
      _uRequest++;
    }
    return _rArticle;
  }

  /** print in console */
  private void proPrintConsole(){
    StringBuilder _rResult = new StringBuilder();
    fncPageLinks().forEach(page -> {
      Integer _rI = 0;
      for (News news : _uNewsList) {
        if (page.equals(news.getPageUrl()) && _rI.equals(0)){
          _rResult.append("\nPage indexed ("+news.getPageUrl() +") = [ \n ");
          _uNewsList.forEach(_rNews -> {
            if(_rNews.getPageUrl().equals(news.getPageUrl()))
              _rResult.append("\t\t\t" + _rNews.toString());
            }
          );
          _rResult.append("\t ]; --  \n ");
          _rI++;
        }
      }
    });
    _uLogger.info(String.valueOf(_rResult));
    writeToFile(_rResult);
  }

  /**
   * Consistency in text file
   * @return void
   * */
  protected void writeToFile(StringBuilder _rContent) {
    try {
      FileWriter writer;
      writer = new FileWriter(Type.NAME_FILE.get());
      writer.write(String.valueOf(_rContent));
      writer.close();
      _uLogger.info("Crawler saved to fale -> "+Type.NAME_FILE.get());
    } catch (IOException e) {
      System.err.println(e.getMessage());
    } finally {
      _uLogger.info("Crawler finish - Number of resquisitions carried out: "+_uRequest);

    }
  }

  /** Mask - does data conversion
   * @return String
   * */
  private String fncMaskDate(Elements _rHtmlDate) {
    DateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
    DateFormat output = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.US);
    String _rResult = "";
    String _rDate;
    try {
      if(_rHtmlDate.size() > 1)
        _rDate = _rHtmlDate.get(1).attr("datetime");
      else
        _rDate = _rHtmlDate.get(0).attr("datetime");
      Date date = input.parse(_rDate.substring (0, _rDate.length() - 6));
      _rResult = output.format(date);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return _rResult;
  }
}