$(document).ready(function () {
    bind();
});
function bind() {
    $("[name=email]").bind("keydown", function (event) {
        if (event.keyCode == 13) {
            ajaxVerify();
        }
    })
}
function close() {
    $("[name=loginname]").attr("disabled", true);
    $("[name=email]").attr("disabled", true);
    $("#submit2").css("display","block");
    $("#submit1").css("display","none");
   
}
function open() {
    $("[name=loginname]").attr("disabled", false);
    $("[name=email]").attr("disabled", false);
    $("#submit2").css("display","none");
    $("#submit1").css("display","block");
}
function ajaxVerify() {
    close();
    var loginname = $("[name=loginname]").val();
    var email = $("[name=email]").val();
    if (loginname == "" || loginname == undefined || email == "" || email == undefined) {
        alert("用户名和邮箱不能为空");
        open();
        return;
    }
    $.ajax({
        type: "post",
        async: true,
        dataType: 'json',
        url: contextPath+'/core/base/forget/verifyEmail',
        data: {loginname: loginname, email: email},
        success: function (data) {
            if (data.msg != undefined && data.msg == 'fail') {
                alert("用户名或邮箱不正确，请重新输入!");
                open();
                return;
            } else if (data.msg == 'success') {
                window.location.href = contextPath+"/core/base/forget/sendEmail";
            }
        }, error: function (e1, e2) {
            alert(e1 + ":" + e2);
            open();
        }
    });
}