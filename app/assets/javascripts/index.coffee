$ ->
  ws = new WebSocket $("body").data("ws-url")
  ws.onmessage = (event) ->
    message = JSON.parse event.data
    updates = message.updates

    appendUpdate update for update in updates

appendUpdate = (update) ->
	appendKeyword update.keyword
	appendTweets update.tweets

appendKeyword = (key) ->
	$('#test').append key + "<br/>"

appendTweets = (tweets) ->
	 $('#tweets').append tweet.user_name + "\t\t" + tweet.text + "\t\t" + tweet.href +  "<br/>" for tweet in tweets
	




###
$ ->
  ws = new WebSocket $("body").data("ws-url")
  ws.onmessage = (event) ->
    message = JSON.parse event.data
    $('#time').append message.time + "<br/>"
    $('#test').append "abc"+ "<br/>"
###