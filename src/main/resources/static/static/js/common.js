/**
 * Created by Administrator on 2018/7/9.
 */
NS('SEA.common').env={
    apiPublicPath: 'http://'+$("meta[name=API-PATH]").attr('content') + '/public/api',
    apiPrivatePath: 'http://'+$("meta[name=API-PATH]").attr('content') + '/private/api'
};

$(function () {
    Handlebars.registerHelper('moment', function (date, options) {
        var formatStr = options.hash.format || 'YYYY-MM-DD';
        return new Handlebars.SafeString(moment(date).format(formatStr));
    });
});