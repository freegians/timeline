/*
 * IE7 이하 버전에서 "'console'이(가) 정의되지 않았습니다." 에러 처리
 */
var console = console || {
        log:function(){},
        warn:function(){},
        error:function(){}
    };


var TimelineClass = function () {

    this.getTimelineAll = function() {
        var result;
        $.ajax({
            url: ctx + '/api/timeline',
            dataType:'json',
            type:'get',
            async: false,
            success:function(res){
                result = res.data;
            }
        });

        return result;
    }

}


var timeline = new TimelineClass();
var timelineAll = timeline.getTimelineAll();
console.log("timelineAll: ", timelineAll);