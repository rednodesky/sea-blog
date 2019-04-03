/**
 * Created by Administrator on 2019/4/2.
 */
$(function () {

    var _this = this;
    this.init = function() {
        this.initVar();
        this.initComment();
        this.registerEvent();
    };
    this.initVar = function () {
        window.scrollReveal = new scrollReveal({reset: true});
    };

    this.initComment = function () {
        var blogId = $("textarea[name=content]").data("blogId");

        $.ajax({
            url:SEA.common.env.apiPublicPath + "/blog/comment/"+blogId,
            method:'get'
        }).done(function(data){
            var templateFun =  Handlebars.compile($("#dataTemplate").html());
            $(".comment-list").append(templateFun(data));
            scrollReveal.init();
        });
    };

    this.registerEvent = function () {
        $(".submit").click(function () {
            var content = $("textarea[name=content]").val();
            if(content.length==0){
                return ;
            }
            var blogId = $("textarea[name=content]").data("blogId");

            $.ajax({
                url:SEA.common.env.apiPublicPath + "/blog/"+blogId,
                async:false
            }).done(function(data){
                var templateFun =  Handlebars.compile($("#dataTemplate").html());
                _this.targetBody.append(templateFun(data.content));
                _this.page = parseInt(_this.page)+1;
                _this.pageCount = data.totalPages;
                scrollReveal.init();
            });
        })

        $(document).on("click", ".reply", function(e) {
            var $target = $(e.target);
            $(".reply-form").hide();
            $target.parents("li").after($("#replyTemplate").html());

        });

    };

    this.init();
});