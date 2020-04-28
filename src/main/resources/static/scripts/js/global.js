function errorMessage(XMLHttpRequest) {
    if (XMLHttpRequest.status == 500) {
        layer.alert("服务器异常，请联系管理员！", {title: "错误信息", icon: 2});
    } else if (XMLHttpRequest.status == 404) {
        layer.alert("请求地址不存在，请联系管理员！", {title: "错误信息", icon: 2});
    } else {
        if (XMLHttpRequest.responseJSON && XMLHttpRequest.responseJSON.message) {
            //console.log(XMLHttpRequest.responseJSON.code);
            var message = XMLHttpRequest.responseJSON.message;
            var errors = XMLHttpRequest.responseJSON.errors;
            if (errors && errors.length > 0) {
                $.each(errors, function (i, error) {
                    if (error && error.message) {
                        message += "<br />" + error.message;
                    }
                });
            }
            layer.alert(message, {title: "错误信息", icon: 2});
        } else {
            layer.alert("服务器异常，请联系管理员！", {title: "错误信息", icon: 2});
        }
    }
}