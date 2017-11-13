/**
 *
 *
 * User: Administrator
 * Date：2017/11/13 0013
 * Time：9:47
 */

//重写alert
window.alert = function(msg, status, callback){
    if (status==null || status ==undefined){
        parent.layer.alert(msg, function(index){
            parent.layer.close(index);
            if(typeof(callback) === "function"){
                callback("ok");
            }
        });
    }else if (status=="warn"){
        parent.layer.alert(msg, {icon:0}, function(index){
            parent.layer.close(index);
            if(typeof(callback) === "function"){
                callback("ok");
            }
        });
    }else if (status=="success"){
        parent.layer.alert(msg, {icon:1}, function(index){
            parent.layer.close(index);
            if(typeof(callback) === "function"){
                callback("ok");
            }
        });
    }else if (status=="error"){
        parent.layer.alert(msg, {icon:2}, function(index){
            parent.layer.close(index);
            if(typeof(callback) === "function"){
                callback("ok");
            }
        });
    }else if (status=="help"){
        parent.layer.alert(msg, {icon:3}, function(index){
            parent.layer.close(index);
            if(typeof(callback) === "function"){
                callback("ok");
            }
        });
    }else if (typeof(status)=="function"){
        parent.layer.alert(msg, function(index){
            parent.layer.close(index);
            callback = status;
            if(typeof(callback) === "function"){
                callback("ok");
            }
        });
    }
}

//重写confirm式样框
window.confirm = function(msg, callback){
        parent.layer.confirm(msg, {btn: ['确定','取消']},
            function(){//确定事件
                if(typeof(callback) === "function"){
                    callback("ok");
                }
            });
    }


//选择一条记录
function getSelectedRow() {
    var grid = $("#jqGrid");
    var rowKey = grid.getGridParam("selrow");
    if(!rowKey){
        alert("请选择一条记录");
        return ;
    }

    var selectedIDs = grid.getGridParam("selarrrow");
    if(selectedIDs.length > 1){
        alert("只能选择一条记录");
        return ;
    }

    return selectedIDs[0];
}

//选择多条记录
function getSelectedRows() {
    var grid = $("#jqGrid");
    var rowKey = grid.getGridParam("selrow");
    if(!rowKey){
        alert("请选择一条记录");
        return ;
    }

    return grid.getGridParam("selarrrow");
}