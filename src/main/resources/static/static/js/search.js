/**
 * Created by Administrator on 2018/7/28.
 */
$(function () {
    $(".search_ico").click(function () {
        $(".search_bar").toggleClass('search_open');
        var keys = $("#keyboard").val();
        if (keys.length > 2) {
            $("#keyboard").val('');
            $("#searchform").submit();
        } else {
            return false;
        }
    });
});