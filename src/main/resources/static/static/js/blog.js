/**
 * Created by Administrator on 2018/7/12.
 */
$(function () {
    var _this = this;
    this.init = function() {
        this.initVar();
    };
    this.initVar = function () {

        var E = window.wangEditor;
        var editor = new E('#content');
        editor.create()
    };

    this.init();
});