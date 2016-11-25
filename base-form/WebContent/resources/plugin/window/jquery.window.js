/*!
 * jquery.window v1.0
 *
 * includes: jquery, bootstrap, fontawesome
 * Copyright 2016, http://www.dlshouwen.com
 */

/**
 * 打开模态窗口方法
 * @param {Object} option 窗口参数
 * 		{
 * 			{String} id : 窗口编号
 * 			{String} title : 窗口标题
 * 			{String} icon : 图标
 *  		{String} url : 窗口页面展示地址
 *   		{String} height : 页面显示高度
 * 			{Boolean} showCloseButton : 是否显示关闭按钮
 * 			{String} modalType : 模态样式，可选：sm\lg
 * 			{Function} callback : 回调方法
 * 			{Function} closeCallback : 关闭模态回调方法
 * 			{Boolean} showConfirmButton 是否显示按钮
 * 		}
 * @example sw.openModal({id:'dlshouwen'}, title:'大连首闻科技官方网站', icon:'dlshouwen', url:'http://www.dlshouwen.com', height:'400px', showCloseButton:false, modalType:'lg', callback:function(){}, closeCallback:function(){}});
 */
function openModal(option) {

    var defaultOption = {id: 'custom', title: '页面', icon: 'file-o', url: 'about:blank', width:'',height: 400, showCloseButton: true, modalType: '',showCoverButton: false, showConfirmButton: false,showExpreviewButton:false };
    option = $.extend({}, defaultOption, option);
    var modalTypeContent = '';
    if (option.modalType) {
        if (option.modalType == 'lg')
            modalTypeContent = 'modal-lg';
        if (option.modalType == 'sm')
            modalTypeContent = 'modal-sm';
    }
    var content = '';
    content += '<div class="modal fade" id="' + option.id + 'Modal" tabindex="-1" role="dialog" aria-labelledby="' + option.id + 'ModalLabel" aria-hidden="true">';
    if(option.width !=""){
        content += '	<div class="modal-dialog ' + modalTypeContent + '" style="width:'+option.width+'">';
    }else{
        content += '	<div class="modal-dialog ' + modalTypeContent + '">';
    }
    content += '		<div class="modal-content">';
    content += '			<div class="modal-header">';
    content += '				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>';
    content += '				<h5 class="modal-title" id="' + option.id + 'ModalLabel"><i class="fa fa-' + option.icon + '"></i>&nbsp;&nbsp;' + option.title + '</h5>';
    content += '			</div>';
    content += '			<div class="modal-body">';
    content += '				<iframe id="' + option.id + 'Frame" name="' + option.id + 'Frame" width="100%" height="' + option.height + '" src="' + option.url + '" style="border:0px;"></iframe>';
    content += '			</div>';

    if (option.showCloseButton != false) {
        content += '		<div class="modal-footer">';
        content += '			<button type="button" class="btn btn-default" data-dismiss="modal">';
        content += '				<i class="fa fa-times"></i>关闭';
        content += '			</button>';
        content += '		</div>';
    }
    
    if (option.showCoverButton != false) {
        content += '		<div class="modal-footer">';
        content += '			<button type="button" class="btn btn-primary" id="coverModel'+option.id+'">';
        content += '				<i class="fa fa-plus"></i>设置为封面';
        content += '			</button>';
        content += '			<button type="button" class="btn btn-primary" id="deleteModel'+option.id+'">';
        content += '				<i class="fa fa-trash-o"></i>删除';
        content += '			</button>';
        content += '                 <button type="button" class="btn btn-default" data-dismiss="modal">';
        content += '				<i class="fa fa-times"></i>关闭';
        content += '			</button>';
        content += '		</div>';
    }
    
    if (option.showConfirmButton != false) {
        content += '		<div class="modal-footer">';
        content += '			<button type="button" class="btn btn-primary" id="submitModel'+option.id+'">';
        content += '				<i class="fa fa-plus"></i>确认';
        content += '			</button>';
        content += '                 <button type="button" class="btn btn-default" data-dismiss="modal">';
        content += '				<i class="fa fa-times"></i>取消';
        content += '			</button>';
        content += '		</div>';
    }
    if (option.showExpreviewButton != false) {
        content += '		<div class="modal-footer">';
        content += '			<button type="button" class="btn btn-primary" id="passModel">';
        content += '				<i class="fa fa-plus"></i>通过';
        content += '			</button>';
        content += '                 <button type="button" class="btn btn-default" id="refusetModel">';
        content += '				<i class="fa fa-times"></i>拒绝';
        content += '			</button>';
        content += '		</div>';
    }

    content += '		</div>';
    content += '	</div>';
    content += '</div>';
    $('#' + option.id + 'Modal').remove();
    $('body').append(content);
    
    if (option.callback) {
        window.frames[option.id + 'Frame'].modalCallback = option.callback;
    }
    if (option.closeCallback) {
        $('#' + option.id + 'Modal').on('hidden.bs.modal', option.closeCallback);
    }
    if (option.showCoverButton) {
        $('#coverModel'+option.id).on('click', option.callback);
        $('#deleteModel'+option.id).on('click', option.callback);
    }
    if (option.showConfirmButton) {
        $('#submitModel'+option.id).on('click', option.callback);
    }
    if (option.showExpreviewButton) {
        $('#passModel').on('click', option.callback);
        $('#refusetModel').on('click', option.callback);
    }
    $('#' + option.id + 'Modal').modal('show');
    return content;
}

/**
 * 关闭模态窗口
 * @param {String} id 需要关闭的模态窗口编号
 * @param {Object} windowObj 执行关闭模态窗口的对象
 * @example sw.closeModal('dlshouwen', parent);
 */
function closeModal(id, windowObj) {
    if (windowObj) {
        windowObj.$('#' + id + 'Modal').modal('hide');
    } else {
        $('#' + id + 'Modal').modal('hide');
    }
}