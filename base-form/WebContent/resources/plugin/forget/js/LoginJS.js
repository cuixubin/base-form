// 输入框
$(function () {
    $("#UserID").focus(function () {
        var txt_value = $(this).val();
        if (txt_value == this.defaultValue) {
            $(this).val("");
        }
        $(this).parent().removeClass("li1");
        $(this).parent().addClass("selected");
    })
    $("#UserID").blur(function () {
        var txt_value = $(this).val();
        if (txt_value == "") {
            $(this).val(this.defaultValue);
        }
        $(this).parent().removeClass("selected");
        $(this).parent().addClass("li1");
    })
});
$(function () {
    $("#TelNumber").focus(function () {
        var txt_value = $(this).val();
        if (txt_value == this.defaultValue) {
            $(this).val("");
        }
    })
    $("#TelNumber").blur(function () {
        var txt_value = $(this).val();
        if (txt_value == "") {
            $(this).val(this.defaultValue);
        }
    })
});
$(function () {
    $("#Password").focus(function () {
        var txt_value = $(this).val();
        if (txt_value == this.defaultValue) {
            $(this).val("");
        }
        $(this).parent().removeClass("li1");
        $(this).parent().addClass("selected");
    })
    $("#Password").blur(function () {
        var txt_value = $(this).val();
        if (txt_value == "") {
            $(this).val(this.defaultValue);
        }
        $(this).parent().removeClass("selected");
        $(this).parent().addClass("li1");
    })
});
// 登录页背景图切换

function leftCheck() {
    var activeImage = $('.active');
    activeImage.removeClass('active');
    if (activeImage.prev().length > 0) {
        activeImage.prev().addClass('active');
    } else {
        $("#imageContainer").children('img:last-child').addClass('active');
        //activeImage.next().addClass('active');
    }
}
;
function rightCheck() {
    var activeImage = $('.active');
    activeImage.removeClass('active');
    if (activeImage.next().length > 0) {
        activeImage.next().addClass('active');
    } else {
        $("#imageContainer").children('img:first-child').addClass('active');
        //activeImage.prev().addClass('active');
    }
}
;