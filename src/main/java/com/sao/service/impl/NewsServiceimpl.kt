package com.sao.service.impl

import com.sao.utils.NewsCrawler;
import com.sao.domain.model.News
import com.sao.domain.repository.NewsRepository
import com.sao.service.NewsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class NewsServiceimpl : NewsService{


    lateinit var newsRepository: NewsRepository

    @Autowired
    constructor(newsRepository: NewsRepository){
        this.newsRepository = newsRepository
    }

    override fun saveNews() {
        val newsCrawler  = NewsCrawler()
        val newList = newsCrawler.news

        for (newsWrapper in newList){

            val columnTitle = newsWrapper.newsColumnTitle
            var contents = newsWrapper.newsContents

            for(newsContent in contents){
                val news = News()

                //if newsContent.imageUrl not null
                val temp :String? = newsContent.imageUrl

                if(temp.equals("")){
                    news.newsType = 0
                    news.imageUrl = ""
                }else{
                    news.imageUrl  = "http://www.ccnu.com.cn${newsContent.imageUrl!!}"
                    news.newsType = 1
                }

                news.contentUrl = "http://www.ccnu.com.cn/${newsContent.contentUrl}"

                news.newsContentTitle = newsContent.newsContentTitle

                news.newsColumn = columnTitle

                newsRepository.save(news)
            }
        }
    }

    override fun findBannerNews(): Iterable<News> {
        val iterable = newsRepository.findAll()
        var news = News()
        var bannerNews  =ArrayList<News>()
        var counter = 0
        for(new in iterable){
            if(news.newsType == 1L&& !news.newsContentTitle.equals("abcd")
            && counter<3) {
                counter ++
                bannerNews.add(news)
            }
        }
        return bannerNews.asIterable()
    }

    override fun findAllNews(): Iterable<News> {
        return newsRepository.findAll()
    }

    override fun findPlainNews(): Iterable<News> {
        val iterable = newsRepository.findAll()
        var news = News()
        var plainNews  =ArrayList<News>()
        for(new in iterable){
            if(news.newsType == 0L)
                plainNews.add(news)
        }
        return plainNews.asIterable()
    }
}