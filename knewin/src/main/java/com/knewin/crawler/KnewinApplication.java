package com.knewin.crawler;


public class KnewinApplication {

	public static void main(String[] args) {
		Crawler crawler = new Crawler("https://www.infomoney.com.br/mercados/", 3);
		crawler.proNewsWebSite();
	}

}
