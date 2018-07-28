/*nav*/
$(function () {
    $(".nav>li").hover(function () {
        $(this).children('ul').stop(true, true).show(400);
    }, function () {
        $(this).children('ul').stop(true, true).hide(400);
    })
});

/*banner*/
$(function () {
    $('#ban').easyFader();
});

$(function () {
    $(".hoverClass").mouseover(function () {
        $(this).css("color","#fff")
    })
    $(".hoverClass").mouseout(function () {
        $(this).css("color","#bdbdbd")
    })
});
