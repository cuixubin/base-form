function reNew() {
    var newPass = $("[name=newPass]").val();
    var reNewPass = $("[name=reNewPass]").val();
    if (newPass == "" || newPass == undefined || reNewPass == "" || reNewPass == undefined) {
        alert("新密码或重复密码不能为空");
        return;
    }
    if (newPass != reNewPass) {
        alert("两次输入不一致");
        return;
    }
    if (newPass.trim().length < 6) {
        alert("新密码长度不得小于6个字符");
        return;
    }
    $.ajax({
        type: "post",
        async: true,
        dataType: 'json',
        url: contextPath+'/core/base/forget/resetPass',
        data: {newPass: newPass},
        success: function (data) {
            if (data.msg == 'success') {
                window.location.href = contextPath+"/core/base/forget/toResetPassSuccessPage"
            } else {
                alert(data.msg);
                return;
            }
        }, error: function (e1, e2) {
            alert(e1 + ":" + e2);
        }
    });
}