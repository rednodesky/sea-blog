/**
 * Created by Administrator on 2018/7/18.
 */
$(function () {

    var _this = this;

    this.init = function() {
        this.initVar();
    };
    this.initVar = function () {
        _this.$el = $(".comment-list");
        _this.page=0;
        _this.pageCount = _this.$el.data("pageCount");

        window.scrollReveal = new scrollReveal({reset: true});
    };

    this.refresh = function () {
        if(_this.page< _this.pageCount){
            $.ajax({
                url:SEA.common.env.apiPublicPath + "/message?page="+ parseInt(_this.page+1),
                async:false
            }).done(function(data){
                var templateFun =  Handlebars.compile($("#dataTemplate").html());
                _this.$el.append(templateFun(data.content));
                _this.page = parseInt(_this.page)+1;
                _this.pageCount = data.totalPages;
                scrollReveal.init();
            });
        }
    };

    window.onscroll = function () {
        if(($(document).height()-20)<=($(window).height()+$(window).scrollTop())){
            _.throttle( _this.refresh(), 500 );
        }
    };
    this.init();
});