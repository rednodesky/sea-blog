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
            $(".comment-list").empty().append(templateFun(data));
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
            var data ={
                content:$("textarea[name=content]").val(),
                userName:$("input[name=name]").val(),
                email:$("input[name=email]").val(),
                parentId:$("#parentId").val(),
                replyId:$("#commentId").val()
            };

            $.ajax({
                url:SEA.common.env.apiPublicPath + "/blog/comment/"+blogId,
                data:data,
                type: 'POST',
            }).success(function(data){
                if(data.success){
                    $("#commentId").val("")
                    $("#parentId").val("")
                    $("textarea[name=content]").val("");
                    $("input[name=name]").val("");
                    $("input[name=email]").val("");
                    _this.initComment();
                }
            });

            return false;
        });


        $(document).on("click", ".reply", function(e) {
            var $target = $(e.target);
            if(!$target.hasClass("cancelReply")){
                $(".reply").show();
                $(".cancelReply").hide();
                $target.parents(".comment-header").find(".reply").hide();
                $target.parents(".comment-header").find(".cancelReply").show();
                $("#commentId").val($target.data("commentId"));
                $("#parentId").val($target.parents("li").data("parentId"));
                $('html,body').animate({scrollTop:$('.submit').offset().top}, 800);
            }
        });


        $(document).on("click", ".cancelReply", function(e) {
            $(".reply").show();
            $(".cancelReply").hide();
            $("#commentId").val("")
            $("#parentId").val("")
        });

    };

    this.init();
});