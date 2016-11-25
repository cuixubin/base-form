/*!
 * 评测申请主页js文件
 * 
 * Author: cuixubin
*/

var tempExpId;//评审记录id
var tempItemType;//评测项类型
//获取当前网址
var curWwwPath=window.document.location.href;
//获取主机地址之后的目录
var pathName=window.document.location.pathname;
var pos=curWwwPath.indexOf(pathName);
//获取主机地址
var localhostPaht=curWwwPath.substring(0,pos);
//获取带"/"的项目名
var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
var thePath = localhostPaht+projectName;

$(function(){
    //动态调整右侧导航样式
    if ($(window).height() <= 288){
        $(".rightNav").css({"top":0,"marginTop":0});
    } else{
        $(".rightNav").css({"top":"50%","marginTop":"-220px"});
    }
    var isIE6 = !!window.ActiveXObject && !window.XMLHttpRequest;
    if (isIE6){ $(window).scroll(function(){ btb.css("top", $(document).scrollTop() + 100) }); }

    //鼠标滑过评测项显示编辑和删除按钮
    $(document).on('mouseover', '.item-table-list-box', function () {
        if(editable) {
            $(this).find('.fa').show();
        }
    });
    $(document).on('mouseleave', '.item-table-list-box', function () {
        $(this).find('.fa').hide();
    });
});

/**
 * 将字符串日期格式化成yyyy/MM的形式
 * @param {type} theDate 字符串类型的时间 例：2016-07-29 15:40
 * @returns {String}
 */
function transDate(theDate) {
    theDate = theDate + "";
    var year = "";
    var month = "";
    var myDate = "";
    if(theDate !== null && theDate.length > 6) {
        year = theDate.substr(0,4);
        month = theDate.substr(5,2);
        if(month.charAt(0)=='0') {
            month = month.substr(1,1);
        }
        myDate = year + "/" + month;
    }else {
        myDate = "至今"
    }
    return myDate;
}

/**
 * 增加一条评测项记录
 * @param {type} expId 评审id
 * @param {type} itemType 评测项类型
 * @returns {undefined}
 */
function addItem(expId, itemType) {
    if(expId !== null && expId !== '') {
        tempExpId = expId;
        tempItemType = itemType;
        var url = thePath + '/jspc/zwpc/zwpc/'+ expId + '_' + itemType  +'/toAddEvalItemPage';
        sw.openModal({
            id: 'viewAddItem',
            icon: 'bell',
            title: '添加资料',
            height: 500,
            url: url,
            modalType: 'lg',
            showConfirmButton: true,
            showCloseButton: false,
            callback: function (data) {
                //提交资料
                var isOk = document.getElementById("viewAddItemFrame").contentWindow.addOneItem();
                if(isOk) {
                    sw.closeModal('viewAddItem');
                }
            }
        });
    }
}

/**
 * 删除一条评测项信息
 * @param {type} itemId 评测项id
 * @returns {undefined}
 */
function deleteItem(itemId) {
    if(!confirm('确定删除该资料吗？')){
        return;
    }
    var url = thePath + '/jspc/zwpc/zwpc/deleteItem';
    var params = new Object();
    params.itemId = itemId;
    sw.showProcessBar();
    sw.ajaxSubmit(url, params, function(data){
        sw.ajaxSuccessCallback(data, function() {
            $('#' + itemId).parents('.item-table-list-li').remove();
        });
    });
}

/**
 * 编辑某一条评测项
 * @param {type} itemId 评测项id
 * @param {type} itemType 评测项类型
 * @returns {undefined}
 */
function editItem(itemId, itemType) {
    tempItemType = itemType;
    var url = thePath + '/jspc/zwpc/zwpc/'+ itemId  +'/toAddEvalItemPage';
    sw.openModal({
        id: 'viewUpdateItem',
        icon: 'bell',
        title: '编辑资料',
        height: 500,
        url: url,
        modalType: 'lg',
        showConfirmButton: true,
        showCloseButton: false,
        callback: function (data) {
            //提交资料
            var isOk = document.getElementById("viewUpdateItemFrame").contentWindow.updateOneItem();
            if(isOk) {
                sw.closeModal('viewUpdateItem');
            }
        }
    });
}

/**
 * 从新加载某一类型的评测项数据
 * @param {type} expId 评审记录id
 * @param {type} itemType 评测项类型
 * @returns {undefined}
 */
function refreshItems(expId, itemType) {
    var url = thePath + '/jspc/zwpc/zwpc/'+ expId + '_' + itemType  +'/refreshItems';
    sw.showProcessBar();
    var params;
    sw.ajaxSubmit(url, params, function(data){
        sw.ajaxSuccessCallback(data, function(){
            if(data.extParam != null) {
                var htmldata = data.extParam.ITEM_DATE;
                var theid = data.extParam.ITEM_TYPE + "_ID";
                $("#"+theid).find("li").remove();
                $("#"+theid).find("div").remove();
                $("#"+theid).append(htmldata);
            }
        });
    });
}

/**
* 提交评审
* @param {type} expId 评审记录id
* @param {type} expStatu 评审记录状态
* @returns {undefined}
*/
function submitPs(expId,expStatu) {
   if(!confirm('确定要提交申请信息吗？')){
       return;
   }
   var url = thePath + '/jspc/zwpc/zwpc/submitItems';
   var params = new Object();
   params.expId = expId;
   params.expStatu = expStatu;
   sw.showProcessBar();
   sw.ajaxSubmit(url, params, function(data){
       sw.ajaxSuccessCallback(data, function() {
       });
   });
}