# Crawler - 

Crawler is an open source web crawler for Java which provides a simple interface for
crawling the Web. Using it, you can setup a web crawler in few minutes.



### Using Maven

dependencies used

```xml
    <!-- Used to analyze, extract and manipulate data stored in HTML documents -->
    <dependency>
        <groupId>org.jsoup</groupId>
        <artifactId>jsoup</artifactId>
        <version>1.7.2</version>
    </dependency>
   
    <!-- Used for logging -->
    <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>slf4j-log4j12</artifactId>
        <version>1.7.25</version>
        <scope>test</scope>
    </dependency>
```



## Start

To test, just create an instance of the Crawler class and inform and pass as parameter the site you want to track and the depth, in this case the https://www.infomoney.com.br/mercados/ was used and the depth is used to search the first 3 pages of the 'Latest Markets' section:
```java
public class KnewinApplication {

  public static void main(String[] args) {
     Crawler crawler = new Crawler("https://www.infomoney.com.br/mercados/", 3);
     crawler.proNewsWebSite(); // Get all news 
  }

}

```

This method below is responsible for, extracting and manipulating data stored in HTML documents. The Jsoup library was used to do the extraction.

```java
  /**
   * Go to the page and get your source code
   * @return Elements
   * */
  private Elements fncVisit(String page, Type atributte) {
    logger.debug("Visiting page {}", page);
    Elements _rArticle = null;
    try {
      Document _rDocument = Jsoup.connect(page).get();
      _rArticle = _rDocument.select(atributte.get());

    } catch (Exception e) {
      System.err.println(e.getMessage());
      logger.error("Page not indexed", e);
    } finally {
      logger.info("Page indexed!");
    }
    return _rArticle;
  }
```