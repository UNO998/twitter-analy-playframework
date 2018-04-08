$ ->
  ws = new WebSocket $("body").data("ws-url")
  ws.onmessage = (event) ->
    message = JSON.parse event.data
    updates = message.updates

    appendUpdate update for update in updates

appendUpdate = (update) ->
	appendTweets update.tweets, update.keyword


appendTweets = (tweets, keyword) ->
  para = $ "<div>"
  para.append "<div  class=\"list-group-item list-group-item-action flex-column align-items-start\">" +
    "<div class=\"d-flex w-100 justify-content-between\">" +
    "<a href='userProfile/" + tweet.user_id + "'" + " class='mb-1'>" + tweet.user_name +  "</a>"  +
    "</div>" +
    "<p class='mb-1'>" + "<strong>" + keyword + "</strong>" + " " + tweet.text + "</p>" +
    " </div>" for tweet in tweets
  $('#tweets').html(para)

