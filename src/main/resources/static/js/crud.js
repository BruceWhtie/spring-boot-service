// 全局表格对象
var $mainTable = false;
/**
 * 初始方法
 */
$(document).ready(function () {
    // 数据表格初始化
    $mainTable = $('#mainTable').bootstrapTable({
        url: 'data/data.json',
        //method:'post',
        queryParams: function (params) {
            params.search = $('#searchInput').val();
            return params;
        },
        height: Common.getContentHeight(),
        striped: true,
        showRefresh: true,
        showToggle: true,
        showColumns: true,
        clickToSelect: true,
        singleSelect: true,
        minimumCountColumns: 2,
        pagination: true,
        paginationLoop: false,
        sidePagination: 'server',
        pageList: [10, 20, 50, 100],
        silentSort: true,
        smartDisplay: false,
        uniqueId: 'id',
        escape: true,
        toolbar: '#tableToolbar',
        columns: [
            {field: 'id', title: '编号', align: 'center', valign: 'middle'},
            {field: 'name', title: '名称', align: 'center', valign: 'middle'},
            {field: 'type', title: '分类', align: 'center', valign: 'middle'},
            {field: 'dateTime', title: '时间', align: 'center', valign: 'middle'},
            {field: 'style', title: '风格', align: 'center', valign: 'middle'},
            {field: 'tags', title: '标签', align: 'center', valign: 'middle'},
            {field: 'remark', title: '备注', align: 'center', valign: 'middle'},
            {
                field: 'editAction', title: '编辑', width: '30px',
                align: 'center', valign: 'middle', formatter: function () {
                    return '<div class="edit_button pointer_hover">' +
                        '<i class="glyphicon glyphicon-edit"></i></div>';
                }
            }, {
                field: 'deleteAction', title: '删除', width: '30px',
                align: 'center', valign: 'middle', formatter: function () {
                    return '<div class="delete_button pointer_hover">' +
                        '<i class="glyphicon glyphicon-remove"></i></div>';
                }
            }
        ], onClickCell: function (field, value, row) {
            if (field === 'editAction') {
                editAction(row);
            } else if (field === 'deleteAction') {
                deleteAction(row);
            }
        }
    });

    // 数据表格动态高度
    $(window).resize(function () {
        $('#mainTable').bootstrapTable('resetView', {
            height: Common.getContentHeight()
        });
    });

    // 日期控件初始化
    layui.laydate.render({
        elem: '#dateTime',
        type: 'datetime'
    });
});

/**
 * 搜索内容
 */
function searchAction() {
    $mainTable.bootstrapTable('refresh');
}

/**
 * 重置表单
 */
function resetEditForm(resetData) {
    $('#editForm')[0].reset();
    if (resetData) {
        $("input[name='name']").val(resetData.name);
        $("input[name='type'][title='" + resetData.type + "']").attr('checked', true);
        $("select[name='style']").val(resetData.style);
        $("input[name='dateTime']").val(resetData.dateTime);
        $("textarea[name='remark']").val(resetData.remark);
        resetZTree(resetData.tags);
    } else {
        resetZTree();
    }
}

// 窗口索引
var formWinIndex = layer.index;

/**
 * 打开新增窗口
 */
function openEditWin(resetData) {
    formWinIndex = layer.open({
        id: 'formWinOpen',
        title: '<i class="glyphicon glyphicon-plus"></i> 新增项目',
        area: '600px',
        zIndex: 10,
        type: 1,
        content: $('#formWin'),
        end: function () {
            $('#formWin').hide();
        }
    });
    resetEditForm(resetData);
}

/**
 * 手动关闭窗口
 */
function closeFormWin() {
    layer.close(formWinIndex);
}

/**
 * 绑定表单提交事件
 */
function forSubmitAction(beforeData, action) {
    // 提交表单
    layui.form.on('submit', function (data) {
        beforeData.tags = [];
        $.fn.zTree.getZTreeObj("zTreeTags").getCheckedNodes()
            .forEach(function (node) {
                beforeData.tags.push(node.name);
            });
        beforeData.name = data.field.name;
        beforeData.type = data.field.type;
        beforeData.style = data.field.style;
        beforeData.dateTime = data.field.dateTime;
        beforeData.remark = data.field.remark;
        action(beforeData);
        closeFormWin();
        return false;
    });
}

/**
 * 添加项目
 */
function createAction() {
    openEditWin();
    forSubmitAction({}, function (afterData) {
        afterData.id = afterData.id || 1;
        $mainTable.bootstrapTable('getData').forEach(function (data) {
            afterData.id = data.id < afterData.id ? afterData.id : data.id + 1;
        });
        $mainTable.bootstrapTable('append', [afterData]);
    });
}

/**
 * 编辑项目
 */
function editAction(row) {
    openEditWin(row);
    forSubmitAction(row, function (afterData) {
        $mainTable.bootstrapTable('updateRow', {row: afterData});
    });
}

/**
 * 删除项目
 */
function deleteAction(row) {
    layer.confirm('你确定要删除 ' + row.name + ' ?', {
        title: '<i class="glyphicon glyphicon-question-sign"></i> 确认删除',
        icon: 3
    }, function (index) {
        $mainTable.bootstrapTable('removeByUniqueId', row.id);
        layer.close(index);
    });
}

// zTree数据
var zTreeData = false;

/**
 * 初始化zTree
 */
function resetZTree(data) {
    // 先获取数据再初始化
    if (!zTreeData) {
        $.getJSON("data/tree.json", function (result) {
            zTreeData = result;
            resetZTree();
        });
        return;
    }
    // 如果树不存在或使用初始数据，则初始化树
    var $zTreeObj = $.fn.zTree.getZTreeObj("zTreeTags");
    if (!$zTreeObj || !data) {
        $zTreeObj = $.fn.zTree.init($("#zTreeTags"), {
            view: {
                showLine: false
            },
            check: {
                enable: true
            }
        }, zTreeData);
    }
    // 使用自定义数据重置树
    if (data) {
        $zTreeObj.checkAllNodes(false);
        data.forEach(function (value) {
            Common.traverseTree($zTreeObj.getNodes(), function (node) {
                if (node.name === value) {
                    $zTreeObj.checkNode(node, true, false);
                }
            });
        });
    }
}
