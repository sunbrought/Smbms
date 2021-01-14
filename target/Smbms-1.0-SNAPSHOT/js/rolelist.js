var roleObj;

//订单管理页面上点击删除按钮弹出删除框(billlist.jsp)
function deleteRole(obj){
	$.ajax({
		type:"GET",
		url:path+"/role/role.do",
		data:{method:"delrole",roleid:obj.attr("roleid")},
		dataType:"json",
		success:function(data){
			if(data.delResult == "true"){//删除成功：移除删除行
				cancleBtn();
				obj.parents("tr").remove();
			}else if(data.delResult == "false"){//删除失败
				//alert("对不起，删除订单【"+obj.attr("rolecc")+"】失败");
				changeDLGContent("对不起，删除角色【"+obj.attr("rolecc")+"】失败");
			}else if(data.delResult == "false1"){//删除失败
				changeDLGContent("对不起，该角色【"+obj.attr("rolecc")+"】下还有用户信息,删除失败");
			}else if(data.delResult == "notexist"){
				//alert("对不起，订单【"+obj.attr("rolecc")+"】不存在");
				changeDLGContent("对不起，订单【"+obj.attr("rolecc")+"】不存在");
			}
		},
		error:function(data){
			alert("对不起，删除失败");
		}
	});
}

function openYesOrNoDLG(){
	$('.zhezhao').css('display', 'block');
	$('#removeBi').fadeIn();
}

function cancleBtn(){
	$('.zhezhao').css('display', 'none');
	$('#removeBi').fadeOut();
}
function changeDLGContent(contentStr){
	var p = $(".removeMain").find("p");
	p.html(contentStr);
}

var ss=null;

$(function(){
	$(".viewRole").on("click",function(){
		//将被绑定的元素（a）转换成jquery对象，可以使用jquery方法
		var obj = $(this);
		$("#viewInfo").html("");
		$.ajax({
			type:"GET",
			url:path+"/role/role.do",
			data:{method:"view",roleid:obj.attr("roleid")},
			dataType:"json",
			success:function(data){
				ss="<p><strong>角色编码：</strong><span>"+data.roleCode+"</span></p><p><strong>角色名称：</strong><span>"+data.roleName+"</span></p><p><strong>创建时间：</strong><span>"+data.creationDate+"</span></p>";
				$(".providerView").html(ss);
			},
			error:function(data){
				alert("查看失败！");
			}
		});
		//window.location.href=path+"/role/role.do?method=view&roleid="+ obj.attr("roleid");
	});
	
	$(".modifyRole").on("click",function(){
		var obj = $(this);
		window.location.href=path+"/role/role.do?method=modify&roleid="+ obj.attr("roleid");
	});
	$('#no').click(function () {
		cancleBtn();
	});
	
	$('#yes').click(function () {
		deleteRole(roleObj);
	});

	$(".deleteRole").on("click",function(){
		roleObj = $(this);
		changeDLGContent("你确定要删除【"+roleObj.attr("rolecc")+"—"+roleObj.attr("roleName")+"】吗？");
		openYesOrNoDLG();
	});
});