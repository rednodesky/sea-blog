/**
 * Created by Administrator on 2018/7/18.
 */
$(function () {
    var _this = this;
    this.init = function() {
        this.registerEvent();
    };
    this.registerEvent = function () {
        $(".info").on("click",function () {
            var $this = $(this);
            var index =  $this.index();
            $(".info").removeClass("active");
            $this.addClass("active");
            $(".content").hide();
            $(".content").eq(index).show();
        })
    };

    this.init();
});