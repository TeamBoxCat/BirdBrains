# TweetFetcher

A great tool for getting Tweets easily.

### Prerequisites

* Twitter4J library files
* TweetFetcher.java class file
* Twitter.java class file

## Getting Started

### Setup

Make sure the following is done to make sure it doesn’t break everything…

Create the object (do this as a global variable):

```
TweetFetcher tweetFetcher = new TweetFetcher();
```

Add all queries (do this in the setup method):

```
tweetFetcher.addQuery("#dumptrump -http -https -RT", 120);
                       ^                             ^
                  search query            number of required tweets
```

Load the Tweets (do this in the setup method):

Note: it will throw an exception if it cant connect, so be sure to handle it.

```
try {
	tweetFetcher.loadTweets();
}
catch(Exception e) {
	//add code here to display a screen that allows you to close the application/retry loading tweets
}
```

And that’s all for setup, folks!

## Using the TweetFetcher

To get a Tweet from the pool, use the following:

```
Tweet newTweet = tweetFetcher.getTweet(0);
                                       ^
                              order of desired query
         (if you declared a Trump query first, then Trump tweets would be 0)
```

To extract the message and retweets from a Tweet:

```
String tweetText = newTweet.getMessage();
int tweetRetweetValue = newTweet.getRetweets();
```


## Versioning

* Software version 1.0
* Doc version 1.0

## Authors

* **Jonathan Moallem**

See also the list of [contributors](https://bitbucket.org/box_cat/) who participated in this project.

