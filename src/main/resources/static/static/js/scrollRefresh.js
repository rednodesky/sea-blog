/**
 * Created by Administrator on 2017/3/21.
 */
$(function () {

    var _this = this;

    this.init = function() {
        this.initVar();
    };
    this.initVar = function () {
        _this.$el = $("#article");
        _this.page=1;
        _this.pageCount = _this.$el.data("pageCount");
        _this.template = $("#tmpl-article");
        _this.body = $(".blogsbox",_this.$el);
    };

    this.refresh = function () {
        if(_this.page< _this.pageCount){
            $.ajax({
                url:GBPS.common.env.apiPublicPath + "/blog?page="+ parseInt(_this.page+1),
                async:false
            }).done(function(data){

                _this.body.append(_this.template.tmpl(data.data));

                _this.page = parseInt(_this.page)+1;
                _this.pageCount = data.pageCount;

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