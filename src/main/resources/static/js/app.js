function nl2br(str){
    return str.replace(/\n/g, "<br />");
}

var TimelineClass = function () {

    var self = this;

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


        if(data) {
            var str = '';
            for(var i = 0; i < data.length; i++) {
                var timelineText = data[i].timelineText;
                timelineText = nl2br(timelineText);
                str += '<div class="post-box">';
                str += '     <div class="username"><a href="' + ctx + '/' + data[i].writerName + '">' + data[i].writerName + '</a></div>';
                str += '     <div class="time">' + data[i].createdDate + ' <button post-id="' + data[i].id + '" class="btn-delete-timeline">글삭제</button></div>';
                str += '     <div class="text">';
                str += timelineText;
                str += '     </div>';
                str += '</div>';
            }
            $(str).insertBefore('#timeline-bottom');

            $('.btn-delete-timeline').each(function() {
                $(this).unbind('click');
                $(this).click(function() {
                    self.deleteTimeline($(this).attr('post-id'));
                });
            })
        }


    }

    this.deleteTimeline = function(_id) {

        var result;
        $.ajax({
            url: ctx + '/api/timeline/delete?id=' + _id,
            dataType:'json',
            type:'delete',
            async: false,
            //data: {
            //    id: _id
            //},
            success:function(res){
                result = res
            }
        });
        return result;
    }

    this.posting = function(timelineText) {

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

    this.following = function(_userId) {

        var result;
        $.ajax({
            url: ctx + '/api/user/following',
            dataType:'json',
            type:'put',
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

    this.unFollowing = function(_userId) {

        var result;
        $.ajax({
            url: ctx + '/api/user/unFollowing?userId=' + _userId,
            dataType:'json',
            type:'delete',
            async: false,
            //data: {
            //    userId: _userId
            //},
            success:function(res){
                result = res.data;
            }
        });

        return result;
    }

}


