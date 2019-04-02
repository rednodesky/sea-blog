/**
 * Created by Administrator on 2019/4/2.
 */
$(function () {

    this.init = function() {
        this.initVar();
    };
    this.initVar = function () {
        window.scrollReveal = new scrollReveal({reset: true});
        scrollReveal.init();
    };

    this.init();
});