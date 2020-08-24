package com.knewin.crawler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KnewinApplication {

	public static void main(String[] args) {
    Logger _rLogger = LoggerFactory.getLogger(Crawler.class);
    long previousTime = System.currentTimeMillis();
		Crawler crawler = new Crawler("https://www.infomoney.com.br/mercados/", 3);
		crawler.proNewsWebSite();
    long currentTime = System.currentTimeMillis();
    _rLogger.info(String.format("Runtime Crawler: %s ms ", (currentTime - previousTime)));
	}

}
