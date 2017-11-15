$(function () {
    $("#jqGrid").jqGrid({
        url: '../sys/log/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '标题', name: 'title', index: 'title', width: 80 }, 			
			{ label: '操作类型', name: 'type', index: 'type', width: 80 }, 			
			{ label: '远程地址', name: 'remoteAddr', index: 'remote_addr', width: 80 }, 			
			{ label: '访问代理', name: 'userAgent', index: 'user_agent', width: 80 }, 			
			{ label: '请求URI', name: 'requestUri', index: 'request_uri', width: 80 }, 			
			{ label: '参数', name: 'params', index: 'params', width: 80 }, 			
			{ label: '执行方法', name: 'method', index: 'method', width: 80 }, 			
			{ label: '操作人', name: 'optation', index: 'optation', width: 80 }, 			
			{ label: '记录时间', name: 'createDate', index: 'create_date', width: 80 }			
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader : {
            root: "list",
            page: "currPage",
            total: "totalPage",
            records: "totalCount"
        },
        prmNames : {
            page:"page", 
            rows:"limit", 
            order: "order"
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		sysLog: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.sysLog = {};
		},
		update: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(id)
		},
		saveOrUpdate: function (event) {
			var url = vm.sysLog.id == null ? "../sys/log/save" : "../sys/log/update";
			$.ajax({
				type: "POST",
			    url: url,
                dataType: "json",
                cache: false,
                contentType: "application/json",
			    data: JSON.stringify(vm.sysLog),
			    success: function(r){
			    	if(r.code === 200){
						alert('操作成功', function(index){
							vm.reload();
						});
					}else{
						alert(r.msg);
					}
				}
			});
		},
		del: function (event) {
			var ids = getSelectedRows();
			if(ids == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: "../sys/log/delete",
                    dataType: "json",
                    cache: false,
                    contentType: "application/json",
				    data: JSON.stringify(ids),
				    success: function(r){
						if(r.code == 200){
							alert('操作成功', function(index){
								$("#jqGrid").trigger("reloadGrid", {page:1});
							});
						}else{
							alert(r.msg);
						}
					}
				});
			});
		},
		getInfo: function(id){
			$.get("../sys/log/info/"+id, function(r){
                vm.sysLog = r.obj;
            },"json");
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page
            }).trigger("reloadGrid");
		}
	}
});