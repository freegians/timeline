function nl2br(str){
    return str.replace(/\n/g, "<br />");
}

var TimelineClass = function () {

    this.getTimeline = function(_userId) {

        var result;
        $.ajax({
            url: ctx + '/api/timeline',
            dataType:'json',
            type:'get',
            async: false,
            data: {
                userId: _userId
            },
            success:function(res){
                result = res.data;
            }
        });

        return result;
    }

    this.showTimeline = function(_userId) {
        if(!_userId) {
            _userId = 0;
        }
        var data = this.getTimeline(_userId);
        console.log('data:', data);


        if(data) {
            var str = '';
            for(var i = 0; i < data.length; i++) {
                var timelineText = data[i].timelineText;
                timelineText = nl2br(timelineText);
                str += '<div class="post-box">';
                str += '     <div class="username"><a href="' + ctx + '/' + data[i].writerName + '">' + data[i].writerName + '</a></div>';
                str += '     <div class="time">' + data[i].createdDate + '</div>';
                str += '     <div class="text">';
                str += timelineText;
                str += '     </div>';
                str += '</div>';
            }
            $(str).insertBefore('#timeline-bottom');
        }


    }

    this.posting = function(timelineText) {
        //var re = /\r\n/g    //개행문자를 나타내는 정규표현식
        //timelineText = timelineText.replace(re, "<br/>");   //개행문자를 <br/>로 치환

        //console.log('ttttttttttttttt', timelineText);
        var result;
        $.ajax({
            url: ctx + '/api/timeline/post',
            dataType:'json',
            type:'post',
            async: false,
            data: {
                timelineText: timelineText
            },
            success:function(res){
                result = res.data;
            }
        });

        return result;
    }

}


var UserClass = function () {

    this.getUserListAll = function() {

        var result;
        $.ajax({
            url: ctx + '/api/user/listAll',
            dataType:'json',
            type:'get',
            async: false,
            success:function(res){
                result = res.data;
            }
        });

        return result;
    }

    this.showUserListAll = function() {

        var data = this.getUserListAll()

        if(data) {
            var str = '';
            for(var i = 0; i < data.length; i++) {
                str += '<div class="user-list" userid="' + data[i].userId + '" username="' + data[i].userName + '">';
                str += '<a href="' + ctx + '/' + data[i].userName + '">' + data[i].userName + '</a>';
                str += '</div>';
            }
            $('#users-list').html(str);
        }


    }

}


var FollowerClass = function () {

    this.getFollowerList = function(_userId) {

        var result;
        $.ajax({
            url: ctx + '/api/user/follower',
            dataType:'json',
            type:'get',
            async: false,
            data: {
                userId: _userId
            },
            success:function(res){
                result = res.data;
            }
        });

        return result;
    }

    this.showFollowerList = function(_userId) {
        if(_userId <= 0) {
            return false;
        }

        var data = this.getFollowerList(_userId)

        if(data) {
            var str = '';
            for(var i = 0; i < data.length; i++) {
                str += '<div class="follower-list" userid="' + data[i].userId + '" username="' + data[i].userName + '">';
                str += '<a href="' + ctx + '/' + data[i].userName + '">' + data[i].userName + '</a>';
                str += '</div>';
            }
            $('#follower-list').html(str);
        }


    }

}


