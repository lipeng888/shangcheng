<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="/js/jquery-easyui-1.4.1/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="/js/jquery-easyui-1.4.1/themes/icon.css" />
<link rel="stylesheet" type="text/css" href="/css/taotao.css" />
<script type="text/javascript" src="/js/jquery-easyui-1.4.1/jquery.min.js"></script>
<script type="text/javascript" src="/js/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/js/jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="/js/common.js"></script>

<title>内容分类管理</title>
</head>
<body>
<div>
	 <ul id="contentCategory" class="easyui-tree">
    </ul>
</div>
<div id="contentCategoryMenu" class="easyui-menu" style="width:120px;" data-options="onClick:menuHandler">
    <div data-options="iconCls:'icon-add',name:'add'">添加</div>
    <div data-options="iconCls:'icon-remove',name:'rename'">重命名</div>
    <div class="menu-sep"></div>
    <div data-options="iconCls:'icon-remove',name:'delete'">删除</div>
</div>
<script type="text/javascript">
//页面加载成功后执行以下逻辑
$(function(){
	//id选择器，获取EasyUI的树组件，创建树,初始化树
	$("#contentCategory").tree({
		//创建树需要数据，所发起的请求
		url : '/rest/content/category',
		animate: true,
		method : "GET",
		//给节点绑定右键点击事件，node就是右键点击的节点
		onContextMenu: function(e,node){
			//关闭window默认的右键菜单
            e.preventDefault();
			//执行树的select，作用是选中右键点击的节点
            $(this).tree('select',node.target);
			//id选择器，获取EasyUI的Menu组件
			//执行Menu的show方法，展示菜单
            $('#contentCategoryMenu').menu('show',{
            	//显示菜单所在的位置，其实就是鼠标所在的位置
                left: e.pageX,
                top: e.pageY
            });
        },
        //在编辑之后，node就是刚刚进行编辑的节点
        onAfterEdit : function(node){
        	//获取树
        	var _tree = $(this);
        	//判断节点id是否为0，其实就是判断是否是新加的节点
        	if(node.id == 0){
        		// 新增节点，发起了Ajax的Post请求
        		//http://manager.taotao.com/rest/content/category/add
        		$.post("/rest/content/category/add",{parentId:node.parentId,name:node.text},function(data){
        			//执行树的update方法，作用是更新节点
        			_tree.tree("update",{
        				//更新的节点是谁，其实就是修改新加的节点
        				target : node.target,
        				//修改节点id
        				id : data.id
        			});
        		});
        	//如果id不为0，表示这是修改操作
        	}else{
        		$.ajax({
        			   type: "POST",
        			   url: "/rest/content/category/update",
        			   data: {id:node.id,name:node.text},
        			   success: function(msg){
        				   //$.messager.alert('提示','新增商品成功!');
        			   },
        			   error: function(){
        				   $.messager.alert('提示','重命名失败!');
        			   }
        			});
        	}
        }
	});
});
//Menu菜单的点击事件，item就是点击的菜单项
function menuHandler(item){
	//id选择器获取树
	var tree = $("#contentCategory");
	//调用树的getSelected方法，作用是获取选中的节点，其实就是获取到了之前右键点击的节点
	var node = tree.tree("getSelected");
	//===:     1===1：结果是true   "1"===1：结果是false
	//== :     1==1:结果是true   "1"==1：结果是true 
	//判断点击的菜单项的name是否为"add"，其实就是判断菜单项是否是添加按钮
	if(item.name === "add"){
		//执行树的append方法，作用是追加一个新的节点
		tree.tree('append', {
			//设置追加的新的节点的父是谁,就是之前右键点击的节点
			//node?;if(node)判断node不为空，或者node不是未定义
            parent: (node?node.target:null),
            //新加节点的数据
            data: [{
                text: '新建分类',
                id : 0,
                parentId : node.id
            }]
        }); 
		//执行树的find方法，作用是查找id为0的节点，其实找的就是刚刚创建的新加的节点
		var _node = tree.tree('find',0);
		//tree.tree("select",_node.target):选中节点，其实就是选中了新加的节点
		//tree('beginEdit',_node.target):开始编辑，其实就是编辑新加的节点
		tree.tree("select",_node.target).tree('beginEdit',_node.target);
	//判断点击的菜单项的name是否为"rename"，其实就是判断菜单项是否是重命名按钮
	}else if(item.name === "rename"){
		//执行树的beginEdit，开始编辑之前右键点击的节点
		tree.tree('beginEdit',node.target);
	//判断点击的菜单项的name是否为"delete"，其实就是判断菜单项是否是删除按钮
	}else if(item.name === "delete"){
		//提示用户，确认是否删除。如果没有业务要求删除的时候提示，默认情况是要添加删除提示
		$.messager.confirm('确认','确定删除名为 '+node.text+' 的分类吗？',function(r){
			//判断用户是否确认，如果确认删除执行以下的Ajax
			if(r){
				$.ajax({
     			   type: "POST",
     			   url: "/rest/content/category/delete",
     			   data : {parentId:node.parentId,id:node.id},
     			   success: function(msg){
     				   //$.messager.alert('提示','新增商品成功!');
     				  //执行树的remove方法，作用是删除右键点击的节点
     				  tree.tree("remove",node.target);
     			   },
     			   error: function(){
     				   $.messager.alert('提示','删除失败!');
     			   }
     			});
			}
		});
	}
}
</script>
</body>
</html>