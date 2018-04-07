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
#  $('#tweets').append tweet.user_name + "<br/>" + tweet.text + "<br/>" + tweet.href +  "<br/>" for tweet in tweets
  para = $ "<a>"
  para.append "<div  class=\"list-group-item list-group-item-action flex-column align-items-start\">" +
    "<div class=\"d-flex w-100 justify-content-between\">" +
    "<a href='#' class='mb-1'>" + tweet.user_name + "</a>"  +
    "</div>" +
    "<p class='mb-1'>" + tweet.text + "</p>" +
    " </div>" for tweet in tweets
  $('#tweets').html(para)






###
$ ->
  ws = new WebSocket $("body").data("ws-url")
  ws.onmessage = (event) ->
    message = JSON.parse event.data
    $('#time').append message.time + "<br/>"
    $('#test').append "abc"+ "<br/>"
###