<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>(尚筹网)后台管理</title>
    <%@ include file="/WEB-INF/include/resources.jsp" %>
    <link rel="stylesheet" href="css/pagination.css">
    <script type="text/javascript" src="jquery/jquery.pagination.js"></script>
    <script type="text/javascript" src="axios/axios.min.js"></script>
    <script type="text/javascript" src="axios/qs.min.js"></script>
    <script type="text/javascript" src="api/role.js"></script>
    <script type="text/javascript">
        $(function () {
            // 初始化数据
            window.pageNum = 1
            window.pageSize = 2
            window.keyword = ""
            // 生成表格
            generateTable()

            $("#searchBtn").click(function () {
                window.keyword = $("#searchInput").val()
                generateTable()
            })

            $("#showModelBtn").click(function () {
                $("#addFormModel").modal("show")
            })

            $("#addFormModelBtn").click(async function () {
                let $addFormModelInput = $("#addFormModelInput");
                const roleName = $addFormModelInput.val().trim()
                const {data: result} = await axios.post("role/save", Qs.stringify({roleName}), {
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded'
                    }
                })

                // 关闭模态框
                $("#addFormModel").modal("hide")
                // 清空数据
                $addFormModelInput.val("")

                if (!result.success) {
                    alert('获取服务器资源失败')
                    return;
                }

                // 刷新分页数据
                window.pageNum = 999999
                generateTable()

            })

            let editRoleId = ""
            // 通过事件委派回显模态框数据
            $("#roleDataTableBody").on('click', '.edit-btn', function () {
                $("#editFormModel").modal('show')
                editRoleId = $(this).data('id')
                let editRoleName = $(this).parent().prev().text()
                $("#editFormModelInput").val(editRoleName)
            })

            $("#editFormModelBtn").click(function () {
                axios.post(
                    'role/edit', Qs.stringify({
                        id: editRoleId,
                        name: $("#editFormModelInput").val()
                    }), {
                        headers: {
                            'Content-Type': 'application/x-www-form-urlencoded'
                        }
                    }
                ).then(({data}) => {
                    $("#editFormModel").modal("hide")
                    if (!data.success) {
                        alert('获取服务器资源失败:' + data.message)
                        return;
                    }

                    generateTable()
                }).catch(reason => console.error(reason));
            })

            let checkedRemoveRoleIds = []
            $("#roleDataTableBody").on('click', '.remove-btn', function () {
                $("#removeFormModel").modal('show')
                $("#removeFormModelBody").empty()
                $("#removeFormModelBody").append('<p>' + $(this).parent().prev().text() + '</p>')
                checkedRemoveRoleIds = [$(this).data('id')]
            })

            $("#batchRemoveBtn").click(function () {
                const $checkedRole = $(".select-remove-role:checked")

                if ($checkedRole.length > 0) {
                    $("#removeFormModel").modal('show')
                    $("#removeFormModelBody").empty()
                    checkedRemoveRoleIds = []
                    $checkedRole.each(function (){
                        $("#removeFormModelBody").append('<p>' + $(this).parent().next().text() + '</p>')
                        checkedRemoveRoleIds.push($(this).data('id'))
                    })
                } else {
                    alert('请先选择要删除的角色')
                }

            })


            $("#removeFormModelBtn").click(function () {
                removeRoleByIds(checkedRemoveRoleIds).then(({data:result}) => {
                    $("removeFormModel").modal('hide')
                    if (result.success) {
                        alert('删除成功')
                        generateTable()
                    } else {
                        alert('删除失败:' + result.message)
                    }
                }).catch(reason => {
                    console.error('服务器出错啦！！' + reason)
                })
            })
        })
    </script>
</head>
<body>
<%@ include file="/WEB-INF/include/nav.jsp" %>
<div class="container-fluid">
    <div class="row">
        <%@ include file="/WEB-INF/include/sidebar.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
                </div>
                <div class="panel-body">
                    <form class="form-inline" role="form" style="float:left;">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <input id="searchInput" class="form-control has-success" type="text"
                                       placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button id="searchBtn" type="button" class="btn btn-warning"><i
                                class="glyphicon glyphicon-search"></i> 查询
                        </button>
                    </form>
                    <button id="batchRemoveBtn" type="button" class="btn btn-danger"
                            style="float:right;margin-left:10px;"><i
                            class="glyphicon glyphicon-remove"></i> 删除
                    </button>
                    <button id="showModelBtn" type="button" class="btn btn-primary" style="float:right;">
                        <i class="glyphicon glyphicon-plus"></i>
                        新增
                    </button>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr>
                                <th width="30">#</th>
                                <th width="30"><input id="allChecked" type="checkbox"></th>
                                <th>名称</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody id="roleDataTableBody">
                            </tbody>
                            <tfoot>
                            <tr>
                                <td colspan="6" align="center">
                                    <div id="Pagination" class="pagination"></div>
                                </td>
                            </tr>
                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<%@ include file="/WEB-INF/common/model-add-form.jsp" %>
<%@ include file="/WEB-INF/common/model-edit-form.jsp" %>
<%@ include file="/WEB-INF/common/model-remove-form.jsp" %>
<script type="text/javascript">
</script>
</body>
</html>
