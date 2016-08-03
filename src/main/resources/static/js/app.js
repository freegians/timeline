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
                str += '<div class="post-box">';
                str += '     <div class="username"><a href="' + ctx + '/' + data[i].writerName + '">' + data[i].writerName + '</a></div>';
                str += '     <div class="time">' + data[i].createdDate + '</div>';
                str += '     <div class="text">';
                str += data[i].timelineText;
                str += '     </div>';
                str += '</div>';
            }
            console.log(str);
            $('#timeline-bottom').before(str);
        }


    }

}


