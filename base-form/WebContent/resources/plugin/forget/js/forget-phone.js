$(document).ready(function () {
    initBind();
});
function initBind() {
    $("#free").bind("click", function () {
        //var value=getVeriNum();
        var loginName = $.trim($("#loginname").val());
        var phoneNum = $.trim($("#phone").val());
        if (loginName == "" || loginName == null) {
            alert("用户名不能为空！");
            return;
        } else if (phoneNum == "" || phoneNum == null) {
            alert("手机号不能为空！");
            return;
        } else {
            $.ajax({
                url: contextPath+"/core/base/forget/sendRegexNum",
                type: "post",
                data: {loginName: loginName, phoneNum: phoneNum},
                success: function (msg) {
                    if (msg == "error") {
                        alert("发送异常,请点击重新发送验证码");
                        return "no";
                    } else if (msg == "N") {
                        alert("用户名或手机号有误，请重新输入");
                        return "no";
                    } else {
                        var i = 60;
                        $("#free").css("display", "none");
                        $("#free1").css("display", "block");
                        $("#free1").html(i + "秒后再获取");
                        var timer1 = setInterval(function () {
                            if (i > 0) {
                                $("#free1").html((--i) + "秒后再获取");
                            } else {
                                $("#free1").css("display", "none");
                                $("#free").css("display", "block");
                                clearInterval(timer1);
                            }
                        }, 1000);
                    }
                },
                error: function () {
                }
            });
        }

    });
    $("#save").bind("click", function () {
        save();
    });
    $("#verifyNum").bind("keyup", function () {
        if (13 == event.keyCode || 13 == event.which) {
            save();
        }
    })

}
function save() {
    var username = $.trim($("#loginname").val());
    var phoneNum = $.trim($("#phone").val());
    var verifyNum = $("#verifyNum").val();
    if (username == "" || username == null) {
        alert("用户名不能为空！");
        return;
    }
    if (phoneNum == "" || phoneNum == null) {
        alert("手机号不能为空！");
        return;
    }
    if (verifyNum == "" || verifyNum == null) {
        alert("请先获取验证码！！");
        return;
    }
    $.ajax({
        url: contextPath+"/core/base/forget/toResetPassword",
        type: "post",
        data: {loginname: $("#loginname").val(), verifyNum: $("#verifyNum").val(), type: "phone"},
        success: function (msg) {
            if (msg != null && msg != "" && eval(msg) != "404") {
                errCss("#verifyErr", eval(msg));
            } else if (eval(msg) == "404") {
                window.location.href = contextPath+"/core/base/forget/toErrorPage"
            } else {
                $("#getBackPasswordForm").attr("action", contextPath+"/core/base/forget/toChangePass");
                $("#getBackPasswordForm").trigger("submit");
            }

        },
        error: function () {
        }
    });
}
function getVeriNum() {
    var loginName = $("#loginname").val();
    var phoneNum = $("#phone").val();
    if (loginName == "" || loginName == null) {
        alert("用户名不能为空！");
        return "no";
    } else if (phoneNum == "" || phoneNum == null) {
        alert("手机号不能为空！");
        return "no";
    } else {
        $.ajax({
            url: contextPath+"/core/base/forget/sendRegexNum",
            type: "post",
            data: {loginName: loginName, phoneNum: phoneNum},
            success: function (msg) {
                if (msg == "error") {
                    alert("发送异常,请点击重新发送验证码");
                    return "no";
                }
                if (msg == "N") {
                    alert("用户名或手机号有误，请重新输入");
                    return "no";
                }
            },
            error: function () {
            }
        });
    }

}
function errCss(id, mes) {
    $(id).css({
        "color": "#e42224",
        "text-indent": "20px",
        "float": "left",
        "height": "25px",
        "line-height": "25px",
        "background": "url(/style/images/cross.png) no-repeat scroll 0 5px"
    });
    $(id).html(mes);
}
